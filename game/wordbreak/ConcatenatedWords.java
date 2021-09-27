package game.wordbreak;

import org.junit.Test;

import java.util.*;

/**
 *  Leetcode #472
 * Given a list of words (without duplicates), please write a program that returns all concatenated words in the given list of words.

    A concatenated word is defined as a string that is comprised entirely of at least two shorter words in the given array.

    Example:
    Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]

    Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]

    Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats";
     "dogcatsdog" can be concatenated by "dog", "cats" and "dog";
    "ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".


    Note:
    The number of elements of the given array will not exceed 10,000
    The length sum of elements in the given array will not exceed 600,000.
    All the input string will only include lower case letters.
    The returned elements order does not matter.
 *
 */

public class ConcatenatedWords {

    public List<String> findAllConcatenatedWordsInADict(String[] words) {

        if(words == null || words.length < 3){
            return Collections.EMPTY_LIST;
        }

        Arrays.sort(words, (w1, w2) -> Integer.compare(w1.length(), w2.length()) );

        Set<String> dict = new HashSet<>();
        List<String> result = new ArrayList<>();

        for(String word : words){
            if( wordBreak(word, 0, new int[word.length()], dict) == 1){
                result.add(word);
            }

            dict.add(word);
        }

        return result;
    }

    /**
     *  dfs
     */
    private int wordBreak(String s, int l, int[] states, Set<String> dict){
        // 0, default; 1, breakable; 2 not breakable. E.g. states[2] == 1 means substring[1..n] is breakable.
        if(s.isEmpty()){ // special for case s == "", l == 0 == s.length()
            return 2;
        }

        if(l == s.length()){
            return 1;
        }

        if(states[l] > 0){
            return states[l];
        }

        states[l] = 2;

        for(int r = l + 1; r <= s.length(); r++){

            if(dict.contains(s.substring(l, r))){
                if(wordBreak(s, r, states, dict) == 1){
                    states[l] = 1;
                    break;
                }
            }
        }

        return states[l];
    }


    @Test
    public void test() {

        String[] words = new String[]{"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"};


        List<String> result = findAllConcatenatedWordsInADict(words);

        System.out.println(result);

    }

}
