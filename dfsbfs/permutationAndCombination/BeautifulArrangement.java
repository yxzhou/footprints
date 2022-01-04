/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfsbfs.permutationAndCombination;

import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/990
 * Suppose you have N integers from 1 to N. We define a beautiful arrangement as an array that is constructed by these N
 * numbers successfully if one of the following is true for the ith position (1 <= i <= N) in this array:
 *  The number at the ith position is divisible by i. 
 *  i is divisible by the number at the ith position. 
 * 
 * Now given N, how many beautiful arrangements can you construct?
 * Note: N is a positive integer and will not exceed 15.
 * 
 * Example1 Input: 2 Output: 2
 * Explanation: 
 *   The first beautiful arrangement is [1, 2]:
 *   The second beautiful arrangement is [2, 1]:
 * 
 * Example2 Input: 3 Output: 3
 * 
 */
public class BeautifulArrangement {
    /**
     * @param N: The number of integers
     * @return: The number of beautiful arrangements you can construct
     */
    public int countArrangement_swap(int N) {
        if(N < 1 || N > 15){
            return 0;
        }

        int[] arr = new int[N + 1]; 
        for(int i = 1; i <= N; i++){
            arr[i] = i;
        }

        return helper(arr, 1);
    }

    private int helper(int[] arr, int p){
        if(p == arr.length){
            return 1;
        }

        int result = 0;
        if(arr[p] % p == 0 || p % arr[p] == 0){
            result = helper(arr, p + 1);
        }

        for(int i = p + 1; i < arr.length; i++){
            if(  arr[i] % p == 0 || p % arr[i] == 0 ){ //find all possible for p_th position
                swap(arr, p, i);
                result += helper(arr, p + 1);
                swap(arr, p, i);
            }
        }

        return result;
    }

    private void swap(int[] arr, int l, int r){
        int tmp = arr[l];
        arr[l] = arr[r];
        arr[r] = tmp;
    }
    
    public static void main(String[] args){
        BeautifulArrangement sv = new BeautifulArrangement();
                
        int[][] inputs = {
            {1, 1},
            {2, 2},
            {3, 3},
            {5, 10},
            {6, 36},
            {7, 41},
            {8, 132},
            {9, 250},
            {10, 700},
            {11, 750},
            {12, 4010},
            {13, 4237},
            {14, 10680},
            {15, 24679}
        };
        
        for(int[] input : inputs){
            Assert.assertEquals(input[1], sv.countArrangement_swap(input[0]));
        }
    }
}
