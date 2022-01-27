package array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * _https://www.lintcode.com/problem/1296
 * 
 * Given a string array words, find the maximum value of length(word[i]) * length(word[j]) where the two words do not
 * share common letters. You may assume that each word will contain only lower case letters. If no such two words exist,
 * return 0.

    Example 1:
    Given ["abcw", "baz", "foo", "bar", "xtfn", "abcdef"]
    Return 16
    The two words can be "abcw", "xtfn".
    
    Example 2:
    Given ["a", "ab", "abc", "d", "cd", "bcd", "abcd"]
    Return 4
    The two words can be "ab", "cd".
    
    Example 3:
    Given ["a", "aa", "aaa", "aaaa"]
    Return 0
    No such pair of words.
 *
 */

public class MaximumProductOfWordsLength {

    
    public int maxProduct(String[] words) {
        if(null == words || words.length < 2 ){
            return 0;
        }
        
        Map<String, Integer> encodes = new HashMap<>();
        for(String word : words){
            if(null == word || 0 == word.length()){
                continue;
            }
            
            int encode = 0;
            for(char c : word.toCharArray()){
                encode = encode | (1 << (c - 'a'));
            }
            encodes.put(word, encode);
        }
        
        int max = 0;
        int curr;
        List<String> list = new ArrayList<>(encodes.keySet());
        String word1;
        String word2;
        for(int i = list.size() - 1; i >= 0; i--){
            word1 = list.get(i);
            for(int j = i- 1; j >= 0; j--){
                word2 = list.get(j);
                
                if((encodes.get(word1) & encodes.get(word2)) == 0){
                    curr = word1.length() * word2.length();
                    max = Math.max(curr, max);
                }
                    
            }
        }
        
        return max;
    }
    
    /**
     * 
     * @param words: a string array
     * @return the maximum product
     */
    public int maxProduct_n(String[] words) {
        if(words == null){
            return 0;
        }

        int n = words.length;

        int[] hashcodes = new int[n];
        for(int i = 0; i < n; i++){
            hashcodes[i] = hashcode(words[i]);
        }

        int max = 0;

        for(int i = 0; i < n; i++){
            for(int j = i + 1; j < n; j++){
                if( (hashcodes[i] & hashcodes[j]) == 0 ){
                    max = Math.max(max, words[i].length() * words[j].length() );
                }
            }
        }

        return max;
    }

    private int hashcode(String word){
        int r = 0;
        
        for(int i = 0; i < word.length(); i++){
            r |= (1 << (word.charAt(i) - 'a' ));
        }

        return r;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
