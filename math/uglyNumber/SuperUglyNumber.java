/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package math.uglyNumber;

import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/518
 * 
 * Write a program to find the nth super ugly number.
 *
 * Super ugly numbers are positive numbers whose all prime factors are in the given prime list primes of size k. 
 * 
 * For example, [1, 2, 4, 7, 8, 13, 14, 16, 19, 26, 28, 32] is the sequence of the first 12 super ugly numbers given 
 * primes = [2, 7, 13, 19] of size 4.
 *
 * Note: 
 *   (1) 1 is a super ugly number for any given primes. 
 *   (2) The given numbers in primes are in ascending order. 
 *   (3) 0 < k ≤ 100, 0 < n ≤ 106, 0 < primes[i] < 1000.
 *
 * Example 1:
 * Input: n = 6, [2,7,13,19] 
 * Output: 13 
 * 
 * Example 2:
 * Input: n = 11, [2,3,5] 
 * Output: 15
 *
 */
public class SuperUglyNumber {

    /**
     * @param n: a positive integer
     * @param primes: the given prime list
     * @return the nth super ugly number
     */
    public int nthSuperUglyNumber(int n, int[] primes) {
        
        int k = primes.length;

        long[][] nums = new long[k][n];
        for(int i = 0; i < k; i++){
            nums[i][0] = primes[i];
        }

        int[] heads = new int[k];
        int[] tails = new int[k];

        long curr = 1l;
        int p;  // the index of min ugly number

        for(int i = 1; i < n; i++){
            p = 0;
            curr = nums[p][heads[p]];
            for(int j = 1; j < k; j++){
                if( curr > nums[j][heads[j]] ){
                    p = j;
                    curr = nums[j][heads[j]];
                }
            }

            for(int j = p; j < k; j++){
                nums[j][++tails[j]] = curr *  primes[j];
            }

            heads[p]++;
        }

        return (int)curr;
    }

    public int nthSuperUglyNumber_2(int n, int[] primes) {
        if(null == primes || 0 == primes.length || n < 1){
            return 1;
        }

        int k = primes.length;
        int[] indexes = new int[k];
        int[] nexts = new int[k];

        int[] result = new int[n];
        result[0] = 1;

        int min;
        for (int i = 1; i < n; i++) {
            min = Integer.MAX_VALUE;
            for (int j = 0; j < k; j++) {
                nexts[j] = primes[j] * result[indexes[j]];
                min = Math.min(min, nexts[j]);
            }

            result[i] = min;

            // update the index
            for (int j = 0; j < k; j++) {
                if ( min == nexts[j]) {
                    indexes[j]++;
                }
            }
        }

        return result[n - 1];
    }
    

    public static void main(String[] args) {
        
        int[][] inputs = {
            {1, 1},
            {2, 2},
            {3, 3},
            {4, 4},
            {5, 5},
            {9, 10},
            {11, 15},
            {19, 32},
            {23, 48},
            {29, 75},
            {41, 150},
            {43, 162},
            {47, 216},
            {1690, 2123366400},
            
        };
        
        int[] primes = {2,3,5};
        //int[] primes = {3,5,7}; //{3,5,7,11,19,23,29,41,43,47};

        SuperUglyNumber sv = new SuperUglyNumber();
        
        for(int[] input : inputs){
            System.out.println(String.format("n = %d, expect : %d", input[0], input[1] ));
            
            Assert.assertEquals(input[1], sv.nthSuperUglyNumber(input[0], primes) );
            Assert.assertEquals(input[1], sv.nthSuperUglyNumber_2(input[0], primes) );
        }

    }
    
}
