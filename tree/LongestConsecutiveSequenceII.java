/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

/**
 *_https://www.lintcode.com/problem/614
 * 
 * Given a binary tree, find the length(number of nodes) of the longest consecutive sequence(Monotonic and adjacent node
 * values differ by 1) path. The path could be start and end at any node in the tree
 * 
 * Example 1:
 * Input: {1,2,0,3}
 * Output: 4
 * Explanation:
    1
   / \
  2   0
 /
3
0-1-2-3
 * 
 * Example 2:
 * Input: {3,2,2}
 * Output: 2
 * Explanation:
    3
   / \
  2   2
2-3
 * 
 */
public class LongestConsecutiveSequenceII {
    /**
     * @param root: the root of binary tree
     * @return: the length of the longest consecutive sequence path
     */
    public int longestConsecutive2(TreeNode root) {
        if(root == null){
            return 0;
        }

        int[] paths = helper(root);
        return paths[0];
    }

    /**
    *
    * @return a int array, {longest path, local path in ascend, local path in descend   } 
    */
    private int[] helper(TreeNode node){
        int[] paths = {1, 1, 1};

        int[] childPaths;

        if(node.left != null){
            childPaths = helper(node.left);

            paths[2] = (node.left.val - 1 == node.val ? childPaths[2] + 1 : 1);
            paths[1] = (node.left.val + 1 == node.val ? childPaths[1] + 1 : 1);
            paths[0] = Math.max(paths[0], childPaths[0]);
        }

        if(node.right != null){
            childPaths = helper(node.right);

            paths[2] = Math.max(paths[2], (node.right.val - 1 == node.val ? childPaths[2] + 1 : 1));
            paths[1] = Math.max(paths[1], (node.right.val + 1 == node.val ? childPaths[1] + 1 : 1));
            paths[0] = Math.max(paths[0], childPaths[0]);
        }

        paths[0] = Math.max(paths[0], paths[1] + paths[2] - 1 );

        return paths;
    }
}
