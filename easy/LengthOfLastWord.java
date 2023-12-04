package easy;

import java.util.StringTokenizer;
import junit.framework.Assert;

/**
 * Given a string s consists of upper/lower-case alphabets and empty space characters ' ', 
 * return the length of last word in the string. If the last word does not exist, return 0. 
 * 
 * Note: A word is defined as a character sequence consists of non-space characters only. 
 * 
 * For example, 
 * Given s = "Hello World", return 5.
 */

public class LengthOfLastWord {


    /**
     * @param s: A string
     * @return the length of last word
     */
    public int lengthOfLastWord(String s) {
        if(s == null){
            return 0;
        }

        int count = 0;

        char c;
        for(int i = s.length() - 1; i >= 0; i--){
            c = s.charAt(i);
            if(c == ' '){
                if(count > 0){
                    break;
                }
            }else { // c is upper/lower-case alphabts
                count++;
            } 
        }

        return count;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        LengthOfLastWord sv = new LengthOfLastWord();

        String[][] inputs = { 
            {null, "0"}, 
            {" ", "0"},
            {"  ", "0"},
            {"last word", "4"},
            {"last word ", "4"},
            {"word", "4"},
            {"word ", "4"},
            {" word ", "4"},
        };
        
        
        for (String[] input : inputs) {
            System.out.println(String.format("\ncall lengthOfLastWord, s - %s, expect = %s ", input[0], input[1]) );
            
            Assert.assertEquals(Integer.parseInt(input[1]), sv.lengthOfLastWord(input[0]));
        }
    }

}
