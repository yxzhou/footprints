package dailyCoding;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Given an array of integers, write a function to determine whether the array could become non-decreasing by modifying at most 1 element.

 For example, given the array [10, 5, 7], you should return true, since we can modify the 10 into a 1 to make the array non-decreasing.

 Given the array [10, 5, 1], you should return false, since we can't modify any one element to get a non-decreasing array.
 *
 * Tags: fb
 *
 */

public class OneChangeEnough {

    public boolean oneChangeEnough(int[] nums){
        if(null == nums || nums.length < 3){
            return true;
        }

        int pre = Integer.MIN_VALUE;
        boolean hasOneChange = false;
        for(int curr : nums){
            if(pre > curr){
                if(hasOneChange){
                    return false;
                }else {
                    hasOneChange = true;
                }
            }

            pre = curr;
        }

        return true;
    }

    @Test public void test(){

        Assert.assertTrue(oneChangeEnough(new int[]{10, 5, 7}));
        Assert.assertFalse(oneChangeEnough(new int[]{10, 5, 1}));

    }

}
