package todo.goo;

import java.util.HashMap;
import java.util.Map;

/**
 * http://www.jiuzhang.com/solution/unique-substrings-in-wraparound-string/
 *
 * Problem:
 *   Define s as a string, "abcdef---xyzabcdef--xyz--", endless and in such a loop
 *   Input a string p, get how many substring in p.
 *
 * E.g.
 *   1. input p = 'a',  output 1,   ('a')
 *   2. input p = 'cac', output 2,  ('a' and 'c')
 *   3, input p = 'zab', output 6,  ('zab', 'za', 'z', 'ab', 'a', 'b')
 *
 * Note
 *   s and p only includes the lower case character from a to z
 *   the length of p maybe larger than 10k.
 */

public class UniqueSubstring {

    public int findSubstringInWraproundString(String p) {

        Map<Character, Integer> indexes = new HashMap<>(26);
        char c ='a';
        for( int i = 0; i < 26; i++, c++){
            indexes.put(c, i);
;       }

        /**
         * Define dp[i] as how many substrings that ends with chars[i]
         */
        int[] dp = new int[26];

        int count = 1;
        for(int i = 0; i < p.length(); i++){
            if(i > 0 && (p.charAt(i) - p.charAt(i - 1) == 1 || (p.charAt(i) == 'a' && p.charAt(i - 1) == 'z'))){
                count++;
            }else{
                count = 1;
            }

            dp[indexes.get(p.charAt(i))] = Math.max(dp[indexes.get(p.charAt(i))], count);
        }

        int total = 0;
        for(int n : dp){
            total += n;
        }
        return total;
    }

}
