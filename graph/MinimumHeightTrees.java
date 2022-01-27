package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * https://www.lintcode.com/problem/1298
 * 
 * For a undirected graph with tree characteristics, we can choose any node as the root. The result graph is then a
 * rooted tree. Among all possible rooted trees, those with minimum height are called minimum height trees (MHTs). Given
 * such a graph, write a function to find all the MHTs and return a list of their root labels.
 *
 * Format 
 * The graph contains n nodes which are labeled from 0 to n - 1. You will be given the number n and a list of
 * undirected edges (each edge is a pair of labels).
 *
 * You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as
 * [1, 0] and thus will not appear together in edges.
  
    Example 1:
    Given n = 4, edges = [[1, 0], [1, 2], [1, 3]]
    
            0
            |
            1
           / \
          2   3
    return [1]
    
    Example 2:
    Given n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]
    
         0  1  2
          \ | /
            3
            |
            4
            |
            5
    return [3, 4]
    

 * Note:
 * (1) According to the definition of tree on Wikipedia: “a tree is an undirected graph in which any two vertices are
 * connected by exactly one path. In other words, any connected graph without simple cycles is a tree.”
 *
 * (2) The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
 *
 */

public class MinimumHeightTrees {

    /**
     * check every node as root, get and compare the minHeight
     * O(n^2)
     */
    public List<Integer> findMinHeightTrees_0(int n, int[][] edges) {
        List<Integer> result  = new ArrayList<>();
        
        if(null == edges || n < 1 || n - 1 != edges.length){
            return result;
        }
        
        //assume there is no loop in edges
        Map<Integer, List<Integer>> relationships = new HashMap<>();
        for(int[] edge : edges){
            if(!relationships.containsKey(edge[0])){
                relationships.put(edge[0], new ArrayList<>());
            }
            relationships.get(edge[0]).add(edge[1]);
            
            if(!relationships.containsKey(edge[1])){
                relationships.put(edge[1], new ArrayList<>());
            }
            relationships.get(edge[1]).add(edge[0]);
        }
        
        int min = Integer.MAX_VALUE;
        int curr;
        
        for(Integer root : relationships.keySet()){
            
            curr = getMinHeight(relationships, root);
            if(curr < min){
                //result.clear();
                result = new ArrayList<>();
                result.add(root);
            }else if(curr == min){
                result.add(root);
            }
        }
        
        return result;
    }
    
    //bfs,
    private int getMinHeight(Map<Integer, List<Integer>> relationships, Integer root){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(root);
        int height = 0;
        
        Integer curr;
        Set<Integer> isVisited = new HashSet<>();
        isVisited.add(root);
        while(!queue.isEmpty()){
            height++;
            
            for(int i= queue.size(); i> 0; i--){
                curr = queue.poll();
                
                for(Integer child : relationships.get(curr)){
                    if(!isVisited.contains(child)){
                        queue.add(child);
                        isVisited.add(child);
                    }
                }
            }
        }
        
        return height;
    }
    
    /**
     * from leaf to root, topological sort 
     * 
     * Time complexity O(n)
     */
    public List<Integer> findMinHeightTrees_2(int n, int[][] edges) {
        List<Integer> result  = new ArrayList<>();
        
        //check
        if(null == edges || n < 1 || n - 1 != edges.length){
            return result;
        }
        if(1 == n){
            result.add(0);
            return result;
        }
        
        //assume there is no loop in edges
        Map<Integer, List<Integer>> relationships = new HashMap<>();
        int[] inDegrees = new int[n];
        for(int[] edge : edges){
            if(!relationships.containsKey(edge[0])){
                relationships.put(edge[0], new ArrayList<>());
            }
            relationships.get(edge[0]).add(edge[1]);
            
            if(!relationships.containsKey(edge[1])){
                relationships.put(edge[1], new ArrayList<>());
            }
            relationships.get(edge[1]).add(edge[0]);
            
            inDegrees[edge[0]]++;
            inDegrees[edge[1]]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < inDegrees.length; i++){
            if(1 == inDegrees[i]){
                queue.add(i);
            }
        }
        
        int p;
        while(n > 2){
            for(int i = queue.size(); i > 0; i--){
                p = queue.poll();
                n--;
                
                for(Integer child : relationships.get(p)){
                    if(2 == inDegrees[child]){
                        queue.add(child);
                    }else{
                        inDegrees[child]--;                       
                    }
                }
            }
        }
        
        while(!queue.isEmpty()){
            result.add(queue.poll());
        }
        
        return result;
    }   
    
    /**
     * topological sort
     * 
     * This takes too much space than findMinHeightTrees_n.
     * because it's a tree in undirected graph. define n as the number of vertex. the number of edges is (n - 1)
     * 
     * Here it store edges with boolean[][] graph, it takes space O(n * n). compare with List<Integer>[], it's O(n).
     * Of course, List object takes more space than boolean. 
     * Meanwhile traversal the neighbors of one vertex with List<> is more efficient than it with boolean[][] graph.  
     * 
     *
     * @param n: n nodes labeled from 0 to n - 1
     * @param edges: a undirected graph
     * @return: a list of all the MHTs root labels
     */
    public List<Integer> findMinHeightTrees_3(int n, int[][] edges) {

        boolean[][] graph = new boolean[n][n];
        int[] degrees = new int[n];

        for(int[] edge : edges){
            graph[edge[0]][edge[1]] = true;
            graph[edge[1]][edge[0]] = true;

            degrees[edge[0]]++;
            degrees[edge[1]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int v = 0; v < n; v++){
            if(degrees[v] == 1){
                queue.add(v);
            }
        }

        boolean[] visited = new boolean[n]; //default all are false
        int x = n;
        int curr;
        while(x > 2){
            x -= queue.size();
            for(int i = queue.size(); i > 0; i--){
                curr = queue.poll();
                visited[curr] = true;

                for(int next = 0; next < n; next++ ){
                    if(graph[curr][next] && !visited[next]){
                        degrees[next]--;

                        if(degrees[next] == 1){
                            queue.add(next);
                        }
                    }
                }
            
            }
        }

        List<Integer> result = new ArrayList<>();
        for(int v = 0; v < n; v++){
            if(!visited[v]){
                result.add(v);
            }
        }

        return result;
    }
    
    /**
     * topological sort
     * 
     * @param n: n nodes labeled from 0 to n - 1
     * @param edges: a undirected graph
     * @return  a list of all the MHTs root labels
     */
    public List<Integer> findMinHeightTrees_n(int n, int[][] edges) {
        if(n == 1){
            return Arrays.asList(0);
        }

        List<Integer>[] graph = new ArrayList[n];
        for(int v = 0; v < n; v++ ){
            graph[v] = new ArrayList<>();
        }

        int[] degrees = new int[n];

        for(int[] edge : edges){
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);

            degrees[edge[0]]++;
            degrees[edge[1]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int v = 0; v < n; v++){
            if(degrees[v] == 1){
                queue.add(v);
            }
        }

        int x = n;
        int curr;
        while(x > 2){
            x -= queue.size();
            for(int i = queue.size(); i > 0; i--){
                curr = queue.poll();

                for(int v : graph[curr] ){
                    if(degrees[v] > 1){
                        degrees[v]--;

                        if(degrees[v] == 1){
                            queue.add(v);
                        }
                    }
                }
            
            }
        }

        List<Integer> result = new ArrayList<>();
        while(!queue.isEmpty()){
            result.add(queue.poll());
        }
        return result;
    }
    
}
