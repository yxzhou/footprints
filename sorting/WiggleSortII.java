/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sorting;

import java.util.Arrays;
import java.util.Random;

/**
 * _https://www.lintcode.com/problem/507
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
 * 
 *  2, quick selection, find the median, split the array to three parts, the first part is smaller than median, the second 
 *     part equals, the third part is bigger. 
 *     to deal with the duplicated median. example [4,5,5,6], merge [5, 4] and [6,5] to [5, 6, 4, 5]
 * 
 *     time O(n), space O(1)
 * 
 */
public class WiggleSortII {
    
    /**
     * @param nums: A list of integers
     * @return: nothing
     */
    public void wiggleSort(int[] nums) {
        if(nums == null || nums.length < 2){
            return;
        }

        int n = nums.length;
        
        kth(nums, 0, n - 1, n/2);

        int[] copy = Arrays.copyOf(nums, n);    
        for(int i = 0, l = (n - 1)/2, r = n - 1; i < n; i++){
            if((i & 1) == 1 && r >= 0){
                nums[i] = copy[r--];
            }else{
                nums[i] = copy[l--];
            }
        }
    }

    final Random random = new Random();

    private int kth(int[] nums, int left, int right, int k){
        int pivot;
        int l, r;
        while(left < right){
            pivot = nums[random.nextInt(right - left + 1) + left];
            
            l = left;
            r = right;
            for(int i = l; i <= r;  i++){
                if( nums[i] < pivot ){
                    swap(nums, l, i);
                    l++;
                }else if( nums[i] > pivot ){
                    swap(nums, i, r);
                    r--;
                    i--;
                }
            }

            if( l <= k && k <= r ){
                break;
            }else if(r < k){
                left = r + 1; 
            }else { //l > k
                right = l - 1;
            }
        }

        return nums[k];
    }

    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    
    
    /**
     * Time O( n ),  Space O(n)
     * 
     * @param nums 
     */
    public void wiggleSortII(int[] nums) {
        if(null == nums || nums.length < 2){
            return;
        }
        
        int n = nums.length;
        int left = (n - 1) / 2;
        int right = n - 1;
        
        getKth_quickSelect(nums, left);
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
    
    private void getKth_quickSelect(int[] nums, int k){
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
        
        int[][] inputs = {
            {1, 3, 2, 2, 3, 1},
            {4,5,5,6},
            {1,5,1,1,6,4}, 
            
            {1,1,2},
            {1,2,3}, //{1,2,3}
            {1,1,2,3},
            {1,2,2,3},  // => 2 3 1 2
            {1,2,3,3},
            {1,1,2,2,3},
            {1,1,2,2,3,3},
            {1,1,1,2,2,3}
        };
        
        WiggleSortII sv = new WiggleSortII();
        
        int[] arr;
        for(int[] input : inputs){
            System.out.println(String.format("\n%s", Arrays.toString(input) ));
            
            arr = Arrays.copyOf(input, input.length);
            sv.wiggleSort(arr);
            System.out.println(String.format("%s", Arrays.toString(arr) ));    
            
            arr = Arrays.copyOf(input, input.length);
            sv.wiggleSortII(arr);
            System.out.println(String.format("%s", Arrays.toString(arr) ));     
        }
        
    }
    
}
