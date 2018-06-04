package fgafa.game.wordbreak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 */
public class WordBreakII {
    public List<String> wordBreak(String s, Set<String> dict) {
		List<String> result = new ArrayList<>();
		if(null == s){
			return result;
		}
		
		//prepare
		int maxLength = 0;
		for(String tmp : dict){
			maxLength = Math.max(maxLength, tmp.length());
		}
		
		//from top to bottom
		Map<Integer, List<Integer>> cache = new HashMap<>();
		boolean[] canBeBroken = new boolean[s.length()+1]; //default all are false
		wordBreak(s, dict, 0, cache, canBeBroken, maxLength);
		
		//from bottom to top
		if(canBeBroken[0]){
			List<Integer> breaks = new ArrayList<>(cache.keySet());
			Collections.sort(breaks);
			
			int breakPoint;
			Map<Integer, List<String>> resultCache = new HashMap<>();
			for(int i = breaks.size() - 1; i>=0; i--){
				breakPoint = breaks.get(i);
				if(canBeBroken[breakPoint] && !resultCache.containsKey(breakPoint)){
					List<String> strs = new ArrayList<>();
					resultCache.put(breakPoint, strs);
					
					List<Integer> ints = cache.get(breakPoint);

					for(int j=ints.size() - 1; j>=0; j--){
						String prefix = s.substring(breakPoint, ints.get(j));
						if(resultCache.containsKey(ints.get(j))){
							for(String tmp : resultCache.get(ints.get(j))){
								strs.add(prefix + " " + tmp);
							}
						}else if(canBeBroken[ints.get(j)]){
							strs.add(prefix);
						}
					}
				}
			}
			
			return resultCache.get(0);
		}
		
		return result;
	}
	
	private boolean wordBreak(String s, Set<String> dict, int i, Map<Integer, List<Integer>> cache, boolean[] canBeBroken, int maxLength){
		if(cache.containsKey(i)){
			return canBeBroken[i];
		}

		if(i == s.length()){
			canBeBroken[i] = true;
		}else{
			List<Integer> breaks = new ArrayList<>();
			cache.put(i, breaks);
			maxLength +=i;
			for(int end = i+1; end <= Math.min(maxLength, s.length()); end ++){
				if(dict.contains(s.substring(i, end))){
					breaks.add(end);
					canBeBroken[i] = wordBreak(s, dict, end, cache, canBeBroken, maxLength) || canBeBroken[i];
				}
			}			
		}

		return canBeBroken[i];
	}
}
