package tree;


/**
 * 
 * Given a binary tree, count the number of uni-value subtrees.
 * 
 * A Uni-value subtree means all nodes of the subtree have the same value.
 * 
 * For example:
 *
 * Given binary tree,
 * 
 *     5
 *    / \
 *   1   5
 *  / \   \
 * 5   5   5
 *
 * return 4.
 *
 */

public class CountSubTree {

    public int countUnivalSubtrees(TreeNode root) {
        if(null == root){
            return 0;
        }
        
        int[] result = new int[1];//default it's 0
        
        helper(root, result);
        
        return result[0];
    }
    
    private boolean helper(TreeNode node, int[] result){
        
        if(null != node.left && ( node.val != node.left.val || !helper(node.left, result) )){
            return false;
        }
        
        if(null != node.right && ( node.val != node.right.val || !helper(node.right, result) )){
            return false;
        }
        
        result[0]++;
        return true;
    }
    
}
