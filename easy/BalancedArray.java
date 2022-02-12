/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easy;

import binarysearch.PeakElement;
import java.util.Arrays;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/1793
 *
 * Given an array, you need to find the "balanced number" in the array. 
 * 
 * A "balanced number" satisfies that the sum of all the numbers on its left is equal to the sum of all the numbers on 
 * its right. Your code should return the subscript of the balanced number. If there are multiple balanced numbers, 
 * return the smallest subscript. 
 *
 * Constraints:
 *   3 <= n <= 10^5 
 *   1 <= sales[i] <= 2*10^4,where 0 <= i<n 
 *   It is guaranteed that a solution always exists
 *
 * Example: 
 * Input : [1, 2, 3, 4, 6] 
 * Output : 3
 * 
 */
public class BalancedArray {
    
    /**
     * Time O(n), Space O(n)
     * 
     * @param sales: a integer array
     * @return the smallest subscript of the balanced number, 
     */
    public int locate(int[] sales) {
        int n = sales.length;

        int[] suffixSums = new int[n];
        
        for(int i = n - 2; i >= 0; i--){
            suffixSums[i] = suffixSums[i + 1] + sales[i + 1];
        }

        int prefixSum = 0;
        for(int i = 0; i < n; i++){
            if(prefixSum == suffixSums[i]){
                return i;
            }

            prefixSum += sales[i];
        }

        //Because it is guaranteed that a solution always exists, 
        return -1;
    }
    
    /**
     * Time O(n), Space O(1)
     * 
     * @param sales
     * @return 
     */
    public int locate_1(int[] sales) {
        int n = sales.length;

        int right = 0;
        
        for(int i = n - 1; i >= 0; i--){
            right += sales[i];
        }

        int left = 0;
        for(int i = 0; i < n; i++){
            right -= sales[i];

            if(left == right){
                return i;
            }

            left += sales[i];
        }

        //Because it is guaranteed that a solution always exists, 
        return -1;
    }
    
    /**
     * Time O(n), Space O(n)
     * 
     * @param sales
     * @return 
     */
    public int locate_2(int[] sales) {
        int n = sales.length;

        int[] prefixSum = new int[n + 1];

        for(int i = 0; i < n; i++){
            prefixSum[i + 1] = prefixSum[i] + sales[i];
        }

        for(int i = 0; i < n; i++){
            if(prefixSum[i] == prefixSum[n] - prefixSum[i + 1]){
                return i;
            }
        }

        //Because it is guaranteed that a solution always exists, 
        return -1;
    }
    
    public static void main(String[] args){
        
        int[][][] inputs = {
            //{test case, expect}
            {{2, 20, 2}, {1}},
            {{1, 2, 3, 4, 6}, {3}}, 
            {{0, 1, -1}, {0}},
            {{1, -1, 0}, {2}},
        }; 
        
        BalancedArray sv = new BalancedArray();
        
        for(int[][] input : inputs){
            System.out.println(String.format("\n%s", Arrays.toString(input[0]) ));
            
            Assert.assertEquals(input[1][0], sv.locate(input[0]));
            Assert.assertEquals(input[1][0], sv.locate_1(input[0]));
            Assert.assertEquals(input[1][0], sv.locate_2(input[0]));
        }
    }
    
}
