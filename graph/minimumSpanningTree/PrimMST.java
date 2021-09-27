package graph.minimumSpanningTree;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 *  What is Minimum Spanning Tree?
 *  Given a connected and undirected graph, a spanning tree of the graph is a subgraph that is a tree and connects all the vertex.
 *  A MST is the spanning tree that the sum of weights is minimum.
 *
 *  How many edges does a MST has?
 *  define V as the vertex number in the graph, a MST has V - 1 edges.
 *
 *  What are the applications of MST?
 *   - Network design.  such as road, TV cable, electrical tower
 *   - Approximately solve the traveling salesman problem
 *
 *  How does Prim's Algorithm work?
 *  Prim is greedy. It start fro a "magic" vertice. and expand on the shortest edge.
 *
 *  1) start from a vertice u, add it in MST
 *  2) compare all neighbors of vertex in MST, find the vertice v that is not in the MST and the edge (u, v) is shortest.
 *  3) keep 1 and 2, until MST has all vertex
 *
 *  LazyPrim vs EagerPrim vs Kruskal ?
 *  define V = vertexNumber,  E = edgeNumber
 *              Time
 *  LazyPrim    O(V^2)
 *  EagerPrim   O(E*logV)
 *  Kruskal     O(E*logE)
 *
 */

public class PrimMST {
    /**
     *  define V = vertexNumber,  E = edgeNumber
     *  Time O(V ^ 2)
     *  Similar with DjkstraSP, If the input graph is represented using adjacency list, the time complexity can be O(E*logV) with binary heap. see eagerProm()
     * @param graph, a graph represented using adjacency matrix representation, graph[i][j] means the distance from v_i to v_j.
     *               graph[i][j] == Integer.MAX_VALUE, means there is no edge from v_i to v_j. graph[i][i] == 0.
     */
    public int[] lazyPrim(int[][] graph, int start){

        int vertexNumber = graph.length;
        int[] parents = new int[vertexNumber];
        boolean[] visited = new boolean[vertexNumber];

        int[] distances = new int[vertexNumber];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        //total it will be vertexNumber - 1 edge
        for(int counter = 1; counter < vertexNumber; counter++){
            //find the vertice that has a shortest edge
            int min = Integer.MAX_VALUE;
            int min_v = 0;
            for(int v = 0; v < distances.length; v++){
                if(distances[v] < min){
                    min = distances[v];
                    min_v = v;
                }
            }

            visited[min_v] = true;

            //add all neighbor of min_v as candidate
            for(int neighbor = 0; neighbor < vertexNumber; neighbor++){
                if( !visited[neighbor] && distances[neighbor] > graph[min_v][neighbor]){
                    distances[neighbor] = graph[min_v][neighbor];
                    parents[neighbor] = min_v;
                }
            }
        }


        return parents;
    }

    public int[] eagerPrim(List<List<Integer>> adj, int start){
        int vertexNumber = adj.size();

        int[] parents = new int[vertexNumber];
        Set<Integer> visited = new HashSet<>(vertexNumber);

        //todo

        return parents;
    }

    class Node{
        int vertice;
        int distance;

        Node(int end, int distance){
            this.vertice = end;
            this.distance = distance;
        }
    }

    @Test
    public void test (String[] args)
    {
        /* Let us create the following graph
            2      3
        (0) -- (1) -- (2)
         |    /    \   |
        6|  8/      \5 |7
         |  /   9    \ |
        (3)-----------(4)

         * the minimal spanning tree would be:
             2      3
         (0) -- (1) -- (2)
          |       \
         6|        \5
          |         \
         (3)        (4)
         *
         */

        int graph[][] = new int[][] {
                {0, 2, 0, 6, 0},
                {2, 0, 3, 8, 5},
                {0, 3, 0, 0, 7},
                {6, 8, 0, 0, 9},
                {0, 5, 7, 9, 0}};

        // Print the solution
        Assert.assertArrayEquals(new int[]{0, 0, 1, 0, 1},lazyPrim(graph, 0));

    }
}
