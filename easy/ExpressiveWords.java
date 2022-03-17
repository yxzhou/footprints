/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/1008
 *
 * Sometimes people repeat letters to represent extra feeling, such as "hello" -> "heeellooo", "hi" -> "hiiii". Here, we
 * have groups, of adjacent letters that are all the same character, and adjacent characters to the group are different.
 * A group is extended if that group is length 3 or more, so "e" and "o" would be extended in the first example, and "i"
 * would be extended in the second example. As another example, the groups of "abbcccaaaa" would be "a", "bb", "ccc",
 * and "aaaa"; and "ccc" and "aaaa" are the extended groups of that string.
 *
 * For some given string S, a query word is stretchy if it can be made to be equal to S by extending some groups.
 * Formally, we are allowed to repeatedly choose a group (as defined above) of characters c, and add some number of the
 * same character c to it so that the length of the group is 3 or more. Note that we cannot extend a group of size one
 * like "h" to a group of size two like "hh" - all extensions must leave the group extended - ie., at least 3 characters
 * long.
 *
 * Given a list of query words, return the number of words that are stretchy.
 *
 * Constraints:
 *   0 <= len(S) <= 100. 
 *   0 <= len(words) <= 100. 
 *   0 <= len(words[i]) <= 100. 
 *   S and all words in words consist only of lowercase letters
 *
 * Example 1: 
 * Input: S = "heeellooo", 
 * words = ["hello", "hi", "helo"] 
 * Output: 1 
 * Explanation: We can extend "e" and "o" in the word "hello" to get "heeellooo". We can't extend "helo" to get 
 * "heeellooo" because the group "ll" is not extended.
 *
 * Example 2: 
 * Input: S = "qqjjjjjjuuuuuvvvviiii", 
 * words = ["qqjjuvvii","qjuvi","qqjuuvii","qjjuvii","qjuvvii","qqjjuvii","qqjuvii"] 
 * Output: 4
 * 
 * Thoughts:
 *   the rules:
 *   heeellooo - hello, true
 *   heeellooo - helo, false
 *   heeellooo - heello, true
 */
public class ExpressiveWords {
    /**
     * @param S: a string
     * @param words: a list of strings
     * @return: return a integer
     */
    public int expressiveWords(String S, String[] words) {
        if(S == null || words == null ){
            return 0;
        }

        List<Character> chars = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        for(int i = 0, n = S.length(), j = 0; i < n; i = j){
            while(j < n && S.charAt(i) == S.charAt(j) ){
                j++;
            }

            chars.add(S.charAt(i));
            counts.add(j - i);
        }

        int result = 0;

        int diff;
        int k;
        outer: for(String word : words){
            k = 0;

            for(int i = 0, n = word.length(), j = 0; i < n; k++, i = j){
                while(j < n && word.charAt(i) == word.charAt(j) ){
                    j++;
                }

                if(k >= chars.size() || chars.get(k) != word.charAt(i) || (diff = counts.get(k) - j + i) < 0 || (diff != 0 && counts.get(k) < 3 )  ){
                    continue outer;
                }
            }

            if(k == chars.size()){
                result++;
            }

        }

        return result;
    }
    
    public static void main(String[] args){
        String[][][] inputs = {
            {
                {"heeellooo"},
                {"hello", "hi", "helo", "heello", "hellon"},
                {"2"}
            },
            {
                {"qqjjjjjjuuuuuvvvviiii"},
                {"qqjjuvvii","qjuvi","qqjuuvii","qjjuvii","qjuvvii","qqjjuvii","qqjuvii"},
                {"4"}
            }
        };
        
        ExpressiveWords sv = new ExpressiveWords();
        
        for(String[][] input : inputs){
            System.out.println(String.format("\n%s\n%s", input[0][0], Arrays.toString(input[1])));
            
            Assert.assertEquals(Integer.parseInt(input[2][0]), sv.expressiveWords(input[0][0], input[1]));
        }
    }
}
