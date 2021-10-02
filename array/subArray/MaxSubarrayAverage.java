/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.subArray;

/**
 *
 * Given an array consisting of n integers, find the contiguous subarray of given length k that has the maximum average value. You need to output the maximum average value.
 * 
 * Notes:
 * 1 <= k <= n <= 30,000.
 * Elements of the given array will be in the range [-10,000, 10,000].
 * 
 * Example1
 * Input:  nums = [1,12,-5,-6,50,3] and k = 4
 * Output: 12.75
 * Explanation: Maximum average is (12-5-6+50)/4 = 51/4 = 12.75
 * 
 * Example2
 * Input:  nums = [4,2,1,3,3] and k = 2
 * Output: 3.00
 * Explanation: Maximum average is (3+3)/2 = 6/2 = 3.00
 * 
 * 
 */
public class MaxSubarrayAverage {
    public double findMaxAverage(int[] nums, int k) {
        int sum = 0; // sum of subarray with length k, 
        for(int i = 0; i < k; i++){
            sum += nums[i];
        }

        int max = sum; // max sum of subarray with length k 
        for(int i = k, j = 0; i < nums.length; i++, j++){
            sum += nums[i] - nums[j];

            max = Math.max(max, sum);
        }

        return (double)max / k;
    }
}
