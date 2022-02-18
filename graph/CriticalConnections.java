/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1271
 *
 * There are n servers numbered from 0 to n-1 connected by undirected server-to-server connections forming a network
 * where connections[i] = [a, b] represents a connection between servers a and b. Any server can reach any other server
 * directly or indirectly through the network.
 *
 * A critical connection is a connection that, if removed, will make some server unable to reach some other server.
 *
 * Return all critical connections in the network in any order, but you should guarantee that for each connections, when
 * you return, index1 is less than index 2. For instance, if the answer is [[1,2],[3,4]] you can return [[3,4],[1,2]],
 * but [[2,1],[3,4]] is invaild.
 * 
 * Notes:
 *   1 <= n <= 10^5
 *   n-1 <= connections.length <= 10^5
 *   connections[i][0] != connections[i][1]
 *   There are no repeated connections.
 * 
 * 
 * Example 1
 * Input: 4  [[0,1],[1,2],[2,0],[1,3]]
 * Output: [[1,3]]
 * Explanation: 
 *   1 -- 2  
 *   | \  |
 *   3    0 
 * 
 * Example 2
 * Input: 10  [[1,2],[0,1],[0,2],[0,3],[4,5],[3,4],[5,6],[6,7],[7,8],[8,9],[9,5]]
 * Output: [[0,3],[3,4],[4,5]]
 * Explanation: 
 *    1 -- 0 -- 3 -- 4 -- 5 -- 6 - 7
 *     \  /               |       /
 *      2                 9 --  8  
 * 
 * 
 * Thoughts:
 *   The critical connections are the edges that are not in a cycle.
 *   How to find out all the cycle and edges?
 * 
 *            3    5
 *             \  /
 *         8 -- 4 -- 6 
 *        /     |   / \ 
 *       9      | /    7
 *        \ --  0 --  /
 *            /  \
 *           1    2   
 * 
 */
public class CriticalConnections {
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (List<Integer> edge : connections) {
            graph.computeIfAbsent(edge.get(0), x -> new ArrayList<>()).add(edge.get(1));
            graph.computeIfAbsent(edge.get(1), x -> new ArrayList<>()).add(edge.get(0));
        }

        int[] ranks = new int[n];
        List<List<Integer>> result = new LinkedList<>();

        for (int v = 0; v < n; v++) {
            if (ranks[v] == 0) {
                ranks[v] = 1;
                setRank(graph, -1, v, ranks, result);
            }
        }

        return result;
    }
    
    private void setRank(Map<Integer, List<Integer>> graph, int pre, int curr, int[] ranks, List<List<Integer>> result){

        int rank = ranks[curr] + 1;
        for (int next : graph.get(curr)) {
            if (ranks[next] == 0) {

                ranks[next] = rank;

                setRank(graph, curr, next, ranks, result);

                if (rank == ranks[next]) {
                    result.add(Arrays.asList(Math.min(curr, next), Math.max(curr, next)));
                } else {
                    ranks[curr] = Math.min(ranks[curr], ranks[next]);
                }

            } else if (next != pre) { // to avoid pre -> curr -> pre, 
                ranks[curr] = Math.min(ranks[curr], ranks[next]);
            }
        }
    }
    
    public static void main(String[] args){
        int[][][][] inputs = {
            //{ 
            //  {{n}}, connections, expection of the criticalConnections
            //}
            {
                {{4}},
                {{0, 1}, {1, 2}, {2, 0}, {1, 3}},
                {{1, 3}}
            },
            {
                {{10}},
                {{1, 2}, {0, 1}, {0, 2}, {0, 3}, {4, 5}, {3, 4}, {5, 6}, {6, 7}, {7, 8}, {8, 9}, {9, 5}},
                {{0, 3}, {3, 4}, {4, 5}}
            },
            {
                {{10}},
                {{1, 0}, {2, 0}, {0, 9}, {0, 7}, {0, 4}, {0, 6}, {4, 5}, {3, 4}, {4, 6}, {4, 8}, {6, 7}, {8, 9}},
                {{0, 1}, {0, 2}, {3, 4}, {4, 5}}
            }
        };
        
        CriticalConnections sv = new CriticalConnections();
        
        List<List<Integer>> result;
        for(int[][][] input : inputs){
            System.out.println(String.format("\n n=%d, %s", input[0][0][0], Misc.array2String(input[1], true).toString() ));
            
            result = sv.criticalConnections(input[0][0][0], Misc.convert(input[1]));
            Collections.sort(result, (a, b) -> a.get(0) == b.get(0)? a.get(1) - b.get(1) : a.get(0) - b.get(0)  );
            
            System.out.println(String.format("\n %s \n %s", Misc.array2String(input[2], true).toString(), Misc.array2String(result, true).toString() ));
            
            
            Assert.assertEquals(Misc.array2String(input[2], true).toString(), Misc.array2String(result, true).toString() );
        }
    }

    
}
