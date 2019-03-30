package fgafa.graph.circle;

import org.junit.Test;

import java.util.*;

public class HasCycle {

    /**
     *  int[] inDegrees; ()
     *  Queue<Integert>; (keep the inDegree is 0)
     *
     *  topological sort
     */
    //public boolean hasCycleInDG_BFS(int[][] edges, int n){
    public boolean hasCycleInDG_BFS(Map<Integer, List<Integer>> adj, int n){
        int[] inDegrees = new int[n];
        for(int parent : adj.keySet()){
            for(int child : adj.get(parent)){
                inDegrees[child]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int v = 0; v < n; v++){
            if(inDegrees[v] == 0){
                queue.add(v);
            }
        }

        int count = 0;
        while(!queue.isEmpty()){
            int curr = queue.poll();
            count++;

            for(int next : adj.get(curr)){
                inDegrees[next]--;

                if(inDegrees[next] == 0){
                    queue.add(next);
                }
            }
        }

        return count != n;
    }

    /**
     *  init int[] status; (status[i] == 0, default, before checking; 1, in checking; 2, checked, no cycle found fro v_i)
     *
     *
     */
    //public boolean hasCircleInDG_DFS(int[][] edges, int n){
    public boolean hasCycleInDG_DFS(Map<Integer, List<Integer>> adj, int n){

        int[] status = new int[n]; //status[i] == 0, default, before checking; 1, in checking; 2, checked, no cycle found fro v_i)

        for(int v : adj.keySet()){
            if(status[v] == 0 && dfs(adj, v, status)){
                return true; //found cycle
            }
        }

        return false;
    }

    private boolean dfs(Map<Integer, List<Integer>> adj, int i, int[] status){
        status[i] = 1;

        for (int child : adj.get(i)) {
            if(status[child] == 2){
                continue;
            }else if(status[child] == 1){
                return true;
            }

            if (dfs(adj, child, status)) {
                return true; //found cycle
            }
        }

        status[i] = 2;
        return false;
    }

    /**
     *
     * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
     * write a function to check whether the graph contains a cycle.
     *
     * For example:
     *
     * Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return false.
     * Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return true.
     *
     *
     *
     * @param edges, detail see example
     * @param n
     * @return
     */
    public boolean hasCycleInUDG(int[][] edges, int n){

        Map<Integer, List<Integer>> adj = new HashMap<>();
        for(int[] edge : edges){
            adj.putIfAbsent(edge[0], new ArrayList<>());
            adj.get(edge[0]).add(edge[1]);

            adj.putIfAbsent(edge[1], new ArrayList<>());
            adj.get(edge[1]).add(edge[0]);
        }

        boolean[] visited = new boolean[n]; // status[i] == 0, default value, not visit; 1, visited.

        for(int v = 0; v < n; v++){
            if(!visited[v] && dfs(adj, n, v, visited)){
                return true; // found cycle
            }
        }

        return false;
    }

    private boolean dfs(Map<Integer, List<Integer>> adj, int n, int parent, boolean[] visited){
        visited[parent] = true;

        for(int child : adj.get(parent)){

            if(!visited[child]){
                if(dfs(adj, n, child, visited)){
                    return true;
                }
            }else if(child != parent ){
                return true;
            }
        }

        return false;
    }


    @Test public void testHasCycleInUDG(){

    }
}
