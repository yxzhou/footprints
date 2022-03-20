/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math.expression;

import junit.framework.Assert;

/**
 * Evaluate a simple expression string. 
 * The expression string contains: non-negative integers, +, - operators, empty spaces. 
 *
 * You may assume that the given expression is always valid.
 *
 * Some examples: "3+2+2" = 7
 */
public class ExpressionEvaluation {
    
    /* Time O(n),  Space O(1)*/
    public int evaluate_I(String expression) {
        if(expression == null){
            throw new IllegalArgumentException();
        }
        
        long result = 0;    // int maybe not enough 
        long num = 0;
        boolean isPlus = true; // true means '+', false means '-'
        
        for(char c : expression.toCharArray()){
            switch(c){
                case ' ':
                    //ignore
                    break;
                case '+':
                case '-':
                    result = calculate(result, isPlus, num);
                    isPlus = (c == '+');
                    num = 0;
                    break;
                default:
                    num = num * 10 + (c - '0');
                    break;
            }
        }
        
        result = calculate(result, isPlus, num);
        return (int)result;
    }
    
    private long calculate(long a, boolean isPlus, long b){
        if(isPlus){
            return a + b;
        }else{
            return a - b;
        }
    }
    
    public static void main(String[] args){
        
        String[][] inputs = {
            {"1 + 1", "2"},
            {"2-1 + 2", "3"},
            {"2","2"},
            {"-12","-12"},
            {"3 + 2 + 2", "7"},
            {"21-1 -2", "18"},
        };
        
        ExpressionEvaluation sv = new ExpressionEvaluation();
        
        for(String[] input : inputs){
            System.out.println(input[0]);
            
            Assert.assertEquals(Integer.parseInt(input[1]), sv.evaluate_I(input[0]));
        }

    }
}
