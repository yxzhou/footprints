package fgafa.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fgafa.util.Misc;

/*
 * 
 * Given a binary tree, return the vertical order traversal of its nodes' values. (ie, from top to bottom, column by column).
    If two nodes are in the same row and column, the order should be from left to right.
    
    Examples:
    Given binary tree [3,9,20,null,null,15,7],
        3
       / \
      9  20
        /  \
       15   7
    return its vertical order traversal as:
    [
      [9],
      [3,15],
      [20],
      [7]
    ]
    
    Given binary tree [3,9,20,4,5,2,7],
        _3_
       /   \
      9    20
     / \   / \
    4   5 2   7
    return its vertical order traversal as:
    [
      [4],
      [9],
      [3,5,2],
      [20],
      [7]
    ]
 *
 */

public class TraversalVerticalOrder {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        
        if(null == root){
            return result;
        }

        int height = height(root);
        int n = (1 << (height - 1));
        for(int i = n; i >= 0; i--){
            result.add(new ArrayList<Integer>());
        }
        
        helper(root, result, 0, n);
        
        for(int i = result.size() - 1; i >= 0; i--){  //back forward
            if(result.get(i).isEmpty()){
                result.remove(i);
            }
        }
        
        return result;
    }
    
    private void helper(TreeNode node, List<List<Integer>> result, int low, int high){
        if(null == node){
            return;
        }
        
        int mid = low + ((high - low) >> 1);
        if(mid == low && (low & 1) == 1){
            mid++;
        }
        result.get(mid).add(node.val);
        
        helper(node.left, result, low, mid);
        helper(node.right, result, mid, high);
    }
    
    private int height(TreeNode node){
        if(null == node){
            return 0;
        }
        
        int left = height(node.left);
        int right = height(node.right);
        return Math.max(left, right) + 1;
    }
    

    public static void main(String[] args) {

        List<TreeNode> list = BinaryTree.initBTs();

        // traverse
        TraversalVerticalOrder sv = new TraversalVerticalOrder();
        Print pnt = new Print();
        
        TreeNode root;
        for (int i = 0; i < list.size(); i++) {
            root = (TreeNode) list.get(i);
            System.out.print("\n" + i + " ===  The tree === \n");
            pnt.printTreeShape(root);

            System.out.println("\t verticalOrder: ");
            Misc.printListList(sv.verticalOrder(root));

        }

    }
}
