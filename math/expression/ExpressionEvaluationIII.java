/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math.expression;

import java.util.Stack;
import junit.framework.Assert;

/**
 * Evaluate a simple expression string. 
 * The expression string contains:
 *    non-negative integers,
 *    +, - operators,
 *    open/closing parentheses, 
 *    empty spaces. 
 * 
 * The integer division should truncate toward zero. 
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

        expression += "+";
        
        Stack<Long> datas = new Stack<>();
        Stack<Boolean> operators = new Stack<>();
        
        long r = 0;
        boolean operator = true; // true - '+', false - '-'
        long num = 0;
        char c;
        for (int i = 0; i < expression.length(); i++) {
            c = expression.charAt(i);

            switch (c) {
                case ' ':
                    //continue  
                    break;
                case '+':
                case '-':
                    if (operator) {
                        r += num;
                    } else {
                        r -= num;
                    }
                    num = 0;
                    operator = (c == '+');
                    break;
                case '(':
                    datas.add(r);
                    operators.add(operator);
                    //stack.add("(");

                    r = 0;
                    operator = true;
                    break;
                case ')':
                    if (operator) {
                        r += num;
                    } else {
                        r -= num;
                    }
                    
                    num = r;
                    operator = operators.pop();
                    r = datas.pop();
                    break;
                default:
                    num = num * 10 + (c - '0');
            }
        }

        return (int) r;
    }
    
    public static void main(String[] args){
        ExpressionEvaluationIII sv = new ExpressionEvaluationIII();
        
        Assert.assertEquals(2, sv.evaluate_III("1 + 1"));
        Assert.assertEquals(3, sv.evaluate_III("2-1 + 2"));
        Assert.assertEquals(2, sv.evaluate_III("2"));

        Assert.assertEquals(23, sv.evaluate_III("(1+(4+5+2)-3)+(6+8)"));
        Assert.assertEquals(7, sv.evaluate_III("(6-(1-2))"));
        Assert.assertEquals(-12, sv.evaluate_III("-12"));
        Assert.assertEquals(21, sv.evaluate_III("-1+22"));
        Assert.assertEquals(2, sv.evaluate_III("(10 -(9-(8-(7))))"));
        Assert.assertEquals(4, sv.evaluate_III("10 - (2 + 3) - ((5-2) - 2)"));
        Assert.assertEquals(6, sv.evaluate_III("1-(2-(3 + 4))"));
        
        
//        String[] inputs = {
//            "1 + 1",
//            " 2-1 + 2 ",
//            "(1+(4+5+2)-3)+(6+8)",
//            "(10 -(9-(8-(7))))",
//            "10 - (2 + 3) - ((5-2) - 2)"
//        };
//        
//        int[] expects = {2, 3, 23, 2, 4};
//        
//        for(int i = 0; i < inputs.length; i++){
//            Assert.assertEquals(" i = "+i, expects[i], sv.evaluate_III(inputs[i]));
//        }
    }
}
