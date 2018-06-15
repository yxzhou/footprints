package fgafa.tree.lowerCommonAncestor;

import fgafa.tree.BinaryTree;
import fgafa.tree.Print;
import fgafa.tree.TreeNode;

/**
 * further more on LCAinBT,  
 *   how to do if the two given nodes are not sure in the tree.
 *
 * There are more cases:
 * 1. only when found both, return the LCA, or return null
 *
 *     
 */
public class LCAinBTII {
    /**
     * get the LCA of a binary tree, the two given nodes both are not sure if they are in the tree
     * A Bottom-Up approach 
     * 
     * Time O(n)  Space O(1)
     * 
     */
    boolean findAll = false;
    public TreeNode findLCAinBT(TreeNode root, TreeNode p, TreeNode q){
        //check
        if( p == null || q == null){
          return null;
        }
        if(p == q){
            return p;
        }
        
        findAll = false;
        TreeNode ret = helper(root, p, q);
        return findAll? ret : null;
    }
    private TreeNode helper(TreeNode root, TreeNode p, TreeNode q){
      //check
      if(root == null || root == p || root == q){
        return root;
      }
      
      //Divide
      TreeNode left = helper(root.left, p, q);
      TreeNode right = helper(root.right, p, q);
      
      //Conquer
      if(left != null && right != null){
          findAll = true;
          return root; // p and q are on 2 side. 
      }
      
      return (left != null)? left : right; //
    }
    
    

    public static void main(String[] args) {
        LCAinBT sv = new LCAinBT();
        Print print = new Print();
        
        System.out.println("===create a binary tree===");
        

        TreeNode root = BinaryTree.initBT();
        //Main.postorder_R(root);
        print.printTreeShape(root);

        TreeNode[][] test = {{root.left.left.right, root.left.right}, // LCD( A, C ) = D
            {root.left.left.right, root.left},  //LCD( A, D ) = D 
            {root.left.left, root.right.right},  //LCD( B, F ) = H 
            {root.left.left, null}
        }; 
                
        for(int i=0; i< test.length; i++){
          System.out.println("\nThe LCD of " + test[i][0]+" and " + test[i][1]);
          System.out.println(" with bottom-up approach, the LCD is: " + sv.findLCAinBT(root, test[i][0], test[i][1]));
         
        }

    }

}
