package tree.traversal;

import tree.BinaryTree;
import tree.Print;
import tree.TreeNode;

import java.util.List;

/**
 * 
 * in-order, pre-order and post-order
 *
 */

public class Main
{

  public static void main(String[] args) {

    TreeNode node = BinaryTree.initBT();
    List<TreeNode> list = BinaryTree.initBTs();
    list.add(node);

    IterativeTraversal iterativeTraversal = new IterativeTraversal();
    Print pnt = new Print();
    
    TreeNode root;
    for(int i=0; i < list.size(); i++){  
      root =  (TreeNode)list.get(i);
      System.out.print("\n\n"+ i +" ===  The tree === \n");
      pnt.printTreeShape(root);

      System.out.print("\n  Recursive Pre-Order: \n");
      RecursiveTraversal.preorder_R(node);
      System.out.println();
      iterativeTraversal.preorder_iterative(node);
      System.out.println();
      IterativeTraversal2.preorder_iterative_n(node);

      System.out.print("\n   Recursive In-Order: \n");
      RecursiveTraversal.inorder_R(node);
      System.out.println();
      iterativeTraversal.inorder_iterative(node);
      System.out.println();
      IterativeTraversal2.inorder_iterative_n(node);

      System.out.print("\n Recursive Post-Order:\n");
      RecursiveTraversal.postorder_R(node);
      System.out.println();
      iterativeTraversal.postorder_iterative(node);
      System.out.println();
      IterativeTraversal2.postorder_iterative(node);

    }
  }


  

  
}
