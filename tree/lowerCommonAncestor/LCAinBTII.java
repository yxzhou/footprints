package tree.lowerCommonAncestor;

import tree.BinaryTree;
import tree.Print;
import tree.TreeNode;

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
    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q){
        //check
        if(root == null || p == null || q == null){
          return null;
        }
        if(p == q){
            return p;
        }
        
        boolean[] found = new boolean[2];//default both are false
        TreeNode result = helper(root, p, q, found);
        return found[0] && found[1]? result : null;
    }
    private TreeNode helper(TreeNode root, TreeNode p, TreeNode q, boolean[] found) {
        if (root == null) {
            return null;
        }
        
        TreeNode result = null;
        if(root == p){
            found[0] = true;
            result = p;
        }else if(root == q){
            found[1] = true;
            result = q;
        }

        //Divide
        TreeNode left = helper(root.left, p, q, found);
        TreeNode right = helper(root.right, p, q, found);

        //Conquer
        if (left != null && right != null) {
            result =  root; // p and q are on 2 side. 
        }else{
            if (result == null){
                result = ( (left != null) ? left : right );
            } 
        }

        return result; //
    }

    public static void main(String[] args) {
        LCAinBTII sv = new LCAinBTII();
        Print print = new Print();

        System.out.println("===create a binary tree===");

        TreeNode root = BinaryTree.initBT();
        //Main.postorder_R(root);
        print.printTreeShape(root);

        TreeNode[][] test = {{root.left.left.right, root.left.right}, // LCD( A, C ) = D
        {root.left.left.right, root.left}, //LCD( A, D ) = D 
        {root.left.left, root.right.right}, //LCD( B, F ) = H 
        {root.left.left, null}
        };

        for (int i = 0; i < test.length; i++) {
            System.out.println("\nThe LCD of " + test[i][0] + " and " + test[i][1]);
            System.out.println(" with bottom-up approach, the LCD is: " + sv.lowestCommonAncestor3(root, test[i][0], test[i][1]));

        }

    }

}
