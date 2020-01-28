package fgafa.slideWindow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


/**
 * similar with stringmatching.ShortestExcerpt.java
 * 
 * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
 * 
 * For example,
 * S = "ADOBECODEBANC"
 * T = "ABC"Rus
 * Minimum window is "BANC".
 * 
 * Note:
 * If there is no such window in S that covers all characters in T, return the empty string "".
 * 
 * If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.
 * 
 * Followup:
 *   In the above example, how about to find the minimum window that includes 'A', 'B' and 'C' in order
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
	
    public String minWindow_xx(String source, String target) {
        if(null == source || 0 == source.length() || null == target){
            return "";
        }
        
        Map<Character, Integer> toFind = new HashMap<>();
        for(char c: target.toCharArray()){
            count(toFind, c);
        }
        
        Map<Character, Integer> found = new HashMap<>();
        Queue<Integer> currWindow = new LinkedList<>();
        int count = 0;
        int minWindowSize = Integer.MAX_VALUE; 
        int minWindowStart = 0;
        for(int right = 0, left = 0; right < source.length(); right++){
            //increase the curr window from the right side
            char currChar = source.charAt(right);
            if(!toFind.containsKey(currChar)){
                continue;
            }
            
            count(found, currChar);
            currWindow.add(right);
            if(found.get(currChar) == toFind.get(currChar)){
                count++;
            }
            
            if(count == toFind.size()){
                //decrease the curr window from the left side
                while( found.get(currChar) >= toFind.get(currChar)){
                    left = currWindow.poll();
                    currChar = source.charAt(left);
                    
                    found.put(currChar, found.get(currChar) - 1);
                }
                
                //check if the curr window is min
                int currSize = right - left + 1;
                if(currSize < minWindowSize){
                    minWindowSize = currSize;
                    minWindowStart = left;
                }
                
                count--;
            }
        }
        
        return minWindowSize > source.length() ? "" : source.substring(minWindowStart, minWindowStart + minWindowSize);
    }
    
    private void count(Map<Character, Integer> counts, char key){
        if(!counts.containsKey(key)){
            counts.put(key, 1);
        }else{
            counts.put(key, counts.get(key) + 1);
        }
    }
    
    
    /**
     * 
     * @param source
     * @param target
     * @return "" if not found.
     */
    public String minWindow_x3(String source, String target) {
        if(null == source || null == target || 0 == target.length() || source.length() < target.length()){
            return "";
        }
        
        Map<Character, Integer> toFind = new HashMap<>();
        for(char c : target.toCharArray()){
            if(toFind.containsKey(c)){
                toFind.put(c, toFind.get(c) + 1);
            }else{
                toFind.put(c, 1);
            }
        }
        
        int left = 0;
        int right = 0;
        final String initWindow = source + " ";
        String minWindow = initWindow;
        int isMeet = toFind.size();
        char curr;
        while(right < source.length()){
            for( ; right < source.length() & isMeet > 0; right++){
                curr = source.charAt(right);
                if(toFind.containsKey(curr)){
                    toFind.put(curr, toFind.get(curr) - 1);
                    
                    if(toFind.get(curr) == 0){
                        isMeet--;
                    }
                }
            }
            
            for( ; left < right && isMeet == 0; left++){
                if(right == source.length()){
                    if(minWindow.length() > right - left){
                        minWindow = source.substring(left, right);
                    }
                }else{
                    if(minWindow.length() > right - left + 1){
                        minWindow = source.substring(left, right + 1);
                    }
                }
                
                curr = source.charAt(left);
                if(toFind.containsKey(curr)){
                    toFind.put(curr, toFind.get(curr) + 1);
                    
                    if(toFind.get(curr) == 1){
                        isMeet++;
                    }
                }
            }
        }
        
        return minWindow == initWindow? "" : minWindow;
    }

    public String minWindow_x4(String s, String t) {
        if(null == s || null == t || t.isEmpty() || s.length() < t.length()){
            return "";
        }

        Map<Character, Integer> need = new HashMap<>();
        for(char c : t.toCharArray()){
            need.put(c, need.containsKey(c)? need.get(c) + 1 : 1);
        }

        int status = need.size();
        int[] min = new int[]{-1, s.length()}; // the min slide window. m[0], the start point; m[1], the size
        char c;
        for(int l = 0, r = 0; r < s.length(); r++){
            c = s.charAt(r);
            if( need.containsKey(c) ){
                need.put(c, need.get(c) - 1);

                if(need.get(c) == 0){
                    status--;
                }
            }

            if(status == 0){
                while(status == 0){
                    c = s.charAt(l++);
                    if(need.containsKey(c)){
                        need.put(c, need.get(c) + 1);

                        if(need.get(c) > 0){
                            status++;
                        }
                    }
                }

                if(min[1] > r - l){
                    min[0] = l - 1;
                    min[1] = r - l;
                }
            }
        }

        return min[0] == -1? "" : s.substring(min[0], min[0] + min[1] + 2);
    }

  /**
   * @param args
   */
  public static void main(String[] args) {
      String[] s = {"a", "a", "ab", "a", "aa", "bba", "bbaa", "bdab", "ADOBECODEBANC", "ADOBECODEBANCA"};
      String[] t = {"a", "b", "a", "ab", "aa", "ab", "aba", "ab", "ABC", "ABCA"};

      MinimumWindowSubstring sv = new MinimumWindowSubstring();
      for (int i = 7; i < s.length; i++) {

          System.out.println("\nInput:" + s[i] + " " + t[i]);

          System.out.println("minWindow: " + sv.minWindow_n(s[i], t[i]));

          System.out.println("minWindow_x: " + sv.minWindow_x(s[i], t[i]));
          System.out.println("minWindow_x2: " + sv.minWindow_x2(s[i], t[i]));
          System.out.println("minWindow_xx: " + sv.minWindow_xx(s[i], t[i]));
          System.out.println("minWindow_x3: " + sv.minWindow_x3(s[i], t[i]));

          System.out.println("minWindow_x3: " + sv.minWindow_x4(s[i], t[i]));
      }

  }

}
