package dailyCoding.game;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * This problem was asked by LinkedIn.
 *
 * You are given a string consisting of the letters x and y, such as xyxxxyxyy. In addition, you have an operation called flip, which changes a single x to y or vice versa.
 *
 * Determine how many times you would need to apply this operation to ensure that all x's come before all y's.
 * In the preceding example, it suffices to flip the second and sixth characters, so you should return 2.
 *
 * Thought:
 *    Example xyxxxyxyy, it can flip 6th character from y to x,  or flip 7th character from x to y.
 *    The point is find the minimal flip times.
 *
 *    The final result would be all x's come before all y's. Total it's n results. To one of them, that has p x before (n - p) y,
 *    how many flip it need? It's about how many y in the left part [0, p -1] and how many x in the right part [p, n - 1]
 *
 */

public class FlipCoinsII {

    public int minFlip(char[] arr){
        if(null == arr || arr.length == 0){
            return 0;
        }

        int[] countX = new int[arr.length + 1]; //count from right to left
        for(int i = arr.length - 1; i >= 0; i--){
            countX[i] = countX[i + 1];

            if(arr[i] == 'x'){
                countX[i]++;
            }
        }

        if(countX[0] == 0 || countX[0] == arr.length){
            return 0;
        }

        int min = arr.length;
        int countY = 0;
        for(int i = 0; i < arr.length; i++){
            min = Math.min(min, countY + countX[i]);

            if(arr[i] == 'y'){
                countY++;
            }
        }

        return min;
    }


    @Test
    public void test(){
        Assert.assertEquals(0, minFlip(null));
        Assert.assertEquals(0, minFlip(new char[0]));

        Assert.assertEquals(0, minFlip("x".toCharArray()));
        Assert.assertEquals(0, minFlip("xx".toCharArray()));
        Assert.assertEquals(0, minFlip("xxx".toCharArray()));
        Assert.assertEquals(0, minFlip("y".toCharArray()));
        Assert.assertEquals(0, minFlip("yy".toCharArray()));
        Assert.assertEquals(0, minFlip("yyy".toCharArray()));

        Assert.assertEquals(2, minFlip("xyxxxyxyy".toCharArray()));
    }
}
