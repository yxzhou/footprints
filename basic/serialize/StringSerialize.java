/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basic.serialize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/659
 * 
 * Design an algorithm to encode a list of strings to a string. The encoded string is then sent over the network and is
 * decoded back to the original list of strings.
 * 
   Machine 1 (sender) has the function:
        string encode(vector<String> strs) {
          // ... your code
          return encoded_string;
        }
    
   Machine 2 (receiver) has the function:
        vector<string> decode(string s) {
          //... your code
          return strs;
        }
        
   So Machine 1 does:
        string encoded_string = encode(strs);
   and Machine 2 does:
        vector<string> strs2 = decode(encoded_string);
   strs2 in Machine 2 should be the same as strs in Machine 1.
   Implement the encode and decode methods.
    
   Note:
    1, The string may contain any possible characters out of 256 valid ascii characters. 
    Your algorithm should be generalized enough to work on any possible characters.
    2, Do not use class member/global/static variables to store states. 
    Your encode and decode algorithms should be stateless.
    3, Do not rely on any library method such as eval or serialize methods. 
    You should implement your own encode/decode algorithm.
 *
 */


public class StringSerialize {

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        if (strs == null || strs.isEmpty()) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
         
        for (String str : strs) {
            if (str == null) {
                sb.append("-1##");
            } else {
                sb.append(str.length()).append("#").append(str).append("#");
            }
        }
         
        return sb.toString();
    }
 
    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> result = new ArrayList<>();
         
        if (s == null || s.length() == 0) {
            return result;
        }
         
        int num;
        for (int i = 0, j; i < s.length(); ) {
            j = s.indexOf("#", i);
            num = Integer.parseInt(s.substring(i, j));
            
            i = j + 1; // skip '#'
            
            if(num == -1){
                result.add(null);
                j++;
            }else if (num == 0) {
                result.add("");
                j++;
            } else {
                j = i + num;
                result.add(s.substring(i, j));
            }
             
            i = j + 1;
        }
         
        return result;
    }

    
    
    public static void main(String[] args){
        String[][] inputs= {
            {"we", "say", ":", "yes"},
            {"try", "escape", "/#", "string1"},
            {"try", "escape/", "/#", "string1/"},
            {"try", "escape", "//#", "string1 //# string2"},
            {"try", "empty string", "", "end"},
            {"try", "escape", null, "string"},  // a solution, example { "", null, "end" }, encode to "0##-1##3#end#"
        };
        
        StringSerialize sv = new StringSerialize();
        
        List<String> list;
        for(String[] input : inputs){
            System.out.println(String.format("\n%s", Arrays.toString(input) ));
            
            list = Arrays.asList(input);
            String encoded = sv.encode(list);
            
            System.out.println(String.format("%s", Misc.array2String(sv.decode(encoded)) ));
            
            Assert.assertEquals(list, sv.decode(encoded));
        }
    }
}
