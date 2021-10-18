/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math.expression;

import junit.framework.Assert;

/**
 * Given a string of numbers, write a function to find the maximum value from the string, you can add a + or * sign between any two numbers.
 * Note, you can add "(" and ")" anywhere you want
 * 
 * Example 1
 * Input:  str = "01231"
 * Output: 12
 * Explanation: (0 + 1 + 2) * (3 + 1) = 12
 * 
 * Example 2
 * Input: str = "891"
 * Output: 80
 * Explanation: 8 * (9 + 1) = 80
 * 
 * Solution:
 *    a -> a 
 *   ab -> a + b, a * b
 *   abc -> ab + c, ab * c, a * bc
 *   abcd -> abc + d, abc * d, ab*cd, a*bcd
 * 
 */
public class CalculateMaxValueII {
    public int calcMaxValue(String str) {
        if(str == null || str.length() == 0){
            return 0;
        }

        int n = str.length();
        int[][] f = new int[n][n]; // define f[0][i] as the max value from the string [0, i]
        
        int l;
        for(int len = 0; len < n; len++ ){
            for(int r = len; r < n; r++){
                if(len == 0){
                    f[r][r] = str.charAt(r) - '0';
                } else {
                    l = r - len;
                    
                    f[l][r] = f[l][r - 1] + f[r][r];
                    for(int k = l; k < r; k++){
                        f[l][r] = Math.max(f[l][r], f[l][k] * f[k + 1][r]);
                    }
                }
            }
        }

        return f[0][n - 1];
    }
    
    public static void main(String[] args){
        CalculateMaxValueII sv = new CalculateMaxValueII();
        
        String[] inputs = {"01231", "891"};
        int[] expects = {12, 80};
        for(int i = 0; i < inputs.length; i++){
            Assert.assertEquals(expects[i], sv.calcMaxValue(inputs[i]));
        }
    }
}
