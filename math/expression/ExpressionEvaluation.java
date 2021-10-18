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
        
        long r = 0;    // int maybe not enough 
        int num = 0;
        boolean operator = true; // true means '+', false means '-'
        char c;
        for(int i = 0; i < expression.length(); i++){
            c = expression.charAt(i);
            if(c == ' '){
                continue;
            }
            if(c == '+' || c == '-'){
                if(operator){
                    r += num;
                }else{
                    r -= num;
                }
                
                num = 0;
                operator = (c == '+');
            }else{
                num = num * 10 + (c - '0');
            }
        }
        
        if(operator){
            r += num;
        }else{
            r -= num;
        }
        
        return (int)r;
    }
    
    
    public static void main(String[] args){
        ExpressionEvaluation sv = new ExpressionEvaluation();
        
        Assert.assertEquals(2, sv.evaluate_I("1 + 1"));
        Assert.assertEquals(3, sv.evaluate_I("2-1 + 2"));
        Assert.assertEquals(2, sv.evaluate_I("2"));
        
        Assert.assertEquals(7, sv.evaluate_I("3 + 2 + 2"));
        Assert.assertEquals(18, sv.evaluate_I("21-1 -2"));
        
//        String[] inputs = {
//            "3 + 2 + 2",
//            "21-1 -2"
//        };
//        
//        int[] expects = { 7, 18 };
//        
//        for(int i = 0; i < inputs.length; i++){
//            Assert.assertEquals(expects[i], sv.evaluate_I(inputs[i]));
//        }

    }
}
