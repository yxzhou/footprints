package fgafa.graph.shortestPath;

/**
 *
 *  Floyed-Warshall, All pairs shortest path algorithm. it works for negative weights, but no negative cycle.
 */

public class FloyedWarshallSP {

    /**
     *  define V = vertexNumber,  E = edgeNumber
     *  Time O(V ^ 3)
     *
     *  if E << V^2, it's a sparse graph. There is a Johnson Algorithm, the time complexity will be O(V*V*logV + VE)
     *
     *  It can use DijkstraSP to find all pair shortest paths by running it for every vertice (as start source).
     *  The time complexity is O(V^3), to Sparse graph, it can be O( V * E * logV)
     *
     * @param graph, a graph represented using adjacency matrix representation, graph[i][j] means the distance from v_i to v_j.
     *               graph[i][j] == Integer.MAX_VALUE, means there is no edge from v_i to v_j. graph[i][i] == 0.
     * @return
     */
    public void floyedWarshall(int[][] graph){
        int vertexNumber = graph.length;

        for(int pivot = 0; pivot < vertexNumber; pivot++){
            for(int from = 0; from < vertexNumber; from++){
                if(graph[from][pivot] == Integer.MAX_VALUE){ //to avoid int over flow
                    continue;
                }

                for(int to = 0; to < vertexNumber; to++){
                    if(graph[pivot][to] == Integer.MAX_VALUE){
                        continue;
                    }

                    if(graph[from][to] > graph[from][pivot] + graph[pivot][to]){
                        graph[from][to] = graph[from][pivot] + graph[pivot][to];
                    }
                }
            }
        }

    }
}
