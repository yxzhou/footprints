package fgafa.tree;

import java.util.*;


/**
 * LeetCode #96, number of unique BST;  #95 generate unique BST
 *
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
      if(n < 3){
          return n;
      }

      int[] f = new int[n + 1];
      f[0] = 1;
      f[1] = 1;

      int mid;
      for(int i = 2; i <= n; i++){
          mid = i / 2;
          f[i] = ( (i & 1) == 1 ? f[mid] * f[mid] : 0 );
          for(int k = 0; k < mid; k++){
              f[i] += f[k]*f[i - k - 1]*2;
          }
      }

      return f[n];
  }
  
  /**
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

    /**
     *  recursive + memorize
     *
     */

    public List<TreeNode> generateTrees_x(int n) {
        if(n < 1){
            return Collections.EMPTY_LIST;
        }

        Map<Integer, Map<Integer, List<TreeNode>>> cache = new HashMap<>();
        cache.computeIfAbsent(0, x -> new HashMap<>()).computeIfAbsent(0, x -> new LinkedList<>()).add(null);
        cache.computeIfAbsent(1, x -> new HashMap<>()).computeIfAbsent(1, x -> new LinkedList<>()).add(new TreeNode(1));

        List<TreeNode> list;
        TreeNode root;
        for(int i = 2; i <= n; i++){
            list = new LinkedList<>();
            for(int j = 1; j <= i; j++){
                List<TreeNode> lefts = generateTrees(j - 1, 1, cache);
                List<TreeNode> rights = generateTrees(i - j, j + 1, cache);

                for(TreeNode l : lefts){
                    for(TreeNode r : rights){
                        root = new TreeNode(j);
                        root.left = l;
                        root.right = r;

                        list.add(root);
                    }
                }
            }

            cache.computeIfAbsent(i, x -> new HashMap<>()).put(1, list);
        }

        return cache.get(n).get(1);
    }

    private List<TreeNode> generateTrees(int n, int start, Map<Integer, Map<Integer, List<TreeNode>>> cache){
        Map<Integer, List<TreeNode>> map = cache.get(n);

        start = (n == 0)? 0 : start;

        if(map.containsKey(start)){
            return map.get(start);
        }

        map.put(start, clone(map.get(1), start - 1));

        return map.get(start);
    }

    private List<TreeNode> clone(List<TreeNode> list, int diff){
        List<TreeNode> result = new LinkedList<>();

        for(TreeNode root : list){
            result.add(clone(root, diff));
        }

        return result;
    }

    private TreeNode clone(TreeNode root, int diff){
        if(root == null){
            return root;
        }

        TreeNode result = new TreeNode(root.val + diff);

        result.left = clone(root.left, diff);
        result.right = clone(root.right, diff);

        return result;
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
			System.out.println(sv.generateTrees_x(i).size());
		}
    
  }

  
}
