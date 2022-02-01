/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graph.topological;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * _https://www.lintcode.com/problem/1015
 *
 * In a directed graph, we start at some node and follow the directed edge of the graph each time. If we reach a point
 * of zero degree, we stop. We call these points of zero degree "terminal nodes".
 *
 * An "ultimately safe node" is defined as one that will reach an "end node" in a finite number of steps, regardless of
 * the path taken, if starting from this node.
 *
 * Your program should return an ordered array representing the numbers of the "final safe nodes" you found. The node
 * subscripts of the graph start at 0. Graphli represents all neighbor node subscripts of node I in the graph.
 * 
 * Constraints: 
 * 1、The number of nodes in the figure should not exceed 10,000. 
 * 2、The number of edges in the figure will not exceed 32,000. 
 * 3、Each graphi is an ordered list of different integers in the range [0, graph.length-1].
 *
 * Example 1: 
 * Input: [[1,2],[2,3],[5],[0],[5],[],[]] 
 * Output: [2,4,5,6] 
 * 
 * Example 2:
 * Input: [[4,9],[3,5,7],[0,3,4,5,6,8],[7,8,9],[5,6,7,8],[6,7,8,9],[7,9],[8,9],[9],[]] 
 * Output: [0,1,2,3,4,5,6,7,8,9]
 * 
 */
public class FindEventualSafeStates {
    /**
     * topological sort on outDegree
     *
     * @param graph: a 2D integers array
     * @return return a list of integers
     */
    public List<Integer> eventualSafeNodes(int[][] graph) {

        int n = graph.length;

        int[] outDegrees = new int[n];

        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int i = 0; i < n; i++){
            map.put(i, new LinkedList<>());
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < n; i++ ){
            outDegrees[i] = graph[i].length;

            if(outDegrees[i] == 0){
                queue.add(i);
            }else{
                for(int out : graph[i]){
                    map.get(out).add(i);
                }
            }
        }

        List<Integer> result = new LinkedList<>();

        int top;
        while(!queue.isEmpty()){
            top = queue.poll();
            result.add(top);

            for(int in : map.get(top)){
                outDegrees[in]--;

                if(outDegrees[in] == 0){
                    queue.add(in);
                }
            }
        }

        Collections.sort(result);
        return result;
    }
}
