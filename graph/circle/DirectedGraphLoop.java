/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graph.circle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * _https://www.lintcode.com/problem/1366
 *
 * 
 * Please judge whether there is a cycle in the directed graph with n vertices and m edges. The parameter is two int
 * arrays. There is a directed edge from start[i] to end[i].
 *
 * Constraints: 
 * 2 <= n <= 10^5 
 * 1 <= m <= 4*10^5 
 * 1 <= start[i], end[i] <= n
 * 
 * 
 * Example1
 * Input: start = [1],end = [2]
 * Output: "False"
 * Explanation:
 * There is only one edge 1->2, and do not form a cycle. 
 * 
 * Example2
 * Input: start = [1,2,3],end = [2,3,1] 
 * Output: "True" 
 * Explanation: There is a cycle 1->2->3->1.
 * 
 * Thoughts:
 *   m1) DFS, with a status, 1 means checking, 2 means checked, 0 means need be checked
 *   m2) BFS, is vertex left after topological sort?
 * 
 */
public class DirectedGraphLoop {
    /**
     * @param start: The start points set
     * @param end: The end points set
     * @return true if the graph is cyclic
     */
    public boolean isCyclicGraph_DFS(int[] start, int[] end) {

        Map<Integer, List<Integer>> graph = new HashMap<>();
        for(int i = 0; i < start.length; i++ ){
            graph.computeIfAbsent(start[i], x -> new LinkedList<>()).add(end[i]);
        }
        
        Map<Integer, Integer> visited = new HashMap<>();

        for(int i = 0; i < start.length; i++ ){
            if(!visited.containsKey(start[i])){
                if( dfs(graph, start[i], visited) ){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(Map<Integer, List<Integer>> graph, int curr, Map<Integer, Integer> visited){
        if(visited.containsKey(curr)){
            return visited.get(curr) == 1; //return true when visited.get(curr) == 1, false when visited.get(curr) == 2
        }
        if(!graph.containsKey(curr)){
            return false;
        }

        visited.put(curr, 1);

        for(int next : graph.get(curr)){
            if(dfs(graph, next, visited)){
                return true;
            }
        }

        visited.put(curr, 2);

        return false;
    }
    
    /**
     * @param start: The start points set
     * @param end: The end points set
     * @return true if the graph is cyclic
     */
    public boolean isCyclicGraph_BFS(int[] start, int[] end) {

        Map<Integer, Integer> degrees = new HashMap<>();
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for(int i = 0; i < start.length; i++ ){
            graph.computeIfAbsent(start[i], x -> new LinkedList<>()).add(end[i]);

            degrees.put(end[i], degrees.getOrDefault(end[i], 0) + 1 );
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int v : graph.keySet()){
            if(!degrees.containsKey(v)){
                queue.add(v);
            }
        }

        int v;
        int d; //degree
        while(!queue.isEmpty()){
            v = queue.poll();

            if(!graph.containsKey(v)){
                continue;
            }

            for(int next : graph.get(v)){
                d = degrees.get(next);
                if(d == 1 ){
                    degrees.remove(next);
                    queue.add(next);
                }else{
                    degrees.put(next, d - 1);
                }
            }
        }

        return !degrees.isEmpty();
    }
}
