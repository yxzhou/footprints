package fgafa.sorting;

import fgafa.util.Misc;

/**
 * 
 * Q1, Given an unsorted array nums, reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3].... 
 * 
 * For example, 
 *   given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].
 *   
 * Solutions:
 *  1, sort and swap,  [3, 5, 2, 1, 6, 4] = > [1, 2, 3, 4, 5, 6] = > [1, 3, 2, 5, 4, 6], 
 *     time O(nlogn) space O(1)
 *  2, find the min and max, quick sort until that a midValue split the array to two part, 
 *     the first part includs n/2 + 1 and smaller than midValue, the second part alll are not smaller than the midValue
 *     time O(n), space O(1)
 *  3, greedy  -- wrong !!
 *     time O(n), space O(1)
 */

/**
 * 
 * Q2, Given an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....

    Example:
    (1) Given nums = {1, 2, 2, 3}, one possible answer is {2,3,1,2}
    (2) Given nums = {1, 1, 2, 2, 3}, one possible answer is {2, 3, 1, 2, 1}
    (3) Given nums = [1, 1, 2, 2, 3, 3], one possible answer is [2, 3, 1, 3, 1, 2].
    
    Note:
    You may assume all input has valid answer.
    
    Follow Up:
    Can you do it in O(n) time and/or in-place with O(1) extra space?
 *
 * Solutions:
 *  1, sort and swap,  [3, 5, 2, 1, 6, 4] = > [1, 2, 3, 4, 5, 6] = > [1, 3, 2, 5, 4, 6], 
 *     time O(nlogn) space O(1)
 *  2, quick selection, find the (n/2)th smallest, split the array to two part, 
 *     the first part includes n/2 + 1 and smaller than midValue, the second part all are not smaller than the midValue
 *     time O(n), space O(1)
 */


public class WiggleSort {

    public boolean isWiggleSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (((i & 1) == 1 && nums[i] <= nums[i - 1])
                        || ((i & 1) == 0 && nums[i] >= nums[i - 1])) {
                return false;
            }
        }

        return true;
    }
    
    /*greedy  WRONG !!*/
    public void wiggleSort(int[] nums) {
        if(null == nums || nums.length < 2){
            return;
        }
        
        for(int i = 1; i < nums.length; i++){
            if((i & 1) == 1){
                if(nums[i] < nums[i - 1]){
                    swap(nums, i, i - 1);
                }
            }else{
                if(nums[i] > nums[i - 1]){
                    swap(nums, i, i - 1);
                }
            }
        }
    }
    
    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    
    
    public void wiggleSortII(int[] nums) {
        if(null == nums || nums.length < 2){
            return;
        }
        
        int n = nums.length;
        int left = (n - 1) / 2;
        int right = n - 1;
        
        getKth_quicksort(nums, left);
        //Arrays.sort(nums);
        //System.out.println(String.format(" %s", Misc.array2String(nums)));
            
        int[] tmp = new int[n];
         
        for (int i = 0; i < n; i++) {
            if ((i & 1) == 0) {
                tmp[i] = nums[left];
                left--;
            } else {
                tmp[i] = nums[right];
                right--;
            }
        }

        System.arraycopy(tmp, 0, nums, 0, n);
    }
    
    private void getKth_quicksort(int[] nums, int k){
        java.util.Random random = new java.util.Random();
 
        int start = 0;
        int end = nums.length - 1;
        
        while(start < end){
            int pivot = start + random.nextInt(end - start + 1);
            swap(nums, pivot, end);
            
            int smaller = start;
            int equal = start;
            int bigger = end - 1;
            while(equal <= bigger){
                if(nums[equal] == nums[end]){
                    equal++;
                }else if(nums[equal] < nums[end]){
                    swap(nums, smaller, equal);
                    smaller++;
                    equal++;
                }else{
                    swap(nums, equal, bigger);
                    bigger--;
                }
            }
            swap(nums, equal, end);
        
            if(smaller <= k && k < equal){
                return;
            }else if(equal < k){
                start = equal;
            }else{ // k < smaller
                end = smaller - 1;
            }
        }
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

        for(int[] nums : input){
            System.out.println(String.format("\n %s", Misc.array2String(nums)));
            
            sv.getKth_quicksort(nums, (nums.length - 1) / 2);
            System.out.println(String.format(" %s", Misc.array2String(nums)));
            
            sv.wiggleSortII(nums);
            System.out.println(String.format(" %s", Misc.array2String(nums)));
        }
        
    }
}
