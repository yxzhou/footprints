package fgafa.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


public class Height 
{

  /*
   * get the height of the tree, height is the max depth of the 
   */
  public int getHeight_R(TreeNode p){
    if(p == null)
      return 0;

    return Math.max(getHeight_R(p.left), getHeight_R(p.right)) + 1;
  }

  
  /*
   * BFS the tree with 2 stack 
   * 
   * 
   */
  public int getHeightBFS(TreeNode p){
    //check
    if(p == null) return 0;
    //init
    int hgt = 0;  // height
    Stack<TreeNode> currLevel = new Stack<TreeNode>();
    Stack<TreeNode> nextLevel = new Stack<TreeNode>();
    currLevel.add(p);
    TreeNode node;
    
    while (!currLevel.isEmpty()) {
      while (!currLevel.isEmpty()) {
        node = (TreeNode) currLevel.pop();
        if (node.left != null)
          nextLevel.add(node.left);
        if (node.right != null)
          nextLevel.add(node.right);
        
      }
      hgt++;
      Stack tmp = currLevel;
      currLevel = nextLevel;      
      nextLevel = tmp;
    }
    
    //return
    return hgt;
  }
 
  /* travel by level  */
  public int getHeightBFS20(TreeNode p){
    //check
    if(p == null) return 0;
    
    //init
    int height = 0;  // height
    LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
    queue.add(p);
    int count = 0, size = 1;
    TreeNode node;
    
    //main
    while(count < size){
      node = queue.pop();
              
        if (node.left != null)
            queue.add(node.left);
        if (node.right != null)
            queue.add(node.right);
        
      if( ++count == size){
        height++;
        
        count = 0;
        size = queue.size();
      }
    }
    
    //return
    return height;
  }
  
  
  /**
   *  TODO
   * 
   */
  protected int getHeightPostOrder(TreeNode p) {
    //TODO
    //http://www.leetcode.com/2010/04/maximum-height-of-binary-html
    return -1;
  }
  
  
/*
 * Given a binary tree, find its minimum depth.
 * 
 * The minimum depth is the number of nodes along the shortest path from the 
 * root node down to the nearest leaf node.
 */
  public int minDepth_recurr(TreeNode root) {
    if(root == null)
      return 0;
      
    if(root.left == null && root.right == null)  //leaf
      return 1;
    
    int result = Integer.MAX_VALUE;
    if(root.left != null)
      result = Math.min(result, minDepth_recurr(root.left) + 1);
    
    if(root.right != null)
      result = Math.min(result, minDepth_recurr(root.right) + 1);
    
    return result;
  }
  
  /* Wrong !! */
  public int minDepth_recurr2(TreeNode root) {
    if(root == null)
      return 0;
    
     return Math.min(minDepth_recurr2(root.left), minDepth_recurr2(root.right)) + 1;
  }
  
  /* correct minDepth_recurr2*/
  public int minDepth_recurr3(TreeNode root) {
      if(root == null)
          return 0;
      
      return minDepth_recurr3_helper(root);
  }
  private int minDepth_recurr3_helper(TreeNode root) {
    if(root == null)
      return Integer.MAX_VALUE;
    
    if(root.left == null && root.right == null)  //leaf
      return 1;
    
     return Math.min(minDepth_recurr3(root.left), minDepth_recurr3(root.right)) + 1;
  }
  
  /**
   * Given a binary tree, find its maximum depth.
   * 
   * The maximum depth is the number of nodes along the longest path from the
   * root node down to the farthest leaf node.
   */
  public int maxDepth(TreeNode root) {
      int max = 0;
      if(null == root)
          return max;
      
      max = Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
      return max;
  }

  public int maxDepth_levelOrder(TreeNode root) {
      if(root == null)    return 0;
       
      // Non-recursive, use level order triversal
      List<TreeNode> q = new ArrayList<TreeNode>();
      q.add(root);
      int depth = 0;
       
      while(!q.isEmpty()) {
          ArrayList<TreeNode> next = new ArrayList<TreeNode>();
          for(TreeNode node : q) {
              if(node.left != null)   next.add(node.left);
              if(node.right != null)  next.add(node.right);
          }
          q = new ArrayList<TreeNode>(next);
          depth++;
      }
       
      return depth;
  }
  
  /**
   * 
   * Given a binary tree, find its minimum depth.
   * 
   * The minimum depth is the number of nodes along the shortest path from the
   * root node down to the nearest leaf node.
   */
  /**
   *                1      
   *              /   \
   *            null   2  
   *                  /  \   
   *                null null 
   *
   */
  public int minDepth(TreeNode root) {
      if(null == root)
          return 0;
      
      //return Math.min(minDepth(root.left), minDepth(root.right)) + 1;  //this is wrong
      
      int min = 1;
      if(null != root.left && null != root.right){ // both left and right are not null
          min += Math.min(minDepth(root.left), minDepth(root.right));
      }else if(null == root.left){ //only left is null
          min += minDepth(root.right);
      }else if(null == root.right){ //only right is null
          min += minDepth(root.left);
      }
      
      return min; 
  }
  
  public int minDepth_2(TreeNode root) {
      if(null == root)
          return 0;
      
      int[] min = new int[1];
      min[0] = Integer.MAX_VALUE;
      
      minDepth(root, 0, min);
      
      return min[0];
  }
  
  private void minDepth(TreeNode node, int depth, int[] min){
      depth++;
      if(null == node.left && null == node.right){
          min[0] = depth;
          return;
      }
      
      if(null != node.left){
          minDepth(node.left, depth, min);
      }
      
      if(null != node.right){
          minDepth(node.right, depth, min);
      }
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {

    List<TreeNode> list = BinaryTree.initBTs();

    //traverse

    Height hgh = new Height();
    Print pnt = new Print();
    
    TreeNode root;
    for(int i=0; i < list.size(); i++){  
      root =  (TreeNode)list.get(i);
      System.out.print("\n"+ i +" ===  The tree === \n");
      pnt.printTreeShape(root);
      
      System.out.println("\ngetHeight_R:" + hgh.getHeight_R(root)+ "\tgetHeightBFS:"+hgh.getHeight_R(root)+"\tgetHeightPostOrder:"+hgh.getHeightPostOrder(root));
    }
    
    
  }
  
}
