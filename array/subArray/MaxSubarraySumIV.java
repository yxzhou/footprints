/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.subArray;

/**
 *
 * Given an integer arrays, find a contiguous subarray which has the largest sum and length should be greater or equal to given length k.
 * Return the largest sum, return 0 if there are fewer than k elements in the array.
 * 
 * Notes:
 * Ensure that the result is an integer type. 
 * k > 0
 * 
 * Example 1:
 * Input: [-2,2,-3,4,-1,2,1,-5,3], 5
 * Output: 5
 * Explanation:
 *   [2,-3,4,-1,2,1] sum=5
 * 
 * Example 2:
 * Input: [5,-10,4], 2
 * Output: -1
 * 
 * 
 *  Solution
 * define sum[i] as the sum of nums in [ 0 , i ] 
 *   sum[0] = nums[0], sum[i + 1] = sum[i] + nums[i]
 *   or sum = 0, .. , sum += num[i] 
 *
 * define minSum[i] as the min sum in { 0, sum[0], .. sum[i] }
 *   minSum[0] = Math.min(0, sum[0]); minSum[i + 1] = Math.min(minSum[i], sum[i])
 *   or minSum = 0, minSum = Math.min(minSum, sum[0]), .. , minSum = Math.min(minSum, sum[i])
 * 
 * define maxSubarraySum[i] as the max sum of subarray in nums [0, i]
 *  maxSubarraySum[i] = sum[i] - min{ 0, sum[0], sum[1], .. , sum[i - 1] }
 *  maxSubarraySum[i] = sum[i] - minSum[i - 1]
 *  
 * define maxSubarraySum[i, k] as the max sum of subarray with k length at least in nums [0, i]
 *  maxSubarraySum[i, k] = sum[i] - min{ 0, sum[0], sum[1], .. , sum[i - k] }
 *  maxSubarraySum[i, k] = sum[i] - minSum[i - k]
 *
 */
public class MaxSubarraySumIV {
    public int maxSubarray4(int[] nums, int k) {
        if(nums == null || nums.length < k){
            //throw new IllegalArgumentException(" nums is null or nums' length is smaller than k");
            return 0;
        }

        int sum = 0; // the sum from 0 to r
        int preSum = 0; //the sum from 0 to r - k
        int preMinSum = 0; // the minimum sum from -1 to j, j is [0, r - k], preMinSum == 0 means nothing selected
        
        for(int i = 0; i < k; i++){
            sum += nums[i];
        }

        int result = sum;
        for(int r = k, l = 0, n = nums.length; r < n; r++, l++){
            sum += nums[r];

            preSum += nums[l];
            preMinSum = Math.min(preMinSum, preSum);

            //result = Math.max(result, Math.max(localSum, localSum - preMinSum));
            if(preMinSum < 0){
                result = Math.max(result, sum - preMinSum);
            }else{
                result = Math.max(result, sum);
            }
        }

        return result;
    }
    
    public int maxSubarray4_n(int[] nums, int k) {
        if(nums == null || nums.length < k){
            //throw new IllegalArgumentException(" nums is null or nums' length is smaller than k");
            return 0;
        }

        int sum = 0; // the sum from 0 to r
        int preSum = 0; //the sum from 0 to r - k
        int preMinSum = 0; // the minimum sum from -1 to j, j is [0, r - k], preMinSum == 0 means nothing selected
        
        for(int i = 0; i < k; i++){
            sum += nums[i];
        }

        int result = sum;
        for(int r = k, l = 0, n = nums.length; r < n; r++, l++){
            sum += nums[r];

            preSum += nums[l];
            preMinSum = Math.min(preMinSum, preSum);

            result = Math.max(result, sum - preMinSum);
        }

        return result;
    }
}
