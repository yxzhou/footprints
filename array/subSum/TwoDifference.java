/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.subSum;

import java.util.Arrays;

/**
 *
 * Given an sorted array of integers, find two numbers that their difference equals to a target value.
 * Return a list with two number like [num1, num2] that the difference of num1 and num2 equals to target value, and num1 is less than num2.
 * 
 * It's guaranteed there is only one available solution.
 * Note: Requires O(1) space complexity 
 * 
 * 
 * Example 1:
 * Input: nums = [2, 7, 15, 24], target = 5 
 * Output: [2, 7] 
 * Explanation: (7 - 2 = 5)
 * 
 * Example 2:
 * Input: nums = [1, 1], target = 0
 * Output: [1, 1] 
 * Explanation: (1 - 1 = 0)
 * 
 */
public class TwoDifference {
    /**
     * @param nums: an array of Integer
     * @param target: an integer
     * @return: [num1, num2] (num1 < num2)
     */
    public int[] twoSum7(int[] nums, int target) {
        if (nums == null) {
            return new int[2];
        }

        target = Math.abs(target);
        Arrays.sort(nums);

        int n = nums.length;
        int diff;
        for (int l = 0, r = 1; l < r && r < n;) {
            diff = nums[r] - nums[l] - target;

            if (diff == 0) {
                return new int[]{nums[l], nums[r]};
            } else if (diff < 0) {
                r++;
            } else {
                l++;
                r = Math.max(l + 1, r);
            }
        }

        //not found
        return new int[2];
    }
}
