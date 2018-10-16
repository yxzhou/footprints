package fgafa.uva.tree.TreeSumN112;

import java.io.*;
import java.util.*;

class Main
{

  /* Definition for binary tree */
  class TreeNode
  {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
  
  private boolean hasPathSum(TreeNode root, int sum) {

    if(root == null )
      return false;
    else if( sum == root.val && root.left == null && root.right == null )
      return true;
    
    
    return hasPathSum(root.left, sum - root.val) ||  hasPathSum(root.right, sum - root.val);
    
  }
  
  private TreeNode buildTree(String tree, ArrayList<Integer> count, int offset){
    //init
    int i=offset, j=offset+1;
    
    //parse the parent
    while( j<count.size() && count.get(i) == count.get(j)) j++;
    if(i == j - 1)
      return null;
    TreeNode node = new TreeNode(Integer.valueOf(tree.substring(i+1, j)));
    
    //parse the right and left child
    int midIndex = indexOf(count, count.get(i), j);
    node.left = buildTree(tree, count, j);
    node.right = buildTree(tree, count, midIndex+1);
    
    return node;
  }
  
  private int indexOf(ArrayList<Integer> count, int target, int offset){

    for (int i = offset; i < count.size(); i++)
      if (count.get(i) == target)
          return i;

    return -1;
  }
  
  public boolean hasPathSum(String tree, ArrayList<Integer> count) {
    //init
    int i=0, j=1;
    //parse the SUM 
    while(j<count.size() && count.get(i) == count.get(j)) j++;
    int sum = Integer.valueOf(tree.substring(i, j));
    
    //parse and build the tree
    TreeNode root = buildTree(tree, count, j);

    //check from root-leaf recursively 
    return hasPathSum(root, sum);

  }

  
  public static void main(String[] args) throws Exception {

    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");

    int cnt = 0;
    boolean flag = false;
    StringBuilder tree = new StringBuilder(); 
    ArrayList<Integer> count = new ArrayList<Integer>();
    try {
      while (in.hasNext()) {        
        char[] chars = in.nextLine().toCharArray();
        
        for(int i=0; i<chars.length; i++){
          if(chars[i] != ' ' && chars[i] != '\n'){
            tree.append(chars[i]);

            if(chars[i] == '('){
              cnt ++;
              flag = true;
            }else if(chars[i] == ')')
              cnt --;
            count.add(cnt );            
          }
        }
        
        if(cnt == 0 && flag){
          if(sv.hasPathSum(tree.toString(), count))
            System.out.println("yes");
          else
            System.out.println("no");
          
          tree = new StringBuilder();
          count = new ArrayList<Integer>();
          flag = false;
        }
        
      }

    }
    catch (Exception e) {
      //e.printStackTrace();
    }
    finally {
      in.close();
    }

  }

}
