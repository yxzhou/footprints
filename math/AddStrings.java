/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import junit.framework.Assert;

/**
 *
 * Given two non-negative integers num1 and num2 represented as strings, return the sum of num1 and num2
 * 
 * Example:
 * input: "315)" "6592"
 * output: "6907"
 * 
 * 
 * 
 */
public class AddStrings {
    
    public String addStrings(String num1, String num2) {
        if(num1 == null){
            return num2;
        }
        if(num2 == null){
            return num1;
        }

        if(num1.length() > num2.length()){
            return addStrings(num2, num1);
        }

        StringBuilder result = new StringBuilder(num2.length() + 1);

        int j = num2.length() - 1;
        int carry = 0;

        for(int i = num1.length() - 1; i >= 0 && j >= 0; i--, j--){
            carry += num1.charAt(i) - '0' + num2.charAt(j)  - '0';

            result.append( carry % 10 );
            carry /= 10;
        }

        for( ; j >= 0 ; j--){
            carry += num2.charAt(j) - '0';

            result.append( carry % 10 );
            carry /= 10;
        }

        if(carry != 0){
            result.append( carry );
        }

        return result.reverse().toString();
    }
   
    
    public static void main(String[] args){
        
        AddStrings sv = new AddStrings();
                
        Assert.assertEquals("6907", sv.addStrings("315", "6592"));

    }
  
}
