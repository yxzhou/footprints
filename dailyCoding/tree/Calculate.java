package fgafa.dailyCoding.tree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 *
 * Suppose an arithmetic expression is given as a binary tree. Each leaf is an integer and each internal node is one of '+', '−', '∗', or '/'.
 Given the root to such a tree, write a function to evaluate it.

 For example, given the following tree:

      *
     / \
   +    +
  / \  / \
 3  2  4  5

 You should return 45, as it is (3 + 2) * (4 + 5).
 *
 * Tags: Microsoft
 */

public class Calculate {

    class Node{
        String value;
        Node left;
        Node right;
    }


    static final Set<String> operators = new HashSet<>(Arrays.asList(new String[]{"+", "-", "*", "/"}));

    public int calcuate(Node root){
        if( !operators.contains(root.value) ){
            return Integer.valueOf(root.value);
        }

        int left = calcuate(root.left);
        int right = calcuate(root.right);

        int result = 0;
        switch (root.value){
        case "+":
            result = left + right;
            break;
        case "-":
            result = left - right;
            break;
        case "*":
            result = left * right;
            break;
        case "/":
            result = left / right;
            break;
        }

        return result;
    }


}
