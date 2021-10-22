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
        if(find(root, n, result, 0)){
            return result;
        }

        return null;
    }

    /**
     * @return true if found, and fill in the result.
     */
    private boolean find(TreeNode node, int n, int[] result, int i){
        if(node == null){
            return false;
        }
        if(i == 1){ // only need find one node whose value is n
            if(node.val == n){
                return true;
            }

            if(node.val < n){
                return find(node.right, n, result, 1);
            }else{
                return find(node.left, n, result, 1);
            }
        }
        
        // i == 0, need find out the two node that the sum is n
        //case 1, one is current node
        result[0] = node.val;
        result[1] = n - node.val;
        if(result[1] > 0 && result[0] != result[1] && find( node, result[1], result, 1 )){
            return true;
        }

        //case 2, both are in the left tree
        if(node.val * 2 - 2 > n && find( node.left, n, result, 0 )){
            return true;
        }

        //case 3, both are in the right tree
        if( node.val * 2 + 2 < n && find( node.right, n, result, 0 )){
            return true;
        }

        //case 4, one is in the left tree, the other is in the right tree 
        for(int l = 1, r = n - l; l < node.val && r > node.val; l++, r-- ){
            result[0] = l;
            result[1] = r;
            if(find( node.left, l, result, 1 ) && find( node.right, r, result, 1 )){
                return true;
            }
        }

        return false;
    }
}
