/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import java.util.Arrays;
import org.junit.Assert;

/**
 *
 * Given a non-negative number represented as an array of digits, plus one to the number.Returns a new array.
 * 
 * The number is arranged according to the number of digits, with the highest digit at the top of the list.
 * 
 * Example 1:
 *   Input: [1,2,3]
 *   Output: [1,2,4]
 * 
 * Example 2:
 *   Input: [9,9,9]
 *   Output: [1,0,0,0]
 * 
 * 
 */
public class AddOne {
    /**
     * @param digits: a number represented as an array of digits
     * @return the result d
     */
    public int[] plusOne(int[] digits) {
        if(digits == null ){
            return new int[]{1};
        }

        int n = digits.length;
        int[] result = new int[n];
        
        int sum = 1;
        for(int i = n - 1; i >= 0; i--){
            sum += digits[i];
            result[i] = sum % 10;
            sum /= 10;
        }

        if(sum == 0){
            return result;
        }
        
        int[] result2 = new int[n + 1];
        result2[0] = sum;
        System.arraycopy(result, 0, result2, 1, n);
        return result2;
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        AddOne sv = new AddOne();
        
        int[][][] inputs = {
            {{1,2,3}, {1,2,4}},
            {{9,9,9}, {1,0,0,0}},
            {null, {1}},
            {{}, {1}},    
        };
        
        for(int[][] input : inputs){
            System.out.println(String.format("Input: digits=%s", Arrays.toString(input[0]) ));
            
            Assert.assertArrayEquals(input[1], sv.plusOne(input[0]));
        }
    }
    
}
