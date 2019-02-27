package fgafa.graph.minimumSpanningTree;


import org.junit.Test;

import java.util.Arrays;

/**
 *
 * How does Kruskal algorithm work?
 *  1) sort all the edges in non-descreasing order of their weight
 *  2) pick the smallest edge. check if it forms a cycle with the spanning tree formed so far.
 *  If cycle is not formed, include this edge. else, discard it.
 *  3) repeat #2 until there are (V-1) edges in the spanning tree.
 *
 */

public class KruskalMST {

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

    public Edge[] kruskal(Edge[] graph, int vertexNumber){

        Edge[] result = new Edge[vertexNumber - 1];

        Arrays.sort(graph, (e1, e2) -> Integer.compare(e1.weight, e2.weight));

        int[] parents = new int[vertexNumber]; // used for union find
        int[] ranks = new int[vertexNumber];
        for(int i = 0; i < parents.length; i++){
            parents[i] = i;
            ranks[i] = 0;
        }

        for(int i = 0,  j = 0; j < result.length; ){
            Edge candidate = graph[i++];

            int x = find(parents, candidate.from);
            int y = find(parents, candidate.to);

            if(x != y){ //not form the cycle
                result[j++] = candidate;
                union(parents, ranks, x, y);
            }//else discard this candidate

        }

        return result;

    }

    private void union(int[] parents, int[] ranks, int i, int j){
        int iroot = find(parents, i);
        int jroot = find(parents, j);

        //attach smaller rank tree in the higher rank tree
        if(ranks[iroot] < ranks[jroot]){
            parents[iroot] = jroot;
            ranks[jroot] += ranks[iroot];
        }else{
            parents[jroot] = iroot;
            ranks[iroot] += ranks[jroot];
        }

    }

    private int find(int[] parents, int i){
        //find root and make root as parent of i (path compression)
        if (parents[i] != i){
            parents[i] = find(parents, parents[i]);
        }

        return parents[i];
    }



    @Test public void test(){
        /* Let us create following weighted graph
                 10
            0--------1
            |  \     |
           6|   5\   |15
            |      \ |
            2--------3
                4
         */

        int V = 4;  // Number of vertices in graph
        int E = 5;  // Number of edges in graph

        Edge[] graph = new Edge[]{
                new Edge(0, 1, 10),
                new Edge(0, 2, 6),
                new Edge(0, 3, 5),
                new Edge(1, 3, 15),
                new Edge(2, 3, 4)
        };

        for(Edge e : kruskal(graph, V)){
            System.out.println(e.from + " -- " + e.to + " " + e.weight);
        }

    }

}
