package tree.lowerCommonAncestor;

import tree.BinarySearchTree;
import tree.Print;
import tree.TreeNode;

public class LCAinBST {

    /**
     * get the LCA of a binary search tree. 
     * 
     * Because it's a binary search tree, the LCD.value is between p.value and q.value. 
     * so if both p and q is bigger than root, the LCD is in the root's right tree
     * if both p and q is smaller than root, the LCD is in the root's left tree
     * else, find the LCD
     *     
     * Time:   O(h)  h = logn, h is the height.    
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
    public TreeNode findLCAinBST(TreeNode root, TreeNode p, TreeNode q){
      //check
      if(root == null || p == null || q == null){
        return null;
      }
      if(root == p || root == q){
        return root;
      }
      if(p == q){
        return p;
      }
      
      return findLCAinBST(root, Math.min(p.val, q.val), Math.max(p.val, q.val));
    }
    
    private TreeNode findLCAinBST(TreeNode node, int min, int max){
      if(node == null){
        return null;
      }
      
      if (max < node.val){
        return findLCAinBST(node.left, min, max);
      } else if (min > node.val) {
        return findLCAinBST(node.right, min, max);
      } else {
        return node;
      }
    }
    
    public TreeNode findLCAinBST_iterator(TreeNode root,
                                          TreeNode p,
                                          TreeNode q) {
        // check
        if (root == null || p == null || q == null) {
            return null;
        }
        if (root == p || root == q){
            return root;
        }
        if (p == q){
            return p;
        }

        int min = Math.min(p.val, q.val);
        int max = Math.max(p.val, q.val);

        while (null != root) {
            if (root.val < min) {
                root = root.right;
            } else if (root.val > max) {
                root = root.left;
            } else {
                return root;
            }
        }

        return null;
    }
    
    public static void main(String[] args) {
        LCAinBST sv = new LCAinBST();
        
        System.out.println("\n===create a binary search tree===");

        TreeNode root2 = BinarySearchTree.initBST();
        
        Print print = new Print();
        print.printTreeShape(root2);

        TreeNode[][] test2 = {
            {root2.left.left, root2.left.right.left},   // 0, 3 -> 2
            {root2.left.right, root2.right.left},  //4, 7 -> 6
            {root2.left, root2.left.right.right},  //2, 5 -> 2
            {root2.right.left, root2.right}  //7, 8 -> 8
        }; 
        
        for(int i=0; i< test2.length; i++){
          System.out.println("\nThe LCD of " + test2[i][0]+" and " + test2[i][1]);
          System.out.println(" with LCDinBST, the LCD is: " + sv.findLCAinBST(root2, test2[i][0], test2[i][1]));
         
        }

    }

}
