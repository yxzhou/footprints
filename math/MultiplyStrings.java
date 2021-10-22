/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import org.junit.Assert;

/**
 *
 * Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also
 * represented as a string.
 *
 * Example 1: Input: num1 = "2", num2 = "3" Output: "6"
 * Example 2: Input: num1 = "123", num2 = "456" Output: "56088"
 * Example 2: Input: num1 = "123", num2 = "0" Output: "0"
 *
 * Note: 
 *   The length of both num1 and num2 is < 110. 
 *   Both num1 and num2 contain only digits 0-9. 
 *   Both num1 and num2 does not contain any leading zero, except the number 0 itself. 
 *   You must not use any built-in BigInteger library or convert the inputs to integer directly.
 *
 */
public class MultiplyStrings {

    /*
     * iteral multiplication
     * 
     *  120*16
     *  (120)*(16) => {1*1, 1*6 + 2*1, 2*6+0*1, 0*6} => {}
     *     
     * @param num1: a non-negative integers
     * @param num2: a non-negative integers
     * @return: return product of num1 and num2
     */
    public String multiply(String num1, String num2) {
        if(num1 == null || num2 == null){
            return "0";
        }

        int n = num1.length();
        int m = num2.length();
        int[] r = new int[m + n];

        int[] x = reverse(num1);
        int[] y = reverse(num2);

        int k;
        for(int i = 0; i < n; i++){
            k = i;
            for(int j = 0; j < m; j++){
                r[k] += x[i] * y[j];
                k++;
            }
        }

        for(int i = 1; i < r.length; i++){
            r[i] += r[i - 1] / 10;
            r[i - 1] %= 10;
        }

        k = r.length - 1;
        while(k >= 0 && r[k] == 0 ){
            k--;
        }

        if(k == -1){
            return "0";
        }

        StringBuilder result = new StringBuilder();
        for(int i = k; i >= 0; i--){
            result.append(r[i]);
        }

        return result.toString();
    }

    private int[] reverse(String num){
        int n = num.length();
        int[] r = new int[n];

        for(int i = 0, j = n - 1; i < n; i++, j--){
            r[j] = num.charAt(i) - '0';
        }


        return r;
    }
    
    
    public String multiply_n (String num1, String num2) {
        if(num1 == null || num2 == null){
            return "0";
        }
        
        int n = num1.length();
        int m = num2.length();
        int[] r = new int[m + n];

        int[] x = convert(num1);
        int[] y = convert(num2);
        
        for(int i = n - 1, p = r.length - 1; i >= 0; i--, p--){
//            if(x[i] == 0){
//                continue;
//            }
            
            for(int j = m - 1, q = p; j >= 0; j--, q--){
                r[q] += x[i] * y[j];
            }
        }
        
        for(int p = r.length - 1; p > 0; p--){
            r[p - 1] += r[p] / 10;
            r[p] %= 10;
        }
        
        StringBuilder result = new StringBuilder();
        int p = 0;
        while(p < r.length && r[p] == 0){
            p++;
        }
        for( ; p < r.length; p++ ){
            result.append(r[p]);
        }
        
        return result.length() == 0? "0" : result.toString();
    }
    
    private int[] convert(String num){
        int n = num.length();
        int[] r = new int[n];

        for(int i = 0; i < n; i++){
            r[i] = num.charAt(i) - '0';
        }

        return r;
    }
    
    public static void main(String[] args){
        
        int[] nums = new int[]{0, 1, 2, 3, 4};
        String s = new String(nums, 2, nums.length - 2);
        System.out.println(" -- " +  new String(new int[]{0, 1, 2, 3, 4}, 1, 3) + " -- " + new String(new char[]{'0', '1', '2', '3', '4'}, 1, 3));
        
//        if(true){
//            return;
//        }
        
        MultiplyStrings sv = new MultiplyStrings();
        
        Assert.assertEquals("6", sv.multiply_n("2", "3"));
        Assert.assertEquals("56088", sv.multiply_n("123", "456"));
        Assert.assertEquals("0", sv.multiply_n("123", "0"));
        Assert.assertEquals("121932631112635269", sv.multiply_n("123456789", "987654321"));
        Assert.assertEquals("491555843274052692", sv.multiply_n("6913259244", "71103343"));
       

    }
}
