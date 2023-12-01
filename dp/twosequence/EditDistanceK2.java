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
public class EditDistanceK2 {
    
    /**
     *
     * based on EditDistance.minDistance_DP_n(), cache with Trie
     */
    public List<String> kDistance(String[] words, String target, int k) {
        if (words == null ) {
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
        
        TrieNode root = new TrieNode();
        int max = m + k;
        int min = m - k;
        int length;
        for(String w : words){
            length = w.length();
            if( length <= max && length >= min ){
                add(root, w);
            }
        }
        
        int[] pre = new int[m + 1];
        for (int c = 1; c <=m ; c++) {
            pre[c] = c;
        }
        
        helper(root, target, pre, k, result);

        return result;
    }
    
    private void helper(TrieNode node, String target, int[] pre, int k, List<String> result){
        int m = target.length();
                
        if(node.word != null && pre[m] <= k){
            result.add(node.word);
        }

        int[] cur = new int[m + 1];
        cur[0] = pre[0] + 1;
        
        for(int i = node.children.length - 1; i >= 0; i--){
            if(node.children[i] == null){
                continue;
            }
            
            for(int c = 0; c < m; c++){
                cur[c + 1] = Math.min(cur[c], pre[c + 1]) + 1;
                cur[c + 1] = Math.min(cur[c + 1], pre[c] + ((i == target.charAt(c) - 'a') ? 0 : 1 ) );
            }
            
            helper(node.children[i], target, cur, k, result);
        }
    }
    
    private void add(TrieNode root, String word){
        TrieNode node = root;
        
        for(int i = 0, n = word.length(), j; i < n; i++){
            j = word.charAt(i) - 'a';
            if(node.children[j] == null){
                node.children[j] = new TrieNode();
            }
            
            node = node.children[j];
        }
        
        node.word = word;
    }

    class TrieNode {
        TrieNode[] children = new TrieNode[26]; // from a to z
        String word = null;
    }
    
    
}
