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
 *    +, -, *, / operators,
 *    open/closing parentheses, 
 *    empty spaces. 
 * 
 * The integer division should truncate toward zero. 
 * You may assume that the given expression is always valid. 
 * 
 * Some examples: 
 *   "3+2*2" = 7 
 *   " 3+5 / 2 " = 5
 *   "(1+(4+5*2)-3)/(6+6)" = 1
 *   "2*6-(23+7)/(1+2)" = 2
 * 
 */
public class ExpressionEvaluationV {
    
    /**
     * same as ExpressionTreeBuild.build()
     * Time O(n),  Space O(n)
     */
    public int evaluate_V(String expression) {
        
        if(expression == null){
            throw new IllegalArgumentException();
        }
        
        expression += "+";
        
        long num;
        char c;
        
        Stack<Long> datas = new Stack<>();
        //datas.add(0l);
        Stack<Character> operators = new Stack<>();
        //operators.add('+');
        
        for(int i = 0; i < expression.length(); i++){
            c = expression.charAt(i);
            
            switch(c){
                case ' ':
                    //nothing to do, continue
                    break;
                case '+':
                case '-':
                    while(!operators.isEmpty() && operators.peek() != '('){
                        calculate(datas, operators);
                    }
                    operators.add(c);
                    break;
                case '*':
                case '/':
                    while(!operators.isEmpty() && (operators.peek() == '*' || operators.peek() == '/') ){
                        calculate(datas, operators);
                    }
                    operators.add(c);
                    break;
                case '(':
                    operators.add(c);
                    break;    
                case ')':
                    while(!operators.isEmpty() && operators.peek() != '('){
                        calculate(datas, operators);
                    }
                    operators.pop(); // pop the '('
                    break;    
                default:
                    num = 0;
                    for( ; i < expression.length(); i++){
                        c = expression.charAt(i);
                        
                        if(c >= '0' && c <= '9'){
                            num = num * 10 + (c - '0');
                        }else{
                            break;
                        }
                    }
                    datas.add(num);
                    i--; //there is i++ in the for statement
            }
        }
        
        return datas.isEmpty()? 0 : datas.pop().intValue();
    }
    
    private void calculate(Stack<Long> datas, Stack<Character> operators){
        long right = datas.pop();
        long left = datas.pop();
        char operator = operators.pop();

        long r;
        switch(operator){
            case '-':
                r = left - right; 
                break;
            case '*':
                r = left * right; 
                break;
            case '/':
                r = left / right; 
                break;
            default: // '+'
                r = left + right; 
        }
        
        datas.add(r);
    }
    
    public static void main(String[] args){
        ExpressionEvaluationV sv = new ExpressionEvaluationV();
        
        Assert.assertEquals(2, sv.evaluate_V("1 + 1"));
        Assert.assertEquals(3, sv.evaluate_V("2-1 + 2"));
        Assert.assertEquals(2, sv.evaluate_V("2"));

        Assert.assertEquals(7, sv.evaluate_V("3+2*2"));
        Assert.assertEquals(1, sv.evaluate_V(" 3/2"));
        Assert.assertEquals(5, sv.evaluate_V("3+5 / 2"));
        Assert.assertEquals(5, sv.evaluate_V("1*2 + 3"));
        Assert.assertEquals(4, sv.evaluate_V("3-2*2*2-3+12"));
        Assert.assertEquals(1, sv.evaluate_V("6/2/3"));
        Assert.assertEquals(3, sv.evaluate_V("3-2*2*2-3+12-6/2/3"));

        Assert.assertEquals(23, sv.evaluate_V("(1+(4+5+2)-3)+(6+8)"));
        Assert.assertEquals(7, sv.evaluate_V("(6-(1-2))"));
        Assert.assertEquals(7, sv.evaluate_V("((6-(1-2)))"));
        //Assert.assertEquals(-12, sv.evaluate_V("-12"));
        //Assert.assertEquals(21, sv.evaluate_V("-1+22"));
        //Assert.assertEquals(2, sv.evaluate_V("1-(-21+20)"));
        Assert.assertEquals(6, sv.evaluate_V("1-(2-(3 + 4))"));

        Assert.assertEquals(1, sv.evaluate_V("(1+(4+5*2)-3)/(6+6)"));
        Assert.assertEquals(2, sv.evaluate_V("2*6-(23+7)/(1+2)"));
        
        Assert.assertEquals(2, sv.evaluate_V("(10 -(9-(8-(7))))"));
        Assert.assertEquals(4, sv.evaluate_V("10 - (2 + 3) - ((5-2) - 2)"));

        
        
//        String[] inputs = {
//            "1 + 1",
//            " 2-1 + 2 ",
//            "(1+(4+5+2)-3)+(6+8)",
//            "(10 -(9-(8-(7))))",
//            "10 - (2 + 3) - ((5-2) - 2)",
//            //"-1 + (2 + 3)",   4
//            "3+2*2",
//            " 3+5 / 2 ",
//            "(1+(4+5*2)-3)/(6+6)",
//            "2*6-(23+7)/(1+2)"
//        };
//        
//        int[] expects = {2, 3, 23, 2, 4, 7, 5, 1, 2};
//        
//        for(int i = 0; i < inputs.length; i++){
//            Assert.assertEquals(" i = "+i, expects[i], sv.evaluate_V(inputs[i]));
//        }
    }
}
