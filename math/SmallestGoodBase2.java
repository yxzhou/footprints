/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package math;

import junit.framework.Assert;

/**
 *
 * _https://www.lintcode.com/problem/1213
 * 
 * 
 * Thoughts:
 * n = k^0 + k^1 + ... + k^m, 
 *   k >= 2, n >= 3, find the smallest k. 
 * 
 *  s1) brute force
 *    k is from 3 to n
 *    3^0 + 3^1 = 4
 *    3^0 + 3^1 + 3^2 = 13
 *    3^0 + 3^1 + 3^2 + 3^3 = 40
 * 
 *    T(n) = 2*log(2, n) + 3*log(3,n) + ... + (n - 1) * log(n-1, n)
 *    time complexity O(n * n * log(2, n)),  space O(1)   
 * 
 *  s2) 
 *    n = k^0 + k^1 + ... + k^m 
 *    n > k^m,  ln(n) > m * ln(k)
 *    m < log(k, n) < log(2, n),  the n <= 10^18, m < log(2, 10^18) < 60
 *   
 *    k = Math.floor(Math.power(n, 1/m))
 *       
 *    time complexity O(log(2, n) * log(2, n)),  space O(1)  
 * 
 */
public class SmallestGoodBase2 {
    /**
     *
     * @param n: a string
     * @return a string, the smallest good base
     */
    public String smallestGoodBase(String n) {
        long nLong = Long.parseLong(n, 10);
        long k;
        
        long sum;
        long x;
        for(int m = (int)(Math.log(nLong) / Math.log(2)); m > 0 ; m--){
            k = (int)Math.pow(nLong, 1.0/m);
            
            sum = 1;
            x = k;
            while(sum < nLong){
                sum += x;
                x *= k;
            }

            if(sum == nLong){
                return String.valueOf(k);
            }
        }

        return Long.toString(nLong - 1);
    }

    public String smallestGoodBase2(String n) {
        long nLong = Long.parseLong(n, 10);
        int k;
        
        long sum;
        long x;
        for(int m = (int)(Math.log(nLong) / Math.log(2)); m > 1 ; m--){
            k = (int)Math.pow(nLong, 1.0/m); // note it's 1.0/m instead of 1/m
            
            sum = 1;
            x = 1;
            for(int i = 0; i < m; i++){
                x *= k;
                sum += x;
            }

            if(sum == nLong){
                return Integer.toString(k);
            }
        }

        return Long.toString(nLong - 1);
    }
    
    public static void main(String[] args){
        SmallestGoodBase2 sv = new SmallestGoodBase2();
                
        String[][] inputs = {
            {"3", "2"},
            {"4681", "8"},
            {"4678913", "4678912"},
            {"100000000", "99999999"},
            {"200000000", "199999999"},
            {"300000000", "299999999"},
            {
                "1000000000", 
                "999999999"
            },
            {
                "2000000000", 
                "1999999999"
            },
            {
                "3000000000", 
                "2999999999"
            },
            {
                "10000000000", 
                "9999999999"},
            {
                "1000000000000", 
                "999999999999"
            },
            {
                "100000000000000", 
                "99999999999999"
            },
            {
                "1000000000000000000", 
                "999999999999999999"
            },
        };
        
        for(String[] input : inputs){
            Assert.assertEquals("input: " + input[0], input[1], sv.smallestGoodBase(input[0]));
            
            Assert.assertEquals("input: " + input[0], input[1], sv.smallestGoodBase2(input[0]));
        }
    } 
}
