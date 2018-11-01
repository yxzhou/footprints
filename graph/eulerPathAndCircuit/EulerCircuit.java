package fgafa.graph.eulerPathAndCircuit;

/**
 *
 *  ——https://www.geeksforgeeks.org/eulerian-path-and-circuit/
 *
 *  *Eulerian Path is a path in graph that visits every edge exactly once.
 *    same as problem of "Is it possible to draw a given graph without lifting pencil from the paper and without tracing any of the edges more than once"
 *  Eulerian Circuit is an Eulerian Path which starts and ends on the same vertex.
 *
 *  *Hamiltonian Path in an undirected graph is a path that visits each vertex exactly once.
 *  A Hamiltonian cycle (or Hamiltonian circuit) is a Hamiltonian Path such that there is an edge (in graph) from the last vertex to the first vertex of the Hamiltonian Path.
 *
 *  find whether a given graph has a Hamiltonian Path is NP complete problem for a general graph.
 *  find whether a given graph has a Eulerian Path or not, it's polynomial time, O(V+E)
 *
 *  Eulerian Cycle
 *  An undirected graph has Eulerian cycle if following two conditions are true.
 *  ….a) All vertices with non-zero degree are connected. We don’t care about vertices with zero degree because they don’t belong to Eulerian Cycle or Path (we only consider all edges).
 *  ….b) All vertices have even degree.
 *
 *  Eulerian Path
 *  An undirected graph has Eulerian Path if following two conditions are true.
 *  ….a) Same as condition (a) for Eulerian Cycle
 *  ….b) If two vertices have odd degree and all other vertices have even degree. Note that only one vertex with odd degree is not possible in an undirected graph (sum of all degrees is always even in an undirected graph)
 *
 *  A graph is called Eulerian if it has an Eulerian Cycle and called Semi-Eulerian if it has an Eulerian Path.
 *  Note that a graph with no edges is considered Eulerian because there are no edges to traverse.
 *
 *  An directed graph has Eulerian Path if
 *    a) the "related" undirected graph has Eulerian Path
 *    b) ?? find the start point and find the Eulerian Path
 *
 *  How does this work?
 *  In Eulerian path, each time we visit a vertex v, we walk through two unvisited edges with one end point as v. ( in a DG, it's inDgree and outDegree ) Therefore, all middle vertices in Eulerian Path must have even degree.
 *  For Eulerian Cycle, any vertex can be middle vertex, therefore all vertices must have even degree.
 *
 */

public class EulerCircuit {

    /**
     *
     * @param edges, a list of undirected edges, such as edges[i][j] means the edge between vertex 0 and vertex 1, edges[i][j] == 0, means not connected
     * @param verticesNumber, such as n, it means there are vertices [0, 1, --, n- 1]
     * @return 2, semi-eulerian; 1, eulerian; 0, neither eulerian or semi-eulerian
     */
    public int isEulerian_UDG(int[][] edges, int verticesNumber){

        int edgesNumber = 0;
        int oddCount = 0;

        for(int i = 0; i < verticesNumber; i++){
            int count = 0;
            for(int j = 0; j < verticesNumber / 2; j++){
                if(edges[i][j] > 0){
                    edgesNumber++;
                    count++;
                }
            }

            if(1 == (count & 1)){
                oddCount++;
            }
        }

        if(edgesNumber == 0){
            return 2; // when no edge in the graph, all vertices are isolate
        }

        //if odd count is 0, then eulerian possible
        //if odd count is 2, then semi-eulerian possible
        if(oddCount != 2 && oddCount != 0){
            return 0;
        }

        if(isConnectted_UDG(edges, verticesNumber)){
            return oddCount == 2 ? 1 : 2;
        }

        return 0;
    }

    private boolean isConnectted_UDG(int[][] edges, int verticesNumber){
        boolean[] visited = new boolean[verticesNumber];

        traverse_dfs(edges,0, visited);

        for(int i = 0; i < visited.length; i++){
            if(!visited[i]){
                return false;
            }
        }

        return true;
    }

    private void traverse_dfs(int[][] edges, int vertexId, boolean[] visited){
        if(visited[vertexId]){
            return;
        }

        visited[vertexId] = true;

        for(int j = 0; j < visited.length; j++){
            if(edges[vertexId][j] > 0){
                traverse_dfs(edges, j, visited);
            }
        }
    }

}
