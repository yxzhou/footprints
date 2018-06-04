package fgafa.tree.lowerCommonAncestor;

import java.util.Set;

import fgafa.tree.TreeNode;

/**
 * further more on LCAinBT,  
 *   Given a binary tree, find the lowest common ancestor of k given nodes in the tree.
 *
 *     
 */
public class LCAinBTIII {
    /**
     * get the LCA of a binary tree, the k given nodes are sure in the tree
     * A Bottom-Up approach 
     * 
     * Time O(n)  Space O(1)
     * 
     */
    public TreeNode findLCAinBT(TreeNode root, Set<TreeNode> nodes){
        //check
        if(nodes == null || nodes.isEmpty()){
            return null;
        }
        if(nodes.size() == 1){
            for(TreeNode node : nodes){
                return node;
            }
        }
        
        return helper(root, nodes);
        
    }

    private TreeNode helper(TreeNode root, Set<TreeNode> nodes){
      //check
      if(root == null || nodes.contains(root)){
        return root;
      }
      
      //Divide 
      TreeNode left = helper(root.left, nodes);
      TreeNode right = helper(root.right, nodes);
      
      //Conquer
      if(left != null && right != null) {
        return root; // p and q are on 2 side. 
      }
      
      return (left != null)? left : right; //
    }
    
    

    public static void main(String[] args) {

    }

}
