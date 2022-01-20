/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

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
     * @return: the result
     */
    public int[] plusOne(int[] digits) {
        if(digits == null){
            return new int[0];
        }

        int n = digits.length;
        int[] r = new int[n];
        
        int sum = 1;
        for(int i = n - 1; i >= 0; i--){
            sum += digits[i];
            r[i] = sum % 10;
            sum /= 10;
        }

        if(sum == 0){
            return r;
        }else{
            int[] r2 = new int[n + 1];
            r2[0] = sum;
            System.arraycopy(r, 0, r2, 1, n);
            return r2;
        }
    }
    
    
    


    
}
