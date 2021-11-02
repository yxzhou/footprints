package array.lis;

/**
 * Leetcode #673
 *
 * Given an unsorted array of integers, find the number of longest increasing subsequence.
 *
 * Example 1:
 * Input: [1,3,5,4,7]
 * Output: 2
 * Explanation: The two longest increasing subsequence are [1, 3, 4, 7] and [1, 3, 5, 7].
 *
 * Example 2:
 * Input: [2,2,2,2,2]
 * Output: 5
 * Explanation: The length of longest continuous increasing subsequence is 1, and there are 5 subsequences' length is 1, so output 5.
 *
 * Note: Length of the given array will be not exceed 2000 and the answer is guaranteed to be fit in 32-bit signed int.
 *
 */

public class NumberOfLongestIncreasingSubsequence {

    public int findNumberOfLIS_n(int[] nums) {
        if (nums == null) {
            return 0;
        }

        int n = nums.length;
        int[] counts = new int[n];
        int[] depths = new int[n];

        int result = 0;
        int maxDepth = 0;

        int diff;
        for (int i = 0; i < n; i++) {
            counts[i] = 1;

            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] < nums[i]) {
                    diff = depths[i] - depths[j] - 1;
                    if (diff < 0) {
                        depths[i] = depths[j] + 1;
                        counts[i] = counts[j];
                    } else if (diff == 0) {
                        counts[i] += counts[j];
                    }
                }
            }

            diff = maxDepth - depths[i];
            if (diff < 0) {
                maxDepth = depths[i];
                result = counts[i];
            } else if (diff == 0) {
                result += counts[i];
            }
        }

        return result;
    }

}
