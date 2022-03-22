/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sorting.kth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1274
 *
 * You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
 *
 * Define a pair (u,v) which consists of one element from the first array and one element from the second array.
 *
 * Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.
 *
 * Return results need to be orderly.
 * 
 * Example 1:
 * Given nums1 = [1,7,11], nums2 = [2,4,6],  k = 3
 * Return: [1,2],[1,4],[1,6]
 * The first 3 pairs are returned from the sequence:
 *  [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
 * 
 * Example 2:
 * Given nums1 = [1,1,2], nums2 = [1,2,3],  k = 2
 * Return: [1,1],[1,1]
 * The first 2 pairs are returned from the sequence:
 * [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
 * 
 * Example 3:
 * Given nums1 = [1,2], nums2 = [3],  k = 3 
 * Return: [1,3],[2,3]
 * All possible pairs are returned from the sequence: [1,3],[2,3]
 * 
 * Thoughts:
 *   How to find kth element? 
 *   m1) quick select on a sorted array
 *   m2) get one by one, compare with two points,  or heap.  
 * 
 *   How to configure PriorityQueue to ignore duplicates:
 *   A PriorityQueue in Java does not have any restriction with regard to duplicate elements. If you want to ensure that
 *   two identical items are never present in the priority queue at the same time the simplest way would be to maintain 
 *   a separate Set in parallel with the priority queue
 * 
 */
public class KPairsWithSmallestSums {
    
    class Pair{
        int i; // index in nums1
        int j; // index in nums2

        Pair(int i, int j){
            this.i = i;
            this.j = j;
        }
    }

    /**
     * @param nums1: List[int]
     * @param nums2: List[int]
     * @param k: an integer
     * @return: return List[List[int]]
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        //assert nums1 == null || nums2 == null ;
        if(nums1 == null || nums2 == null || k < 1){
            return Collections.EMPTY_LIST;
        }

        int n = nums1.length;
        int m = nums2.length;
        
        k = Math.min(k, n * m);
        List<List<Integer>> result = new ArrayList<>(k);

        PriorityQueue<Pair> minHeap = new PriorityQueue<>(k, (a, b) -> ((long)nums1[a.i] + nums2[a.j] - nums1[b.i] - nums2[b.j]) >= 0? 1 : -1 );
        minHeap.add(new Pair(0, 0));

        boolean[][] visited = new boolean[n][m];

        Pair top;
        for( ; k > 0; k-- ){
            top = minHeap.poll();

            result.add(Arrays.asList(nums1[top.i], nums2[top.j]));

            if(top.j + 1 < m && !visited[top.i][top.j + 1]){
                minHeap.add(new Pair(top.i, top.j + 1));
                visited[top.i][top.j + 1] = true;
            }
            if(top.i + 1 < n && !visited[top.i + 1][top.j]){
                minHeap.add(new Pair(top.i + 1, top.j));
                visited[top.i + 1][top.j] = true;
            }

        }

        return result;
    }

    public static void main(String[] args){
        
        int[][][][] inputs = {
            //{ {nums1, nums2, {k}}, expect }
            {{null, null, {1}}, {}},
            {{{1}, {2}, {0}}, {}},
            {
                {
                    {1,7,11},
                    {2,4,6},
                    {3}
                },
                {{1,2},{1,4},{1,6}}
            },
            {
                {
                    {1,1,2},
                    {1,2,3},
                    {2}
                },
                {{1,1},{1,1}}
            },
            {
                {
                    {1,2},
                    {3},
                    {3}
                },
                {{1,3},{2,3}}
            }
        };
        
        KPairsWithSmallestSums sv = new KPairsWithSmallestSums();
        
        for(int[][][] input : inputs){
            
            System.out.println(String.format("\nnums1: %s\nnums2: %s\nk=%d", Arrays.toString(input[0][0]), Arrays.toString(input[0][1]), input[0][2][0] ));
            
            Assert.assertEquals( Misc.array2String(input[1], true), Misc.array2String(sv.kSmallestPairs(input[0][0], input[0][1], input[0][2][0])).toString() );
            
        }
        
    }

}
