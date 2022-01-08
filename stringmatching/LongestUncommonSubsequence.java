/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringmatching;

/**
 *_https://www.lintcode.com/problem/1192
 * 
 * Given a group of two strings, you need to find the longest uncommon subsequence of this group of two strings. The
 * longest uncommon subsequence is defined as the longest subsequence of one of these strings and this subsequence
 * should not be any subsequence of the other strings.
 *
 * A subsequence is a sequence that can be derived from one sequence by deleting some characters without changing the
 * order of the remaining elements. Trivially, any string is a subsequence of itself and an empty string is a
 * subsequence of any string.
 *
 * The input will be two strings, and the output needs to be the length of the longest uncommon subsequence. If the
 * longest uncommon subsequence doesn't exist, return -1.
 *
 * Notes:
 *   Both strings' lengths will not exceed 100. 
 *   Only letters from a ~ z will appear in input strings. 
 * 
 * Example 
 * Input: "aba", "cdc" Output: 3 
 * Explanation: The longest uncommon subsequence is "aba" (or "cdc"), because "aba" is a subsequence of "aba", but not 
 * a subsequence of any other strings in the group of two strings.
 * 
 */
public class LongestUncommonSubsequence {
    /**
     * @param a: a string
     * @param b: a string
     * @return: return a integer
     */
    public int findLUSlength(String a, String b) {
        if(a == null || a.isEmpty()){
            return b == null || b.isEmpty()? -1 : b.length();
        }
        if(b == null || b.isEmpty()){
            return a.length();
        }

        int i = a.length() - 1;
        int j = b.length() - 1;

        while(i >= 0 && j >= 0 ){
            if(a.charAt(i) == b.charAt(j)){
                i--;
            }

            j--;
        }

        return i == -1 ? -1 : Math.max(a.length(), b.length()) ;

    }
    
    
    public int findLUSlength_n(String a, String b) {
        if(a == null || a.isEmpty()){
            return b == null || b.isEmpty()? -1 : b.length();
        }
        if(b == null || b.isEmpty()){
            return a.length();
        }
        
        return a.equals(b)? -1 : Math.max(a.length(), b.length());
    }
}
