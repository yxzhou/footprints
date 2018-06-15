package fgafa.array.slideWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * This is a follow up of Shortest Word Distance. The only difference is now 
 * you are given the list of words and your method will be called repeatedly
 *  many times with different parameters. How would you optimize it?

    Design a class which receives a list of words in the constructor, and 
    implements a method that takes two words word1 and word2 and return the 
    shortest distance between these two words in the list.
    
    For example,
    Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
    
    Given word1 = "coding”, word2 = "practice”, return 3.
    Given word1 = "makes", word2 = "coding", return 1.
    
    Note: You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
 *
 */

public class ShortestWordDistanceII {
    String[] words;
    Map<String, List<Integer>> positions = new HashMap<>();
    
    public ShortestWordDistanceII(String[] words){
        //check
        if(null == words || 2 > words.length){
            return;
        }
        
        this.words = words;
        
        init();
    }

    private void init(){
        for(int i = 0; i < words.length; i++){
            if(!positions.containsKey(words[i])){
                positions.put(words[i], new ArrayList<Integer>());
            }
            
            positions.get(words[i]).add(i);
        }
    }

    public int shortestDistance(String word1,
                                String word2) {
        if (null == words || null == word1 || null == word2
                    || !positions.containsKey(word1)
                    || !positions.containsKey(word2)) {
            return -1;
        }

        int min = Integer.MAX_VALUE;

        List<Integer> l1 = positions.get(word1);
        List<Integer> l2 = positions.get(word2);
        int diff = 0;
        for (int i = 0, j = 0; i < l1.size() && j < l2.size();) {
            diff = l1.get(i) - l2.get(j);
            if (diff > 0) {
                min = Math.min(min, diff);
                j++;
            } else {
                min = Math.min(min, -diff);
                i++;
            }
        }

        return min;
    }
}
