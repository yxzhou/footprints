package array.subArray;

import util.Misc;

/**
 * 
 * Given an array of integers, find a contiguous subarray which has the largest sum.
 * 
 * 
 * Example 1:
 * Input: nums = [−2,2,−3,4,−1,2,1,−5,3]
 * Output: 6
 * Explanation:
 * the contiguous subarray [4,−1,2,1] has the largest sum = 6.
 * 
 * Example 2:
 * Input: nums = [1,2,3,4]
 * Output: 10
 * Explanation:
 * the contiguous subarray [1,2,3,4] has the largest sum = 10.
 * 
 * Challenge
 * Can you do it in time complexity O(n)?
* 
 */

public class MaxSubarraySum {

    /**
     * Solution: 
     *   Define a f[i] as the max subsum of subarray that end with nums[i]
     * 
     * Time O(n) Space O(1), n is the nums' length
     */
    public int maxSubArray_n(int[] nums) {
        if (null == nums || 0 == nums.length) {
            return Integer.MIN_VALUE;
        }

        int localSubSum = 0;
        int result = Integer.MIN_VALUE;
        for (int num : nums) {
            localSubSum = Math.max(localSubSum, 0) + num;
            result = Math.max(result, localSubSum);
        }

        return result;
    }

    
    /*It's only available to that the array is not with all negative numbers */
    protected int getMaxSum_wrong(int[] a) {
        int maxsum = 0;
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
            if (maxsum < sum) {
                maxsum = sum;
            } else if (sum < 0) { // how about if the array is not with all negative numbers
                sum = 0;
            }
        }

        return maxsum;
    }


    /**
     * @param args
     */
    public static void main(String[] args) {

        MaxSubarraySum sv = new MaxSubarraySum();
        /**
         * *
         */
        //int[] arr = {-1, 1, -2, 3, 5, -1, 0};
        int[][] arr = {{-3, 1, 3, -3, 4}, {-1, 1, -2, 3, 5, -1, 3, 0}, {0, -1}, {-2, -1, -3}, {2, 1}, {-2, 0}, {-1, 2}, {0, 0}, {1, -2, 3, 5, -3, 2},
        {0, -2, 3, 5, -1, 2}, {-9, -2, -3, -5, -3}};

        for (int i = 0; i < 1; i++) {
            System.out.println("\nThe original array is: " + Misc.array2String(arr[i]));

            System.out.println("The value of max sub array is: " + sv.maxSubArray_n(arr[i]));
        }

    }
}
