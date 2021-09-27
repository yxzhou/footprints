package datastructure.hash.rollingHash;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Leetcode #1316
 * Return the number of distinct non-empty substrings of text that can be written as the concatenation of some string with itself (i.e. it can be written as a + a where a is some string).
 *
 *
 *
 * Example 1:
 *
 * Input: text = "abcabcabc"
 * Output: 3
 * Explanation: The 3 substrings are "abcabc", "bcabca" and "cabcab".
 * Example 2:
 *
 * Input: text = "leetcodeleetcode"
 * Output: 2
 * Explanation: The 2 substrings are "ee" and "leetcodeleetcode".
 *
 *
 * Constraints:
 *
 * 1 <= text.length <= 2000
 * text has only lowercase English letters.
 *
 */

public class EchoSubstring {

    @Test public void test(){

        String text = "abcabcabc";
        int len = text.length();
        char[] arr = text.toCharArray();

        long[] hc = new long[len + 1]; //hashcode

        for(int i = 0; i < len; i++){
            hc[i + 1] = ((hc[i] << 1) % MOD + arr[i]) % MOD;
        }

        System.out.println("===" + cal(0, 3, hc));
        System.out.println("===" + cal(3, 6, hc));
        System.out.println("===" + cal(6, 9, hc));

        Assert.assertEquals(cal(0, 3, hc), cal(3, 6, hc) );


//        Assert.assertEquals(1, distinctEchoSubstrings("aa"));
//        Assert.assertEquals(1, distinctEchoSubstrings("aaa"));
//        Assert.assertEquals(1, distinctEchoSubstrings("abab"));

        Assert.assertEquals(3, distinctEchoSubstrings("abcabcabc"));
        Assert.assertEquals(2, distinctEchoSubstrings("leetcodeleetcode"));
    }

    private long cal(int l, int r, long[] hc){
//        return (hc[r] - (hc[l] << (r-l)) % MOD + MOD) % MOD;  //wrong


        long tmp = hc[l];
        for(int k = 0; k < (r - l); k++){
            tmp <<= 1;
            tmp %= MOD;
        }
        return (hc[r] - tmp + MOD) % MOD;

    }

    /******  ******  *****/

    public int distinctEchoSubstrings_bruteforce(String text) {
        int len = text.length();
        char[] arr = text.toCharArray();

        Set<String> set = new HashSet<>();

        for(int w = len/ 2; w > 0; w--){
            for(int l = 0, r = w; r + w <= len; l++, r++){
                if(Arrays.equals(arr, l, r, arr, r, r+w)){
                    set.add(text.substring(l, r));
                }
            }
        }

        return set.size();
    }

    /******  ******  *****/
    long MOD = 1315423911L;
    int BASE = 27;

    public int distinctEchoSubstrings(String text) {
        Set<String> set = new HashSet<>();

        int n = text.length();

        long[] codes = new long[n + 1];
        long[] pows = new long[n + 1];
        pows[0] = 1;
        for(int i = 1; i <= n; i++){
            codes[i] = ( (codes[i - 1] * BASE) + text.charAt(i - 1) ) % MOD;
            pows[i] = pows[i - 1] * BASE % MOD;
        }

        String first;
        String second;
        for(int w = 1, half = n / 2; w <= half; w++){
            for(int l = 0, r = l + w; r + w <= n; l++, r++){
                if( getHash(l, r, codes, pows) == getHash(r, r + w, codes, pows) ){
                    first = text.substring(l, r);
                    second = text.substring(r, r + w);

                    if(first.equals(second)){
                        set.add(first);
                    }
                }
            }
        }

        return set.size();
    }

    long getHash(int l, int r, long[] hash, long[] pow) {
        return (hash[r] - hash[l] * pow[r - l] % MOD + MOD) % MOD;
    }

}
