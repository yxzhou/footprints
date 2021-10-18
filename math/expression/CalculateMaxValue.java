/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math.expression;

/**
 *
 * Given a string of numbers, write a function to find the maximum value from the string, you can add a + or * sign between any two numbers.
 * 
 * Example1
 * Input:  str = "01231"
 * Output: 10
 * Explanation: ((((0 + 1) + 2) * 3) + 1) = 10 we get the maximum value 10
 * 
 * Example2
 * Input:  str = "891"
 * Output: 73
 * Explanation:  As 8 * 9 * 1 = 72 and 8 * 9 + 1 = 73 so 73 is maximum.
 * 
 * 
 * Solution:
 *    a -> a 
 *   ab -> a + b, a * b
 *   abc -> ab + c, ab * c
 *   abcd -> abc + d, abc * d
 * 
 */
public class CalculateMaxValue {
    /**
     * @param str: the given string
     * @return: the maximum value
     */
    public int calcMaxValue(String str) {
        if(str == null || str.length() == 0){
            return 0;
        }

        int pre = 0;
        int curr;
        for(int i = 0; i < str.length(); i++){
            curr = str.charAt(i) - '0';
            pre = Math.max(pre + curr, pre * curr);
        }

        return pre;
    }
}
