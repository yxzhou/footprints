/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp.twosequence;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * Given a set of strings which just has lower case letters and a target string, output all the strings for each the edit distance with the target no greater than k.
 * You have the following 3 operations permitted on a word:
 *   Insert a character
 *   Delete a character
 *   Replace a character
 *   
 * Example 1:
 * Given words = `["abc", "abd", "abcd", "adc"]` and target = `"ac"`, k = `1`
 * Return `["abc", "adc"]`
 * Explanation: "abc" remove "b"  "adc" remove "d"
 * 
 * Example 2:
 * Input: ["acc","abcd","ade","abbcd"] "abc" 2
 * Output: ["acc","abcd","ade","abbcd"]
 * Explanation:
 *   "acc" turns "c" into "b"
 *   "abcd" remove "d"
 *   "ade" turns "d" into "b" turns "e" into "c"
 *   "abbcd" gets rid of "b" and "d"
 * 
 */
public class EditDistanceK {
    
    /**
     *
     * based on EditDistance.minDistance_DP_n()
     */
    public List<String> kDistance(String[] words, String target, int k) {
        if (words == null || words.length == 0) {
            return Collections.EMPTY_LIST;
        }

        List<String> result = new LinkedList<>();
        if (target == null || target.length() == 0) {
            for (String w : words) {
                if (w.length() <= k) {
                    result.add(w);
                }
            }

            return result;
        }

        int n = target.length();
        TrieNode root = new TrieNode(n + 1);
        int[] preF = root.f;
        for (int i = 1; i <= n; i++) {
            preF[i] = i;
        }
        int[] currF;

        int i;
        TrieNode curr;
        for (String word : words) {
            curr = root;
            for (int j = 0; j < word.length(); j++) {
                i = word.charAt(j) - 'a';
                if (curr.children[i] == null) {
                    preF = curr.f;

                    curr.children[i] = new TrieNode(n + 1);
                    curr = curr.children[i];

                    currF = curr.f;
                    currF[0] = j + 1;
                    for (int m = 0, m1 = 1; m < n; m++, m1++) {
                        currF[m1] = Math.min(currF[m], preF[m1]) + 1;
                        currF[m1] = Math.min(currF[m1], preF[m] + (word.charAt(j) == target.charAt(m) ? 0 : 1));
                    }
                } else {
                    curr = curr.children[i];
                }

                /**
                 * the following checking is Wrong, for case {source = "ab", target = "eefab"}, 
                 *   f[n] is 4 for "a" and "eefab"
                 *   f[n] is 3 for "ab" and "eefab"
                 */
                // if(curr.f[n] > k ){ 
                //     break;
                // }
            }

            if (curr.f[n] <= k) {
                result.add(word);
            }
        }

        return result;
    }

    class TrieNode {
        TrieNode[] children = new TrieNode[26]; // from a to z
        int[] f;   //min edit distance 

        TrieNode(int n) {
            f = new int[n];
        }
    }
}
