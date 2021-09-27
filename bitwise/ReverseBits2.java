package bitwise;

import org.junit.Test;

/**
 * Given a 32-bit integer, return the number with its bits reversed.

 For example,
   given the binary number 0000 0000 1111 0000 1111 0000 1111 0000,
                    return 1111 1111 0000 1111 0000 1111 0000 1111.
 *
 * Tags: facebook
 *
 * Solution:
 *   1) xor,  0 ^ 1 = 1, 1 ^ 1 = 0
 *
 */

public class ReverseBits2 {

    public int bitsReverse(int n){
        long mask = 1;
        mask <<= 32;
        mask--;

        return (n ^ (int)mask);
    }

    @Test
    public void test(){

//        long mask = 1;
//        mask <<= 32;
//        mask--;
//        System.out.println(Integer.toBinaryString((int)mask));

        int[] input = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, Integer.MAX_VALUE, Integer.MIN_VALUE};

        for(int i = 0; i < input.length; i++){
            int n = input[i];
            int r = bitsReverse(n);
            System.out.println(Integer.toBinaryString(n));
            System.out.println(Integer.toBinaryString(r));
            System.out.println(Integer.toBinaryString(~n));
            System.out.println();
        }

//        mask
//        1111,1111,1111,1111,1111,1111,1111,1111

//        0111,1111,1111,1111,1111,1111,1111,1111
//        1000,0000,0000,0000,0000,0000,0000,0000
//
//        1000,0000,0000,0000,0000,0000,0000,0000
//         111,1111,1111,1111,1111,1111,1111,1111


    }
}
