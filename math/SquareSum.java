/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

/**
 *
 * Given a integer c, your task is to decide whether there're two integers a and b such that a^2 + b^2 = c.
 * 
 * Example 1:
 *   Input : n = 5
 *   Output : true
 *   Explanation : 1 * 1 + 2 * 2 = 5
 * 
 * Example 2:
 *   Input : n = -5
 *   Output : false
 * 
 */
public class SquareSum {
    /**
     * @param num: the given number
     * @return: whether whether there are two integers
     */
    public boolean checkSumOfSquareNumbers(int num) {
        if(num < 0){
            return false;
        }else if(num < 2){
            return true;
        }

        // (a = x * x) + (b = y * y) == (c = z * z)
        int b;
        int y;
        for(int x = 0, a = 0, half = num / 2; a <= half ; a += x*2 + 1, x++){ // (a + 1)*(a + 1) - a*a = a*2 + 1
            b = num - a;

            y = (int)Math.sqrt(b);

            if( y * y == b ){
                return true;
            }
        }

        return false;
    }
}
