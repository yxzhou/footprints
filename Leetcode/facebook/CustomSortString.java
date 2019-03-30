package fgafa.Leetcode.facebook;

import org.junit.Test;

import java.util.Arrays;

/**
 *
 * S and T are strings composed of lowercase letters. In S, no letter occurs more than once.

 S was sorted in some custom order previously. We want to permute the characters of T so that they match the order that S was sorted. More specifically, if x occurs before y in S, then x should occur before y in the returned string.

 Return any permutation of T (as a string) that satisfies this property.

 Example :
 Input:
 S = "cba"
 T = "abcd"
 Output: "cbad"
 Explanation:
 "a", "b", "c" appear in S, so the order of "a", "b", "c" should be "c", "b", and "a".
 Since "d" does not appear in S, it can be at any position in T. "dcba", "cdba", "cbda" are also valid outputs.


 Note:

 S has length at most 26, and no character is repeated in S.
 T has length at most 200.
 S and T consist of lowercase letters only.
 *
 */

public class CustomSortString {
    public String customSortString(String S, String T) {
        if(null == S || null == T){
            return null;
        }

        int base = 97; //'a'
        int[] counts = new int[26];
        for(char t : T.toCharArray()){
            counts[t - base]++;
        }

        char[] result = new char[T.length()];
        int i = 0;
        int j = 0;
        int c;
        for(char s : S.toCharArray() ){
            c = s - base;

            if(counts[c] == 0){
                continue;
            }

            j = i + counts[c];
            Arrays.fill(result, i, j, s);
            i = j;
            counts[c] = 0;
        }

        for(c = 0; c < 26; c++){
            if(counts[c] == 0){
                continue;
            }

            j = i + counts[c];
            Arrays.fill(result, i, j, (char)(c + base));
            i = j;
        }

        return new String(result);
    }


    @Test public void test(){
        int base = 'a';

        System.out.println(base);

        System.out.println(customSortString("cba", "abcd"));
    }
}
