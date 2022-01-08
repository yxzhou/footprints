/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.path;

import tree.TreeNode;

/**
 * _https://www.lintcode.com/problem/1085
 * 
 * Given a binary tree, find the length of the longest path where each node in the path has the same value. This path
 * may or may not pass through the root.
 *
* 
 * 1.The length of path between two nodes is represented by the number of edges between them.
 * 2.The given binary tree has not more than 10000 nodes. The height of the tree is not more than 1000.
 * 
 * Example 1:
 * Input:

              5
             / \
            4   5
           / \   \
          1   1   5
 * Output: 2
 * 
 * Example 2:
 * Input:

              1
             / \
            4   5
           / \   \
          4   4   5
 * Output: 2
 * 
 */
public class LongestUnivaluePath {
    /**
     * 
     * @param root: 
     * @return the length of the longest path where each node in the path has the same value
     */
    public int longestUnivaluePath(TreeNode root) {
        int[] paths = helper(root);
        return paths[0] - 1;
    }

    /**
     * 
     * @param curr
     * @return new int[2]{the longest path in subtree of curr, the longest path by curr node }
     */
    private int[] helper(TreeNode curr){
        if(curr == null){
            return new int[]{0, 0};
        }

        int[] left = helper(curr.left);
        int[] right = helper(curr.right);

        int byLeft = curr.left != null && curr.left.val == curr.val ? left[1] : 0;
        int byRight = curr.right != null && curr.right.val == curr.val ? right[1] : 0;

        int byCurr = 1 + Math.max(byLeft, byRight);
        int longestUnivaluePath = Math.max( 1 + byLeft + byRight, Math.max(left[0], right[0]) );
        
        return new int[]{longestUnivaluePath, byCurr};

    }
}
