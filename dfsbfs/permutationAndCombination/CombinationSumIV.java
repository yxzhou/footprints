/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfsbfs.permutationAndCombination;

import java.util.Arrays;

/**
 * _https://www.lintcode.com/problem/564
 * 
 * Description
    Given an integer array nums with all positive numbers and no duplicates, find the number of possible combinations that add up to a positive integer target.

    A number in the array can be used multiple times in the combination.
    Different orders are counted as different combinations.

   Example1
    Input: nums = [1, 2, 4], and target = 4
    Output: 6
    Explanation:
    The possible combination ways are:
    [1, 1, 1, 1]
    [1, 1, 2]
    [1, 2, 1]
    [2, 1, 1]
    [2, 2]
    [4]
    
   Example2
    Input: nums = [1, 2], and target = 4
    Output: 5
    Explanation:
    The possible combination ways are:
    [1, 1, 1, 1]
    [1, 1, 2]
    [1, 2, 1]
    [2, 1, 1]
    [2, 2]
 * 
 * 
 * 
 * 
 */
public class CombinationSumIV {
    /**
     * Solution:
     * define f[i] as the number of possible combination that up to i. 
     * init f[0] = 1;
     * 
     * Example {[1, 2, 4], 4}
     * f(1) = 1  ( 0 + 1 )
     * f(2) = 2  ( 1 + 1 or 0 + 2)  (1 + 2)
     * f(3) =    ( 1 + 1 + 1 or 1 + 2 or 2 + 1)
     * f(4) =    ( )
     */
    public int backPackVI(int[] nums, int target) {
        if(nums == null || target < 1){
           return 0;
        }

        int[] dp = new int[target + 1];//dp[i] is the number of possible combination that add up
        dp[0] = 1;

        int z;
        for(int x = 1; x <= target; x++ ){
            for(int y : nums){
                z = x - y;
                if(z >= 0 && dp[z] > 0){
                    dp[x] += dp[z];
                }
            } 
        }

       return dp[target];
    }
}
