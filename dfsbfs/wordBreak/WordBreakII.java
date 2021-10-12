package dfsbfs.wordBreak;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * Given a string s and a dictionary of words dict, add spaces in s to construct a sentence where each word is a valid dictionary word.
 * 
 * Return all such possible sentences.
 * 
 * For example, given
 * s = "catsanddog",
 * dict = ["cat", "cats", "and", "sand", "dog"].
 * 
 * A solution is ["cats and dog", "cat sand dog"].
 * 
 * Solutions:
 *   In WordBreak, it's enough to find any way to break the word.  while here it has to find all possible ways. 
 *   so the best idea is based on WordBreak.wordBreak_DFS.
 *     wordBreak_DP_n  <  wordBreak_DFS_n
 * 
 *  
 *
 */
public class WordBreakII {
    public List<String> wordBreak(String s, Set<String> wordSet) {
        List<String> result = new ArrayList<>();
        if (null == s) {
            return result;
        }

        //prepare
        int maxLength = 0;
        for (String tmp : wordSet) {
            maxLength = Math.max(maxLength, tmp.length());
        }

        //from top to bottom
        Map<Integer, List<Integer>> cache = new HashMap<>();
        boolean[] canBeBroken = new boolean[s.length() + 1]; //default all are false
        helper(s, wordSet, 0, cache, canBeBroken, maxLength);

        //from bottom to top
        if (canBeBroken[0]) {
            List<Integer> breaks = new ArrayList<>(cache.keySet());
            Collections.sort(breaks);

            int breakPoint;
            Map<Integer, List<String>> resultCache = new HashMap<>();
            for (int i = breaks.size() - 1; i >= 0; i--) {
                breakPoint = breaks.get(i);
                if (canBeBroken[breakPoint] && !resultCache.containsKey(breakPoint)) {
                    List<String> strs = new ArrayList<>();
                    resultCache.put(breakPoint, strs);

                    List<Integer> ints = cache.get(breakPoint);

                    for (int j = ints.size() - 1; j >= 0; j--) {
                        String prefix = s.substring(breakPoint, ints.get(j));
                        if (resultCache.containsKey(ints.get(j))) {
                            for (String tmp : resultCache.get(ints.get(j))) {
                                strs.add(prefix + " " + tmp);
                            }
                        } else if (canBeBroken[ints.get(j)]) {
                            strs.add(prefix);
                        }
                    }
                }
            }

            return resultCache.get(0);
        }

        return result;
    }

    private boolean helper(String s, Set<String> dict, int i, Map<Integer, List<Integer>> cache, boolean[] canBeBroken, int maxLength) {
        if (cache.containsKey(i)) {
            return canBeBroken[i];
        }

        if (i == s.length()) {
            canBeBroken[i] = true;
        } else {
            List<Integer> breaks = new ArrayList<>();
            cache.put(i, breaks);
            maxLength += i;
            for (int end = i + 1; end <= Math.min(maxLength, s.length()); end++) {
                if (dict.contains(s.substring(i, end))) {
                    breaks.add(end);
                    canBeBroken[i] = helper(s, dict, end, cache, canBeBroken, maxLength) || canBeBroken[i];
                }
            }
        }

        return canBeBroken[i];
    }
    
    /**
     * refer to WordBreak.wordBreak_DP_n(), change dp from boolean[] to Map<Integer, List<Integer>>, to store the previous break point 
     * 
     * 
     * @param s
     * @param dict
     * @return 
     */
    public List<String> wordBreak_DP_n(String s, Set<String> wordSet) {
        if (s == null || s.length() == 0 || wordSet == null) {
            return Collections.EMPTY_LIST;
        }

        //step 1, from left to right, break the word with DP, store the previous break points. 
        int maxLen = 0;
        for (String word : wordSet) {
            maxLen = Math.max(maxLen, word.length());
        }

        int n = s.length();
        int[][] dp = new int[n + 1][maxLen + 1]; //dp[n][maxLen] store the number of previous breakpoint,   
        dp[0][maxLen] = Integer.MAX_VALUE; //any positive number is ok 
        
        int p;
        for (int r = 1; r <= n; r++) {
            p = 0;
            for (int l = r - 1, k = Math.max(r - maxLen - 1, -1); l > k; l--) {
                if (dp[l][maxLen] > 0 && wordSet.contains(s.substring(l, r))) {
                    dp[r][p++] = l;
                    dp[r][maxLen] = p;
                }
            }
        }

        //step 2, from right to left, start from dp[n], follow the previous break point, get all possible ways 
        List<String> result = new LinkedList<>();
        
        Queue<LinkedList<Integer>> queue = new LinkedList<>();
        LinkedList<Integer> top = new LinkedList<>();
        top.add(n);
        queue.add(top);
        
        while(!queue.isEmpty()){
            top = queue.poll();
            p = top.getFirst();
            
            if(p == 0){
                StringBuilder sb = new StringBuilder();
                for(int l = 0, r = 1; r < top.size(); l = r, r++){
                    sb.append(s.substring(top.get(l), top.get(r))).append(" ");
                }
                result.add(sb.deleteCharAt(sb.length() - 1).toString());
            }else{
                for(int i = 0; i < dp[p][maxLen]; i++){
                    LinkedList<Integer> newList = new LinkedList<>();
                    newList.add(dp[p][i]);
                    newList.addAll(top);
                    queue.add(newList);
                }
            }
        }
        
        return result;
    }
    
    /**
     * refer to WordBreak.wordBreak_DP_2(), add a Map<Integer, List<String>> to store the possible sentences 
     * 
     */
    public List<String> wordBreak_DP_2(String s, Set<String> wordSet) {
        if(s == null || s.length() == 0 || wordSet == null){
            return Collections.EMPTY_LIST;
        }
        
        int maxLen = 0;
        for (String word : wordSet) {
            maxLen = Math.max(maxLen, word.length());
        }
        
        int n = s.length();
        Map<Integer, List<String>> cache = new HashMap<Integer, List<String>>();
        cache.put(0, Arrays.asList(""));
        
        List<String> tokens;   
        String postfix;
        for (int l = 0; l < n; l++) {
            if (!cache.containsKey(l) ) {
                continue;
            }

            for (int r = l + 1, end = Math.min(l + maxLen + 1, n + 1); r < end; r++) {
                postfix = s.substring(l, r);
                if (wordSet.contains(postfix)) {
                    cache.putIfAbsent(r, new ArrayList<>());
                    tokens = cache.get(r); 
                    for(String prefix : cache.get(l)){
                        tokens.add(prefix + " " + postfix);
                    }
                }
            }
        }
        
        if(cache.containsKey(n)){
            //return cache.get(n).stream().map(str -> str.substring(1, str.length())).collect(Collectors.toList()); // remove the last space
            List<String> result = new ArrayList<>();
            for(String str : cache.get(n)){
                result.add(str.substring(0, str.length() - 1));
            }
            return result;
        }
        
        return Collections.EMPTY_LIST;
    }  
    
    /** 
     * DFS + memorization search,  refer to WordBreak.wordBreak_DFS()
     */
    public List<String> wordBreak_DFS_n(String s, Set<String> wordSet) {
        if (s == null || s.length() == 0 || wordSet == null) {
            return Collections.EMPTY_LIST;
        }
        
        int maxLen = 0;
        for (String word : wordSet) {
            maxLen = Math.max(maxLen, word.length());
        }

        int n = s.length();
        int[] states = new int[n + 1]; // 0, default; 1, breakable; 2 not breakable. E.g. states[2] == 1 means substring[1..n] is breakable.
        states[n] = 1;
        Map<Integer, List<String>> cache = new HashMap<Integer, List<String>>();
        cache.put(n, Arrays.asList(""));
        
        dfs(s, 0, states, wordSet, maxLen, cache);
        if(states[0] == 1){
            //return cache.get(0).stream().map(str -> str.substring(0, str.length() - 1)).collect(Collectors.toList()); // remove the last space
            List<String> result = new ArrayList<>();
            for(String str : cache.get(0)){
                result.add(str.substring(0, str.length() - 1));
            }
            return result;
        }
        
        return Collections.EMPTY_LIST;
    }

    private void dfs(String s, int l, int[] states, Set<String> wordSet, int maxLen, Map<Integer, List<String>> cache){
        if(states[l] > 0){
            return;
        }

        List<String> tokens = new ArrayList<>();                    
        states[l] = 2;
        
        String prefix;
        for (int r = l + 1, end = Math.min(l + maxLen + 1, s.length() + 1); r < end; r++) {
            prefix = s.substring(l, r);
            if(!wordSet.contains(prefix)){
                continue;
            }

            dfs(s, r, states, wordSet, maxLen, cache);
            if(states[r] == 1){
                states[l] = 1;

                for(String postfix : cache.get(r)){
                    tokens.add(prefix + " " + postfix);
                }

                //break;   //comment out, because it need find all possible ways 
            }
        }

        cache.put(l, tokens);
    }
    
}
