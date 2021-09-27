package test;

import org.junit.Assert;
import org.junit.Test;

/**
 * LeetCode 581
 *
 * Given an integer array, you need to find one continuous subarray that if you only sort this subarray in ascending order, then the whole array will be sorted in ascending order, too.
 *
 * You need to find the shortest such subarray and output its length.
 *
 * Example 1:
 * Input: [2, 6, 4, 8, 10, 9, 15]
 * Output: 5
 * Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted in ascending order.
 * Note:
 * Then length of the input array is in range [1, 10,000].
 * The input array may contain duplicates, so ascending order here means <=.
 *
 */

public class UnsortedSubarray {

    public int findUnsortedSubarray(int[] nums) {
        int n = nums.length;

        int l = 0;
        boolean flag = true;
        for(int i = 1; i < n; i++){
            if(l == -1){
                break;
            }

            if(nums[l] <= nums[i] && flag){
                l = i;
            }else if( nums[l] > nums[i]){
                do{
                    l--;
                }while(l >= 0 && nums[l] > nums[i]);

                if(flag){
                    flag = false;
                }
            }
        }

        if(l == n - 1){
            return 0;
        }

        int r = n - 1;
        flag = true;
        for(int i = n - 2; i >= 0; i--){
            if(r == n){
                break;
            }

            if(nums[r] >= nums[i] && flag){
                r = i;
            }else if( nums[r] < nums[i]){
                do{
                    r++;
                }while(r < n && nums[r] < nums[i]);

                if(flag){
                    flag = false;
                }
            }
        }

        return r - l - 1;
    }

    @Test
    public void test(){

        Assert.assertEquals(3, findUnsortedSubarray(new int[]{2,3,1,4,5}));

    }

}
