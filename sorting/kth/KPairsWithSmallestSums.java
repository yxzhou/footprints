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

    /**
     * @param nums1: List[int]
     * @param nums2: List[int]
     * @param k: an integer
     * @return: return List[List[int]]
     */
    int[][] diffs = {{0, 1}, {1, 0}};
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        if (nums1 == null || nums2 == null || k < 1) {
            return Collections.EMPTY_LIST;
        }

        int n = nums1.length;
        int m = nums2.length;

        k = Math.min(k, m * n);

        boolean[][] visited = new boolean[n][m]; //to avoid add heap duplicated

        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(nums1[a[0]] + nums2[a[1]], nums1[b[0]] + nums2[b[1]]));
        minHeap.add(new int[]{0, 0});

        List<List<Integer>> result = new ArrayList<>(k);

        int[] top;
        int ni;
        int nj;
        for (int i = 0; i < k; i++) {
            top = minHeap.poll();
            result.add(Arrays.asList(nums1[top[0]], nums2[top[1]]));

            for (int[] diff : diffs) {
                ni = top[0] + diff[0];
                nj = top[1] + diff[1];

                if (ni < n && nj < m && !visited[ni][nj]) {
                    minHeap.add(new int[]{ni, nj});
                    visited[ni][nj] = true;
                }
            }
        }

        return result;
    }
}
