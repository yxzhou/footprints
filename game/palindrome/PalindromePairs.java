package fgafa.game.palindrome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * Given a list of unique words. Find all pairs of distinct indices (i, j) in the given list, 
 * so that the concatenation of the two words, i.e. words[i] + words[j] is a palindrome.

    Example 1:
    Given words = ["bat", "tab", "cat"]
    Return [[0, 1], [1, 0]]
    The palindromes are ["battab", "tabbat"]
    
    Example 2:
    Given words = ["abcd", "dcba", "lls", "s", "sssll"]
    Return [[0, 1], [1, 0], [3, 2], [2, 4]]
    The palindromes are ["dcbaabcd", "abcddcba", "slls", "llssssll"]
 *
 */

public class PalindromePairs {
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<>();
        
        if(null == words || words.length < 2){
            return result;
        }
        
        for(int i = 0; i < words.length; i++){
            for(int j = i + 1; j < words.length; j++){
                if(isPalindromePair(words[i], words[j])){
                    result.add(Arrays.asList(new Integer[]{i, j}));
                }
                
                if(isPalindromePair(words[j], words[i])){
                    result.add(Arrays.asList(new Integer[]{j, i}));
                }
            }
        }
        
        return result;
    }
    
    private boolean isPalindromePair(String s1, String s2){
        int left = 0; 
        int right = s2.length() - 1;
        
        for( ; left < s1.length() && right >= 0; left++, right-- ){
            if(s1.charAt(left) != s2.charAt(right)){
                return false;
            }
        }
        
        if(left < s1.length()){
            for( right = s1.length() - 1; left < right; left++, right-- ){
                if(s1.charAt(left) != s1.charAt(right)){
                    return false;
                }
            }
        }else if(right > 0){
            for( left = 0; left < right; left++, right-- ){
                if(s2.charAt(left) != s2.charAt(right)){
                    return false;
                }
            }
        }
        
        return true;
    }
}
