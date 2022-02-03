/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array;

/**
 * _https://www.lintcode.com/problem/1212
 *
 * Given a binary array, find the maximum number of consecutive 1s in this array.
 *
 * Constraints:
 * The input array will only contain 0 and 1. 
 * The length of input array is a positive integer and will not exceed 10,000
 *
 *
 * Example 1:
 * Input: [1,1,0,1,1,1] 
 * Output: 3 
 * Explanation: 
 * The first two digits or the last three digits are consecutive 1s. The maximum number of consecutive 1s is 3. 
 * 
 * Example 2:
 * Input: [1] 
 * Output: 1
 * 
 */
public class MaxConsecutiveOnes {
    /**
     * @param nums: a binary array
     * @return the maximum number of consecutive 1s
     */
    public int findMaxConsecutiveOnes_n(int[] nums) {
        int max = 0;
        int count = 0;

        for(int x : nums){
            if(x == 0){
                max = Math.max(max, count);
                count = 0;
            }else{
                count++;
            }
        }

        return Math.max(max, count);
    }
    
    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0;

        int n = nums.length;
        int start = 0;
        for(int i = 0; i < n; i++){
            if(nums[i] == 0){
                if(i > 0 && nums[i - 1] == 1){
                    max = Math.max(max, i - start);
                }
            }else{
                if(i > 0 && nums[i - 1] == 0){
                    start = i;
                }
            }
        }

        if(nums[n - 1] == 1){
            max = Math.max(max, n - start);
        }

        return max;
    }
}
