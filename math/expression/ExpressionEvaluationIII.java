/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math.expression;

import java.util.Stack;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/978
 * 
 * Evaluate a simple expression string. 
 * The expression string contains:
 *    non-negative integers,
 *    +, - operators,
 *    open/closing parentheses, 
 *    empty spaces. 
 * 
 * You may assume that the given expression is always valid. 
 * 
 * Some examples: 
 *   "1 + 1" = 2 
 *   " 2-1 + 2 " = 3 
 *   "(1+(4+5+2)-3)+(6+8)" = 23
 *   "(10 -(9-(8-(7))))" = 2
 *   "10 - (2 + 3) - ((5-2) - 2)" = 4
 */
public class ExpressionEvaluationIII {
    
    /*  Time O(n),  Space O(n)*/
    public int evaluate_III(String expression) {
        if (expression == null) {
            throw new IllegalArgumentException();
        }
        //assume that the given expression is always valid

        expression += "+";
        
        Stack<Long> nums = new Stack<>();
        Stack<Boolean> operators = new Stack<>();// true, '+'; false, '-'
        
        long num = 0;
        long preNum = 0;
        boolean isPlus = true; 

        for (char c : expression.toCharArray()) {

            switch (c) {
                case ' ':
                    //ignore
                    break;
                case '+':
                case '-':
                    preNum = calculate(preNum, isPlus, num);

                    isPlus = (c == '+');
                    num = 0;
                    break;
                case '(':
                    nums.add(preNum);
                    operators.add(isPlus);

                    preNum = 0;
                    isPlus = true;
                    break;
                case ')':
                    num = calculate(preNum, isPlus, num);
                    
                    isPlus = operators.pop();
                    preNum = nums.pop();
                    break;
                default:
                    num = num * 10 + (c - '0');
                    break;
            }
        }

        return (int) preNum;
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
            {"(1+(4+5+2)-3)+(6+8)","23"},
            {"(6-(1-2))","7"},
            {"-1+22","21"},
            {"(10 -(9-(8-(7))))","2"},
            {"10 - (2 + 3) - ((5-2) - 2)","4"},
            {"1-(2-(3 + 4))","6"},
            
        };
        
        ExpressionEvaluationIII sv = new ExpressionEvaluationIII();
        
        for(String[] input : inputs){
            System.out.println(input[0]);
            
            Assert.assertEquals("Found error on input: "+input[1], Integer.parseInt(input[1]), sv.evaluate_III(input[0]));
        }
        
    }
}
