package fgafa.dp;

import java.util.Arrays;
import java.util.List;

import fgafa.util.Misc;

/**
 * 
 * Given a total amount of money amount, and coins of different denominations, 
 * compute the fewest number of coins that you need to "make up" that amount. 
 * If that amount of money cannot be made up by any combination of the coins, return -1.

    Example 1:
    coins = [1, 2, 5], amount = 11
    return 3 (11 = 5 + 5 + 1)
    
    Example 2:
    coins = [2], amount = 3
    return -1.
    
    Note:
    You may assume that you have an infinite number of each kind of coin.
 *
 */

public class CoinChangeII {

    
    /**
     * The Optimal Change == the solution with the fewest change amount. 
     * 
     * Greedy
     * e.g.  input:  target = 87, candidates = {50, 20, 10, 5, 1}
     *            50, 20, 10, 5, 1
     * coinsUsed   1   1   1  1  2   
     * 
     * Note:  Sometimes the greedy solution cann't get the optimal change 
     *   such as,  1) the coin value is a special array. (8, 5, 1) is not. 
     *             2) the coin/candidate is limited
     * 
     * @param target
     * @param candidates
     * @return the Optimal Change coin solution
     */
    public String getOptimalChange_Greedy(int target, int[] candidates) {
      int[] coinsUsed = new int[candidates.length];

      for(int i=0; i<candidates.length; i++){
        coinsUsed[i] = target / candidates[i];
        target = target % candidates[i];
        
        if(target == 0)  break;
          
      }
      
      return Misc.array2String(coinsUsed).toString();
    }
    
    
    /**  
     * The Optimal Change == the solution with the fewest change amount. 
     * DP,  
     * Define coinsUsed[n] == how many coins to a optimal change solution for n 
     * coinsUsed[target] = 0                                          when target = 0;
     *              = min( coinsUsed[cents - values[kind]] + 1 )      cents is from 1 to target
     *   
     * @param candidates  
     *            :the array of coin candidate  
     * @param target  
     *            :the target for making change  
     * @return the Optimal Change coin amount           
     */ 
    /* Time O(m * n)  Space O(n)*/
    public int getOptimalChange_DP_wrong(int target, int[] candidates) {  

        int[] coinsUsed = new int[target + 1];  // for storing the result,  store in index that is from 1 to target 
        coinsUsed[0] = 0;  
        
        for (int cents = 1; cents <= target; cents++) {  

            int minCoins = cents;  // the default is n * 1, the maximum. 

            //check if it can be divided by the candidate one by one 
            for (int kind = 0; kind < candidates.length; kind++) {               
                  
                if (candidates[kind] <= cents) {  
                    int temp = coinsUsed[cents - candidates[kind]] + 1;  
                    if (temp < minCoins) {  
                        minCoins = temp;  
                    }  
                }  
            }  
              
            coinsUsed[cents] = minCoins;  
        }  
        
        return coinsUsed[target] == target ? -1 : coinsUsed[target];
    }  
    
    public int getOptimalChange_DP1(int amount, int[] coins) {  
        //check
        if(null == coins || 0 == coins.length || amount < 1 ){
            return 0;
        }
        
        Arrays.sort(coins);
        
        int start = 0;
        while(start < coins.length && coins[start] <= 0 ){
            start++;
        }
        int end = start;
        for(int k = start + 1; k < coins.length; k++ ){
            if(coins[end] != coins[k]){
                end ++;
                coins[end] = coins[k];
            }
        }
        
        int[] dp = new int[amount + 1]; //default all are 0
        for(int j = start; j <= end && coins[j] <= amount; j++ ){
            dp[coins[j]] = 1;
        }
        
        int pre;
        l1: for(int curr = 1; curr <= amount; curr++){
            l2: for(int j = start; j <= end; j++){
                
                if( coins[j] >= curr ){
                    continue l1;
                }
                
                pre = curr - coins[j];
                if(dp[pre] > 0){
                    if(dp[curr] == 0 ){
                        dp[curr] = dp[pre] + 1;
                    }else{
                        dp[curr] = Math.min(dp[curr], dp[pre] + 1);
                    }
                }
                    
            }
        }
        
        return dp[amount] == 0 ? -1 : dp[amount];
    }
    
    
    public int getOptimalChange_DP2(int amount, int[] coins) {  
        //check
        if(null == coins || 0 == coins.length || amount < 1 ){
            return 0;
        }
        
        Arrays.sort(coins);
        
        //ignore the invalid coins
        int start = 0;
        while(start < coins.length && coins[start] <= 0 ){
            start++;
        }
        //ignore the duplicated 
        int end = start;
        for(int k = start + 1; k < coins.length && coins[k] <= amount; k++ ){
            if(coins[end] != coins[k]){
                end ++;
                coins[end] = coins[k];
            }
        }
        
        int[] dp = new int[amount + 1]; //default all are 0
        dp[0] = 0;
        for(int j = 1; j < dp.length; j++ ){
            dp[j] = Integer.MAX_VALUE;
        }
        
        int pre;
        l1: for(int curr = 1; curr <= amount; curr++){
            l2: for(int j = start; j <= end; j++){
                
                if( coins[j] > curr ){  //**
                    continue l1;
                }
                
                pre = curr - coins[j];
                if(dp[pre] != Integer.MAX_VALUE){  //**
                    dp[curr] = Math.min(dp[curr], dp[pre] + 1);
                }
                    
            }
        }
        
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
    
    
    public static void main(String[] args) {  
        CoinChangeII sv = new CoinChangeII();
        
        // the candidate is in descend order
        int[][] coinValue = {{ 25, 21, 10, 5, 1 }, {10, 1, 2, 7, 6, 1, 5}, { 25, 10, 5, 1 }, {8, 5, 1}, {4, 3, 2}, {4, 2}};  
        // the target for making change. 
        int[] target = {65, 8, 67, 20, 20, 5};  

        for(int i = 0; i<target.length; i++){
          System.out.println("\n--getOptimalChange_DP of "+ target[i] + " from " + Misc.array2String(coinValue[i]) + " is: " + sv.getOptimalChange_DP_wrong(target[i], coinValue[i]));
          System.out.println("--getOptimalChange_Greedy of "+ target[i] + " from " + Misc.array2String(coinValue[i]) + " is: " + sv.getOptimalChange_Greedy(target[i], coinValue[i]));
          
          System.out.println("--getOptimalChange_DP1 of "+ target[i] + " from " + Misc.array2String(coinValue[i]) + " is: " + sv.getOptimalChange_DP1(target[i], coinValue[i]));
          System.out.println("--getOptimalChange_DP2 of "+ target[i] + " from " + Misc.array2String(coinValue[i]) + " is: " + sv.getOptimalChange_DP2(target[i], coinValue[i]));
          
        }

    } 
}
