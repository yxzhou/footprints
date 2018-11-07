package fgafa.dailyCoding.graph;

/**
 *
 * Given an undirected graph represented as an adjacency matrix,
 * write a function to determine whether each vertex in the graph can be colored such that no two adjacent vertices share the same color using at most k colors.
 *
 */

public class ColorSolutions {
    int count = 0;

    public long colorSolutions(boolean[][] graph, int k){

        if(null == graph || k < 1){
            return 0;
        }

        colorSolutions(graph, k, new int[graph.length], 0);

        return count;
    }

    private void colorSolutions(boolean[][] graph, int k, int[] colors, int from){

        if(from >= graph.length){
            count++;
        }

        for(int color = 1; color < k; color++){
            if(ok(graph, colors, from, color)){
                colors[from] = color;

                colorSolutions(graph, k, colors, from + 1);

                colors[from] = 0;
            }
        }
    }

    private boolean ok(boolean[][] graph, int[] colors, int vertexId, int color){

        for(int to = 0; to < graph.length; to++){
            if(graph[vertexId][to] && colors[to] == color){
                return false;
            }
        }

        return true;
    }
}
