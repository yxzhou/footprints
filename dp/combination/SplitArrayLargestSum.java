package dp.combination;

import org.junit.Assert;
import org.junit.Test;

/**
 * Leetcode #410
 *
 * Given an array which consists of non-negative integers and an integer m, you can split the array into m non-empty continuous subarrays. Write an algorithm to minimize the largest sum among these m subarrays.
 *
 * Note:
 * If n is the length of array, assume the following constraints are satisfied:
 *
 * 1 ≤ n ≤ 1000
 * 1 ≤ m ≤ min(50, n)
 * Examples:
 *
 * Input:
 * nums = [7,2,5,10,8]
 * m = 2
 *
 * Output:
 * 18
 *
 * Explanation:
 * There are four ways to split nums into two subarrays.
 * The best way is to split it into [7,2,5] and [10,8],
 * where the largest sum among the two subarrays is only 18.
 *
 */

public class SplitArrayLargestSum {

    public int splitArray(int[] nums, int m) {
        if(nums == null || m < 1 || nums.length < m){
            return -1;
        }

        long sum = 0;
        int max = 0;
        for(int n : nums){
            sum += n;
            max = Math.max(max, n);
        }

        int low = max;
        int high = (int)sum;

        int mid;
        int x;
        while(low < high){
            mid = low + (high - low) / 2;

            x = split(nums, mid);

            if(x > m){
                low = mid + 1;
            }else{
                high = mid;
            }
        }

        return low;
    }

    /**
     *
     * @param nums  non-negative integers
     * @param sum   the max sum of continuous subarrays
     * @return how many subarrays can be splited
     */
    private int split(int[] nums, int sum){
        int count = 0;

        int subsum = 0;
        for(int n : nums){
            if(subsum + n > sum){
                count++;
                subsum = n;
            }else{
                subsum += n;
            }
        }

        return count + 1;
    }


    @Test public void test(){
        Assert.assertEquals(18, splitArray(new int[]{7,2,5,10,8}, 2));

        Assert.assertEquals(2147483647, splitArray(new int[]{1,2147483646},1));
    }
}
