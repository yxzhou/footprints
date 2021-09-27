package math.expression;

import math.expression.ExpressionTreeBuild.ExpressionTreeNode;
import util.Misc;

import java.util.ArrayList;
import java.util.Stack;


public class PolishNotation {

    /*
     * Given an expression string array, return the Reverse Polish notation of this expression. (remove the parentheses)
     *
     * Example
     *   For the expression [3 - 4 + 5] (which denote by ["3", "-", "4", "+", "5"]),
     *   return [3 4 - 5 +] (which denote by ["3", "4", "-", "5", "+"])
     */
    public ArrayList<String> convertToRPN(String[] expression) {
        ArrayList<String> result = new ArrayList<String>();
        
        //check
        if(null == expression || 0 == expression.length){
            return result;
        }
        
        ExpressionTreeBuild tree = new ExpressionTreeBuild();
        ExpressionTreeNode root = tree.build(expression);
        
        //postorder
        postorder(root, result);
        
        return result;
    }
    
    private void postorder(ExpressionTreeNode p, ArrayList<String> result) {
        Stack<ExpressionTreeNode> stack = new Stack<ExpressionTreeNode>();
        ExpressionTreeNode node = p, prev = p;
        while (node != null || !stack.isEmpty()) {
          while (node != null) {
            stack.push(node);
            node = node.left;
          }
          
          if (!stack.isEmpty()) {
            node = stack.peek().right;
            if (node == null || node == prev) {
              node = stack.pop();
              
              result.add(node.symbol);
              
              prev = node;
              node = null;
            }
          }
      
        }
      }
    
    /*
     * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
     * 
     * Valid operators are +, -, *, /. Each operand may be an integer or another expression.
     * 
     * Some examples:
     *   ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
     *   ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
     */
    public int evalRPN(String[] tokens) {
    	//check
    	if(null == tokens)
    		return Integer.MAX_VALUE;
    	
        Stack<String> stack = new Stack<>(); // store the integer
        
        //assume the tokens are valid operators or valid integer
        int tmp = 0, right, left;
        for(String token : tokens){
        	if(token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")){
                right = Integer.parseInt(stack.pop());  
                left = Integer.parseInt(stack.pop()); 
                
    	    	switch(token){
		    	case "+" :
		    		tmp = left + right;  
		    		break;
		    	case "-" :
	                tmp = left - right;
		    		break;
		    	case "*" :
		    		tmp =  left * right;
		    		break;
		    	case "/" :
		    		if( 0 == right ){
                        throw new IllegalArgumentException("");
		    		}
		    		
	                tmp = left / right;
	                break;
		    	}
    	    	
		    	stack.push(String.valueOf(tmp));
        	}else {
        		stack.push(String.valueOf(token));
        	}
        }
        
        return Integer.valueOf(stack.pop());
    }
    
    /*   ??  need check/valid the input*/
    public int evalRPN_n(String[] tokens) {
        int result = 0;

        if(null == tokens){
            return result;
        }
        
        Stack<String> stack = new Stack<String>();
        int right, left;
        for(String token : tokens){
            if(isOperator(token)){
                right = Integer.valueOf(stack.pop());
                left = Integer.valueOf(stack.pop());
                stack.add(String.valueOf(calculator(left, token, right)));
            }else{
                stack.add(token);
            }
        }
        
        return Integer.valueOf(stack.pop());
    }
    
    private boolean isOperator(String str){
        return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") ;
    }
    
    private int calculator( int left, String operator, int right){

        switch(operator){
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                if( 0 == right ){
                    throw new IllegalArgumentException("");
                }
                return left / right;
            default:
                return 0;
        }
    }
    
    
    
    
	public static void main(String[] args) {
		System.out.println("===start===");
		PolishNotation sv = new PolishNotation();
		
		String[][] input = {
				{"2", "1", "+", "3", "*"},
				{"4", "10", "5", "/", "/", "2", "/"}
		};
		
		int[] results = {
				9,
				1
		};
		
		for( int i = 0; i< input.length; i++){
			String[] tokens = input[i];
			int result = results[i];
			
			System.out.println("input: " + Misc.array2String(tokens));
			System.out.println("output: " + sv.evalRPN(tokens) + " \t result: " + result);
		}
		
		
		System.out.println("===end===");
	}
    
}
