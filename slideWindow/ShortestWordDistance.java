package fgafa.slideWindow;

import org.junit.Assert;
import org.junit.Test;

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
            }else if(word2.equals(words[i])){
                i2 = i;
                
                if(i1 != -1){
                    min = Math.min(min, i - i1);
                }
            }
        }
        
        return min == Integer.MAX_VALUE ? -1 : min;
    }

    /**
     * Find an efficient algorithm to find the smallest distance (measured in number of words) between any two given words in a string.
     *
     * For example, given words "hello", and "world" and a text content of "dog cat hello cat dog dog hello cat world",
     * return 1 because there's only one word "cat" in between the two words.
     *
     *
     */
    public int smallestDistance(String word1, String word2, String words){
        if(words == null || word1 == null || word2 == null){
            return -1;
        }

        String[] wordsArray = words.split(" ");

        int poistions0 = -1;
        int poistions1 = -1;

        int distances = Integer.MAX_VALUE;

        for(int i = 0; i < wordsArray.length; i++){
            String word = wordsArray[i];
            if(word.equals(word1)){
                poistions0 = i;
            }else if(word.equals(word2)){
                poistions1 = i;
            }

            if(poistions0 != -1 && poistions1 != -1){
                distances = Math.min(distances, Math.abs(poistions1 - poistions0));
            }
        }

        return distances == Integer.MAX_VALUE ? distances : distances - 1;
    }

    @Test public void test(){

        Assert.assertEquals(1, smallestDistance("hello", "world", "dog cat hello cat dog dog hello cat world"));

    }

}
