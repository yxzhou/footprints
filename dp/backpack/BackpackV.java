/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp.backpack;

import java.util.Arrays;

/**
 * _https://www.lintcode.com/problem/563
 * 
 * Given n items with size num[i] which an integer array and all positive numbers.
 * Also give a backpack with size target. Find the number of possible fill the backpack.
 *
 * Note
 * You cannot divide item into small pieces.
 * Each item may only be used once.
 *
 * Example 1
 * Input: A = [1, 2, 3, 3, 7] and target = 7.
 * Output: 2
 * Explanation: solution sets are:
 * [7]
 * [1, 3, 3]
 * 
 *
 */
public class BackpackV {
    
    public int backPackV(int[] nums, int target) {
        if(nums == null || nums.length == 0){
            return 0;
        }

        int[] times = new int[target + 1];
        times[0] = 1;

        for(int x : nums){
            for(int size = target - x; size >= 0; size--){
                if(times[size] > 0){
                    times[size + x] += times[size]; 
                }
            }
        }

        return times[target];
    }
       
   
    
}
