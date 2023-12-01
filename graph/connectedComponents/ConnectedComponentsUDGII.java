package graph.connectedComponents;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), 
 * write a function to find the number of connected components in an undirected graph.

    Example 1:
         0          3
         |          |
         1 --- 2    4
    Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2.

    Example 2:
         0           4
         |           |
         1 --- 2 --- 3
    Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]], return 1.
 
    Note:
    You can assume that no duplicate edges will appear in edges. 
    Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.
 *
 *
 * Solutions:
 *   1 DFS
 *   2 BFS
 *   2 Union Find
 */

public class ConnectedComponentsUDGII {

    private int[] father;
    
    //quick union
    public int countComponents(int n, int[][] edges) {
     
        father = new int[n];
        for (int i = 0; i < n; i++) {
            father[i] = i;
        }
        for (int[] edge : edges) {
            union(edge[0], edge[1]);
        }
     
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++){ 
            set.add(find(i));
        }
        return set.size();
    }
     
    private int find(int node) {
        while (father[node] != node) {
            node = father[node];
        }
        
        return node;
    }
     
    private void union(int node1, int node2) {
        int root1 = find(node1);
        int root2 = find(node2);
        
        if(root1 == root2){
            return;
        }
        
        father[root2] = root1;
    }
    
}
