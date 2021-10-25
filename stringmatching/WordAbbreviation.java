/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringmatching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.junit.Assert;

/**
 *
 * Given an array of n distinct non-empty strings, you need to generate minimal possible abbreviations for every word following rules below.
 *   1. Begin with the first character and then the number of characters abbreviated, which followed by the last character.
 *   2. If there are any conflict, that is more than one words share the same abbreviation, a longer prefix is used 
 *   instead of only the first character until making the map from word to abbreviation become unique. In other words, 
 *   a final abbreviation cannot map to more than one original words.
 *   3. If the abbreviation doesn't make the word shorter, then keep it as original.
 * 
 * Notes:
 *   Both n and the length of each word will not exceed 400.
 *   The length of each word is greater than 1.
 *   The words consist of lowercase English letters only.
 *   The return answers should be in the same order as the original array.
 * 
 * Example 1:
 * Input: ["like","god","internal","me","internet","interval","intension","face","intrusion"]
 * Output: ["l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"]
 * 
 * Example 2:
 * Input: ["where","there","is","beautiful","way"]
 * Output: "w3e","t3e","is","b7l","way"]
 * 
 * Thoughts:
 *   1) in example 1:
 *      internet  i6t
        internal  internal
        interval  interval
        intension inte4n 
        intrusion intr4n 
 *   So the rule #1: check the abbr, first+number+last, if it's unique, such as i6t ..., if it's not unique, 
 *          rule #2: the abbr is the-longest-common-prefix + number + last 
 *          rule #3: in the above rules, if the number is 1, use the word directly
 */
public class WordAbbreviation {
    
    public String[] wordsAbbreviation(String[] dict) {
        Map<String, List<Integer>> map = new HashMap<>();
        String abbr;
        int n = dict.length;
        for(int i = 0; i < n; i++){
            abbr = getAbbr(dict[i]);
            map.putIfAbsent(abbr, new LinkedList<>());
            map.get(abbr).add(i);
        }

        String[] result = new String[dict.length];
        int k = 0;

        Map<Integer, String> abbrs = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();

        for(int i = 0; i < n; i++){
            abbr = getAbbr(dict[i]);
            if(map.get(abbr).size() == 1){
                result[k++] = abbr;
            }else {
                if(!abbrs.containsKey(i)){
                    queue.addAll(map.get(abbr));
                    createAbbrs(dict, queue, abbrs);
                }

                result[k++] = abbrs.get(i);
            }
        }

        return result;
    }

    private void createAbbrs(String[] dict, Queue<Integer> queue, Map<Integer, String> abbrs){
        int n = dict[queue.peek()].length();
        int[] counts;
        int top; 
        String word;

        for(int i = 0; !queue.isEmpty(); i++){ // i < n
            //Arrays.fill(counts, 0);
            counts = new int[26];

            for(int j = queue.size(); j > 0; j--){
                top = queue.poll();
                word = dict[top];

                counts[word.charAt(i) - 'a']++;
                queue.add(top);
            }
            for(int j = queue.size(); j > 0; j--){
                top = queue.poll();
                word = dict[top];
                        
                if(counts[word.charAt(i) - 'a'] == 1){
                    if( i < n - 3){     
                        abbrs.put(top, word.substring(0, i + 1) + String.valueOf(n - i - 2) + word.charAt(n - 1));
                    }else{
                        abbrs.put(top, word);
                    }
                }else{
                    queue.add(top);
                }
            }
        }
    }
    
    /**
     * 
     * @param dict: an array of n distinct non-empty strings
     * @return: an array of minimal possible abbreviations for every word
     */
    public String[] wordsAbbreviation_trie(String[] dict) {
        Map<String, List<Integer>> map = new HashMap<>();
        String abbr;
        int n = dict.length;
        for(int i = 0; i < n; i++){
            abbr = getAbbr(dict[i]);
            map.putIfAbsent(abbr, new ArrayList<>());
            map.get(abbr).add(i);
        }

        String[] result = new String[dict.length];
        int k = 0;

        Map<Integer, String> abbrs = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();

        for(int i = 0; i < n; i++){
            abbr = getAbbr(dict[i]);
            if(map.get(abbr).size() == 1){
                result[k++] = abbr;
            }else {
                if(!abbrs.containsKey(i)){
                    createAbbrs(dict, map.get(abbr), abbrs);
                }

                result[k++] = abbrs.get(i);
            }
        }

        return result;
    }

    /**
     * @param words
     * @param abbrs <word, the-longest-common-prefix + number + last>
     */
    private void createAbbrs(String[] dict, List<Integer> words, Map<Integer, String> abbrs){
        TrieNode root = new TrieNode();

        TrieNode curr;
        int c;
        String word;
        for(Integer id : words){
            word = dict[id];
            curr = root;

            for(int i = 0, n = word.length(); i < n; i++){
                c = word.charAt(i) - 'a';
                if(curr.children[c] == null){
                    curr.children[c] = new TrieNode();
                }
                curr = curr.children[c];
                curr.size++;
            }
        }

        int i;
        int n;
        for(Integer id : words){
            word = dict[id];
            
            curr = root;
            i = 0;
            n = word.length();
            while( i < n ){
                curr = curr.children[word.charAt(i) - 'a'];
                if(curr.size == 1){
                    break;
                }
                i++;
            }

            if( i < n - 3){
                abbrs.put(id, word.substring(0, i + 1) + String.valueOf(n - i - 2) + word.charAt(n - 1));
            }else{
                abbrs.put(id, word);
            }
        }
    }

    class TrieNode{
        TrieNode[] children = new TrieNode[26];
        int size = 0;
    }
    
    private String getAbbr(String word){
        int n = word.length();
        if(n < 4){
            return word;
        }

        return word.charAt(0) + String.valueOf(n - 2) + word.charAt(n - 1);
    }
    
    
    public static void main(String[] args){
        WordAbbreviation sv = new WordAbbreviation();
        
        Assert.assertArrayEquals(new String[]{"l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"}, sv.wordsAbbreviation(new String[]{"like","god","internal","me","internet","interval","intension","face","intrusion"}));
        Assert.assertArrayEquals(new String[]{"w3e","t3e","is","b7l","way"}, sv.wordsAbbreviation(new String[]{"where","there","is","beautiful","way"}));
        
        Assert.assertArrayEquals(new String[]{"l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"}, sv.wordsAbbreviation_trie(new String[]{"like","god","internal","me","internet","interval","intension","face","intrusion"}));
        Assert.assertArrayEquals(new String[]{"w3e","t3e","is","b7l","way"}, sv.wordsAbbreviation_trie(new String[]{"where","there","is","beautiful","way"}));
    }
}
