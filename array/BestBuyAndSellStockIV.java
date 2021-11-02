/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array;

import junit.framework.Assert;
import util.Misc;

/**
 *
 * Given an array prices and the i-th element of it represents the price of a stock on the i-th day.
 * 
 * You may complete at most k transactions. What's the maximum profit?
 * 
 * You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
 * 
 * Example 1:
 * Input: k = 2, prices = [4, 4, 6, 1, 1, 4, 2 ,5]
 * Output: 6
 * Explanation: Buy at 4 and sell at 6. Then buy at 1 and sell at 5. Your profit is 2 + 4 = 6.
 * 
 * Example 2:
 * Input: k = 1, prices = [3, 2, 1]
 * Output: 0
 * Explanation: No transaction.
 * 
 * Challenge O(nk) time. n is the size of prices.
 * 
 */
public class BestBuyAndSellStockIV {
    
    /**
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * 
     * Design an algorithm to find the maximum profit. You may complete at most k transactions.
     * 
     * Note: You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     * 
     * Thoughts:
     *  define dp[k][i] as the max profit that buy and sell at most k transactions in array(0, i)
     *  define p[i][j] as the max profit that buy and sell 1 transactions in array(i, j)
     *
     *  if k > n - 1, it equals maxProfit_multipleTransaction
     *
     *  dp[0][0] = 0
     *  dp[k][k] = dp[k - 1][k - 1] + Math.max(0, prices[k] - prices[k - 1]);
     *
     *  dp[k][i] = max(dp[k-1][j] + p[j + 1][i]),  for i from k to n, for j from 0 to i - 1
     *
     *  How to calculate p[i][j]? It's similar with the max sum of subArray 
     * 
     *  Time O( k * n * n),   Space O(n * k)
     *  
     * 
     */
    //TODO  optimized to  Time O( k * n)
    
    
    /**
     * Based on maxProfit_OneLoop and maxProfit_DP
     * 
     * Time O(k * n), Space O(k*n) that can be optimized to O(n)
     * 
     * @param k
     * @param prices
     * @return 
     */
    
    public int maxProfit_n(int k, int[] prices){
        if(k == 0 || prices.length == 0 ){
            return 0;
        }
        
        int n = prices.length;
        
        //in n days, the price is up and down, the max up wave number is n / 2, 
        //so when k >= n/2, it's as same as BestBuyAndSellStockII, 
        if(k * 2 >= n){
            int result = 0;
            for(int i = 1; i < n; i++){
                result += Math.max(0, prices[i] - prices[i - 1]);
            }
            return result;
        }
        
        int[][] dp = new int[k + 1][n + 1];
        int buyLast;
        for(int t = 1; t <= k; t++){
            buyLast = -prices[0];
            for(int i = 2; i <= n; i++){
                dp[t][i] = Math.max(dp[t][i - 1], buyLast + prices[i - 1]);
                
                buyLast = Math.max(buyLast, dp[t - 1][i - 1] - prices[i - 1]);
            }
        }
        
        return dp[k][n];
    }
    
    
    /**
     * 
     * refer to http://liangjiabin.com/blog/2015/04/leetcode-best-time-to-buy-and-sell-stock.html
     * 
     * 局部最优解 vs. 全局最优解：   
     local[i][j] = max(global[i – 1][j – 1], local[i – 1][j] + diff)
     global[i][j] = max(global[i – 1][j], local[i][j])

      local[i][j]和global[i][j]的区别是：
      local[i][j]意味着在第i天一定有交易（卖出）发生。
             当第i天的价格高于第i-1天（即diff > 0）时，那么可以把这次交易（第i-1天买入第i天卖出）跟第i-1天的交易（卖出）合并为一次交易，
             即local[i][j]=local[i-1][j]+diff；
             当第i天的价格不高于第i-1天（即diff<=0）时，那么local[i][j]=global[i-1][j-1]+diff，而由于diff<=0，所以可写成local[i][j]=global[i-1][j-1]。

      global[i][j]就是我们所求的前i天最多进行k次交易的最大收益，可分为两种情况：
             如果第i天没有交易（卖出），那么global[i][j]=global[i-1][j]；
             如果第i天有交易（卖出），那么global[i][j]=local[i][j]。
     *
     * 
     * Time O(n * k)  Space O(n)
     */
	
    public int maxProfit_2(int k, int[] prices) {
        if (null == prices || prices.length < 2 || k < 1) {
            return 0;
        }

        int days = prices.length;
        if (days <= k * 2) {
            int result = 0;
            for (int i = 1; i < days; i++) {
                result += Math.max(0, prices[i] - prices[i - 1]);
            }
            return result;
        }

        int[] local = new int[k + 1]; // 
        int[] global = new int[k + 1]; //
        for (int i = 1; i < days; i++) {
            int diff = prices[i] - prices[i - 1];

            for (int j = 1; j <= k; j++) {
                local[j] = Math.max(global[j - 1], local[j] + diff);
                global[j] = Math.max(global[j], local[j]);
            }
        }

        return global[k];
    }
    
    public static void main(String[] args){
        
        System.out.println("\n--maxProfit, buy and sell k times--");
        BestBuyAndSellStockIV sv = new BestBuyAndSellStockIV();

        int[][] inputs = {
            {1, 2, 4, 2, 5, 7, 2, 4, 9, 0}, 
            {4, 4, 6, 1, 1, 4, 2 ,5},
            {3, 2, 1}
        };
        int[] k = {4, 2, 1};
        int[] expects = {15, 6, 0};

        for (int i = 0; i < inputs.length; i++) {
            System.out.println(String.format("Input : k = %d, Prices = %s ", k[i], Misc.array2String(inputs[i])));
            
            //System.out.println("Output: " + sv.maxProfit_2(k[i], inputs[i]) + " - " + sv.maxProfit_n(k[i], inputs[i]));
            Assert.assertEquals(expects[i], sv.maxProfit_2(k[i], inputs[i]));
            Assert.assertEquals(expects[i], sv.maxProfit_n(k[i], inputs[i]));
        }
    }
}
