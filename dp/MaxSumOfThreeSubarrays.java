/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp;

import org.junit.Assert;

/**
 *
 * In a given array nums of positive integers, find three non-overlapping subarrays with maximum sum.
 *
 * Each subarray will be of size k, and we want to maximize the sum of all 3*k entries.
 *
 * Return the result as a list of indices representing the starting position of each interval (0-indexed). If there are
 * multiple answers, return the lexicographially smallest one.
 * 
 * Notes:
 * nums.length will be between 1 and 20000. 
 * nums[i] will be between 1 and 65535. 
 * k will be between 1 and floor(nums.length / 3).

 * Example 1
 * Input:  [1,2,1,2,6,7,5,1]  2
 * Output: [0, 3, 5]
 * Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5]. We could have also taken 
 * [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.
 * 
 * Example 2
 * Input: [1,2,3]  1
 * Output: [0,1,2]
 * 
 * Thoughts:
 * 1) maxSumOfThreeSubarrays[n - 1] = Math.max( maxSumOfThreeSubarrays[n - 2], maxSumOfTwoSubarrays[n - k], subarraySum(n - k, n - 1]) )
 * 
 * DP
 *    define sums[i] is the sum of subarray in nums from (n - 1 - k , n - 1]
 *    define m[3][n - 1] is the maxSumOfThreeSubarrays(nums, k), 
 *       m[3][i] = max( m[3][i - 1], m[2][i - k] + sums[i] )   i >= 3*k - 1
 *       m[2][i] = max( m[2][i - 1], m[1][i - k] + sums[i] )   i >= 2*k - 1
 *       m[1][i] = max( m[1][i - 1], m[0][i - k] + sums[i] )   i >= k - 1
 *         attention to m[1][i - 1] when k == 0,
 *         attention to m[0][i - k] when k == k - 1
 *    
 * 
 */
public class MaxSumOfThreeSubarrays {
    /**
     * 
     * 
     * @param nums: an array
     * @param k: an integer
     * @return: three non-overlapping subarrays with maximum sum
     */
    public int[] maxSumOfThreeSubarrays_DP(int[] nums, int k) {
        int n = nums.length;

        int[] s = new int[n]; // s[i] is the sum of subarrays (i - k, i] 
        for(int i = 0, m = k - 1; i <= m; i++){
            s[m] += nums[i];
        }
        for(int i = 0, j = k; j < n; i++, j++){
            s[j] = s[j - 1] + nums[j] - nums[i];
        }

        int[][] m = new int[4][n + 1]; //m[w][i] is maxSumOfWSubarrays(nums, i - 1), subarray's size is k 
        int[][] p = new int[4][n + 1]; //p[w][i] is right-most index of last subarray when 

        for(int x = 1; x < 4; x++){
            for(int i = x*k ; i <= n; i++){
                m[x][i] = Math.max( m[x][i - 1], m[x - 1][i - k] + s[i - 1] );

                p[x][i] = i;
                if(m[x][i] == m[x][i - 1]){
                    p[x][i] = p[x][i - 1];
                }
            }
        }

        int[] result = new int[3];
        for(int w = 3, last = n; w > 0; w--){
            last = p[w][last] - k;
            result[w - 1] = last;
        }
        return result;
    }
    
    public static void main(String[] main){
        
        MaxSumOfThreeSubarrays sv = new MaxSumOfThreeSubarrays();
        
        int[][][] inputs = {
            {{1,2,1,2,6,7,5,1}, {2}, {0,3,5}},
            {{1,2,3},{1},{0,1,2}}
        };
        
        for (int[][] input : inputs) {
            Assert.assertArrayEquals(input[2], sv.maxSumOfThreeSubarrays_DP(input[0], input[1][0]));
        }
    }
}
