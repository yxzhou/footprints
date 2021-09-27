package tree.pathSum;

import tree.TreeNode;


public class MaxPathSumAny2Nodes
{


  
  /*
   * 
   * Given a binary tree, find the maximum path sum.
   * 
   * The path may start and end at any node in the tree.
   * 
   * For example:
   * Given the below binary tree,
   * 
   *        1
   *       / \
   *      2   3
   * Return 6.
   * 
   * bottom-up
   * Time O(n) Space O(1) 
   * 
   */
  public int maxPathSum(TreeNode root) {
    int[] r = maxPathSumHelper(root);
    return r[1];
  }
  
  private int[] maxPathSumHelper(TreeNode node) {
      if ( null == node)
          return new int[]{0, Integer.MIN_VALUE}; // key point
  
      int[] left = maxPathSumHelper(node.left);
      int[] right = maxPathSumHelper(node.right);
  
      /* r[0]  max path from this node, that 1) start from this node  2) this node itself
       * r[1]  max path in subTree of this node, that 1) in left tree, 2) in right tree, 3) path that includes node
       */
      int[] r = new int[2];  
      r[0] = Math.max(node.val, node.val + Math.max(left[0], right[0])); 
      r[1] = Math.max(Math.max(left[1], right[1]), Math.max(r[0], node.val + left[0] + right[0]));
     
      return r;
  }

  /**
   * 
   * !wrong,
   *   use case: node.val is negative
   */
  private int[] maxPathSumHelper_wrong(TreeNode node) {
      if ( null == node)
          return new int[]{0, 0}; // 
  
      int[] left = maxPathSumHelper(node.left);
      int[] right = maxPathSumHelper(node.right);
  
      /* r[0]  max path from this node, that 1) start from this node  2) this node itself
       * r[1]  max path in subTree of this node, that 1) in left tree, 2) in right tree, 3) path that includes node
       */
      int[] r = new int[2];  
      r[0] = node.val + Math.max(left[0], right[0]); 
      r[1] = Math.max(Math.max(left[1], right[1]), node.val + left[0] + right[0]);
     
      return r;
  }
  
  private int maxPathSum = Integer.MIN_VALUE; // global variable, 
  public int maxPathSum_2(TreeNode root) {
      maxPathSumHelper_2(root);
      return maxPathSum;
  }
    
    private int maxPathSumHelper_2(TreeNode node) {
        if ( null == node)
            return 0; // key point
        
        int left = maxPathSumHelper_2(node.left);
        int right = maxPathSumHelper_2(node.right);
        
        int maxIncludeTheNode = node.val + Math.max( left, right);
        this.maxPathSum = Math.max(maxPathSum, node.val + left + right);
        
        return maxIncludeTheNode > 0 ? maxIncludeTheNode : 0;
    }
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

   
}
