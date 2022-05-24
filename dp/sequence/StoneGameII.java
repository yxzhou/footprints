/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dp.sequence;

import java.util.Arrays;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/593
 *
 * There is a game of rocks. At the beginning of the game, the players pick n piles of stones and form a circle.
 *
 * The goal is to merge the stones in one pile observing the following rules:
 *
 * At each step of the game,the player can merge two adjacent piles to a new pile. The score is the number of stones in
 * the new pile. You are to determine the minimum of the total score.
 *
 *
 * Example 1: 
 * Input: [1,1,4,4] 
 * Output:18 
 * Explanation: 
 * 1. Merge the first two piles => [2, 4, 4], score +2 
 * 2. Merge the first two piles => [6, 4]，score +6 
 * 3. Merge the last two piles => [10], score +10
 *
 * Example 2: 
 * Input: [1, 1, 1, 1] 
 * Output:8 
 * Explanation: 
 * 1. Merge the first two piles => [2, 1, 1], score +2 
 * 2. Merge the last two piles => [2, 2]，score +2 
 * 3. Merge the last two piles => [4], score +4
 *
 */
public class StoneGameII {
    /**
     * DP
     * 
     * Example A = [a, b, c]
     * Define f(a, b, c) as the minimum of the total score. 
     * f(a, b, c) = 
     *   min( 
     *        f(a) + f(b, c) + sum(a, b, c), 
     *        f(b) + f(c, a) + sum(a, b, c), 
     *        f(c) + f(a, b) + sum(a, b, c), 
     *      )
     * 
     *  convert [a, b, c] to [a, b, c, a, b, c]
     *    f_circle(a, b, c) = min( f_line(a, b, c), f_line(b, c, a), f_line(c, a, b) )  
     * 
     * @param A: An integer array
     * @return the minimum of the total score
     */
    public int stoneGame2(int[] A) {
        if(A == null || A.length == 0){
            return 0;
        }

        int n = A.length;
        int n2 = n * 2;
        int end = n2 - 1;

        int[] prefixSum = new int[n2];
        for(int i = 0; i < n; i++){
            prefixSum[i + 1] = prefixSum[i] + A[i];
        }
        for(int i = n; i < end; i++){
            prefixSum[i + 1] = prefixSum[i] + A[i - n];
        }

        int[][] f = new int[n2][n2];
        for(int w = 1; w < n; w++){
            for(int i = 0, j = w; j < end; i++, j++){
                f[i][j] = Integer.MAX_VALUE;

                for(int k = i; k < j; k++){
                    f[i][j] = Math.min(f[i][j], f[i][k] + f[k + 1][j] + prefixSum[j + 1] - prefixSum[i] );
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for(int i = 0; i < n; i++){
            min = Math.min(min, f[i][i + n - 1]);
        }
        return min;
    }
    
    public static void main(String[] args){
     
        StoneGameII sv = new StoneGameII();
        
        int[][][] inputs = {
            {null, {0}},
            {{}, {0}},
            {{3,4,3}, {16}},
            {{4, 1, 1, 4}, {18}},
            {
                {1, 1, 1, 1}, 
                {8}
            },
        };
        
        for(int[][] input : inputs){
            System.out.println(String.format("\ninput: %s, expect = %d", Arrays.toString(input[0]), input[1][0] ));
            
            Assert.assertEquals(input[1][0], sv.stoneGame2(input[0]));
        }

    }
    
}
