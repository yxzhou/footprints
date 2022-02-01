/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringmatching.subsequence;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * _https://www.lintcode.com/problem/1191/
 * 
 * Given a list of strings, you need to find the longest uncommon subsequence among them. The longest uncommon
 * subsequence is defined as the longest subsequence of one of these strings and this subsequence should not be any
 * subsequence of the other strings.
 *
 * A subsequence is a sequence that can be derived from one sequence by deleting some characters without changing the
 * order of the remaining elements. Trivially, any string is a subsequence of itself and an empty string is a
 * subsequence of any string.
 *
 * The input will be a list of strings, and the output needs to be the length of the longest uncommon subsequence. If
 * the longest uncommon subsequence doesn't exist, return -1.
 *
 * Notes:
 *   All the given strings' lengths will not exceed 10. 
 *   The length of the given list will be in the range of [2, 50].
 * 
 * Example 1:
 * Input: ["aaa","acb"]  Output 3
 * 
 * Example 2:
 * Input: ["aabbcc", "aabbcc","cb"] Output: 2
 * 
 */
public class LongestUncommonSubsequenceII {
    /**
     * @param strs: List[str]
     * @return: return an integer
     */
    public int findLUSlength(String[] strs) {
        int result = -1;

         Map<String, Integer> uniques = new HashMap<>();
         for(String str : strs){
             if(str != null  ){
                 uniques.put(str, uniques.getOrDefault(str, 0) + 1);
             }
         }
        
        outer: for(String s1 : uniques.keySet()){
            if(uniques.get(s1) == 1 ){

                for(String s2 : uniques.keySet()){
                    if( isCommonSubsequence(s1, s2) ){
                        continue outer;
                    }
                }

                result = Math.max(result, s1.length());
            }
        }

        return result;
    }

    private boolean isCommonSubsequence(String s1, String s2){
        int n = s1.length();
        int m = s2.length();
        if( n > m) {
            return false;
        }

        int i = n - 1;
        int j = m - 1;
        while( i >= 0 && j >= 0 ){
            if(s1.charAt(i) == s2.charAt(j) ){
                i--;
            }

            j--;
        }

        return i == -1 ? ( j == -1 ? false : true) : false; // i and j both -1 means s1 == s2, not just equal. 
    }
    
    
    /**
     * @param strs: List[str]
     * @return: return an integer
     */
    public int findLUSlength_2(String[] strs) {
        int result = -1;
        
        int n = strs.length;
        
        Set<String> uniques = new HashSet<>();
        outer: for(int i = 0; i < n; i++ ){
            if(uniques.contains(strs[i]) ){
                continue;
            }
            uniques.add(strs[i]);
            
            for(int j = 0; j < n; j++){
               if(i == j){
                   continue;
               }
               if(isCommonSubsequence2(strs[i], strs[j]) ){
                   continue outer;
               }
               
               result = Math.max(result, strs[i].length() );
            }
        }
        
        return result;
    }
    
    private boolean isCommonSubsequence2(String s1, String s2){
        int n = s1.length();
        int m = s2.length();
        if( n > m) {
            return false;
        }

        int i = n - 1;
        int j = m - 1;
        while( i >= 0 && j >= 0 ){
            if(s1.charAt(i) == s2.charAt(j) ){
                i--;
            }

            j--;
        }

        return i == -1;
    }
}
