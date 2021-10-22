/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.subArray;

/**
 * Given an array of n positive integers and a positive integer s, find the minimal length of a subarray of which the sum ≥ s. If there isn't one, return -1 instead.
 * 
 * Example 1:
 * Input: [2,3,1,2,4,3], s = 7
 * Output: 2
 * Explanation: The subarray [4,3] has the minimal length under the problem constraint.
 * 
 * Example 2:
 * Input: [1, 2, 3, 4, 5], s = 100
 * Output: -1
 * 
 */
public class MinSubarraySize {
    /**
     * @param nums: an array of integers
     * @param s: An integer
     * @return 
     * @return  
     * @return: an integer representing the minimum size of subarray
     */
    public int minimumSize(int[] nums, int s) {
        if(nums == null || nums.length == 0){
            return -1;
        }

        int n = nums.length;
        int minLength = n;
        int sum = 0;

        for(int l = 0, r = 0; r < n; r++){
            sum += nums[r];

            while(sum >= s){
                if( minLength > r - l){ 
                    minLength = r - l;
                }
                //minLength = Math.min(minLength, r - l);

                sum -= nums[l];
                l++;
            }
        }

        return minLength == n ? -1 : minLength + 1;
    }
}
