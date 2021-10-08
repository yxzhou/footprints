/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array;

/**
 *
 * A zero-indexed array A of length N contains all integers from 0 to N-1. Find and return the longest length of set S, where S[i] = {A[i], A[A[i]], A[A[A[i]]], ... } subjected to the rule below.
 * 
 * Suppose the first element in S starts with the selection of element A[i] of index = i, the next element in S should be A[A[i]], and then A[A[A[i]]]… By that analogy, we stop adding right before a duplicate element occurs in S.
 * 
 * Notes
 * N is an integer within the range [1, 20,000].
 * The elements of A are all distinct.
 * Each element of A is an integer within the range [0, N-1].
 * 
 * Example1
 * Input: [5,4,0,3,1,6,2]
 * Output: 4
 * Explanation: 
 * A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.
 * One of the longest S[K]: S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}
 * 
 * Example2
 * Input: [0,1,2]
 * Output: 1
 * 
 * Solution #1, dfs, find all circle, similar with the islands number problem
 * 
 * 
 */
public class ArrayNesting {
    /**
     * @param nums: an array
     * @return: the longest length of set S
     */
    public int arrayNesting(int[] nums) {
        int n = nums.length;
        boolean[] visited = new boolean[n]; // default all are false
        int max = 0;

        int k;
        int count;
        for(int i = 0; i < n; i++){
            if(!visited[i]){
                count = 0;
                k = i;
                while(!visited[k]){
                    visited[k] = true;
                    count++;

                    k = nums[k];
                }

                max = Math.max(max, count);
            }
        }

        return max;
    } 
}
