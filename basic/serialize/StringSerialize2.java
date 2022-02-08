/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basic.serialize;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import util.Misc;

/**
 * continue on StringSerialize, which supports empty string and null
 *   here it does not support null. 
 * 
 * 
 */
public class StringSerialize2 {

    /**
     * 
     * @param strs: a list of strings
     * @return encodes a list of strings to a single string.
     */
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();

        String s;
        for(String str : strs){
            if(str == null || str.isEmpty()){
                //sb.append("#");
            } else {
                s = str.replaceAll("/", "//").replaceAll("#", "/#");
                sb.append(s);
            }
            sb.append("#");
        }

        return sb.toString();
    }

    /*
     * @param str: A string
     * @return dcodes a single string to a list of strings
     */
    public List<String> decode(String str) {
        List<String> result = new LinkedList<>();

        //System.out.println(" --decode-: "+ str);
        StringBuilder sb = new StringBuilder();
        char c;

        for(int i = 0; i < str.length(); i++){
            c = str.charAt(i);
            
            if(c == '/'){
                sb.append(str.charAt(++i));
            }else if(c == '#'){                
                if(sb.length() == 0){
                    result.add("");
                }else{
                    result.add(sb.toString());
                    sb.delete(0, sb.length());
                }
            }else{
                sb.append(c);
            }
        
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
            //{"try", "escape", null, "string"},  // a solution, example { "", null, "end" }, encode to "0##-1##3#end#"
        };
        
        StringSerialize2 sv = new StringSerialize2();
        
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
