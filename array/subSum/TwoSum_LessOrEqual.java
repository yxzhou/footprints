/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.subSum;

import java.util.Arrays;

/**
 *
 * Given an array of integers, find how many pairs in the array such that their sum is less than or equal to a specific 
 * target number. Please return the number of pairs.
 * 
 * Example 1:
 * Input: nums = [2, 7, 11, 15], target = 24. 
 * Output: 5. 
 * Explanation:
 *   2 + 7, 2 + 11, 2 + 15 
 *   7 + 11, 7 + 15 
 * 
 * Example 2:
 * Input: nums = [1], target = 1. 
 * Output: 0. 
 * 
 */
public class TwoSum_LessOrEqual {
    /**
     * 
     * Time O(nlogn) + O(n), Space O(1)
     * 
     * @param nums
     * @param target
     * @return 
     */
    
    public int twoSum5(int[] nums, int target) {
        if (nums == null) {
            return 0;
        }

        Arrays.sort(nums);

        int result = 0;
        int diff;
        for (int l = 0, r = nums.length - 1; 0 < r; r--) {
            diff = nums[r] - target;

            while (l < r && diff + nums[l] <= 0) {
                l++;
            }

            result += Math.min(l, r);
        }

        return result;
    }
    


}
