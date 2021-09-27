package bitwise;

import org.junit.Test;

/**
 * Given a 32-bit integer, return the number with its bits reversed.

 For example,
 given the binary number 0000 0000 0000 0000 0000 0000 0000 1010,
 return                  0000 0000 0000 0000 0000 0000 0000 0101.
 *
 * Tags: facebook
 *
 * Solution:
 *   1)
 *
 */

public class ReverseBits3 {

    public static int bitsReverse(int n){

        if(n == Integer.MIN_VALUE){
            return Integer.MAX_VALUE;
        }

        int r = 0;
        boolean flag = false;
        for( int base = (1 << 30); base > 0; base >>>= 1) {
            if( (n & base) > 0 ){
                flag = true;
            } else if(flag){
                r |= base;
            }
        }

        return r;
    }

    @Test
    public void test(){

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
