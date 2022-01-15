/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array;

import java.util.Arrays;
import org.junit.Assert;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 *
 * Example, 
 * For "abcabcbb" the longest substring without repeating letters is "abc", which the length is 3. 
 * For "bbbbb"  the longest substring is "b", with the length of 1.
 *
 *
 *
 */
public class LongestSubstring {
    /**
     * define n as the length of s
     * Time Complexity O(n), Space O(1)
     * 
     * @param s
     * @return 
     */
    
    public int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }

        int max = 0;
        
        int[] p = new int[256]; // the index of latest found
        Arrays.fill(p, -1);
                
        int c;
        for (int base = -1, i = 0, n = s.length(); i < n; i++) {
            c = s.charAt(i);

            base = Math.max(p[c], base);
            max = Math.max(max, i - base);

            p[c] = i;
        }

        return max;
    }
    
    /**
     * define n as the length of s
     * Time Complexity O(n), Space O(1)
     * 
     * @param s
     * @return 
     */
    
    public String longestSubstring(String s) {
        if (s == null) {
            return "";
        }

        int maxLength = 0;
        int maxLeft = 0;
        
        int[] p = new int[256]; // the index of latest found
        Arrays.fill(p, -1);
                
        int c;
        int len;
        for (int base = -1, i = 0, n = s.length(); i < n; i++) {
            c = s.charAt(i);

            base = Math.max(p[c], base);
            
            len = i - base;
            if(maxLength < len){
                maxLength = len;
                maxLeft = base + 1;
            }

            p[c] = i;
        }

        return s.substring(maxLeft, maxLeft + maxLength);
    }
    
    public static void main(String[] args) {
        String[][] inputs = {
            {null, ""}, 
            {"a", "a"},
            {"ab", "ab"}, 
            {"aaaa", "a"},
            {"aababa", "ab"},
            {"abcabcaacb", "abc"},
            {"pwwkew", "wke"},
        };

        LongestSubstring sv = new LongestSubstring();

        for (String[] input : inputs) {
            
            System.out.println(String.format("\n input: %s, \tthe Longest Substring without repeating character is: %s", input[0], input[1]));

            //System.out.println("lengthOfLongestSubstring: \t" + sv.lengthOfLongestSubstring(input[0]));
            Assert.assertEquals(input[1].length(), sv.lengthOfLongestSubstring(input[0]));
            
            Assert.assertEquals(input[1], sv.longestSubstring(input[0]));
        }

    }
}
