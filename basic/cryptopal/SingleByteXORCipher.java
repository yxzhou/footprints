package fgafa.basic.cryptopal;

import junit.framework.Assert;
import org.junit.Test;

/**
 *  From https://cryptopals.com/sets/1/challenges/3
 This problem was asked by Apple.

 You are given a hexadecimal-encoded string that has been XOR'd against a single char.

 Decrypt the message. For example, given the string:

 7a575e5e5d12455d405e561254405d5f1276535b5e4b12715d565b5c551262405d505e575f
 You should be able to decrypt it and get:

 Hello world from Daily Coding Problem
 *
 *
 *
 */

public class SingleByteXORCipher {



    @Test
    public void test(){
        System.out.println((int)'z');
        System.out.println((int)'Z');
        System.out.println(Character.getNumericValue('A'));
        System.out.println(Character.digit('a', 16));

        //System.out.println(  (byte) ( (Character.digit('7', 16) << 4) + Character.digit('a', 16)));

        final String hexBytes = "7a575e5e5d12455d405e561254405d5f1276535b5e4b12715d565b5c551262405d505e575f";
        Assert.assertEquals("Hello world from Daily Coding Problem", decrypt(hexBytes));

    }

    private String decrypt(String hexBytes){

        int[] bytes = convert(hexBytes);

        //System.out.println("after convert: " + String.valueOf(bytes).toString());

        //try to find out the single char
        int b;
        float rating;
        float max = 0;
        int key = 0;
        for(int c = 0; c < 256; c++){

            rating = 0;
            for(int j = 0; j < bytes.length; j++){
                //decrypt
                b = bytes[j] ^ c;

                //score
                if( b >= 97 && b <= 122){   //'a' is 97, 'z' is 122
                    rating += characterFrequencies[b - 97];
                }else if (b >= 65 && b <= 90){   //'A' is 65, 'Z' is 90
                    rating += characterFrequencies[b - 65];
                }else if (b == ' ') { //space
                    rating += characterFrequencies[26];
                } //else score += 0;

            }

            //find the best score
            if(max < rating){
                max = rating;
                key = c;
            }
        }

        StringBuilder result = new StringBuilder();
        for(int j = 0; j < bytes.length; j++) {
            result.append((char)(bytes[j] ^ key));
        }
        return result.toString();
    }


    /**
     * From https://en.wikipedia.org/wiki/Letter_frequency with the exception of ' ', which I estimated.
     */
    final static float[] characterFrequencies = {
            .08167f, .01492f, .02782f, .04253f, .12702f, .02228f, .02015f,  // a, b, c, d, e, f, g
            .06094f, .06094f, .00153f, .00772f, .04025f, .02406f, .06749f,  // h, i, j, k, l, m, n
            .07507f, .01929f, .00095f, .05987f, .06327f, .09056f,           // o, p, q,  r, s, t
            .02758f, .00978f, .02360f, .00150f, .01974f, .00074f,           // u, v, w,  x, y, z
            .13000f                                                         // ' ' space
    };

    /**
     * convert Hex String to byte Array
     */
    private int[] convert(String hexBytes) {
        int len = hexBytes.length();
        int[] result = new int[len / 2];
        for (int i = 0; i < len; i += 2) {
            result[i/2] = (Character.digit(hexBytes.charAt(i), 16) << 4)
                    + Character.digit(hexBytes.charAt(i + 1), 16);
        }

        return result;
    }

}
