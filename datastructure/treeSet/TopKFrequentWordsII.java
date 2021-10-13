/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.treeSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;
import util.Misc;

/**
 *
 * Find top k frequent words in real time data stream.
 * Implement three methods for Topk Class:
 *   TopK(k). The constructor.
 *   add(word). Add a new word.
 *   topk(). Get the current top k frequent words.
 * 
 * If two words have the same frequency, rank them by dictionary order.
 * 
 * Example 1:
 * Input：
 *   TopK(2)
 *   add("lint")
 *   add("code")
 *   add("code")
 *   topk()
 * Output：["code", "lint"]
 * Explanation： "code" appears twice and "lint" appears once, they are the two most frequent words.
 * 
 * Example 2:
 * Input：
 *   TopK(1)
 *   add("aa")
 *   add("ab")
 *   topk()
 * Output：["aa"]
 * Explanation： "aa" and "ab" appear once , but aa's dictionary order is less than ab's.
 * 
 */
public class TopKFrequentWordsII {
    Map<String, Integer> counts;
    int k;
    NavigableSet<String> treeSet;

    /*
    * @param k: An integer
    */
    public TopKFrequentWordsII(int k) {
//        if(k < 1){
//            throw new IllegalArgumentException();
//        }

        this.k = k;
        counts = new HashMap<>();
        treeSet = new TreeSet<>((s1, s2) -> {
            int diff = counts.get(s1) - counts.get(s2);
            return diff == 0? s2.compareTo(s1) : diff;
        });
    }

    /*
     * @param word: A string
     * @return: nothing
     */
    public void add(String word) {

        //flush the sortedSet
        if(counts.containsKey(word)){ // without this checking or checking with treeSet.contains(word), it will cause NPE from the Comparor in treeSet, 
            treeSet.remove(word);
        }
        
        counts.put(word, counts.getOrDefault(word, 0) + 1); //this changes the counts, if this is moved before treeSet.remove, treeSet maybe cannot remove an existing one because the Comparor in treeSet 
                
        treeSet.add(word);

        if(treeSet.size() > k){
            treeSet.pollFirst();
        }
    }

    /*
     * @return: the current top k frequent words.
     */
    public List<String> topk() {
        LinkedList<String> result = new LinkedList();

        treeSet.forEach(s -> result.addFirst(s));

        return result;
    }
    
    public static void main(String[] args){
        TopKFrequentWordsII sv = new TopKFrequentWordsII(4);
        sv.add("yes");
        sv.add("lint");
        sv.add("code");
        sv.add("yes");
        sv.add("code");
        Misc.printArrayList(sv.topk());
        Misc.printArrayList( Arrays.asList(new String[]{"code","yes","lint"}) );
        
        sv.add("baby");
        sv.add("you");        
        sv.add("baby");        
        Misc.printArrayList(sv.topk());
        Misc.printArrayList( Arrays.asList(new String[]{"baby","code","yes","lint"}));

        
        sv.add("chrome");
        sv.add("safari");
        sv.add("lint");
        sv.add("code");
        sv.add("body");
        sv.add("lint");
        sv.add("code");
        Misc.printArrayList(sv.topk());
        Misc.printArrayList( Arrays.asList(new String[]{"code","lint","baby","yes"}));
         
    }
}
