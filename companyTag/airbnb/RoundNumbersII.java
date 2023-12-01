/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.Arrays;
import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/1800
 *
 * Given an array of A, a non-negative integer target. Each decimal in A needs to be operated by ceil or floor, and
 * finally an integer array is obtained, requiring the sum of all integers that are in the array to be equal to target,
 * and requiring adjustments sum of the decimal array is minimum. 
 * Such as ceil(1.2),adjustment is 0.8; floor(1.2),adjustment is 0.2. return the integer array.
 *
 * Notes:
 *   1.1<=A.length<=300
 *   2.0<=target<=15000
 *   3.0.0<=A[i]<=100.0
 *   4.Datas guarantees the existence of answers.
 * 
 * Example 1:
 * Input：A=[1.2, 1.7],target=3
 * Output：[1, 2] 
 * Explanation：1.2->1, 1.7->2.
 * 
 * Example 2:
 * Input：A=[2.4, 2.5],target=5
 * Output：[2, 3]
 * Explanation：2.4->2, 2.5->3.
 * 
 * Thoughts:
 *   Refer to the thought on RoundNumbers. 
 *   Define S as the sum of all integers, S is minimal when all integers are floor, S is maximum when all integers are ceil.
 *   The target has to be in [S_min, S_max], 
 *  
 *   The x = (target - S_min) means how many integer are changed from floor to ceil.
 *   
 *   Define the adjustments sum of the decimal array as R = sum(|Y_i - X_i|), 
 *   Define X = {x1, x2, x3, ...} y_i = floor(x_i), Y = {y1, y2, y3}, d_i = x_i - y_i, {d1, d2, d3} = {x1-y1, x2-y2, x3-y3}
 * 
 *   if x == 2, 0 <= d1 <= d2 <= d3 <= 1
 *   min(R2) = R0 + 1 - 2 * (d2 + d3)  or  min(R2) = min(R1) + 1 - 2 * d2
 * 
 */
public class RoundNumbersII {
    
    /**
     * 
     * @param prices 
     * @param target
     * @return a integer array that sum is target, and the adjustments sum of the decimal array is minimum
     */
    public int[] roundNumber(double[] prices, int target) {
        int n = prices.length;
        int[] result = new int[n];
        
        Node[] nodes = new Node[n];
        int minSum = 0;
        for(int i = 0; i < n; i++){
            result[i] = (int)Math.floor(prices[i]);
            
            minSum += result[i];
            
            nodes[i] = new Node(prices[i] - result[i], i);
        }
        
        int m = target - minSum;
        
        if(m == 0){
            return result;
        }
        
        Arrays.sort(nodes, (a, b) -> Double.compare(b.gap, a.gap));
                
        for(int i = 0; i < m; i++){
            result[nodes[i].id]++;
        }
        
        return result;
    }
    
    class Node{
        int id; //the index in the prices
        double gap; // price[i] - floor(price[i])
        
        Node(double diff, int id){
            this.gap = diff;
            this.id = id;
        }
    }
    
    public static void main(String[] args){
        double[][][] inputs = {
            {
                {1.2, 1.7},
                {3}
            },
            {
                {2.4, 2.5},
                {5}
            }
            
        };
        
        int[][] expects = {
            {1, 2},
            {2, 3}
        };
        
        
        RoundNumbersII sv = new RoundNumbersII();
        
        for(int i = 0; i < expects.length; i++){
            
            System.out.println(String.format("\n prices: %s, \ttarget = %d", Arrays.toString(inputs[i][0]), (int)inputs[i][1][0] ));
            
            Assert.assertArrayEquals("roundNumber ", expects[i], sv.roundNumber(inputs[i][0], (int)inputs[i][1][0] ));
            
        }
        
    }
    
}
