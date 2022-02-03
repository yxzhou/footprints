/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array;

/**
 * _https://www.lintcode.com/problem/883
 * 
 * Given a binary array, find the maximum number of consecutive 1s in this array if you can flip at most one 0.
 *
 * Constraints:
 *   The input array will only contain 0 and 1. 
 *   The length of input array is a positive integer and will not exceed 10,000.
 *
 *
 * Example 1: 
 * Input: nums = [1,0,1,1,0] 
 * Output: 4
 * Explanation: Flip the first zero will get the the maximum number of consecutive 1s. After flipping, the maximum
 * number of consecutive 1s is 4.
 *
 * Example 2: 
 * Input: nums = [1,0,1,0,1] 
 * Output: 3
 * Explanation: Flip each zero will get the the maximum number of consecutive 1s. After flipping, the maximum number of
 * consecutive 1s is 3.
 *
 *
 */
public class MaxConsecutiveOnesII {
    /**
     * @param nums: a list of integer
     * @return a integer, denote  the maximum number of consecutive 1s
     */
    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0;

        int[] ones = new int[2];
        int o = 0;

        int[] zeros = new int[2];
        int z = 0;
        for(int i = 0, n = nums.length; i < n; i++){
            if(nums[i] == 1){
                ones[o]++; 
                
                if(ones[o] == 1){
                    z ^= 1;
                    zeros[z] = 0;
                }

            }else{
                if( zeros[z ^ 1] == 1 ){
                    max = Math.max(max, ones[o] + 1 +  ones[o^1] );
                }else{
                    max = Math.max(max, ones[o] );
                }

                zeros[z]++;

                if(zeros[z] == 1){
                    o ^= 1;
                    ones[o] = 0;
                }
            }
        }

        return max;
    }
    
    
    public int findMaxConsecutiveOnes_2(int[] nums) {
        int max = 0;

        int[] start = new int[2];
        int s = 0;
        for (int i = 0, n = nums.length; i < n; i++) {
            if (nums[i] == 1) {
                if (i > 0 && nums[i - 1] == 0) {
                    s ^= 1;
                    start[s] = i;
                }
            } else {
                if (i > 0 && nums[i - 1] == 1) {
                    if (start[s] > 1 && nums[start[s] - 2] == 1) {
                        max = Math.max(max, i - start[s ^ 1]);
                    } else {
                        max = Math.max(max, i - start[s]);
                    }
                }
            }
        }

        return max;
    }
}
