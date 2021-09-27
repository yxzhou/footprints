package datastructure.hash.rollingHash;

import org.junit.Assert;
import org.junit.Test;

/**
 * Leetcode #1147
 *
 * Return the largest possible k such that there exists a_1, a_2, ..., a_k such that:
 *
 * Each a_i is a non-empty string;
 * Their concatenation a_1 + a_2 + ... + a_k is equal to text;
 * For all 1 <= i <= k,  a_i = a_{k+1 - i}.
 *
 *
 * Example 1:
 *
 * Input: text = "ghiabcdefhelloadamhelloabcdefghi"
 * Output: 7
 * Explanation: We can split the string on "(ghi)(abcdef)(hello)(adam)(hello)(abcdef)(ghi)".
 * Example 2:
 *
 * Input: text = "merchant"
 * Output: 1
 * Explanation: We can split the string on "(merchant)".
 * Example 3:
 *
 * Input: text = "antaprezatepzapreanta"
 * Output: 11
 * Explanation: We can split the string on "(a)(nt)(a)(pre)(za)(tpe)(za)(pre)(a)(nt)(a)".
 * Example 4:
 *
 * Input: text = "aaa"
 * Output: 3
 * Explanation: We can split the string on "(a)(a)(a)".
 *
 *
 * Constraints:
 *
 * text consists only of lowercase English characters.
 * 1 <= text.length <= 1000
 *
 *
 */

public class PalindromeDecomposition {


    @Test public void test(){

        Assert.assertEquals(1, longestDecomposition("a"));
        Assert.assertEquals(1, longestDecomposition("ab"));
        Assert.assertEquals(3, longestDecomposition("aba"));
        Assert.assertEquals(2, longestDecomposition("abab"));
        Assert.assertEquals(3, longestDecomposition("aaa"));

        Assert.assertEquals(7, longestDecomposition("ghiabcdefhelloadamhelloabcdefghi"));
        Assert.assertEquals(1, longestDecomposition("amerchant"));
        Assert.assertEquals(11, longestDecomposition("antaprezatepzapreanta"));
        Assert.assertEquals(1, longestDecomposition("upauvonsntvtnkhqdsguutcxwlzphfcqslvhse"));

    }

    public int longestDecomposition(String text) {

        int result = 0;

        long lhash = 0;
        long rhash = 0;
        long SIZE = 2166136261L;
        for(int l = 0, end = text.length(), r = end - 1; l <= r; ){
            for(int k = 0, len = r - l + 1; k < len; k++){
                lhash = (lhash << 5) + lhash + text.charAt(l + k);
                lhash %= SIZE;

                long ltmp = text.charAt(r - k);
                for(int t = 0; t < k; t++){
                    ltmp = (ltmp << 5 ) + ltmp;
                    ltmp %= SIZE;
                }
                rhash = ltmp + rhash;
                rhash %= SIZE;

                if(lhash == rhash && text.substring(l, l + k + 1).equals(text.substring(r - k, r + 1)) ){
                    result += 2;

                    if(l == r - k){
                        result--;
                        l = end; //jump out the outside loop
                    }else{
                        l = l + k + 1;
                        r = r - k - 1;

                        lhash = 0;
                        rhash = 0;
                    }

                    break; //jump out the inside loop
                }
            }

        }

        return result;
    }

    @Test public void testHash(){

        String s;
        s = "upauvonsntvtnkhqdsguutcxwlzphfcqslvhse";
        //s = "abcdefghijklmnopqrstuvwxyz";


        long lhash = 0;
        long rhash = 0;
        long ltmp = 0;

        int lc;
        int rc;

        long SIZE = 2166136261L;


        for(int i = 0, end = s.length(), r = end - 1; i < end; i++){
            lc = s.charAt(i);
            rc = s.charAt(r - i);

//            lhash = (lhash << 5) + lhash + lc;
//            lhash %= SIZE;
//
//            ltmp = rc;
//            for(int k = 0; k < i; k++){
//                 ltmp = (ltmp << 5 ) + ltmp;
//                 ltmp %= SIZE;
//            }
//            rhash = ltmp + rhash;
//            rhash %= SIZE;

            lhash = ( (lhash << 1) + lc ) % SIZE ;

            ltmp = rc;
            for(int k = 0; k < i; k++){
                 ltmp <<= 1;
                 ltmp %= SIZE;
            }
            //rhash = (ltmp + rhash) % SIZE;

            //rhash = ((rc << i) % SIZE + rhash ) % SIZE; //wrong
        }

        System.out.println(lhash);
        System.out.println(rhash);
    }


}
