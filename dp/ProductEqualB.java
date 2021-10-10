/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import junit.framework.Assert;

/**
 *
 * You are given a positive integer array A,  A's length is n
 * For each operation you can choose a number from the array and add or subtract 1 from it at the cost of 1.
 *  You are expected to make the product of all these numbers equal to B at the minimum cost.
 * The numbers must be positive integers after each operation.
 * 
 * Example #1
 * input: 12 [1,3,5]
 * output:1
 * Explanation: you can change A[2] to 4,  1 * 3 * 4 = 12
 * 
 * Example #1
 * input: 14 [1,3,5,13]
 * output:7
 * Explanation:  1 * (3 - 2) * (5 - 4) * (13 + 1) = 14
 * 
 */
public class ProductEqualB {
    /**
     * @param B: the all Ai product equal to B
     * @param A: the positive int array
     * @return: return the minium cost 
     */

    public int getMinCost(int B, int[] A) {
        
        //find out all factors of B
        List<Integer> factors = new LinkedList<>();
        for(int f = 1; f <= B; f++){  
            if( B % f == 0){
                factors.add(f);
            }
        }
        int n = factors.size();

        //
        int[][] minCosts = new int[2][n];
        Arrays.fill(minCosts[0], Integer.MAX_VALUE);
        minCosts[0][0] = 0;

        int x, y;
        for(int i = 0, p = 0, q = 1; i < A.length; i++, p ^= 1, q ^= 1){
            for(int j = 0; j < n; j++){
                y = factors.get(j);
                minCosts[q][j] = Integer.MAX_VALUE;
                for(int k = 0; k <= j; k++){  
                    x = factors.get(k);  // x * ( A[i] + diff ) = y
                    if(y % x != 0 || minCosts[p][k] == Integer.MAX_VALUE){
                        continue;
                    }

                    minCosts[q][j] = Math.min(minCosts[q][j], minCosts[p][k] + Math.abs(y / x - A[i]) );
                }
            }
        }

        return minCosts[A.length%2][n - 1];
    }
    
    public int getMinCost_n(int B, int[] A) {
         //find out all factors of B
        int[] factors = new int[200];
        int n = 0;
        for(int f = 1; f <= B; f++){  
            if( B % f == 0){
                factors[n++]= f;
            }
        }

        //
        int[][] minCosts = new int[2][n];
        Arrays.fill(minCosts[0], Integer.MAX_VALUE);
        minCosts[0][0] = 0;

        for(int i = 0, p = 0, q = 1; i < A.length; i++, p ^= 1, q ^= 1){
            for(int j = 0; j < n; j++){
                minCosts[q][j] = Integer.MAX_VALUE;
                for(int k = 0; k <= j; k++){  
                    // factors[k] * ( A[i] + diff ) = factors[j]
                    if(factors[j] % factors[k] != 0 || minCosts[p][k] == Integer.MAX_VALUE){
                        continue;
                    }

                    minCosts[q][j] = Math.min(minCosts[q][j], minCosts[p][k] + Math.abs(factors[j] / factors[k] - A[i]) );
                }
            }
        }

        return minCosts[A.length%2][n - 1];
    }
        
    
    public static void main(String[] args){
        ProductEqualB sv = new ProductEqualB();
        
        Assert.assertEquals(7, sv.getMinCost(14, new int[]{1, 3, 5, 13}));
    }
}
