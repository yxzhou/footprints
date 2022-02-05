package sorting;

import java.util.Arrays;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/508/
 * 
 * Q1, Given an unsorted array nums, reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3].... 
 * 
 * For example, 
 *   given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].
 *   
 * Solutions:
 *  1, sort and swap,  [3, 5, 2, 1, 6, 4] = > [1, 2, 3, 4, 5, 6] = > [1, 3, 2, 5, 4, 6], 
 *     time O(nlogn) space O(1)
 * 
 *  2, find the min and max, quick sort until that a midValue split the array to two part, 
 *     the first part includes n/2 + 1 and smaller than midValue, the second part all are not smaller than the midValue
 *     time O(n), space O(1)
 * 
 *  3, greedy  -- wrong !!
 *     time O(n), space O(1)
 */


public class WiggleSort {

    public boolean isWiggleSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (((i & 1) == 1 && nums[i] <= nums[i - 1]) || ((i & 1) == 0 && nums[i] >= nums[i - 1])) {
                return false;
            }
        }

        return true;
    }
    
    /**
     * Time O( n * logn ),  Space O(1)
     * 
     * @param nums 
     */
    public void wiggleSort(int[] nums) {
        if(null == nums || nums.length < 2){
            return;
        }
        
        Arrays.sort(nums);

        for(int i = 1; i < nums.length; i++){
            if( ((i & 1) == 1 && nums[i - 1] > nums[i]) || ((i & 1) == 0 && nums[i - 1] < nums[i]) ){
                swap(nums, i - 1, i);
            } 
        }
    }
    
    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
   
    
    public static void main(String[] args){
        WiggleSort sv = new WiggleSort();
        
        int[][] input = {
                    {1,1,2},
                    {1,2,3}, //{1,2,3}
                    {1,1,2,3},
                    {1,2,2,3},  // => 2 3 1 2
                    {1,2,3,3},
                    {1,1,2,2,3},
                    {1,1,2,2,3,3},
                    {1,1,1,2,2,3}
                    
        };
        
        int[] arr;

        for(int[] nums : input){
            System.out.println(String.format("\n %s", Misc.array2String(nums)));
            
//            sv.getKth_quicksort(nums, (nums.length - 1) / 2);
//            System.out.println(String.format(" %s", Misc.array2String(nums)));

            arr = Arrays.copyOf(nums, nums.length);
            sv.wiggleSort(arr);
            System.out.println(String.format(" %s", Misc.array2String(arr)));
            
        }
        
    }
}
    