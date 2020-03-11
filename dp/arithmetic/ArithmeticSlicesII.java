package fgafa.dp.arithmetic;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Leetcode #446
 *
 * A sequence of numbers is called arithmetic if it consists of at least three elements and if the difference between any two consecutive elements is the same.
 *
 * For example, these are arithmetic sequences:
 * 1, 3, 5, 7, 9
 * 7, 7, 7, 7
 * 3, -1, -5, -9
 *
 * The following sequence is not arithmetic.
 * 1, 1, 2, 5, 7
 *
 * A zero-indexed array A consisting of N numbers is given. A subsequence slice of that array is any sequence of integers (P0, P1, ..., Pk) such that 0 ≤ P0 < P1 < ... < Pk < N.
 *
 * A subsequence slice (P0, P1, ..., Pk) of array A is called arithmetic if the sequence A[P0], A[P1], ..., A[Pk-1], A[Pk] is arithmetic. In particular, this means that k ≥ 2.
 *
 * The function should return the number of arithmetic subsequence slices in the array A.
 *
 * The input contains N integers. Every integer is in the range of -2^31 and 2^31-1 and 0 ≤ N ≤ 1000. The output is guaranteed to be less than 2^31-1.
 *
 *
 * Example:
 * Input: [2, 4, 6, 8, 10]
 * Output: 7
 *
 * Explanation:
 * All arithmetic subsequence slices are:
 * [2,4,6]
 * [4,6,8]
 * [6,8,10]
 * [2,4,6,8]
 * [4,6,8,10]
 * [2,4,6,8,10]
 * [2,6,10]
 *
 * Input: [0,1,2,2,2]
 * Output: 4
 *
 */

public class ArithmeticSlicesII {

    public int numberOfArithmeticSlices(int[] A) {
        if(A == null || A.length < 3){
            return 0;
        }

        int n = A.length;
        int result = 0;

        Map<Long, Integer>[] counts = new HashMap[n]; // Map<diff, counts>

        long diff;
        int x;
        List<Integer> list;
        for(int r = 0; r < n; r++ ){
            counts[r] = new HashMap<>();
            for(int l = r - 1; l >= 0; l--){
                diff = (long)A[r] - A[l];

                x = counts[l].getOrDefault(diff, 0);

                counts[r].put(diff, counts[r].getOrDefault(diff, 0) + x + 1);

                result += x;
            }
        }

        return result;
    }


    @Test public void test(){
        Assert.assertEquals(0 , numberOfArithmeticSlices(new int[]{0,2000000000,-294967296}));

        Assert.assertEquals(1 , numberOfArithmeticSlices(new int[]{1, 1, 1}));
        Assert.assertEquals(5 , numberOfArithmeticSlices(new int[]{1, 1, 1, 1}));
        Assert.assertEquals(16 , numberOfArithmeticSlices(new int[]{1, 1, 1, 1, 1}));

        Assert.assertEquals(1 , numberOfArithmeticSlices(new int[]{-4, -2, 0}));
        Assert.assertEquals(2 , numberOfArithmeticSlices(new int[]{-4, -2, 0, 0}));
        Assert.assertEquals(6 , numberOfArithmeticSlices(new int[]{-4, -2, 0, 0, 2}));
        Assert.assertEquals(14 , numberOfArithmeticSlices(new int[]{-4, -2, 0, 0, 2, 4}));
    }
}
