/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.topological;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * There are a total of n courses you have to take, labeled from 0 to n - 1. Some courses may have prerequisites, for
 * example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
 *
 * Given the total number of courses and a list of prerequisite pairs, return the number of different ways you can get
 * all the lessons
 *
 * Example 1
 *   Input: n = 2 prerequisites = [[1,0]] 
 *   Output: 1 
 *   Explantion: You must have class in order 0->1. 
 * 
 * Example 2
 *   Input: n = 2 prerequisites = [] 
 *   Output: 2 
 *   Explanation: You can have class in order 0->1 or 1->0.
 *
 */
public class CourseScheduleIV {
    public int topologicalSortNumber_DFS(int n, int[][] p) {
        if(p == null){
            return 0;
        }
        
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] indegrees = new int[n];
        for(int[] edge : p){
            indegrees[edge[0]]++;
            
            graph.computeIfAbsent(edge[1], x -> new ArrayList<>()).add(edge[0]);
        }
        
        
        return dfs(graph, 0, new boolean[n], indegrees);
    }
    
    private int dfs(Map<Integer, List<Integer>> graph, int level, boolean[] visited, int[] indegrees){
        if(level == indegrees.length){
            return 1;
        }
        
        int result = 0;
        for(int i = 0; i < indegrees.length; i++){
            if(visited[i] || indegrees[i] != 0){
                continue;
            }
            
            visited[i] = true;
            if(graph.containsKey(i)){
                for(int j : graph.get(i)){
                    indegrees[j]--;
                }
            }

            result += dfs(graph, level + 1, visited, indegrees);
            
            visited[i] = false;
            if(graph.containsKey(i)){
                for(int j : graph.get(i)){
                    indegrees[j]++;
                }
            }
            
        }
        
        return result;
    }
    
    /**
     *  TODO
     * 
     * @param n
     * @param p
     * @return 
     */
    public int topologicalSortNumber_DP(int n, int[][] p) {
        if(p == null){
            return 0;
        }
        
        int[] children = new int[n];
        for(int[] edge : p){
            children[edge[1]] |= (1 << edge[0]); // assume n < 32
        }
        
        int m = (1 << n);
        int[] dp = new int[m];
        dp[0] = 1;
        
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if( (i & children[j]) == children[j] &&  (i & (1 << j)) == 0 ) {
                    dp[i | (1 << j)] += dp[i];
                }
            }
        }
        
        return dp[m - 1];
    }
    
}
