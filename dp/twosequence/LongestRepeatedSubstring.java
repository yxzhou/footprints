package dp.twosequence;

import java.util.HashMap;
import java.util.Map;

/**
 * _https://leetcode.com/problems/longest-duplicate-substring/
 * 
 * Given a string s, consider all duplicated substrings: (contiguous) substrings of s that occur 2 or more times. The
 * occurrences may overlap.
 *
 * Return any duplicated substring that has the longest possible length. If s does not have a duplicated substring, the
 * answer is "".
 * 
 * Example 1: Input: s = "banana" Output: "ana"
 * Example 2: Input: s = "abcd" Output: ""
 *  
 * Constraints:
 *   2 <= s.length <= 3 * 104
 *   s consists of lowercase English letters.
 * 
 * 
 */

public class LongestRepeatedSubstring{

    /**
     * Solution: define n is the length of input string
     * 1) There are 2 substrings whose length is n - 1, check the duplication with HashMap<Substr, count>. 
     * 2) There are 3 substrings whose length is n - 2, check the duplication with HashMap<Substr, count>. 
     * 3) There are 4 substrings whose length is n - 3, check the duplication with HashMap<Substr, count>.
     * --- 4) Return "" if not found
     *
     * Time O(n^2), Space(n?), n is the length of input.
     *
     */
    public String longestRepeatedSubstr(String s) {
        if (s == null) {
            return s;
        }

        String str;
        HashMap<String, Integer> map;
        for (int n = s.length(), w = n - 1; w > 0; w--) {
            map = new HashMap<>();

            for (int i = 0; i <= n - w; i++) {
                str = s.substring(i, i + w);

                if (map.containsKey(str)) {
                    return str;
                } else {
                    map.put(str, 1);
                }
            }
        }

        return "";
    }


    public static void main(String[] args) {
        String[] input = {null, "a", "ab", "aa", "aaa", "aaaaaa", "aababa", "abcabcaacb", "pwwkew"};

        LongestRepeatedSubstring sv = new LongestRepeatedSubstring();

        for (String str : input) {
            System.out.println("\n input: " + str);
            
            System.out.println("longestRepeatedSubstr: \t" + sv.longestRepeatedSubstr(str));
        }

    }

}
