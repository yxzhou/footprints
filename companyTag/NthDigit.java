/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package companyTag;

/**
 * _https://www.lintcode.com/problem/1256
 * 
 * Find the nth digit of the infinite integer sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...
 *
 *  Note: n is positive and will fit within the range of a 32-bit signed integer (n < 2^31). 
 * 
 * Example 1: Input：3 Output：3 
 * 
 * Example 2: Input：11 Output：0 
 * Explanation：The 11th digit of the sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ... is a 0, which is part of the number 10.
 *
 * Thoughts:
 *   1 - 9.     m = 9           x = n / 1 + 1       n % 1 == 0 
 *   10 - 99    m = 90 * 2,     x = n / 2 + 10      n % 2 == 0 means the left-most one 
 *   100 - 999  m = 900 * 3     x = n / 3 + 100     n % 3 == 0    
 * 
 */
public class NthDigit {
    /**
     * @param n: a positive integer
     * @return the nth digit of the infinite integer sequence
     */
    public int findNthDigit(int n) {
        long m = 9;
        int base = 1;
        int k = 1;

        while(n > m){
            n -= m;

            base *= 10;
            k++;
            m = (long)9 * base * k; 
        }

        n--;
        String x = String.valueOf(n / k + base);
        return x.charAt(n % k) - '0';
    }
}
