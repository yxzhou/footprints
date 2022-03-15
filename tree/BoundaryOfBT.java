/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/878
 *
 * Given a binary tree, return the values of its boundary in anti-clockwise direction starting from root. Boundary
 * includes left boundary, leaves, and right boundary in order without duplicate nodes.
 *
 * Left boundary is defined as the path from root to the left-most node. Right boundary is defined as the path from root
 * to the right-most node. If the root doesn't have left subtree or right subtree, then the root itself is left boundary
 * or right boundary. Note this definition only applies to the input binary tree, and not applies to any subtrees.
 *
 * The left-most node is defined as a leaf node you could reach when you always firstly travel to the left subtree if
 * exists. If not, travel to the right subtree. Repeat until you reach a leaf node.
 *
 * The right-most node is also defined by the same way with left and right exchanged.
 * 
 * 
 * Example 1:
 * Input: {1,#,2,3,4}
 * Output: [1,3,4,2]
 * Explanation: 
  1
   \
    2
   / \
  3   4
 * The root doesn't have left subtree, so the root itself is left boundary.
 * The leaves are node 3 and 4.
 * The right boundary are node 1,2,4. Note the anti-clockwise direction means you should output reversed right boundary.
 * So order them in anti-clockwise without duplicates and we have [1,3,4,2]
 * 
 * 
 * Example 2:
 * Input: {1,2,3,4,5,6,#,#,#,7,8,9,10}
 * Output: [1,2,4,7,8,9,10,6,3]
 * Explanation: 
          1
     /          \
    2            3
   / \          / 
  4   5        6   
     / \      / \
    7   8    9  10  
 * The left boundary are node 1,2,4. (4 is the left-most node according to definition)
 * The leaves are node 4,7,8,9,10
 * he right boundary are node 1,3,6,10. (10 is the right-most node).
 * So order them in anti-clockwise without duplicate nodes we have [1,2,4,7,8,9,10,6,3]
 * 
 * 
 * Example 3:
 * Input: {1,#,2,3,4,5,6,#,10,#,#,7,9,11,#,#,8}
 * Output: [1,5,8,9,11,10,4,2]
 * Explanation: 
        1
         \
          2
         /  \
        3    4  
       / \    \
      5   6    10
         / \   / 
        7   9 11  
         \
          8
 * The left boundary are node 1. 
 * The leaves are node 5,8,9,11
 * he right boundary are node 1,2,4,10. 
 * So order them in anti-clockwise without duplicate nodes we have [1,5,8,9,11,10,4,2] 
 * 
 * Example 4:
 * Input: {2,3,4,5,6,#,10,#,#,7,9,11,#,#,8}
 * Output: [1,5,8,9,11,10,4,2]
 * Explanation: 
          2
         /  \
        3    4  
       / \    \
      5   6    10
         / \   / 
        7   9 11  
         \
          8
 * The left boundary are node 2,3,5. (5 is the left-most node according to definition)
 * The leaves are node 5,8,9,11
 * he right boundary are node 2,4,10. 
 * So order them in anti-clockwise without duplicate nodes we have [2,3,5,8,9,11,10,4] 
 * 
 */
public class BoundaryOfBT {
    /**
     * @param root: a TreeNode
     * @return: a list of integer
     */
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        if(root == null){
            return Collections.EMPTY_LIST;
        }
        
        List<Integer> result = new ArrayList<>();
        result.add(root.val);
        
        if(root.left == null && root.right == null ){
            return result;
        }
                
        dfsLeftMost(root.left, result);
        dfsLeaf(root, result);
        dfsRightMost(root.right, result);

        return result;
    }
    
    private void dfsLeftMost(TreeNode node, List<Integer> result){
        if(node == null || (node.left == null && node.right == null)){
            return;
        }
        
        result.add(node.val);

        if(node.left != null){
            dfsLeftMost(node.left, result);
        }else{
            dfsLeftMost(node.right, result);
        }
        
    }
    private void dfsRightMost(TreeNode node, List<Integer> result){
        if(node == null || (node.left == null && node.right == null)){
            return;
        }

        if(node.right != null){
            dfsRightMost(node.right, result);
        }else{
            dfsRightMost(node.left, result);
        }
        
        result.add(node.val);
    }
    private void dfsLeaf(TreeNode node, List<Integer> result){
        if(node == null){
            return;
        }
        
        if(node.left == null && node.right == null){
            result.add(node.val);
            return;
        }
        
        dfsLeaf(node.left, result);
        dfsLeaf(node.right, result);
    }
    
    /**
     * @param root: a TreeNode
     * @return: a list of integer
     */
    public List<Integer> boundaryOfBinaryTree_n(TreeNode root) {
        if(root == null){
            return Collections.EMPTY_LIST;
        }
        
        List<Integer> result = new ArrayList<>();
        result.add(root.val);
                
        dfs(root.left, true, false, result);
        dfs(root.right, false, true, result);

        return result;
    }
    
    public void dfs(TreeNode node, boolean isLeftMost, boolean isRightMost, List<Integer> result) {
        if (node == null){
            return;
        }
            
        if (node.left == null && node.right == null) { //it is leaf
            result.add(node.val);
            return;
        }
        
        if (isLeftMost){
            result.add(node.val);// left-most
        }
        
        dfs(node.left, isLeftMost, isRightMost && node.right == null, result);
        dfs(node.right, isLeftMost && node.left == null, isRightMost, result);
        
        if (isRightMost){
            result.add(node.val);// right-most
        }
    }
    
    public static void main(String[] args){
        String[][] inputs = {
            {
                "1,#,2,3,4",
                "1,3,4,2"
            },
            {
                "1,2,3,4,5,6,#,#,#,7,8,9,10",
                "1,2,4,7,8,9,10,6,3"
            },
            {
                "1,#,2,3,4,5,6,#,10,#,#,7,9,11,#,#,8",
                "1,5,8,9,11,10,4,2"
            },
            {
                "2,3,4,5,6,#,10,#,#,7,9,11,#,#,8",
                "2,3,5,8,9,11,10,4"
            }
        };
        
        BoundaryOfBT sv = new BoundaryOfBT();
        
        for(String[] input : inputs){
            System.out.println(input[0]);
            
            Assert.assertEquals(input[1], Misc.array2String(sv.boundaryOfBinaryTree(BinaryTree.buildTree_levelOrder(input[0])), false).toString());
            Assert.assertEquals(input[1], Misc.array2String(sv.boundaryOfBinaryTree_n(BinaryTree.buildTree_levelOrder(input[0])), false).toString());
        }
        
    }
    
}
