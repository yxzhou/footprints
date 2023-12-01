/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;

/**
 *
 * Given a directed graph, represented in a two dimension array, output a list of points that can be used to traverse
 * every points with the least number of visited vertices.
 *
 * Example 1:
 * Input edges: 
 *    --> 4
    /
   5 --> 2 --> 1 <--> 0
                \
                  --> 3
 * Output: [5] 
 * Explanation: From node 5 all other nodes are reachable.
 * 
 * Example 2:
 * Input edges: 
 * 5 --> 4
    
   2 --> 1 --> 0
         \
           --> 3
 * Output: [5, 2] 
 * 
 * Thoughts:
 * The problem is to find out the "start vertex" for every connected components. 
 * case1, 1 --> 2 --> 3, the start vertex is 1
 * case2, 1 <-- 2 --> 3, the start vertex is 2
 * case3, 2 <--> 3, the start vertex is 2 or 3
 * case4, 1 --> 2 --> 3 --> 4, the start vertex is 3 or 2 or 1
 *         \         /
 *          <-------- 
 * case5, 1 --> 2 <-- 3, the start vertex are 1 and 3
 * 
 * s1: 
 *   step1, find the vertex whose indegree is 0, then DFS
 *          loop
 *   step2, find the vertex whose outdegree is max, then DFS
 *          loop
 * 
 */
public class MinimumVerticestoTraverseDirectedGraph {
   
    public List<Integer> getMin(int[][] edges, int n) {
        
        int[][] matrix = new int[n][n]; //default all are 0
        for(int[] pair : edges){
            matrix[pair[0]][pair[1]] = 1;
        }
            
        
        List<Integer> result = new ArrayList<>();
        
        return result;
    }
    
    
    
    public static void main(String[] args){
        
        int[][][][] inputs = {
            //{ edges, {{n}}, {expect}}
            {
                {{0, 0}, {1, 2}, {2, 0}, {2, 3}, {3, 1}}, //1->2->3->1, 2->0->0
                {{4}},
                {{1}}
            },
            {
                {{0, 1}, {1, 0}, {2, 1}, {2, 3}, {3, 2}},  //0->1->0, 2->3->2->1
                {{4}},
                {{2}}
            },
            {
                {{0, 1}, {1, 0}, {2, 1}, {3, 1}, {3, 2}},  //3->2->1->0  0->1 3->1
                {{4}},
                {{3}}
            },
            {
                {{0, 1}, {1, 0}, {2, 1}, {3, 1}, {3, 2}},  //5->4  5->2->1<->0  1->3
                {{6}},
                {{5}}
            },
            {
                {{0, 1}, {1, 0}, {2, 1}, {3, 1}, {3, 2}},  //5->4  2->1->0  1->3
                {{6}},
                {{5, 2}}
            },
        };
        
        MinimumVerticestoTraverseDirectedGraph sv = new MinimumVerticestoTraverseDirectedGraph();
        
        for(int[][][] input : inputs){
            
            //Assert.assertArrayEquals(Misc.array2String(input[0]),   expecteds, actuals);
            List<Integer> a = Arrays.stream(input[2][0]).boxed().collect(Collectors.toList());
            
            List<Integer> b = sv.getMin(input[0], input[1][0][0]);
            
            //Assert.assertTrue(a.size() == b.size() && a.containsAll(b) && b.containsAll(a));
            Assert.assertTrue(CollectionUtils.isEqualCollection(a, b) );
        }
        
    }
}
