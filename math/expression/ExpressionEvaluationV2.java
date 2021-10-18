/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math.expression;

import java.util.Stack;

/**
 *
 *  Given an expression string array, return the final result of this expression
 *  
 *  The expression contains only integer, +, -, *, /, (, ).
 *  
 *  Example 1:
 *  For the expression `2*6-(23+7)/(1+2)`,
 *  Input: ["2", "*", "6", "-", "(","23", "+", "7", ")", "/", "(", "1", "+", "2", ")"]
 *  Output: 2
 *  
 * Example 2:
 *  For the expression `4-(2-3)*2+5/5`,
 *  Input: ["4", "-", "(", "2","-", "3", ")", "*", "2", "+", "5", "/", "5"]
 *  Output: 7
 * 
 */
public class ExpressionEvaluationV2 {
 
    /**
     * @param expression: a list of strings
     * @return: an integer
     */
    public int evaluateExpression(String[] expression) {
        if (expression == null) {
            throw new IllegalArgumentException();
        }

        String curr;

        Stack<Long> datas = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (int i = 0; i < expression.length; i++) {
            curr = expression[i];

            switch (curr) {
                case "+":
                case "-":
                    while (!operators.isEmpty() && !operators.peek().equals("(")) {
                        calculate(datas, operators);
                    }
                    operators.add(curr);
                    break;
                case "*":
                case "/":
                    while (!operators.isEmpty() && (operators.peek().equals("*") || operators.peek().equals("/"))) {
                        calculate(datas, operators);
                    }
                    operators.add(curr);
                    break;
                case "(":
                    operators.add(curr);
                    break;
                case ")":
                    while (!operators.isEmpty() && !operators.peek().equals("(")) {
                        calculate(datas, operators);
                    }
                    operators.pop(); // pop the "("
                    break;
                default:
                    datas.add(Long.valueOf(curr));

            }
        }

        while (!operators.isEmpty()) {
            calculate(datas, operators);
        }

        return datas.isEmpty() ? 0 : datas.pop().intValue();
    }

    private void calculate(Stack<Long> datas, Stack<String> operators) {
        long right = datas.pop();
        long left = datas.pop();
        String operator = operators.pop();

        long r;
        switch (operator) {
            case "-":
                r = left - right;
                break;
            case "*":
                r = left * right;
                break;
            case "/":
                r = left / right;
                break;
            default: // '+'
                r = left + right;
        }

        datas.add(r);
    }

    
}
