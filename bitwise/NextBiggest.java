package bitwise;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * This problem was asked by Facebook.
 *
 * Given an integer n, find the next biggest integer with the same number of 1-bits on.
 *
 * For example, given the number 6 (0110 in binary), return 9 (1001).
 *
 * Thoughts:
 *  1)  to the binary string of the integer n, from right to left, find the first 01,  change it to 10, change the right part to smallest number.
 *
 */

public class NextBiggest {

    /**
     *
     *
     * @return Integer.MAX_VALUE, if there is no next biggest number.
     *
     */
    public int nextBiggest(int n){

        //to the binary string of the integer n, from right to left, find the first 01
        int count = 0;
        int count1 = 0;

        int t;
        while( n != 0 && (t = n & 0b11) != 0b01 ) {
            if(t > 1){
                n >>>= 1;
                count++;

                count1 += t & 1 ;
            }else{ // t == 0b00
                n >>>= 2;
                count += 2;
            }
        }

        if(count == 15){
            return Integer.MAX_VALUE;
        }

        //change the end 01 to 10
        n ^= 0b11;
        n <<= count;

        if (count1 > 0) {
            n |= ((1 << count1) - 1);
        }

        return n;
    }


    @Test
    public void test(){
        Assert.assertEquals(0b1001, nextBiggest(0b0110));

        Assert.assertEquals(0b10000, nextBiggest(0b1000));

        Assert.assertEquals(0b10111, nextBiggest(0b1111));

        Assert.assertEquals(0xbfff, nextBiggest(0x7fff));
        Assert.assertEquals(0x8003, nextBiggest(0x7000));
        Assert.assertEquals(0x57ff, nextBiggest(0x4fff));
        Assert.assertEquals(0x507f, nextBiggest(0x4ff0));

        Assert.assertEquals(Integer.MAX_VALUE, nextBiggest(0xffff));
        Assert.assertEquals(Integer.MAX_VALUE, nextBiggest(0xfff8));
    }
}
