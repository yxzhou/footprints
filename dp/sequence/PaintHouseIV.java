/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dp.sequence;

import java.util.Arrays;
import org.junit.Assert;
import util.Misc;

/**
 * _https://leetcode.com/problems/paint-house-iii/
 * 
 *
 * There is a row of m houses in a small city, each house must be painted with one of the n colors (labeled from 1 to
 * n), some houses that have been painted last summer should not be painted again.
 *
 * A neighborhood is a maximal group of continuous houses that are painted with the same color.
 *
 * For example: houses = [1,2,2,3,3,2,1,1] contains 5 neighborhoods [{1}, {2,2}, {3,3}, {2}, {1,1}]. Given an array
 * houses, an m x n matrix cost and an integer target where:
 *
 * houses[i]: is the color of the house i, and 0 if the house is not painted yet. cost[i][j]: is the cost of paint the
 * house i with the color j + 1. Return the minimum cost of painting all the remaining houses in such a way that there
 * are exactly target neighborhoods. If it is not possible, return -1.

    Example 1:
    Input: houses = [0,0,0,0,0], cost = [[1,10],[10,1],[10,1],[1,10],[5,1]], m = 5, n = 2, target = 3
    Output: 9
    Explanation: Paint houses of this way [1,2,2,1,1]
    This array contains target = 3 neighborhoods, [{1}, {2,2}, {1,1}].
    Cost of paint all houses (1 + 1 + 1 + 1 + 5) = 9.
    * 
    Example 2:
    Input: houses = [0,2,1,2,0], cost = [[1,10],[10,1],[10,1],[1,10],[5,1]], m = 5, n = 2, target = 3
    Output: 11
    Explanation: Some houses are already painted, Paint the houses of this way [2,2,1,2,2]
    This array contains target = 3 neighborhoods, [{2,2}, {1}, {2,2}]. 
    Cost of paint the first and last house (10 + 1) = 11.
    * 
    Example 3:
    Input: houses = [3,1,2,3], cost = [[1,1,1],[1,1,1],[1,1,1],[1,1,1]], m = 4, n = 3, target = 3
    Output: -1
    Explanation: Houses are already painted with a total of 4 neighborhoods [{3},{1},{2},{3}] different of target = 3.


    Constraints:

    m == houses.length == cost.length
    n == cost[i].length
    1 <= m <= 100
    1 <= n <= 20
    1 <= target <= m
    0 <= houses[i] <= n
    1 <= cost[i][j] <= 104
 * 
 * Thoughts:
 * 
 *    m1) top-down,  dfs + memorization
 *    m2) bottom-up, 
 */
public class PaintHouseIV {
    int[] houses;
    int[][] costs;
    int m;
    int n;
    int target;
    int[][][] cache;
    
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
        this.houses = houses;
        this.costs = cost;
        this.m = m;
        this.n = n;
        this.target = target;
        cache = new int[m][n + 1][target + 1];

        int result = dfs(0, 0, 0);
        
        return result == Integer.MAX_VALUE? -1 : result;
    }
    
    private int dfs(int i, int color, int groupNumber){
        if(i == m){            
            return groupNumber == target ? 0 : -1;
        }
    
        if(groupNumber > target){
            return -1;
        }
        
        if(cache[i][color][groupNumber] != 0){
            return cache[i][color][groupNumber];
        }
        
        int min = Integer.MAX_VALUE;
        int price;
        if(houses[i] > 0){
            min = dfs(i + 1, houses[i], groupNumber + ( houses[i] == color? 0 : 1 ) );
            
        }else{
            for(int c = 1; c <= n; c++){
                price = dfs(i + 1, c,  groupNumber + (color == c? 0 : 1 ) );
                
                if(price >= 0){
                    min = Math.min( min,  costs[i][c - 1] + price );    
                }
                
            }
        }

        cache[i][color][groupNumber] = (min == Integer.MAX_VALUE? -1 : min);
        return cache[i][color][groupNumber];
    }
    
    
    public static void main(String[] args){
        int[][][][] inputs = {
            {
                {{1,10},{10,1},{10,1},{1,10},{5,1}}, {{0,0,0,0,0},{5, 2, 3}}, {{9}}
            },
            {
                {{1,10},{10,1},{10,1},{1,10},{5,1}}, {{0,2,1,2,0}, {5, 2, 3}}, {{11}}
            },
            {
                {{1,1,1},{1,1,1},{1,1,1},{1,1,1}}, {{3,1,2,3}, {4, 3,  3}}, {{-1}}
            }
        };
        
        PaintHouseIV sv = new PaintHouseIV();
        PaintHouseIV2 sv2 = new PaintHouseIV2();
        
        for(int[][][] input : inputs){
            System.out.println(String.format("\nhouses: %s, costs: [%s], m = %d, n = %d, target = %d ", 
                    Arrays.toString(input[1][0]), 
                    Misc.array2String(input[0], true),
                    input[1][1][0], input[1][1][1], input[1][1][2]));
            
            Assert.assertEquals(input[2][0][0], sv.minCost(input[1][0], input[0], input[1][1][0], input[1][1][1], input[1][1][2]));
            
            Assert.assertEquals(input[2][0][0], sv2.minCost(input[1][0], input[0], input[1][1][0], input[1][1][1], input[1][1][2]));
        }
    }
}
