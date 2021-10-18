/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math.expression;

import java.util.StringTokenizer;

/**
 *
 * Given a string represents a Boolean expression only including "true","false","or","and".
 * Your task is to calculate the result of the expression by returning "true" or "false".
 * If the expression is not valid, you need to return a string "error".
 * 
 * Notes:
 *   We promise that there are no more than 10000 elements in the expression.
 *   There are only 4 kinds of elements in the expression : "true", "false", "and", "or".
 * 
 * Example 1
 *   Input： "true and false"
 *   Output： "false"
 * 
 * Example 2
 *   Input： "true or"
 *   Output： "error"
 * 
 * Example 3
 *   Input: "false and true or true or false and false"
 *   Output: "true"
 * 
 * Solutions:
 *   Precedence order:  logic AND > logic OR
 *     "false and true or true or false and false" -> " false and true OR true OR false and false" -> " false OR true OR ..."      
 * 
 *   For Example:    "false     and     true    or     true    or  false   and     false"
 *    curr token:       F       A       T       O      T       O    F      A        F  
 *     pre token:   or     F        A       T       O      T       O    F      A        F
 *        result:    T     F                        T              T    F               F
 */
public class BooleanExpressionEvaluation {
    /**
     * 
     *
     * @param expression: a string that representing an expression
     * @return: the result of the expression
     */
    public String evaluation(String expression) {
        if(expression == null || expression.trim().isEmpty() ){
            return "true";
        }

        boolean result = true;  
        //int o = 0; // "or"
        //int a = 1; // "and"
        //int t = 3; // "true"
        //int f = 4; // "false"

        StringTokenizer tokens = new StringTokenizer(expression);
        String curr; //curr token
        int pre = 0; //pre token

        while(tokens.hasMoreTokens()){
            curr = tokens.nextToken();

            switch(curr){
                case "or":
                    if(pre == 0 || pre == 1){ //for case:  "or or", "and or"   
                        return "error";
                    }

                    if(result){ // for case:  false || false || true || ...
                        return "true";
                    }

                    result = true;
                    pre = 0;
                    break;
                case "and":
                    if(pre == 0 || pre == 1){ //for case:  "or and", "and and"   
                        return "error";
                    }
                    pre = 1;
                    break;
                case "true":
                    if(pre == 3 || pre == 4){ //for case:  "true true", "false true"   
                        return "error";
                    }

                    //"result && true" -> result needn't change , "result || true" -> result = true, if result is false, it would trigger "error" when check the "||"
                    
                    pre = 3;
                    break;
                case "false":
                    if(pre == 3 || pre == 4){ //for case:  "true false", "false false"  
                        return "error";
                    }

                    //"preValue && false" -> false,  "preValue || false" -> preValue 
                    //if(pre == a){
                        result = false ;
                    //}
                    pre = 4;
                    break;
                default: //only support the above 4 kinds of elements 
                    return "error";
            }
        }

        if(pre == 0 || pre == 1){
            return "error";
        }

        return result? "true" : "false";
    }
}
