package greedy;

import java.util.Arrays;

import util.Misc;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * This problem was asked by Flipkart.
 *
 * Starting from 0 on a number line, you would like to make a series of jumps that lead to the integer N.
 *
 * On the ith jump, you may move exactly i places to the left or right.
 *
 * Find a path with the fewest number of jumps required to get from 0 to N.
 *
 */

public class JumpGameII2 {
    public int minJump(int[] jumps){
        if(null == jumps || jumps.length <= 1){
            return 0;
        }

        int count = 0;
        int i = 0;
        for(int end = 0, nextEnd = 0; i <= end; i++){
            if(nextEnd < i + jumps[i]){
                nextEnd = i + jumps[i];
            }

            if( i == end ){
                if(end < nextEnd) {
                    end = nextEnd;
                    count++;

                    if(end >= jumps.length - 1){
                        return count;
                    }
                } else {
                    return -1;
                }
            }
        }

        return -1;
    }

    public int minJump_n(int[] jumps){
        if(null == jumps || jumps.length <= 1){
            return 0;
        }

        int count = 0;
        int i = 0;
        for(int end = 0, nextEnd = 0; i <= end; i++){
            nextEnd = Math.max(nextEnd, i + jumps[i]);

            if( i == end && end < nextEnd) {
                end = nextEnd;
                count++;

                if(end >= jumps.length - 1){
                    return count;
                }
            }
        }

        return -1;
    }

    @Test
    public void test(){
        int[][] arr = { { 1 }, { 1, 2 }, { 1, 2, 3 }, { 2, 3, 1, 1, 4 },
                { 2, 3, 1, 2, 0, 1 }, { 3, 2, 1, 0, 4 }, { 0, 1 } };

        int[] expects = {0, 1, 2, 2, 3, -1, -1};

        for (int i = 0; i < arr.length; i++) {
            System.out.println("\n -" + i
                    + "-The non-negative integers array is: "
                    + Arrays.toString(arr[i]));

            Assert.assertEquals(expects[i], minJump(arr[i]));

            Assert.assertEquals(expects[i], minJump_n(arr[i]));
        }
    }

}
