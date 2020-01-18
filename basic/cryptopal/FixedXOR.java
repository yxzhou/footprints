package fgafa.basic.cryptopal;

import org.junit.Assert;
import org.junit.Test;

/**
 * From https://cryptopals.com/sets/1/challenges/2
 *
 * Fixed XOR
 * Write a function that takes two equal-length buffers and produces their XOR combination.
 *
 * Feed the hex strings:
 * 1c0111001f010100061a024b53535009181c and
 * 686974207468652062756c6c277320657965
 *
 * ... should produce:
 * 746865206b696420646f6e277420706c6179
 *
 *
 */

public class FixedXOR {

    @Test
    public void test(){
        final String hexStr1 = "1c0111001f010100061a024b53535009181c";
        final String hexStr2 = "686974207468652062756c6c277320657965";

        final String hexStr3 = "746865206b696420646f6e277420706c6179";

        Assert.assertEquals(hexStr3, fixedXOR(hexStr1, hexStr2));
    }

    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    private String fixedXOR(String hexStr1, String hexStr2){
        assert hexStr1 != null && hexStr2 != null && hexStr1.length() == hexStr2.length();

        StringBuilder sb = new StringBuilder(hexStr1.length());

        for(int i = 0; i < hexStr1.length(); i++){
            sb.append(HEX_ARRAY[Character.digit(hexStr1.charAt(i), 16) ^ Character.digit(hexStr2.charAt(i), 16)]);
            //sb.append(Integer.toHexString(Character.digit(hexStr1.charAt(i), 16) ^ Character.digit(hexStr2.charAt(i), 16)));
        }

        return sb.toString();

    }
}
