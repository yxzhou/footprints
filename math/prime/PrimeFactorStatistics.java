/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math.prime;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;

/**
 *_https://www.lintcode.com/problem/1295
 * 
 * Given a positive integer N, you need to factorize all integers between (1, N]. Then you have to count the number of
 * the total prime numbers.
 * 
 * 1<N<=100000
 * 
 * Example
 * input：6
 * output：7
 * explain：2=2, 3=3, 4=2*2, 5=5, 6=2*3, the number of prime number : 1+1+2+1+2=7
 */
public class PrimeFactorStatistics {
    /**
     * @param N: a number
     * @return the number of prime numbers.
     */
    public int Count_PrimeNum(int N) {
        List<Integer> primes = new ArrayList<>();
        primes.add(2);

        int result = 0;
        for(int x = 2; x <= N; x++){
            result += primeNum(x, primes);
        }
    
        return result;
    }

    private int primeNum(int x, List<Integer> primes){
        int count = 0;

        for(int prime : primes){
            while(prime <= x && (x % prime == 0)){
                x /= prime;
                count++;
            }

            if(x == 1){
                break;
            }
        }

        if(x != 1){
            count++;
            primes.add(x);
        }

        return count;
    }
    
    /**
     * @param N: a number
     * @return the number of prime numbers.
     */
    public int Count_PrimeNum_n(int N) {
        List<Integer> primes = new ArrayList<>();
        int[] primeNums = new int[N + 1];

        int result = 0;

        for(int x = 2; x <= N; x++){
            for(int prime : primes){
                if( x % prime == 0 ){
                    primeNums[x] = primeNums[x / prime] + 1;
                    break;
                }
            }

            if(primeNums[x] == 0){
                primeNums[x] = 1;
                primes.add(x);
            }

            result += primeNums[x] ;
        }

        return result;
    }
    
    public static void main(String[] args){
        int[][] inputs = {
            {1, 0},
            {2, 1},
            {6, 7},
            {20, 36},
            {100, 239},
            {10000, 31985},
            {100000, 343614}
        };
        
        PrimeFactorStatistics sv = new PrimeFactorStatistics();
        
        for(int i = 0; i < inputs.length; i++){
            System.out.println(String.format("\n Input: %d", inputs[i][0]));
            
            Assert.assertEquals(inputs[i][1], sv.Count_PrimeNum(inputs[i][0]));
            Assert.assertEquals(inputs[i][1], sv.Count_PrimeNum_n(inputs[i][0]));
        }
        
    }
}
