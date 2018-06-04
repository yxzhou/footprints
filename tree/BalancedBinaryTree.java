package fgafa.tree;

import java.util.ArrayList;

import fgafa.util.Misc;


public class BalancedBinaryTree
{
  
/*
 * Given a binary tree, determine if it is height-balanced. (approximately balanced.  AVL)
 * 
 * Note: For this problem, a height-balanced binary tree is defined as a binary tree 
 * in which the depth of the two subtrees of every node never differ by more than 1.
 * 
 
 */
  public boolean isBalanced_subtree(TreeNode root) {
    return  -1 != isBalanced_subtreeHelp(root);
  }
  
  /**
   * 
   */
  private int isBalanced_subtreeHelp(TreeNode node){
    if(null == node)
      return 0;
  
    int leftSubTreeH = isBalanced_subtreeHelp(node.left); 
    int rightSubTreeH = isBalanced_subtreeHelp(node.right);
    
    if(-1 == leftSubTreeH && -1 == rightSubTreeH && Math.abs(leftSubTreeH - rightSubTreeH) < 2)
       return Math.max(leftSubTreeH, rightSubTreeH) + 1;
    else
       return -1;
    
  }
  
  
  /**
   * Given a binary tree, determine if it is height-balanced.  ( prefect balanced. )
   * 
   * Note: For this problem, a height-balanced binary tree is defined as a binary tree 
   * in which the depth of "ANY leaf" never differ by more than 1.
   * 
   */
  public boolean isBalanced_AnyLeaf(TreeNode root) {
    if(root == null)
      return true;
    
    ArrayList<Integer> heights = new ArrayList<Integer>();
    return isBalanced_AnyLeafHelp(root, 1, heights) ;
  }

  private boolean isBalanced_AnyLeafHelp(TreeNode node, int currLevel, ArrayList<Integer> heights){
    if(node == null){
      if(heights.size() == 2 && (currLevel != heights.get(0) && currLevel != heights.get(1)) )
        return false;
      
      if(heights.size() == 1 ){
        if( Math.abs(currLevel - heights.get(0)) > 1 )
          return false;
        else if (currLevel != heights.get(0))
          heights.add(currLevel);    
      }
      
      if(heights.size() == 0)
        heights.add(currLevel);     
      
      return true;
    }
    
    
    return isBalanced_AnyLeafHelp(node.left, currLevel + 1, heights) && isBalanced_AnyLeafHelp(node.right, currLevel + 1, heights);
    
  }
  
  /*
   * build AVL tree with sorted array in ascending order
   * 
   */
  public TreeNode sortedArrayToAVT(int[] num){
      //check
      if(null == num || 0== num.length)
          return null;
          
      return buildTree(num, 0, num.length);
  }
  private TreeNode buildTree(int[] num, int start, int end){
      if(start == end)
          return null;
      
      int middle = start + (end - start) / 2;
      TreeNode root = new TreeNode(num[middle]);
      root.left = buildTree(num, start, middle);
      root.right = buildTree(num, middle + 1, end);
      
      return root;
  }
  
  /*TODO*/
  public TreeNode buildAVT_iteral(int[] sortedArray){
    //check input
    if(sortedArray == null || sortedArray.length == 0)
      return null;
    
    int start = 0, end = sortedArray.length;
    int mid = start + ((end - start) >> 1);
    TreeNode root = new TreeNode(sortedArray[mid]);    
    
//    for(int height = (int)Math.log(end), space = (end >> 2); height > 0; height--){
//      
//      for()
//        addNodeinBST( new TreeNode(sortedArray[space]) , root);
//      space
//      
//    }
    
    
    
    return root;
  }
  
  /*
   *build AVL tree with a singly linked list where elements are sorted in ascending order
   * 
   */
  public TreeNode sortedListToAVT(ListNode list){
    if(list == null )
      return null;
    
    int length = 0;
    for( ListNode node = list; node != null; node = node.next )
      length ++;
    
    Element result = sortedListToAVT(list, 0, length - 1);
    return result.t;
  }
  
  private Element sortedListToAVT(ListNode head, int start, int end){
    if(start > end) return null;
          
    int mid = start + ((end - start) >> 1); //(start + end) / 2;
    
    Element left = sortedListToAVT(head, start, mid - 1);
    
    head = left.n;
    TreeNode parent = new TreeNode(head.val);
    parent.left = left.t;
    
    Element right = sortedListToAVT(head.next, mid + 1, end);
    parent.right = right.t;
    
    return new Element(right.n, parent);
  }
  
  class Element{
    ListNode n;
    TreeNode t;
    
    public Element(ListNode n, TreeNode t){
        this.n=n;
        this.t=t;
    }
  } 
  
  public TreeNode sortedListToAVT_x(ListNode head) {  
	  return buildTree(head, null);
  }
  private TreeNode buildTree(ListNode head, ListNode end){
	  if(head == end)
		  return null;
	  
	  ListNode slow = head, fast = head;
	  while(fast != end && fast.next != end && fast.next.next != end ){
		  slow = slow.next;
		  fast = fast.next.next;
	  }
	  
	  TreeNode root = new TreeNode(slow.val);
	  root.left = buildTree(head, slow);
	  root.right = buildTree(slow.next, end);
	  
	  return root;
  }
  
  /*  TODO,  rebalance ??   */
  private void addNodeinBST(TreeNode node, TreeNode root){
    
    while(root != null){
      if( node.val < root.val ){
        if(root.left != null)
          root = root.left;
        else
          root.left = node;    
      }else if(node.val > root.val){ 
         if(root.right != null)
           root = root.right;
         else
           root.right = node;
      }else // node.val == root.val
         break;
    }
      
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    BalancedBinaryTree tree = new BalancedBinaryTree();
    TreeNode root;

    //buildAVL
    int[][] arr = {null,{}, {1, 3}, {0, 2, 3, 4, 5, 6, 7, 8, 9}};
    
    for(int i=0; i< arr.length; i++){
      System.out.println("\nthe sorted array is:" + Misc.array2String(arr[i]) );
      
      //build with array
//      root = tree.sortedArrayToAVT(arr[i]);
      
//      System.out.println("the AVL is:" );
//      Traverse.inorder_R(root);
//      
//      //build with linked list
//      fgafa.linkedlist.Linkedlist list = new fgafa.linkedlist.Linkedlist();
//      list.buildLL(arr[i]);
//      
//      root = tree.sortedListToAVT(list.getHeader());
//      System.out.println("\nthe AVL is:" );
//      Traverse.inorder_R(root);      

  }
    
 }
  
  /*Definition for LinkedList*/
  public class ListNode{
    int val;
    ListNode next;
  }
}
