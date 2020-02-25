package fgafa.bitwise;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Leetcode 1255
 *
 * Given a list of words, list of  single letters (might be repeating) and score of every character.
 *
 * Return the maximum score of any valid set of words formed by using the given letters (words[i] cannot be used two or more times).
 *
 * It is not necessary to use all characters in letters and each letter can only be used once. Score of letters 'a', 'b', 'c', ... ,'z' is given by score[0], score[1], ... , score[25] respectively.
 *
 *
 *
 * Example 1:
 *
 * Input: words = ["dog","cat","dad","good"], letters = ["a","a","c","d","d","d","g","o","o"], score = [1,0,9,5,0,0,3,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0]
 * Output: 23
 * Explanation:
 * Score  a=1, c=9, d=5, g=3, o=2
 * Given letters, we can form the words "dad" (5+1+5) and "good" (3+2+2+5) with a score of 23.
 * Words "dad" and "dog" only get a score of 21.
 * Example 2:
 *
 * Input: words = ["xxxz","ax","bx","cx"], letters = ["z","a","b","c","x","x","x"], score = [4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,10]
 * Output: 27
 * Explanation:
 * Score  a=4, b=4, c=4, x=5, z=10
 * Given letters, we can form the words "ax" (4+5), "bx" (4+5) and "cx" (4+5) with a score of 27.
 * Word "xxxz" only get a score of 25.
 * Example 3:
 *
 * Input: words = ["leetcode"], letters = ["l","e","t","c","o","d"], score = [0,0,1,1,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,0,0,0]
 * Output: 0
 * Explanation:
 * Letter "e" can only be used once.
 *
 *
 * Constraints:
 *
 * 1 <= words.length <= 14
 * 1 <= words[i].length <= 15
 * 1 <= letters.length <= 100
 * letters[i].length == 1
 * score.length == 26
 * 0 <= score[i] <= 10
 * words[i], letters[i] contains only lower case English letters.
 *
 */

public class MaxScoreWords {

    @Test public void test(){
        String[] words = {"dog","cat","dad","good"};
        char[] letters = {'a','a','c','d','d','d','g','o','o'};
        int[] score = {1,0,9,5,0,0,3,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0};

        //System.out.println(maxScoreWords(words, letters, score));

        Assert.assertEquals(23, maxScoreWords(words, letters, score));

        words = new String[]{"xxxz","ax","bx","cx"};
        letters = new char[]{'z','a','b','c','x','x','x'};
        score = new int[]{4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,10};

        Assert.assertEquals(27, maxScoreWords(words, letters, score));

    }

    public int maxScoreWords(String[] words, char[] letters, int[] score) {
        int[] full = new int[26];
        for(char c : letters){
            full[c - 'a']++;
        }

        int wn = words.length;
        int[][] wordCode = new int[wn][26];
        for(int i = 0; i < wn; i++){
            for(char c : words[i].toCharArray()){
                wordCode[i][c - 'a']++;
            }
        }

        //dfs
        return dfs(wordCode, 0, full.clone(), new HashSet<>(), score, full);
    }

    //dfs
    private int dfs(int[][] wordCode, int position, int[] state, Set<int[]> visited, int[] score, int[] full){
        if(visited.contains(state)){
            return 0;
        }

        visited.add(state.clone());

        int max = calculate(state, score, full);
        for(int i = position, nw = wordCode.length; i < nw; i++){
            if(valid(state, wordCode[i])){
                decrease(state, wordCode[i]);

                max = Math.max(max, dfs(wordCode, i + 1, state, visited, score, full));

                increase(state, wordCode[i]);
            }
        }

        return max;
    }

    private boolean valid(int[] forFill, int[] toFill){
        for(int i = 0; i < 26; i++){
            if(forFill[i] < toFill[i]){
                return false;
            }
        }
        return true;
    }

    private void decrease(int[] forFill, int[] toFill){
        for(int i = 0; i < 26; i++){
            forFill[i] -= toFill[i];
        }
    }

    private void increase(int[] forFill, int[] toFill){
        for(int i = 0; i < 26; i++){
            forFill[i] += toFill[i];
        }
    }

    private int calculate(int[] state, int[] score, int[] full){
        int sum = 0;
        for(int i = 0; i < 26; i++){
            sum += (full[i]- state[i]) * score[i];
        }
        return sum;
    }

}
