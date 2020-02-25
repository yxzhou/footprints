package fgafa.graph.circle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HasCycleUDG {
    /**
     *
     * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
     * write a function to check whether the graph contains a cycle.
     *
     * For example:
     *
     * Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return false.
     * Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return true.
     *
     *
     *
     * @param edges, detail see example
     * @param n
     * @return
     */
    public boolean hasCycleInUDG(int[][] edges, int n){

        Map<Integer, List<Integer>> adj = new HashMap<>();
        for(int[] edge : edges){
            adj.putIfAbsent(edge[0], new ArrayList<>());
            adj.get(edge[0]).add(edge[1]);

            adj.putIfAbsent(edge[1], new ArrayList<>());
            adj.get(edge[1]).add(edge[0]);
        }

        boolean[] visited = new boolean[n]; // status[i] == 0, default value, not visit; 1, visited.

        for(int v = 0; v < n; v++){
            if(!visited[v] && dfs(adj, v, visited)){
                return true; // found cycle
            }
        }

        return false;
    }

    private boolean dfs(Map<Integer, List<Integer>> adj, int parent, boolean[] visited){
        visited[parent] = true;

        for(int child : adj.get(parent)){

            if(!visited[child]){
                if(dfs(adj, child, visited)){
                    return true;
                }
            }else if(child != parent ){
                return true;
            }
        }

        return false;
    }



}
