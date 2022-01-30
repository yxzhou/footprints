package datastructure.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import util.Misc;

/**
 * _https://www.lintcode.com/problem/1281/
 * 
 * Given a non-empty array of integers, return the k most frequent elements.

    For example,
    Given [1,1,1,2,2,3] and k = 2, return [1,2].
    
    Note: 
    You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
    Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 *
 */

public class TopKFrequentNumber {


    public List<Integer> topKFrequent(int[] nums, int k) {
        if(null == nums || 0 == nums.length || k <= 0){
            return Collections.EMPTY_LIST ;
        }
        
        //count with Hashmap
        Map<Integer, Integer> counts = new HashMap<>(); //<element, frequency>
        for(int x : nums){
            counts.put(x, counts.getOrDefault(x, 0) + 1);
        }
        
        //get top k with minHeap
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((i1, i2) -> Integer.compare( counts.get(i1), counts.get(i2)) );
        for(Map.Entry<Integer, Integer> entry : counts.entrySet()){
            if(minHeap.size() < k){
                minHeap.add(entry.getKey());
            }else if(counts.get(minHeap.peek()) < entry.getValue()){
                minHeap.poll();
                minHeap.add(entry.getKey());
            }
        }
        
        //return
        return new ArrayList<>(minHeap);
    }
    
    public static void main(String[] args) {
        TopKFrequentNumber sv = new TopKFrequentNumber();
        
        int[][] input = {
                {1,1,1,2,2,3},
                {1,1,1,2,2,3,3,3},
                {1,1,1,2,2,3,3,3,3}
        };
        
        for(int[] nums : input){
            System.out.println(String.format("Input: %s", Misc.array2String(nums)));
            System.out.println("Output:");
            Misc.printArrayList_Integer(sv.topKFrequent(nums, 2));
        }

    }

}
