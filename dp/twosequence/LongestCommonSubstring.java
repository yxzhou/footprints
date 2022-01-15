/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp.twosequence;

import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/79
 * 
 * Given two strings, find the longest common substring.
 *
 * The characters in substring should occur continuously in original string. This is different with subsequence.
 *
 * Example 1:
 * Input: stringA = "ABCD" stringB = "CBCE" Output: 2
 * Explanation: Longest common substring is "BC"
 *
 * Example 2:
 * Input: stringA = "ABCD" stringB = "EACB" Output: 1
 * Explanation: Longest common substring is 'A' or 'C' or 'B'
 * 
 */
public class LongestCommonSubstring {
  /*
   * fetch Longest Common consecutive Substring with DP on two arrays.
   * input arr1={a1, a2, ---, am},  arr2={b1, b2, ---, bn}  where m>=n 
   * 
   * We use the notation opt[i][j] = length of LCS of x[0..i-1] and y[0..j-1]
   * opt[i][j] = 0                              if i == 0 or j == 0
   *           = opt[i-1][j-1] + 1              if arr1[i] == arr2[j]
   *           = 0                              otherwise
   * 
   *        0  1  2  3  4  5  6  7  8  9 10 11 12
   * X\Y       a  c  g  g  c  g  g  a  t  a  c  g  
   * --------------------------------------------
   * 0      0  0  0  0  0  0  0  0  0  0  0  0  0 
   * 1 g    0  0  0  1  1  0  1  1  0  0  0  0  1
   * 2 g    0  0  0  1  2  0  1  2  0  0  0  0  1
   * 3 c    0  0  1  0  0  3  0  0  0  0  0  1  0
   * 4 a    0  1  0  0  0  0  0  0  1  0  1  0  0
   * 5 c    0  0  2  0  0  1  0  0  0  0  0  2  0
   * 6 c    0  0  1  0  0  1  0  0  0  0  0  1  0
   * 7 a    0  1  0  0  0  0  0  0  1  0  1  0  0
   * 8 c    0  0  2  0  0  1  0  0  0  0  0  2  0
   * 9 g    0  0  0  3  1  0  2  1  0  0  0  0  3
   *           
   * time O(m*n) and space O(m*n)
   * 
   * e.g.
   * input:  arr1 = {ggcaccacg}, arr2 = {acggcggatacg}
   * expect output:  ggc or acg
   *  
   * calLCStr_DP,  store the above opt[i][j] with int[], the matrix is created line by line, so we can just store the temporary result with int[]
   * calLCStr_DP2, store the above opt[i][j] with int[][] 
   *  
   */
    
    /**
     * @param A: A string
     * @param B: A string
     * @return the length of the longest common substring.
     */
    public int longestCommonSubstring(String A, String B) {
        if(A == null || B == null){
            return 0;
        }

        int max = 0;
        int n = A.length();
        int m = B.length();

        int[][] f = new int[n + 1][m + 1];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                f[i + 1][j + 1] = A.charAt(i) == B.charAt(j)? f[i][j] + 1 : 0 ;

                max = Math.max(max, f[i + 1][j + 1]);
            }
        }
        
        return max;
    }
    
    
    
    public String calLCStr_DP(char[] arr1, char[] arr2) {
        if (arr1 == null || arr2 == null) {
            return null;
        }

        int M = arr1.length;
        int N = arr2.length;

        // opt[i][j] = length of LCS of x[0..i] and y[0..j]
        int[] opt = new int[M + 1];

        // compute length of LCS and all subproblems via dynamic programming
        int[] max = new int[M];  // there are LCStr with same length, 
        int[] maxIndex = new int[M];
        for (int i = 0; i < N; i++) {
            for (int j = M - 1; j >= 0; j--) {
                if (arr1[j] == arr2[i]) {
                    opt[j + 1] = opt[j] + 1;

                    recordTheMax(max, maxIndex, opt, j);
                } else {
                    opt[j + 1] = 0;
                }
            }
            //System.out.println(Misc.array2String(opt));
        }

        // recover LCS itself and print it to standard output
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < M; k++) {
            if (max[k] > 0) {
                sb.append(getSubString(arr1, maxIndex[k] - max[0], maxIndex[k]));
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    private void recordTheMax(int[] max, int[] maxIndex, int[] opt, int j) {
        int M = max.length;

        if (opt[j + 1] > max[0]) {
            //add the new max, replace the first old one
            max[0] = opt[j + 1];
            maxIndex[0] = j + 1;

            //remove the other old 
            for (int k = 1; k < M; k++) {
                max[k] = 0;
                maxIndex[k] = 0;
            }
        } else if (opt[j + 1] == max[0]) {
            //add the new max at the end, no replacement, because the length is as same as existed
            for (int k = 0; k < M; k++) {
                if (max[k] == 0) {
                    max[k] = max[0];
                    maxIndex[k] = j + 1;
                    break;   //exit the loop when add it in
                }
            }
        }
    }

    private String getSubString(char[] arr, int start, int end) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = start; i < end; i++) {
            sb.append(arr[i]);
            sb.append(", ");
        }
        sb.append("]");

        return sb.toString();
    }

    public String calLCStr_DP2(char[] arr1, char[] arr2) {
        if (arr1 == null || arr2 == null) {
            return null;
        }

        int M = arr1.length;
        int N = arr2.length;

        // opt[i][j] = length of LCS of x[0..i] and y[0..j]
        int[][] opt = new int[N + 1][M + 1];

        // compute length of LCS and all subproblems via dynamic programming
        int max = 0, iMax = 0, jMax = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr1[j] == arr2[i]) {
                    opt[i + 1][j + 1] = opt[i][j] + 1;
                    if (opt[i + 1][j + 1] > max) {
                        iMax = i + 1;
                        jMax = j + 1;
                        max = opt[iMax][jMax];
                    }
                } else {
                    opt[i + 1][j + 1] = 0;
                }
            }
        }

        // recover LCS itself and print it to standard output
        return getSubString(arr1, iMax - 1, jMax);
    }



    public static void main(String[] args) {
        String[][] inputs = {
            {"ggcaccacg", "acggcggatacgc", "ggc"},
            {"abcdefghijklxyztpr", "pacdfeoomnrdffrr", "acdefr"}
        };

        LongestCommonSubstring sv = new LongestCommonSubstring();

        for (String[] input : inputs) {
            System.out.println(String.format("\n callLCSSeq %s and %s ", input[0], input[1]));
            Assert.assertEquals(input[2], sv.calLCStr_DP2(input[0].toCharArray(), input[1].toCharArray()));
        }

    }
}
