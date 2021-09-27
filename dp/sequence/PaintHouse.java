package dp.sequence;

import java.util.Arrays;

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
    
    /**
     * Time O(n)  Space O(1)
     */
    public int minCost(int[][] costs) {
        if(costs == null || costs.length == 0){
            return 0;
        }

        int n = costs.length;

        int[][] dp = new int[2][3]; //RGB, default all are 0
        int curr = 0;
        int next;
        for(int d = 0; d < n; d++){
            next = curr ^ 1;

            dp[next][0] = Math.min(dp[curr][1], dp[curr][2]) + costs[d][0];
            dp[next][1] = Math.min(dp[curr][2], dp[curr][0]) + costs[d][1];
            dp[next][2] = Math.min(dp[curr][0], dp[curr][1]) + costs[d][2];

            curr = next;
        }

        return Math.min(Math.min(dp[curr][0], dp[curr][1]), dp[curr][2]);
    }
    
    public int minCost_2(int[][] costs) {
        //check input
        if(null == costs || 0 == costs.length){
            return 0;
        }
        
        int[][] dp = new int[2][3];
        int curr = 0;
        int next;
        for(int i = 0; i < costs.length; i++){
            next = curr ^ 1;
            
            for(int color = 0; color < 3; color++){
                dp[next][color] = Math.min(dp[curr][(color + 1) % 3], dp[curr][(color + 2) % 3]) + costs[i][color];
            }

            curr = next;
        }

        return Math.min(Math.min(dp[curr][0], dp[curr][1]), dp[curr][2]);
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
    
    /**
     * Time O(n*k)  Space O(k),
     * space can be O(1) if change the costs[][]
     */
    public int minCostII(int[][] costs) {
        if(costs == null || costs.length == 0 || costs[0].length == 0){
            return 0;
        }

        int n = costs.length;
        int k = costs[0].length;

        int[] dp = new int[k]; //default all are 0,  the minimum cost
        int[][] mins = new int[2][2]; //minimum and 2rd minimum of cost
        int curr = 0;
        int next;
        for(int d = 0; d < n; d++){
            next = curr ^ 1;

            Arrays.fill( mins[next], Integer.MAX_VALUE );

            for(int i = 0; i < k; i++){
                dp[i] = (dp[i] == mins[curr][0] ? mins[curr][1] : mins[curr][0]) + costs[d][i];

                if(dp[i] < mins[next][0]){
                    mins[next][1] = mins[next][0];
                    mins[next][0] = dp[i];
                }else if(dp[i] < mins[next][1]){
                    mins[next][1] = dp[i];
                }
            }

            curr = next;
        }

        return mins[curr][0];
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
    /**
     * Time O(n)  Space O()
     */
    public int minCostIII(int[][] costs) {
        if(null == costs || 0 == costs.length){
            return 0;
        }
        
        int n = costs.length;
        int k = 3;
        
        int[][] dp = new int[k][2]; // RGB, dp[0][0], Red twice; dp[0][1], Red once, the last one is Red
        int tmp;

        int curr = 0;
        int next;
        for(int d = 0; d < n; d++){
            next = curr & 1;

            tmp = dp[0][0];
            dp[0][0] = dp[0][1] + costs[d][0];
            dp[0][1] = Math.min(tmp, Math.min(dp[1][1], dp[2][1])) + costs[d][0];

            tmp = dp[1][0];
            dp[1][0] = dp[1][1] + costs[d][1];
            dp[1][1] = Math.min(tmp, Math.min(dp[0][1], dp[2][1])) + costs[d][1];


            tmp = dp[2][0];
            dp[2][0] = dp[2][1] + costs[d][2];
            dp[2][1] = Math.min(tmp, Math.min(dp[0][1], dp[1][1])) + costs[d][2];

            curr = next;
        }
        
        return Math.min(Math.min(Math.min(dp[0][0], dp[0][1]), dp[0][2]), Math.min(Math.min(dp[1][0], dp[1][1]), dp[1][2]));
    }
}
