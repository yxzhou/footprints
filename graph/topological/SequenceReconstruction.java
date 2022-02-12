/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graph.topological;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * _https://www.lintcode.com/problem/605
 *
 * Check whether the original sequence org can be uniquely reconstructed from the sequences in seqs. The org sequence is
 * a permutation of the integers from 1 to n, with 1 <= n <= 10^4 . Reconstruction means building a shortest common
 * supersequence of the sequences in seqs (i.e., a shortest sequence so that all sequences in seqs are subsequences of
 * it). Determine whether there is only one sequence that can be reconstructed from seqs and it is the org sequence.
 *
 *
 * Example 1: 
 * Input:org = [1,2,3], seqs = [[1,2],[1,3]] 
 * Output: false 
 * Explanation: [1,2,3] is not the only one sequence that can be reconstructed, because [1,3,2] is also a valid sequence
 * that can be reconstructed. 
 * 
 * Example 2:
 * Input: org = [1,2,3], seqs = [[1,2]] 
 * Output: false 
 * Explanation: The reconstructed sequence can only be [1,2]. 
 * 
 * Example 3:
 * Input: org = [1,2,3], seqs = [[1,2],[1,3],[2,3]] 
 * Output: true 
 * Explanation: The sequences [1,2], [1,3], and [2,3] can uniquely reconstruct the original sequence [1,2,3]. 
 * 
 * Example 4:
 * Input:org = [4,1,5,2,6,3], seqs = [[5,2,6,3],[4,1,5,2]] 
 * Output:true
 * 
 * Thoughts:
 *   Determine whether there is only one sequence that can be reconstructed from seqs and it is the org sequence.
 *   
 * 
 *
 */
public class SequenceReconstruction {
    
    /**
     * 
     * @param org: a permutation of the integers from 1 to n
     * @param seqs: a list of sequences
     * @return: true if it can be reconstructed only one or false
     */
    public boolean sequenceReconstruction(int[] org, int[][] seqs) {
        if(org == null || seqs == null  ){
            return false;
        }

        int n = org.length;

        int[] indegrees = new int[n + 1];
        List<Integer>[] graph = new ArrayList[n + 1];
        int max = 0;
        for(int[] seq : seqs){
            if(seq.length == 1){
                max = Math.max(max, seq[0]);
            }
        
            for(int i = seq.length - 2; i >= 0; i--){
                max = Math.max(max, seq[i]);
                max = Math.max(max, seq[i + 1]);

                if(max > n){
                    return false;
                }

                if(graph[seq[i]] == null){
                    graph[seq[i]] = new ArrayList<>();
                }

                graph[seq[i]].add(seq[i + 1]);
                indegrees[seq[i + 1]]++;
            }
        }

        if( n != max ){
            return false;
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int v = 1; v < indegrees.length; v++){
            if(indegrees[v] == 0){
                queue.add(v);
            }
        }

        int i = 0; // index in the org
        int top;
        while(queue.size() == 1){ //only one sequence that can be reconstructed
            top = queue.poll();

            if(org[i++] != top){ //check if it is the org sequence
                return false;
            }

            if(graph[top] == null){
                break;
            }

            for(int next : graph[top]){
                indegrees[next]--;

                if(indegrees[next] == 0){
                    queue.add(next);
                }
            }
        }

        return i == org.length; //check if it is the org sequence
    }
}
