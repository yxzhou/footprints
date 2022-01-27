/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array.anagram;

import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/1174
 * 
 *  as same as NextPermutation
 * 
 * Given a positive 32-bit integer n, you need to find the smallest 32-bit integer which has exactly the same digits
 * existing in the integer n and is greater in value than n.Please return the smallest such number. If no such positive
 * 32-bit integer exists, you need to return -1.
 *
 * Example 1:
 * Input: 12
 * Output: 21
 * 
 * Example 2:
 * Input: 21
 * Output: -1
 * 
 */
public class NextGreaterElementIII {
    /**
     * 
     * @param n: an integer
     * @return the smallest 32-bit integer which has exactly the same digits existing in the integer n and is greater in value than n
     */
    public int nextGreaterElement(int n) {
        assert n < 1;

        char[] arr = String.valueOf(n).toCharArray();
        int m = arr.length;

        //check from right to left, find ss[i] < s[i - 1]
        int l = m - 2;
        for( ; l >= 0; l--){
            if(arr[l] < arr[l + 1]){
                break;
            }
        }

        if(l == -1){
            return -1;
        }

        int r = m - 1;
        for( ; r > l; r--){
            if(arr[r] > arr[l]){
                break;
            }
        }

        swap(arr, l, r);

        for( l++, r = m - 1 ; l < r; l++, r--){
            swap(arr, l, r);
        }

        long result = Long.parseLong(String.valueOf(arr));
        return result > Integer.MAX_VALUE ? -1 : (int)result;
    }

   
    private void swap(char[] arr, int l, int r){
        char c = arr[l];
        arr[l] = arr[r];
        arr[r] = c;
    }
    
    public int nextGreaterElement_n(int n) {
        assert n < 1;

        char[] arr = String.valueOf(n).toCharArray();
        int m = arr.length;

        //check from right to left, find ss[i] < s[i - 1]
        int l = m - 2;
        for( ; l >= 0; l--){
            if(arr[l] < arr[l + 1]){
                break;
            }
        }

        if(l == -1){
            return -1;
        }

        int r = m - 1;
        for( ; r > l; r--){
            if(arr[r] > arr[l]){
                break;
            }
        }

        swap(arr, l, r);

        for( l++, r = m - 1 ; l < r; l++, r--){
            swap(arr, l, r);
        }

        long result = Long.parseLong(String.valueOf(arr));
        return result > Integer.MAX_VALUE ? -1 : (int)result;
    }
    
    
    public static void main(String[] args){
        int[][] inputs = {
            //{test case, expect}
            {12, 21},
            {21, -1},
            {1999999999, -1},
            {24321, 31224}
        };
        
        NextGreaterElementIII sv = new NextGreaterElementIII();
        
        for(int[] input : inputs){
            System.out.println("\nInput: " + input[0]);
            
            Assert.assertEquals(input[1], sv.nextGreaterElement(input[0]));
        }
        
    }
}
