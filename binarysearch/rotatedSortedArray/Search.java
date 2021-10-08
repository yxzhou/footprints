package binarysearch.rotatedSortedArray;

import util.Misc;

  /**
   * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
   * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
   * 
   * You are given a target value to search. If found in the array return its index, otherwise return -1.
   * You may assume no duplicate exists in the array.
   * 
   * Example 1:
   *   Input: array = [4, 5, 1, 2, 3]  target = 1
   *   Output: 2
   *   Explanation: 1 is indexed at 2 in the array.
   *   
   * Example 2:
   *   Input: array = [4, 5, 1, 2, 3] target = 0
   *   Output: -1
   *   Explanation: 0 is not in the array. Returns -1.
   * 
   * Challenge Time O(logn) Space O(1)
   */

public class Search {
	  
    public int binarySearch_Rotated(int[] A, int target) {
        if(A == null || A.length == 0){
            return -1;
        }

        int left = 0;
        int right = A.length - 1;
        int mid;
        while (left < right) {
            mid = left + ((right - left) >> 1); // Avoid overflow

            if (A[mid] == target) {
                return mid;
            }

            if (A[left] <= A[mid]) { // the left half (left, mid) is sorted
                if (A[left] <= target && target < A[mid]) { // num is in [left, mid)
                    right = mid - 1;
                } else { // num is in (mid, right]
                    left = mid + 1;
                }
            } else { // the right half is sorted
                if (A[mid] < target && target <= A[right]) {// num is in (mid, right]    
                    left = mid + 1;
                } else { // num is in [left, mid)
                    right = mid - 1;
                }
            }
        }

        return A[left] == target ? left :  -1;
    }
    
    public int binarySearch_Rotated_n(int[] A, int target) {
        if(A == null || A.length == 0){
            return -1;
        }

        int l = 0;
        int r = A.length - 1;

        int mid;
        while(l < r){
            mid = l + (r - l) / 2;

            if(A[mid] == target){
                return mid;
            }else if( ( A[mid] < target && target <= A[r]) || ( A[mid] > A[r] && ( A[mid] < target || target <= A[r])) ){
                l = mid + 1;
            }else{
                r = mid - 1;
            }
        }

        return A[l] == target ? l :  -1;
    }    
    
}
