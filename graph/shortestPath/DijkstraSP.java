package graph.shortestPath;


import util.Misc;
import org.junit.Test;

import java.util.*;

/**
 *  DijkstraSP, Single source shortest path algorithm. not work for negative weights.
 *
 */
public class DijkstraSP {

    /**
     *  define V = vertexNumber,  E = edgeNumber
     *  Time O(V ^ 2)
     *
     * @param graph, a graph represented using adjacency matrix representation, graph[i][j] means the distance from v_i to v_j.
     *               graph[i][j] == Integer.MAX_VALUE, means there is no edge from v_i to v_j. graph[i][i] == 0.
     */
    public int[] dijkstra(int[][] graph, int start){
        int vertexNumber = graph.length;

        int[] distances = new int[vertexNumber];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        boolean[] visited = new boolean[vertexNumber];

        for(int counter = vertexNumber; counter > 1; counter--){
            int min = Integer.MAX_VALUE;
            int min_v = 0;
            for( int v = 0; v < vertexNumber; v++){
                if( !visited[v] && distances[v] < min){
                    min = distances[v];
                    min_v = v;
                }
            }

            visited[min_v] = true;

            for (int neighbor = 0; neighbor < vertexNumber; neighbor++) {
                if (!visited[neighbor]
                        && graph[min_v][neighbor] != Integer.MAX_VALUE
                        && distances[neighbor] > distances[min_v] + graph[min_v][neighbor]) {
                    distances[neighbor] = distances[min_v] + graph[min_v][neighbor];
                }
            }

        }

        return distances;
    }


    /**
     * with PriorityQueue
     *
     * define V = vertexNumber,  E = edgeNumber
     * Time O(E * logV),  so this is specially for sparse graph (E << V^2)
     *
     * @param adj, a graph represented using adjacency representation, adj.get(i) is a List<Node> means the neighbors of v_i.
     *
     *
     */
    public int[] dijkstra_n(List<List<Node>> adj, int start){
        int vertexNumber = adj.size();

        int[] distances = new int[vertexNumber];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        minHeap.add(new Node(start, 0));

        Set<Integer> visited = new HashSet<>();

        while( visited.size() < vertexNumber){
            Node curr = minHeap.poll();

            visited.add(curr.vertice);

            for(Node neighbor : adj.get(curr.vertice)){
                if(visited.contains(neighbor.vertice)){
                    continue;
                }

                if(distances[neighbor.vertice] > distances[curr.vertice] + neighbor.distance){ //here the neighbor.distance is the distance from neighbor to curr.
                    distances[neighbor.vertice] = distances[curr.vertice] + neighbor.distance;
                    minHeap.add(new Node(neighbor.vertice, distances[neighbor.vertice])); //

                    //we don't remove/replace the old Node(neighbor, ---),
                }
            }
        }

        return distances;
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
    public void test(){
        int vertexNumber = 9;

        int X = Integer.MAX_VALUE;
        int[][] graph = new int[][]{
                {0,  4, X,  X,  X,  X,  X,  8,  X},
                {4,  0, 8,  X,  X,  X,  X, 11,  X},
                {X,  8, 0,  7,  X,  4,  X,  X,  2},
                {X,  X, 7,  0,  9, 14,  X,  X,  X},
                {X,  X, X,  9,  0, 10,  X,  X,  X},
                {X,  X, 4, 14, 10,  0,  2,  X,  X},
                {X,  X, X,  X,  X,  2,  0,  1,  6},
                {8, 11, X,  X,  X,  X,  1,  0,  7},
                {X,  X, 2,  X,  X,  X,  6,  7,  0}
        };

        int start = 0;

        Misc.printArray_Int(dijkstra(graph, start));

        List<List<Node>> adj = new ArrayList<>(vertexNumber);
        for(int from = 0; from < vertexNumber; from++){
            List neighbors = new ArrayList<>();
            adj.add(neighbors);

            for(int to = 0; to < vertexNumber; to++){
                if(graph[from][to] != 0 && graph[from][to] != X){
                    neighbors.add(new Node(to, graph[from][to]));
                }
            }
        }

        Misc.printArray_Int(dijkstra_n(adj, start));
    }
}
