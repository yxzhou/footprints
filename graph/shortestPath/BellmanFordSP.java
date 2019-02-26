package fgafa.graph.shortestPath;

import java.util.Arrays;

/**
 *  Bellman-Ford, Single source shortest path algorithm. Similar with DijkstraSP, difference is supporting negative weights.
 *  It can used to find the negative cycle.
 *
 */

public class BellmanFordSP {

    /**
     *  time O(vertexNumber * edgesNumber)
     * @param edges, it's all edges of the graph,
     */
    public int[] bellmanFord(int vertexNumber, Edge[] edges, int start){
        //init distances from start to all other vertex as Integer.MAX_VALUE
        int[] distances = new int[vertexNumber];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        //relax all edges (vertexNumber - 1) times, A simple shortest path from start to any other vertex can have at-most (vertexNumber - 1) edges
        for(int i = vertexNumber; i > 1; i--){
            for(Edge edge : edges){
                if(distances[edge.from] != Integer.MAX_VALUE && distances[edge.from] + edge.weight < distances[edge.to]){
                    distances[edge.to] = distances[edge.from] + edge.weight;
                }
            }
        }

        /* check for negative-weight cycles. The above guarantees shortest distances if graph doesn't contain negative weight cycle.
         * if we get a shorter path, then there is a cycle
         */
        for(Edge edge : edges){
            if(distances[edge.from] != Integer.MAX_VALUE && distances[edge.from] + edge.weight < distances[edge.to]){
                throw new IllegalArgumentException("Graph contains negative weight cycle");
            }
        }

        return distances;
    }

    class Edge{
        int from;
        int to;
        int weight;

        Edge(int from, int to, int weight){
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
}
