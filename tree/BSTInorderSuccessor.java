package fgafa.tree;


/*
 * Given a binary search tree and a node in it, find the in-order successor of that node in the BST.
    Note: If the given node has no in-order successor in the tree, return null.
 */

public class BSTInorderSuccessor {
    
    /*
     * solution: 
     *   if p.right != null, the successor is the leftmost node in the p.right;
     *   else, traversal from the root, if the p.val < root.val, p is in the left tree, root maybe the successor.  
     * 
     * Time O(h)
     **/
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if(null == root || null == p){
            return null;
        }
        
        TreeNode successor = null;
        if(p.right != null){
            successor = p.right;
            
            while(null != successor.left){
                successor = successor.left;
            }

            return successor;
        }
        
        TreeNode result = root;
        while(result != p){
            if(result.val > p.val){
                successor = result;
                result = result.left;
            }else{
                result = result.right;
            }
        }
        
        return successor;
    }
    
}
