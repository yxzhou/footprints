package basic.cryptopal;

import org.junit.Assert;
import org.junit.Test;

/**
 * For https://cryptopals.com/sets/1/challenges/1
 *
 * Convert hex to base64
 * The hex string:
 * 49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d
 * Should produce:
 * SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t
 *
 * Prerequisite:
 *   1) encode and decode,  character <-> int / code
 *      base256, character - int, define as ascii codes: https://theasciicode.com.ar/
 *
 *      base16 ?
 *      base64 ?
 *
 *   2) convert among base10, base2, base16 / Hex, base 64
 *
 *
 */

public class ConvertHex2Base64 {

    @Test
    public void test(){
        final String hexStr = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d";
        final String base64Str = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t";
        Assert.assertEquals(base64Str, base16ToBase64(hexStr));
    }

    /**
     * base16, 2^4, 4 bits
     * base64, 2^6, 6 bits
     *
     * so the Great Common Factor is 2. the convert process is: base16 -> base2 -> base64
     */
    private String base16ToBase64(String hexStr){

        StringBuilder base64 = new StringBuilder();

        char[] base16 = hexStr.toCharArray();
        int c1, c2, c3;

        for (int i = 0; i < base16.length; i += 3) {

            c1 = Character.digit(base16[i], 16);
            c2 = Character.digit(base16[i + 1], 16);
            c3 = Character.digit(base16[i + 2], 16);

            base64.append( BASE64[(c1 << 2) + (c2 >> 2)] );
            base64.append( BASE64[((c2 & 0b0011) << 4) + c3] );
        }

        return base64.toString();
    }

    final static char[] BASE16 = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'd', 'f'
    };
    final static char[] BASE64 = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '+', '/'
    };

}
