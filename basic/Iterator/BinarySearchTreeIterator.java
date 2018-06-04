package fgafa.basic.Iterator;

import java.util.Stack;


/**
 * 
 * Design an iterator over a binary search tree with the following rules:

Elements are visited in ascending order (i.e. an in-order traversal)
next() and hasNext() queries run in O(1) time in average.

Example
For the following binary search tree, in-order traversal by using iterator is [1, 6, 10, 11, 12]

   10
 /    \
1      11
 \       \
  6       12
 */

/*
 * Example of iterate a tree:
 * BSTIterator iterator = new BSTIterator(root);
 * while (iterator.hasNext()) {
 *    TreeNode node = iterator.next();
 *    do something for node
 * } 
 */
/**  
Challenge
Extra memory usage O(h), h is the height of the tree.

Super Star: Extra memory usage O(1)
 *
 */


/* Extra memory usage O(h) */
public class BinarySearchTreeIterator {
    private Stack<TreeNode> stack = new Stack<TreeNode>();

    // @param root: The root of binary tree.
    public BinarySearchTreeIterator(TreeNode root) {
        TreeNode p = root;
        while (null != p) {
            stack.push(p);
            p = p.left;
        }
    }

    // @return: True if there has next node, or false
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    // @return: return next node
    public TreeNode next() {
        if(stack.isEmpty()){
            return null;
        }
        
        TreeNode result = stack.pop();

        TreeNode p = result.right;
        while (null != p) {
            stack.push(p);
            p = p.left;
        }

        return result;
    }
        
        
    /**
     * Definition of TreeNode:
     */
    public class TreeNode {
        public int val;
        public TreeNode left, right;

        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }

}

/* Extra memory usage O(h) */
 class BinarySearchTreeIterator2 {
    private Stack<TreeNode> stack = new Stack<TreeNode>();
    private TreeNode curr = null;

    // @param root: The root of binary tree.
    public BinarySearchTreeIterator2(TreeNode root) {
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

        if(stack.isEmpty()){
            return null;
        }
        
        TreeNode result = stack.pop();
        curr = result.right;
        return result;
    }
        
        
    /**
     * Definition of TreeNode:
     */
    public class TreeNode {
        public int val;
        public TreeNode left, right;

        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }

 }
 
 /* average time O(1) and average extra memory usage O(1) */
 class BinarySearchTreeIterator3 {
     private TreeNode curr = null;
     private TreeNode pre = null;;

     // @param root: The root of binary tree.
     public BinarySearchTreeIterator3(TreeNode root) {
         curr = root;
     }

     // @return: True if there has next node, or false
     public boolean hasNext() {
         return null != curr;
     }

     // @return: return next node
     public TreeNode next() {
         if(null == curr){
             return null;
         }
         
         TreeNode result = null;
         
         //TreeNode left = null;
         while(null != curr.left){
             pre = curr.left;
             //left = curr.left;
             //curr.left = null;
             
             while(null != pre.right){
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
      * Definition of TreeNode:
      */
     public class TreeNode {
         public int val;
         public TreeNode left, right;

         public TreeNode(int val) {
             this.val = val;
             this.left = this.right = null;
         }
     }

  }