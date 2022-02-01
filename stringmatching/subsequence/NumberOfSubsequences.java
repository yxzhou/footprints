/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stringmatching.subsequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * _https://www.lintcode.com/problem/1024/
 * 
 * 
 * Given string S and a dictionary of words words, find the number of words[i] that is a subsequence of S.
 *
 * Subsequence is different from substring. Subsequences may not be continuous.
 *
 * Constraints:
 *   All words in words and S will only consists of lowercase letters. 
 *   The length of S will be in the range of [1, 50000].
 *   The length of words will be in the range of [1, 5000]. 
 *   The length of words[i] will be in the range of [1, 50].
 * 
 * Example 1:
 * Input: S = "abcde", words = ["a", "bb", "acd", "ace"] 
 * Output: 3 
 * Explanation: 
 * There are three words in words that are a subsequence of S: "a", "acd", "ace". 
 * 
 * Example 2:
 * Input: S = "dsahjpjauf", words = ["ahjpjau","ja","ahbwzgqnuk","tnmlanowax"] 
 * Output: 2
 *
 */
public class NumberOfSubsequences {
    
    /**
     * @param S: a string
     * @param words: a dictionary of words
     * @return the number of words[i] that is a subsequence of S
     */
    public int numMatchingSubseq(String S, String[] words) {
        TreeSet<Integer>[] letters = new TreeSet[26]; // 
        for(int i = 0; i < letters.length; i++){
            letters[i] = new TreeSet<>();
        }

        for(int i = 0; i < S.length(); i++ ){
            letters[S.charAt(i) - 'a' ].add(i);
        }

        int count = 0;
        Integer p;
        outer: for(String word : words){
            
            p = -1;
            for(int i = 0; i < word.length(); i++){
                p = letters[word.charAt(i) - 'a'].higher(p);

                if(p == null){
                    continue outer;
                }
            }

            count++;
        }

        return count;
    }
    
    
    public int numMatchingSubseq_n(String S, String[] words) {
        List<Integer>[] letters = new ArrayList[26]; // 
        for(int i = 0; i < letters.length; i++){
            letters[i] = new ArrayList<>();
        }

        for(int i = 0; i < S.length(); i++ ){
            letters[S.charAt(i) - 'a' ].add(i);
        }

        int count = 0;
        int p;
        List<Integer> list;
        int k;
        outer: for(String word : words){
            
            p = -1;
            for(int i = 0; i < word.length(); i++){
                list = letters[word.charAt(i) - 'a'];
                k = Collections.binarySearch(list, p + 1);

                if(k < 0){
                    k = -k - 1;
                }

                if(k == list.size()){
                    continue outer;
                }

                p = list.get(k);
            }

            count++;
        }

        return count;
    }
    
    
}
