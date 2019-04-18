package fgafa.sorting;

import org.junit.Assert;

import java.util.Random;

/**
 *
 * Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.
 *
 * Example:
 * Input: [3,2,1,5,6,4] and k = 2
 * Output: 5
 *
 * Input: [3,2,3,1,2,4,5,5,6] and k = 4
 * Output: 4
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ array's length.
 *
 */

public class FindKthLargest {

    public int find2ndLargest(int[] nums){
        //check
        if(null == nums || 2 > nums.length){
            return -1;
        }
        
        int[] largests = new int[2];
        largests[0] = nums[0] > nums[1] ? 0 : 1;
        largests[1] = nums[0] > nums[1] ? 1 : 0;
        for(int i = 2, end = nums.length; i < end; i++){
            
            if(nums[i] > nums[largests[0]]){
                largests[1] = largests[0];
                largests[0] = i;
            }else if(nums[i] > nums[largests[1]]){
                largests[1] = i;
            }
            
        }
        
        return largests[1];
    }

    public int findKthLargest(int[] nums, int k) {
        if(null == nums || k < 1 || k > nums.length){
            return -1;
        }

        Random random = new Random();
        k--;

        for(int left = 0, right = nums.length - 1, p = 0; left <= right; ){
            p = partition(nums, left, right, left + random.nextInt(right - left + 1));

            if(p == k){
                return nums[k];
            }else if(p < k){
                left = p + 1;
            }else{
                right = p - 1;
            }
        }

        //this should not happen
        return -1;
    }

    private int partition(int[] nums, int left, int right, int i){
        swap(nums, left, i);

        int pivot = nums[left];
        i = left + 1;

        while(i <= right){
            if(nums[i] >= pivot){
                i++;
            }else{
                swap(nums, right, i);
                right--;
            }
        }

        swap(nums, left, i - 1);
        return i - 1;
    }

    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        System.out.println("==start==");

        FindKthLargest sv = new FindKthLargest();

        // Input: [3,2,1,5,6,4] and k = 2 * Output: 5
        // Input: [3,2,3,1,2,4,5,5,6] and k = 4   * Output: 4

        Assert.assertEquals(5, sv.findKthLargest(new int[]{3,2,1,5,6,4}, 2));
        Assert.assertEquals(4, sv.findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4));

        //System.out.println(sv.findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4));
    }

}
