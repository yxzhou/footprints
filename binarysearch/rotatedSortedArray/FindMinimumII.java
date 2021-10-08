/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarysearch.rotatedSortedArray;

/**
 *
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * 
 * Find the minimum element.
 * 
 * The array may contain duplicates. (10,1, 10, 10, 10)  (10,10,10,1,10)
 * 
 * Example 1:
 * Input :[2,1]
 * Output : 1.
 * 
 * Example 2:
 * Input :[4,4,5,6,7,0,1,2]
 * Output : 0.
 * 
 */
public class FindMinimumII {

    public int findMin_RotatedWithDupl(int[] num) {
        if (null == num) {
            return Integer.MIN_VALUE;
        }

        int left = 0;
        int right = num.length - 1;
        int mid = 0;
        while (left < right) {
            mid = left + ((right - left) >> 1);
            
            if (num[left] < num[mid]) {
                if (num[left] >= num[right]) {
                    left = mid + 1;
                } else if (num[left] < num[right]) {
                    return num[left];
                }

            } else if (num[left] > num[mid]) {
                right = mid;
            } else { //  num[left] == num[mid]
                if (num[left] > num[right]) {
                    left = mid + 1;
                } else if (num[left] < num[right]) {
                    right = mid;
                } else {
                    left++;
                }
            }
        }

        return num[left];
    }

    public int findMin_RotatedWithDupl_2(int[] num) {
        if (num == null || num.length == 0) {
            return 0;
        }
        int l = 0;
        int r = num.length - 1;
        int min = num[0];
        while (l < r - 1) {
            int m = (l + r) / 2;
            if (num[l] < num[m]) {
                min = Math.min(num[l], min);
                l = m + 1;
            } else if (num[l] > num[m]) {
                min = Math.min(num[m], min);
                r = m - 1;
            } else {
                l++;
            }
        }
        min = Math.min(num[r], min);
        min = Math.min(num[l], min);
        return min;
    }

    public int findMin_RotatedWithDupl_n(int[] nums) {
        if (null == nums || 0 == nums.length) {
            return Integer.MIN_VALUE;
        }

        int l = 0;
        int r = nums.length - 1;
        int mid;
        while (l < r) {
            mid = l + ((r - l) >> 1); // 
            
            if (nums[mid] < nums[r]) {
                r = mid;
            } else if (nums[mid] > nums[r]) { 
                l = mid + 1;
            } else {
                r--;
            }
        }

        return nums[r];
    }
}
