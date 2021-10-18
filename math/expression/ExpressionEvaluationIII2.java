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
 *    single digits, and positive and negative signs,
 *    +, - operators,
 *    open/closing parentheses, 
 *    empty spaces. 
 *
 * Don't use eval or a similar built-in parser.
 *
 * For example, given '-1 + (2 + 3)', you should return 4.
 *
 * Tags: FB
 * 
 * Solutions:
 *   In fact, it's as same as ExpressionEvaluationIII, here it's another point 
 * 
 */
public class ExpressionEvaluationIII2 {
    
    public int evaluate_III_2(String expression) {
        if(expression == null){
            throw new IllegalArgumentException();
        }
        
        expression += "+";
        
        long r = 0;
        long sign = 1;
        long num = 0;
        
        Stack<Long> datas = new Stack<>();
        char c;
        for(int i = 0; i < expression.length(); i++){
            c = expression.charAt(i);
            
            switch(c){
                case ' ':
                    //nothing to do, continue 
                    break;    
                case '-':
                case '+':
                    r += sign * num;
                    num = 0;
                    sign = (c == '+'? 1 : -1);
                    break;
                case '(':
                    datas.add(r);
                    datas.add(sign);
                    
                    r = 0;
                    sign = 1;
                    break;
                case ')':
                    r += sign * num;
                    
                    num = r;
                    sign = datas.pop();
                    r = datas.pop();
                            
                    break;
                default:
                    num = num * 10 + (c - '0');
            }
        }
        
        return (int)r;
    }
    
    public static void main(String[] args){
        ExpressionEvaluationIII2 sv = new ExpressionEvaluationIII2();
        
        Assert.assertEquals(2, sv.evaluate_III_2("1 + 1"));
        Assert.assertEquals(3, sv.evaluate_III_2("2-1 + 2"));
        Assert.assertEquals(2, sv.evaluate_III_2("2"));

        Assert.assertEquals(23, sv.evaluate_III_2("(1+(4+5+2)-3)+(6+8)"));
        Assert.assertEquals(7, sv.evaluate_III_2("(6-(1-2))"));
        Assert.assertEquals(7, sv.evaluate_III_2("((6-(1-2)))"));
        Assert.assertEquals(-12, sv.evaluate_III_2("-12"));
        Assert.assertEquals(21, sv.evaluate_III_2("-1+22"));
        Assert.assertEquals(6, sv.evaluate_III_2("1-(2-(3 + 4))"));
        
        Assert.assertEquals(2, sv.evaluate_III_2("(10 -(9-(8-(7))))"));
        Assert.assertEquals(4, sv.evaluate_III_2("10 - (2 + 3) - ((5-2) - 2)"));

        
//        String[] inputs = {
//            "1 + 1",
//            " 2-1 + 2 ",
//            "(1+(4+5+2)-3)+(6+8)",
//            "(10 -(9-(8-(7))))",
//            "10 - (2 + 3) - ((5-2) - 2)",
//            "-1 + (2 + 3)"
//        };
//        
//        int[] expects = {2, 3, 23, 2, 4, 4};
//        
//        for(int i = 0; i < inputs.length; i++){
//            Assert.assertEquals(" i = "+i, expects[i], sv.evaluate_III_2(inputs[i]));
//        }
    }
}
