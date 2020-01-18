package fgafa.sorting;

import java.util.Arrays;
import java.util.PriorityQueue;

import fgafa.datastructure.heap.HeapTest;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * This problem was asked by Palantir.
 *
 * You are given a list of N numbers, in which each number is located at most k places away from its sorted position.
 * For example, if k = 1, a given element at index 4 might end up at indices 3, 4, or 5.
 *
 * Come up with an algorithm that sorts this list in O(N log k) time.
 *
 */

public class SortingII {

    public int[] sort(int[] nums, int k){
        if(null == nums || nums.length < 2 || k == 0){
            return nums;
        }

        int size = nums.length;
        if(k >= size){
            return sort(nums, size - 1);
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for(int i = 0; i <= k; i++){
            minHeap.add(nums[i]);
        }

        int j = 0;
        int[] result = new int[size];
        for(int i = k + 1; i < size; i++){
            result[j++] = minHeap.poll();

            minHeap.add(nums[i]);
        }

        while(!minHeap.isEmpty()){
            result[j++] = minHeap.poll();
        }

        return result;
    }

    @Test
    public void test(){

        //Assert.assertArrayEquals(, sort(null, 0));

    }
}
