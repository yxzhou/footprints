package fgafa.graph.shortestPath;


import fgafa.util.Misc;
import org.junit.Test;

import java.util.*;

/**
 *  Dijkstra, Single source shortest path algorithm. nonnegative weights.
 *
 */
public class Dijkstra {

    /**
     *  time O(vertexNumber * vertexNumber)
     * @param graph, a graph represented using adjacency matrix representation, graph[i][j] means the weight on edge from v_i to v_j.
     *               graph[i][j] == 0, means there is no edge from v_i to v_j.
     */
    public int[] dijkstra(int vertexNumber, int[][] graph, int start){
        int[] distances = new int[vertexNumber];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        boolean[] visited = new boolean[vertexNumber];

        for(int i = vertexNumber; i > 1; i--){
            int min = Integer.MAX_VALUE;
            int min_v = 0;
            for( int v = 0; v < vertexNumber; v++){
                if( !visited[v] && distances[v] < min){
                    min = distances[v];
                    min_v = v;
                }
            }

            visited[min_v] = true;

            for(int neighbor = 0; neighbor < vertexNumber; neighbor++){
                if(visited[neighbor] || graph[min_v][neighbor] == 0){
                    continue;
                }

                if(distances[neighbor] > distances[min_v] + graph[min_v][neighbor]){
                    distances[neighbor] = distances[min_v] + graph[min_v][neighbor];
                }
            }

        }

        return distances;
    }


    /**
     * with PriorityQueue
     *
     * time O(edgeNumber * log(vertexNumber))
     *
     * @param adj, a graph represented using adjacency representation, adj.get(i) is a List<Node> means the neighbors of v_i.
     *
     *
     */
    public int[] dijkstra_n(int vertexNumber, List<List<Node>> adj, int start){
        int[] distances = new int[vertexNumber];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        PriorityQueue<Node> minHeap = new PriorityQueue<>((n1, n2) -> Integer.compare(n1.distance, n2.distance));
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

        int[][] graph = new int[][]{
                {0,  4, 0,  0,  0,  0,  0,  8,  0},
                {4,  0, 8,  0,  0,  0,  0, 11,  0},
                {0,  8, 0,  7,  0,  4,  0,  0,  2},
                {0,  0, 7,  0,  9, 14,  0,  0,  0},
                {0,  0, 0,  9,  0, 10,  0,  0,  0},
                {0,  0, 4, 14, 10,  0,  2,  0,  0},
                {0,  0, 0,  0,  0,  2,  0,  1,  6},
                {8, 11, 0,  0,  0,  0,  1,  0,  7},
                {0,  0, 2,  0,  0,  0,  6,  7,  0}
        };

        int start = 0;

        Misc.printArray_Int(dijkstra(vertexNumber, graph, start));

        List<List<Node>> adj = new ArrayList<>(vertexNumber);
        for(int from = 0; from < vertexNumber; from++){
            List neighbors = new ArrayList<>();
            adj.add(neighbors);

            for(int to = 0; to < vertexNumber; to++){
                if(graph[from][to] != 0){
                    neighbors.add(new Node(to, graph[from][to]));
                }
            }
        }

        Misc.printArray_Int(dijkstra_n(vertexNumber, adj, start));
    }
}
