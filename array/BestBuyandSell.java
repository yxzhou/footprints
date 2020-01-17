package fgafa.array;

import fgafa.util.Misc;

public class BestBuyandSell
{

  /*
   * Say you have an array for which the ith element is the price of a given stock on day i.
   * If you were only permitted to complete at most on transaction (ie, buy one and sell one share of the stock),
   * design an algorithm to find the maximum profit.
   * 
   * Time O(n)   Space O(1)
   */
  public int maxProfitI(int[] prices) {
      if(null == prices || prices.length < 2){
          return 0;
      }
      
      int result = 0;
      int bottom = Integer.MAX_VALUE;
      for(int price : prices){
          if(price < bottom){
              bottom = price;
          }else{
              result = Math.max(result, price - bottom);
          }
      }
      
      return result;
  }
  
  /*
   * Say you have an array for which the ith element is the price of a given stock on day i.
   * Design an algorithm to find the maximum profit.
   * You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times).
   * However, you may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
   */

  public int maxProfitII(int[] prices) {
      if(null == prices || prices.length < 2){
          return 0;
      }
      
      int result = 0;
      for(int i = 1; i < prices.length; i++){
          if(prices[i] > prices[i - 1]){
              result += prices[i] - prices[i - 1];
          }
      }
      
      return result;
  }
  
  /*
   * Say you have an array for which the ith element is the price of a given stock on day i.
   * Design an algorithm to find the maximum profit. You may complete at most two transactions.
   * 
   * Note:
   * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
   * 
   * Solution:
   * First check from left to right to find the maximum profit between the start to the i-th day.
   * Then check from right to left to find the maximum profit between the i-th day to the end.
   * Add two arrays and find the maximum profit.
   */
  public int maxProfitIII(int[] prices) {
      if (prices == null || prices.length < 2)
          return 0;

      int length = prices.length;

      //from left to right
      int[] leftMaxProfit = new int[length]; //leftProfit[i] = the max profit between 0 to i, default value all are 0
      leftMaxProfit[0] = 0;
      for (int bottom = prices[0], i = 1; i < length; i++) {
          if (prices[i] < bottom) {
              bottom = prices[i];

              leftMaxProfit[i] = leftMaxProfit[i - 1];
          } else {
              leftMaxProfit[i] = Math.max(prices[i] - bottom, leftMaxProfit[i - 1]);
          }
      }

      //from right to left
      int[] rightMaxProfit = new int[length]; //rightProfix[i] = the max profit between i to length, default value all are 0
      rightMaxProfit[length - 1] = 0;
      for (int top = prices[length - 1], i = length - 2; i >= 0; i--) {
          if (prices[i] > top) {
              top = prices[i];

              rightMaxProfit[i] = rightMaxProfit[i + 1];
          } else {
              rightMaxProfit[i] = Math.max(top - prices[i], rightMaxProfit[i + 1]);
          }
      }

      int maxProfit = leftMaxProfit[length - 1];
      for (int i = length - 2; i >= 0; i--) {
          maxProfit = Math.max(maxProfit, leftMaxProfit[i] + rightMaxProfit[i + 1]);
      }

      return maxProfit;
  }

  public int maxProfitIII_n(int[] prices) {
      if(null == prices || prices.length < 2){
          return 0;
      }

      int length = prices.length;
      int[] leftMaxProfit = new int[length]; //default all are 0
      for(int bottom = prices[0], i = 1; i < length; i++){
          if(prices[i] < bottom){
              bottom = prices[i];
              
              leftMaxProfit[i] = leftMaxProfit[i - 1];
          }else{
              leftMaxProfit[i] = Math.max(leftMaxProfit[i - 1], prices[i] - bottom);
          }
      }
      
      int result = leftMaxProfit[length - 1];
      int rightMaxProfit = 0;
      int top = prices[length - 1];
      for(int i = length - 2; i >= 0; i--){
          result = Math.max(result, leftMaxProfit[i] + rightMaxProfit);

          if(prices[i] > top){
              top = prices[i];
          }else{
              rightMaxProfit = Math.max(rightMaxProfit, top - prices[i]);
          }
      }
      
      return result;
  }
  
	/**
	 * Say you have an array for which the ith element is the price of a given stock on day i.
	 * 
	 * Design an algorithm to find the maximum profit. You may complete at most k transactions.
	 * 
	 * Note: You may not engage in multiple transactions at the same time (ie,
	 * you must sell the stock before you buy again).
	 * 
	 * Thoughts:
     *  define dp[k][i] as the max profit that buy and sell at most k transactions in array(0, i)
     *  define p[i][j] as the max profit that buy and sell 1 transactions in array(i, j)
     *
     *  if k > n - 1, it equals maxProfitII
     *
     *  dp[0][0] = 0
     *  dp[k][k] = dp[k - 1][k - 1] + Math.max(0, prices[k] - prices[k - 1]);
     *
     *  dp[k][i] = max(dp[k-1][j] + p[j + 1][i]),  for i from k to n, for j from 0 to i - 1
     *
	 */
  /**
   * Time O( k * n * n * n),   Space O(n * k)
   */
  public int maxProfit(int k, int[] prices) {
      //check
      if(null == prices || 0 == prices.length || k < 1){
          return 0;
      }
      
      int days = prices.length;

      if(days - 1 < k){
          //return maxProfit(days - 2, prices);
          return maxProfitII(prices);
      }
      
      //define p[i][j] as the max profit from 0 to j - 1 with at most i transactions
      int[][] p = new int[k + 1][days]; //default all are 0
      p[0][0] = 0;
      
      for(int i = 1; i <= k; i++ ){
          p[i][i] = p[i - 1][i - 1] + Math.max(0, prices[i] - prices[i - 1]);
          
          for(int j = i + 1; j < days; j++){
              for(int q = i - 1; q < j; q ++){
                  p[i][j] = Math.max(p[i][j], p[i - 1][q] + maxProfit(prices, q, j));
              }
          }
          
      }
      
      return p[k][days - 1];
  }
  
  private int maxProfit(int[] prices, int start, int end){
      int maxDiff = 0;
      int min = Integer.MAX_VALUE;
      
      for(int i = start; i <= end; i++){
          if(prices[i] < min){
              min = prices[i];
          }else{
              maxDiff = Math.max(maxDiff, prices[i] - min);
          }
      }
      
      return maxDiff; 
  }

    /**
     * Time O( k * n * n ),   Space O(n * n)
     */
    public int maxProfit_k(int k, int[] prices) {
        //check
        if (null == prices || 0 == prices.length || k < 1) {
            return 0;
        }

        int n = prices.length;

        if(n - 1 < k){
            return maxProfitII(prices);
        }

        //define p[i][j] as the max profit that buy and sell 1 transactions in array(i, j)
        int[][] p = new int[n][n];
        for(int i = 0; i < n; i++){
            int min = prices[i];
            for(int j = i + 1; j < n; j++){
                min = Math.min(min, prices[j]);
                p[i][j] = Math.max(p[i][j - 1], prices[j] - min);
            }
        }

        //define dp[k][i] as the max profit that buy and sell at most k transactions in array(0, i)
        int[][] dp = new int[k + 1][n];
        dp[0][0] = 0;
        for(int i = 1; i <= k; i++ ){
            dp[i][i] = dp[i - 1][i - 1] + Math.max(0, prices[i] - prices[i - 1]);

            for(int j = i + 1; j < n; j++){
                for(int q = i - 1; q < j; q ++){
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][q] + p[q][j]);
                }
            }

        }

        return dp[k][n - 1];
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
   * Time O(k * n)  Space O(n * n)
   */
	public int maxProfit_n(int k, int[] prices) {
	    //check
	    if(null == prices || prices.length < 2|| k < 1 ){
	        return 0;
	    }
	    
	    int days = prices.length;
	    if(days <= k){
	        return maxProfitII(prices);
	    }

        int[][] local = new int[days][k + 1];
        int[][] global = new int[days][k + 1];
        
        for (int i = 1; i < days ; i++) {
            int diff = prices[i] - prices[i - 1];
            
            for (int j = 1; j <= k; j++) {
                local[i][j] = Math.max(global[i - 1][j - 1], local[i - 1][j] + diff);
                global[i][j] = Math.max(global[i - 1][j], local[i][j]);
             }
        }
        
        return global[days - 1][k];
	}

    /**
     * Time O(k * n)  Space O(n)
     */
	
  public int maxProfit_n2(int k, int[] prices) {
      //check
      if(null == prices || prices.length < 2|| k < 1 ){
          return 0;
      }
      
      int days = prices.length;
      if(days <= k){
          return maxProfitII(prices);
      }

      int[] local = new int[k + 1];
      int[] global = new int[k + 1];
      
      for (int i = 1; i < days ; i++) {
          int diff = prices[i] - prices[i - 1];
          
//          for (int j = k; j > 0; j--) {
//              local[j] = Math.max(global[j - 1], local[j]) + diff;
//              global[j] = Math.max(global[j], local[j]);
//          }
          for (int j = 1; j <= k; j++) {
              local[j] = Math.max(global[j - 1], local[j] + diff);
              global[j] = Math.max(global[j], local[j]);
          }
      }
      
      return global[k];
  }
  
  /**
   * Say you have an array for which the ith element is the price of a given stock on day i.

    Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times) with the following restrictions:
    
    You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
    After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
    
    Example:
    prices = [1, 2, 3, 0, 2]
    maxProfit = 3
    transactions = [buy, sell, cooldown, buy, sell]
   */
  
  /*
   * 0 Analyze:
   * The possible behavior on the case [1, 2, 3, 0, 2] would be
   *
   * 1. Define States
    To represent the decision at index i:
    buy[i]: Max profit till index i. The series of transaction is ending with a buy.
    sell[i]: Max profit till index i. The series of transaction is ending with a sell.
    
    To clarify:
    Till index i, the buy / sell action must happen and must be the last action. 
    It may not happen at index i. It may happen at i - 1, i - 2, ... 0.

    In the end n - 1, return sell[n - 1]. Apparently we cannot finally end up with a buy. 
    In that case, we would rather take a rest at n - 1.
    For special case no transaction at all, classify it as sell[i], 
    so that in the end, we can still return sell[n - 1]. 
    
    2. Define Recursion
    buy[i]: To make a decision whether to buy at i, we either take a rest, by just using the old decision at i - 1, 
    or sell at/before i - 2, then buy at i, We cannot sell at i - 1, then buy at i, because of cooldown.
    sell[i]: To make a decision whether to sell at i, we either take a rest, by just using the old decision at i - 1, 
    or buy at/before i - 1, then sell at i.
    
    So we get the following formula:
    buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);   
    sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
    
    3. Optimize to O(1) Space
    DP solution only depending on i - 1 and i - 2 can be optimized using O(1) space.
    Let b2, b1, b0 represent buy[i - 2], buy[i - 1], buy[i]
    Let s2, s1, s0 represent sell[i - 2], sell[i - 1], sell[i]
    
    Then arrays turn into Fibonacci like recursion:
    b0 = Math.max(b1, s2 - prices[i]);
    s0 = Math.max(s1, b1 + prices[i]);
    
    4. Write Code in 5 Minutes
    First we define the initial states at i = 0:
    We can buy. The max profit at i = 0 ending with a buy is -prices[0].
    We cannot sell. The max profit at i = 0 ending with a sell is 0.
   */
    public int maxProfitIV(int[] prices) {
        if(null == prices || prices.length < 2){
            return 0;
        }

        //at the end of every day, there are 3 status, the last action is buy or sell or cooldown
        int buy = -prices[0]; //define 'buy' as the max profit that the last action is buy
        int sell = 0; //define 'sell' as the max profit that the last action is sell
        int cooldown = 0; //define 'cooldown' as the max profit that the last action is cooldown

        for (int i = 1; i < prices.length; i++) {
            buy = Math.max(buy, cooldown - prices[i]);

            //cooldown = Math.max(cooldown, sell);
            cooldown = sell;

            sell = Math.max(sell, buy + prices[i]);
        }

        return sell;
    }

    /**
     * Say you have an array for which the ith element is the price of a given stock on day i.
     Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times) with the following restrictions:

     You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     It will take a fee to every transaction

     Example:
     prices = [1, 3, 2, 8, 4, 9], fee = 2
     maxProfit = 8
     transactions = [buy, rest, rest, sell, buy, sell]
     */
    public int maxProfitV(int[] prices, int fee){
        if(null == prices || prices.length < 2){
            return 0;
        }

        //at the end of every day, there are 2 status, the last action is buy or sell
        int buy = -prices[0]; //define 'buy' as the max profit that the last action is buy
        int sell = 0; //define 'sell' as the max profit that the last action is sell

        for(int i = 1; i < prices.length; i++){
            buy = Math.max(buy, sell - prices[i]);
            sell = Math.max(sell, buy + prices[i] - fee);
        }

        return sell;
    }

    /**
   * @param args
   */
  public static void main(String[] args) {
    BestBuyandSell sv = new BestBuyandSell();

    System.out.println("\n--maxProfitII, buy and sell multiple times--");
    int[][] prices = { {6,1,3,2,4,7}, {2,1,4,5,2,9,7}};
    
    for(int i=0; i< prices.length; i++){
      System.out.println("Input : "+ Misc.array2String(prices[i]));
      System.out.println("Output: "+ sv.maxProfitII(prices[i]));
      
    }
    
    System.out.println("\n--maxProfit, buy and sell k times--");
    
    int[][] prices_2 = {{1,2,4,2,5,7,2,4,9,0}};
    int[] k  = {4};
    
    for(int i=0; i< prices_2.length; i++){
        System.out.println(String.format("Input : k = %d, Prices = %s ", k[i], Misc.array2String(prices_2[i])));
        System.out.println("Output: "+ sv.maxProfit_k(k[i], prices_2[i]) + " - " + sv.maxProfit_n2(k[i], prices_2[i]));
        
    }
    
    System.out.println("\n--maxProfitIV--, buy and sell multiple times with cooldown");
    
    int[][] prices_3 = {
                {1, 2, 3, 0, 2},
                {6,1,3,2,4,7}
                };
    
    for(int i=0; i< prices_3.length; i++){
        System.out.println(String.format("Input : Prices = %s ",  Misc.array2String(prices_3[i])));
        System.out.println("Output: "+ sv.maxProfitIV(prices_3[i]) );
        
    }

      System.out.println("\n--maxProfitV--, buy and sell multiple times with a fee");

      int[][] prices_4 = {
              {1, 3, 2, 8, 4, 9}
      };
      int[] fees = {
              2
      };

      for(int i=0; i< prices_4.length; i++){
          System.out.println(String.format("Input : Prices = %s, fee=%d",  Misc.array2String(prices_4[i]), fees[i]));
          System.out.println("Output: "+ sv.maxProfitV(prices_4[i], fees[i]) );

      }
  }

}
