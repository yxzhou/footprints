package tree.lowerCommonAncestor;

import tree.BinaryTree;
import tree.Print;
import tree.TreeNode;

/**
 * 
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 * 
 * Assume two nodes are exist in tree.
 *
 * Solution:
 * 
 * The two given nodes is in the tree, there are only three cases:
 * 1. Both nodes are to the left of the tree.  
 * 2. Both nodes are to the right of the tree.
 * 3. One node is on the left while the other is on the right.
 * 4. When the current node equals to either of the two nodes, this node must be the LCA too.
 *
 * e.g. To a binary tree
 * 
 *         H
 *      /      \  
 *     D        G
 *    /  \       \
 *   B    C       F
 *    \          / 
 *     A        E
 *  The postOrder is A B C D E F G H   
 *  the LCD( A, C ) = D
 *  the LCD( A, G ) = H 
 *  the LCD( B, F ) = H     
 *     
 *  e.g. To a binary search tree, 
 *     
 *         6
 *      /      \  
 *     2        8
 *    / \      /  \
 *   0  4     7    9
 *     /  \           
 *    3    5  
 *  The postOrder is 0 3 5 4 2 7 9 8 6    
 *  the LCD( 0, 3 ) = 2
 *  the LCD( 4, 7 ) = 6    
 *  the LCD( 2, 5 ) = 2 
 *  the LCD( 7, 8 ) = 8  
 *     
 */
public class LCAinBT {
    /**
     * get the LCA of a binary tree, the two given nodes both are in the tree, and no duplicated nodes in the tree.
     * A Bottom-Up approach 
     * 
     * Time O(n)  Space O(1)
     * 
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        //Divide
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        //Conquer
        if(left == null){
            return right;
        }else if(right == null){
            return left;
        } // else{ left != null && right != null

        return root;
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
            {root.left.left, root.right.right}  //LCD( B, F ) = H 
        }; 
                
        for(int i=0; i< test.length; i++){
          System.out.println("\nThe LCD of " + test[i][0]+" and " + test[i][1]);
          System.out.println(" with bottom-up approach, the LCD is: " + sv.lowestCommonAncestor(root, test[i][0], test[i][1]));
         
        }

    }

}
