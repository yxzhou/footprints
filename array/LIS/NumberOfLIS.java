package array.LIS;

import java.util.Arrays;

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

public class NumberOfLIS {

    public int findNumberOfLIS(int[] nums) {
        if(nums == null || nums.length == 0 ){
            return 0;
        }

        int n = nums.length;

        int[] counts = new int[n];
        Arrays.fill(counts, 1);

        int[] depths = new int[n];

        for(int i = 1; i < n; i++){
            for(int j = i - 1; j >= 0; j-- ){

                if( nums[j] < nums[i]){
                    if(depths[j] >= depths[i] ){
                        depths[i] = depths[j] + 1;

                        counts[i] = counts[j];
                    }else if(depths[j] + 1 == depths[i]){
                        counts[i] += counts[j];
                    }
                }

            }
        }

        int maxDepth = 0;
        for(int depth : depths){
            maxDepth = Math.max(maxDepth, depth);
        }

        int sum = 0;
        for(int i = 0; i < n; i++){
            if(maxDepth == depths[i]){
                sum += counts[i];
            }
        }

        return sum;
    }


    public int findNumberOfLIS_n(int[] nums) {
        if(nums == null || nums.length == 0 ){
            return 0;
        }

        int n = nums.length;

        int[] counts = new int[n];
        int[] depths = new int[n];

        int maxDepth = 0;
        int result = 0;

        for(int i = 0; i < n; i++){
            counts[i] = 1;

            for(int j = i - 1; j >= 0; j-- ){
                if( nums[j] < nums[i]){
                    if(depths[i] <= depths[j] ){
                        depths[i] = depths[j] + 1;

                        counts[i] = counts[j];
                    }else if(depths[i] == depths[j] + 1){
                        counts[i] += counts[j];
                    }
                }
            }

            if(maxDepth < depths[i]){
                maxDepth = depths[i];

                result = counts[i];
            }else if(maxDepth == depths[i]){
                result += counts[i];
            }

        }

        return result;
    }

}
