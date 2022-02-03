/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp;

import junit.framework.Assert;
import util.TimeCost;

/**
 *
 * We have two types of tiles: a 2x1 domino shape, and an "L" tromino shape. These shapes may be rotated.
 *   XX  <- "|" or "--"  domino
 *   XX  <- "L" tromino
 *   X
 * 
 * Given N, how many ways are there to tile a 2 x N board? Return your answer modulo 10^9 + 7.
 * (In a tiling, every square must be covered by a tile. Two tilings are different if and only if there are two 4-directionally adjacent cells on the board such that exactly one of the tilings has both squares occupied by a tile.)
 * 
 * N will be in range [1, 1000].
 * 
 * Example 1:
 * Input: 3
 * Output: 5
 * Explanation: The five different ways are listed below, different letters indicates different tiles:
 *   1. XYZ  2. XXZ  3. XYY  4. XXY  5. XYY
 *      XYZ     YYZ     XZZ     XYY     XXY
 * 
 * Example 2:
 * Input: 1
 * Output: 1
 * 
 * Solution #1:  DP 
 * 1) when N = 1, only one ways
 *    1. X
 *       X
 * 2) when N = 2, there are two ways
 *    1. XY  2. XX
 *       XY     YY
 * 3) when N = 3, there are five ways, includes:
 *      3.1 Start from 
 *        X     XYZ   XYY
 *        X     XYZ   XZZ
 *      it's f(2), 
 *      
 *      3.2 Start from 
 *        XX   XXZ
 *        YY   YYZ
 *      it's f(1)
 * 
 *      3.3 a whole shape can't divided by vertical line. 
 *        XXY  XYY
 *        XYY  XXY
 *      it's 2. these 2 are same in the horizontal mirror line. 
 * 
 * So there are two kinds of 2*M shape, 
 *   one is dividable by vertical line, the other is not dividable by vertical line. 
 *    define f[m] as all the ways to tile a 2*m board
 *    define w[m] as all the ways to tile a 2*m board that are not dividable by vertical line. 
 *   f[1] = 1  w[1] = 1
 *   f[2] = 2  w[2] = 1
 *   
 *   f[3] = w[1]*f[2] + w[2]*f[1] + w[3],   w[3] = 2
 *   f[4] = w[1]*f[3] + w[2]*f[2] + w[3]*f[1] + w[4],  
 * 
 *     w[4] = 2, it's 
 *        XZZY     
 *        XXYY  and  the other shape in the horizontal mirror line.
 *    
 *   f[i] = 2 + f[i - 1] + f[i-2] + 2 * f[i - 3] + ---
 * 
 * Solution #2: DP
 *   solution #1 is focus on the START part, Here it thinks on END part. 
 *   To a 2*m board that 2 is height and m is width. There are 3 kinds of shape.
 *   1)  ...X    2) ...X     3) ...        
 *       ...X       ...         ...X
 *   define f[i][0] as the status #1, the end is perfect. the upper and bottom both lines are same.
 *   define f[i][1] as the status #2, the upper line has one extra cell more than the bottom line. 
 *   define f[i][2] as the status #3, the bottom line has one extra cell more than the upper line.  
 * 
 *   So:
 *   f[0][0] = f[1][0] = f[1][1] = f[1][2] = 1
 *   f[i][0] = f[i - 1][0] + f[i - 2][0] + f[i - 2][1] + f[i - 2][2] 
 *     ( to f[i - 1][0], add one domino "|" ; to f[i - 2][0], add two dominos "==" ; to f[i - 2][1] and f[i - 2][2], add a tromino "L"  )
 *   f[i][1] = f[i - 1][0] + f[i - 1][2]
 *     ( to f[i - 1][0], add a tromino "L" ; to f[i - 1][2], add a dominos "--" on the upper line )
 *   f[i][2] = f[i - 1][0] + f[i - 1][1]
 *     ( to f[i - 1][0], add a tromino "L" ; to f[i - 1][1], add a dominos "--" on the bottom line )
 * 
 */
public class DominoTrominoTiling {
    /**
     * Time O(n*n), Space O(n)
     * 
     * @param N: a integer
     * @return a integer
     */
    public int numTilings_DPI(int N) {
        if(N < 3){
            return N;
        }

        final int MOD = 1000000007;

        int[] f = new int[N + 1]; 
        f[1] = 1; 
        f[2] = 2;

        for(int i = 3; i <= N; i++){
            f[i] = 2;
            for(int k = 1; k < i; k++){
                f[i] += (k > 2 ? 2 : 1) * f[i - k] % MOD;
                f[i] %= MOD;
            }
        }

        return f[N];
    }
    
    /**
     * Time O(n), Space O(n)
     * 
     * @param N: a integer
     * @return a integer
     */
    public int numTilings_DPII(int N) {
        final int MOD = 1000000007;
        
        int[][] f = new int[N + 1][3];
        f[0][0] = f[1][0] = f[1][1] = f[1][2] = 1;
        
        for(int i = 2; i <= N; i++){
            f[i][0] = ((f[i - 1][0] + f[i - 2][0]) % MOD + (f[i - 2][1] + f[i - 2][2]) % MOD) % MOD;
            f[i][1] = (f[i - 1][0] + f[i - 1][2]) % MOD;
            f[i][2] = (f[i - 1][0] + f[i - 1][1]) % MOD;
        }
        
        return f[N][0];
    }
    
    /**
     * optimize numTilings_DPII
     * Time O(n), Space O(1)
     * 
     * @param N
     * @return 
     */
    public int numTilings_DPII_2(int N) {
        final int MOD = 1000000007;
        
        int[][] f = new int[3][3];
        f[0][0] = f[1][0] = f[1][1] = f[1][2] = 1;
        
        int i1; 
        int i2; 
        int i3; 
        for(int i = 2; i <= N; i++){
            i1 = (i - 1) % 3;
            i2 = (i - 2) % 3;          
            i3 = i % 3;
        
            f[i3][0] = ((f[i1][0] + f[i2][0]) % MOD + (f[i2][1] + f[i2][2]) % MOD) % MOD;
            f[i3][1] = (f[i1][0] + f[i1][2]) % MOD;
            f[i3][2] = (f[i1][0] + f[i1][1]) % MOD;
        }
        
        return f[N%3][0];
    }
    
    /**
     * @param N: a integer
     * @return return a integer
     */
    public int numTilings_x(int N) {
        if(N < 3){
            return N;
        }

        final int MOD = 1_000_000_007;

        int[][] f = new int[3][2];

        f[0][0] = 1;
        f[0][1] = 2;
        f[1][0] = 2;
        f[1][1] = 4;

        int n = 2;
        for( int i, j, k; n < N; n++){
            k = (n - 2) % 3;
            j = (n - 1) % 3;
            i = n % 3;

            f[i][0] = ((f[j][0] + f[k][0]) % MOD + f[k][1])%MOD;
            f[i][1] = ((f[j][0] * 2) % MOD + f[j][1]) % MOD;
        }

        return f[(N - 1)%3][0];
    }
    
    public static void main(String[] args){
        
        int[][] inputs = {
            //{N, expect}
            {1, 1},
            {2, 2},
            {3, 5},
            {4, 11}, 
            {1000, 979232805}
        };
        
        DominoTrominoTiling sv = new DominoTrominoTiling();

        for (int[] input : inputs) {
            System.out.println(String.format("\nN = %d,  expect: %d", input[0], input[1]));

            Assert.assertEquals(input[1], sv.numTilings_x(input[0]));
            Assert.assertEquals(input[1], sv.numTilings_DPII_2(input[0]));
        }

        System.out.println("\n---performance test--");
        TimeCost tc = TimeCost.getInstance();
        tc.init();

        for (int k = 19999; k <= 39999; k++) {
            sv.numTilings_DPII_2(k);
        }
        System.out.println("\nThe numTilings_DPII_2, timeCost:" + tc.getTimeCost());

        for (int k = 19999; k <= 39999; k++) {
            sv.numTilings_x(k);
        }
        System.out.println("\nThe numTilings_x, timeCost:" + tc.getTimeCost());
        
    }
    
}
