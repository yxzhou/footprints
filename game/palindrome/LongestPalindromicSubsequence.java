package game.palindrome;

import org.junit.Assert;
import org.junit.Test;

/**
 * Leetcode #516
 *
 * Given a string s, find the longest palindromic subsequence's length in s. You may assume that the maximum length of s is 1000.
 *
 * Example 1:
 * Input:  "bbbab"
 * Output:  4
 * One possible longest palindromic subsequence is "bbbb".
 *
 *
 * Example 2:
 * Input: "cbbd"
 * Output: 2
 * One possible longest palindromic subsequence is "bb".
 *
 */

public class LongestPalindromicSubsequence {

    /**
     *
     * Define dp[i][j]: the longest palindromic subsequence's length of substring(i, j), here i, j represent left, right indexes in the string
     * State transition:
     * dp[i][j] = dp[i+1][j-1] + 2 if s.charAt(i) == s.charAt(j)
     * otherwise, dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1])
     * Initialization: dp[i][i] = 1
     *
     */

    public int longestPalindromeSubseq(String s) {
        if(s == null || s.isEmpty()){
            return 0;
        }

        int n = s.length();
        int[][] f =  new int[n][n];

        for(int l = n - 1; l >= 0; l-- ){

            f[l][l] = 1;
            for(int r = l + 1; r < n; r++){
                if(s.charAt(l) == s.charAt(r)){
                    f[l][r] = f[l + 1][r - 1] + 2;
                }else{
                    f[l][r] = Math.max(f[l + 1][r], f[l][r - 1]);
                }
            }

        }

        return f[0][n - 1];
    }

    public int longestPalindromeSubseq_2(String s) {
        if(s == null || s.isEmpty()){
            return 0;
        }

        int n = s.length();
        int[][] f =  new int[n][n];

        for(int r = 0; r < n; r++ ){

            f[r][r] = 1;
            for(int l = r - 1; l >= 0; l--){
                if(s.charAt(l) == s.charAt(r)){
                    f[l][r] = f[l + 1][r - 1] + 2;
                }else{
                    f[l][r] = Math.max(f[l + 1][r], f[l][r - 1]);
                }
            }

        }

        return f[0][n - 1];
    }


    @Test public void test(){
        Assert.assertEquals(4, longestPalindromeSubseq_2("bbbab"));

        Assert.assertEquals(2, longestPalindromeSubseq_2("cbbd"));
    }

}
