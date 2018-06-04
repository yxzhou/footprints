package fgafa.tree.pathSum;

import fgafa.tree.TreeNode;

/*
 * 
 * Given a binary tree, find the maximum path sum from root to leaf.
 * 
 * 
 * For example:
 * Given the below binary tree,
 * 
 *        1
 *       / \
 *      2   3
 * Return 4. 1->3
 * 
 *        1
 *       / \
 *     -2  -3   
 * Return 1. 1->1
 * 
 * 
 * bottom-up
 * Time O(n) Space O(1) 
 * 
 * test cases
 *  1,  null == root
 *  2,  null == root.left && null == root.right
 *  3,  null == root.left && null != root.right
 *  4,  null != root.left && null != root.right
 * 
 */
public class MaxPathSumRoot2AnyNode
{

    public int maxPathSum(TreeNode root){
        if(null == root){
            return Integer.MIN_VALUE;
        }
        if(null == root.left && null == root.right){
            return root.val > 0 ? root.val : 0;
        }
        
        return root.val + Math.max(0, Math.max(maxPathSum(root.left), maxPathSum(root.right)));     
    }

  /**
   * @param args
   */
  public static void main(String[] args) {
    

  }

}
