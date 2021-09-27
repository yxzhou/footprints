package math;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Given a positive integer n, find the smallest number of squared integers which sum to n.

 For example, given n = 13, return 2 since 13 = 32 + 22 = 9 + 4.

 Given n = 27, return 3 since 27 = 32 + 32 + 32 = 9 + 9 + 9.
 *
 */

public class SquareSum {
    /**
     *  Time O(n * n),  Space O(n)
     */
    public int smallestOfSquare(int n){
        if(n < 1){
            return 0;
        }

        int squareRoot = (int)Math.ceil(Math.sqrt(n));
        if(squareRoot * squareRoot == n){
            return 1;
        }

        int[] counter = new int[n + 1];
        for(int i = 1; i < squareRoot; i++){
            counter[i * i] = 1;
        }

        for(int i = 2; i <= n; i++){
            if(counter[i] == 1){
                continue;
            }
            counter[i] = n; //init value
            for(int j = 1, half = (i >> 1); j <= half; j++){
                counter[i] = Math.min(counter[i], counter[j] + counter[i - j]);
            }
        }

        return counter[n];
    }

    @Test
    public void test(){
        Assert.assertEquals(1, smallestOfSquare(1));
        Assert.assertEquals(2, smallestOfSquare(2));
        Assert.assertEquals(3, smallestOfSquare(3));
        Assert.assertEquals(1, smallestOfSquare(4));
        Assert.assertEquals(2, smallestOfSquare(5));
        Assert.assertEquals(3, smallestOfSquare(6));
        Assert.assertEquals(4, smallestOfSquare(7));
        Assert.assertEquals(2, smallestOfSquare(8));
        Assert.assertEquals(1, smallestOfSquare(9));
        Assert.assertEquals(2, smallestOfSquare(10));
        Assert.assertEquals(3, smallestOfSquare(11));
        Assert.assertEquals(3, smallestOfSquare(12));
        Assert.assertEquals(2, smallestOfSquare(13));
        Assert.assertEquals(3, smallestOfSquare(14));
        Assert.assertEquals(4, smallestOfSquare(15));
        Assert.assertEquals(1, smallestOfSquare(16));

        Assert.assertEquals(3, smallestOfSquare(27));
    }

}
