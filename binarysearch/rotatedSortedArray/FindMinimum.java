/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarysearch.rotatedSortedArray;

import util.Misc;

/**
 *
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * 
 * Find the minimum element.
 * 
 * You may assume no duplicate exists in the array.
 * 
 * Example 1:
 * Input：[4, 5, 6, 7, 0, 1, 2]
 * Output：0
 * Explanation： he minimum value in an array is 0.
 * 
 * Example 2:
 * Input：[2,1]
 * Output：1
 * Explanation： The minimum value in an array is 1.
 * 
 */
public class FindMinimum {


    /*Time O(logn) Space O(1)*/
    public int findMin_Rotated_n(int[] nums) {
        if (null == nums || 0 == nums.length) {
            throw new IllegalArgumentException(); // error code
        }

        int l = 0; //left
        int r = nums.length - 1; //right
        int mid = 0;
        while (l < r) {
            mid = l + ((r - l) >> 1); //l <= mid < r
                        
            if( nums[l] <= nums[mid] ){
                if(nums[mid] < nums[r]){
                    return nums[l];
                }else{ //nums[mid] > nums[r]
                    l = mid + 1;
                }
            }else { //nums[l] > nums[mid]
                r = mid; // not mid + 1
            }
        }

        return nums[l];
    }

    public int findMin_Rotated_1(int[] nums) {
        if (null == nums || 0 == nums.length) {
            throw new IllegalArgumentException(); // error code
        }

        int l = 0; //left
        int r = nums.length - 1; //right
        int mid;
        while (l < r) {
            mid = l + ((r - l) >> 1); //l <= mid < r
            
//            if(l == mid){ //for case: [2, 1]
//                return Math.min(nums[l], nums[r]);
//            }
            
            if (nums[mid] < nums[r]) {
                r = mid;
            } else { //nums[mid] >= nums[r]
                l = mid + 1;
            }
        }

        return nums[l];
    }
    
    public static void main(String[] args) {
        
        FindMinimum sv = new FindMinimum();
            
        int[][] arr = {
                    {1}, 
                    {1,3}, 
                    {3,1}, 
                    {1,3,5}, 
                    {3,5,1}, 
                    {5,1,3}, 
                    {1,3,5,6},
                    {3,5,6,1},
                    {5,6,1,3},
                    {6,1,3,5},
                    {1,3,5,6,7},
                    {3,5,6,7,1},
                    {5,6,7,1,3},
                    {6,7,1,3,5},
                    {7,1,3,5,6},
           };
        int[] num = {0, 1, 2, 3, 4, 5, 6, 7
            , 0, 1, 2, 3, 4
            , 0, 1, 2};

        for(int i=0; i<arr.length; i++){
          System.out.println("Input: " + Misc.array2String(arr[i]));
          System.out.println("Output: " + sv.findMin_Rotated_1(arr[i]));
        }

    }
}
