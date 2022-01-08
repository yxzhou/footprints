/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringmatching;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/812
 * 
 * Given a set of keywords words and a string S, make all appearances of all keywords in S bold. Any letters between <b>
 * and </b> tags become bold. The returned string should use the least number of tags possible, and of course the tags
 * should form a valid combination.
 *
 * Notes:
 * words has length in range [0, 50]. 
 * words[i] has length in range [1, 10]. 
 * S has length in range [0, 500]. 
 * All characters in words[i] and S are lowercase letters.
 * 
 * Example 1:
 * Input: ["ab", "bc"] "aabcd"
 * Output: "a<b>abc</b>d"
 * Explanation: Note that returning "a<b>a<b>b</b>c</b>d" would use more tags, so it is incorrect.
 * 
 * Example 2:
 * Input: ["bcccaeb","b","eedcbda","aeebebebd","ccd","eabbbdcde","deaaea","aea","accebbb","d"] "ceaaabbbedabbecbcced"
 * Output: "ceaaa<b>bbb</b>e<b>d</b>a<b>bb</b>ec<b>b</b>cce<b>d</b>"
 * 
 */
public class BoldWordsInString {
    
    /**
     * define n as the length of S, m as the length of words, w as the length of words[i]
     * Time O(n*m*w),  Space O(n)
     * 
     * @param words: the words
     * @param S: the string
     * @return the string with least number of tags
     */
    public String boldWords(String[] words, String S) {
        if(S == null || S.isEmpty()){
            return S;
        }

        int n = S.length();
        boolean[] bolds = new boolean[n];

        for(String word : words){
            int i = 0;
            while( (i = S.indexOf(word, i)) != -1  ){
                Arrays.fill(bolds, i, i + word.length(), true);
                i++;  // not i += word.length()
            }
        } 

        StringBuilder result = new StringBuilder();
        
        for(int r = 0; r < n; r++){
            if( (r == 0 && bolds[r]) || ( r > 0 && bolds[r] != bolds[r - 1]) ){
                if(bolds[r]){
                    result.append("<b>");
                }else{
                    result.append("</b>");
                }
            }
            
            result.append(S.charAt(r));
        }
        
        if(bolds[n - 1]){
            result.append("</b>");
        }

        return result.toString();
    }
    
    public String boldWords_n(String[] words, String S) {
        if (S == null || S.isEmpty()) {
            return S;
        }

        int n = S.length();
        int[] s = new int[n + 1];
        
        for (String w : words) {
            int i = 0;
            while ((i = S.indexOf(w, i)) >= 0) {
                s[i]++;
                s[i + w.length()]--;
                i++;
            }
        }
        StringBuilder sb = new StringBuilder();
        int pre = 0;
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            sum += s[i];
            if (sum > 0 && pre == 0) {
                sb.append("<b>");
            }
            if (sum == 0 && pre > 0) {
                sb.append("</b>");
            }
            if (i < n) {
                sb.append(S.charAt(i));
            }
            pre = sum;
        }
        return sb.toString();
       
    }
    
    public static void main(String[] args){
        String[][][] inputs = {
            {
                {"ab", "bc"},
                {"aabcd"}, 
                {"a<b>abc</b>d"}
            },
            {
                {"abc"},
                {"abcabcabcdab"}, 
                {"<b>abcabcabc</b>dab"}
            },
            {
                {"bcccaeb","b","eedcbda","aeebebebd","ccd","eabbbdcde","deaaea","aea","accebbb","d"},
                {"ceaaabbbedabbecbcced"},
                {"ceaaa<b>bbb</b>e<b>d</b>a<b>bb</b>ec<b>b</b>cce<b>d</b>"}
            }
        };
        
        BoldWordsInString sv = new BoldWordsInString();
        
        for(int i = 0, n = inputs.length; i < n; i++ ){
            Assert.assertEquals(inputs[i][2][0], sv.boldWords(inputs[i][0], inputs[i][1][0]));
            
            Assert.assertEquals(inputs[i][2][0], sv.boldWords_n(inputs[i][0], inputs[i][1][0]));
        }

    }
}
