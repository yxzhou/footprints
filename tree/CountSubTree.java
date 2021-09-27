package tree;


/**
 * 
 * Given a binary tree, count the number of uni-value subtrees.

    A Uni-value subtree means all nodes of the subtree have the same value.
    
    For example:
    
    Given binary tree,
    
        5
       / \
      1   5
     / \   \
    5   5   5
    return 4.
 *
 */

public class CountSubTree {

    public int countUnivalSubtrees(TreeNode root) {
        
        int[] result = new int[1];//default it's 0
        
        //check
        if(null == root){
            return result[0];
        }
        
        helper(root, result);
        
        return result[0];
    }
    
    private boolean helper(TreeNode node, int[] result){
        
        if(null != node.left && ( !helper(node.left, result) || node.val != node.left.val )){
            return false;
        }
        
        if(null != node.right && ( !helper(node.right, result) || node.val != node.right.val )){
            return false;
        }
        
        result[0]++;
        return true;
    }
    
}
