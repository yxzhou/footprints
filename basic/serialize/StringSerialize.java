package basic.serialize;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Design an algorithm to encode a list of strings to a string. The encoded 
 * string is then sent over the network and is decoded back to the original list of strings.
 * 
   Machine 1 (sender) has the function:
        string encode(vector<string> strs) {
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
        if (strs == null || strs.size() == 0) {
            return "";
        }
        
        StringBuffer sb = new StringBuffer();
         
        for (String str : strs) {
            if (str == null || str.length() == 0) {
                sb.append("0#");
            } else {
                sb.append(str.length() + "#" + str);
            }
        }
         
        return sb.toString();
    }
 
    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> strs = new ArrayList<>();
         
        if (s == null || s.length() == 0) {
            return strs;
        }
         
        for (int i = 0; i < s.length(); ) {
            int j = i;
            while (j < s.length() && Character.isDigit(s.charAt(j))) {
                j++;
            }
             
            int num = Integer.parseInt(s.substring(i, j));
            i = j;
            i++; // skip '#'
            if (num == 0) {
                strs.add("");
            } else {
                strs.add(s.substring(i, i + num));
            }
             
            i += num;
        }
         
        return strs;
    }
    
}
