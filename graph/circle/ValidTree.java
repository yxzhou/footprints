package graph.circle;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Leetcode #261
 *
 * Given n nodes labeled from 0 to n-1 and a list of undirected edges (each edge is a pair of nodes), write a function to check whether these edges make up a valid tree.
 *
 * Example 1:
 * Input: n = 5, and edges = [[0,1], [0,2], [0,3], [1,4]]
 * Output: true
 * 
 * Example 2
 * Input: n = 5, and edges = [[0,1], [1,2], [2,3], [1,3], [1,4]]
 * Output: false
 * 
 * Note: you can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0,1] is the same as [1,0] and thus will not appear together in edges.
 *
 * Solution:
 *   if it's a valid tree,  
 *      rule 1. vertex == edge + 1
 *      rule 2. no circle, meanwhile it's not a forest. it's a connected component. 
 *   To check the rule 2, 
 *      method 1, dfs or bfs, travesal all the vertex
 *      method 2, unionFind 
 * 
 */

public class ValidTree {

    @Test
    public void test() {

        Assert.assertTrue(validTree_dfs(5, new int[][]{{0, 1}, {0, 2}, {0, 3}, {1, 4}}));

        Assert.assertFalse(validTree_dfs(5, new int[][]{{0, 1}, {1, 2}, {2, 3}, {1, 3}, {1, 4}}));
    }

    public boolean validTree_dfs(int n, int[][] edges) {

        //rule 1,  edge + 1 == vertex
        if( n != edges.length + 1 ){
            return false;
        }

        //rule 2, no circle
        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int[] edge : edges){
            map.putIfAbsent(edge[0], new LinkedList<>());
            map.get(edge[0]).add(edge[1]);

            map.putIfAbsent(edge[1], new LinkedList<>());
            map.get(edge[1]).add(edge[0]);
        }

        boolean[] states = new boolean[n]; //false, default; true, checked;

        for(int i = 0; i < n; i++){
            if(!states[i] && dfs(map, i, states, -1)){
                return false;
            }
        }

        return true;
    }

    //return true when it found cycle
    private boolean dfs(Map<Integer, List<Integer>> map, int vertex, boolean[] states, int parent){
        states[vertex] = true;

        if(map.containsKey(vertex)){
            for(int next : map.get(vertex)){
                if(!states[next]){
                    if(dfs(map, next, states, vertex)){
                        return true;
                    }
                }else{
                    if(next != parent){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean validTree_UnionFind(int n, int[][] edges) {
        if(n - 1 != edges.length){
            return false;
        }

        //check if it's a circle
        int[] parents = new int[n];
        for(int i = 1; i < n; i++){
            parents[i] = i;
        }

        int p, q;
        for(int[] edge : edges){
            p = find(parents, edge[0]);
            q = find(parents, edge[1]);

            if(p == q){
                return false;
            }

            parents[p] = q;
        }

        return true;
    }

    private int find(int[] parents, int p){
        while( p != parents[p]){
            parents[p] = parents[parents[p]];
            p = parents[p];
        }

        return p;
    }

}
