package fgafa.dailyCoding.graph;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import java.util.Map;


/**
 *
 * In a directed graph, each node is assigned an uppercase letter. We define a path's value as the number of most frequently-occurring letter along that path. For example, if a path in the graph goes through "ABACA", the value of the path is 3, since there are 3 occurrences of 'A' on the path.

 Given a graph with n nodes and m directed edges, return the largest value path of the graph. If the largest value is infinite, then return null.

 The graph is represented with a string and an edge list. The i-th character represents the uppercase letter of the i-th node. Each tuple in the edge list (i, j) means there is a directed edge from the i-th node to the j-th node. Self-edges are possible, as well as multi-edges.

 For example,
 1) the following input graph: ABACA
 [(0, 1),
 (0, 2),
 (2, 3),
 (3, 4)]

 Would have maximum value 3 using the path of vertices [0, 2, 3, 4], (A, A, C, A).

 2) The following input graph: A
 [(0, 0)]

 Should return null, since we have an infinite loop.
 *
 */

public class LargestPaths {

    public Integer largestPath(String vertex, int[][] edges){
        if(null == vertex || vertex.isEmpty() || null == edges || 0 == edges.length ){
            return 0;
        }

        int size = vertex.length();
        Map<Integer, List<Integer>> neighbors = new HashMap<>(size);

        for(int[] edge : edges){
            if(!neighbors.containsKey(edge[0])){
                neighbors.put(edge[0], new ArrayList<>());
            }

            neighbors.get(edge[0]).add(edge[1]);
        }

        int[] depth = new int[size];
        int[] status = new int[size]; // 0, default value, before checking; 1, in checking; 2, checked.

        int largest = 0;
        for(int i = 0; i < size; i++){
            if(status[i] == 0){
                dfs(neighbors, depth, status, i);
            }

            if(depth[i] == -1){
                return null;
            }

            largest = Math.max(largest, depth[i]);
        }

        return largest - 1;
    }

    private void dfs(Map<Integer, List<Integer>> neighbors, int[] depth, int[] status, int verticeId){
        if(status[verticeId] == 2){
            return;
        }

        if(status[verticeId] == 1){
            depth[verticeId] = -1;
            return;
        }

        status[verticeId] = 1;

        int largest = 1;
        if(neighbors.containsKey(verticeId)){
            for(int neighbor : neighbors.get(verticeId)){
                dfs(neighbors, depth, status, neighbor);

                if(depth[neighbor] == -1){
                    largest = -1;
                    break;
                }

                largest = Math.max(largest, depth[neighbor] + 1);
            }
        }
        depth[verticeId] = largest;

        status[verticeId] = 2;
    }

}
