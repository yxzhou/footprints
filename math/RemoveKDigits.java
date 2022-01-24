/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package math;

import java.util.Stack;
import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/1255
 *
 * Given a non-negative integer num represented as a string, remove k digits from the number so that the new number is
 * the smallest possible.Find the smallest possible result.
 *
 * The length of num is less than 10002 and will be ≥ k. The given num does not contain any leading zero.
 *
 * Example 1:
 * Input: num = "1432219", k = 3 
 * Output: "1219" 
 * Explanation: 
 * Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest. 
 * 
 * Example 2:
 * Input: num = "10200", k = 1 
 * Output: "200" 
 * Explanation: Remove the leading 1 and the number is 200. 
 * Note that the output must not contain leading zeroes. 
 * 
 * Example 3:
 * Input: num = "10", k = 2 
 * Output: "0" 
 * Explanation: 
 * Remove all the digits from the number and it is left with nothing which is 0.
 * 
 * Thoughts:
 *   think about the following case
 *   1) num = "1432219", k = 3 
 *   1432219 -> 132219 -> 12219 -> 1219
 * 
 *   2) num ="1241241", k = 2
 *   1241241 -> 121241 -> 11241
 * 
 *   3) num = "10200", k = 1 
 *   10200 -> 0200 -> 200
 * 
 *   So:
 *   1) check from left to right, remove the first i_th digit, if num.charAt(i) > num.charAt(i + 1)
 *   2) need pay attention to the leading zero 
 * 
 */
public class RemoveKDigits {
    
    /**
     * 
     * @param num: a string
     * @param k: an integer
     * @return return a string
     */
    public String removeKdigits_n(String num, int k) {
        if(num == null || num.length() <= k){
            return "0";
        }
        if(k < 1 ){
            return num;
        }
        
        char[] arr = new char[num.length() + 1];
        arr[0] = Character.MIN_VALUE;
        int r = 0;
        
        for(int i = 0; i < num.length(); i++ ){
            while(k > 0 && arr[r] > num.charAt(i)){
                k--;
                r--;
            }
            
            arr[++r] = num.charAt(i);
        }
        
        r -= k;
        
        int l = 1;
        while( l < r && arr[l] == '0' ){
            l++;
        }

        return String.valueOf(arr, l, r + 1 - l);
    }
    
    public String removeKdigits(String num, int k) {
        if (num == null || num.length() <= k) {
            return "0";
        }

        Stack<Character> stack = new Stack<>();
        //stack.push(Character.MIN_VALUE);
        //stack.push('0');
        stack.push((char)0);

        for (char c : num.toCharArray()) {
            while (k > 0 && stack.peek() > c) {
                stack.pop();
                k--;
            }

            stack.push(c);
        }

        while (k > 0) {
            stack.pop();
            k--;
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        sb.reverse();

        int l = 0;
        for ( int n = sb.length(); l < n && sb.charAt(l) <= '0' ; l++);
        
        return sb.substring(l);
    }
    
    public static void main(String[] args){
        String[][] inputs = {
            //{num, k, expect}
            {"1432219", "3", "1219"},
            {"1432229", "3", "1222"},
            {"10200", "1", "200"},
            {"10", "2", "0"},
            {"124319", "3", "119"},
            {"124319", "2", "1219"}, 
            {"124391", "2", "1231"},   
            {"1241241", "2", "11241"}, 
        };
        
        RemoveKDigits sv = new RemoveKDigits();
        
        for(String[] input : inputs){
            System.out.println(String.format("\n num: %s, k-%s", input[0], input[1]));
            
            Assert.assertEquals(input[2], sv.removeKdigits(input[0], Integer.parseInt(input[1])));
            
            Assert.assertEquals(input[2], sv.removeKdigits_n(input[0], Integer.parseInt(input[1])));
        }
    }
}
