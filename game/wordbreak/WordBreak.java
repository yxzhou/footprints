package game.wordbreak;

import java.util.HashSet;
import java.util.List;
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
 */
public class WordBreak {

    /** dp **/
    public boolean wordBreak_dp(String s, List<String> dict) {
        boolean[] dp = new boolean[s.length()+1]; //default all are false
        dp[0] = true; //set first to be true, why? Because we need initial state
 
        for(int i=0; i<s.length(); i++){
            //should continue from match position
            if(!dp[i]) {
                continue;
            }
 
            //if the dict.size() is small
            for(String a: dict){
                int end = i + a.length();

                if(end > s.length() || dp[end]) {
                    continue;
                }
 
                if(s.substring(i, end).equals(a)){
                    dp[end] = true;
                }
            }
        }
 
        return dp[s.length()];
	}
    

	/**  dfs + set **/

    public boolean wordBreak_dfs(String s, List<String> wordDict) {

        int n = s.length();
        int[] states = new int[n + 1]; // 0, default; 1, breakable; 2 not breakable. E.g. states[2] == 1 means substring[1..n] is breakable.
        states[n] = 1;

        Set<String> dict = new HashSet<>(wordDict);

        return dfs(s, 0, states, dict) == 1;
    }

    private int dfs(String s, int i, int[] states, Set<String> dict){
        if(states[i] > 0){
            return states[i];
        }

        states[i] = 2;

        for(int k = i + 1; k <= s.length(); k++){

            if(dict.contains(s.substring(i, k))){
                if(dfs(s, k, states, dict) == 1){
                    states[i] = 1;
                    break;
                }
            }
        }

        return states[i];
    }
    
    
     /** dfs + Trie tree  **/
     class TrieNode{
         TrieNode[] nexts = new TrieNode[26];
         boolean leaf = false;
     }

    public boolean wordBreak(String s, List<String> wordDict) {

        int n = s.length();
        int[] states = new int[n + 1]; // 0, default; 1, breakable; 2 not breakable. E.g. states[2] == 1 means substring[1..n] is breakable.
        states[n] = 1;

        TrieNode head = new TrieNode();
        for(String word : wordDict){
            add(head, word);
        }

        return dfs(s, 0, states, head) == 1;
    }

    private void add(TrieNode head, String word){
        TrieNode curr = head;
        int i;
        for(char c : word.toCharArray()){
            i = c - 'a';

            if(curr.nexts[i] == null){
                curr.nexts[i] = new TrieNode();
            }

            curr = curr.nexts[i];
        }

        curr.leaf = true;
    }

    private int dfs(String s, int i, int[] states, TrieNode head){
        if(states[i] > 0){
            return states[i];
        }

        states[i] = 2;

        TrieNode curr = head;
        for(int k = i; k < s.length(); k++){
            curr = curr.nexts[s.charAt(k) - 'a'];

            if(curr == null){
                break;
            }else if(curr.leaf){
                if(dfs(s, k + 1, states, head) == 1){
                    states[i] = 1;
                    break;
                }
            }
        }

        return states[i];
    }
}
