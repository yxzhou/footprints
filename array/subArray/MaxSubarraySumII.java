package array.subArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.Misc;

/**
 * 
 * Given an array of integers, find two non-overlapping subarrays which have the largest sum.
 * The number in each subarray should be contiguous.
 * Return the largest sum.
 * 
 * Example 1:
 * Input: nums = [1, 3, -1, 2, -1, 2]
 * Output: 7
 * Explanation:
 *   the two subarrays are [1, 3] and [2, -1, 2] or [1, 3, -1, 2] and [2].
 * 
 * Example 2:
 * Input: nums = [5,4]
 * Output: 9
 * Explanation:
 *   the two subarrays are [5] and [4].
 * 
 * Challenge
 *   Can you do it in time complexity O(n) ?
 * 
 * 
 */

public class MaxSubarraySumII {

    /**
     *
     * Solution:
     *   to every point nums[i], calculate the subSum of [0, i] and [i+1, n]
     *   
     *   Time O(N) Space O(n)
     */
    public int maxTwoSubArrays(List<Integer> nums) {
        int n = nums.size();
        int localSum = 0;

        //from left to right
        int[] left = new int[n + 1];//left[i] is the max sum of subarray [0, i)
        left[0] = Integer.MIN_VALUE;

        for(int i = 0; i < n; i++){
            localSum = Math.max(localSum, 0) + nums.get(i);

            left[i + 1] = Math.max(left[i], localSum);
        }

        //from right to left
        localSum = 0;
        int right = Integer.MIN_VALUE;////right[i] is the max sum of subarray [i, n)
        int result = Integer.MIN_VALUE;
        for(int i = n - 1; i > 0; i--){
            localSum = Math.max(localSum, 0) + nums.get(i);

            right = Math.max(right, localSum);
            result = Math.max(result, left[i] + right);
        }

        return result;
    }
	
    /**
     * Solution:
     *  
     *   define f[0][i] as the max sum of subarray that end with nums[i]
     *   define f[1][i] as the max sum of two non-overlapping subarrays that end with nums[i]
     *   define p[0][i] as the max sum of subarray
     *   define p[1][i] as the max sum of two non-overlapping subarrays
     *   
     *   f[0][0] = nums[0];
     *   f[0][i] = max(f[0][i - 1], 0) + nums[i],  (i > 0)
     *   f[1][1] = f[0][0] + nums[1]
     *   f[1][i] = max(max(f[0][0], f[0][1], -, f[0][i - 1]), f[1][i - 1]) + nums[i], (i > 1) 
     *           = max(p[0][i - 1], f[1][i - 1]) + nums[i], (i > 1)  
     * 
     *   p[1][i] = max(f[1][0], f[1][1], -, f[1][i - 1], f[1][i]) ;
     * 
     * Time O(N) Space O(n) 
     */
    public int maxTwoSubArrays_dp(ArrayList<Integer> nums) {
        if (null == nums || 2 > nums.size()) {
            return Integer.MIN_VALUE; // exception return
        }

        int n = nums.size();
        int[] localOneSum = new int[n]; //the max sum of subarray that end with nums[i]
        localOneSum[0] = nums.get(0);

        for (int i = 1; i < n; i++) {
            localOneSum[i] = Math.max(localOneSum[i - 1], 0) + nums.get(i);
        }

        int LocaltwoSum = nums.get(0); //the max sum of two non-overlapping subarrays that end with nums[i]
        int oneMax = Integer.MIN_VALUE; //the max sum of subarray 
        int result = Integer.MIN_VALUE;
        for (int i = 1; i < n; i++) {
            oneMax = Math.max(oneMax, localOneSum[i - 1]);

            LocaltwoSum = Math.max(LocaltwoSum, oneMax) + nums.get(i);
            result = Math.max(result, LocaltwoSum);
        }

        return result;
    }
    
    /*Time O(1) Space O(1) */
    public int maxTwoSubArrays_dp2(ArrayList<Integer> nums) {
        if (null == nums || 2 > nums.size()) {
            return Integer.MIN_VALUE; // exception return
        }

        int localOneSum = nums.get(0); //the max sum of subarray that end with nums[i]
        int localTwoSum = nums.get(0); //the max sum of two non-overlapping subarrays that end with nums[i]
        int oneMax = Integer.MIN_VALUE; ////the max sum of subarray that end with nums[i]
        int twoMax = Integer.MIN_VALUE; //result
        for (int i = 1; i < nums.size(); i++) {
            oneMax = Math.max(oneMax, localOneSum);

            localTwoSum = Math.max(localTwoSum, oneMax) + nums.get(i);
            twoMax = Math.max(twoMax, localTwoSum);

            localOneSum = Math.max(localOneSum, 0) + nums.get(i);
        }

        return twoMax;
    }
    
    /*Time O(1) Space O(1) */
    public int maxTwoSubArrays_dp3(ArrayList<Integer> nums) {
        if (null == nums || 2 > nums.size()) {
            return Integer.MIN_VALUE; // exception return
        }

        int localOneSum = 0;
        int localTwoSum = nums.get(0);
        int oneMax = Integer.MIN_VALUE;
        int twoMax = Integer.MIN_VALUE;
        for (int i = 1; i < nums.size(); i++) {
            localOneSum = Math.max(localOneSum, 0) + nums.get(i - 1);
            oneMax = Math.max(oneMax, localOneSum);

            localTwoSum = Math.max(localTwoSum, oneMax) + nums.get(i);
            twoMax = Math.max(twoMax, localTwoSum);
        }

        return twoMax;
    }


    
    public static void main(String[] args) {

        MaxSubarraySumII sv = new MaxSubarraySumII();

        Integer[][] input2 = {
            {1, 3, -1, 2, -1, 2},
            {-1, -2, -3, -100, -1, -50}
        };
        for (int i = 0; i < 2; i++) {
            System.out.println("\nThe original array is: " + Misc.array2String(input2[i]));

            System.out.println("The value of max sub array is: " + sv.maxTwoSubArrays(new ArrayList<Integer>(Arrays.asList(input2[i]))));
            System.out.println("The value of max sub array is: " + sv.maxTwoSubArrays_dp(new ArrayList<Integer>(Arrays.asList(input2[i]))));

            System.out.println("The value of max sub array is: " + sv.maxTwoSubArrays_dp2(new ArrayList<Integer>(Arrays.asList(input2[i]))));

            System.out.println("The value of max sub array is: " + sv.maxTwoSubArrays_dp3(new ArrayList<Integer>(Arrays.asList(input2[i]))));
        }

    }
    
    
}
