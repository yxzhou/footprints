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
    
    
    public String multiply_n(String num1, String num2) {
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
    
    /**
     * @param num1: a non-negative integers
     * @param num2: a non-negative integers
     * @return: return product of num1 and num2
     */
    public String multiply_x(String num1, String num2) {
        if(num1 == null || num2 == null || num1.equals("0") || num2.equals("0")){
            return "0";
        }

        int n = num1.length();
        int m = num2.length();

        int[] nums1 = helper(num1, n);
        int[] nums2 = helper(num2, m);

        int[] r = new int[n + m]; // result

        for(int i = 0; i < n; i++){
            if(nums1[i] == 0){
                continue;
            }
            
            for(int j = 0, k = i; j < m; j++, k++){
                r[k] += nums1[i] * nums2[j];
            }
        }

        StringBuilder result = new StringBuilder();
 

        return result.reverse().toString();
    }

    private int[] helper(String nums, int n){
        int[] r = new int[n];
        int k = 0;
        for(int i = n - 1; i >= 0; i--){
            r[k++] = nums.charAt(i) - '0';
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
        
        String[][] inputs = {
            //{num1, num2, expect}
            {"2", "3", "6"},
            {"123", "456", "56088"},
            {"123", "0", "0"},
            {"123456789", "987654321", "121932631112635269"},
            {"6913259244", "71103343", "491555843274052692"}
        };
        
        for(String[] input : inputs){
            Assert.assertEquals(input[2], sv.multiply_n(input[0], input[1]));
            
            Assert.assertEquals(input[2], sv.multiply_x(input[0], input[1]));
        }
       
    }
}
