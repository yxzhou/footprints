/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array.parenthese;

import java.util.HashSet;
import java.util.Set;
import junit.framework.Assert;

/**
 *
 * You're given a string consisting solely of (, ), and *. * can represent either a (, ), or an empty string. 
 * Determine whether the parentheses are balanced.
 * For example, 
 *   (()* and (*) are balanced. 
 *   )*( is not balanced.
 * 
 * 
 */
public class ValidParenthesesIII {
    
    /*
     *
     * Time O(n)  Space O(n)
     */
    public boolean isValidParentheses(String s) {
        if(s == null || s.isEmpty()){
            return true;
        }
        
        Set<Integer> curr = new HashSet<>(); //store the count number of how many "(" as status
        curr.add(0);
        Set<Integer> next;
        
        char c;
        for(int i = 0; i < s.length(); i++){
            c = s.charAt(i);
            
            next = new HashSet<>();
            
            switch(c){
                case '(':
                    
                    for(int count : curr){
                        next.add(count + 1);
                    }
                    
                    break;
                case ')':
                    for(int count : curr){
                        if(count > 0){
                            next.add(count - 1);
                        }
                    }
                    
                    break;
                case '*':
                    for(int count : curr){
                        // * represent (
                        next.add(count + 1);
                        
                        // * represent NULL
                        next.add(count);
                        
                        // * represent )
                        if(count > 0){
                            next.add(count - 1);
                        }
                    }
                    
                    break;
                default:
                    throw new IllegalArgumentException("The input s should only contains (, ) and *, now it found " + c);
            }
            
            curr = next;
        }
        
        for(int count : curr){
            if(count == 0){
                return true;
            }
        }
        
        return false;
    }
    
    
    public static void main(String[] args){
        
        String[][] inputs = {
            //{s, expect}
            {null, "true"},
            {"()", "true"},
            {")", "false"},
            {"(()", "false"},
            {"())", "false"},
            {"(())", "true"},
            {"(()))", "false"},
            {"(()())", "true"},
            
            {"(()*", "true"},
            {"(*)", "true"},
            {")*(", "false"},
            
            {"(***********", "true"},
        };
        
        ValidParenthesesIII sv = new ValidParenthesesIII();
        
        for(String[] input : inputs){
            System.out.println( String.format("\n call isValidParentheses: s = %s ", input[0]) );

            Assert.assertEquals( Boolean.parseBoolean(input[1]), sv.isValidParentheses(input[0]));
        }
        
    }
    
}
