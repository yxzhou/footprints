package fgafa.dailyCoding.easy;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * This problem was asked by Squarespace.
 *
 * Write a function, add_subtract, which alternately adds and subtracts curried arguments. Here are some sample operations:
 *
 * add_subtract(7) -> 7
 *
 * add_subtract(1)(2)(3) -> 1 + 2 - 3 -> 0
 *
 * add_subtract(-5)(10)(3)(9) -> -5 + 10 - 3 + 9 -> 11
 *
 */

public class AddAndSubtract {

    public int addAndSubtract(int[] nums){
        if(null == nums || 0 == nums.length){
            throw new IllegalArgumentException(" Input can't be null or empty. ");
        }

        int result = nums[0];

        for(int i = 1; i < nums.length; i++){
            if((i & 1) == 0) {
                result -= nums[i];
            }else{
                result += nums[i];
            }
        }

        return result;
    }


    @Test
    public void test(){
        Assert.assertEquals(7, addAndSubtract(new int[]{7}) );
        Assert.assertEquals(0, addAndSubtract(new int[]{1, 2, 3}) );
        Assert.assertEquals(11, addAndSubtract(new int[]{-5, 10, 3, 9}) );

    }
}
