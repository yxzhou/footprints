/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp.twosequence;

import junit.framework.Assert;

/**
 * __https://www.lintcode.com/problem/581
 * 
 *
 * Given a string, find length of the longest repeating subsequence such that the two subsequence don’t have same string
 * character at same position, i.e., any ith character in the two subsequences should not have the same index in the
 * original string. 
 * 
 * Example 1: 
 * Input:"aab"  
 * Output:1 
 * Explanation: The two subsequence are a(first) and a(second).
 * Note that b cannot be considered as part of subsequence as it would be at same index in both. 
 * 
 * Example 2: 
 * Input:"abc"
 * Output:0 
 * Explanation: There is no repeating subsequence
 * 
 * Thoughts:
 * 
 */
public class LongestRepeatingSubsequence {

    /**
     * refer to LongestCommonSequence
     * 
     * @param s
     * @return 
     */
    public int longestRepeatingSubsequence_Wrong(String s) {
        if (null == s || 0 == s.length() ) {
            return 0;
        }

        int n = s.length();
        
        int[][] f = new int[n + 1][n + 1]; //default all are 0
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i != j && s.charAt(i - 1) == s.charAt(j - 1)) {
                    f[i][j] = f[i - 1][j - 1] + 1;
                } else {
                    f[i][j] = Math.max(f[i][j - 1], f[i - 1][j]);
                }
            }
        }

        return f[n][n];
    }
    
    /**
     * 
     * 
     * @param s
     * @return 
     */
    public int longestRepeatingSubsequence(String s) {
        if (null == s || 0 == s.length() ) {
            return 0;
        }

        int n = s.length();
    
        return dfs(s.toCharArray(), 0, 1, new boolean[n], new int[n][n]);
    }
    
    private int dfs(char[] arr, int i, int j, boolean[] visited, int[][] cache){
        int n = arr.length;
        if(i >= n || j >= n){
            return 0;
        }
        
        if(cache[i][j] > 0){
            return cache[i][j];
        }
        
        for(int l = i; l < n; l++){
            if(visited[l]){
                continue;
            }
            for(int r = Math.max(j, l + 1); r < n; r++){
                if(visited[r]){
                    continue;
                }
                
                if(arr[l] == arr[r]){
                    visited[l] = visited[r] = true; 
                    cache[i][j] = Math.max(cache[i][j], dfs(arr, l + 1, r + 1, visited, cache) + 1);
                    visited[l] = visited[r] = false; 
                }
            }
        }
        
        return cache[i][j];
    }
    

    
    public static void main(String[] args) {

        String[][] inputs = {            
            {"aab", "a"},
            {"abc", ""},
            {"bbb", "b"}, 
            {"abab","ab"},
            {"abccbabcd", "abc"},
            {"abcabcbb", "abcb"}, 
            {"abccbabb", "abb"}, 
            {"aabebcdd", "abd"}
        };

        LongestRepeatingSubsequence sv = new LongestRepeatingSubsequence();

        for (String[] input : inputs) {
            System.out.println(String.format("\n Input %s, \tOutput %s", input[0], input[1]));
            
            
            //Assert.assertEquals(input[1].length(), sv.longestRepeatingSubsequence_Wrong(input[0]));
            Assert.assertEquals(input[1].length(), sv.longestRepeatingSubsequence(input[0]));
        }
    }
}
