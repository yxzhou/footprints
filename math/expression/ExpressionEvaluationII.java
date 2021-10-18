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
 * The expression string contains: non-negative integers, +, -, *, / operators, empty spaces.
 *
 * The integer division should truncate toward zero. You may assume that the given expression is always valid.
 *
 * Some examples: 
 *   "3+2*2" = 7 
 *   "3- 2*2" = -1
 *   " 3/2 " = 1 
 *   " 3+5 / 2 " = 5
 *   " 5 / 2 * 2" = 4
 *   "3 + 2*2 + 3" = 10
 */
public class ExpressionEvaluationII {
    
    /* Time O(n),  Space O(1)*/
    public int evaluate_II(String expression) {
        if(expression == null){
            throw new IllegalArgumentException();
        }
        
        expression += '+';
        
        long r = 0;
        long preNum = 1; // ?? float
        int num = 0;
        char operator = '+'; // {'+', '-', '*', '/'}
        char c;
        
        for(int i = 0; i < expression.length(); i++){
            c = expression.charAt(i);
            
            switch(c){
                case ' ':
                  //continue;
                  break;
                case '+':
                case '-':
                  if(operator == '*' || operator == '/'){
                      preNum = calculate(preNum, operator, num);
                      r = calculate(r, '+', preNum);
                      
                      preNum = 1;
                  }else{ // + or -
                      r = calculate(r, operator, num);
                  }
                  
                  num = 0;
                  operator = c;
                  break;
                case '*':
                case '/':
                  if(operator == '*' || operator == '/'){
                      preNum = calculate(preNum, operator, num);
                  }else{ // + or -
                      preNum = calculate(0, operator, num);
                  }
                  
                  num = 0;
                  operator = c;
                  break;
                default: //number 
                  num = num * 10 + (c - '0');
                    
            }
        }
        
        return (int)r;
    }
    
    /* Time O(n),  Space O(1)*/
    public int evaluate_II_stack(String expression) {
        if(null == expression || 0 == expression.length()){
            throw new IllegalArgumentException();
        }
        
        Stack<Long> stack = new Stack<>();
        stack.push(0L);
        
        expression += '+';
        
        long curr = 0;
        char operator = '+';
        
        for(char c : expression.toCharArray()){
            if(c >= '0' && c <= '9'){
                curr = curr * 10 + c - '0';
            }else if(c == '+' || c == '-' || c == '*' || c == '/'){
                if(c == '+' || c == '-'){
                    curr = calculate(stack.pop(), operator, curr);
                    
                    if(!stack.isEmpty()){
                        curr = calculate(stack.pop(), '+', curr);
                    }
                }else{ //c == '*' || c == '/' 
                    if(operator == '*' || operator == '/'){
                        curr = calculate(stack.pop(), operator, curr);
                    }else{
                        curr = calculate(0, operator, curr);
                    }
                }
                
                stack.push(curr);
                curr = 0;
                operator = c;
            } //else ignore
        }
        
        return stack.pop().intValue();
    }
    
    private long calculate(long a, char operator, long b){
        switch (operator){
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            default:
                throw new IllegalArgumentException("found incorrect character");
        }
    }
    
    public static void main(String[] args){
        ExpressionEvaluationII sv = new ExpressionEvaluationII();
        
        Assert.assertEquals(2, sv.evaluate_II("1 + 1"));
        Assert.assertEquals(3, sv.evaluate_II("2-1 + 2"));
        Assert.assertEquals(2, sv.evaluate_II("2"));

        Assert.assertEquals(7, sv.evaluate_II("3+2*2"));
        Assert.assertEquals(-1, sv.evaluate_II("3- 2*2"));
        Assert.assertEquals(1, sv.evaluate_II(" 3/2"));
        Assert.assertEquals(5, sv.evaluate_II("3+5 / 2"));
        Assert.assertEquals(4, sv.evaluate_II(" 5 / 2 * 2 "));
        Assert.assertEquals(5, sv.evaluate_II("1*2 + 3"));
        Assert.assertEquals(10, sv.evaluate_II("3 + 2*2 + 3"));
        Assert.assertEquals(4, sv.evaluate_II("3-2*2*2-3+12"));
        Assert.assertEquals(1, sv.evaluate_II("6/2/3"));
        Assert.assertEquals(3, sv.evaluate_II("3-2*2*2-3+12-6/2/3"));
        
//        String[] inputs = {
//            "3+2*2",
//            "3- 2*2",
//            "3/2",
//            "3+5 / 2",
//            " 5 / 2 * 2 ",
//            "3 + 2*2 + 3"
//        };
//        
//        int[] expects = {7, -1, 1, 5, 4, 10};
//        
//        for(int i = 0; i < inputs.length; i++){
//            Assert.assertEquals(expects[i], sv.evaluate_II(inputs[i]));
//        }
    }
}
