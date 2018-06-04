package fgafa.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import fgafa.util.Misc;

/**
 * 
 * Given a non-empty array of integers, return the k most frequent elements.

    For example,
    Given [1,1,1,2,2,3] and k = 2, return [1,2].
    
    Note: 
    You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
    Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 *
 */

public class TopKFrequent {


    public List<Integer> topKFrequent(int[] nums, int k) {
        List<Integer> ret = new ArrayList<>();
        
        //check
        if(null == nums || 0 == nums.length || k <= 0){
            return ret;
        }
        
        //count with Hashmap
        Map<Integer, Integer> counts = new HashMap<>();
        for(int num : nums){
            if(counts.containsKey(num)){
                counts.put(num, counts.get(num) + 1);
            }else{
                counts.put(num, 1);
            }
        }
        
        //get top k with minHeap
        PriorityQueue<Pair> minHeap=new PriorityQueue<>(k, new Comparator<Pair>(){
            @Override
            public int compare( Pair x, Pair y )
            {
                return x.value - y.value;
            }
        });
        
        for(Map.Entry<Integer, Integer> entry : counts.entrySet()){
            if(minHeap.size() < k){
                minHeap.add(new Pair(entry.getKey(), entry.getValue()));
            }else if(entry.getValue() > minHeap.peek().value){
                minHeap.poll();
                minHeap.add(new Pair(entry.getKey(), entry.getValue()));
            }
        }
        
        //return
        for(Pair pair : minHeap){
            ret.add(pair.key);
        }
        return ret;
    }
    
    public static void main(String[] args) {
        TopKFrequent sv = new TopKFrequent();
        
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

    class Pair{
        int key;
        int value;
        
        Pair(int key, int value){
            this.key = key;
            this.value = value;
        }
    }
}
