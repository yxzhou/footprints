package fgafa.dailyCoding.graph;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Given an undirected graph represented as an adjacency matrix and an integer k,
 * write a function to determine whether each vertex in the graph can be colored such that no two adjacent vertices share the same color using at most k colors.
 *
 * Tags: google
 */

public class Colorable {

    class Vertex<V>{
        V value;
        int color = 0; //0 means no color

        List<Vertex> adjacencies = new ArrayList<Vertex>();
    }

    /**
     * traversal/dfs by Relationship
     *
     * @param graph
     * @param k, colorId will be [1, k]
     * @return
     */

    //todo verify / test
    public boolean colorable_1(Vertex[] graph, int k){
        if(null == graph || k < 0 || (k == 0 && graph.length > 0)){
            return false;
        }

        if(k >= graph.length){
            return true;
        }

        for(Vertex vertex : graph){
            if(vertex.color != 0 && !dfs(graph, k, vertex, 1)){
                return false;
            }
        }

        return true;
    }

    private boolean dfs(Vertex[] graph, int k, Vertex vertex, int color){
        if(vertex.color == color){
            return true;
        }

        if(vertex.color != 0 ){
            return false;
        }

        //when vertex.color == 0, color it
        vertex.color = color;

        for(Vertex v : (List<Vertex>)vertex.adjacencies){
            boolean result = false;
            for(int c = 1; c <= k; c++){
                if(c != color && dfs(graph, k, v, c)){
                    result = true;
                    break;
                }
            }

            if(!result){
                return false;
            }
        }

        vertex.color = 0;

        return true;
    }

    /**
     * traversal/dfs by Relationship
     * @param graph
     * @param k
     * @return
     */
    public boolean colorable_11(boolean[][] graph, int k){
        if(null == graph || k < 0 || (k == 0 && graph.length > 0)){
            return false;
        }

        int length = graph.length;

        if(k >= length){
            return true;
        }

        int[] colors = new int[length];
        for(int vertexId = 0; vertexId < length; vertexId++){
            if(colors[vertexId] != 0 && !dfs_11(graph, k, colors, vertexId)){
                return false;
            }
        }

        return true;
    }

    private boolean dfs_11(boolean[][] graph, int k, int[] colors, int vertexId){

        if(colors[vertexId] != 0 ){
            return ok(graph, colors, vertexId, colors[vertexId]);
        }

        boolean[] colorUsed = fetchNeigborColors(graph, colors, vertexId);
        for(int color = 1; color <= k; color++) {
            if (!colorUsed[color]) {
                colors[vertexId] = color;

                boolean result = true;
                for(int to = 0; to < graph.length; to++){
                    if(graph[vertexId][to] && !dfs_11(graph, k, colors, to)){
                        result = false;
                        break;
                    }
                }

                if(result){
                    return true;
                }

                colors[vertexId] = 0;
            }
        }

        return false;
    }

    /**
     * traversal / dfs by vertex order
     * similar with the 8 queens problem.
     * and this method is ok to find all soltuions, see ColorSolutions
     *
     * @param graph
     * @param k
     * @return
     */
    public boolean colorable_2(boolean[][] graph, int k){
        if(null == graph || k < 0 || (k == 0 && graph.length > 0)){
            return false;
        }

        if(k >= graph.length){
            return true;
        }

        return dfs_2(graph, k, new int[graph.length], 0);
    }

    private boolean dfs_2(boolean[][] graph, int k, int[] colors, int vertexId){
        if(vertexId >= graph.length){
            return true;
        }

        for(int color = 1; color <= k; color++){
            if(ok(graph, colors, vertexId, color)){
                colors[vertexId] = color;

                if(dfs_2(graph, k, colors, vertexId + 1)){
                    return true;
                }

                colors[vertexId] = 0;
            }
        }

        return false;
    }

    private boolean ok(boolean[][] graph, int[] colors, int vertexid, int color){
        for(int to = 0; to < graph.length; to++){
            if(graph[vertexid][to] && colors[to] == color){
                return false;
            }
        }

        return true;
    }

    private boolean[] fetchNeigborColors(boolean[][] graph, int[] colors, int vertexid){
        boolean[] result = new boolean[graph.length + 1];

        for(int to = 0; to < graph.length; to++){
            if(graph[vertexid][to] ){
                result[colors[to]] = true;
            }
        }

        return result;
    }


}
