package fgafa.dp.coinChange;

import fgafa.util.Misc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

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

public class CoinChange {

    
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

    public int coinChange_DP(int[] coins, int amount) {
        if(null == coins || 0 == coins.length || amount < 1 ){
            return 0;
        }

        Arrays.sort(coins);

        if(amount < coins[0]){
            return -1;
        }

        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        int length = coins.length;
        int diff;
        for(int i = coins[0]; i <= amount; i++){
            for(int j = 0; j < length && coins[j] <= i; j++){
                diff = i - coins[j];

                if(dp[diff] != Integer.MAX_VALUE ){
                    dp[i] = Math.min(dp[i], dp[diff] + 1);
                }

            }
        }


        return dp[amount] == Integer.MAX_VALUE? -1 : dp[amount];
    }

    public int coinChange_bfs(int[] coins, int amount) {
        if(null == coins || 0 == coins.length || amount < 1 ){
            return 0;
        }

        Arrays.sort(coins);

        if(amount < coins[0]){
            return -1;
        }

        boolean[] dp = new boolean[amount - coins[0] + 1];

        //bfs
        Queue<Integer> queue = new LinkedList<>();
        queue.add(amount);

        int n;
        int c = coins.length - 1;
        int diff;
        int counter = 0;
        while(!queue.isEmpty()){
            counter++;

            while(c >= 0 && coins[c] > amount){
                c--;
            }
            amount -= coins[0];

            for(int i = queue.size(); i > 0; i--){
                n = queue.poll();

                for(int j = c; j >= 0; j--){
                    diff = n - coins[j];

                    if( diff > 0 && !dp[diff]){
                        dp[diff] = true;
                        queue.add(diff);
                    }else if(diff == 0 ){
                        return counter;
                    }
                }
            }
        }

        return -1;
    }

    
    public static void main(String[] args) {  
        CoinChange sv = new CoinChange();
        
        // the candidate is in descend order
        int[][] coinValue = {{1}, {2}, { 25, 21, 10, 5, 1 }, {10, 1, 2, 7, 6, 1, 5}, { 25, 10, 5, 1 }, {8, 5, 1}, {4, 3, 2}, {4, 2}};
        // the target for making change. 
        int[] target = {0, 1, 65, 8, 67, 20, 20, 5};

        for(int i = 0; i<target.length; i++){
          System.out.println("\n--getOptimalChange_DP of "+ target[i] + " from " + Misc.array2String(coinValue[i]) + " is: " + sv.getOptimalChange_DP_wrong(target[i], coinValue[i]));
          System.out.println("--getOptimalChange_Greedy of "+ target[i] + " from " + Misc.array2String(coinValue[i]) + " is: " + sv.getOptimalChange_Greedy(target[i], coinValue[i]));
          
          System.out.println("--getOptimalChange_DP2 of "+ target[i] + " from " + Misc.array2String(coinValue[i]) + " is: " + sv.getOptimalChange_DP2(target[i], coinValue[i]));

          System.out.println(String.format("-coinChange_DP- %s %d , %d", Misc.array2String(coinValue[i]), target[i], sv.coinChange_DP(coinValue[i], target[i])) );
          System.out.println(String.format("-coinChange_bfs- %s %d , %d", Misc.array2String(coinValue[i]), target[i], sv.coinChange_bfs(coinValue[i], target[i])) );
        }

    } 
}
