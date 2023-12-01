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
 * For an integer n, we call k>=2 a good base of n, if all digits of n base k are 1.
 * Now given a string representing n, you should return the smallest good base of n in string format.
 *
 * Notes:
 *   The range of n is [3, 10^18]. 
 *   The string representing n is always valid and no zero at the beginning.
 *
 * Example 1:
 * Input: "3" Output: "2" 
 * Explanation: 3 base 2 is 11. 
 * 
 * Example 2:
 * Input: "4681" Output: "8" 
 * Explanation: 4681 base 8 is 11111. 
 * 
 * Example 3:
 * Input: "1000000000000000000" 
 * Output: "999999999999999999" 
 * Explanation: 1000000000000000000 base 999999999999999999 is 11.
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
 *    time complexity O(n * log(2, n)),  space O(1)   
 * 
 *  s2) 
 *    n = k^0 + k^1 + ... + k^m 
 *    n > k^m
 *    k < log(m, n) < log(2, m)
 *    m = Math.floor(Math.power(n, 1/m))
 * 
 */
public class SmallestGoodBase {
    /**
     *
     * @param n: a string
     * @return a string, the smallest good base
     */
    public String smallestGoodBase(String n) {
        long nLong = Long.parseLong(n, 10);
        long sum;
        long x;
        for(long k = 2; k < nLong; k++){
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

        //It will not be here
        return "";
    }
    
    public String smallestGoodBase2(String n) {
        long nLong = Long.parseLong(n, 10);
        long x;
        for(long k = 2; k < nLong; k++){
            x = nLong;
            while(x > k){
                if(x % k == 1){
                    x /= k;
                }else{
                    break;
                }
            }

            if(x == 1){
                return String.valueOf(k);
            }
        }

        //It will not be here
        return "";
    }
    
    
    public static void main(String[] args){
        SmallestGoodBase sv = new SmallestGoodBase();
                
        String[][] inputs = {
            {"3", "2"},
            {"4681", "8"},
            {"4678913", "4678912"},
            {"100000000", "99999999"},
            {"200000000", "199999999"},
            {"300000000", "299999999"},
            {"1000000000", "999999999"},
            {"2000000000", "1999999999"},
            {"3000000000", "2999999999"},
            //{"10000000000", "9999999999"},
            //{"1000000000000", "9999999999"},
            //{"100000000000000", "999999999999999999"},
            //{"1000000000000000000", "999999999999999999"},
        };
        
        for(String[] input : inputs){
            Assert.assertEquals("input: " + input[0], input[1], sv.smallestGoodBase(input[0]));
            Assert.assertEquals(input[1], sv.smallestGoodBase2(input[0]));
        }
    } 
}
