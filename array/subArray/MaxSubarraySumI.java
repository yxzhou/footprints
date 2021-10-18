/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.subArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import util.Misc;

/**
 * 
 * Given an integer array, find a continuous subarray where the sum of numbers is the biggest. 
 * Your code should return the index of the first number and the index of the last number. 
 * (If their are duplicate answer, return the minimum one in lexicographical order)
 * 
 * Example: Give [-3, 1, 3, -3, 4], return [1,4].
 * 
 * Follow up:
 *   How about the array is an circular (the next of the last element is the first element, your sub array length can't exceed the input array ) ? 
 *   Example: Give [3, 1, -100, -3, 4], return [4,1].
 * 
 */
public class MaxSubarraySumI {

    
    public List<Integer> maxSubarraySum(int[] A) {
        if(A == null){
            return Collections.EMPTY_LIST;
        }

        int max = Integer.MIN_VALUE; //global max sum of subarray
        int[] maxIndex = new int[2];
        int localMax = Integer.MIN_VALUE; //
        int localMaxStart = 0;

        for(int i = 0; i < A.length; i++){
            if(localMax < 0){
                localMax = A[i];
                localMaxStart = i;
            }else{
                localMax += A[i];
            }

            if(max < localMax){
                max = localMax;
                maxIndex[0] = localMaxStart;
                maxIndex[1] = i;
            }
        }

        List<Integer> result = new ArrayList<>(maxIndex.length);
        for(int i : maxIndex){
            result.add(i);
        }
        return result;
    }
    

    /*
     * @param A: An integer array
     * @return: A list of integers includes the index of the first number and the index of the last number
     */
    public List<Integer> maxSubArraySum_P2(int[] A) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (A == null || A.length == 0){
            return result;
        }
        
        int[] maxSum = subSum(A, 1); // maximal subarray sum
        int[] minSum = subSum(A, -1); // minimal subarray sum

        int n = A.length;
        if( minSum[2] == minSum[3] || minSum[3] - minSum[2] <= maxSum[2] ){ // minSum[2] == minSum[3] means all are negative 
            result.add(maxSum[0]);
            result.add(maxSum[1]);
        }else{
            result.add( (minSum[1] + 1 + n) % n );
            result.add( (minSum[0] - 1 + n) % n );
        }

        return result;
    }

    private int[] subSum(int[] A, int sign) {
        int[] result = new int[4];
        
        int maxSum = Integer.MIN_VALUE;
        int localSum = Integer.MIN_VALUE;
        
        int start = 0; // candidate for first
        int x;
        for (int i = 0; i < A.length; i++) {
            result[3] += A[i];
                        
            x = sign * A[i];
            if (localSum < 0) {
                localSum = x;
                start = i;
            }else{
                localSum += x;
            }

            if (maxSum < localSum) {
                maxSum = localSum;
                result[0] = start;
                result[1] = i;
            }
        }
        
        result[2] = sign * maxSum;
        return result;
    }
    
    
    public static void main(String[] args) {

        MaxSubarraySumI sv = new MaxSubarraySumI();
    /**  **/
        //int[] arr = {-1, 1, -2, 3, 5, -1, 0};
        int[][] arr = {{-3,1,3,-3,4}, {-1, 1, -2, 3, 5, -1, 3, 0}, {0,-1}, {-2,-1, -3}, {2,1}, {-2,0}, {-1,2}, {0,0}, {1, -2, 3, 5, -3, 2 }, 
            {0, -2, 3, 5, -1, 2 },  {-9, -2, -3, -5, -3 }};


        for(int i = 0; i< 1; i++){
          System.out.println("\nThe original array is: "+Misc.array2String(arr[i]) );
          
          System.out.println("The value of max sub array is: "+ sv.maxSubArraySum_P2(arr[i]) );  
        }
        
    }
}
