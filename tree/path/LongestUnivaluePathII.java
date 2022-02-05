/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tree.path;

import java.util.ArrayList;
import java.util.List;

/**
 * _https://www.lintcode.com/problem/717
 *
 * We consider an undirected tree with N nodes, numbered from 1 to N, Additionally, each node also has a label attached
 * to it and the label is an integer value. Note that different nodes can have identical labels. You need to write a
 * function , that , given a zero-indexed array A of length N, where A[J] is the label value of the (J + 1)-th node in
 * the tree, and a zero-indexed array E of length K = (N - 1) * 2 in which the edges of the tree are described (for
 * every 0 <= j <= N -2 values E[2 * J] and E[2 * J + 1] represents and edge connecting node E[2 * J] with node E[2 * J
 * + 1])， returns the length of the longest path such that all the nodes on that path have the same label. Then length
 * of a path if defined as the number of edges in that path.
 *
 * Assume that: 1 <= N <= 1000
 *
 * Example1
 * Input: A = [1, 1, 1 ,2, 2] and E = [1, 2, 1, 3, 2, 4, 2, 5]
 * Output: 2
 * Explanation: 
 * described tree appears as follows:

                   1 （value = 1）
                 /   \
    (value = 1) 2     3 (value = 1)
               /  \
 (value = 2)  4     5 (value = 2)

 * The longest path (in which all nodes have the save value of 1) is (2 -> 1 -> 3). 
 * 
 * Example2
 * Input: A = [1, 2, 1 ,2, 2] and E = [1, 2, 1, 3, 2, 4, 2, 5]
 * Output: 2
 * Explanation: 
 * described tree appears as follows:

                   1 （value = 1）
                 /   \
    (value = 2) 2     3 (value = 1)
               /  \
 (value = 2)  4     5 (value = 2)

 * The longest path (in which all nodes have the save value of 2) is (4 -> 2 -> 5). 
 * 
 */
public class LongestUnivaluePathII {
    
    /**
     * @param A: as indicated in the description
     * @param E: as indicated in the description
     * @return: Return the number of edges on the longest path with same value.
     */
    int result = 0; // the number of vertex on the longest path with same value

    public int LongestPathWithSameValue(int[] A, int[] E) {
        if(A == null || E == null){
            return 0;
        }

        int n = A.length;
        List<Integer>[] edges = new ArrayList[n];
        for(int v = 0; v < n; v++ ){
            edges[v] = new ArrayList<>(2);
        }

        for(int p = 1, a, b; p < E.length; p+=2){
            a = E[p] - 1;
            b = E[p - 1] - 1;
            edges[a].add(b);
            edges[b].add(a);
        }

        boolean[] visited = new boolean[n];

        for(int v = 0; v < n; v++ ){
            if(!visited[v]){
                dfs(A, edges, v, visited);
            }
        }

        return result - 1; // the number of edges instead of the number of vertex
    }

    private int dfs(int[] A, List<Integer>[] edges, int v, boolean[] visited){

        visited[v] = true;
        
        List<Integer> nexts = edges[v];

        int sum = 0;
        int max = 0;
        int child;
        int len;
        for(int i = 0; i < nexts.size(); i++ ){
            child = nexts.get(i);
            if(!visited[child] && A[child] == A[v] ){
                len = dfs(A, edges, child, visited);

                sum += len;
                max = Math.max(max, len);
            }
        }

        result = Math.max(result, sum + 1);
        return max + 1;
    }
    
}
