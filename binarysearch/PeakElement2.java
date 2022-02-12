package binarysearch;

import java.util.Arrays;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/75
 * 
 * Continue on PeakElement, without:
 *   The numbers in adjacent positions are different. 
 * 
 * Thoughts:
 *   to case {1, 2, 2, 2, 2, 3, 0},  it's O(n)
 * 
 */
public class PeakElement2 {

    /**
     * @param A: An integers array.
     * @return return any of peek positions.
     */
    public int findPeak(int[] A) {
        int left = 0;
        int right = A.length - 1;
        
        int mid;
        int tmp;
        while(left < right){
            mid = left + (right - left) / 2;

            if(A[mid - 1] < A[mid] && A[mid] > A[mid + 1]){                
                return mid;
            }

            //else A[mid - 1] => A[mid] || A[mid] <= A[mid + 1]    
            tmp = mid - 1;
            while(left < tmp && A[mid] == A[tmp] ){
                tmp--;
            }

            if(A[tmp] > A[mid]){
                right = tmp + 1;
            }else{ 
 
                //It's guaranteed the array has at least one peak.
                tmp = mid + 1; 
                while(tmp < right && A[mid] == A[tmp] ){
                    tmp++;
                }
                left = tmp - 1;
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
            {{1, 2, 2, 2, 2, 3, 0}, {5}},
            {{0, 3, 2, 2, 2, 2, 1}, {1}},
            
        }; 
        
        PeakElement2 sv = new PeakElement2();
        
        for(int[][] input : inputs){
            System.out.println(String.format("\n%s", Arrays.toString(input[0]) ));
            
            Assert.assertEquals(input[1][0], sv.findPeak(input[0]));
        }
    }
    
}
