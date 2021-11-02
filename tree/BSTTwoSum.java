/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;


import tree.TreeNode;
        
/**
 *
 * Given a binary search tree and a number n, find two numbers in the tree that sums up to n.
 * 
 * Use O(1) extra space.
 * 
 * Example
 * Input： {4,2,5,1,3}, 3
 * Output： [1,2] (or [2,1])
 * Explanation：
 *   the BST is
 *         4
 *        / \
 *       2   5
 *      / \
 *     1   3
 * 
 * Input: {4,2,5,1,3}, 5
 * Output: [2,3] (or [3,2] or [1,4] or [4,1])
 * 
 */
public class BSTTwoSum {
    /*
     * @param : the root of tree
     * @param : the target sum
     * @return: two numbers from tree which sum is n
     */
   public int[] twoSum(TreeNode root, int n) {

        int[] result = new int[2];
        if(helper(root, n, result)){
            return result;
        }

        return null;
    }

    private boolean helper(TreeNode node, int target, int[] result){
        if(node == null){
            return false;
        }

        //case 1, one is current node
        result[0] = node.val;
        result[1] = target - node.val;
        if(result[1] > 0 && result[0] != result[1] && existed( node, result[1] )){
            return true;
        }

        //case 2, both are in left tree
        if(node.val * 2 - 2 > target && helper( node.left, target, result )){
            return true;
        }

        //case 3, both are in right tree
        if( node.val * 2 + 2 < target && helper( node.right, target, result )){
            return true;
        }

        //case 4, one is in left tree, the other is in right tree
        for(int l = 1, r = target - l; l < node.val && r > node.val; l++, r-- ){
            result[0] = l;
            result[1] = r;
            if(existed( node.left, l ) && existed( node.right, r )){
                return true;
            }
        }

        return false;
    }

    private boolean existed(TreeNode node, int target){
        if(node == null){
            return false;
        }

        if(node.val == target){
            return true;
        }

        if(node.val < target){
            return existed(node.right, target);
        }else{
            return existed(node.left, target);
        }
    }
}
