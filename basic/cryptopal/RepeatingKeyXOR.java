package fgafa.basic.cryptopal;

import org.junit.Assert;
import org.junit.Test;

/**
 * From https://cryptopals.com/sets/1/challenges/5
 *
 * Here is the opening stanza of an important work of the English language:
 * Burning 'em, if you ain't quick and nimble
 * I go crazy when I hear a cymbal
 *
 * Encrypt it, under the key "ICE", using repeating-key XOR.
 *
 * In repeating-key XOR, you'll sequentially apply each byte of the key;
 * the first byte of plaintext will be XOR'd against I, the next C, the next E, then I again for the 4th byte, and so on.
 *
 * It should come out to:
 * 0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f
 *
 * Encrypt a bunch of stuff using your repeating-key XOR function. Encrypt your mail. Encrypt your password file. Your .sig file.
 * Get a feel for it. I promise, we aren't wasting your time with this.
 *
 */

public class RepeatingKeyXOR {

    @Test
    public void test(){
        final String origin = "Burning 'em, if you ain't quick and nimble\n" +
                "I go crazy when I hear a cymbal";

        final String expected = "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272" +
                "a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f";

        final String key = "ICE";

        byte[] bytes = key.getBytes();

        Assert.assertEquals(expected, repeatingKeyXOR(origin, key));

    }


    private String repeatingKeyXOR(String origin, String key){
        assert key != null && !key.trim().isEmpty() && origin != null && !origin.isEmpty();

        char[] keyArray = key.toCharArray();

        StringBuilder result = new StringBuilder();
        String s;
        for(int i = 0; i < origin.length(); i++){
//            if(origin.charAt(i) == '\n'){
//                System.out.println(result.toString());
//
//                result.append('\n');
//                continue;
//            }

            s = Integer.toHexString(origin.charAt(i) ^ keyArray[i % 3]);

            if(s.length() == 1){
                result.append(0);
            }

            result.append(s);
        }

        return result.toString();
    }
}
