package tree.path;

import tree.TreeNode;

/**
 * _https://www.lintcode.com/problem/1181/?_from=ladder&fromId=29
 *
 * Diameter of Binary Tree
 *
 * Description
 * Given a binary tree, you need to compute the length of the diameter of the tree. The diameter of a binary tree is the length of the longest path between any two nodes in a tree.
 *
 * The length of path between two nodes is represented by the number of edges between them.
 *
 * Example 1:
 * Given a binary tree
 *           1
 *          / \
 *         2   3
 *        / \
 *       4   5
 * Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].
 *
 * Example 2:
 * Input:[2,3,#,1]
 * Output:2
 * Explanation:
 *       2
 *     /
 *    3
 *  /
 * 1
 *
 */

public class DiameterinBT {

    /**
     * @param root: a root of binary tree
     * @return: return a integer
     */
    public int diameterOfBinaryTree(TreeNode root) {
        int[] depths = depth(root);

        return depths[1] - 1;
    }

    /**
     * @return a int array ( int[] depths = new int[2] )
     *   depths[0] is the max length path leaf-to-node in the sub-tree;
     *.  depths[1] is the max length path leaf-to-leaf in the sub-tree, exclude the depths[0]
     */
    private int[] depth(TreeNode node){
        if(node == null){
            return new int[]{0, 0};
        }

        int[] left = depth(node.left);
        int[] right = depth(node.right);

        return new int[]{Math.max(left[0], right[0]) + 1, Math.max(Math.max(left[1], right[1]), left[0] + right[0] + 1) };
    }



}
