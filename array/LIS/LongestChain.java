package array.LIS;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Leetcode #646
 *
 * You are given n pairs of numbers. In every pair, the first number is always smaller than the second number.
 *
 * Now, we define a pair (c, d) can follow another pair (a, b) if and only if b < c. Chain of pairs can be formed in this fashion.
 *
 * Given a set of pairs, find the length longest chain which can be formed. You needn't use up all the given pairs. You can select pairs in any order.
 *
 * Example 1:
 * Input: [[1,2], [2,3], [3,4]]
 * Output: 2
 * Explanation: The longest chain is [1,2] -> [3,4]
 *
 * Note:
 * The number of given pairs will be in the range [1, 1000].
 *
 * Thoughts:
 * when input, [[a1, a2], [b1, b2]]
 *
 * If the chain is: [a1, a2] -> [b1, b2],  it need: a2 < b1
 *   from input,  a1 < a2 and b1 < b2
 *
 *
 *   It can be sorted by a or b
 *
 *
 */

public class LongestChain {

    public int findLongestChain(int[][] pairs) {
        if(pairs == null || pairs.length == 0){
            return 0;
        }

        Arrays.sort(pairs, (p1, p2) ->  (p1[0] == p2[0]) ? p1[1] - p2[1] : p1[0] - p2[0] );

        int count = 0;
        int h = pairs[0][1];

        for(int[] p : pairs){
            if(h < p[0]){
                count++;
                h = p[1];
            }else if( h > p[1]){
                h = p[1];
            }
        }

        return count;
    }

    public int findLongestChain_x(int[][] pairs) {
        if(pairs == null || pairs.length == 0){
            return 0;
        }

        Arrays.sort(pairs, (p1, p2) -> p1[1] - p2[1] );

        int count = 0;
        int h = Integer.MIN_VALUE;

        for(int[] p : pairs){
            if(h < p[0]){
                count++;
                h = p[1];
            }
        }

        return count;
    }


    @Test public void test(){

        int[][] pairs = {{-6,9},{1,6},{8,10},{-1,4},{-6,-2},{-9,8},{-5,3},{0,3}};

        Assert.assertEquals(3, findLongestChain(pairs));

    }

}
