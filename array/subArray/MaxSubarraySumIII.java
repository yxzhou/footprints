/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.subArray;

import java.util.ArrayList;

/**
 *
 * Given an array of integers and a number k, find k non-overlapping subarrays which have the largest sum.
 * 
 * The number in each subarray should be contiguous.
 * 
 * Example 1:
 * Input: nums = [1,2,3] k = 1
 * Output: 6
 * Explanation:
 *   1 + 2 + 3 = 6
 * 
 * Example 2:
 * Input: nums = [-1,4,-2,3,-2,3] k = 2
 * Output: 8
 * Explanation:
 *   4 + (3 + -2 + 3) = 8
 * 
 */
public class MaxSubarraySumIII {
    /**
     *
     * refer to MaxSubarraySumII.maxTwoSubArrays_dp
     * 
     * Time O(K*n) space O(n)
     */
    public int maxSubArray(ArrayList<Integer> nums, int k) {
        if (null == nums || k < 1 || nums.size() < k) {
            return Integer.MIN_VALUE; //
        }

        int size = nums.size();
        int[][] f = new int[k + 1][size];//default all are 0, 
        //f[k][len - 1] is the sum of max k non-overlapping subarray that includes nums.get(len - 1) 

        //calculate f[i][i - 1], i is [1, k]
        f[1][0] = nums.get(0);
        //f[i][i - 1] = f[i - 1][i - 2] + nums.get(i)
        for (int i = 2, j = i - 1; i < k + 1; i++, j++) {
            f[i][j] = f[j][i - 2] + nums.get(j);
        }

        //calculate f[1][i], i is [1, len)
        //f[1][i] = max(f[1][i - 1], 0) + nums.get(i)
        for (int i = 1; i < size; i++) {
            f[1][i] = Math.max(f[1][i - 1], 0) + nums.get(i);
        }

        //calculate f[i][n], i is [2, k], n is [i-2, len)
        //f[i][n] = max(f[i][n-1], max(f[i - 1][i - 2], f[i - 1][i - 3], ---, f[i - 1][n - 1])) + nums.get(n)
        int max = Integer.MIN_VALUE;
        for (int i = 2; i < k + 1; i++) {
            max = f[i - 1][i - 2];
            for (int n = i; n < size; n++) {
                max = Math.max(max, f[i - 1][n - 1]);
                f[i][n] = Math.max(max, f[i][n - 1]) + nums.get(n);
            }
        }

        max = Integer.MIN_VALUE;
        for (int i = k - 1; i < size; i++) {
            max = Math.max(f[k][i], max);
        }
        return max;
    }
}
