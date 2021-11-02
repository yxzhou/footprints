/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array;

import org.junit.Assert;

/**
 * Leetcode #309
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 *
 * Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and
 * sell one share of the stock multiple times) with the following restrictions:
 *
 * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 * After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
 *
 * Example 1: 
 * Input prices = [1, 2, 3, 0, 2] 
 * Output maxProfit = 3 
 * Explanation: transactions = [buy, sell, cooldown, buy, sell]
 *
 * Example 2:
 * Input prices = [3, 2, 6, 0, 3]
 * Output 7 
 */
public class BestBuyAndSellStockWithCooldown {

    /**
     *
     * 0 Analyze: 
     * The possible behavior on the case [1, 2, 3, 0, 2] would be
     *
     * 1. Define States 
     * To represent the decision at index i: buy[i]: Max profit till index i. The series of transaction
     * is ending with a buy. sell[i]: Max profit till index i. The series of transaction is ending with a sell.
     *
     * To clarify: Till index i, the buy / sell action must happen and must be the last action. It may not happen at
     * index i. It may happen at 0, ... i - 2, i - 1.
     *
     * In the end n - 1, return sell[n - 1]. Apparently we cannot finally end up with a buy. In that case, we would
     * rather take a rest at n - 1. For special case no transaction at all, classify it as sell[i], so that in the end,
     * we can still return sell[n - 1].      *
     * 2. Define Recursion 
     * buy[i]: To make a decision whether to buy at i, we either take a rest, by just using the old decision at i - 1, 
     * or sell at/before i - 2, then buy at i, We cannot sell at i - 1, then buy at i, because of the cool_down.
     *
     * sell[i]: To make a decision whether to sell at i, we either take a rest, by just using the old decision at i - 1,
     * or buy at/before i - 1, then sell at i.
     *
     * So we get the following formula: 
     * buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]); 
     * sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
     *
     * 3. Optimize to O(1) Space DP solution only depending on i - 1 and i - 2 can be optimized using O(1) space. 
     * Let b2, b1, b0 represent buy[i - 2], buy[i - 1], buy[i] 
     * Let s2, s1, s0 represent sell[i - 2], sell[i - 1], sell[i]
     *
     * Then arrays turn into Fibonacci like recursion: 
     * b0 = Math.max(b1, s2 - prices[i]); 
     * s0 = Math.max(s1, b1 + prices[i]);
     *
     * 4. Write Code in 5 Minutes 
     * First we define the initial states at i = 0: 
     * We can buy. The max profit at i = 0 ending with a buy is -prices[0]. 
     * We cannot sell. The max profit at i = 0 ending with a sell is 0.
     *
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        if (null == prices || prices.length < 2) {
            return 0;
        }

        int n = prices.length;
        int[] buy = new int[n + 2];
        int[] sell = new int[n + 2];

        buy[2] = -prices[0];
        sell[2] = 0;
        for (int i = 1; i < n; i++) {
            buy[i + 2] = Math.max(buy[i + 1], sell[i] - prices[i]);
            sell[i + 2] = Math.max(sell[i + 1], buy[i + 1] + prices[i]);
        }

        return sell[n + 1];
    }

    public int maxProfit_2(int[] prices) {
        if (null == prices || prices.length < 2) {
            return 0;
        }

        //at the end of every day, there are 3 status, the last action is buy or sell or cooldown
        int buy = -prices[0]; //define 'buy' as the max profit that the last action is buy
        int sell = 0; //define 'sell' as the max profit that the last action is sell
        int cooldown = 0; //define 'cooldown' as the max profit that the last action is cooldown

        int tmp;
        for (int i = 1; i < prices.length; i++) {
            tmp = buy;

            buy = Math.max(buy, cooldown - prices[i]);

            //cooldown = Math.max(cooldown, sell);
            cooldown = sell;

            sell = Math.max(sell, tmp + prices[i]);
        }

        return sell;
    }

    public static void main(String[] args) {

        BestBuyAndSellStockWithCooldown sv = new BestBuyAndSellStockWithCooldown();

        int[][] inputs = {
            {3,3,5,0,0,3,1,4},
            {3,2,6,5,0,3},
            {1,2,3,0,2}
        };
        
        int[] expects = {6, 7, 3};
        
        for(int i = 0; i < inputs.length; i++){
            Assert.assertEquals(expects[i], sv.maxProfit(inputs[i]));
            Assert.assertEquals(expects[i], sv.maxProfit_2(inputs[i]));
        }
    }
}
