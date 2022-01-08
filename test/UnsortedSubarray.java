package test;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * LeetCode 581
 * _https://www.lintcode.com/problem/1157
 *
 * Given an integer array, you need to find one continuous subarray that if you only sort this subarray in ascending
 * order, then the whole array will be sorted in ascending order, too.
 *
 * You need to find the shortest such subarray and output its length.
 *
 * Example 1:
 * Input: [2, 6, 4, 8, 10, 9, 15]
 * Output: 5
 * Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted in ascending order.
 * 
 * Note:
 * Then length of the input array is in range [1, 10,000].
 * The input array may contain duplicates, so ascending order here means <=.
 *
 */

public class UnsortedSubarray {

    
    /**
     * Time O(n)  Space O(n)
     * 
     * @param nums: an array
     * @return the shortest subarray's length
     */
    public int findUnsortedSubarray(int[] nums) {
        if(nums == null || nums.length < 2){
            return 0;
        }

        int n = nums.length;

        int[] maxs = new int[n];
        maxs[0] = nums[0];
        for(int i = 1; i < n; i++){
            maxs[i] = Math.max(nums[i], maxs[i - 1]);
        }

        int[] mins = new int[n];
        mins[n - 1] = nums[n - 1];
        for(int i = n - 2; i >= 0; i--){
            mins[i] = Math.min(nums[i], mins[i + 1]);
        }

        int count = n;
        for(int i = 0; i < n; i++){
            if(mins[i] == nums[i]){
                count--;
            }else{
                break;
            }
        }
        for(int i = n - 1; i >= 0; i--){
            if(maxs[i] == nums[i]){
                count--;
            }else{
                break;
            }
        }

        return count < 0 ? 0 : count; 
    }
    
    /**
     * Time O( n* logn )  Space O(n)
     * 
     * @param nums: an array
     * @return the shortest subarray's length
     */
    public int findUnsortedSubarray_sorting(int[] nums) {
        if(nums == null){
            return 0;
        }

        int n = nums.length;
        int[] sorted = new int[n];
        System.arraycopy(nums, 0, sorted, 0, n);
        Arrays.sort(sorted);
        
        int left = 0;
        int right = n - 1;
        while(left < n){
            if(sorted[left] == nums[left]){
                left++;
            }else{
                break;
            }
        }
        while(right >= 0){
            if(sorted[right] == nums[right]){
                right--;
            }else{
                break;
            }
        }
        
        return Math.max(right - left + 1, 0); 
    }
    
    public int findUnsortedSubarray_2(int[] nums) {
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
