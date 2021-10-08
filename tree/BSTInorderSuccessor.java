package tree;


/**
 * 
 * Given a binary search tree and a node in it, find the in-order successor of that node in the BST.
 *   Note: If the given node has no in-order successor in the tree, return null.
 * 
 * Example 1:
 * Input: {1,#,2}, node with value 1
 * Output: 2
Explanation:
  1
   \
    2

 * Example 2:
 * Input: {2,1,3}, node with value 1
 * Output: 2
Explanation: 
    2
   / \
  1   3

 * Challenge O(h), where h is the height of the BST.
 */

public class BSTInorderSuccessor {
    
    /*
     * solution: 
     *   if p.right != null, the successor is the leftmost node in the p.right;
     *   else, traversal from the root, if the p.val < root.val, p is in the left tree, root maybe the successor.  
     * 
     * Time O(h)
     **/
    public TreeNode inorderSuccessor_n(TreeNode root, TreeNode p) {
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
        
        TreeNode curr = root;
        while(curr != p){
            if(curr.val > p.val){
                successor = curr;
                curr = curr.left;
            }else{
                curr = curr.right;
            }
        }
        
        return successor;
    }
    
    
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        TreeNode successor = null;
        TreeNode curr = root;

        while(curr != null){
            if(curr.val <= p.val){
                curr = curr.right;
            }else{
                successor = curr;
                curr = curr.left;
            }
        }

        return successor;
    }
}
