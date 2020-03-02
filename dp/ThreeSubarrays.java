package fgafa.dp;

/**
 * Leetcode #689
 *
 * In a given array nums of positive integers, find three non-overlapping subarrays with maximum sum.
 *
 * Each subarray will be of size k, and we want to maximize the sum of all 3*k entries.
 *
 * Return the result as a list of indices representing the starting position of each interval (0-indexed). If there are multiple answers, return the lexicographically smallest one.
 *
 * Example:
 *
 * Input: [1,2,1,2,6,7,5,1], 2
 * Output: [0, 3, 5]
 * Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5].
 * We could have also taken [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.
 *
 *
 * Note:
 *
 * nums.length will be between 1 and 20000.
 * nums[i] will be between 1 and 65535.
 * k will be between 1 and floor(nums.length / 3).
 *
 */

public class ThreeSubarrays {


    /**
     *
     * define s[k] as the sum of subarray from array [0, k-1], both included.
     * define f[i][3] as the max sum of 3 non-overlapping subarrays from array[0, i - 1],
     *
     * so:
     * f[i][0] = 0
     * f[i][1] = Math.max(f[i-1][1], f[i-k][0] + s[i])
     * f[i][2] = Math.max(f[i-1][2], f[i-k][1] + s[i])
     * f[i][3] = Math.max(f[i-1][3], f[i-k][2] + s[i])
     *
     * the max sum is f[len][3]
     *
     *
     */

    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        int[] s = new int[n + 1];

        for(int i = 0; i < k; i++){
            s[k] += nums[i];
        }

        for(int l = 0, r = k; r < n; r++, l++){
            s[r + 1] = s[r] + nums[r] - nums[l];
        }

        int[][] f = new int[n + 1][4];

        for(int w = 1; w <= 3; w++ ){
            for(int r = w * k, l = r - k; r <= n; r++, l++){
                f[r][w] = Math.max(f[r-1][w], f[l][w - 1] + s[r]);
            }
        }

        int p = n;
        int w = 3;
        int[] result = new int[3];
        for(int i = 2; i >= 0; i--){
            while(p > 0 && f[p][w] == f[p - 1][w]){
                p--;
            }

            p -= k;
            result[i] = p;
            w--;
        }

        return result;
    }


}
