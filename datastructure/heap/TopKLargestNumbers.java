/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.heap;

import java.util.PriorityQueue;

/**
 * Given an integer array, find the top k largest numbers in it.
 * 
 * Example1
 * Input: [3, 10, 1000, -99, 4, 100] and k = 3
 * Output: [1000, 100, 10]
 * 
 */
public class TopKLargestNumbers {
    /**
     * @param nums: an integer array
     * @param k: An integer
     * @return: the top k largest numbers in array
     */
    public int[] topk(int[] nums, int k) {
        if(nums == null || k < 1){
            return new int[0];
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int x : nums){
            if(minHeap.size() < k){
                minHeap.add(x);
            }else if(minHeap.peek() < x){
                minHeap.poll();
                minHeap.add(x);
            }
        }

        int[] result = new int[minHeap.size()]; // not k, for case: nums={1,2}, k=3
        for(int i = minHeap.size() - 1; i >= 0; i--){
            result[i] = minHeap.poll();
        }
        return result;
    }
}
