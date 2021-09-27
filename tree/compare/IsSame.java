package tree.compare;

/*
 * 
 * Given two binary trees, write a function to check if they are equal or not.
 * 
 * Two binary trees are considered equal if they are structurally identical and the nodes have the same value
 * 
 * similar to SymmetricTree
 */

import tree.TreeNode;

public class IsSame {

  public boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null || q == null) {
      return p == q;
    }


    if (p.val != q.val) {
      return false;
    }


    return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);

  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }
  

}
