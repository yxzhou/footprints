package graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * Given an undirected graph, return true if and only if it is bipartite.

 Recall that a graph is bipartite if we can split it's set of nodes into two independent subsets A and B such that every edge in the graph has one node in A and another node in B.

 The graph is given in the following form: graph[i] is a list of indexes j for which the edge between nodes i and j exists.  Each node is an integer between 0 and graph.length - 1.  There are no self edges or parallel edges: graph[i] does not contain i, and it doesn't contain any element twice.

 Example 1:
 Input: [[1,3], [0,2], [1,3], [0,2]]
 Output: true
 Explanation:
 The graph looks like this:
 0----1
 |    |
 |    |
 3----2
 We can divide the vertices into two groups: {0, 2} and {1, 3}.
 Example 2:
 Input: [[1,2,3], [0,2], [0,1,3], [0,2]]
 Output: false
 Explanation:
 The graph looks like this:
 0----1
 | \  |
 |  \ |
 3----2
 We cannot find a way to divide the set of nodes into two independent subsets.


 Note:

 graph will have length in range [1, 100].
 graph[i] will contain integers in range [0, graph.length - 1].
 graph[i] will not contain i or duplicate values.
 The graph is undirected: if any element j is in graph[i], then i will be in graph[j].
 *
 */

public class GraphBipartite {

    public boolean isBipartite_DFS(int[][] graph) {
        if(graph == null){
            return true;
        }
                
        int n = graph.length;
        int[] colors = new int[n]; //init-0, groupA - 1, groupB - 2

        for(int i = 0; i < n; i++){
            if(colors[i] == 0 && !dfs(graph, i, 1, colors)){
                return false;
            }
        }

        return true;
    }

    private boolean dfs(int[][] graph, int i, int color, int[] colors){
        if(colors[i] == color){
            return true;
        } else if(colors[i] != 0){
            return false;
        }

        //colors[i] == 0
        colors[i] = color;
        for(int next : graph[i]){
            if(!dfs(graph, next, color == 1 ? 2 : 1, colors)){
                return false;
            }
        }
        
        return true;
    }
    
    public boolean isBipartite_2(int[][] graph) {
        if(graph == null){
            return true;
        }

        int n = graph.length;
        int[] colors = new int[n + 1]; //default all are 0, 1 - Black, 2 - White

        for(int v = 0; v < n; v++){
            if(colors[v] == 0){
                for(int neighbor : graph[v]){
                    if(colors[neighbor] > 0){
                        colors[v] = 3 - colors[neighbor];
                        break;
                    }
                }

                if(colors[v] == 0){
                    colors[v] = 1;
                }
            }

            // when colors[v] != 0
            for(int neighbor : graph[v]){
                if( colors[v] == colors[neighbor] ){
                    return false;
                }else if(colors[neighbor] == 0 ) {
                    colors[neighbor] = 3 - colors[v];
                }
            }
        }

        return true;
    }

    @Test
    public void test(){
        Assert.assertEquals(true, isBipartite_DFS(new int[][]{{1, 3}, {0, 2}, {1, 3}, {0, 2}}));
        Assert.assertEquals(false, isBipartite_DFS(new int[][]{{1, 2, 3}, {0, 2}, {0, 1, 3}, {0, 2}}));
        
        Map<int[], List<String>> map = new HashMap<>();
    }

}
