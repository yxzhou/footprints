/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dp.sequence;

/**
 *
 * There are a row of house, each house can be painted with one of the three colors: red, blue or green. The cost of
 * painting each house with a certain color is different. You have to paint all the house such that no three adjacent
 * houses have the same color.
 *
 * The cost of painting each house with a certain color is represented by a n x 3 cost matrix. For example, costs[0][0]
 * is the cost of painting house 0 with color red; costs[1][2] is the cost of painting house 1 with color green, and so
 * on... Find the minimum cost to paint all houses.
 *
 * Note: All costs are positive integers.
 *
 */
public class PaintHouseIII {
    
    /**
     * Time O(n)  Space O()
     */
    public int minCostIII(int[][] costs) {
        if (null == costs || 0 == costs.length) {
            return 0;
        }

        /**
         * dp[i][j][k], i is 0 or 1, j is RGB, k is 0 or 1. 
         * 
         * j = 0 and k = 0, means the last one is Red; 
         * j = 0 and k = 1, means the last two is Red
         * 
         */
        int[][][] dp = new int[2][3][2]; 

        int p = 0;
        int q;
        int[][] curr;
        int[][] next;
        for (int[] cost : costs) {
            q = p & 1;
            curr = dp[p];
            next = dp[q];

            next[0][1] = curr[0][0] + cost[0];
            next[0][0] = Math.min(Math.min(curr[1][0], curr[1][1]), Math.min(curr[2][0], curr[2][1]) ) + cost[0];

            next[1][1] = curr[1][0] + cost[1];
            next[1][1] = Math.min(Math.min(curr[2][0], curr[2][1]), Math.min(curr[0][0], curr[0][1])) + cost[1];

            next[2][1] = curr[2][0] + cost[2];
            next[2][0] = Math.min(Math.min(curr[2][0], curr[2][1]), Math.min(curr[0][1], curr[1][1])) + cost[2];

            p = q;
        }

        curr = dp[p];
        return Math.min( Math.min(Math.min(curr[0][0], curr[0][1]), Math.min(curr[1][0], curr[1][1])), Math.min(curr[2][0], curr[2][1]) );
    }
    
}
