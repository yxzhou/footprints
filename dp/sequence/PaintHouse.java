package dp.sequence;

import java.util.Arrays;

/**
 * _https://www.lintcode.com/problem/515
 * 
 * There are a row of n houses, each house can be painted with one of the three colors: red, blue or green. The cost of
 * painting each house with a certain color is different. You have to paint all the houses such that no two adjacent
 * houses have the same color, and you need to cost the least. Return the minimum cost.
 *
 * The cost of painting each house with a certain color is represented by a n x 3 cost matrix. For example, costs[0][0]
 * is the cost of painting house 0 with color red; costs[1][2] is the cost of painting house 1 with color green, and so
 * on... Find the minimum cost to paint all houses.
 *
 * Note: All costs are positive integers.
 *
 * Example 1:
 * Input: [[14,2,11],[11,14,5],[14,3,10]] 
 * Output: 10 
 * Explanation: Paint house 0 into blue, paint house 1 into green, paint house 2 into blue. Minimum cost: 2 + 5 + 3 = 10.
 *
 *
 * Example 2:
 * Input: [[1,2,3],[1,4,6]] 
 * Output: 3
 *
 *
 */

public class PaintHouse {
    
    /**
     * Time O(n)  Space O(1)
     *
     * @param costs: n x 3 cost matrix
     * @return An integer, the minimum cost to paint all houses
     */
    public int minCost(int[][] costs) {
        if(costs == null){
            return 0;
        }

        int[] curr = new int[3]; // store the minimum cost when the last paint color is {red, blue, green} 
        int[] next = new int[3];

        int[] tmp;
        for(int[] cost : costs){
            next[0] = Math.min(curr[1], curr[2]) + cost[0];
            next[1] = Math.min(curr[2], curr[0]) + cost[1];
            next[2] = Math.min(curr[0], curr[1]) + cost[2];

            tmp = curr;
            curr = next;
            next = tmp;
        }

        return Math.min(curr[0], Math.min(curr[1], curr[2]) );
    }

    /**
     * Time O(n)  Space O(1)
     */
    public int minCost_1(int[][] costs) {
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
        if(null == costs || 0 == costs.length){
            return 0;
        }
        
        int[][] dp = new int[2][3];
        int curr = 0;
        int next;
        for(int d = 0; d < costs.length; d++){
            next = curr ^ 1;
            
            for(int color = 0; color < 3; color++){
                dp[next][color] = Math.min(dp[curr][(color + 1) % 3], dp[curr][(color + 2) % 3]) + costs[d][color];
            }

            curr = next;
        }

        return Math.min(Math.min(dp[curr][0], dp[curr][1]), dp[curr][2]);
    }
    
    
}
