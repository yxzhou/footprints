package dailyCoding.tree;

import tree.Print;
import tree.TreeNode;

/**
 * Given a binary tree where all nodes are either 0 or 1, prune the tree so that subtrees containing all 0s are removed.
   
   For example, given the following tree:
   
         0
        / \
       1   0
      / \
     1   0
    / \
   0   0
   should be pruned to:
   
       0
      / \
     1   0
    /
   1
   We do not remove the tree at the root or its left child because it still has a 1 as a descendant.
 *
 * Question:
 * is it should be:
 *        0
 *       /
 *      1
 *     /
 *    1
 * tags: BufferBox.
 *
 */

public class Prune {


    public TreeNode prune(TreeNode node){
        if(node == null){
            return null;
        }

        node.left = prune(node.left);
        node.right = prune(node.right);

        if(node.left == null && node.right == null && node.val == 0){
            return null;
        }

        return node;
    }

    public static void main(String[] args){
        Prune sv = new Prune();

        Print print = new Print();

       /* case #1
               0
              / \
             1   0
            / \
           1   0
          / \
         0   0
       */
        TreeNode n1 = new TreeNode(0, null, null);
        TreeNode n2 = new TreeNode(0, null, null);
        TreeNode n3 = new TreeNode(1, n1, n2);

        TreeNode n4 = new TreeNode(0, null, null);
        TreeNode n5 = new TreeNode(1, n3, n4);

        TreeNode n6 = new TreeNode(0, null, null);
        TreeNode n7 = new TreeNode(0, n5, n6);

        print.printTreeShape(n7);

        sv.prune(n7);

        print.printTreeShape(n7);
    }
}
