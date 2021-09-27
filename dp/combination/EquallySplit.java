package fgafa.dp.splitArray;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Given a multiset of integers, return whether it can be partitioned into two subsets whose sums are the same.

 For example, given the multiset {15, 5, 20, 10, 35, 15, 10}, it would return true, since we can split it up into {15, 5, 10, 15, 10} and {20, 35}, which both add up to 55.

 Given the multiset {15, 5, 20, 10, 35}, it would return false, since we can't split it up into two subsets that add up to the same sum.
 *
 *
 * tags: facebook
 */

/**
 *  Leetcode #416
 *
 * Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.
 *
 * Note:
 *
 * Each of the array element will not exceed 100.
 * The array size will not exceed 200.
 *
 *
 * Example 1:
 *
 * Input: [1, 5, 11, 5]
 *
 * Output: true
 *
 * Explanation: The array can be partitioned as [1, 5, 5] and [11].
 *
 *
 * Example 2:
 *
 * Input: [1, 2, 3, 5]
 *
 * Output: false
 *
 * Explanation: The array cannot be partitioned into equal sum subsets.
 *
 */

public class EquallySplit {

    public boolean canEquallySplit(int[] nums){

        if(null == nums || nums.length < 1){
            return false;
        }

        int sum = 0; //
        for(int num : nums){
            sum += num;
        }

        if( 1 == (sum & 1) ){
            return false;
        }

        sum >>= 1;

        boolean[] reachable = new boolean[sum + 1]; //default all are 0
        reachable[0] = true;

        for(int num : nums){
            for(int i = sum - num; i >= 0; i-- ){
                reachable[i + num] = reachable[i];
            }
        }

        return reachable[sum];
    }


    @Test public void test(){

        Assert.assertTrue(canEquallySplit(new int[]{15, 5, 20, 10, 35, 15, 10}));

        Assert.assertFalse(canEquallySplit(new int[]{15, 5, 20, 10, 35}));

    }

}
