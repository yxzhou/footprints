/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp.twosequence;

/**
 * _https://www.lintcode.com/problem/762
 * 
 * Given two sequence P and Q of numbers. The task is to find Longest Common Subsequence of two sequence if we are
 * allowed to change at most k element in first sequence to any value.
 * 
 * Example 1:
 * Input: [8,3] [1,3] 1
 * Output: 2
 * Explanation: chang 8 to 1,the longest common subsuquence is[1,3]
 * 
 * Example 2:
 * Input: [1, 2, 3, 4, 5] [5, 3, 1, 4, 2] 1
 * Output: 3
 * 
 */
public class LongestCommonSubsequenceII {
    
    /**
     * @param P: an integer array P
     * @param Q: an integer array Q
     * @param k: the number of allowed changes
     * @return return LCS with at most k changes allowed.
     */
    public int longestCommonSubsequence2(int[] P, int[] Q, int k) {
        if(P == null || Q == null){
            return 0;
        }
        
        int n = P.length;
        int m = Q.length;
        
        int[][][] f = new int[n + 1][m + 1][k + 1];
        
        //longest common subsequence without any change 
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(P[i] == Q[j]){
                    f[i + 1][j + 1][0] = f[i][j][0] + 1;
                }else{
                    f[i + 1][j + 1][0] = Math.max(f[i][j + 1][0], f[i + 1][j][0]);
                }
            }
        }
        
        //longest common subsequence with change
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                for(int c = 1; c <= k; c++){
                    if(P[i] != Q[j]){
                        f[i + 1][j + 1][c] = Math.max(f[i][j + 1][c], Math.max(f[i + 1][j][c], f[i][j][c - 1] + 1));
                    }else{
                        f[i + 1][j + 1][c] = Math.max(f[i][j + 1][c], Math.max(f[i + 1][j][c], f[i][j][c] + 1));
                    }
                }
            }
        }
        
        return f[n][m][k];
    }
}
