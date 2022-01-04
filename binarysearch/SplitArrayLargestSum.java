package binarysearch;

import org.junit.Assert;

/**
 * Leetcode #410
 *
 * Given an array which consists of non-negative integers and an integer m, you can split the array into m non-empty
 * continuous subarrays. Write an algorithm to minimize the largest sum among these m subarrays.
 *
 * Note: If n is the length of array, assume the following constraints are satisfied:
 * 1 ≤ n ≤ 1000 
 * 1 ≤ m ≤ min(50, n) Examples:
 *
 * Example 1
 * Input: nums = [7,2,5,10,8] m = 2
 * Output: 18
 * Explanation: 
 *   The best way is to split it into [7,2,5] and [10,8], where the largest sum among the two subarrays is only 18.
 *
 * Example 2
 * Input: nums = [1,4,4] m = 3
 * Output: 4
 * 
 */

public class SplitArrayLargestSum {

    public int splitArray(int[] nums, int m) {
        if (nums == null || m < 1 || nums.length < m) {
            return -1;
        }

        int sum = 0;
        int max = 0;
        for (int n : nums) {
            sum += n;
            max = Math.max(max, n);
        }
        
        // if(nums.length == m ){
        //     return max;
        // }else if(m == 1){
        //     return sum;
        // }

        int low = max;
        int high = sum;
        int mid;
        while (low < high) {
            mid = low + (high - low) / 2;

            if (canSplit(nums, m, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private boolean canSplit(int[] nums, int m, int width) {
        int curr = 0;
        for (int n : nums) {
            if (curr < n) {
                m--;
                curr = width - n;
            } else {
                curr -= n;
            }
        }

        return m >= 0;
    }

    public static void main(String[] arg) {
        SplitArrayLargestSum sv = new SplitArrayLargestSum();
        
        Assert.assertEquals(18, sv.splitArray(new int[]{7, 2, 5, 10, 8}, 2));
        Assert.assertEquals(4, sv.splitArray(new int[]{1, 4, 1}, 3)); 
        Assert.assertEquals(4, sv.splitArray(new int[]{2, 3, 1, 2, 4, 3}, 5));
        Assert.assertEquals(2147483647, sv.splitArray(new int[]{1, 2147483646}, 1));
    }
}
