/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.unionFind;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1087
 * 
 * In this problem, a rooted tree is a directed graph such that, there is exactly one node (the root) for which all
 * other nodes are descendants of this node, plus every node has exactly one parent, except for the root node which has
 * no parents.
 *
 * The given input is a directed graph that started as a rooted tree with N nodes (with distinct values 1, 2, ..., N),
 * with one additional directed edge added. The added edge has two different vertices chosen from 1 to N, and was not an
 * edge that already existed.
 *
 * The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] that represents a
 * directed edge connecting nodes u and v, where u is a parent of child v.
 *
 * Return an edge that can be removed so that the resulting graph is a rooted tree of N nodes. If there are multiple
 * answers, return the answer that occurs last in the given 2D-array.
 *
 * Notes:
 *   The size of the input 2D-array will be between 3 and 1000. 
 *   Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.

Example 1:
Input: [[1,2], [1,3], [2,3]]
Output: [2,3]
Explanation: The given directed graph will be like this:
  1
 / \
v   v
2-->3
* 
Example 2:
Input: [[1,2], [2,3], [3,4], [4,1], [1,5]]
Output: [4,1]
Explanation: The given directed graph will be like this:
5 <- 1 -> 2
     ^    |
     |    v
     4 <- 3
 * 
 * Thoughts:
 *   case 1: only found one child has two parents, return the last found, see example 1
 *   case 2: only found circle, return the last found, see example 2
 *   case 3: found one child has two parents and circle, return the edge to the child and it's in the circle, 
 * 
        5 -> 1 -> 2
             ^    |
             |    v
             4 <- 3
 *
 *   to edges = {{5, 1}, {2, 3}, {3, 4}, {4, 1}, {1, 2}},  found{{1, 2}, {4, 1}}
 *   to edges = {{2, 3}, {3, 4}, {4, 1}, {1, 2}, {5, 1}},  found{{1, 2}, {5, 1}}
 *   to edges = {{1, 2}, {2, 3}, {3, 4}, {4, 1}, {5, 1}},  found{{4, 1}, {5, 1}}
 *   to edges = {{2, 3}, {3, 4}, {4, 1}, {5, 1}, {1, 2}},  found{{1, 2}, {5, 1}}
 *   to edges = {{5, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 1}},  found{{4, 1}}
 * 
 * 
 * 
 */
public class RedundantConnectionII {
    /**
     * @param edges: List[List[int]]
     * @return: return List[int]
     */
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length;

        int[] parents = new int[n + 1];

        int[] candidate1 = new int[0]; //when found a circle 
        int[] candidate2 = new int[0]; //when found one child has two parent

        Map<Integer, Integer> map = new HashMap<>();

        int up;
        int vp;
        for (int[] edge : edges) {
            map.put(edge[0], edge[1]);

            up = parent(parents, edge[0]);
            vp = parent(parents, edge[1]);

            if (up == vp || vp != edge[1]) {
                if (vp != edge[1]) { //one child has two parent
                    candidate2 = edge;
                } else {
                    candidate1 = edge;
                }
            } else {
                //union
                parents[vp] = up;
            }
        }

        if (candidate2.length != 0 && candidate1.length != 0) { //found circle and one child has two parent

            int from = candidate1[0];
            do {
                if (candidate2[1] == map.get(from) && candidate2[0] != from) {
                    return new int[]{from, map.get(from)};
                }

                from = map.get(from);
            } while (candidate1[0] != from);
        }

        return candidate2.length != 0 ? candidate2 : candidate1;
    }

    private int parent(int[] parents, int curr) {
        if (parents[curr] == 0) {
            parents[curr] = curr;

            return curr;
        }

        while (curr != parents[curr]) {
            parents[curr] = parents[parents[curr]];

            curr = parents[curr];
        }

        return curr;
    }
    
    public static void main(String[] args){
        int[][][][] inputs = {
            {
                {{1,2},{1,3},{2,3}},
                {{2,3}}
            },
            {
                {{1,2}, {2,3}, {3,4}, {4,1}, {1,5}},
                {{4,1}}
            },
            {
                {{5, 1}, {2, 3}, {3, 4}, {4, 1}, {1, 2}},
                {{4,1}}
            },
            {
                {{2, 3}, {3, 4}, {4, 1}, {1, 2}, {5, 1}},
                {{4,1}}
            },
            {
                {{1, 2}, {2, 3}, {3, 4}, {4, 1}, {5, 1}},
                {{4,1}}
            },
            {
                {{2, 3}, {3, 4}, {4, 1}, {5, 1}, {1, 2}},
                {{4,1}}
            },
            {
                {{5, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 1}},
                {{4,1}}
            }
        };
        
        RedundantConnectionII sv = new RedundantConnectionII();
        
        for(int[][][] input : inputs ){
            System.out.println(Misc.array2String(input[0], true));
            
            Assert.assertArrayEquals(input[1][0], sv.findRedundantDirectedConnection(input[0]));
        }
        
    }
}
