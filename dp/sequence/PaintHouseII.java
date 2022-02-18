/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dp.sequence;

import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/516
 * 
 * There are a row of n houses, each house can be painted with one of the k colors. The cost of painting each house with
 * a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.
 *
 * The cost of painting each house with a certain color is represented by a n x k cost matrix. For example, costs[0][0]
 * is the cost of painting house 0 with color 0; costs[1][2] is the cost of painting house 1 with color 2, and so on...
 * Find the minimum cost to paint all houses.
 *
 * Note: All costs are positive integers.
 *
 * Example 1
 * Input: costs = [[14,2,11],[11,14,5],[14,3,10]] 
 * Output: 10 
 * Explanation: The three house use color [1,2,1] for each house. The total cost is 10. 
 * 
 * Example 2
 * Input: costs = [[5]] 
 * Output: 5 
 * Explanation: There is only one color and one house. \
 * 
 * Challenge Could you solve it in O(nk)
 *
 */
public class PaintHouseII {

    public int minCostII(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0;
        }

        int n = costs.length;
        int k = costs[0].length;

        int[] dp = new int[k]; //default all are 0,  the minimum cost
        int[][] mins = new int[2][2]; //minimum and 2rd minimum of cost
        int curr = 0;
        int next;
        for (int d = 0; d < n; d++) {
            next = curr ^ 1;

            mins[next][0] = mins[next][1] = Integer.MAX_VALUE;
            for (int i = 0; i < k; i++) {
                
                dp[i] = (dp[i] == mins[curr][0] ? mins[curr][1] : mins[curr][0]) + costs[d][i];

                if (dp[i] < mins[next][0]) {
                    mins[next][1] = mins[next][0];
                    mins[next][0] = dp[i];
                } else if (dp[i] < mins[next][1]) {
                    mins[next][1] = dp[i];
                }
            }

            curr = next;
        }

        return mins[curr][0];
    }
    
    
    public static void main(String[] args){
     
        int[][][][] inputs = {
            {
                {{1,2,3},{1,4,6}},
                {{3}}
            },
            {    
                {{14,2,11},{11,14,5},{14,3,10}},
                {{10}}
            },
            {
                {{1,5,6},{14,15,5},{4,3,3},{15,15,9},{9,2,7},{6,5,7},{19,4,4},{6,13,3},{8,16,20},{18,7,9}},
                {{48}}
            },
        
        };
        
        PaintHouseII sv = new PaintHouseII();
        
        for(int[][][] input : inputs){
            System.out.println(String.format("\n%s", Misc.array2String(input[0])));
            
            Assert.assertEquals(input[1][0][0], sv.minCostII(input[0]));
        }
        
    }
    
}
