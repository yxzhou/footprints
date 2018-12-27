package fgafa.dailyCoding;

import org.junit.Assert;
import org.junit.Test;

/**
 * Given a list of integers and a number K, return which contiguous elements of the list sum to K.

 For example, if the list is [1, 2, 3, 4, 5] and K is 9, then it should return [2, 3, 4].
 *
 * tags: lyft
 */

public class ContiguousElementsSum {

    public int[] contiguousElementsSum(int[] nums, int target){
        if(null == nums || nums.length == 0){
            return null; //or {-1, -1}
        }

        long[] sums = new long[nums.length]; //default all are 0
        sums[0] = nums[0];
        for(int i = 1; i < nums.length; i++){
            sums[i] = sums[i - 1] + nums[i];

            if(sums[i] == target){
                return new int[]{0, i};
            }
        }

        for(int r = 1; r < nums.length; r++){
            for(int l = 0; l < r; l++){
                if(sums[r] - sums[l] == target){
                    return new int[]{l + 1, r};
                }
            }
        }

        return null;
    }

    public int[] contiguousElementsSum_n(int[] nums, int target){
        if(null == nums || nums.length == 0){
            return null; //or {-1, -1}
        }

        long[] sums = new long[nums.length]; //default all are 0
        for(int r = 0; r < nums.length; r++){
            for(int l = 0; l <= r; l++){
                sums[l] += nums[r];

                if(sums[l] == target){
                    return new int[]{l, r};
                }
            }
        }

        return null;
    }

    @Test public void test(){
        Assert.assertArrayEquals(new int[]{1,3}, contiguousElementsSum(new int[]{1, 2, 3, 4, 5}, 9));
        Assert.assertArrayEquals(new int[]{1,3}, contiguousElementsSum_n(new int[]{1, 2, 3, 4, 5}, 9));
    }

}
