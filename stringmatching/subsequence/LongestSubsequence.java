/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stringmatching.subsequence;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1190
 *
 * Given a string and a string dictionary, find the longest string in the dictionary that can be formed by deleting some
 * characters of the given string. If there are more than one possible results, return the longest word with the
 * smallest lexicographical order. If there is no possible result, return the empty string.
 *
 * Constraints:
 *   All the strings in the input will only contain lower-case letters. 
 *   The size of the dictionary won't exceed 1,000. 
 *   The length of all the strings in the input won't exceed 1,000. 
 * 
 * Example 1:
 * Input: s = "abpcplea", d = ["ale","apple","monkey","plea"]
 * Output: "apple" 
 * 
 * Example 2:
 * Input: s = "abpcplea", d = ["a","b","c"]
 * Output: "a"
 * 
 * Thoughts:
 *   If a string can be formed by deleting some characters of the given string, the sting is a subsequence of the given 
 *   string. 
 * 
 *   How to check if it is a subsequence?  isSubsequence(s, w) 
 *   Define n as the length of s, m as the length of strings in dictionary, 
 *    m1) with two pointer, check characters in both string. 
 *        Time O(n + m)
 *    m2) store the s into TreeSet[26], 
 *        Time O( m * logn )
 * 
 *   if n >> m, the m2 is better. Here n == m, the m1 is better.
 * 
 */
public class LongestSubsequence {
    /**
     * 
     * 
     * @param s: a string
     * @param d: List[str]
     * @return the longest subsequence
     */
    public String findLongestWord_TreeSet(String s, List<String> d) {
        if(s == null || d == null){
            return "";
        }

        TreeSet<Integer>[] trees = new TreeSet[26]; //All the strings in the input will only contain lower-case letters
        for(int i = 0; i < trees.length; i++){
            trees[i] = new TreeSet<>();
        }

        for(int i = 0, n = s.length(); i < n; i++){
            trees[s.charAt(i) - 'a'].add(i);
        }

        String result = "";
        Integer k;
        int diff;
        
        for(String w : d){
            diff = result.length() - w.length();
            if( diff > 0 || ( diff == 0 && result.compareTo(w) <= 0) ){
                continue;
            }

            k = -1;
            for(int i = 0 ; i < w.length(); i++){
                k = trees[w.charAt(i) - 'a'].higher(k);
                
                if(k == null){
                    break;
                }
            }

            if(k != null){
                result = w;
            }
        }

        return result;
    }

    /**
    private int compare(String a, String b){
        int diff = a.length() - b.length();
        if(diff != 0){
            return diff;
        }

        for(int i = 0; i < a.length(); i++ ){
            diff = a.charAt(i) - b.charAt(i);

            if(diff == 0){
                continue;
            }
            
            return -diff;
        }

        return 0;
    }
    */
    
    /**
     * 
     * 
     * @param s: a string
     * @param d: List[str]
     * @return the longest subsequence
     */
    public String findLongestWord(String s, List<String> d) {
        if(s == null || d == null){
            return "";
        }

        int n = s.length();
        
        String result = "";
        int diff;
        int i, j;
        for(String w : d){
            diff = result.length() - w.length();
            if( diff > 0 || ( diff == 0 && result.compareTo(w) <= 0) ){
                continue;
            }

            for( i = 0, j = 0; i < n && j < w.length(); i++){
                if(s.charAt(i) == w.charAt(j)){
                    j++;
                }
            }

            if(j == w.length()){
                result = w;
            }
        }

        return result;
    }
    
    public static void main(String[] args){
        
        String[][][] inputs = {
            //{ {s}, d, expect}
            {
                {""},
                {},
                {""}
            },
            {
                {"aaa"},
                {"a", "aa", "aaa"},
                {"aaa"}
            },
            {
                {"bab"},
                {"ba","ab","a","b"},
                {"ab"}
            },
            {
                {"abpcplea"},
                {"ale","apple","monkey","plea"},
                {"apple"}
            },

        };
        
        LongestSubsequence sv = new LongestSubsequence();
        
        for(String[][] input : inputs){
            System.out.println(String.format("\ns=%s, dictionary = %s, \nexpect: %s", input[0][0], Misc.array2String(input[1]), input[2][0] ));
            
            Assert.assertEquals(input[2][0], sv.findLongestWord(input[0][0], Arrays.asList(input[1])));
        }
    }

}
