package tree;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 
 * One way to serialize a binary tree is to use pre-order traversal. When we encounter a non-null node, we record the node's value. If it is a null node, we record using a sentinel value such as #.

         _9_
        /   \
       3     2
      / \   / \
     4   1  #  6
    / \ / \   / \
    # # # #   # #
    For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", where # represents a null node.
    
    Given a string of comma separated values, verify whether it is a correct preorder traversal serialization of a binary tree. Find an algorithm without reconstructing the tree.
    
    Each comma separated value in the string must be either an integer or a character '#' representing null pointer.
    
    You may assume that the input format is always valid, for example it could never contain two consecutive commas such as "1,,3".
    
    Example 1:
    "9,3,4,#,#,1,#,#,2,#,6,#,#"
    Return true
    
    Example 2:
    "1,#"
    Return false
    
    Example 3:
    "9,#,#,1"
    Return false
 *
 */

public class VerifyPreorderSerialization {

    public boolean isValidSerialization(String preorder) {
        Stack<String> stack = new Stack<>();
        
        StringTokenizer st = new StringTokenizer(preorder, ",");
        
        String node;
        while(st.hasMoreTokens()){
            node = st.nextToken();
            
            while(node.equals("#") && !stack.isEmpty() && "#".equals(stack.peek())){
                stack.pop();
                stack.pop();
            }
            
            stack.push(node);
        }
        
        return stack.isEmpty();
    }
    
    public boolean isValidSerialization_n(String preorder) {
        int diff = 1;
        StringTokenizer st = new StringTokenizer(preorder, ",");
        
        String node;
        while(st.hasMoreTokens()){
            node = st.nextToken();
            
            diff--;
            
            if(diff < 0){
                return false; 
            }
            
            if(!node.equals("#")){
                diff += 2;
            }
        }
        
        return diff == 0;
    }
    
    public static void main(String[] args) {
        VerifyPreorderSerialization sv = new VerifyPreorderSerialization();
        
        String[] input = {
                    "9,3,4,#,#,1,#,#,2,#,6,#,#",
                    "1,#",
                    "9,#,#,1"
        };

        for(String preorder : input){
            System.out.println(String.format("%s -- %b, %b", preorder, sv.isValidSerialization(preorder), sv.isValidSerialization_n(preorder)));
        }
        
    }

}
