package dp.arithmetic;

import java.util.HashMap;
import java.util.Map;

/**
 * Leetcode #1218
 *
 * Given an integer array arr and an integer difference, return the length of the longest subsequence in arr which is an arithmetic sequence such that the difference between adjacent elements in the subsequence equals difference.
 *
 *
 *
 * Example 1:
 *
 * Input: arr = [1,2,3,4], difference = 1
 * Output: 4
 * Explanation: The longest arithmetic subsequence is [1,2,3,4].
 * Example 2:
 *
 * Input: arr = [1,3,5,7], difference = 1
 * Output: 1
 * Explanation: The longest arithmetic subsequence is any single element.
 * Example 3:
 *
 * Input: arr = [1,5,7,8,5,3,4,2,1], difference = -2
 * Output: 4
 * Explanation: The longest arithmetic subsequence is [7,5,3,1].
 *
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^5
 * -10^4 <= arr[i], difference <= 10^4
 *
 */

public class LongestArithmeticSequenceII {

    public int longestSubsequence(int[] arr, int difference) {

        if(arr == null || arr.length == 0){
            return 0;
        }

        int n = arr.length;
        Map<Integer, Integer> counts = new HashMap<>(); // <arr[i], count>

        int max = 0;

        int pre;
        for(int v : arr){
            pre = v - difference;
            counts.put(v, counts.getOrDefault(pre, 0) + 1 );

            max = Math.max(max, counts.get(v));
        }

        return max;
    }

}
