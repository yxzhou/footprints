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
 * 
 */
public class EditDistanceK3 {
    
    /**
     *
     * based on EditDistance.minDistance_DP_n(), cache with Trie
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

        int m = target.length();
        TrieNode root = new TrieNode(m + 1); //suffix tree
        int[] pre = root.f;
        for (int c = m; c >= 0; c--) {
            pre[c] = m - c;
        }
        
        int[] cur;
        TrieNode node;
        int i;
        for (String word : words) {
            node = root;
            for (int n = word.length(), r = n - 1; r >= 0; r--) {
                i = word.charAt(r) - 'a';
                
                if (node.children[i] == null) {
                    pre = node.f;

                    node.children[i] = new TrieNode(m + 1);
                    node = node.children[i];

                    cur = node.f;
                    cur[m] = n - r;
                    for (int c = m - 1; c >= 0; c--) {
                        cur[c] = Math.min(cur[c + 1], pre[c]) + 1;
                        cur[c] = Math.min(cur[c], pre[c + 1] + (word.charAt(r) == target.charAt(c) ? 0 : 1));
                    }
                } else {
                    node = node.children[i];
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

            if (node.f[0] <= k) {
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
