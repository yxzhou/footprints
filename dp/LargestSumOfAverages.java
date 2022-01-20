
package dp;

/**
 * _https://www.lintcode.com/problem/1004
 * 
 * We partition a row of numbers A into at most K adjacent (non-empty) groups, then our score is the sum of the average
 * of each group. What is the largest score we can achieve?
 *
 * Note that our partition must use every number in A, and that scores are not necessarily integers.
 *   1 <= A.length <= 100.
 *   1 <= A[i] <= 10000.
 *   1 <= K <= A.length.
 *   Answers within 10^-6 of the correct answer will be accepted as correct.
 * 
 * Example 1
 * Input:  [9,1,2,3,9] 3
 * Output: 20
 * Explanation: 
 * The best choice is to partition A into [9], [1, 2, 3], [9]. The answer is 9 + (1 + 2 + 3) / 3 + 9 = 20.
 * We could have also partitioned A into [9, 1], [2], [3, 9], That score of 5 + 2 + 6 = 13, which is worse.
 * 
 * Example 2
 * Input: [9,3]  2
 * Output: 12
 * 
 * Thougths, 
 *   define f(k, n - 1) as the largest sum of averages, which partitioned arr(0...n-1) into k group
 *   define sum(i) as the prefix sum, arr[0] + arr[1] + ... + arr[i]
 * 
 *   f(1, i) = sum(i) / (i + 1)
 *   f(2, i) = max( f(1, i - 1) + arr[i], f(1, i - 2) + (sum[i] - sum[i-2] ) / 2, ..., f(1, 0) + (sum[i] - sum[0]) / i  )
 * 
 *   so: 
 *     to get f(K, n), define it as f[K - 1][n - 1]
 *     init value:        f[0][0] = arr[0], f[0][1] = sum[1] / 2, f[0][2] = sum[2]/3 ...
 *     transformations:   when i >= k,  
 *       f[k][i] = max( f[k-1][k-1] + (sum[i] - sum[k-1]) / (i - k + 1),
 *                      f[k-1][k] +   (sum[i] - sum[k]) / (i - k),
 *                      ---
 *                      f[k-1][i-1] + (sum[i] - sum[i-1]) / 1 ),
 *                    )          
 * 
 */
public class LargestSumOfAverages {
    public double largestSumOfAverages(int[] A, int K) {
        int n = A.length;
        double[][] dp = new double[K + 1][n + 1];
        double[] sum = new double[n + 1];
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + A[i - 1];
            dp[1][i] = sum[i] / i;
        }
        for (int i = 2; i <= K; i++) {
            for (int j = i; j <= n; j++) {
                for (int k = i - 1; k < j; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][k] + (sum[j] - sum[k]) / (j - k));
                }
            }
        }
        return dp[K][n];
    }
    
    /**
     * @param A: an array
     * @param K: an integer
     * @return the largest score
     */
    public double largestSumOfAverages_n(int[] A, int K) {
        int n = A.length;
        
        double[][] f = new double[K][n];
        int[] sums = new int[n]; //prefix sum
        sums[0] = A[0];
        f[0][0] = A[0];
        for (int i = 1, j = 0; i < n; j = i, i++) {
            sums[i] = sums[j] + A[i];
            f[0][i] = (double) sums[i] / (i + 1);
        }

        for (int k = 1; k < K; k++) {
            for (int i = k; i < n; i++) {
                for (int j = k - 1; j < i; j++) {
                    f[k][i] = Math.max(f[k][i], f[k - 1][j] + (double) (sums[i] - sums[j]) / (i - j));
                }
            }
        }

        return f[K - 1][n - 1];
    }
    
    
    
}
