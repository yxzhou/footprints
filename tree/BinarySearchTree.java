package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import tree.TreeNode;


/**
 * 
 * @author yxzhou
 *
 */

public class BinarySearchTree extends BinaryTree
{

  
  
  /**
   * init a binary search tree
   * @return the root Node of the tree
   *         6
   *      /      \  
   *     2        8
   *    / \      /  \
   *   0  4     7    9
   *     /  \           
   *    3    5        
   * 
   */
  public static TreeNode initBST(){
   
      TreeNode a1 = new TreeNode(3);
      TreeNode a2 = new TreeNode(5);
      TreeNode b = new TreeNode(4, a1, a2);
      TreeNode c = new TreeNode(0);
      TreeNode d = new TreeNode(2, c, b);
      TreeNode e1 = new TreeNode(7);
      TreeNode e2 = new TreeNode(9);
      TreeNode f = new TreeNode(8, e1, e2);
      TreeNode h = new TreeNode(6, d, f);
    return h;// root  
  }
  
  /*
   * A BS can be built from inOrder + preOrder/postOrder, can't not from preOrder + postOrder.
   * 
   * A BST can be built from preOrder or postOrder, can't not from inOrder 
   *
   */
  /*
   * f(n) = f(n - 1) + logn
   *   
   *  time complexity  T(n) = O(nlogn)
   */
  public TreeNode buildBSTPreOrder(int[] preOrder){
    if(preOrder == null || preOrder.length == 0){
      return null;
    }
    
    TreeNode root = new TreeNode(preOrder[0]);
    
    for(int i=1; i<preOrder.length; i++ ){
      addNodeinBST(preOrder, i, root);
    }
    
    return root;
    
  }
  
  private void addNodeinBST(int[] preOrder, int index, TreeNode p){
    int curr = preOrder[index]; 
    TreeNode currNode = new TreeNode(preOrder[index]);
    
    TreeNode node = p;

    while( node != null){
      if( curr < node.val  ){
        if(node.left == null)
          node.left = currNode;
        else
          node = node.left;
        
      }else  if ( curr > node.val  ){
        if(node.right == null) 
          node.right = currNode;
        else
          node = node.right;
        
      }else// curr == Integer.valueOf(node.getKey())
        break;
    }
  }
  
  /*
   * with a Stack
   * f(n) = f(n - 1) + 1
   *   
   *  time complexity  T(n) = O(n)
   */
  public TreeNode buildBSTPreOrder_n(int[] preOrder){
      if(preOrder == null || preOrder.length == 0){
        return null;
      }

      TreeNode root = new TreeNode(preOrder[0]);
      
      Stack<TreeNode> stack = new Stack<>();
      TreeNode curr = root;
      
      TreeNode next;
      for(int i = 1; i < preOrder.length; i++){
          next = new TreeNode(preOrder[i]);
          
          if(curr.val < next.val){
              while(!stack.isEmpty() && stack.peek().val < next.val){
                  curr = stack.pop();
              }
              curr.right = next;
          }else{ // curr.val > next.val
              curr.left = next;
              stack.push(curr);
          }
          
          curr = next;
      }
      
      return root;
  }
  
  /**
   *  Given a array in ascend order, build a BST
   *  
   *  f(n) = 2 * f(n/2) + 1
   *  
   *  Time complexity: T(n) = O(n)
   */
  public TreeNode sortedArray2BST(int[] sortedArray){
      if(null == sortedArray){
          return null;
      }
      
      return buildBST(sortedArray, 0, sortedArray.length - 1);
  }
  
  private TreeNode buildBST(int[] sortedArray, int start, int end){
      if(start < end){
          return null;
      }
      
      int middle = start + (end - start) / 2;
      TreeNode result = new TreeNode(middle);
      result.left = buildBST(sortedArray, start, middle - 1);
      result.right = buildBST(sortedArray, middle + 1, end);
      
      return result;
  }
  
  /**
   * 
   * copy from BinarySearchTree.java
   * 
   * @param preOrder
   * @return
   */
  public static TreeNode buildBST_levelorder(int[] preOrder){
    //check
    if(preOrder == null || preOrder.length == 0)
      return null;
    
    TreeNode root = new TreeNode(preOrder[0]); 
    
    List<TreeNode> list = new ArrayList<TreeNode>();
    list.add(root);
    
    TreeNode curr ;
    int i = 1;
    int len = preOrder.length;
    while(true){
      curr = list.get(0);
      list.remove(0);
      
      if(i < len ){
        if(preOrder[i] != 0){
          curr.left = new TreeNode(preOrder[i]);
          list.add(curr.left);
        }
        
        i++;
      }else
        break;
        
      if(i < len ){
        if(preOrder[i] != 0){
          curr.right = new TreeNode(preOrder[i]);
          list.add(curr.right);
        }
        i++;
      }else
        break;
      
    }
    
    return root;
  }
  
  /*
   * First, you must understand the difference between Binary Tree and Binary Search Tree (BST). 
   * Binary tree is a tree data structure in which each node has at most two child nodes. 
   * A binary search tree (BST) is based on binary tree, but with the following additional properties:
   * 1 The left subtree of a node contains only nodes with keys less than the node's key.
   * 2 The right subtree of a node contains only nodes with keys greater than the node's key.
   * 3 Both the left and right subtrees must also be binary search trees.
   * 
   * top-down approach 
   * 
   * Time O(N) , Space O(1) (neglecting the stack space used by calling function recursively).
   */
  
  /*
   * The point:
   *    1 why it long low instead of int low ?
   *    2 why it's root.val <= low instead of root.val < low
   */
  public boolean isValidBST_n(TreeNode root) {
      return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE); // Long.MIN_VALUE < Integer.MIN_VALUE
  }
  
  private boolean isValidBST(TreeNode root, long low, long high){
      if(null == root){
          return true;
      }
      
      if( root.val <= low || root.val >= high){
          return false;
      }
      
      return isValidBST(root.left, low, root.val) && isValidBST(root.right, root.val, high);
  }
  
  public boolean isValidBST_n2(TreeNode root) {
      return isValidBST_n2(root, null, null);
  }
  
  private boolean isValidBST_n2(TreeNode root, Integer low, Integer high){
      if(null == root){
          return true;
      }
      
      if( (null != low && root.val <= low) || (null != high && root.val >= high)){
          return false;
      }
      
      return isValidBST(root.left, low, root.val) && isValidBST(root.right, root.val, high);
  }
  
  /*
   * Another solution is to do an in-order traversal of the binary tree, 
   * and verify that the previous value (can be passed into the recursive function as reference) is less than the current value. 
   * This works because when you do an in-order traversal on a BST, the elements must be strictly in increasing order. 
   * 
   * Time O(n), Space O(n).
   * 
   */
  public boolean isBST_inOrder(TreeNode p){
    if(p == null) return true;

    Stack<TreeNode> stack = new Stack<TreeNode>();
    int currValue = Integer.MIN_VALUE;
    
    while( p!= null || !stack.isEmpty() ){
      while(p != null){
        stack.push(p);
        p = p.left;
      }
      
      if(!stack.isEmpty()){
        p = stack.pop();
        
        if(p.val > currValue )
          currValue = p.val;
        else
          return false;
        
        p = p.right;
      }
        
    }
    
    return true;
  }
  

  
  /** Given a BST and a value x, Find two nodes in the tree whose sum is equal to x. 
   *  Additional space O(height of the tree). It's not allowed to modify the tree.
   *  Assume there is no duplicate value in this BST 
   */
    public ArrayList<TreeNode[]> findPairNode(TreeNode root,
                                              int target) {
        // init
        ArrayList<TreeNode[]> ret = new ArrayList<>();

        // check
        if (root == null)
            return ret;

        int currValue = root.val;
        int diff = currValue - target;
        TreeNode otherNode = null;

        if (diff < currValue)
            otherNode = findOneNode(root.left, diff);
        else
            otherNode = findOneNode(root.right, diff);

        if (otherNode != null) {
            TreeNode[] pair = { root, otherNode };
            ret.add(pair);
        }

        return ret;
    }

    private TreeNode findOneNode(TreeNode node,
                                 int target) {
        int currValue;
        while (node != null) {
            currValue = node.val;
            if (currValue == target)
                return node;
            else if (currValue < target)
                node = node.right;
            else
                node = node.left;
        }

        return null;
    }

    public TreeNode findClosestNodeBST(TreeNode root,
                                       int target) {
        // if (root == null)
        // return root;

        TreeNode closestNode = root, curr = root;
        int minDiff = Integer.MAX_VALUE, currValue, currDiff;

        while (curr != null) {
            currValue = curr.val;
            currDiff = Math.abs(currValue - target);

            if (currDiff < minDiff) {
                closestNode = curr;
                minDiff = currDiff;
            }

            if (currValue < target)
                curr = curr.right;
            else
                curr = curr.left;
        }

        return closestNode;
    }

  /**
   * Find k nearest neightbors in BST  -- inOrder traverse
   */
    public void findKNN(TreeNode node,
                        int k,
                        int target,
                        LinkedList<Integer> queue) {
        if (node == null)
            return;

        findKNN(node.left, k, target, queue);

        if (queue.size() < k)
            queue.add(node.val);
        else { // queue.size == k
            if (Math.abs(target - node.val) < Math.abs(target - queue.peek())) {
                queue.pop();
                queue.add(node.val);
            }
        }

        findKNN(node.right, k, target, queue);
    }

    /*
     *  Insert key-value pair into BST
     *  If key already exists, update with new value
     */
  public TreeNode insertNode(TreeNode root, TreeNode node) {
      //check
      if( null == node ){
          return root;
      }else if( null == root ){ // key point
          return node;
      }
      
      TreeNode curr = root;
      while(null != curr){
          if(node.val < curr.val){
              if(null == curr.left){
                  curr.left = node;
                  break;
              }
              curr = curr.left;
              
          }else if( node.val > curr.val ){
              if(null == curr.right){
                  curr.right = node;
                  break;
              }
              curr = curr.right;
              
          }else{   // key point
              curr.val = node.val;
              break;
          }
      }
      
      return root;
  }
  
  public TreeNode insertNode_R(TreeNode root, TreeNode node) {
      //check
      if( null == node ){
          return root;
      }else if( null == root ){ // key point
          return node;
      }
      
      if(node.val < root.val){
          root.left = insertNode_R(root.left, node);
          
      }else if( node.val > root.val ){
          root.right = insertNode_R(root.right, node);
          
      }else{   // key point,  update
          root.val = node.val; 
      }
      
      return root;
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    //
    if(0 > Integer.MIN_VALUE)
      System.out.println("0 > Integer.MIN_VALUE");
    
  }

}
