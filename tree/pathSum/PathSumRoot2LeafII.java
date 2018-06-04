package fgafa.tree.pathSum;

import java.util.ArrayList;
import java.util.List;

import fgafa.tree.TreeNode;

public class PathSumRoot2LeafII {
    /*
     * 
     * Given a binary tree and a sum, determine if the tree has a root-to-leaf path 
     * such that adding up all the values along the path equals the given sum.
     * 
     * For example:<br>
     * Given the below binary tree and sum = 22, <br>
     *               5            <br>
     *              / \           <br>
     *             4   8          <br>
     *            /   / \         <br>
     *           11  13  4        <br>
     *          /  \      \       <br>
     *         7    2      1      <br>
     * return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22. <br>
     * 
     */
    
      public boolean hasPathSum(TreeNode root, int sum) {

          if (root == null)
              return false;
          else if (sum == root.val && root.left == null && root.right == null)
              return true;

          return hasPathSum(root.left, sum - root.val)
                  || hasPathSum(root.right, sum - root.val);

      }


    /*
     * 
     * Given a binary tree and a sum, find all root-to-leaf paths where each 
     * path's sum equals the given sum.<br>
     * <br>
     * For example:<br>
     * Given the below binary tree and sum = 22,<br>
     *               5            <br>
     *              / \           <br>
     *             4   8          <br>
     *            /   / \         <br>
     *           11  13  4        <br>
     *          /  \    / \       <br>
     *         7    2  5   1      <br>
     * return     <br>
     * [
     *    [5,4,11,2],
     *    [5,8,4,5]
     * ]
     * 
     */
    
    public ArrayList<ArrayList<Integer>> pathSum(TreeNode root, int sum) {
      ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
      
      if(root == null)
        return result;
      
      ArrayList<Integer> subResult = new ArrayList<Integer> ();
      
      pathSum(root, sum, result, subResult);
      
      return result;
    }
    
    private void pathSum(TreeNode node, int sum, ArrayList<ArrayList<Integer>> result, ArrayList<Integer> subResult){
      
      subResult.add(node.val);
      
      if(node.val == sum && node.left == null && node.right == null){
        result.add(subResult);
        
        return ;
      }

      //clone 
      ArrayList<Integer> subResult4Right = new ArrayList<Integer> (subResult);
  //ArrayList<Integer> subResult4Right = new ArrayList<Integer> ();
//      subResult4Right.addAll(subResult);
//      for(int i=0; i<subResult.size(); i++){
//        subResult4Right.add(subResult.get(i));
//      }
      
      if(node.left != null)  //continue on right true
        pathSum(node.left, sum - node.val, result, subResult);

      if(node.right != null){ //continue on right true
        pathSum(node.right, sum - node.val, result, subResult4Right);
      }
    }
    
    /* this is better than pathSum in performance */
    public ArrayList<ArrayList<Integer>> pathSum_backtrack(TreeNode root, int sum) {
      ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
      
      if(root == null)
        return result;
      
      ArrayList<Integer> subResult = new ArrayList<Integer> ();
      //subResult.add(root.val);
      
      pathSum_backtrack(root, sum, result, subResult);
      
      return result;
    }
    
    private void pathSum_backtrack(TreeNode node, int sum, ArrayList<ArrayList<Integer>> result, ArrayList<Integer> subResult){
      
      subResult.add(node.val);
      
      if(node.val == sum && node.left == null && node.right == null){
        result.add(new ArrayList<Integer> (subResult));
      }else {
        if(node.left != null)  //continue on right true
          pathSum_backtrack(node.left, sum - node.val, result, subResult);

        if(node.right != null){ //continue on right true
          pathSum_backtrack(node.right, sum - node.val, result, subResult);
        }      
      }
      
      //back-track
      subResult.remove(subResult.size()-1);
    }
    
    
    public List<List<Integer>> pathSum_x(TreeNode root, int sum) {
          List<List<Integer>> result = new ArrayList<>();
          List<Integer> path = new ArrayList<>();
          
          pathSum_x(root, sum, path, result);
          
          return result;
    }
    
    public void pathSum_x(TreeNode node, int sum, List<Integer> path, List<List<Integer>> paths) {
        if(null == node){
            return;
        } else if( sum == node.val && null == node.left && null == node.right){
            List<Integer> validPath = new ArrayList<>(path);
            validPath.add(sum);
            
            paths.add(validPath);
        }else{
            path.add(node.val);
            pathSum_x(node.left, sum - node.val, path, paths);
            pathSum_x(node.right, sum - node.val, path, paths);
            path.remove(path.size() - 1);
        }
    }
}
