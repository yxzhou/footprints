/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp.backpack;

import java.util.Arrays;

/**
 * _https://www.lintcode.com/problem/562
 * 
 * Given n items with an array, num[i] indicate the size of ith item. 
 * Also give a backpack with size target. Find the number of ways to fill the backpack.
 *
 * Note
 * You cannot divide item into small pieces.
 * Each item may be chosen unlimited number of times.
 *
 * Example 1
 * Input: A = [2, 3, 6, 7] and target = 7.
 * Output: 2
 * Explanation: solution sets are:
 * [7]
 * [2, 2, 3]
 * 
 * Example 2
 * Input: A = [12, 3, 4, 5] and target = 7.
 * Output: 3
 * Explanation: solution sets are:
 * [2, 5]
 * [3, 4]
 * [2, 2, 3]
 * 
 *
 */
public class BackpackIV {
    //?? duplicate item size in the nums 
    // to confirm, all item size is positive and target is positive
    // pay attention to the difference with BackpackVI.java
    public int backPackIV(int[] nums, int target) {
        if(nums == null || nums.length == 0 || target < 1){
            return 0;
        }

        //Arrays.sort(nums);

        int[] times = new int[target + 1];//times[i] is the number of ways to fill backpack with size i
        times[0] = 1;

        for(int x : nums){
            for(int i = 0, end = target - x; i <= end; i++){
                if(times[i] > 0 ){
                    times[i + x] += times[i];
                }
            }
        }

        return times[target];
    }
}
