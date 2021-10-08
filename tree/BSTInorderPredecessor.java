/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

/**
 * Given a binary search tree and a node in it, find the in-order predecessor of that node in the BST.
 *
 * If the given node has no in-order predecessor in the tree, return null
 *
 * Example1
 * Input: root = {2,1,3}, p = 1
 * Output: null
 * 
 * Example2
 * Input: root = {2,1}, p = 2
 * Output: 1
 * 
 */
public class BSTInorderPredecessor {
    public TreeNode inorderPredecessor(TreeNode root, TreeNode p) {
        TreeNode candidate = null;
        TreeNode curr = root;

        while(curr != null){
            if(curr.val >= p.val){
                curr = curr.left;
            }else{
                candidate = curr;
                curr = curr.right;
            }
        }

        return candidate;
    }
}
