package math.expression;

import java.util.Stack;

/**
 * 
 * The structure of Expression Tree is a binary tree to evaluate certain expressions.
 *  All leaves of the Expression Tree have an number string value. All non-leaves of the Expression Tree have an operator string value.
 *  
 *  Now, given an expression array, build the expression tree of this expression, return the root of this expression tree.
 *  
 *  Example
 *  For the expression (2*6-(23+7)/(1+2)) (which can be represented by ["2" "*" "6" "-" "(" "23" "+" "7" ")" "/" "(" "1" "+" "2" ")"]). 
 *  The expression tree will be like

                     [ - ]
                 /          \
            [ * ]              [ / ]
          /     \           /         \
        [ 2 ]  [ 6 ]      [ + ]        [ + ]
                         /    \       /      \
                       [ 23 ][ 7 ] [ 1 ]   [ 2 ] .
 *  After building the tree, you just need to return root node [-].
 *  
 *   Clarification -- See wiki: Expression Tree
 *
 *  Thoughts:
 *     2 + 3 + 4  =>   (2 + 3) + 4 
 *            +
 *         +     4
 *       2   3
 * 
 *     2 + 3 * 4   =>  2 + (3 * 4)
 *              +
 *           2     *
 *               3    4
 * 
 *     2 + 3 * 4 * 5
 *              +
 *           2     *
 *               *    5
 *             3   4
 * 
 *     2 + (3 + 4 * 5) * 6     
 *  
 * 
 * 
 * 
 * 
 */

public class ExpressionTreeBuild {

    /*
     * @param expression: A string array
     * @return: The root of expression tree
     */
    public ExpressionTreeNode build(String[] expression) {
        if(expression == null){
            return null;
        }

        Stack<ExpressionTreeNode> datas = new Stack<>();
        Stack<ExpressionTreeNode> ops = new Stack<>();
        char c;
        for(String symbol : expression){

            switch(symbol){
                case "+":
                case "-":
                    while(!ops.isEmpty() && ops.peek().symbol.charAt(0) != '('){
                        build(datas, ops);
                    }

                    ops.add(new ExpressionTreeNode(symbol));
                    break;
                case "*":
                case "/":
                    if(!ops.isEmpty() && ((c = ops.peek().symbol.charAt(0)) == '*' || c == '/' ) ){
                        build(datas, ops);
                    }

                    ops.add(new ExpressionTreeNode(symbol));
                    break;
                case "(":
                    ops.add(new ExpressionTreeNode(symbol));
                    break;
                case ")":
                    while(ops.peek().symbol.charAt(0) != '('){
                        build(datas, ops);
                    }
                    ops.pop(); // pop the '('

                    break;
                default:
                    datas.add(new ExpressionTreeNode(symbol));
            }
        }

        while(!ops.isEmpty()){
            build(datas, ops);
        }

        return datas.isEmpty() ? null : datas.pop();

    }

    private void build(Stack<ExpressionTreeNode> datas, Stack<ExpressionTreeNode> ops) {
        ExpressionTreeNode root = ops.pop();
        root.right = datas.pop();
        root.left = datas.pop();

        datas.add(root);
    }

    public static void main(String[] args) {
        ExpressionTreeBuild sv = new ExpressionTreeBuild();

        String[] input = {"2", "*", "6", "-", "(", "23", "+", "7", ")", "/", "(", "1", "+", "2", ")"};

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
