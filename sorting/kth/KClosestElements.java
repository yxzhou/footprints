/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sorting.kth;

import java.util.Arrays;

/**
 * _https://www.lintcode.com/problem/460
 * 
 * Given target, a non-negative integer k and an integer array A sorted in ascending order, find the k closest numbers
 * to target in A, sorted in ascending order by the difference between the number and target. Otherwise, sorted in
 * ascending order by number if the difference is same.
 *
 * Constraints:
 * The value k is a non-negative integer and will always be smaller than the length of the sorted array. 
 * Length of the given array is positive and will not exceed 10^4
 * Absolute value of elements in the array will not exceed 10^4
 *
 * Example 1:
 * Input: A = [1, 2, 3], target = 2, k = 3 
 * Output: [2, 1, 3] 
 * 
 * Example 2:
 * Input: A = [1, 4, 6, 8], target = 3, k = 3 
 * Output: [4, 1, 6] 
 * 
 * Challenge O(logn + k) time
 * 
 */
public class KClosestElements {
    
    /**
     *  Step 1: binary search the target in A, locate the insertion point. 
     *  Step 2: start from the point, go to two sides with two pointers, get the k closest number to target.  
     * 
     * @param A: an integer array A sorted in ascending order
     * @param target: An integer
     * @param k: An integer
     * @return the k closest numbers to target in A
     */
    public int[] kClosestNumbers(int[] A, int target, int k) {
        
        int p = Arrays.binarySearch(A, target);

        if(p < 0){
            p = -p - 1;
        }

        int n = A.length;
        int[] result = new int[k];

        int l = p - 1;
        int r = p;
        
        int diff1;
        int diff2;
        for( int i = 0; i < k; i++){
            
            diff1 = ( l >= 0 ? target - A[l] : Integer.MAX_VALUE );
            diff2 = ( r < n ?  A[r] - target : Integer.MAX_VALUE );

            if(diff2 < diff1){
                result[i] = A[r];
                r++;
            }else{
                result[i] = A[l];
                l--;
            }

        }

        return result;
    }
}
