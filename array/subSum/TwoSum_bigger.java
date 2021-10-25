/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.subSum;

import java.util.Arrays;

/**
 *
 * Given an array of integers, find how many pairs in the array such that their sum is bigger than a specific target 
 * number. Please return the number of pairs.
 * 
 * Example 1:
 * Input: [2, 7, 11, 15], target = 24
 * Output: 1
 * Explanation: 11 + 15 is the only pair.
 * 
 * Example 2:
 * Input: [1, 1, 1, 1], target = 1
 * Output: 6
 * 
 * Challenge Do it in O(1) extra space and O(nlogn) time.
 * 
 */
public class TwoSum_bigger {
    /*Time O(nlogn) + O(n), Space O(1)*/
    public int twoSumBiggerThan_n(int[] nums, int target) {

        if (nums == null) {
            return 0;
        }

        Arrays.sort(nums);

        int result = 0;
        int diff;
        for (int l = 0, r = nums.length - 1; l < r;) {
            diff = nums[r] + nums[l] - target;
            if (diff > 0) {
                result += r - l;
                r--;
            } else { //sum <= target
                l++;
            }
        }

        return result;
     }
}
