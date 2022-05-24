/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dp.sequence;

import java.util.Arrays;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/476
 * 
 *
 * There is a stone game.At the beginning of the game the player picks n piles of stones in a line.
 *
 * The goal is to merge the stones in one pile observing the following rules:
 *
 * At each step of the game,the player can merge two adjacent piles to a new pile. The cost of each combination is the
 * sum of the weights of the two piles of stones combined. Please find out the minimum cost of merging.
 *
 * Example 1: 
 * Input: [3, 4, 3] 
 * Output: 17
 * Example 2:
 * Input: [4, 1, 1, 4]
 * Output: 18
 * Explanation:
 *   1. Merge second and third piles => [4, 2, 4], score = 2
 *   2. Merge the first two piles => [6, 4]，score = 8
 *   3. Merge the last two piles => [10], score = 18
 * 
 */
public class StoneGame {
    /**
     * Greedy, every time merge the pair whose sum is minimum
     * 
     * Greedy is wrong !!
     * 
     * @param a: An integer array
     * @return: An integer
     */
    public int stoneGame(int[] a) {
        if(a == null ){
            return 0;
        }

        int n = a.length;
        int[][] stones = new int[2][n];
        System.arraycopy(a, 0, stones[0], 0, n);

        int result = 0;

        int sum;
        int minSum;
        int minIndex = 0;

        int curr = 0; 
        int next;
        for(int i = n; i > 1; i--){
            next = (curr ^ 1);

            minSum = Integer.MAX_VALUE;
            for(int j = 1; j < i; j++){
                sum = stones[curr][j] + stones[curr][j - 1];

System.out.println(String.format(" -%d- %d + %d = %d", i, stones[curr][j - 1], stones[curr][j], sum) );    
                if(sum < minSum){
                    minSum = sum;
                    minIndex = j;
                }
            }

            result += minSum;
            System.arraycopy(stones[curr], 0, stones[next], 0, minIndex - 1);
            stones[next][minIndex - 1] = minSum;
            System.arraycopy(stones[curr], minIndex + 1, stones[next], minIndex, i - minIndex - 1);

            curr = next;
        }

        return result;
    }
    
    /**
     * DP. 
     * 
     * Define f[i][j] as the minimum cost of merging, n as the piles of stones 
     * Init state, f[i][i] = 0
     * Final state, f[0][n - 1]
     * Formulate state relationship: f[i][j] = min( f[i][k] + f[k + 1][j] + sum[i][j])   k is [i, j)   
     *    
     * @param a: An integer array
     * @return the minimum cost of merging
     */
    public int stoneGame_DP(int[] a) {
        if(a == null || a.length == 0 ){
            return 0;
        }

        int n = a.length;

        int[] prefixSum = new int[n + 1];
        for(int i = 0; i < n; i++){
            prefixSum[i + 1] = prefixSum[i] + a[i];
        }

        int[][] f = new int[n][n];
        
        // for(int i = 0; i < n; i++){
        //     Arrays.fill(f[i], Integer.MAX_VALUE);
        //     f[i][i] = 0;
        // }        

        for(int w = 1; w < n; w++){
            for(int l = 0, r = w; r < n; l++, r++){
                f[l][r] = Integer.MAX_VALUE;

                for(int k = l; k < r; k++){
                    f[l][r] = Math.min(f[l][r], f[l][k] + f[k + 1][r] + (prefixSum[r + 1] - prefixSum[l]) );
                }
            }
        }

        return f[0][n - 1];
    }
    
    public static void main(String[] args){
     
        StoneGame sv = new StoneGame();
        
        int[][][] inputs = {
            {null, {0}},
            {{}, {0}},
            {{3,4,3}, {17}},
            {{4, 1, 1, 4}, {18}},
            {
                {87,328,580,738,637,837,822,780,481,244,359}, 
                {19692}
            },
            {
                {87,328,580,738,637,837,822,780,481,244,359,732,43,275,681,254,857,269,407,597,500,30,356,356,612,190,503,823,599,39,648,420,477,306,236,546,642,737,180,522,228,431,471,25,746}, 
                {110922}
            },
        };
        
        for(int[][] input : inputs){
            System.out.println(String.format("\ninput: %s, expect = %d", Arrays.toString(input[0]), input[1][0] ));
            
            Assert.assertEquals(input[1][0], sv.stoneGame_DP(input[0]));
        }

    }
}
