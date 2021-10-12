package dfsbfs.wordBreak;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Leetcode #139
 *
 *
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
 *
 * Note:
 *
 * The same word in the dictionary may be reused multiple times in the segmentation.
 * You may assume the dictionary does not contain duplicate words.
 *
 * Example 1:
 * Input: s = "leetcode", wordDict = ["leet", "code"]
 * Output: true
 * Explanation: Return true because "leetcode" can be segmented as "leet code".
 *
 * Example 2:
 * Input: s = "applepenapple", wordDict = ["apple", "pen"]
 * Output: true
 * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 *              Note that you are allowed to reuse a dictionary word.
 *
 * Example 3:
 * Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * Output: false
 * 
 * Solutions:
 * Solution #1, DFS + memorization search 
 * Solution #2, DP
 * Solution #3, DFS + Trie tree + memorization search 
 *
 * 
 */
public class WordBreak {

    /** 
     * DP 
     *   wordBreak_DP < wordBreak_DP_2 < wordBreak_DP_n
     **/
    public boolean wordBreak_DP(String s, List<String> dict) {
        if(s == null || s.length() == 0){
            return true;
        }
                
        boolean[] dp = new boolean[s.length() + 1]; //default all are false
        dp[0] = true; //set first to be true, why? Because we need initial state

        for (int i = 0; i < s.length(); i++) {
            //should continue from match position
            if (!dp[i]) {
                continue;
            }

            //if the dict.size() is small
            for (String a : dict) {
                int end = i + a.length();

                if (end > s.length() || dp[end]) {
                    continue;
                }

                if (s.substring(i, end).equals(a)) {
                    dp[end] = true;
                }
            }
        }

        return dp[s.length()];
    }
    
    public boolean wordBreak_DP_2(String s, List<String> dict) {
        if(s == null || s.length() == 0 || dict == null){
            return true;
        }

        Set<String> wordSet = new HashSet<>(dict);
        
        int maxLen = 0;
        for (String word : wordSet) {
            maxLen = Math.max(maxLen, word.length());
        }
        
        int n = s.length();
        boolean[] f = new boolean[n + 1]; //default all are false
        f[n] = true;
        
        for (int r = n; r > 0; r--) {
            if (!f[r]) {
                continue;
            }

            for (int l = r - 1, k = Math.max(r - maxLen - 1, -1); l > k; l--) {
                if (wordSet.contains(s.substring(l, r))) {
                    f[l] = true;
                }
            }
        }

        return f[0];
    }  
    
    /** 
     * define n as s' length, m as the number of words in wordSet, l as the max word length in wordSet, 
     * Time O( m + n * l ) Space O(n)
     */
    public boolean wordBreak_DP_n(String s, List<String> dict) {
        if(s == null || s.length() == 0 || dict == null){
            return true;
        }

        Set<String> wordSet = new HashSet<>(dict);
        
        int maxLen = 0;
        for (String word : wordSet) {
            maxLen = Math.max(maxLen, word.length());
        }

        int n = s.length();
        boolean[] f = new boolean[n + 1]; //default all are false
        f[0] = true;
        
        for (int r = 1; r <= n; r++) {
            for (int l = r - 1, k = Math.max(r - maxLen - 1, -1); l > k; l--) {
                if (f[l] && wordSet.contains(s.substring(l, r))) {
                    f[r] = true;
                    break;
                }
            }
        }

        return f[n];
    }
    

    /**  dfs + set + memorization search **/

    public boolean wordBreak_DFS(String s, List<String> dict) {
        if(s == null || s.length() == 0 || dict == null){
            return true;
        }
        
        int n = s.length();
        int[] states = new int[n + 1]; // 0, default; 1, breakable; 2 not breakable. E.g. states[2] == 1 means substring[1..n] is breakable.
        states[n] = 1;

        Set<String> wordSet = new HashSet<>(dict);
        
        int maxLen = 0;
        for (String word : wordSet) {
            maxLen = Math.max(maxLen, word.length());
        }

        return dfs(s, 0, states, wordSet, maxLen) == 1;
    }

    private int dfs(String s, int l, int[] states, Set<String> wordSet, int maxLen){
        if(states[l] > 0){
            return states[l];
        }

        states[l] = 2;

        for (int r = l + 1, end = Math.min(l + maxLen + 1, s.length() + 1); r < end; r++) {
            if(wordSet.contains(s.substring(l, r))){
                if(dfs(s, r, states, wordSet, maxLen) == 1){
                    states[l] = 1;
                    break;
                }
            }
        }

        return states[l];
    }
    
    /**  --  Trie start -- */

    class TrieNode {
        TrieNode[] nexts = new TrieNode[26];
        boolean leaf = false;
    }
    
    private void addPrefixTree(TrieNode root, String word) {
        TrieNode curr = root;
        int i;
        for (char c : word.toCharArray()) {
            i = c - 'a';

            if (curr.nexts[i] == null) {
                curr.nexts[i] = new TrieNode();
            }

            curr = curr.nexts[i];
        }

        curr.leaf = true;
    }
    
    private void addPostfixTree(TrieNode root, String word) {
        TrieNode curr = root;
        int i;
        for (int j = word.length() - 1; j >= 0; j--) {
            i = word.charAt(j) - 'a';

            if (curr.nexts[i] == null) {
                curr.nexts[i] = new TrieNode();
            }

            curr = curr.nexts[i];
        }

        curr.leaf = true;
    }
    
    /** 
     *  refer to wordBreak_DP_n
     *  DP + postfix tree 
     */
    public boolean wordBreak_Trie_n(String s, List<String> dict){
        if(s == null || s.length() == 0 || dict == null){
            return true;
        }

        TrieNode root = new TrieNode();
        dict.forEach(word -> {
            addPostfixTree(root, word);
        });
        
        int n = s.length();
        boolean[] dp = new boolean[n + 1]; //default all are false
        dp[0] = true;

        TrieNode curr;
        for (int r = 1; r <= n; r++) {
            curr = root;
            for (int l = r - 1; l >= 0 && curr != null; l--) {
                curr = curr.nexts[s.charAt(l) - 'a'];
                if (dp[l] && curr != null && curr.leaf) {
                    dp[r] = true;
                    break;
                }
            }
        }

        return dp[n];
    }
    
    /** 
     *  refer to wordBreak_DP_2
     * DP + prefix tree  
     */
    public boolean wordBreak_Trie_2(String s, List<String> dict){
        if(s == null || s.length() == 0 || dict == null){
            return true;
        }

        TrieNode root = new TrieNode();
        dict.forEach(word -> {
            addPrefixTree(root, word);
        });
        
        int n = s.length();
        boolean[] dp = new boolean[n + 1]; //
        dp[0] = true;

        TrieNode curr;
        for(int l = 0; l < n; l++){
            if(!dp[l]){
                continue;
            }
            
            curr = root;
            int r = l;
            for( ; r < n && curr != null; r++){
                if(curr.leaf){
                    dp[r] = true;
                }
                curr = curr.nexts[s.charAt(r) - 'a'];
            }

            if(r == n && curr != null && curr.leaf){
                return true;
            }
        }

        return false;
    }

    /** dfs + Trie tree + memorization search  **/
    public boolean wordBreak_Trie(String s, List<String> wordDict) {
        if(s == null || s.length() == 0 || wordDict == null){
            return true;
        }

        int n = s.length();
        int[] states = new int[n + 1]; // 0, default; 1, breakable; 2 not breakable. E.g. states[2] == 1 means substring[1..n] is breakable.
        states[n] = 1;

        TrieNode head = new TrieNode();
        for (String word : wordDict) {
            addPrefixTree(head, word);
        }

        return dfs(s, 0, states, head) == 1;
    }



    private int dfs(String s, int i, int[] states, TrieNode head) {
        if (states[i] > 0) {
            return states[i];
        }

        states[i] = 2;

        TrieNode curr = head;
        for (int k = i; k < s.length(); k++) {
            curr = curr.nexts[s.charAt(k) - 'a'];

            if (curr == null) {
                break;
            } else if (curr.leaf) {
                if (dfs(s, k + 1, states, head) == 1) {
                    states[i] = 1;
                    break;
                }
            }
        }

        return states[i];
    }
}
