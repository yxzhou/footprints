/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.util.Stack;

/**
 * _https://www.lintcode.com/problem/1188
 * 
 * 
 * Given a binary search tree with non-negative values, find the minimum absolute difference between values of any two nodes.
 *
 * There are at least two nodes in this BST.
 * 
 * Example
 * Input:
 * 
 *    1
 *     \
 *      3
 *     /
 *    2
 * 
 * Output: 1
 *  Explanation: The minimum absolute difference is 1, which is the difference between 2 and 1 (or between 2 and 3).
 * 
 */
public class BSTMinDifference {
    
    /**
     * @param root: the root
     * @return the minimum absolute difference between values of any two nodes
     */
    public int getMinimumDifference(TreeNode root) {
        int[] result = helper(root);

        return result[0];
    }

    /**
    *
    * @param curr, the subtree root 
    * @return new int[]{the minimum difference in the subtree, the maximum value in the subtree, the minimum value in the subtree}
    */
    private int[] helper(TreeNode curr){
        if(curr == null){
            return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE};
        }

        int[] left = helper(curr.left);
        int[] right = helper(curr.right);

        int minDiff = Integer.MAX_VALUE;
        if(curr.left != null){
            minDiff = Math.min( Math.min(minDiff, curr.val - left[1]), left[0]);
        }
        if(curr.right != null){
            minDiff = Math.min( Math.min(minDiff, right[2] - curr.val), right[0]);
        }

        return new int[]{minDiff, Math.max(curr.val, right[1]), Math.min(curr.val, left[2])};
    }
    
    
    /**
     * @param root: the root
     * @return the minimum absolute difference between values of any two nodes
     */
    public int getMinimumDifference_InorderTraversal(TreeNode root) {
        int result = Integer.MAX_VALUE;
        
        TreeNode curr = root;
        Stack<TreeNode> stack = new Stack<>();
        
        TreeNode pre = null;
        while(curr != null || !stack.isEmpty()){
            if(curr == null){
                curr = stack.pop();
                
                if(pre != null){
                    result = Math.min(result, curr.val - pre.val);
                }
                pre = curr;
                curr = curr.right;
            }else{
                stack.add(curr);
                curr = curr.left;
            }
        }

        return result;
    }
    
}
