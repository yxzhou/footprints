package fgafa.game.wordbreak;

import java.util.Set;

/**
 * 
 * Given a string s and a dictionary of words dict, 
 * determine if s can be segmented into a space-separated sequence of K dictionary words, 
 *  
 *
 * For example, 
 * Given s = "leetcode", dict = ["leet", "code"], k = 2
 * Return true because "leetcode" can be segmented as "leet code".
 *
 */

public class WordBreakIII {
    
    public boolean wordBreak_n(String s, Set<String> dict, int k) {
        boolean[] dp = new boolean[s.length()+1]; //default all are false
        dp[0] = true; //set first to be true, why? Because we need initial state
 
        for(int i=0; i<s.length(); i++){
            //should continue from match position
            if(!dp[i]) 
                continue;
 
            for(String a: dict){
                int len = a.length();
                int end = i + len;
                if(end > s.length())
                    continue;
 
                if(dp[end]) continue;
 
                if(s.substring(i, end).equals(a)){
                    dp[end] = true;
                }
            }
        }
 
        return dp[s.length()];
    }
    

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
