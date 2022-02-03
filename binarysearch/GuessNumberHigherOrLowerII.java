/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package binarysearch;

import junit.framework.Assert;
import util.TimeCost;

/**
 * _https://www.lintcode.com/problem/666
 *
 * We are playing the Guess Game. The game is as follows: I pick a number from 1 to n. You have to guess which number I
 * picked. Every time you guess wrong, I'll tell you whether the number I picked is higher or lower. However, when you
 * guess a particular number x, and you guess wrong, you pay $x. You win the game when you guess the number I picked.
 * 
 * Given a particular n ≥ 1, find out how much money you need to have to guarantee a win.
 *
 * Example1 
 * Input: 10  Output: 16 
 * Explanation: 
 * Given n = 10, I pick 2. 
 *   First round: You guess 7, I tell you that it's lower. You pay $7. 
 *   Second round: You guess 3, I tell you that it's lower. You pay $3. 
 *   Third round: You guess 1, I tell you that it's higher. You pay $1. 
 *   Game over. 1 is the number I picked. You end up paying $7 + $3 + $1 = $11.
 *
 * Given n = 10, I pick 4. 
 *   First round: You guess 7, I tell you that it's lower. You pay $7. 
 *   Second round: You guess 3, I tell you that it's higher. You pay $3. 
 *   Third round: You guess 5, I tell you that it's lower. You pay $5. 
 *   Game over.  4 is the number I picked. You end up paying $7 + $3 + $5 = $15.
 *
 * Given n = 10, I pick 8. 
 *   First round: You guess 7, I tell you that it's higher. You pay $7. 
 *   Second round: You guess 9, I tell you that it's lower. You pay $9. 
 *   Game over. 8 is the number I picked. You end up paying $7 + $9 = $16.
 *
 * So given n = 10, the answer is 16.
 *
 * Example2 
 * Input: 5  Output: 6
 * 
 * 
 * Thoughts:
 *   1) nobody know the number I picked, so every number [1, n] is possible. 
 *   2) At beginning, it is [1, n-1] for guessing, after picking x, it left[1, x-1] and [x+1, n-1] for guessing.
 *      If it is 2 number for guessing, [x, x+1], it need to guess x, the smaller one
 *      If it is 3 number for guessing, [x - 1, x, x + 1], it need to guess x, the middle one. 
 *      If it is 4 number for guessing, [x, x+1, x+2, x+3]. the best strategy is (x+2)+(x)
 *      If it is 5 number for guessing, [x-2, x-1, x, x+1, x+2] the best strategy is:
 *         m1)pick x first, if lower, it's x + (x - 2), if higher, it's x + (x+1)
 *         m2)pick x+1 first, if lower, it's (x+1)+(x-1), if higher, it's (x+1). 
 *        so the best strategy is m2, and the answer is (x+1)+(x-1). 
 *         
 *      example [1, 2, 3, 4, 5], the best strategy is 4 + 2, the answer is 6
 *   3) to [x, x+1, ..., y-1, y], define f[x][y] as the money to guarantee a win. 
 *      f[x][y] = min( k + max(f[x][k-1], f[k+1][y]) ) ,   k in [x, y]
 *       
 *      when y = x+1, 
 *        f[x][y] = min{ x + max(f[x][x-1], f[x+1][y]), (x+1) + max(f[x][x], f[x+2][y])}
 *                = min{ x, x+1}
 *        f[x][x-1] and f[x+1][x+1] and f[x][x] and f[x+2][x+1] all are 0.
 *      when y = x+2, 
 *        f[x][y] = min{ x + max(f[x][x-1], f[x+1][y]), (x+1) + max(f[x][x], f[x+2][y]), (x+2)+ max(f[x][x+1], f[x+3][y])}
 *                = min{ x + (x+1), (x+1), (x+2) + x }
 *      
 * 
 */
public class GuessNumberHigherOrLowerII {
    
    /**
     * @param n: An integer
     * @return how much money you need to have to guarantee a win
     */
    
    public int getMoneyAmount(int n) {

        int[][] f = new int[n + 2][n + 2];

        for(int len = 2; len <= n; len++){
            for(int l = 1, r = len; r <= n; l++, r++){

                f[l][r] = Integer.MAX_VALUE;
                for(int k = l; k <= r; k++){
                    f[l][r] = Math.min(f[l][r], k + Math.max(f[l][k - 1], f[k + 1][r]));
                }
            }
        }

        return f[1][n];
        
    }
    
    public int getMoneyAmount_2(int n) {

        int[][] f = new int[n + 1][n + 1];

        for(int len = 2; len <= n; len++){
            for(int l = 1, r = len; r <= n; l++, r++){

                if(len < 4){
                    f[l][r] = r - 1;
                }else{
                    f[l][r] = Integer.MAX_VALUE;
                    for(int k = l + 1; k < r; k++){
                        f[l][r] = Math.min(f[l][r], k + Math.max(f[l][k - 1], f[k + 1][r]));
                    }
                }

            }
        }

        return f[1][n];
    }
    
    public static void main(String[] args){
        
        int[][] inputs = {
            {1, 0},
            {2, 1},
            {3, 2},
            {4, 4},
            {5, 6},
            {10, 16},
            {100, 400}
        };
        
        GuessNumberHigherOrLowerII sv = new GuessNumberHigherOrLowerII();
        
        for(int[] input : inputs){
            System.out.println(String.format("\n n = %d, expect: %d", input[0], sv.getMoneyAmount(input[0]) ));
            
            Assert.assertEquals(" -- ", input[1], sv.getMoneyAmount(input[0]) );
            Assert.assertEquals(" -2- ", input[1], sv.getMoneyAmount_2(input[0]) );
        }
        
        System.out.println("\n---performance test--");
        TimeCost tc = TimeCost.getInstance();
        tc.init();

        for (int k = 199; k <= 599; k++) {
            sv.getMoneyAmount(k);
        }
        System.out.println("\nThe numTilings_DPII_2, timeCost:" + tc.getTimeCost());

        for (int k = 199; k <= 599; k++) {
            sv.getMoneyAmount_2(k);
        }
        System.out.println("\nThe numTilings_x, timeCost:" + tc.getTimeCost());
        
    }
    
}
