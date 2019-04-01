package fgafa.dp.coinChange;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 *
 * You are given coins of different denominations and a total amount of money.
 * Write a function to compute the number of combinations that make up that amount.
 * You may assume that you have infinite number of each kind of coin.
 *
 * Example 1:
 * Input: amount = 5, coins = [1, 2, 5]
 * Output: 4
 * Explanation: there are four ways to make up the amount:
 * 5=5
 * 5=2+2+1
 * 5=2+1+1+1
 * 5=1+1+1+1+1
 *
 * Example 2:
 * Input: amount = 3, coins = [2]
 * Output: 0
 * Explanation: the amount of 3 cannot be made up just with coins of 2.
 *
 * Example 3:
 * Input: amount = 10, coins = [10]
 * Output: 1
 *
 * Note:
 * You can assume that
 * 0 <= amount <= 5000
 * 1 <= coin <= 5000
 * the number of coins is less than 500
 * the answer is guaranteed to fit into signed 32-bit integer
 *
 */

public class CoinChangeII {

    public int change(int amount, int[] coins) {
        if(amount < 1 || amount < coins[0]){
            return 0;
        }

        Arrays.sort(coins);

        int[] dp = new int[amount + 1]; //default all are 0
        dp[0] = 1;

        for(int c : coins){
            for(int x = c; x <= amount; x++){
                dp[x] += dp[x - c];
            }
        }

        return dp[amount];
    }

    @Test
    public void test(){

        Assert.assertEquals(4, change(5, new int[]{1, 2, 5}));
        Assert.assertEquals(0, change(3, new int[]{2}));
        Assert.assertEquals(1, change(10, new int[]{10}));

    }

}
