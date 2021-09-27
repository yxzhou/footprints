package dailyCoding;

import util.Misc;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Given a number represented by a list of digits, find the next greater permutation of a number, in terms of lexicographic ordering. If there is not greater permutation possible, return the permutation with the lowest value/ordering.

 For example, the list [1,2,3] should return [1,3,2]. The list [1,3,2] should return [2,1,3]. The list [3,2,1] should return [1,2,3].

 Can you perform the operation without allocating extra memory (disregarding the input memory)?
 *
 * tags: Palantir
 *
 *
 * Solution:
 *    [1, 2, 3, 4]
 *  ->[1, 2, 4, 3]
 *  ->[1, 3, 2, 4]
 *  ->[1, 3, 4, 2]
 *  ->[1, 4, 2, 3]
 *
 *  step 1: from right (n-1) to left (0), find the first down ( nums[i] < nums[i + 1] )
 *  stip 2: if it's nums[i],
 *             from the right to left, find the first one that is bigger than nums[i], let's say it's nums[j]
 *             swap(nums, i, j)
 *             reverse(nums, i+1, n-1)
 *
 *             example: [3, 5, 4, 2, 1]  -> [4, 1, 2, 3, 5]
 *                 the first down is 3 < 5,
 *                 the first one that is bigger than 3 is 4.
 *                 after swap 3 and 4,  it's [4, 5, 3, 2, 1]
 *                 after reverse, it's [4, 1, 2, 3, 5]
 *          if not found nums[i],  example [4, 3, 2, 1], reverse(nums, 0, n-1)
 *
 *
 */

public class PermutationNext {

    //do it in place
    public void next(int[] nums){
        if(null == nums || nums.length < 2){
            return;
        }

        //from right to left, find the first down ( nums[i] > nums[i - 1] )
        int i = nums.length - 2;
        while(i >= 0 && nums[i] >= nums[i + 1]){
            i--;
        }

        if(i == -1){
            reverse(nums, 0, nums.length - 1);
            return;
        }

        int j = nums.length - 1;
        while(nums[j] <= nums[i]){
            j--;
        }

        swap(nums, i, j);
        reverse(nums, i + 1, nums.length - 1);
    }

    private void reverse(int[] nums, int from, int to){
        while(from  < to){
            swap(nums, from, to);
            from++;
            to--;
        }
    }

    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    @Test public void test(){
        //Assert.assertArrayEquals(new int[]{1, 2, 3, 4}, next(new int{4, 3, 2, 1}));

        int n = 4;

        int[] input = new int[n];
        int factorial = 1;
        for(int i = 0, j = 0; i < n; i = j){
            j = i + 1;

            input[i] = j;
            factorial *= j;
        }

        System.out.println("---start--");
        System.out.println(Misc.array2String(input));

        for(int i = 0; i < factorial; i++){
            next(input);
            System.out.println(Misc.array2String(input));
        }
    }
}
