/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

/**
 * _https://www.lintcode.com/problem/1028
 * 
 * If x can get a legal number different from X after rotating each digit 180 degrees, then x is a good number. Each
 * digit must be rotated - we cannot choose to leave it alone.
 *
 * A number is valid if each digit remains a digit after rotation. 0, 1, and 8 rotate to themselves; 2 and 5 rotate to
 * each other; 6 and 9 rotate to each other, The rest of the numbers will not turn into any numbers after rotation, so
 * it is illegal.
 *
 * Now given a positive number N, how many numbers X from 1 to N are good?
 * 
 * N will be in range [1, 10000].
 * 
 * Example 1:
 * Input: 10  Output: 4
 * Explanation: There are four good numbers in the range [1, 10] : 2, 5, 6, 9.
 * Note that 1 and 10 are not good numbers, since they remain unchanged after rotating.
 * 
 * Example 2:  Output: 2
 * Explanation: There are two good numbers in the range [1, 5] : 2, 5.
 * 
 * Thoughts:
 *      10 -> 10
 *      12 -> 15
 *      18 -> 18
 * 
 */
public class RotatedDigits {
    /**
     * 
     * @param N: a positive number
     * @return how many numbers X from 1 to N are good
     */
    public int rotatedDigits(int N) {
        int count = 0;
        for(int x = 2; x <= N; x++){
            if(valid(x)){
                count++;
            } 
        }
        return count;
    }

    int[] pairs = {0, 1, 5, -1, -1, 2, 9, -1, 8, 6};

    private boolean valid(int n){
        int x = n;
        int y = 0;
        int base = 1;
        int i;
        while(x > 0){
            i = pairs[x % 10];

            if(i == -1){
                return false;
            }

            y += i * base;
            base *= 10;
            x /= 10;
        }

        //System.out.println(String.format("n = %d,  y = %d", n, y));
        return n != y;
    }
}
