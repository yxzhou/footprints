package array;

import java.util.Arrays;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1099
 * 
 * Given an array of integers, write a function to determine whether the array could become non-decreasing by modifying
 * at most 1 element.
 *
 * Example 1, Input: [10, 5, 7], Output: true, 
 *  Explanation: since we can modify the 10 into a 1 to make the array non-decreasing.
 *
 * Example 2, Input: [10, 5, 1], Output: false
 *  Explanation: since we can't modify any one element to get a non-decreasing array.
 *
 * Tags: fb
 *
 * Further: MinimumSwap
 * 
 */

public class NondecreasingArray {
    /**
     * @param nums: an array
     * @return if it could become non-decreasing by modifying at most 1 element
     */
    public boolean oneChangeEnough(int[] nums) {
        if (null == nums ) {
            return true;
        }

        int n = nums.length;

        for (int i = 1; i < n; i++) {
            //when it found nums[i - 1] > nums[i], there is 2 options, one is change nums[i] to nums[i - 1], the other 
            //is change nums[i - 1] to nums[i].
            
            if (nums[i - 1] > nums[i]) {
                int tmp = nums[i - 1];
                nums[i - 1] = nums[i];
                if (isAscendant(nums, i - 1)) {
                    return true;
                }

                nums[i] = tmp;
                if (isAscendant(nums, i + 1)) {
                    return true;
                }

                return false;
            }
        }

        return true;
    }

    private boolean isAscendant(int[] nums, int i) {
        int n = nums.length;
        i = Math.max(i, 1);
        for (; i < n; i++) {
            if (nums[i - 1] > nums[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param nums: an array
     * @return if it could become non-decreasing by modifying at most 1 element
     */
    public boolean oneChangeEnough_n(int[] nums) {
        int n = nums.length;
        int count = 0;
        
        for (int i = 1; i < n; i++) {
            if (nums[i - 1] > nums[i]) {
                count++;
                
                if(count > 1){
                    return false;
                }
                
                /**
                 * when it found nums[i - 1] > nums[i], there is 2 options, one is changing nums[i] to nums[i - 1], the
                 * other is changing nums[i - 1] to nums[i]
                 * if nums[i - 2] > nums[i], the only way is changing nums[i] to nums[i - 1], or it's better to change 
                 * nums[i - 1] instead of nums[i], because it will not affect the on-going checking
                 * 
                 */
                if( i > 1 && nums[i - 2] > nums[i] ){
                    nums[i] = nums[i - 1];
                }
            }
        }

        return true;
    }
    
    
    public static void main(String[] args) {
        NondecreasingArray sv = new NondecreasingArray();

        int[][] inputs = {
            {10, 5, 7},
            {10, 5, 1},
            {3, 4, 2, 3},
            {3, 4, 3, 2},
            {3, 4, 2, 5},
            {3, 4, 2, 4},
            {1, 4, 2, 4}
                
                
        };

        boolean[] expects = {
            true,
            false,
            false,
            false,
            true,
            true,
            true
            
        };

        for (int i = 0; i < expects.length; i++) {
            System.out.println(Misc.array2String(inputs[i]));

            Assert.assertEquals(expects[i], sv.oneChangeEnough(Arrays.copyOf(inputs[i], inputs[i].length)));
           
            
            Assert.assertEquals(expects[i], sv.oneChangeEnough_n(Arrays.copyOf(inputs[i], inputs[i].length)));
        }
    }

}
