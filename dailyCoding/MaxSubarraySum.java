package dailyCoding;

/**
 *
 * Given an array of numbers, find the maximum sum of any contiguous subarray of the array.
 * For example, given the array [34, -50, 42, 14, -5, 86], the maximum sum would be 137, since we would take elements 42, 14, -5, and 86.
 * Given the array [-5, -1, -8, -9], the maximum sum would be 0, since we would not take any elements.
 *
 * Tags: amazon
 */

public class MaxSubarraySum {
    public int maxSubarraySum(int[] nums){
        if(null == nums || 0 == nums.length){
            return 0;
        }

        int max = 0;
        int maxLocal = 0;
        for(int n : nums){
            maxLocal = Math.max(maxLocal + n, n);

            max = Math.max(max, maxLocal);
        }

        return max;
    }

    public static void main(String[] args){
        int[][] cases = {
                {34, -50, 42, 14, -5, 86},
                {-5, -1, -8, -9}
        };

        int[] expects = {
                137,
                0
        };

        MaxSubarraySum sv = new MaxSubarraySum();

        for(int i = 0; i < cases.length; i++){
            int result = sv.maxSubarraySum(cases[i]);

            System.out.println(expects[i] + " " + result);
        }

    }

}
