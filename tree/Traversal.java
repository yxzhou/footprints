package fgafa.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


import java.util.ArrayList;

/**
 * 
 * in-order, pre-order and post-order
 *
 */

public class Traversal
{
  
  /**
   * @param args
   */
  public static void main(String[] args) {

    TreeNode node = BinaryTree.initBT();
    List<TreeNode> list = BinaryTree.initBTs();
    list.add(node);
    
    //Traversal trav = new Traversal();
    Print pnt = new Print();
    
    TreeNode root;
    for(int i=0; i < list.size(); i++){  
      root =  (TreeNode)list.get(i);
      System.out.print("\n\n"+ i +" ===  The tree === \n");
      pnt.printTreeShape(root);
      
      System.out.print("\n  Recursive Pre-Order: \n");
      Traversal.preorder_R(node);
      System.out.println();
      Traversal.preorder_iterative_n2(node);
      
      System.out.print("\n   Recursive In-Order: \n");
      Traversal.inorder_R(node);
      System.out.println();
      Traversal.inorder_iterative(node);
      
      System.out.print("\n Recursive Post-Order:\n");
      Traversal.postorder_R(node);
      System.out.println();
      Traversal.postorder_iterative_n(node);  
      
    }
  }


  /** 
   * inorder with stack and a Node
   * 
   * 1 If node is not null, put node and node left child in stack
   * 2 pop stack, visit it, set right node  ---
   * 
   * 
   *  Time O(n)  Space O(n)    
   */
  public static void inorder_iterative(TreeNode p) {
    Stack<TreeNode> stack = new Stack<TreeNode>();
    TreeNode node = p;
    while (node != null || !stack.isEmpty()) {
      while (node != null) {
        stack.push(node);
        node = node.left;
      }
      if (!stack.isEmpty()) {
        node = stack.pop();
        BinaryTree.visit(node);
        node = node.right;
      }
    }
  }
  /** 
   * inorder with 2 Nodes, Threaded binary tree.
   * 
   * 1. Initialize current as root 
   * 2. While current is not NULL
   *    If current does not have left child
   *       a) Print current’s data
   *       b) Go to the right, i.e., current = current->right
   *    Else
   *       a) Make current as right child of the rightmost node in current's left subtree
   *       b) Go to this left child, i.e., current = current->left
   *       
   *  Time O(n)  Space O(1)     
   * 
   */
    public ArrayList<Integer> inorder_iterative_X(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<Integer>();

        TreeNode curr = root;
        TreeNode pre;
        while (curr != null) {
            if (curr.left == null) {
                result.add(curr.val);
                curr = curr.right;
            } else {
                /* Find the inorder predecessor of current */
                pre = curr.left;
                while (pre.right != null && pre.right != curr) {
                    pre = pre.right;
                }

                /*Make current as right child of its inorder predecessor*/
                if (pre.right == null) {
                    pre.right = curr;
                    curr = curr.left;
                } else {
                    pre.right = null;
                    result.add(curr.val);
                    curr = curr.right;
                }
            }
        }

        return result;
    }

  public ArrayList<Integer> inorderTraversal_n(TreeNode root) {
      ArrayList<Integer> result = new ArrayList<Integer>();
      
      TreeNode p = root;
      Stack<TreeNode> stack = new Stack<TreeNode>();
      
      while(null != p || !stack.isEmpty()){
          if(null == p){
              p = stack.pop();
              result.add(p.val);
              p = p.right;
          }else{
              stack.add(p);
              
              p = p.left;
          }
      }
      
      return result;
  }
  
  //inorder traversal
  public int kthSmallestBST(TreeNode root, int k) {
      //ignore check, You may assume k is always valid, 1 ≤ k ≤ BST's total element
      
      Stack<TreeNode> stack = new Stack<>();
      TreeNode curr = root;
      int result = 0;
      while(k > 0){
          if(null == curr){
              curr = stack.pop();
              result = curr.val;
              k--;
              curr = curr.right;
          }else{
              stack.add(curr);
              curr = curr.left;
          }
      }
      
      return result;
  }
  
  //someone say it cost space O(1)
  public int kthSmallestBST_R(TreeNode root, int k) {
      int leftCount = countNodes(root.left) + 1;
      if (leftCount == k) {
          return root.val;
      } else if (leftCount > k) {
          return kthSmallestBST_R(root.left, k);
      } else {
          return kthSmallestBST_R(root.right, k - leftCount);
      }
  }
  
  private int countNodes(TreeNode root) {
      if (root == null) {
          return 0;
      }
      return countNodes(root.left) + countNodes(root.right) + 1;
  }
  
  /** 
   * postorder with one Stack and 2 node
   * @param p
   */
  public static void postorder_iterative_n(TreeNode p) {
    Stack<TreeNode> stack = new Stack<TreeNode>();
    TreeNode node = p, prev = p;
    while (node != null || !stack.isEmpty()) {
      while (node != null) {
        stack.push(node);
        node = node.left;
      }
      
      if (!stack.isEmpty()) {
        node = stack.peek().right;
        if (node == null || node == prev) {
          node = stack.pop();
          visit(node);
          prev = node;
          node = null;
        }
      }
  
    }
  }
  
  /**
   * print a Node
   * @param p
   */
  static void visit(TreeNode p) {
    System.out.print(p.val + " ");
  }
  

  /*
   * preorder with stack
   * 
   * 1 if the root is not null, push into stack
   * 2 pop stack, visit it, put right child and left child into stack
   * 3 loop 2 when stack is not empty.
   * 
   */
  public static void preorder_iterative_n2(TreeNode p) {
    Stack<TreeNode> stack = new Stack<TreeNode>();
    if (p != null) {
      stack.push(p);
      while (!stack.empty()) {
        p = stack.pop();
        visit(p);
        if (p.right != null)
          stack.push(p.right);
        if (p.left != null)
          stack.push(p.left);
      }
    }
  }


  /*
   * preorder with stack 
   * 
   * 1 assign root to node, 
   * 2 If node is not null, visit the node and the node left child, put his right child in stack. 
   * 
   */	
    public ArrayList<Integer> preorderTraversal_n(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        
        TreeNode p = root;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        
        while(null != p || !stack.isEmpty()){
            if(null == p){
                p = stack.pop();
            }else{
                result.add(p.val);
            
                if(null != p.right){
                    stack.add(p.right);
                }
                
                p = p.left;
            }
        }
        
        return result;
    }
	
  /*
   * inorder with recursive
   */
  public static void inorder_R(TreeNode p) {
    if (p != null) {
      inorder_R(p.left);
      BinaryTree.visit(p);
      inorder_R(p.right);
    }
  }
  
  /*
   * postorder with recursive
   */
  public static void postorder_R(TreeNode p) {
    if (p != null) {
      postorder_R(p.left);
      postorder_R(p.right);
      BinaryTree.visit(p);
    }
  }

  /*
   * preorder with recursive
   */
  public static void preorder_R(TreeNode p) {
    if (p != null) {
      BinaryTree.visit(p);
      preorder_R(p.left);
      preorder_R(p.right);
    }
  }
  
}
