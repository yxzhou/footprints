package dp.twosequence;

import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/119
 * 
 * Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. 
 * (each operation is counted as 1 step.)
 * 
 * You have the following 3 operations permitted on a word:
 * a) Insert a character
 * b) Delete a character
 * c) Replace a character
 * 
 * Notes:
 *   the length of word1 and word2 is no bigger than 500
 * 
 * time O(m*n) and space O(m*n)
 *
 */
public class EditDistance {

    /**
     * Q2, find the minimum edit distance
     */
    public int minDistance_recur(String word1, String word2) {
        if (word1 == null && word2 == null) {
            return 0;
        } else if (word1 == null || word1.length() == 0) {
            return word2.length();
        } else if (word2 == null || word2.length() == 0) {
            return word1.length();
        }

        int len1 = word1.length();
        int len2 = word2.length();

        /* define a int[][] to cache the result */
        int[][] cache = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                cache[i][j] = -1;
            }
        }

        return minDistance(word1, 0, len1 - 1, word2, 0, len2 - 1, cache);
    }

    private int minDistance(String s1, int start1, int end1, String s2, int start2, int end2, int[][] cache) {

        /*if it was cached, just return */
        if (cache[start1][start2] != -1) {
            return cache[start1][start2];
        }

        /* no cache, have to calculate  */
        int result = -1;
        if (start1 > end1 && start2 > end2)  //
            result = 0;
        else if (start1 > end1)  //
            result = end2 - start2 + 1;
        else if (start2 > end2)  //
            result = end1 - start1 + 1;
        else {
            /*  */
            if (s1.charAt(start1) == s2.charAt(start2)) {
                result = minDistance(s1, start1 + 1, end1, s2, start2 + 1, end2, cache);
            } else {
                int r1 = minDistance(s1, start1, end1, s2, start2 + 1, end2, cache); // remove s2.charAt(start2), or add s2.charAt(start2) before s1.charAt(start1)
                int r2 = minDistance(s1, start1 + 1, end1, s2, start2, end2, cache); // remove s2.charAt(start2), or add s1.charAt(start1) before s2.charAt(start2)
                int r3 = minDistance(s1, start1 + 1, end1, s2, start2 + 1, end2, cache); // update, make s2.charAt(start2) and s1.charAt(start1) same

                result = Math.min(r1, Math.min(r2, r3)) + 1;
            }
        }

        cache[start1][start2] = result;
        return result;

    }
  
  
  
  /*
   * calculate the min steps change, ( same as fgafa.uva.dp.StringComputerN164 )
   * input arr1={a1, a2, ---, am},  arr2={b1, b2, ---, bn}  
   * 
   * We use the notation dp[i][j] = the min steps change of x[0..i] and y[0..j]
   * dp[i][j] = j                              if i == 0
   *          = i                              if j == 0
   *          = min(dp[i][j-1] + 1,   (Insert)            
   *                dp[i-1][j] + 1,   (Delete)
   *                dp[i-1][j-1]) + s (Change) if arr1[i] == arr2[j], s=0;  else s=1
   *               )    
   *           
   * time O(m*n) and space O(m*n)
   * 
   */
    public int minDistance_DP(String word1, String word2) {
        if(word1 == null || word2 == null){
            return 0;
        }

        int n = word1.length();
        int m = word2.length();

        int[][] dp = new int[n + 1][m + 1];
        for(int i = 0; i <= n; i++){
            dp[n - i][m] = i;
        }
        for(int i = 1; i <= m; i++){
            dp[n][m - i] = i;
        }

        for(int r = n - 1; r >= 0; r--){
            for(int c = m - 1; c >= 0; c--){
                dp[r][c] = Math.min(dp[r + 1][c], dp[r][c + 1]) + 1;
                dp[r][c] = Math.min(dp[r][c], dp[r + 1][c + 1] + ( word1.charAt(r) == word2.charAt(c) ? 0 : 1) );
            }
        }

        return dp[0][0];
    }

    public int minDistance_DP_n(String word1, String word2) {
        if(word1 == null || word2 == null){
            return 0;
        }

        int n = word1.length();
        int m = word2.length();

        int[] pre = new int[m + 1];
        int[] cur = new int[m + 1];
        for (int i = 1; i <= m; i++) {
            pre[m - i] = i;
        }

        int[] tmp;
        for (int r = n - 1; r >= 0; r--) {
            //cur[m] = n - r;
            cur[m] = pre[m] + 1;

            for (int c = m - 1; c >= 0; c--) {
                cur[c] = Math.min(pre[c], cur[c + 1]) + 1;
                
                cur[c] = Math.min(cur[c], pre[c + 1] + ( word1.charAt(r) == word2.charAt(c) ? 0 : 1) ); 
            }

            tmp = pre;
            pre = cur;
            cur = tmp;
        }

        return pre[0];
    }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
      String[][] inputs = {
          {
              "horse",
              "ros",
              "3"
          },
          {
              "ros",
              "horse",
              "3"
          },
          {
              "aa",
              "aaa",
              "1"
          },
          {
              "intention",
              "execution",
              "5"
          },
          {
              "pneumonoultramicroscopicsilicovolcanoconiosis",
              "ultramicroscopically",
              "27"
          }
          
      };
      
      EditDistance sv = new EditDistance();

      for (int i = 0; i < inputs.length; i++) {
          System.out.println(String.format("\nInput s1: %s, s2: %s", inputs[i][0], inputs[i][1]));
          
          Assert.assertEquals("minDistance_recur ",  Integer.parseInt(inputs[i][2]), sv.minDistance_recur(inputs[i][0], inputs[i][1]) );
          
          Assert.assertEquals("minDistance_DP ",  Integer.parseInt(inputs[i][2]), sv.minDistance_DP(inputs[i][0], inputs[i][1]) );
          
          Assert.assertEquals("minDistance_DP_n ",  Integer.parseInt(inputs[i][2]), sv.minDistance_DP_n(inputs[i][0], inputs[i][1]) );

      }

  }

}
