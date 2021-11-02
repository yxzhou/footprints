/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array;

/**
 * Leetcode #121  oneTransaction
 *
 * Say you have an array for which the ith element is the price of a given stock on day i. 
 * If you were only permitted to complete at most on transaction (ie, buy one and sell one share of the stock), 
 * design an algorithm to find the maximum profit.
 *
 * Example 1
 * Input: [3, 2, 3, 1, 2]
 * Output: 1
 * Explanation: You can buy at the third day and then sell it at the 4th day. The profit is 2 - 1 = 1
 * 
 * Example 2
 * Input: [1, 2, 3, 4, 5]
 * Output: 4
 * Explanation: You can buy at the 0th day and then sell it at the 4th day. The profit is 5 - 1 = 4
 * 
 * Example 3
 * Input: [5, 4, 3, 2, 1]
 * Output: 0
 * Explanation: You can do nothing and get nothing.
 * 
 * Time O(n) Space O(1)
 * 
 */
public class BestBuyAndSellStock {

    /**
     * Define global[i] as the biggest profit in [0, i], min[i] as the minimum in [0, i)
     * 
     *    global[i] = Math.max(global[i], prices[i] - min[i]);
     * 
     * @param prices
     * @return 
     */
    public int maxProfit(int[] prices) {
        if (null == prices || prices.length < 2) {
            return 0;
        }

        int result = 0; //max profit
        int min = Integer.MIN_VALUE;
        int profit;
        for (int i = 0; i < prices.length; i++ ) {
            profit = prices[i] - min;
            
            if (profit <= 0) {
                min = prices[i];
            } else {
                result = Math.max(result, profit);
            }
        }

        return result;
    }
    
    /**
     * change the price array {price0, price1, price2 ... price_i} to profit array { profit0, profit1 ... profit_i }
     *    profit_i = prices[i] - prices[i - 1]
     * The the maximum profit is similar with the max sum of sub array in the profit array 
     * 
     *    local[i] = Math.max(local[i - 1] + profit[i], profit[i]);
     *    global[i] = Math.max(global[i], local[i]);
     * 
     * @param prices
     * @return 
     */
    public int maxProfit_2(int[] prices) {
        if (null == prices || prices.length < 2) {
            return 0;
        }

        int local = 0; //local max profix
        int result = 0; //global max profit, 
        int profit;
        for (int i = 1; i < prices.length; i++ ) {
            profit = prices[i] - prices[i - 1];
            
            local = Math.max(local + profit, profit);
            result = Math.max(result, local);

        }

        return result;
    } 
    
    /**
     * To i_day, it can be three status, buy or sell or nothing to do.
     * 
     * @param prices
     * @return 
     */
    public int maxProfit_3(int[] prices) {
        if (null == prices || prices.length < 2) {
            return 0;
        }

        int buy = Integer.MIN_VALUE;
        int sell = 0;
        for (int price : prices ) {
            buy = Math.max(buy, -price); //only can buy one, Math.max( not buy this, buy this)
            sell = Math.max(sell, buy + price);            
        }

        return sell;
    }

}
