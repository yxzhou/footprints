/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sorting.kth;

import java.util.Arrays;
import org.junit.Assert;

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
        
        long diffL; //to avoid overflow, for case, A = [Integer.MIN_VALUE, Long.MAX_VALUE], target = 0, k = 2
        long diffR;
        for( int i = 0; i < k; i++){
            
            diffL = ( l >= 0 ? (long)target - A[l] : Long.MAX_VALUE );
            diffR = ( r < n ?  (long)A[r] - target : Long.MAX_VALUE );

            if(diffR < diffL){
                result[i] = A[r];
                r++;
            }else{
                result[i] = A[l];
                l--;
            }

        }

        return result;
    }
    
    public static void main(String[] args){
        
        int[][][] inputs = {
            //{A, {target, k}, expect}
            {{1, 2, 3}, {2, 3}, {2, 1, 3}},
            {{1, 4, 6, 8}, {3, 3}, {4, 1, 6}},
            {{1, 4, 6, 8}, {2, 3}, {1, 4, 6}},
            {{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE}, {0, 5}, {0, -1, 1, Integer.MAX_VALUE, Integer.MIN_VALUE}},
        };
        
        KClosestElements sv = new KClosestElements();
        
        for(int[][] input : inputs){
            System.out.println(String.format("\nA: %s, target = %d, k = %d", Arrays.toString(input[0]), input[1][0], input[1][1] ));
            
            Assert.assertArrayEquals(input[2], sv.kClosestNumbers(input[0], input[1][0], input[1][1]));
        }
    }
}
