package array;

import java.util.HashMap;
import java.util.Map;

/*
 * Given a string s, find the longest substring that appears more than once in s.
 * 
 * For example,
 * 
 * s = "abcabcaacb", return "abc"
 * s = "aababa", return "aba"
 * 
 */

public class LongestRepeatedSubstr
{


  /*
   * Solution:
   *  1) There are 2 substrings whose length is input.length - 1, check the duplication with HashMap<Substr, count>. 
   *  2) There are 3 substrings whose length is input.length - 2, check the duplication with HashMap<Substr, count>.
   *  3) There are 4 substrings whose length is input.length - 3, check the duplication with HashMap<Substr, count>.
   *  --- 
   *  4) Return "" if not found    
   * 
   * Time O(n^2), Space(n?), n is the length of input.
   * 
  */
  public String longestRepeatedSubstr(String input) {
	  if (input == null) {
		  return input;
	  }

	  String str;
	  for (int step = input.length() - 1; step > 0; step--) {
		  HashMap<String, Integer> map = new HashMap<String, Integer>();

		  for (int i = 0; i <= input.length() - step; i++) {
			  str = input.substring(i, i + step);

			  if (map.containsKey(str)) {
				  return str;
			  }else {
				  map.put(str, 1);
			  }
		  }

	  }

	  return "";

  }

	/*
	 * Given a string, find the length of the longest substring without repeating characters.
	 * For example, 
	 * the longest substring without repeating letters for "abcabcbb" is "abc", which the length is 3. 
	 * For"bbbbb" the longest substring is "b", with the length of 1.
	 */
	/*
	 * Solution:
	 * Hashmap<character, position of the character>
	 * 
	 * 
	 */
	public int lengthOfLongestSubstring(String s) {
		int max = 0;
		if (null == s) {
			return max;
		}

		Map<Integer, Integer> map = new HashMap<>();//map<character, index of the character>
		int currStartIndex = 0;
		int currEndIndex = 0;
		int currChar;
		for (int i = 0; i < s.length(); i++) {
			currChar = s.charAt(i) - 0;
			if (map.containsKey(currChar)) {
				max = Math.max(max, map.size());

				currEndIndex = map.get(currChar);
				while (currStartIndex <= currEndIndex) {
					map.remove(s.charAt(currStartIndex) - 0);
					currStartIndex++;
				}
			}

			map.put(currChar, i);
		}

		return Math.max(max, map.size());
	}

	public int lengthOfLongestSubstring_n(String s) {
		if (null == s) {
			return 0;
		}

		int max = -1;
		int size = s.length();
		Map<Character, Integer> indexs = new HashMap<Character, Integer>();
		int left = 0, right = 0;
		for (; right < size; right++) {
			char c = s.charAt(right);

			if (indexs.containsKey(c) && indexs.get(c) != size) {
				max = Math.max(max, right - left);

				for (int end = indexs.get(c); left <= end; left++) {
					indexs.put(s.charAt(left), size);
				}
			}

			indexs.put(c, right);
		}

		return Math.max(max, right - left);  //note
	}


	public int lengthOfLongestSubstring_n2(String s) {
		if (null == s) {
			return 0;
		}

		int size = s.length();
		int[] indexs = new int[256]; //default all are 0
		for (int i = 0; i < indexs.length; i++) {
			indexs[i] = size;
		}

		int max = -1;
		int left = 0, right = 0;
		for (; right < size; right++) {
			int c = s.charAt(right);

			if (indexs[c] != size) {
				max = Math.max(max, right - left);

				for (int end = indexs[c]; left <= end; left++) {
					indexs[s.charAt(left)] = size;
				}
			}

			indexs[c] = right;
		}

		return Math.max(max, right - left);  //note
	}

	public int lengthOfLongestSubstring_x(String s) {
		if(s == null){
			return 0;
		}

		int max = 0;
		int[] p = new int[256];

		char c;
		for(int l = 0, r = 0, n = s.length(); r < n; r++){
			c = s.charAt(r);

			l = Math.max(p[c], l);
			max = Math.max(max, r - l + 1);

			p[c] = r + 1;
		}

		return max;
	}
	
    /**
     * 
     * Given a string s, find the length of the longest substring T that contains at most k distinct characters.

		Example
		For example, Given s = "eceba", k = 3,
		
		T is "eceb" which its length is 4.
		
		Challenge
		O(n), n is the size of the string s.
     */
	public int lengthOfLongestSubstringKDistinct(String s, int k) {
		if (null == s || 1 > k || 0 == s.length()) {
			return 0;
		}

		int max = -1;
		int left = 0, right = 0;
		Map<Character, Integer> counts = new HashMap<Character, Integer>();
		int distinctNum = 0;
		char c;
		int count;
		for (int size = s.length(); right < size; right++) {
			c = s.charAt(right);

			if (!counts.containsKey(c) || 0 == counts.get(c)) {
				distinctNum++;
				counts.put(c, 1);

				for (; distinctNum > k; left++) {
					c = s.charAt(left);
					count = counts.get(c);
					counts.put(c, count - 1);

					if (1 == count) {
						distinctNum--;
					}
				}
			} else {
				counts.put(c, counts.get(c) + 1);
			}

			max = Math.max(max, right - left);
		}

		return Math.max(max + 1, right - left);
	}

	public int lengthOfLongestSubstringKDistinct_n(String s, int k) {
		if (null == s || 1 > k || 0 == s.length()) {
			return 0;
		}

		int max = -1;
		int left = 0, right = 0;
		int[] counts = new int[256];
		int distinctNum = 0;
		int c;
		for (int size = s.length(); right < size; right++) {
			c = s.charAt(right);

			if (0 == counts[c]) {
				distinctNum++;
				counts[c] = 1;

				for (; distinctNum > k; left++) {
					c = s.charAt(left);

					counts[c]--;

					if (0 == counts[c]) {
						distinctNum--;
					}
				}
			} else {
				counts[c]++;
			}

			max = Math.max(max, right - left);
		}

		return Math.max(max + 1, right - left);
	}

  public static void main(String[] args) {
	  String[] input = {null, "a", "ab", "aa", "aaa", "aaaaaa", "aababa", "abcabcaacb", "pwwkew"};

	  LongestRepeatedSubstr sv = new LongestRepeatedSubstr();

	  for (String str : input) {
		  System.out.println("\n input: " + str);
		  System.out.println("longestRepeatedSubstr: \t" + sv.longestRepeatedSubstr(str));
		  System.out.println("lengthOfLongestSubstring: \t" + sv.lengthOfLongestSubstring(str));
	  }

  }

}
