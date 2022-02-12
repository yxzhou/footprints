/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package binarysearch;

import java.util.Arrays;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/1476
 * _https://www.lintcode.com/problem/585
 *
 * Let's call an array A a mountain if the following properties hold:
 *
 * Notes: 
 *   A.length >= 3 
 *   There exists some 0 < i < A.length - 1 such that A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1]
 *
 * Given an array that is definitely a mountain, return any i such that 
 *   A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1].
 *
 * Constraints:
 * 1) 3 <= A.length <= 10000 
 * 2) 0 <= A[i] <= 10^6  
 * 
 * Example 1:
 * Input: [0,1,0] 
 * Output: 1 
 * 
 * Example 2:
 * Input: [0,2,1,0] 
 * Output: 1
 *
 */
public class PeakIndexInMountainArray {

    /**
     * Time O(logn) Space O(1)
     * 
     * @param A: an array
     * @return any i such that A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1]
     */
    public int peakIndexInMountainArray(int[] A) {
        int left = 0;
        int right = A.length - 1;

        int mid;
        while(left + 1 < right){
            mid = left + (right - left) / 2;

            if(A[mid] < A[mid + 1] ){
                left = mid;
            }else {
                right = mid;
            }
        }

        return A[left] < A[right]? right : left;
    }
    
    /**
     * @param nums: a mountain sequence which increase firstly and then decrease
     * @return then mountain top
     */
    public int mountainSequence(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        int mid;
        while(left + 1 < right){
            mid = left + (right - left) / 2;

            if(nums[mid] > nums[mid + 1]){
                right = mid;
            }else {
                left = mid;
            }

        }

        return Math.max(nums[left], nums[right]);
    }
    
    public static void main(String[] args){
        
        int[][][] inputs = {
            //{test case, expect}
            {{0,1,0}, {1}}, 
            {{0,2,1,0}, {1}},
            {{0,5,3,2,1,0}, {1}},
            {{0,1,2,3,5,1}, {4}},
            {{2,1,0}, {0}},
            {{5,3,2,1,0}, {0}},
            {{1,2,5}, {2}},
            {{0,1,2,3,5}, {4}},
        }; 
        
        PeakIndexInMountainArray sv = new PeakIndexInMountainArray();
        
        for(int[][] input : inputs){
            System.out.println(String.format("\n%s", Arrays.toString(input[0]) ));
            
            Assert.assertEquals(input[1][0], sv.peakIndexInMountainArray(input[0]));
            
            Assert.assertEquals(input[0][input[1][0]], sv.mountainSequence(input[0]));
        }
    }
}
