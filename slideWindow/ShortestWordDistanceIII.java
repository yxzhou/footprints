package slideWindow;

/**
 * 
 * Given a list of words and two words word1 and word2, 
 * return the shortest distance between these two words in the list.

    word1 and word2 may be the same and they represent two individual words in the list.
    
    For example,
    
    Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
    
    Given word1 = "makes", word2 = "coding", return 1.
    Given word1 = "makes", word2 = "makes", return 3.
    Note: You may assume that word1 and word2 are both in the list.
 *
 */

public class ShortestWordDistanceIII {
    
    //word1 and word2 are both in the list
    //word1 and word2 may be the same and they represent two individual words in the list
    public int shortestDistance(String[] words, String word1, String word2) {

        if(word1.equals(word2)){
            return checkSame(words, word1);
        }else{
            return checkDifference(words, word1, word2);
        }
        
    }
    
    private int checkSame(String[] words, String word){
        int min = Integer.MAX_VALUE;
        int pre = -1; 
        
        for(int i = 0; i < words.length; i++){
            if(word.equals(words[i])){
                if(pre != -1){
                    min = Math.min(min, i - pre);
                }
                
                pre = i;
            }
        }
        
        return min;
    }
    
    public int checkDifference(String[] words, String word1, String word2) {
        int min = words.length;
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
        
        return min;
    }
    
}
