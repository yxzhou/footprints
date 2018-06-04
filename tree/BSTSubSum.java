package fgafa.tree;

import java.util.Stack;


/*
 * Problem: 
 *  Given a binary search tree, please check whether there are two nodes in it whose sum equals a given value.
 * 
 * Solution: 
 *  it similar to two numbers in a sorted array whose sum equals a given value
 */
public class BSTSubSum
{

  /*
   * Time O(n); Space O(height), which is O(logn) on average, O(n) in the worst case  
   */
  public boolean hasTwoNodes(Node root, int sum) {
    Stack<Node> smallNodes = new Stack<Node>();
    Stack<Node> bigNodes = new Stack<Node>();
    buildSmallNodes(root, smallNodes);
    buildBigNodes(root, bigNodes);

    Node smallNode = getNextSmallNode(smallNodes);
    Node bigNode = getNextBigNode(bigNodes);
    while (smallNode != null && bigNode != null && smallNode != bigNode) {
      int currentSum = smallNode.val + bigNode.val;
      if (currentSum == sum)
        return true;

      if (currentSum < sum)
        smallNode = getNextSmallNode(smallNodes);
      else
        bigNode = getNextBigNode(bigNodes);
    }

    return false;
  }

  private void buildSmallNodes(Node root, Stack<Node> stack) {
    for (Node node = root; node != null; node = node.left)
      stack.push(node);
  }

  private void buildBigNodes(Node root, Stack<Node> stack) {
    // Node node = root;
    // while(node != null)
    // {
    // nodes.push(node);
    // node = node.right;
    // }
    for (Node node = root; node != null; node = node.right)
      stack.push(node);

  }

  private Node getNextSmallNode(Stack<Node> stack) {
    Node next = null;
    if (!stack.empty()) {
      next = stack.pop();

      // Node right = next.right;
      // while(right != null)
      // {
      // nodes.push(right);
      // right = right.left;
      // }
      buildSmallNodes(next.right, stack);
    }

    return next;
  }



  private Node getNextBigNode(Stack<Node> stack) {
    Node next = null;
    if (!stack.empty()) {
      next = stack.pop();
      buildBigNodes(next.left, stack);
    }

    return next;
  }



  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

  /* Definition for binary tree */
  public class Node
  {
    int val;
    Node left;
    Node right;



    Node(int x) {
      val = x;
    }
  }
}
