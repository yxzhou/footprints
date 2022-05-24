package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import util.Misc;

/**
 * 
 * Suppose we have a list of strings called words and another string called letters, we have to find the length of
 * longest string in words that can be formed from characters in letters. 
 * Here we cannot reuse letters.
 *
 * Example 1
 * Input: words = ["dog", "cat", "rat", "bunny", "lion", "bat"], letters = "gabctnyu", 
 * Output: {"cat", "bat"}.
 *
 */


public class LongestFormedWord {

    
    public List<String> longestFormedWord(char[] board, String[] words) {
        
        int[] counts = new int[26];
        int c;
        for(int i = 0; i < board.length; i++){
            counts[board[i] - 'a']++;
        }
        
        List<String> result = new ArrayList<>();
        int maxLength = 0;
        
        int[] needs = new int[26];
        outer: for(String word : words){
          
            System.arraycopy(counts, 0, needs, 0, counts.length);
            
            for(int i = 0; i < word.length(); i++){
                c = word.charAt(i) - 'a';
                needs[c]--;
                
                if(needs[c] < 0){
                    continue outer; 
                }
            }
            
            if(maxLength < word.length()){
                result.clear();
                maxLength = word.length();
            }
            if(maxLength == word.length()){
                result.add(word);
            }
            
        }
        
        return result;
    }   
    
    public static void main(String[] args){
        
        LongestFormedWord sv = new LongestFormedWord();
        
        String[][][] inputs = {
            {
                {"dog", "cat", "rat", "bunny", "lion", "bat"},
                {"gabctnyu"},
                {"bat", "cat"}
            }
        };
        
        String[] result;
        for(String[][] input : inputs){
            System.out.println(String.format("words: %s, board: %s", Arrays.toString(input[0]), input[1][0]));
            
            result = sv.longestFormedWord(input[1][0].toCharArray(), input[0]).toArray(new String[0]);
            Arrays.sort(result);
            //System.out.println( Misc.array2String(result) );
            
            Assert.assertArrayEquals(input[2], result );
        }
    }
    
}
