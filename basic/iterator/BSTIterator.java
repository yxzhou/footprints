package basic.iterator;

import java.util.Stack;


/**
 * 
 * Design an iterator over a binary search tree with the following rules:
 *   Elements are visited in ascending order (i.e. an in-order traversal)
 *   next() and hasNext() queries run in O(1) time in average.
 * 
 * Example 1
 *  Input {10,1,11,#,6,#,12}

   10
 /    \
1      11
 \       \
  6       12
 *  Output  [1, 6, 10, 11, 12]

 * Challenge
 *   Extra memory usage O(h), h is the height of the tree.
 * 
 * Super Star: Extra memory usage O(1)
 *
 */


/* average time O(1) and extra memory usage O(h) */
public class BSTIterator {
    private Stack<TreeNode> stack = new Stack<TreeNode>();

    // @param root: The root of binary tree.
    public BSTIterator(TreeNode root) {
        fillLefts(root);
    }

    // @return: True if there has next node, or false
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    // @return: return next node
    public TreeNode next() {
        if(hasNext()){
            TreeNode result = stack.pop();
            fillLefts(result.right);
            return result;
        }
        
        return null;
    }
    
    private void fillLefts(TreeNode node){
        while (null != node) {
            stack.push(node);
            node = node.left;
        }
    }

    /**
    * pre-defined
    *  Definition of TreeNode:
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

 
 