package fgafa.array.wordDistance;

/**
 * 
 * Given a list of words and two words word1 and word2, 
 * return the shortest distance between these two words in the list.

    For example,
    Assume that words =["practice", "makes", "perfect", "coding", "makes"].
    
    Given word1=“coding”, word2=“practice”, return 3.
    Given word1="makes", word2="coding", return 1.
    
    Note:
    You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
 *
 *
 *  cases:
 *    #1, there are couples of word1 and word2
 */

public class ShortestWordDistance {

    //You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
    public int shortestDistance(String[] words, String word1, String word2) {
        if(null == words || 0 == words.length){
            return -1;
        }
        
        int min = Integer.MAX_VALUE;
        int i1 = -1;
        int i2 = -1;
        
        for(int i = 0; i < words.length; i++){
            if(word1.equals(words[i])){
                i1 = i;
                
                if(i2 != -1){
                    min = Math.min(min, i - i2);
                }
            }else if(word1.equals(words[i])){
                i2 = i;
                
                if(i1 != -1){
                    min = Math.min(min, i - i1);
                }
            }
        }
        
        return min == Integer.MAX_VALUE ? -1 : min;
    }
}
