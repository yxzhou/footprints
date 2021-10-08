/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.iterator;

/**
 * Change the BST structure
 * 
 * average time O(1) and extra memory usage O(1) 
 */
class BSTIterator3 {

    private TreeNode curr = null;
    private TreeNode pre = null;

    // @param root: The root of binary tree.
    public BSTIterator3(TreeNode root) {
        curr = root;
    }

    // @return: True if there has next node, or false
    public boolean hasNext() {
        return null != curr;
    }

    // @return: return next node
    /** average time O(1) */
    public TreeNode next() {
        if (null == curr) {
            return null;
        }
        TreeNode result = null;
        //TreeNode left = null;
        while (null != curr.left) {
            pre = curr.left;
            //left = curr.left;
            //curr.left = null;
            while (null != pre.right) {
                pre = pre.right;
            }
            pre.right = curr;
            curr = curr.left;
            //curr = left;
        }
        result = curr;
        curr = curr.right;
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
