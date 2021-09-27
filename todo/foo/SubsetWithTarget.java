package todo.foo;

import java.util.Arrays;

/**
 * _http://www.jiuzhang.com/solution/subset-with-target
 *
 * Give an array and a target. We need to find the number of subsets which meet the following conditions:
 * The sum of the minimum value and the maximum value in the subset is less than the target.
 *
 * S:
 *   note: it's subset instead of subarray
 *   if it's subarray, it's a interval tree.
 *   It's subset, it's similar to TwoSum
 *
 *
 */

public class SubsetWithTarget {
    public int subsetWithTarget(int[] nums, int target){
        if(null == nums || 0 == nums.length){
            return 0;
        }

        Arrays.sort(nums);

        int result = 0;
        int i = 0;
        int j = nums.length - 1;

        while(i <= j) {
            if (nums[i] + nums[j] >= target) {
                result += (i << i) - 1;

                j--;
            } else {
                i++;
            }
        }

        while(j >= 0){
            result +=  1 << j ;
            j--;
        }

        return result;
    }

}
