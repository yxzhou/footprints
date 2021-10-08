/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.iterator;

import java.util.Stack;

/* average time O(1) and extra memory usage O(h) */
class BSTIterator2 {

    private Stack<TreeNode> stack = new Stack<TreeNode>();
    private TreeNode curr = null;

    // @param root: The root of binary tree.
    public BSTIterator2(TreeNode root) {
        curr = root;
    }

    // @return: True if there has next node, or false
    public boolean hasNext() {
        return null != curr || !stack.isEmpty();
    }

    // @return: return next node
    public TreeNode next() {
        while (null != curr) {
            stack.push(curr);
            curr = curr.left;
        }
        if (stack.isEmpty()) {
            return null;
        }
        TreeNode result = stack.pop();
        curr = result.right;
        return result;
    }

    /**
    * pre-defined
    *  Definition of TreeNode:
    */
    public class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }
    
}
