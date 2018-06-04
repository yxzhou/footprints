package fgafa.tree;

import java.util.Stack;

public class FlattenBT2LinkedList
{

  /*
   * 
   * Given a binary tree, flatten it to a linked list in-place.
   * 
   * For example,
   * Given
   * 
   *          1
   *         / \
   *        2   5
   *       / \   \
   *      3   4   6
   * The flattened tree should look like:
   *    1
   *     \
   *      2
   *       \
   *        3
   *         \
   *          4
   *           \
   *            5
   *             \
   *              6
   * 
   * 
   * Note
	Don't forget to mark the left child of each node to null. Or you will get Time Limit Exceeded or Memory Limit Exceeded.
	
	Challenge
	Do it in-place without any extra memory.
   */
  
  /* Wrong */
  public void flatten(TreeNode root) {
    if(root == null)
      return;
    
    TreeNode left = root.left;
    TreeNode right = root.right;
    
    root.left = null;
    root.right = left;    // how about left == null??
    
    flatten(left);
    
    while(root.left != null)
      root = root.left;   // root.left has been set null in the above flatten(left);
    
    root.right = right;
    
    flatten(right);
  }
  

  /* recurrence: 
   * 1) if current node is null or leaf, return it.
   * 2) get the left child and right child of current node, 
   * 2.1)if the left is not null, set the left as the current node's right, 
   * flat the left tree. return the tail of left child tree as current node.
   * 2.2)if the right is not null, set the right as the current node's right, 
   * flat the right tree. return the tail of left child tree as current node.
   *   
   */
  public void flatten_recurr(TreeNode root) {
    if (root == null){
      return;
    }

    flatten_recurrHelp(root);
  }

  /*return the mostRight*/
  private TreeNode flatten_recurrHelp(TreeNode root) {
    
    TreeNode left = root.left;
    TreeNode right = root.right;
    TreeNode mostRight = root;
    
    root.left = null;

    if (left != null) {
      root.right = left;
      mostRight = flatten_recurrHelp(left);
    }

    if (right != null) {
      mostRight.right = right;
      mostRight = flatten_recurrHelp(right);
    }

    return mostRight;
  }
  
  /*
   * preOrder:
   * 1) check, if current node is null or leaf, return it.
   * 2) init a stack to store the root
   * 3) preOrder travel the tree
   * 3.1) if current node is not null, store it in stack, 
   *      loop check its left child.  (Note,  keep it as the left child)
   * 3.2) else, loop check the node in the stack, find out a node with a right node.  
   *      move the right child as the current node's left child
   *
   * 4) change all left child as right child 
   */
  public void flatten_preOrder(TreeNode root) {
    if (root == null)
      return;

    Stack<TreeNode> stack = new Stack<TreeNode>();
    TreeNode curr = root, right;

    while (curr != null || !stack.isEmpty()) {

      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }

      if (!stack.isEmpty()) {
        curr = stack.pop();

        right = curr;
        while (right.right == null && !stack.isEmpty()) {
          right = stack.pop();
        }

        if (right != null) {
          curr.left = right.right;
          right.right = null;
          curr = curr.left;
        }
      }

    }

    while (root != null) {
      root.right = root.left;
      root.left = null;
      root = root.right;
    }

  }
  
  /*
   * preOrder:
   * 1) check, if current node is null or leaf, return it.
   * 2) init a stack to store the right child tree
   * 3) preOrder travel the tree
   * 3.1) if current node has left child, store the right child in stack, 
   *      change the left child to right.  
   * 3.2) else, get the right child from the stack and 
   *      set it as the right child as the current node
   *
   */
  public void flatten_preOrder2(TreeNode root) {
    if (root == null)
      return;

    Stack<TreeNode> stack = new Stack<TreeNode>();
    TreeNode curr = root;

    while (curr != null || !stack.isEmpty()) {

      while (curr.left != null) {
        if(curr.right != null)  
            stack.push(curr.right);
        
        curr.right = curr.left;
        curr.left = null;
        curr = curr.right;
      }

      if (curr.right == null && !stack.isEmpty()) 
        curr.right = stack.pop();

      curr = curr.right;
    }
  }
  
  /**
   * @param root: a TreeNode, the root of the binary tree
   * @return: nothing
   */
  /** Time O(n), Space O(1) **/
  public void flatten_preOrder_n(TreeNode root) {
      //check
      if(null == root){
          return;
      }
      
      TreeNode curr = root;
      TreeNode tail;
      
      while(null != curr){
          if(null != curr.left){
              if(null != curr.right){
                  tail = curr.left;
                  if(null != tail){
                      while(null != tail.right){
                          tail = tail.right;
                      }
                      
                      tail.right = curr.right;                    
                  }
              }
              
              curr.right = curr.left;
              curr.left = null;
          } 

          curr = curr.right;
      }
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

  
}
