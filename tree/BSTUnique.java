package fgafa.tree;

import java.util.ArrayList;
import java.util.List;


/**
 * Given n, how many structurally unique BST's (binary search trees) that store values 1...n?
 * 
 * For example,
 * Given n = 3, there are a total of 5 unique BST's.
 * 
 * 	   1         3     3      2      1
 * 	    \       /     /      / \      \
 * 	     3     2     1      1   3      2
 * 	    /     /       \                 \
 * 	   2     1         2                 3
 */

public class BSTUnique
{

  /*
   * DP
   *  define f(n),  how many structurally unique BST
   * 
   * f(n) = 1   when n = 0 or 1
   *      = sum of f(i) * f(n-1-i),  i is from 0 to n-1, n >1 
   *        
   *  Catalan number ,  similar to UniquePaths.java
   */
  public int numTrees(int n) {
      if(n < 0){
          return 0;
      }else if( n < 2){
          return 1;
      }
      
      int[] result = new int[n+1]; // default all are 0
      result[0] = 1;
      result[1] = 1;

      int mid;
      for(int i = 2; i <=n; i++){
          mid = i >> 1;

          for(int j = 0, k = i - 1; j < mid; j++, k--){
              result[i] += result[j] * result[k];
          }
          result[i] <<= 1;
          
          if( (i & 1) == 1 ){
              result[i] += result[mid] * result[mid];
          }
      }

      return result[n];
  }
  
  /*
   * Given n, generate all structurally unique BST's (binary search trees) that store values 1...n.
   * 
   */
  public List<TreeNode> generateTrees_n(int n) {
      return generate(1, n);
  }
  private ArrayList<TreeNode> generate(int start, int end) {
      ArrayList<TreeNode> result = new ArrayList<TreeNode>();
      if (start > end) {
          result.add(null);
          return result;
      }
      
      ArrayList<TreeNode> left;
      ArrayList<TreeNode> right;
      TreeNode node;
      for (int i = start; i <= end; i++){
          left = generate(start, i - 1);
          right = generate(i +  1, end);
          
          for (TreeNode l : left) {
              for (TreeNode r : right) {
                  node = new TreeNode(i);
                  node.left = l;
                  node.right = r;
                  result.add(node);
              }
          }
      }
      return result;
  }
  
    public List<TreeNode> generateTrees(int n) {
        List<List<TreeNode>> structures = new ArrayList<List<TreeNode>>();
        List<TreeNode> ret = new ArrayList<TreeNode>();
        // check
        if (n < 0)
            return ret; //

        ret.add(null);
        if (n == 0)
            return ret;
        structures.add(0, ret); // save structure(0) in index 0

        ret = new ArrayList<TreeNode>();
        ret.add(new TreeNode(1));
        if (n == 1)
            return ret;
        structures.add(1, ret); // save structure(1) in index 1

        for (int i = 2; i <= n; i++) {
            ret = new ArrayList<TreeNode>();
            for (int j = 1; j <= i; j++) {

                List<TreeNode> leftTrees = null;
                if (j - 1 > 0) {
                    leftTrees = structures.get(j - 1);
                    // makeUp(leftTree, buildMapping(0, j-1) );
                }

                List<TreeNode> rightTrees = null;
                if (j < i) {
                    rightTrees = structures.get(i - j);
                    rightTrees = cloneTrees(rightTrees, j);
                }

                ret.addAll(buildTrees(leftTrees, rightTrees, new TreeNode(j)));
            }

            structures.add(i, ret);
        }

        return ret;
    }

    private List<TreeNode> buildTrees(List<TreeNode> left,
                                      List<TreeNode> right,
                                      TreeNode parent) {
        ArrayList<TreeNode> ret = new ArrayList<TreeNode>();

        /* no left, no right */
        if (left == null && right == null)
            return ret;

        List<TreeNode> leftTrees = null;
        if (left != null)
            leftTrees = cloneTrees(left, 0);
        List<TreeNode> rightTrees = null;
        if (right != null)
            rightTrees = cloneTrees(right, 0);

        TreeNode curr = null;

        /* only has right trees */
        if (leftTrees == null) {

            for (int j = 0; j < rightTrees.size(); j++) {
                curr = new TreeNode(parent.val);
                curr.right = rightTrees.get(j);

                ret.add(curr);
            }
            return ret;
        }

        /* only has left trees */
        if (rightTrees == null) {
            for (int i = 0; i < leftTrees.size(); i++) {
                curr = new TreeNode(parent.val);
                curr.left = leftTrees.get(i);

                ret.add(curr);

            }
            return ret;
        }

        /* have both right and left trees */
        TreeNode leftT = null;
        for (int i = 0; i < leftTrees.size(); i++) {
            leftT = leftTrees.get(i);
            for (int j = 0; j < rightTrees.size(); j++) {
                curr = new TreeNode(parent.val);
                curr.left = leftT;
                curr.right = rightTrees.get(j);

                ret.add(curr);
            }
        }

        return ret;
    }
  
  private List<TreeNode> cloneTrees( List<TreeNode> ret, int offset ){

    if(ret == null)
      return null;

    ArrayList<TreeNode> result = new  ArrayList<TreeNode>();;
    
    for(int i=0; i< ret.size(); i++){
      result.add(cloneTree(ret.get(i),offset));
    }
    
    return result;
  }
  
  /*
   * recursive
   */
  private TreeNode cloneTree(TreeNode node, int offset){
    TreeNode curr = null;
    if(node != null){
      curr = new TreeNode(node.val + offset);
    
      curr.left = cloneTree(node.left, offset) ;
      curr.right = cloneTree(node.right, offset) ;    
    }
    return curr;
  }
  
//  
//  private int[] buildMapping(int start, int end){
//    int[] vals = new int[end - start + 1];
//    vals[0] = 0;
//    
//    for(int i=1, val = start; val <= end; val ++, i++ )
//      vals[i] = val;
//    
//    return vals;
//  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    /* fundmental test  
    
    ArrayList<TreeNode> ret = new ArrayList<TreeNode>();
    System.out.println(ret.size());
    
    //ret.get(0);
    ret.add(null);
*/
    
		/* generateTrees test */
		BSTUnique sv = new BSTUnique();

		for (int i = -1; i < 10; i++) {
			System.out.println("\n n = " + i + "\t numTree = " + sv.numTrees(i));
		}

		for (int i = 0; i < 6; i++) {
			System.out.println(sv.generateTrees(i).size());
		}
    
  }

  
}
