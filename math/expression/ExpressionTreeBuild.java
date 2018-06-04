package fgafa.math.expression;

import java.util.Stack;

/**
 * 
 * The structure of Expression Tree is a binary tree to evaluate certain expressions.
	All leaves of the Expression Tree have an number string value. All non-leaves of the Expression Tree have an operator string value.
	
	Now, given an expression array, build the expression tree of this expression, return the root of this expression tree.
	
	Example
	For the expression (2*6-(23+7)/(1+2)) (which can be represented by ["2" "*" "6" "-" "(" "23" "+" "7" ")" "/" "(" "1" "+" "2" ")"]). 
	The expression tree will be like
	
	                 [ - ]
	             /          \
	        [ * ]              [ / ]
	      /     \           /         \
	    [ 2 ]  [ 6 ]      [ + ]        [ + ]
	                     /    \       /      \
	                   [ 23 ][ 7 ] [ 1 ]   [ 2 ] .
	After building the tree, you just need to return root node [-].
	
	Clarification
	See wiki:
	Expression Tree
 *
 */

public class ExpressionTreeBuild {


    /**
     * @param expression: A string array
     * @return: The root of expression tree
     */
    public ExpressionTreeNode build(String[] expression) {
		//check
		if(null == expression || 0 == expression.length){
			return null;
		}
		
        Stack<ExpressionTreeNode> op   = new Stack<ExpressionTreeNode>();
        Stack<ExpressionTreeNode> data = new Stack<ExpressionTreeNode>();
        for(String tmp : expression){
            char firstc = tmp.charAt(0);
            if(firstc<='9'&&firstc>='0'){
                //System.out.println("add data "+tmp);
                data.push(new ExpressionTreeNode(tmp));
            }else{
                //System.out.println("get op "+ tmp);
                switch(firstc){
                    case '(':
                        op.push(new ExpressionTreeNode(tmp));
                        break;
                    case '+':
                    case '-':
                        while(!op.isEmpty()&&op.peek().symbol.charAt(0)!='('){
                        	build(op, data);
                        }
                        op.push(new ExpressionTreeNode(tmp));
                        break;
                    case '*':
                    case '/':
                        while(!op.isEmpty()&&(op.peek().symbol.charAt(0)=='*'||op.peek().symbol.charAt(0)=='/')){
                        	build(op, data);
                        }
                        op.push(new ExpressionTreeNode(tmp));
                        break;
                    case ')':
                        while(op.peek().symbol.charAt(0)!='('){
                        	build(op, data);
                        }
                        op.pop();
                }
            }
        }
        while(!op.isEmpty()){
        	build(op, data);
        }

        return data.isEmpty()? null :data.pop();
    }
    
    private void build(Stack<ExpressionTreeNode> op, Stack<ExpressionTreeNode> data){
        ExpressionTreeNode opnode = op.pop();

        opnode.right = data.pop();  
        opnode.left = data.pop();
        
        data.push(opnode);
    }
    
    public static void main(String[] args){
    	ExpressionTreeBuild sv = new ExpressionTreeBuild();
    	
    	String[] input = {"2","*","6","-","(","23","+","7",")","/","(","1","+","2",")"};
    	
    	sv.build(input);
    	
    }
    
    
	/**
	 * Definition of ExpressionTreeNode:
	 */
	public class ExpressionTreeNode {
		public String symbol;
		public ExpressionTreeNode left, right;

		public ExpressionTreeNode(String symbol) {
			this.symbol = symbol;
			this.left = this.right = null;
		}
	}
}
