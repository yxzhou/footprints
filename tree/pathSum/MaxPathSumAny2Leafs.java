package tree.pathSum;

import tree.TreeNode;

public class MaxPathSumAny2Leafs {
    /*
     * 
     * Given a binary tree, find the maximum path sum.
     * 
     * The path may start and end at any leaf in the tree.
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
        if( null == node){
            return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE}; // key point
        }
        if( null == node.left && null == node.right){
            return new int[]{node.val, node.val};
        }
    
        int[] left = maxPathSumHelper(node.left);
        int[] right = maxPathSumHelper(node.right);
    
        /* r[0]  max path from this node, that 1) start from leaf to node itself
         * r[1]  max path in subTree of this node, that 1) in left tree, 2) in right tree 3) left tree or right tree to node 
         */
        int[] r = new int[2];  
        r[0] = node.val + Math.max(left[0], right[0]); 
        r[1] = Math.max(Math.max(left[1], right[1]), r[0] + left[0] + right[0]);
       
        return r;
    }

    
    private int maxPathSum = Integer.MIN_VALUE; // global variable, 
    public int maxPathSum_2(TreeNode root) {
        maxPathSumHelper_2(root);
        return maxPathSum;
    }
      
      private int maxPathSumHelper_2(TreeNode node) {
          if ( null == node){
              return Integer.MIN_VALUE; // key point
          }
          if( null == node.left && null == node.right){
              return node.val;
          }
          
          int left = maxPathSumHelper_2(node.left);
          int right = maxPathSumHelper_2(node.right);
          
          int maxIncludeTheNode = node.val + Math.max( left, right);
          this.maxPathSum = Math.max(maxPathSum, node.val + left + right);
          
          return maxIncludeTheNode;
      }
    /**
     * @param args
     */
    public static void main(String[] args) {
      // TODO Auto-generated method stub

    }

}
