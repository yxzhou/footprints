package fgafa.game.wordbreak;

import java.util.Set;

/**
 * 
 * Given a string s and a dictionary of words dict, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
 *
 * For example, given
 * s = "leetcode",
 * dict = ["leet", "code"].
 *
 * Return true because "leetcode" can be segmented as "leet code".
 *
 */
public class WordBreak {

    public boolean wordBreak_n(String s, Set<String> dict) {
        boolean[] dp = new boolean[s.length()+1]; //default all are false
        dp[0] = true; //set first to be true, why? Because we need initial state
 
        for(int i=0; i<s.length(); i++){
            //should continue from match position
            if(!dp[i]) {
                continue;
            }
 
            //if the dict.size() is small
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
    
    
    public boolean wordBreak_n2(String s, Set<String> dict) {
        // check
        if(null == s || 0 == s.length() || null == dict){
            return true;
        }
        
        boolean[] dp = new boolean[s.length()+1]; //default all are false
        dp[0] = true; //set first to be true, why? Because we need initial state
        
        //if the maxLength is small, especially than dict.size().
        int maxLength = 0;
        for(String word : dict){
            maxLength = Math.max(maxLength, word.length());
        }
        
        for(int i = 0; i<s.length(); i++){
        	if(!dp[i]){
        		continue;
        	}
        	
        	for(int j=i+1, end = Math.min(i + maxLength, s.length()); j <= end ; j++){
    			if (!dp[j] && dict.contains(s.substring(i, j))) {
    				dp[j] = true;
    			}
        	}
        }
        
        return dp[s.length()];
    }
    
    
    /**
     * @param s: A string s
     * @param dict: A dictionary of words dict
     */
    /*cons:  maybe cause java.lang.StackOverflowError*/
    public boolean wordBreak_2(String s, Set<String> dict) {
        // check
        if(null == s || 0 == s.length() || null == dict){
            return true;
        }
        
        return wordBreak_2_helper(s, 0, dict);
    }
    
    private boolean wordBreak_2_helper(String s, int start, Set<String> dict){
        if(start >= s.length()){
            return true;
        }
        
        for(int i = start+1; i<=s.length(); i++){
			if (dict.contains(s.substring(start, i)) && wordBreak_2_helper(s, i, dict)) {
				return true;
			}
        }
        
        return false;
    }
}
