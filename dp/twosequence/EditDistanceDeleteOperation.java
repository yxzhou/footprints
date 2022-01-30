/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dp.twosequence;

import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/1156
 * 
 * Given two words word1 and word2, find the minimum number of steps required to make word1 and word2 the same, where in
 * each step you can delete one character in either string.
 *
 * The length of given words won't exceed 500. Characters in given words can only be lower-case letters. 
 * 
 * Example 1:
 * Input: "sea", "eat" Output: 2 
 * Explanation: You need one step to make "sea" to "ea" and another step to make "eat" to "ea". 
 * 
 * Example 2:
 * Input: "horse", "ros" Output: 4 
 * Explanation: You need three steps to make "horse" to "os" and another step to make "ros" to "os".
 * 
 */
public class EditDistanceDeleteOperation {
    /**
     * @param word1: a string
     * @param word2: a string
     * @return the minimum number of steps required to make word1 and word2 the same
     */
    public int minDistance(String word1, String word2) {
        if(word1 == null && word2 == null){
            return 0;
        }
        if(word1 == null){
            return word2.length();
        }
        if(word2 == null){
            return word1.length();
        }

        int n = word1.length();
        int m = word2.length();

        int[][] dp = new int[n + 1][m + 1]; // the length of longest common subsequence
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(word1.charAt(i) == word2.charAt(j)){
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                }else{
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }

        return n + m - dp[n][m] * 2;
    }
    
    public int minDistance_2(String word1, String word2) {
        if(word1 == null && word2 == null){
            return 0;
        }
        if(word1 == null){
            return word2.length();
        }
        if(word2 == null){
            return word1.length();
        }

        int n = word1.length();
        int m = word2.length();

        int[][] dp = new int[n + 1][m + 1]; // the minimum number of steps required to make word1 and word2 the same
        
        for(int i = 1; i <= n; i++){
            dp[i][0] = i;
        }
        for(int j = 1; j <= m; j++){
            dp[0][j] = j;
        }
        
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(word1.charAt(i) == word2.charAt(j)){
                    dp[i + 1][j + 1] = dp[i][j];
                }else{
                    dp[i + 1][j + 1] = Math.min(dp[i][j + 1], dp[i + 1][j]) + 1;
                }
            }
        }

        return dp[n][m];
    }
    
    public static void main(String[] args){
        String[][] inputs = {
            {"sea", "eat", "2"},
            {"horse", "ros", "4"}
        };
        
        EditDistanceDeleteOperation sv = new EditDistanceDeleteOperation();
        
        for(String[] input : inputs){
            System.out.println(String.format("\nword1=%s, word2=%s, expect:%s", input[0], input[1], input[2] ));
            
            Assert.assertEquals(Integer.parseInt(input[2]), sv.minDistance(input[0], input[1]));
            Assert.assertEquals(Integer.parseInt(input[2]), sv.minDistance_2(input[0], input[1]));
        }
    }
}
