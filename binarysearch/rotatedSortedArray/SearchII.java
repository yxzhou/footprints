/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarysearch.rotatedSortedArray;

/**
 *
 * Follow up for RotatedSortedArray:
 *   What if duplicates are allowed?
 *   Would this affect the run-time complexity? How and why?
 * Write a function to determine if a given target is in the array.
 * 
 * Example 1:
 * Input: A = [] target = 1
 * Output: false
 * Explanation: Array is empty, 1 is not in array.
 * 
 * Example 2:
 * Input: A = [3,4,4,5,7,0,1,2] target = 4
 * Output: true
 * Explanation: 4 in the array.
 * 
 */
public class SearchII {
    /*Time O(n) Space O(1)*/
    public boolean search(int[] A, int target) {
        if(A == null || A.length == 0){
            return false;
        }

        return helper(A, target, 0, A.length - 1);
    }

    private boolean helper_n(int[] A, int target, int left, int right) {
        int mid;
        while (left < right) {
            mid = left + ((right - left) >> 1);

            if (A[mid] == target) {
                return true;
            } 
            
            if( (A[mid] < target && target <= A[right] ) || ( A[mid] > A[right] && ( A[mid] < target || target <= A[right] )) ){
                left = mid + 1;
            }else if( (A[left] <= target &&  target < A[mid]) || ( A[left] > A[mid] && ( A[left] <= target || target < A[mid]) )){
                right = mid - 1;
            }else{
                return helper_n(A, target, left, mid - 1) || helper_n(A, target, mid + 1, right);
            }
        }

        return A[left] == target;
    }
    

    private boolean helper(int[] A, int target, int left, int right){
        if(left > right){
            return false;
        }
        if(left == right){
            return A[left] == target;
        }

        int mid = left + (right - left) / 2;

        if(target == A[mid]){
            return true;
        }
        
        if( (A[mid] < target && target <= A[right] ) || ( A[mid] > A[right] && ( A[mid] < target || target <= A[right] )) ){
            return helper(A, target, mid + 1, right);
        }

        if( (A[left] <= target &&  target < A[mid]) || ( A[left] > A[mid] && ( A[left] <= target || target < A[mid]) )){
            return helper(A, target, left, mid - 1);
        }

        return helper(A, target, left, mid - 1) || helper(A, target, mid + 1, right) ;
    }
}
