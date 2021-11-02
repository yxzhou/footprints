/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array;

import org.junit.Assert;
import util.Misc;

/**
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit. You may complete at most two transactions.
 * 
 * Note:
 *   You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 * 
 * Example 1
 * Input : [4,4,6,1,1,4,2,5]
 * Output : 6
 * 
 * Solution:
 * First check from left to right to find the maximum profit between the start to the i-th day.
 * Then check from right to left to find the maximum profit between the i-th day to the end.
 * Add two arrays and find the maximum profit.
 * 
 */
public class BestBuyAndSellStockIII {

    public int maxProfit_TwoLoop(int[] prices) {
        if(prices == null || prices.length < 2 ){
            return 0;
        }

        int n = prices.length;

        //max profit from left to right
        int[] profits = new int[n];
        int min = prices[0];
        int profit = 0;
        int diff;
        for(int i = 1; i < n; i++){
            diff = prices[i] - min;

            if(diff <= 0){
                min = prices[i];
            }else{
                profit = Math.max(profit, diff);
                profits[i] = profit;
            }
        }

        int result = 0;
        // from right to left
        int max = 0;
        profit = 0;
        for(int i = n - 1; i >= 0; i--){
            diff = max - prices[i];

            if(diff <= 0){
                max = prices[i];
            }else{
                profit = Math.max(profit, diff);
            }

            result = Math.max(result, profits[i] + profit);
        }

        return result;
    }

    /**
     * 
     * @param prices
     * @return 
     */
    public int maxProfit_OneLoop(int[] prices) {
        if(prices == null || prices.length < 2 ){
            return 0;
        }

        int buy1 = Integer.MIN_VALUE; // the first buy
        int buy2 = Integer.MIN_VALUE; // the second buy
        int sell1 = 0; //the first sell, profit
        int sell2 = 0; //the second sell, 
        for(int price : prices){
            buy1 = Math.max(buy1, -price);
            sell1 = Math.max(sell1, buy1 + price);
            buy2 = Math.max(buy2, sell1 - price);
            sell2 = Math.max(sell2, buy2 + price);
        }
    
        return sell2;
    }
    
    /**
     *  when k == 1, 
     *    buyLast = -prices[0];
     *    dp[2] = Math.max(dp[1], buyLast + prices[1]);
     *    buyLast = Math.max(buyLast, -prices[1]);
     * 
     *    for i from 3 to n, both inclusive
     *      dp[i] = Math.max(dp[i - 1], buyLast + prices[i - 1]);
     *      buyLast = Math.max(buyLast, -prices[i - 1]);
     * 
     *  when k == 2,
     * 
     * 
     * @param prices
     * @return 
     */
    public int maxProfit_DP(int[] prices) {
        if(prices == null || prices.length < 2 ){
            return 0;
        }

        int n = prices.length;
    
        int[][] dp = new int[3][n + 1];
        int buyLast;
        for(int t = 1; t <= 2; t++){
            buyLast = -prices[0];
            for(int i = 2; i <= n; i++){
                dp[t][i] = Math.max(dp[t][i - 1], buyLast + prices[i - 1]);
                
                buyLast = Math.max(buyLast, dp[t - 1][i - 1] - prices[i - 1]);
            }
        }
        
        return dp[2][n];
    }
    
    
    public static void main(String[] args){
        
        System.out.println("\n--maxProfit, buy and sell k times--");
        BestBuyAndSellStockIII sv = new BestBuyAndSellStockIII();

        int[][] inputs = {{4,4,6,1,1,4,2,5}};
        int[] expects = {6};

        for (int i = 0; i < inputs.length; i++) {
            System.out.println(String.format("Input : Prices = %s ", Misc.array2String(inputs[i])));
            
            //System.out.println("Output: " + sv.maxProfit_kTransaction_n(k[i], prices_2[i]) + " - " + sv.maxProfit_kTransaction_x2(k[i], prices_2[i]));
            Assert.assertEquals(expects[i], sv.maxProfit_OneLoop(inputs[i]));
            Assert.assertEquals(expects[i], sv.maxProfit_TwoLoop(inputs[i]));
            Assert.assertEquals(expects[i], sv.maxProfit_DP(inputs[i]));
        }
    }
}
