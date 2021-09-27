package game.palindrome;

import java.util.*;

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

    /** with HashMap */
    public List<List<Integer>> palindromePairs_n(String[] words) {
        List<List<Integer>> ret = new ArrayList<>();
        if (words == null || words.length < 2) {
            return ret;
        }

        Map<String, Integer> map = new HashMap<>();
        for (int i=0; i<words.length; i++) {
            map.put(words[i], i);
        }

        for (int i=0; i<words.length; i++) {
            for (int j=0; j<=words[i].length(); j++) { // notice it should be "j <= words[i].length()"
                String str1 = words[i].substring(0, j);
                String str2 = words[i].substring(j);
                if (isPalindrome(str1)) {
                    String str2rvs = new StringBuilder(str2).reverse().toString();
                    if (map.containsKey(str2rvs) && map.get(str2rvs) != i) {
                        ret.add(Arrays.asList(new Integer[]{map.get(str2rvs), i}));
                    }
                }

                // check "str.length() != 0" to avoid duplicates
                if (str2.length()!=0 && isPalindrome(str2)) {
                    String str1rvs = new StringBuilder(str1).reverse().toString();

                    if (map.containsKey(str1rvs) && map.get(str1rvs) != i) {
                        ret.add(Arrays.asList(new Integer[]{i, map.get(str1rvs)}));
                    }
                }
            }
        }
        return ret;
    }

    private boolean isPalindrome(String str) {
        for (int left = 0, right = str.length() - 1; left <= right; left++, right-- ) {
            if (str.charAt(left) !=  str.charAt(right)) {
                return false;
            }
        }

        return true;
    }
}
