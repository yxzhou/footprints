/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package math;

/**
 * _https://www.lintcode.com/problem/729
 *
 * We are given two numbers A and B such that B >= A. We need to compute the last digit of this resulting F such that F
 * = B! / A! where 1 <= A, B <= 10^18 (A and B are very large)
 *
 *
 * Example
 * Given A = 2, B = 4, return 2
 * A! = 2 and B! = 24, F = 24 / 2 = 12 --> last digit = 2
 *
 * Given A = 107, B = 109, return 2
 *
 * Thoughts:
 *   define r = A!/B!, 
 *   if A > B,  r = 0.
 *   if A == B, r = 1;
 *   if A < B,  r = (A + 1) * (A + 2) * ... * B
 *   
 *   see 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,  
 *   the last digits 2 * 5 and 5 * 8 are 0. so if B - A > 2, the last digit is 0
 *   
 */
public class LastDigitByFactorialDivide {
    /**
     * @param A: the given number
     * @param B: another number
     * @return the last digit of B! / A! 
     */
    public int computeLastDigit(long A, long B) {
        if(A > B || B - A > 2){
            return 0; 
        }

        long result = 1;

        for(long i = A + 1; i <= B; i++ ){
            result = (result * (i % 10)) % 10;
        }

        return (int)result;
    }
}
