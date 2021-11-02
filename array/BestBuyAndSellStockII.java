/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array;

import junit.framework.Assert;
import util.Misc;

/**
 * Leetcode #122
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * 
 * You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times).
 * However, you may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 * 
 * Design an algorithm to find the maximum profit.
 * 
 * Example 1:
 * Input: [2, 1, 2, 0, 1]
 * Output: 2
 * Explanation: 
    1. Buy the stock on the second day at 1, and sell the stock on the third day at 2. Profit is 1.
    2. Buy the stock on the 4th day at 0, and sell the stock on the 5th day at 1. Profit is 1.
    Total profit is 2.
 * 
 * Example 2:
 * Input: [4, 3, 2, 1]
 * Output: 0
 * Explanation: No transaction, profit is 0.
 * 
 */
public class BestBuyAndSellStockII {


    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int result = 0;
        for (int i = 1; i < prices.length; i++) {
            result += Math.max(0, prices[i] - prices[i - 1]);
        }

        return result;
    }
    
    public int maxProfit_2(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int sell = 0;
        int profit;
        for (int i = 1; i < prices.length; i++) {
            profit = prices[i] - prices[i - 1];
            sell = Math.max(sell, sell + profit);
        }

        return sell;
    }
    
    public static void main(String[] args){
        BestBuyAndSellStockII sv = new BestBuyAndSellStockII();

        System.out.println("\n--maxProfit_multipleTransaction, buy and sell multiple times--");
        int[][] prices = { 
            {2,1,2,0,1},
            {4,3,2,1},
            {6,1,3,2,4,7}, 
            {2,1,4,5,2,9,7}};
        
        int[] expects = {2, 0, 7, 11};

        for (int i = 0; i < prices.length; i++) {
            System.out.println("Input : " + Misc.array2String(prices[i]));
            
            //System.out.println("Output: " + sv.maxProfit(prices[i]));
            Assert.assertEquals(expects[i], sv.maxProfit(prices[i]));
            Assert.assertEquals(expects[i], sv.maxProfit_2(prices[i]));
        }
    }
    
}
