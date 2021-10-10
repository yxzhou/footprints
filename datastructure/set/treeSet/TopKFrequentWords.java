/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.set.treeSet;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * Given a list of words and an integer k, return the top k frequent words in the list.
 * 
 * You should order the words by the frequency of them in the return list, the most frequent one comes first. If two words has the same frequency, the one with lower alphabetical order come first.
 * 
 * Example 1:
 * Input:
 *  [
 *     "yes", "lint", "code", "yes", "code", "baby", "you", "baby", "chrome",
 *     "safari", "lint", "code", "body", "lint", "code"
 *   ]
 *   k = 3
 * Output: ["code", "lint", "baby"]
 * 
 * Example 2:
 * Input:
 *   [
 *     "yes", "lint", "code", "yes", "code", "baby", "you", "baby", "chrome",
 *     "safari", "lint", "code", "body", "lint", "code"
 *   ]
 *   k = 4
 * Output: ["code", "lint", "baby", "yes"]
 * 
 * Challenge: Do it in O(nlogk) time and O(n) extra space.
 * 
 */
public class TopKFrequentWords {
    /**
     * @param words: an array of string
     * @param k: An integer
     * @return: an array of string
     */
    public String[] topKFrequentWords(String[] words, int k) {
        if(words == null || k < 1){
            return new String[0];
        }

        Map<String, Integer> counts = new HashMap<>();
        for(String word : words){
            counts.put(word, counts.getOrDefault(word, 0) + 1);
        }

        PriorityQueue<String> minHeap = new PriorityQueue<>((s1, s2) -> {
            int diff = counts.get(s1) - counts.get(s2);
            return diff == 0? s2.compareTo(s1) : diff;
        });

        counts.keySet().forEach(key -> {
            if(minHeap.size() < k){
                minHeap.add(key);
            }else if(counts.get(minHeap.peek()) <= counts.get(key)){
                minHeap.add(key);
                minHeap.poll();
            }
        });

        String[] result = new String[minHeap.size()];
        //minHeap.toArray(result);
        int i = minHeap.size() - 1;
        while(!minHeap.isEmpty()){
            result[i--] = minHeap.poll();
        }

        return result;
    }
}
