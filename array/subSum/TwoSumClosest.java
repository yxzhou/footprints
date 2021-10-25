package array.subSum;

import java.util.Arrays;

/**
 * 
 * Given an array nums of n integers, find two integers in nums such that the sum is closest to a given number, target.
 * Return the absolute value of difference between the sum of the two numbers and the target.
 * 
 * Example1
 * Input:  nums = [-1, 2, 1, -4] and target = 4
 * Output: 1   
 * Explanation: The minimum difference is 1. (4 - (2 + 1) = 1).
 * 
 * Example2
 * Input:  nums = [-1, -1, -1, -4] and target = 4
 * Output: 6
 * Explanation: The minimum difference is 6. (4 - (- 1 - 1) = 6).
 * 
 * Challenge: Do it in O(nlogn) time complexity.
 * 
 */

public class TwoSumClosest {

    /**
     * 
     * Time O(nlogn) + O(n), Space O(1)
     */
    public int twoSumClosest(int[] nums, int target) {
        if (nums == null) {
            return -1;
        }

        Arrays.sort(nums);

        int result = Integer.MAX_VALUE;
        int diff;
        for (int l = 0, r = nums.length - 1; l < r;) {
            diff = nums[l] + nums[r] - target;

            if (diff == 0) {
                return 0;
            } else if (diff < 0) {
                l++;
                result = Math.min(result, -diff);
            } else {
                r--;
                result = Math.min(result, diff);
            }
        }

        return result;
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
