package fgafa.tree.pathSum;

import fgafa.tree.TreeNode;

/*
 * Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
 * 
 * An example is the root-to-leaf path 1->2->3 which represents the number 123.
 * 
 * Find the total sum of all root-to-leaf numbers.
 * 
 * For example,
 * 
 *     1
 *    / \
 *   2   3
 * The root-to-leaf path 1->2 represents the number 12.
 * The root-to-leaf path 1->3 represents the number 13.
 * 
 * Return the sum = 12 + 13 = 25.
 */
public class PathSumRoot2Leaf
{

  class TreeSum
  {
    private int sum = 0;

    void addSum(int x) {
      this.sum += x;
    }

    int getSum() {
      return this.sum;
    }
  }

  public int sumNumbers(TreeNode root) {
      int ret[] = new int[1]; //default it's 0
      sumNumbers(root, 0, ret);
      return ret[0];
  }

  /*
   * 1 calculate on the leaf
   * 2 recursion, tail call
   */
  private void sumNumbers(TreeNode node, int pre, int[] ret){
      if(null != node){
          pre = pre * 10 + node.val;
          if(null == node.left && null == node.right){
              ret[0] += pre;
          }else{
              sumNumbers(node.left, pre, ret);
              sumNumbers(node.right, pre, ret);
          }
      }
  }
  
  
  public int sumNumbers2(TreeNode root) {
    if (root == null)
      return 0;

    return dfs(root,0);
  }
  
  private int dfs(TreeNode node, int sum){
    if(node==null) return 0;
    
    sum=sum*10+node.val;
    if(node.left==null && node.right==null) return sum;
    return dfs(node.left,sum) + dfs(node.right,sum);
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
