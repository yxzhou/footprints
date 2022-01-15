/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.util.List;

/**
 *  _https://www.lintcode.com/problem/619
 * 
 * It's follow up problem for Binary Tree Longest Consecutive Sequence II
 *
 * Given a k-ary tree, find the length of the longest consecutive sequence path. The path could be start and end at any
 * node in the tree
 * 
 * Example 1:
 * Input: 5<6<7<>,5<>,8<>>,4<3<>,5<>,31<>>>
 * Output: 5
 * Explanation:
     5
   /   \
  6     4
 /|\   /|\
7 5 8 3 5 31

 * return 5, // 3-4-5-6-7
 * 
 * Example 2:
 * Input: 1<>
 * Output: 1
 */
public class LongestConsecutiveSequenceII2 {

    int max = 0;

    /**
     * @param root: the root of binary tree
     * @return the length of the longest consecutive sequence path
     */
    public int longestConsecutive2(TreeNode root) {
        if(root == null){
            return 0;
        }

        helper(root);
        return max;
    }

    /**
    *
    * @return a int array, {local path in ascend, local path in descend   } 
    */
    private int[] helper(TreeNode node){

        int asc = 1;
        int desc = 1;
        
        int[] paths;
        if(node.left != null){
            paths = helper(node.left);

            asc = (node.left.val + 1 == node.val ? paths[0] + 1 : 1);
            desc= (node.left.val - 1 == node.val ? paths[1] + 1 : 1);
        }

        if(node.right != null){
            paths = helper(node.right);

            asc = Math.max(asc, (node.right.val + 1 == node.val ? paths[0] + 1 : 1));
            desc = Math.max(desc, (node.right.val - 1 == node.val ? paths[1] + 1 : 1));
        }

        max = Math.max(max, asc + desc - 1 );

        return new int[]{asc, desc};
    }
    
    
}
