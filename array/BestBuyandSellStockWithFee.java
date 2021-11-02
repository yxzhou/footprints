package array;

import junit.framework.Assert;
import util.Misc;

/**
 * 
 * Say you have an array for which the ith element is the price of a given stock on day i. Design an algorithm to find 
 * the maximum profit. 
 * You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times) with 
 * the following restrictions: You may not engage in multiple transactions at the same time (ie, you must sell the stock
 * before you buy again). It will take a fee to every transaction
 * 
 *  Example 1:
 *  Input: prices = [1, 3, 2, 8, 4, 9], fee = 2
 *  Output: maxProfit = 8
 *  Explanation: transactions = [buy, rest, rest, sell, buy, sell]
 *    The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8
 * 
 *  Example 2:
 *  prices = [1, 4, 6, 2, 8, 3, 10, 14], fee = 3
 *  maxProfit = 13
 * 
 */

public class BestBuyandSellStockWithFee {


    public int maxProfit_withFee(int[] prices, int fee){
        if(null == prices || prices.length < 2){
            return 0;
        }

        //at the end of every day, there are 2 status, the last action is buy or sell
        int buy = -prices[0]; //define 'buy' as the max profit that the last action is buy
        int sell = 0; //define 'sell' as the max profit that the last action is sell

        int tmp;
        for(int i = 1; i < prices.length; i++){
            tmp = buy; 
            buy = Math.max(buy, sell - prices[i]); 
            
            sell = Math.max(sell, tmp + prices[i] - fee);
        }

        return sell;
    }


    public static void main(String[] args) {
        System.out.println("\n--maxProfit_withFee--, buy and sell multiple times with a fee");

        BestBuyandSellStockWithFee sv = new BestBuyandSellStockWithFee();

        int[][] inputs = {
            {1, 3, 2, 8, 4, 9},
            {1, 4, 6, 2, 8, 3, 10, 14}
        };
        int[] fees = {2, 3};
        int[] expects = {8, 13};

        for (int i = 0; i < inputs.length; i++) {
            System.out.println(String.format("Input : Prices = %s, fee=%d", Misc.array2String(inputs[i]), fees[i]));
            //System.out.println("Output: " + sv.maxProfit_withFee(prices_4[i], fees[i]));
            Assert.assertEquals(expects[i], sv.maxProfit_withFee(inputs[i], fees[i]) );
        }
    }

}
