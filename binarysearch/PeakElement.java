package binarysearch;

import java.util.Arrays;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/75
 * 
 * There is an integer array which has the following features:
 *
 * Notes:
 *   The numbers in adjacent positions are different. 
 *   A[0] < A[1] && A[A.length - 2] > A[A.length - 1]. 
 * 
 * We define a position P is a peak if: A[P] > A[P-1] && A[P] > A[P+1] 
 * Find a peak element in this array. Return the index of the peak.
 *
 * It's guaranteed the array has at least one peak. The array may contain multiple peeks, find any of them. The array
 * has at least 3 numbers in it.  
 * 
 * Example 1:
 * Input: A = [1, 2, 1, 3, 4, 5, 7, 6] 
 * Output: 1
 * Explanation: Returns the index of any peak element. 6 is also correct.
 *
 * Example 2: 
 * Input: A = [1,2,3,4,1] 
 * Output:3
 *
 * Challenge: Time complexity O(logN)
 * 
 * 
 */
public class PeakElement {

    /**
     * @param A: An integers array.
     * @return return any of peek positions.
     */
    public int findPeak(int[] A) {
        int left = 0;
        int right = A.length - 1;
        
        int mid;
        while(left + 1 < right){
            mid = left + (right - left) / 2;

            if(A[mid - 1] < A[mid] && A[mid] > A[mid + 1]){                
                return mid;
            }

            //else A[mid - 1] > A[mid] || A[mid] < A[mid + 1], The numbers in adjacent positions are different.    
            if(A[mid - 1] > A[mid]){
                right = mid;
            }else { 
                left = mid;
            }

        }

        // because it's guaranteed the array has at lease one peak, the following will not happen
        return -1;
    }
    
    public int findPeak_n(int[] A) {
        int left = 0;
        int right = A.length - 1;
        
        int mid;
        while(left + 1 < right){
            mid = left + (right - left) / 2;

            // The numbers in adjacent positions are different.    
            if(A[mid - 1] > A[mid]){
                right = mid;
            }else if(A[mid] < A[mid + 1]){ 
                left = mid;
            }else{
                return mid;
            }

        }

        // because it's guaranteed the array has at lease one peak, the following will not happen
        return -1;
    }
    
    public static void main(String[] args){
        
        int[][][] inputs = {
            //{test case, expect}
            {{1, 2, 1, 3, 4, 5, 7, 6}, {6}}, // or 6
            {{1, 2, 3, 4, 1}, {3}},
            {{12, 20, 11}, {1}},
            {{1, 2, 1, 2, 3, 1}, {1}}, // or 4
            
        }; 
        
        PeakElement sv = new PeakElement();
        
        for(int[][] input : inputs){
            System.out.println(String.format("\n%s", Arrays.toString(input[0]) ));
            
            Assert.assertEquals(input[1][0], sv.findPeak(input[0]));
            Assert.assertEquals(input[1][0], sv.findPeak_n(input[0]));
        }
    }
    
}
