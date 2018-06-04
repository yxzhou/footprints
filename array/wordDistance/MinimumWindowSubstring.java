package fgafa.array.wordDistance;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


/**
 * similar with stringmatching.ShortestExcerpt.java
 * 
 * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
 * 
 * For example,
 * S = "ADOBECODEBANC"
 * T = "ABC"
 * Minimum window is "BANC".
 * 
 * Note:
 * If there is no such window in S that covers all characters in T, return the empty string "".
 * 
 * If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.
 * 
 * @author yxzhou
 *
 */

public class MinimumWindowSubstring
{
	public String minWindow_x(String S, String T) {
		if (null == S || null == T){
			return "";
		}

		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = 0; i < T.length(); i++) {
			if (map.containsKey(T.charAt(i))) {
				map.put(T.charAt(i), map.get(T.charAt(i)) + 1);
			} else {
				map.put(T.charAt(i), 1);
			}
		}

		int left = 0;
		int count = 0;
		int minLen = Integer.MAX_VALUE;
		int minStart = 0;
		for (int right = 0; right < S.length(); right++) {
			if (map.containsKey(S.charAt(right))) {
				map.put(S.charAt(right), map.get(S.charAt(right)) - 1);
				if (map.get(S.charAt(right)) >= 0) {
					count++;
				}

				while (count == T.length()) {
					if (right - left + 1 < minLen) {
						minLen = right - left + 1;
						minStart = left;
					}
					if (map.containsKey(S.charAt(left))) {
						map.put(S.charAt(left), map.get(S.charAt(left)) + 1);
						if (map.get(S.charAt(left)) > 0) {
							count--;
						}
					}
					
					left++;
				}
			}
		}

		return minLen > S.length() ? "" :S.substring(minStart, minStart + minLen);
	}

	public String minWindow_x2(String S, String T) {
		int needToFind[] = new int[256]; // default all are 0
		for (int i = 0; i < T.length(); i++){
			needToFind[T.charAt(i)]++;
		}

		int hasFound[] = new int[256]; //default all are 0
		int minWindowBegin = 0;
		int minWindowLen = Integer.MAX_VALUE;
		int count = 0;
		for (int begin = 0, end = 0; end < S.length(); end++) {
			// skip characters not in T
			if (needToFind[S.charAt(end)] > 0) {
				hasFound[S.charAt(end)]++;
				if (hasFound[S.charAt(end)] <= needToFind[S.charAt(end)]){
					count++;
				}

				// if window constraint is satisfied
				if (count == T.length()) {
					// advance begin index as far right as possible,
					// stop when advancing breaks window constraint.
					while (needToFind[S.charAt(begin)] == 0
							|| hasFound[S.charAt(begin)] > needToFind[S.charAt(begin)]) {
						if (hasFound[S.charAt(begin)] > needToFind[S.charAt(begin)]){
							hasFound[S.charAt(begin)]--;
						}

						begin++;
					}

					// update minWindow if a minimum length is met
					int windowLen = end - begin + 1;
					if (windowLen < minWindowLen) {
						minWindowBegin = begin;
						minWindowLen = windowLen;
					}
				}
			}
		}

		return minWindowLen > S.length() ? "" : S.substring(minWindowBegin, minWindowBegin + minWindowLen);
	}
	
    public String minWindow_n(String source, String target) {
        //check
        if(null == source || null == target){
            return "";
        }
        
        
        String result = "";
        int min = Integer.MAX_VALUE;
        
        Map<Character, Integer> targetCounts = new HashMap<>();
        Map<Character, Integer> currWindowCounts = new HashMap<>();
        int count = 0;
        Integer tmp;
        for(char c : target.toCharArray()){
            tmp = targetCounts.get(c);
            
            if(tmp == null){
                count++;
                tmp = 0;
                
                currWindowCounts.put(c, 0);
            }
           
            targetCounts.put(c, tmp + 1 );
        }
        
        int left = 0;
        int right = 0;

        //int currWindowCount = 0;
        char c;
        while(right < source.length()){
            if( 0 < count){
                c = source.charAt(right);
                right++;
                
                if(currWindowCounts.containsKey(c)){
                    currWindowCounts.put(c, currWindowCounts.get(c) + 1);
                    
                    if(currWindowCounts.get(c) == targetCounts.get(c)){
                        count--;
                    }
                }
            }
            
            while( 0 == count){
                if(min > right - left){
                    min = right - left;
                    result = source.substring(left, right);
                }
                
                c = source.charAt(left);
                left++;
                
                if(currWindowCounts.containsKey(c)){
                    
                    if(currWindowCounts.get(c) == targetCounts.get(c)){
                        count++;
                    }
                    
                    currWindowCounts.put(c, currWindowCounts.get(c) - 1);
                }
            }
        }
        
        return result;
    }
	
  /**
   * @param args
   */
  public static void main(String[] args) {
    String[] s = {"a","a","ab","a", "aa", "bba","bbaa", "bdab", "ADOBECODEBANC", "ADOBECODEBANCA"};
    String[] t = {"a","b","a","ab", "aa", "ab","aba", "ab", "ABC", "ABCA"};

    MinimumWindowSubstring sv = new MinimumWindowSubstring();
    for(int i=0; i< s.length; i++){
      
      System.out.println("\nInput:"+s[i]+" "+t[i]);
      
      System.out.println("minWindow: " + sv.minWindow_n(s[i], t[i]));
      
      System.out.println("minWindow_x: " + sv.minWindow_x(s[i], t[i]));
      System.out.println("minWindow_x2: " + sv.minWindow_x2(s[i], t[i]));
    }

  }

}
