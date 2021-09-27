package dp.sequence;

import util.Misc;

/**
 * 
 * Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums. 
 * You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins. 
 * Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.

    Find the maximum coins you can collect by bursting the balloons wisely.
    
    Note: 
    (1) You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
    (2) 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
    
    Example:
    
    Given [3, 1, 5, 8]
    
    Return 167
    
        nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
       coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
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
 *   To f[1, a0, a1, a2, 1],
 *   If the idea is to burn a1 at first,  it's a0*a1*a2 + f[1, a0, a2 , 1], the states are variable.
 *   If the idea is to burn a1 at last,  it's f[1, a0, a1] + f[a1, a2, 1] + 1 * a1 * 1.
 *
 */

public class BurstBalloons {

    public int maxCoins_dp(int[] nums) {
        int len = nums.length;
        int n = len + 2;

        int[] v = new int[n];
        v[0] = 1;
        v[n - 1] = 1;
        System.arraycopy(nums, 0, v, 1, len);

        int[][] dp = new int[n][n]; //default all are 0

        for(int gap = 2; gap < n; gap++){
            for(int left = 0, right = gap; right < n; left++, right++){
                for(int i = left + 1; i < right; i++){
                    dp[left][right] = Math.max(dp[left][right], dp[left][i] + v[left]*v[i]*v[right] + dp[i][right]);
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

        int[][] input = { 
                    { 3, 1, 5, 8 } 
        };

        for(int[] nums : input){
            System.out.println(String.format("%s -- %d", Misc.array2String(nums), sv.maxCoins(nums)));
        }
    }

}
