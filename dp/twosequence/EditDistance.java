package dp.twosequence;

/**
 * 

 * 
 * Q2: Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. 
 * (each operation is counted as 1 step.)
 * 
 * You have the following 3 operations permitted on a word:
 * 
 * a) Insert a character
 * b) Delete a character
 * c) Replace a character
 * 
 *     int minDistance(String s, String t)
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
        if (word1 == null || word1.length() == 0) {
            return word2 == null ? 0 : word2.length();
        }
        if (word2 == null || word2.length() == 0) {
            return word1.length();
        }

        final int m = word1.length();
        final int n = word2.length();
        char[] s = word1.toCharArray(); //source
        char[] t = word2.toCharArray(); //target

        int[][] f = new int[m][n]; //default all are 0

        // i = 0, j from 0 to n
        boolean found = false;
        for (int j = 0; j < n; j++) {
            if (!found && s[0] == t[j]) {
                f[0][j] = (j == 0 ? 0 : f[0][j - 1]);
                found = true;
            } else {
                f[0][j] = (j == 0 ? 0 : f[0][j - 1]) + 1;
            }
        }
        // i from 0 to m, n = 0
        found = false;
        for (int i = 0; i < m; i++) {
            if (!found && s[i] == t[0]) {
                f[i][0] = (i == 0 ? 0 : f[i - 1][0]);
                found = true;
            } else {
                f[i][0] = (i == 0 ? 0 : f[i - 1][0]) + 1;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                f[i][j] = Math.min(f[i][j - 1], f[i - 1][j]) + 1;
                f[i][j] = Math.min(f[i][j], f[i - 1][j - 1] + (s[i] == t[j] ? 0 : 1));
            }
        }

        return f[m - 1][n - 1];
    }

    public int minDistance_DP_1(String word1, String word2) {
        if(word1 == null || word1.length() == 0){
            return word2 == null? 0 : word2.length();
        }
        if(word2 == null || word2.length() == 0){
            return word1.length();
        }

        final int M = word1.length();
        final int N = word2.length();

        int[][] dp = new int[M + 1][N + 1];
        for(int m = 1; m <= M; m++ ){
            dp[m][0] = m;
        }
        for(int n = 1; n <= N; n++ ){
            dp[0][n] = n;
        }

        char[] cs1 = word1.toCharArray();
        char[] cs2 = word2.toCharArray();
        for (int m = 0, m1 = 1; m < M; m++, m1++) {
            for (int n = 0, n1 = 1; n < N; n++, n1++) {
                dp[m1][n1] = dp[m][n] + (cs1[m] == cs2[n] ? 0 : 1);
                dp[m1][n1] = Math.min(Math.min(dp[m][n1], dp[m1][n]) + 1, dp[m1][n1]);
            }
        }

        return dp[M][N];
    }

    public int minDistance_DP_n(String word1, String word2) {
        if (word1 == null && word2 == null) {
            return 0;
        }
        if (word1 == null || word2 == null) {
            return word1 == null ? word2.length() : word1.length();
        }

        final int M = word1.length();
        final int N = word2.length();

        int[] pre = new int[N + 1];
        int[] curr = new int[N + 1];
        for (int n = 1; n <= N; n++) {
            pre[n] = n;
        }

        char[] cs1 = word1.toCharArray();
        char[] cs2 = word2.toCharArray();
        int[] tmp;
        for (int m = 0, m1 = 1; m < M; m++, m1++) {
            curr[0] = m1;

            for (int n = 0, n1 = 1; n < N; n++, n1++) {
                curr[n1] = pre[n] + (cs1[m] == cs2[n] ? 0 : 1); // pre[n] + cs1[m] == cs2[n] ? 0 : 1 is wrong
                curr[n1] = Math.min(Math.min(pre[n1], curr[n]) + 1, curr[n1]);
            }

            tmp = pre;
            pre = curr;
            curr = tmp;
        }

        return pre[N];
    }
  


  
  /**
   * @param args
   */
  public static void main(String[] args) {
      EditDistance sv = new EditDistance();

      String[] s1 = {null, "", "horse", "ros", "intention"};
      String[] s2 = {null, "", "ros", "horse", "execution"};

      for (int i = 2; i < s1.length; i++) {
          System.out.println("\nInput s1:" + s1[i] + ", s2:" + s2[i]);
          System.out.println("Output  :" + sv.minDistance_recur(s1[i], s2[i]));
          System.out.println("Output  :" + sv.minDistance_DP(s1[i], s2[i]));
          System.out.println("Output  :" + sv.minDistance_DP_1(s1[i], s2[i]));
          System.out.println("Output  :" + sv.minDistance_DP_n(s1[i], s2[i]));

      }

  }

}
