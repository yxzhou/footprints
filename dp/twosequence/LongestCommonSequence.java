package dp.twosequence;


import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/77
 * 
 * Given two strings, find the longest common subsequence (LCS).
 * 
 * Your code should return the length of LCS.
 *
 * What's the definition of Longest Common Subsequence? The longest common subsequence problem is to find the longest
 * common subsequence in a set of sequences (usually 2). This problem is a typical computer science problem, which is
 * the basis of file difference comparison program, and also has applications in bioinformatics.
 * http://baike.baidu.com/view/2020307.htm 
 * 
 * Example:
 * Input: A = "ABCD" B = "EDCA"   Output: 1
 * Explanation: LCS is 'A' or 'D' or 'C' Example 2:
 *
 * Input: A = "ABCD" B = "EACB"   Output: 2
 * Explanation: LCS is "AC"
 * 
 * 
 * LCSeq(str1, str2), Longest Common Subsequence
 * 查找 LCS 是计算两个序列相似程度的一种方法： LCS 越长，两个序列越相似。
 * e.g:
 *  input:  str1 = {ggcaccacg}, str2 = {acggcggatacg}  
 *  output:  ggcaacg
 * 
 * DP:  
 *  We use the notation opt[i][j] = length of LCS of x[i..M] and y[j..N]
 *  opt[i][j] = 0                               if i == M or j == N
 *            = opt[i+1][j+1] + 1               if str1[i] == str2[j]
 *            = max(opt[i][j+1], opt[i+1][j])   otherwise
 *  
 *  We use the notation opt[i][j] = length of LCS of x[0..i-1] and y[0..j-1]
 * opt[i][j] = 0                              if i == 0 or j == 0
 *           = opt[i-1][j-1] + 1              if arr1[i] == arr2[j]
 *           = max(opt[i][j-1], opt[i-1][j])  otherwise
 * 
 * 
 * LCStr(str1, str2), longest Common Substring ( substring => consecutive subsequence, LCStr is a special case of LCSeq ) 
 * e.g:
 *  input:  str1 = {ggcaccacg}, str2 = {acggcggatacg}
 *  output:  ggc or acg
 * 
 * DP：
 *  We use the notation opt[i][j] = length of LCS of x[i..M] and y[j..N]
 *  opt[i][j] = 0                           if str1[i] != str2[j]
 *            = opt[i-1][j-1]+1             if str1[i] == str2[j] 
 *      
 *      
 * LCStr(str1), longest substring without repeating characters
 * e.g:
 *  input:  str1 = "abcabcbb"
 *  output:  abc 
 * 
 */

public class LongestCommonSequence{
    /**
     * @param A, B: Two strings.
     * @return The length of longest common subsequence of A and B.
     */
    public int longestCommonSubsequence(String A, String B) {
        if (null == A || 0 == A.length() || null == B || 0 == B.length()) {
            return 0;
        }

        int m = A.length();
        int n = B.length();

        int[][] dp = new int[m + 1][n + 1]; //default all are 0
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (A.charAt(i) == B.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
                //f[i + 1][j + 1] = Math.max( A.charAt(i) == B.charAt(j) ? f[i][j] + 1 : 0, Math.max(f[i + 1][j], f[i][j + 1]));
            }
        }

        return dp[m][n];
    }
  
    
  /*
   * fetch Longest Common Subsequence with DP on two arrays.
   * input arr1={a1, a2, ---, am},  arr2={b1, b2, ---, bn}  where m>=n 
   * 
   * bottom-up translation of this recurrence
    
   * We use the notation opt[i][j] = length of LCS of x[i..M] and y[j..N]
   * opt[i][j] = 0                              if i == M or j == N
   *           = opt[i+1][j+1] + 1              if arr1[i] == arr2[j]
   *           = max(opt[i][j+1], opt[i+1][j])  otherwise
   * 
   *        0  1  2  3  4  5  6  7  8  9 10 11 12
   * x\y    a  c  g  g  c  g  g  a  t  a  c  g  
   * --------------------------------------------
   * 0 g    7  7  7  6  6  6  5  4  3  3  2  1  0  
   * 1 g    6  6  6  6  5  5  5  4  3  3  2  1  0
   * 2 c    6  5  5  5  5  4  4  4  3  3  2  1  0  
   * 3 a    6  5  4  4  4  4  4  4  3  3  2  1  0  
   * 4 c    5  5  4  4  4  3  3  3  3  3  2  1  0  
   * 5 c    4  4  4  4  4  3  3  3  3  3  2  1  0  
   * 6 a    3  3  3  3  3  3  3  3  3  3  2  1  0  
   * 7 c    2  2  2  2  2  2  2  2  2  2  2  1  0  
   * 8 g    1  1  1  1  1  1  1  1  1  1  1  1  0  
   * 9      0  0  0  0  0  0  0  0  0  0  0  0  0
   *           
   * time O(m*n) and space O(m*n)
   * 
   * Detail refer to http://introcs.cs.princeton.edu/java/96optimization/
   * 
   * e.g.
   * input:  arr1 = {ggcaccacg}, arr2 = {acggcggatacg}
   * expect output:  ggcaacg
   * --ggc--a-ccacg
   * acggcggat--acg
   * 
   */
  public String longestCommonSubsequence_2(char[] arr1, char[] arr2){
      if (arr1 == null || arr2 == null) {
          return null;
      }

      int M = arr1.length;
      int N = arr2.length;

      // opt[i][j] = length of LCS of x[i..M] and y[j..N]
      int[][] f = new int[N + 1][M + 1];

      // compute length of LCS and all subproblems via dynamic programming
      for (int i = N - 1; i >= 0; i--) {
          for (int j = M - 1; j >= 0; j--) {
              if (arr1[j] == arr2[i]) {
                  f[i][j] = f[i + 1][j + 1] + 1;
              } else {
                  f[i][j] = Math.max(f[i + 1][j], f[i][j + 1]);
              }
          }
      }

      // recover LCS itself and print it to standard output
      StringBuilder sb = new StringBuilder();

      for (int i = 0, j = 0; i < N && j < M; ) {
          if (arr1[j] == arr2[i]) {
              sb.append(arr2[i]);
              i++;
              j++;
          } else if (f[i + 1][j] >= f[i][j + 1]) {
              i++;
          } else {
              j++;
          }
      }

      return sb.toString();
  }
  
  /*
   * fetch Longest Common Subsequence with DP on two arrays.
   * input arr1={a1, a2, ---, am},  arr2={b1, b2, ---, bn}  where m>=n 
   * 
   * We use the notation opt[i][j] = length of LCS of x[0..i-1] and y[0..j-1]
   * opt[i][j] = 0                              if i == 0 or j == 0
   *           = opt[i-1][j-1] + 1              if arr1[i] == arr2[j]
   *           = max(opt[i][j-1], opt[i-1][j])  otherwise
   * 
   *        0  1  2  3  4  5  6  7  8  9 10 11 12
   * X\Y       a  c  g  g  c  g  g  a  t  a  c  g  
   * --------------------------------------------
   * 0      0  0  0  0  0  0  0  0  0  0  0  0  0 
   * 1 g    0  0  0  1  1  1  1  1  1  1  1  1  1
   * 2 g    0  0  0  1  2  2  2  2  2  2  2  2  2
   * 3 c    0  0  1  1  2  3  3  3  3  3  3  3  3
   * 4 a    0  1  1  1  2  3  3  3  4  4  4  4  4
   * 5 c    0  1  2  2  2  3  3  3  4  4  4  5  5
   * 6 c    0  1  2  2  2  3  3  3  4  4  4  5  5
   * 7 a    0  1  2  2  2  3  3  3  4  4  5  5  5
   * 8 c    0  1  2  2  2  3  3  3  4  4  5  6  6
   * 9 g    0  1  2  3  3  3  4  4  4  4  5  6  7
   *           
   * time O(m*n) and space O(m*n)
   * 
   */
  public String longestCommonSubsequence(char[] arr1, char[] arr2){
      if (arr1 == null || arr2 == null) {
          return null;
      }

      int M = arr1.length;
      int N = arr2.length;

      // f[i][j] is the length of LCS of x[0..i] and y[0..j]
      int[][] f = new int[N + 1][M + 1]; 

      // compute length of LCS and all subproblems via dynamic programming
      for (int i = 0; i < N; i++) {
          for (int j = 0; j < M; j++) {
              if (arr1[j] == arr2[i]) {
                  f[i + 1][j + 1] = f[i][j] + 1;
              } else {
                  f[i + 1][j + 1] = Math.max(f[i + 1][j], f[i][j + 1]);
              }
          }
      }

      // recover LCS itself and print it to standard output
      StringBuilder sb = new StringBuilder();

      for (int i = N, j = M; i > 0 && j > 0; ) {
          if (arr1[j - 1] == arr2[i - 1]) {
              sb.append(arr1[j - 1]);
              i--;
              j--;
          } else if (f[i - 1][j] >= f[i][j - 1]) {
              i--;
          } else {
              j--;
          }
      }

      return sb.reverse().toString();
  }


  public static void main(String[] args) {
      
      String[][] inputs = {
          {"ABCD","EDCA", "A"}, // "A" or "C" or "D"
          {"ABCD","EACB", "AB"}, // "AC" or "AB"
          {"ggcaccacg","acggcggatacgc", "ggcaacg"},
          {"abcdefghijklxyztpr", "pacdfeoomnrdffrr", "acdefr"}
      };
   
      LongestCommonSequence sv = new LongestCommonSequence();

      for(int i = 0; i < inputs.length; i++){
          System.out.println(String.format("\n callLCSSeq %s and %s ", inputs[i][0], inputs[i][1]));
          
          Assert.assertEquals(inputs[i][2].length(), sv.longestCommonSubsequence(inputs[i][0], inputs[i][1]));
          
          //Assert.assertEquals(inputs[i][2], sv.longestCommonSubsequence(inputs[i][0].toCharArray(), inputs[i][1].toCharArray()));
          Assert.assertEquals(inputs[i][2], sv.longestCommonSubsequence_2(inputs[i][0].toCharArray(), inputs[i][1].toCharArray()));
      }
    
  }
  
}