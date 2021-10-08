/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Given a binary tree, return the vertical order traversal of its nodes' values. (ie, from top to bottom, column by column).
    If two nodes are in the same row and column, the order should be from left to right.
    
 * similar with TraversalVerticalOrderII, the difference is 
 * 
    Examples:

    Given binary tree [3,9,20,4,5,2,7],
        _3_
       /    \
      9     8
     / \   / \
    4   0 1   7
         X
       5   2
    return its vertical order traversal as:
    [
      [4],
      [9,5],
      [3,0,1],
      [8,2],
      [7]
    ]
 * 
 */
public class TraversalVerticalOrderI {
    /**
     * Wrong !! 
     * 
     * The order is from left child tree to right child tree, then from top to bottom.  
     * The requirement is from top to bottom then from left to right.  
     */
    public List<List<Integer>> verticalOrder_dfs(TreeNode root) {
        TreeMap<Integer, List<Integer>> map = new TreeMap<>(); // <offset, list of node vals> 
        
        dfs(root, 0, map);
        
        return new ArrayList<>(map.values());
    }
    
    private void dfs(TreeNode node, int id, TreeMap<Integer, List<Integer>> map){
        map.putIfAbsent(id, new LinkedList<>());
        map.get(id).add(node.val);

        if(node.left != null){
            dfs(node.left, id - 1, map);
        }
        if(node.right != null){
            dfs(node.right, id + 1, map);
        }
    }
    
    public List<List<Integer>> verticalOrder_bfs(TreeNode root) {
        if(root == null){
            return Collections.EMPTY_LIST;
        }

        TreeMap<Integer, List<Integer>> map = new TreeMap<>(); // <offset, list of node vals> 
        
        //bfs
        Map<TreeNode, Integer> nodeIds = new HashMap<>();
        nodeIds.put(root, 0);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        TreeNode top;
        Integer id;
        while(!queue.isEmpty()){
            top = queue.poll();
            id = nodeIds.get(top);

            map.putIfAbsent(id, new LinkedList<>());
            map.get(id).add(top.val);

            if(top.left != null){
                queue.add(top.left);
                nodeIds.put(top.left, id - 1);
            }
            if(top.right != null){
                queue.add(top.right);
                nodeIds.put(top.right, id + 1);
            }            
        }
        
        return new ArrayList<>(map.values());
    }
    
}
