package dailyCoding;

import util.Misc;

/**
 * Given a list of integers, write a function that returns the largest sum of non-adjacent numbers. Numbers can be 0 or negative.
 *
 *  For example,
 *    [2, 4, 6, 2, 5] should return 13, since we pick 2, 6, and 5.
 *    [5, 1, 1, 5] should return 10, since we pick 5 and 5.
 *
 * Follow-up: Can you do this in O(N) time and constant space?
 *
 * Tags: Airbnb,  DP
 */

public class SumOfNonAdjacentNumbers {
    public int largestSumOfNonAdjacent(int[] nums){
        if(null == nums || 0 == nums.length){
            return 0;
        }

        if( 1 == nums.length){
            return nums[0];
        }

        int largestSumWithLast = nums[1];
        int largestSumWithoutLast = nums[0];

        int result = Math.max(largestSumWithLast, largestSumWithoutLast);

        for(int i = 2; i < nums.length; i++){
            largestSumWithLast = Math.max(nums[i], largestSumWithoutLast + nums[i]);

            largestSumWithoutLast = result;

            result = Math.max(result, largestSumWithLast);
        }

        return result;
    }


    public static void main(String[] args){
        int[][] input = {
                null,
                {},
                {2},
                {1,3},
                {2, 4, 6, 2, 5},
                {5, 1, 1, 5}
        };

        SumOfNonAdjacentNumbers sv = new SumOfNonAdjacentNumbers();

        for(int i = 0; i < input.length; i++){
            System.out.println(String.format(" %s \t %d ", Misc.array2String(input[i]), sv.largestSumOfNonAdjacent(input[i])));
        }
    }
}
