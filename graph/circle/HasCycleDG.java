package fgafa.graph.circle;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class HasCycleDG {

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


}
