package dp.sequence;

import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/168
 * 
 * Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums. 
 * You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins. 
 * Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.
 * 
 *  Find the maximum coins you can collect by bursting the balloons wisely.
 *
 * Note: (1) You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them. (2) 0 ≤ n ≤
 * 500, 0 ≤ nums[i] ≤ 100
 * 
 * Example:
 * Given [3, 1, 5, 8]
 * Return 167
 * Explanation:
 *     nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
 *    coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
 *
 *
 * Thoughts:
 * Given [a0, a1, a2], define f[1, a0, a1, a2, 1] as the max coins from [a0, a1, a2]
 *
 *   f[1, a0, a1, a2, 1]
 * If the last step is [1, a0, 1], it's f[a0, a1, a2, 1] + 1*a0*1
 * If the last step is [1, a1, 1], it's f[1, a0, a1] + f[a1, a2, 1] + 1*a1*1
 * If the last step is [1, a2, 1], it's f[1, a0, a1, a2] + 1*a2*1
 *
 * Summary:
 *   Define f[1, a0, a1, a2, 1] as the main coins of {a0, a1, a2}
 * 
 *   If it is to burn a1 at first,  it's a0*a1*a2 + f[1, a0, a2, 1], the states are variable.
 *   If it is to burn a1 at last,  it's f[1, a0, a1] + 1 * a1 * 1 + f[a1, a2, 1]  ( burn a1 at last)
 *     f[1, a0, a1, a2, 1] = max(
 *          1 * a0 * 1 + f[a0, a1, a2, 1]               ( burn a0 at last)
 *          f[1, a0, a1] + 1 * a1 * 1 + f[a1, a2, 1]    ( burn a1 at last)
 *          f[1, a0, a1, a2] + 1 * a2 * 1               ( burn a2 at last)
 *     )
 *
 */

public class BurstBalloons {

    public int maxCoins_dp(int[] nums) {
        if(nums == null){
            return 0;
        }
        
        int len = nums.length;
        int n = len + 2;

        int[] v = new int[n];
        v[0] = v[n - 1] = 1;
        System.arraycopy(nums, 0, v, 1, len);   

        int[][] dp = new int[n][n]; //default all are 0

        for(int w = 2; w < n; w++){
            for(int i = 0, j = w; j < n; i++, j++){
                for(int k = i + 1; k < j; k++){
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + v[i]*v[k]*v[j] + dp[k][j]);
                }
            }
        }

        return dp[0][n - 1];
    }

    public int maxCoins(int[] nums) {
        if(null == nums || 0 == nums.length){
            return 0;
        }
        
        int n = nums.length;
        
        int[] newNums = new int[n + 2];
        newNums[0] = newNums[n + 1] = 1;
        System.arraycopy(nums, 0, newNums, 1, n);
        
        int[][] dp = new int[n + 2][n + 2]; //default all are 0
        return maxCoins(newNums, 1, n, dp);
    }
    
    private int maxCoins(int[] nums, int i, int j, int[][] dp){
        
        if(dp[i][j] > 0){
            return dp[i][j];
        }
        
        for(int k = i; k <=j; k++){
            dp[i][j] = Math.max(dp[i][j], maxCoins(nums, i, k - 1, dp) + nums[i - 1] * nums[k] * nums[j + 1] + maxCoins(nums, k + 1, j, dp));
        }
        
        return dp[i][j];
    }
    
    public static void main(String[] args) {

        BurstBalloons sv = new BurstBalloons();

        int[][][] inputs = { 
            {null, {0}},
            {{ }, {0}},
            {{ 3 }, {3}},
            {{ 3, 5 }, {20}},
            {{ 3, 1, 5 }, {35}},
            {{ 3, 1, 5, 8 }, {167}},
            {{ 4, 1, 5, 10 }, {270}},

        };

        for(int[][] input : inputs){
            System.out.println(String.format("\nnums: %s,  \tmaxCoins = %d", Misc.array2String(input[0]), sv.maxCoins(input[0])));
            
            Assert.assertEquals(input[1][0], sv.maxCoins_dp(input[0]));
            
            Assert.assertEquals(input[1][0], sv.maxCoins(input[0]));
        }
    }

}
