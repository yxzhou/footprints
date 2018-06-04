package fgafa.dp.sequence;

/**
 * 
 * Q1, There are a row of n houses, each house can be painted with one of the three
 * colors: red, blue or green. The cost of painting each house with a certain 
 * color is different. You have to paint all the houses such that no two 
 * adjacent houses have the same color.

   The cost of painting each house with a certain color is represented by a n x 3 cost matrix. 
   For example, costs[0][0] is the cost of painting house 0 with color red; 
   costs[1][2] is the cost of painting house 1 with color green, and so on... 
   Find the minimum cost to paint all houses.
    
   Note: All costs are positive integers.
 *
 *
 * Q2, There are a row of n houses, each house can be painted with one of the k colors. 
 *  The cost of painting each house with a certain color is different. You have to 
 *  paint all the houses such that no two adjacent houses have the same color.
    The cost of painting each house with a certain color is represented by a n x k cost matrix. 
    For example, costs[0][0] is the cost of painting house 0 with color 0; costs[1][2]
    is the cost of painting house 1 with color 2, and so on... Find the minimum cost to paint all houses.
    
    Note:
    All costs are positive integers.
    
    Follow up:
    Could you solve it in O(nk) runtime?
 *
 *  Q3, There are a row of house, each house can be painted with one of the three
 *  colors: red, blue or green. The cost of painting each house with a certain 
 *  color is different. You have to paint all the hourse such that no three
 *  adjacent houses have the same color.
 *  
 *  The cost of painting each house with a certain color is represented by a n x 3 cost matrix. 
    For example, costs[0][0] is the cost of painting house 0 with color red; 
    costs[1][2] is the cost of painting house 1 with color green, and so on... 
    Find the minimum cost to paint all houses.
    
    Note: All costs are positive integers.
 */

public class PaintHouse {
    /**
     * Q1, There are a row of n houses, each house can be painted with one of the three
     * colors: red, blue or green. The cost of painting each house with a certain 
     * color is different. You have to paint all the houses such that no two 
     * adjacent houses have the same color.
    
       The cost of painting each house with a certain color is represented by a n x 3 cost matrix. 
       For example, costs[0][0] is the cost of painting house 0 with color red; 
       costs[1][2] is the cost of painting house 1 with color green, and so on... 
       Find the minimum cost to paint all houses.
        
       Note: All costs are positive integers.
     */
    
    /*Time O(n)  Space O(1)*/
    public int minCost(int[][] costs) {
        //check input
        if(null == costs || 0 == costs.length){
            return 0;
        }
        
        int[][] dp = new int[2][3];
        int pre;
        int curr;
        for(int i = 0; i < costs.length; i++){
            pre = i & 1;
            curr = (pre + 1) & 1;
            
            dp[curr][0] = Math.min(dp[pre][1], dp[pre][2]) + costs[i][0];
            dp[curr][1] = Math.min(dp[pre][0], dp[pre][2]) + costs[i][1];
            dp[curr][2] = Math.min(dp[pre][0], dp[pre][1]) + costs[i][2];
        }
        
        pre = costs.length & 1;
        return Math.min(Math.min(dp[pre][0], dp[pre][1]), dp[pre][2]);
    }
    
    public int minCost_2(int[][] costs) {
        //check input
        if(null == costs || 0 == costs.length){
            return 0;
        }
        
        int[][] dp = new int[2][3];
        int pre;
        int curr;
        for(int i = 0; i < costs.length; i++){
            pre = i & 1;
            curr = (pre + 1) & 1;
            
            for(int color = 0; color < 3; color++){
                dp[curr][color] = Math.min(dp[pre][(color + 1) % 3], dp[pre][(color + 2) % 3]) + costs[i][color];
            }
        }
        
        pre = costs.length & 1;
        return Math.min(Math.min(dp[pre][0], dp[pre][1]), dp[pre][2]);
    }
    
    
    /**
     * Q2, There are a row of n houses, each house can be painted with one of the k colors. 
     *  The cost of painting each house with a certain color is different. You have to 
     *  paint all the houses such that no two adjacent houses have the same color.
        The cost of painting each house with a certain color is represented by a n x k cost matrix. 
        For example, costs[0][0] is the cost of painting house 0 with color 0; costs[1][2]
        is the cost of painting house 1 with color 2, and so on... Find the minimum cost to paint all houses.
        
        Note:
        All costs are positive integers.
        
        Follow up:
        Could you solve it in O(nk) runtime?
     */
    
    /*Time O(n*k)  Space O(k)*/
    public int minCostII(int[][] costs) {
        //check input
        if(null == costs || 0 == costs.length){
            return 0;
        }
        
        int n = costs.length;
        int k = costs[0].length;
        
        int[] dp = new int[k];
        int[][] min = new int[2][2]; //min[i][0] is the min, min[i][1] is 2nd min. 
        int pre;
        int curr;
        for(int i = 0; i < n; i++){
            pre = i & 1;
            curr = (pre + 1) & 1;
            
            min[curr][0] = Integer.MAX_VALUE;
            min[curr][1] = Integer.MAX_VALUE;
            
            for(int color = 0; color < k; color++){
                if(dp[color] == min[pre][0]){
                    dp[color] = min[pre][1] + costs[i][color];
                }else{
                    dp[color] = min[pre][0] + costs[i][color];
                }
                
                if(min[curr][0] < dp[color]){
                    min[curr][1] = min[curr][0];
                    min[curr][0] = dp[color];
                }else if(min[curr][1] < dp[color]){
                    min[curr][1] = dp[color];
                }
            }
        }
        
        //
        pre = n & 1;
        
        return Math.min(min[pre][0], min[pre][1]);
    }
    
    
    /**
     *  Q3, There are a row of house, each house can be painted with one of the three
     *  colors: red, blue or green. The cost of painting each house with a certain 
     *  color is different. You have to paint all the hourse such that no three
     *  adjacent houses have the same color.
     *  
     *  The cost of painting each house with a certain color is represented by a n x 3 cost matrix. 
        For example, costs[0][0] is the cost of painting house 0 with color red; 
        costs[1][2] is the cost of painting house 1 with color green, and so on... 
        Find the minimum cost to paint all houses.
        
        Note: All costs are positive integers.
     */
    /*Time O(n)  Space O()*/
    public int minCostIII(int[][] costs) {
        //check input
        if(null == costs || 0 == costs.length){
            return 0;
        }
        
        int n = costs.length;
        int k = costs[0].length;
        
        int[] dp = new int[k];
        int[][] min = new int[2][2]; //min[i][0] is the min, min[i][1] is 2nd min. 
        int pre;
        int curr;
        for(int i = 0; i < n; i++){
            pre = i & 1;
            curr = (pre + 1) & 1;
            
            min[curr][0] = Integer.MAX_VALUE;
            min[curr][1] = Integer.MAX_VALUE;
            
            for(int color = 0; color < k; color++){
                if(dp[color] == min[pre][0]){
                    dp[color] = min[pre][1] + costs[i][color];
                }else{
                    dp[color] = min[pre][0] + costs[i][color];
                }
                
                if(min[curr][0] < dp[color]){
                    min[curr][1] = min[curr][0];
                    min[curr][0] = dp[color];
                }else if(min[curr][1] < dp[color]){
                    min[curr][1] = dp[color];
                }
            }
        }
        
        //
        pre = n & 1;
        
        return Math.min(min[pre][0], min[pre][1]);
    }
}
