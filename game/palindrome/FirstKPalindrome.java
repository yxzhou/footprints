/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.palindrome;

/**
 *
 * Given a integer k, find the sum of first k even-length palindrome numbers.
 * Even length here refers to the number of digits of a number is even.
 * 
 * Example1
 * Input:  k = 3
 * Output: 66
 * Explanation: 11 + 22 + 33  = 66 (Sum of first three even-length palindrome numbers)
 * 
 * Example2
 * Input:  k = 10
 * Output: 1496
 * Explanation: 11 + 22 + 33 + 44 + 55 + 66 + 77 + 88 + 99 + 1001 = 1496
 * 
 * Questions:
 *   what's k? min and max,  how to do if the final sum over  int? 
 * 
 * 
 */
public class FirstKPalindrome {
    /**
     * @param k: Write your code here
     * @return: the sum of first k even-length palindrome numbers
     */
    public int sumKEven(int k) {
        if(k < 1){
            return 0;
        }

        int sum = 0;
        int rate = 1;
        for(int x = 1; x <= k; x++ ){
            if(x >= rate * 10){
                rate = x;
            }
            
            sum += x * (rate * 10) + mirror(x);
        }

        return sum;
    }

    private int mirror(int x){
        int mirror = 0;
        for(int y = x; y > 0; y /= 10 ){
            mirror = mirror * 10 + y % 10;
        }
        return mirror;
    }
}
