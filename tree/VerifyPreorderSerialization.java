package tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * _https://www.lintcode.com/problem/1066
 * 
 * One way to serialize a binary tree is to use pre-order traversal. When we encounter a non-null node, we record the
 * node's value. If it is a null node, we record using a sentinel value such as #.

         _9_
        /   \
       3     2
      / \   / \
     4   1  #  6
    / \ / \   / \
    # # # #   # #
    
 * For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", where # represents a
 * null node.
 *
 * Given a string of comma separated values, verify whether it is a correct preorder traversal serialization of a binary
 * tree. Find an algorithm without reconstructing the tree.
 *
 * Each comma separated value in the string must be either an integer or a character '#' representing null pointer.
 *
 * You may assume that the input format is always valid, for example it could never contain two consecutive commas such
 * as "1,,3".
 * 
 * Example 1: 
 * Input: tree = "#" 
 * Output: true
 * Explanation: Empty tree is legal.
 *
 * Example 2: 
 * Input: tree = "9,3,4,#,#,1,#,#,2,#,6,#,#" 
 * Output: true
 *
 * Example 3: 
 * Input: tree = "1,#" 
 * Output: false
 * Explanation: It's not a complete tree.
 *
 * Example 4: 
 * Input: tree = "9,#,#,1" 
 * Output: false
 * Explanation: It's not a tree.
 *
 *
 */

public class VerifyPreorderSerialization {

    /**
     * @param preorder: a string
     * @return: return a bool
     */
    public boolean isValidSerialization(String preorder) {
        if(preorder == null  ){
            return false;
        }

        String[] tokens = preorder.split(",");

        Queue<String> queue = new LinkedList<>();
        queue.add(tokens[0]);

        int n = tokens.length;
        int i = 1;
        while(!queue.isEmpty()){
            if(!queue.poll().equals("#") ){
                if(i + 1 < n){
                    queue.add(tokens[i++]);
                    queue.add(tokens[i++]);
                }else{
                    return false;
                }
            }
        }

        return i == n;
    }
    
    public boolean isValidSerialization_n(String preorder) {
        if(preorder == null  ){
            return false;
        }
        
        /**
         * The split() method is preferred and recommended even though it is comparatively slower than StringTokenizer.
         * This is because it is more robust and easier to use than StringTokenizer.
         */
        //StringTokenizer st = new StringTokenizer(preorder, ",");
        String[] tokens = preorder.split(",");
        
        int diff = 1;
        for(int i = 0; i < tokens.length; i++){
            diff--;
            
            if(diff < 0){
                return false; 
            }
            
            if(!tokens[i].equals("#")){
                diff += 2;
            }
        }
        
        return diff == 0;
    }
    
    public static void main(String[] args) {
        VerifyPreorderSerialization sv = new VerifyPreorderSerialization();
        
        String[] input = {
            "",
            "#",
            "1",
            "9,3,4,#,#,1,#,#,2,#,6,#,#",
            "1,#",
            "9,#,#,1"
        };

        for(String preorder : input){
            System.out.println(String.format("\n%s -- %b, %b", preorder, sv.isValidSerialization(preorder), sv.isValidSerialization_n(preorder)));
        }
        
    }

}
