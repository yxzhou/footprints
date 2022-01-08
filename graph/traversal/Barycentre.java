/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.traversal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * For a multi-branch tree, if there is a node R with R as the root, and the largest sub-tree of all its sub-trees has
 * the least number of nodes, the node R is said to be the center of gravity of the tree. 
 * 
 * Now give you a multi-branch tree with n nodes. Find the center of gravity of this tree. If there are multiple centers
 * of gravity, return the one with the lowest number. 
 * 
 * x[i], y[i] represents the two points of the i-th edge.
 *   2 <= n <= 10^5 
 *   1 <= x[i], y[i] <= n 
 * 
 * Example 1:
 * Input: [1] [2] Output: 1
 * Explanation: Both 1 and 2 can be center of gravity, but the number of 1 is the smallest. 
 * 
 * Example 2:
 * Input: [1,2,2] [2,3,4] Output: 2
 * Explanation: 2 is the center of gravity.
 * 
 *        1 - 2 - 3
 *             \
 *              4
 * 
 * Solutions 1: DFS
 *   Start from 3, 
 * 
 */
public class Barycentre {
    
    public int getBarycentre(int[] x, int[] y) {
        Map<Integer, Set<Integer>> tree = new HashMap<>();
        
        for(int i = 1, n = x.length; i < n; i++){
            tree.computeIfAbsent(x[i], k -> new HashSet<>()).add(y[i]);
            tree.computeIfAbsent(y[i], k -> new HashSet<>()).add(x[i]);
        }
        
        int[] grevityCerter = {0, Integer.MAX_VALUE};//{the center of gravity, the number of nodes of the gravity certer's largest subtree}
        dfs(1, 0, tree,  grevityCerter);
        
        return grevityCerter[0];
    }
    
    /**
     *
     * @param curr
     * @param pre
     * @param tree
     * @param grevityCerter
     * @return the verticeNum of curr, excludes the pre
     */
    private int dfs(int curr, int pre, Map<Integer, Set<Integer>> tree, int[] grevityCerter){
        
        int subtreeNode;
        int total = 1; // the total of vertice number on edge pre-curr, curr inclusive
        int largestSubtree = 0; //the nodes number of curr's the largest sub-tree
        for(int next : tree.get(curr)){
            if(next == pre){
                continue;
            }
            
            subtreeNode = dfs(next, curr, tree, grevityCerter);
            total += subtreeNode;
            largestSubtree = Math.max(largestSubtree, subtreeNode);
        }
        
        largestSubtree = Math.max(largestSubtree, tree.size() - total); // graph.size() - total is the subtree of curr, on edge curr-pre
        if(grevityCerter[1] > largestSubtree || ( grevityCerter[1] == largestSubtree && grevityCerter[0] > curr)){
            grevityCerter[0] = curr;
            grevityCerter[1] = largestSubtree;
        }
        
        return total;
    }
    
}
