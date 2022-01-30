/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.unionFind;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * _https://www.lintcode.com/problem/1088
 * 
 * In this problem, a tree is an undirected graph that is connected and has no cycles.
 *
 * The given input is a graph that started as a tree with N nodes (with distinct values 1, 2, ..., N), with one
 * additional edge added. The added edge has two different vertices chosen from 1 to N, and was not an edge that already
 * existed.
 *
 * The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] with u < v, that
 * represents an undirected edge connecting nodes u and v.
 *
 * Return an edge that can be removed so that the resulting graph is a tree of N nodes. If there are multiple answers,
 * return the answer that occurs last in the given 2D-array. The answer edge [u, v] should be in the same format, with u
 * < v.
 *
 * Constraints: 
 *   The size of the input 2D-array will be between 3 and 1000. 
 *   Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.
 *
 * Example 1:
 * Input: [[1,2], [1,3], [2,3]] 
 * Output: [2,3] 
 * Explanation:
  looks like:
	  1
	 / \
	2 - 3
 * 
 * Example 2:
 * Input:  [[1,2], [2,3], [3,4], [1,4], [1,5]]
 * Output:  [1,4]	
 * Explanation:
	looks like:
	5 - 1 - 2
	    |   |
	    4 - 3
 * 
 * Thoughts:
 *   The redundant connection is the one make a cycle
 *   m1) union find. pay attention to the path compression
 * 
 *   m2) 
 * 
 */
public class RedundantConnection {
    /**
     * 
     * @param edges: List[List[int]]
     * @return the redundant edge
     */
    public int[] findRedundantConnection(int[][] edges) {
        Map<Integer, Integer> parents = new HashMap<>();

        Integer p0;
        Integer p1;
        for(int[] edge : edges){
            p0 = getParent(parents, edge[0]);
            p1 = getParent(parents, edge[1]);

            if( p0.equals(p1)){
                return edge;
            }else { // p1 != p0
                parents.put(p1, p0);
            }
        }

        //not found 
        return new int[2];
    }

    private Integer getParent(Map<Integer, Integer> parents, int curr){
        if(!parents.containsKey(curr)){
            parents.put(curr, curr);
            return curr;
        }

        Integer parent; 

        while( curr != ( parent = parents.get(curr) ) ){
            parents.put(curr, parents.get(parent));
            curr = parents.get(curr);
        }

        return parent;
    }
    
    public int[] findRedundantConnection_n(int[][] edges) {
        int n = edges.length;

        int[] parents = new int[n + 1];
        for(int i = 1; i <= n; i++){
            parents[i] = i;
        }

        int p0;
        int p1;
        for(int[] edge : edges){
            p0 = getParent(parents, edge[0]);
            p1 = getParent(parents, edge[1]);

            if( p1 == p0){
                return edge;
            }else { // p1 != p0
                parents[p1] = p0;
            }
        }

        //not found 
        return new int[2];
    }

    private int getParent(int[] parents, int curr){

        int p;
        while( curr != ( p = parents[curr] ) ){
            parents[curr] = parents[p] ;
            curr = parents[p];
        }

        return curr;
    }
}
