package math.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Test;

public class ExpressionEvaluation {

    /**
     * Evaluate a simple expression string. 
     * The expression string contains:
     *    non-negative integers,
     *    +, -  operators,
     *    empty spaces. . 
     * 
     * You may assume that the given expression is always valid. 
     *      
     * Some examples: 
     * "3+2+2" = 7 
     */
    
    /* Time O(n),  Space O(1)*/
    public int evaluate_I(String expression) {
        if(null == expression || 0 == expression.length()){
            throw new IllegalArgumentException();
        }
        
        long result = 0;
        int n = 0;
        char operator = '+';
        
        for(char c : expression.toCharArray()){
            if(c >= '0' && c <= '9'){
                n = n * 10 + c - '0';
            }else if(c == '+' || c == '-'){
                result = calculate( result, operator, n);
                operator = c;
                n = 0;
            } // else ignore, such as space
        }
        
        result = calculate( result, operator, n); //**
        return (int)result;
    }
    
    /**
     * Evaluate a simple expression string. 
     * The expression string contains:
     *    non-negative integers,
     *    +, -, *, / operators,
     *    empty spaces. 
     * 
     * The integer division should truncate toward zero. 
     * You may assume that the given expression is always valid. 
     * 
     * Some examples: 
     * "3+2*2" = 7 
     * " 3/2 " = 1 
     * " 3+5 / 2 " = 5
     */
    /* Time O(n),  Space O(1)*/
    public int evaluate_II(String expression) {
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
     * "1 + 1" = 2 
     * " 2-1 + 2 " = 3 
     * "(1+(4+5+2)-3)+(6+8)" = 23
     * "a - (b + c) - ((d-f) - e)"
     */
    /*  Time O(n),  Space O(n)*/
    public int evaluate_III(String expression) {
        if(null == expression || 0 == expression.length()){
            throw new IllegalArgumentException();
        }
        
        Stack<Character> oprators = new Stack<>();
        Stack<Long> datas = new Stack<>();
        
        expression += '+';
        
        long result = 0;
        long curr = 0;
        char preOperator = '+';
        
        for(char c : expression.toCharArray()){
            if(c >= '0' && c <= '9'){
                curr = curr * 10 + c - '0';
            }else if(c == '+' || c == '-' ){
                result = calculate( result, preOperator, curr);
                curr = 0;
                preOperator = c;
                
            }else if(c == '('){
                datas.push(result);
                oprators.push(preOperator);
                
                result = 0;
                curr = 0;
                preOperator = '+';
            }else if(c == ')'){
                result = calculate( result, preOperator, curr);
                
                curr = result;
                result = datas.pop();
                preOperator = oprators.pop();
            }// else ignore
        }
        
        return (int)result;
    }

    /**
     *
     * This problem was asked by Facebook.
     *
     * Given a string consisting of parentheses, single digits, and positive and negative signs, convert the string into a mathematical expression to obtain the answer.
     *
     * Don't use eval or a similar built-in parser.
     *
     * For example, given '-1 + (2 + 3)', you should return 4.
     *
     */

    public int evaluate_III_2(String expression) {
        if(null == expression || expression.trim().isEmpty()){
            return 0;
        }

        long result = 0;

        expression += ')';

        boolean sign = true; //default it's positive
        long n = 0;

        Stack<Long> stack = new Stack<>();

        for(char c : expression.toCharArray()){
            if(c >= '0' && c <= '9'){
                n = n * 10 + c - '0';
            }else if(c == '+' || c == '-'){
                result += (sign ? 1 : -1) * n;
                sign = ( c == '+');
                n = 0;
            }else if(c == '('){
                stack.add(result);
                stack.add(sign ? 1L : -1L);

                result = 0;
                sign = true;

            }else if(c == ')'){
                result += (sign ? 1 : -1) * n;
                sign = true;
                n = 0;

                if(!stack.isEmpty()){
                    result *= stack.pop();
                }

                if(!stack.isEmpty()){
                    result += stack.pop();
                }
            } //else,  ignore the other characters, such as space
        }

        return (int)result;
    }

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
     * "3+2*2" = 7 
     * " 3+5 / 2 " = 5
     * "(1+(4+5*2)-3)/(6+6)" = 1
     * "2*6-(23+7)/(1+2)" = 2
     */

    /* same as ExpressionTreeBuild.build()
     * Time O(n),  Space O(n)*/
    public int evaluate_IV(String expression) {
        if(null == expression || 0 == expression.length()){
            throw new IllegalArgumentException();
        }
        
        Stack<Character> oprators = new Stack<>();
        Stack<Long>  datas= new Stack<>();
        
        expression += '+';
        
        for(int i = 0; i < expression.length(); i++){
            char c = expression.charAt(i);
                        
            if(c >= '0' && c <= '9'){
                int j = getEndIndexOfNum(expression, i);
                datas.push(Long.valueOf(expression.substring(i, j)));
                i = j - 1;
            }else if(c == '+' || c == '-' || c == '*' || c == '/'){
                if(c == '+' || c == '-'){
                    while(!oprators.isEmpty() && oprators.peek() != '('){
                        calculate(oprators, datas);
                    }
                }else{ //c == '*' || c == '/' 
                    while(!oprators.isEmpty()&&(oprators.peek() == '*'||oprators.peek() == '/')){
                        calculate(oprators, datas);
                    }
                }
                
                oprators.push(c);
            }else if(c == '('){
                oprators.push(c);
            }else if(c == ')'){
                while(oprators.peek() != '('){
                    calculate(oprators, datas);
                }
                oprators.pop(); // pop '('
            }
            // else ignore
        }

        return datas.isEmpty()? 0 : datas.pop().intValue();
    }

    private int getEndIndexOfNum(String expression, int start){
        int end = start;
        for( ; end < expression.length(); end++ ){
            char c = expression.charAt(end);
            if(c < '0' || c > '9'){
                break;
            }
        }

        return end;
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

    private void calculate(Stack<Character> op, Stack<Long> data){
        
        Long right = data.pop();
        Long left = data.isEmpty() ? 0L : data.pop();

        data.push(calculate(left, op.pop(), right));
    }

    @Test
    public void test() {

        System.out.println("\n================= I");

        Assert.assertEquals(2, evaluate_I("1 + 1"));
        Assert.assertEquals(3, evaluate_I("2-1 + 2"));
        Assert.assertEquals(2, evaluate_I("2"));

         
        System.out.println("\n================= II");

        Assert.assertEquals(2, evaluate_II("1 + 1"));
        Assert.assertEquals(3, evaluate_II("2-1 + 2"));
        Assert.assertEquals(2, evaluate_II("2"));

        Assert.assertEquals(7, evaluate_II("3+2*2"));
        Assert.assertEquals(1, evaluate_II(" 3/2"));
        Assert.assertEquals(5, evaluate_II("3+5 / 2"));
        Assert.assertEquals(5, evaluate_II("1*2 + 3"));
        Assert.assertEquals(4, evaluate_II("3-2*2*2-3+12"));
        Assert.assertEquals(1, evaluate_II("6/2/3"));
        Assert.assertEquals(3, evaluate_II("3-2*2*2-3+12-6/2/3"));

        
        System.out.println("\n================= III");

        Assert.assertEquals(2, evaluate_III("1 + 1"));
        Assert.assertEquals(3, evaluate_III("2-1 + 2"));
        Assert.assertEquals(2, evaluate_III("2"));

        Assert.assertEquals(23, evaluate_III("(1+(4+5+2)-3)+(6+8)"));
        Assert.assertEquals(7, evaluate_III("(6-(1-2))"));
        Assert.assertEquals(-12, evaluate_III("-12"));
        Assert.assertEquals(21, evaluate_III("-1+22"));
        Assert.assertEquals(6, evaluate_III("1-(2-(3 + 4))"));

        System.out.println("\n================= III_2");

        Assert.assertEquals(2, evaluate_III_2("1 + 1"));
        Assert.assertEquals(3, evaluate_III_2("2-1 + 2"));
        Assert.assertEquals(2, evaluate_III_2("2"));

        Assert.assertEquals(23, evaluate_III_2("(1+(4+5+2)-3)+(6+8)"));
        Assert.assertEquals(7, evaluate_III_2("(6-(1-2))"));
        Assert.assertEquals(7, evaluate_III_2("((6-(1-2)))"));
        Assert.assertEquals(-12, evaluate_III_2("-12"));
        Assert.assertEquals(21, evaluate_III_2("-1+22"));
        Assert.assertEquals(6, evaluate_III_2("1-(2-(3 + 4))"));


        System.out.println("\n================= IV");

        Assert.assertEquals(2, evaluate_IV("1 + 1"));
        Assert.assertEquals(3, evaluate_IV("2-1 + 2"));
        Assert.assertEquals(2, evaluate_IV("2"));

        Assert.assertEquals(7, evaluate_IV("3+2*2"));
        Assert.assertEquals(1, evaluate_IV(" 3/2"));
        Assert.assertEquals(5, evaluate_IV("3+5 / 2"));
        Assert.assertEquals(5, evaluate_IV("1*2 + 3"));
        Assert.assertEquals(4, evaluate_IV("3-2*2*2-3+12"));
        Assert.assertEquals(1, evaluate_IV("6/2/3"));
        Assert.assertEquals(3, evaluate_IV("3-2*2*2-3+12-6/2/3"));

        Assert.assertEquals(23, evaluate_IV("(1+(4+5+2)-3)+(6+8)"));
        Assert.assertEquals(7, evaluate_IV("(6-(1-2))"));
        Assert.assertEquals(-12, evaluate_IV("-12"));
        Assert.assertEquals(21, evaluate_IV("-1+22"));
        Assert.assertEquals(6, evaluate_IV("1-(2-(3 + 4))"));

        Assert.assertEquals(1, evaluate_IV("(1+(4+5*2)-3)/(6+6)"));
        Assert.assertEquals(2, evaluate_IV("2*6-(23+7)/(1+2)"));

        System.out.println("\n================= end ==");
    }

}
