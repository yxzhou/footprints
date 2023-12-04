/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easy;

import junit.framework.Assert;

/**
 * _
 * 
 * Given a string S, find an alphabetic character whose upper and lower case letters appear in S. Return the upper case of the letter, or the largest letter if more than one answer exists, or "NO" if none exists.
 * 
 * Note:  1<=len(s)<=10^6 
 * 
 * Example
 * Input: S = "admeDCAB"
 * Output: "D"
 * 
 * Input: S = "adme"
 * Output: "NO"
 *
 * @author yuanxi
 */
public class LargestLetter {
    
    /**
     * @param s: a string
     * @return the largest letter if more than one answer exists, or "NO"
     */
    public String largestLetter(String s) {
        if(s == null){
            return "NO";
        }

        /* example, 
         * found[0] is true if it found 'A' in s 
         * found[25] is true if it found 'Z' in s 
         * found[26] is true if it found 'a' in s 
         * found[51] is true if it found 'z' in s 
         */
        boolean[] found = new boolean[52]; //default all are false

        char result = ' ';
        char c;
        int diff;
        for(int i = 0, n = s.length(); i < n; i++){
            c = s.charAt(i);

            diff = c - 'A';
            if( diff > -1 && diff < 26 ){  // it's between 'A' and 'Z'
                if(found[diff + 26] && c > result ){
                    result = c;
                }

                found[diff] = true;
            }else if( diff > 31 && diff < 58 ){ // it's between 'a' and 'z'
                if(found[diff - 32] && Character.toUpperCase(c) > result ){
                    result = Character.toUpperCase(c);
                }

                found[diff - 6] = true;
            }
        }

        return result == ' ' ? "NO" : String.valueOf(result);
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        LargestLetter sv = new LargestLetter();
        
        String[][] inputs = {
            //{s, expect}
            {"admeDCAB", "D"},
            {"adme", "NO"},
            {null, "NO"},
            {"", "NO"},
            {" ", "NO"},
        };
        
        for(String[] input : inputs){
            System.out.println(String.format("\ncall largestLetter(), s = %s, expect = %s", input[0], input[1]));
            
            Assert.assertEquals(input[1], sv.largestLetter(input[0]));
        }
    }
    
    
}
