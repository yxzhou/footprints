package tree;

import java.util.List;


/**
 * 
 * Given a binary tree where all the right nodes are either leaf nodes with a sibling (a left node that shares the same parent node) or empty, 
 * flip it upside down and turn it into a tree where the original right nodes turned into left leaf nodes. Return the new root.
 * 
    For example:
    Given a binary tree {1,2,3,4,5,#,#},
        1
       / \
      2   3
     / \
    4   5
    return the root of the binary tree [4,5,2,#,#,3,1].
       4
      / \
     5   2
        / \
       3   1  
 *
 */

public class UpsideDown {

    public TreeNode upsideDown(TreeNode root){
        if(null == root || null == root.left){
            return root;
        }
        
        TreeNode left = root.left;
        TreeNode right = root.right;
        
        root.left = null;  //note
        root.right = null; //note
        
        TreeNode newRoot = upsideDown(left);
        
        left.right = root;
        left.left = upsideDown(right);
        
        return newRoot;
    }
    
    public static void main(String[] args) {

        List<TreeNode> list = BinaryTree.initBTs();

        // traverse
        UpsideDown sv = new UpsideDown();
        Print pnt = new Print();
        
        TreeNode root;
        for (int i = 0; i < list.size(); i++) {
            root = (TreeNode) list.get(i);
            System.out.print("\n" + i + " ===  The tree === \n");
            pnt.printTreeShape(root);

            System.out.println("\t upsideDown: ");
            pnt.printTreeShape(sv.upsideDown(root));

        }

    }
}
