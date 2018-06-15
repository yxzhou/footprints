package fgafa.math.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

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
        int curr = 0;
        char operator = '+';
        
        for(char c : expression.toCharArray()){
            if(c >= '0' && c <= '9'){
                curr = curr * 10 + c - '0';
            }else if(c == '+' || c == '-'){
                result = calculate( result, operator, curr);
                operator = c;
                curr = 0;
            } // else ignore, such as space
        }
        
        result = calculate( result, operator, curr); //**
        return (int)result;
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
                    while(!oprators.isEmpty()&&oprators.peek() != '('){
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

    private void calculate(Stack<Character> op, Stack<Long> data){
        
        Long right = data.pop();
        Long left = data.pop();

        data.push(calculate(left, op.pop(), right));
    }
    
    public static void main(String[] args) {
        ExpressionEvaluation sv = new ExpressionEvaluation();

        System.out.println("\n================= I");
        String[] input_I = { 
                    "1 + 1", // = 2
                    " 2-1 + 2 ", // = 3
                    " 2 " // 2
        };

        for (String s : input_I) {
            System.out.println(String.format(" %s = %d ", s, sv.evaluate_I(s)));
        }
         
        System.out.println("\n================= II");
        List<String> input_II = new ArrayList<>(Arrays.asList(input_I));
        
        input_II.addAll(new ArrayList<>(Arrays.asList(
                    "3+2*2", // = 7 
                    " 3/2 ", // = 1 
                    "3+5 / 2 ", // = 5
                    "1*2 + 3", //5
                    "3-2*2*2-3+12", // = 4 
                    "6/2/3", // = 1 
                    "3-2*2*2-3+12-6/2/3" // = 3 
        )));

        for (String s : input_II) {
            System.out.println(String.format(" %s = %d ", s, sv.evaluate_II(s)));
        }
        
        System.out.println("\n================= III");
        List<String> input_III = new ArrayList<>(Arrays.asList(input_I));
        input_III.addAll(Arrays.asList(
                    "(1+(4+5+2)-3)+(6+8)", // = 23
                    "(6-(1-2))" // 7
        ));

        for (String s : input_III) {
            System.out.println(String.format(" %s = %d", s, sv.evaluate_III(s)));
        }
        

        System.out.println("\n================= IV");
        List<String> input_IV = new ArrayList<>();
        input_IV.addAll(input_II);
        input_IV.addAll(input_III);
        input_IV.addAll(Arrays.asList(
                    "(1+(4+5*2)-3)/(6+6)", // = 1
                    "2*6-(23+7)/(1+2)" // 2
        ));

        for (String s : input_IV) {
            System.out.println(String.format(" %s = %d", s, sv.evaluate_IV(s)));
        }             
    }

}
