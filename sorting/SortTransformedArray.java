/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorting;

import java.util.Arrays;
import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/906
 *
 * Given a sorted array of integers nums and integer values a, b and c. Apply a quadratic function of the form 
 * f(x)=ax^2 + bx + c to each element x in the array.
 * 
 * The returned array must be in sorted order.
 * 
 * Expected time complexity: O(n)
 * 
 * Example1
 * Input: nums = [-4, -2, 2, 4], a = 1, b = 3, c = 5
 * Output: [3, 9, 15, 33]
 * 
 * Example2
 * Input: nums = [-4, -2, 2, 4], a = -1, b = 3, c = 5
 * Output: [-23, -5, 1, 7]
 * 
 */
public class SortTransformedArray {
    /**
     * @param nums: a sorted array
     * @param a: 
     * @param b: 
     * @param c: 
     * @return a sorted array
     */
    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        if(nums == null){
            return new int[0];
        }

        int n = nums.length;
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = nums[i] * ( a * nums[i] + b) + c; 
        }
        
        int[] result = new int[n];
        if(a >= 0){
            for(int l = 0, r = n - 1, k = n - 1; l <= r; ){
                if( arr[l] > arr[r] ){
                    result[k--] = arr[l];
                    l++;
                }else{
                    result[k--] = arr[r];
                    r--;
                }
            }
        }else{
            for(int l = 0, r = n - 1, k = 0; l <= r; ){
                if( arr[l] > arr[r] ){
                    result[k++] = arr[r];
                    r--;
                }else{
                    result[k++] = arr[l];
                    l++;
                }
            }
        }

        return result;
    }
    
    public static void main(String[] args){
        int[][][] inputs = {
            {{-4, 2, 2, 4}, {-3, -2, -5}, {-61, -45, -21, -21}},
            {{-4, 2, 2, 4}, {3, -2, -5}, {3, 3, 35, 51}},
            {{-4, 2, 2, 4}, {0, -2, -5}, {-13, -9, -9, 3}},
            {{-4, 2, 2, 4}, {0, 2, -5}, {-13, -1, -1, 3}},
            {{-4, 2, 2, 4}, {0, 0, -5}, {-5, -5, -5, -5}},
            {{2, 2, 3, 4}, {3, 2, -5}, {11, 11, 28, 51}},
            {{2, 2, 3, 4}, {-3, 2, -5}, {-45, -26, -13, -13}},
            {{1, 2, 4, 4}, {3, 3, -5}, {1, 13, 55, 55}},
            {{1, 2, 4, 4}, {-3, 3, -5}, {-41, -41, -11, -5}},
        };
        
        SortTransformedArray sv = new SortTransformedArray();
        
        for(int[][] input : inputs){
            System.out.println(String.format("nums:%s \t\ta=%d, b=%d, c=%d", Arrays.toString(input[0]),input[1][0], input[1][1], input[1][2] ));
            
            //System.out.println( Arrays.toString( sv.sortTransformedArray(input[0], input[1][0], input[1][1], input[1][2]) )) ;
            
            Assert.assertArrayEquals(input[2], sv.sortTransformedArray(input[0], input[1][0], input[1][1], input[1][2]));
        }
    }
    
}
