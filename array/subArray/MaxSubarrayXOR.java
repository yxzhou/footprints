/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array.subArray;

import java.util.Arrays;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/722/
 *
 * Given an array of integers. find the maximum XOR subarray value in given array.
 *
 * What's the XOR: https://en.wikipedia.org/wiki/Exclusive_or
 *
 * Notes:
 *   The expected time complexity is O (n). 
 *   a[i]<=2147483647
 *
 *
 * Example 1
 * Input: [1, 2, 3, 4] 
 * Output: 7 
 * Explanation: The subarray [3, 4] has maximum XOR value 
 * 
 * Example 2
 * Input: [8, 1, 2, 12, 7, 6] 
 * Output: 15 
 * Explanation: The subarray [1, 2, 12] has maximum XOR value 
 * 
 * Example 3
 * Input: [4, 6] 
 * Output: 6 
 * Explanation: The subarray [6] has maximum XOR value
 * 
 * Thoughts:
 * define n as the length of nums
 * 1 brute force, there are n * (n - 1) subarray, 
 *   Time Complexity O(n*n) 
 * 2 prefix sum (xor), define f(i) = nums[0]^nums[1]^...^nums[i] 
 *   to f(x), how to find the f(y) to make f(x)^f(y) as the max value
 *   Time Complexity O(n*32) 
 * 
 * 
 */
public class MaxSubarrayXOR {
    
    /**
     * @param nums: the array
     * @return: the max xor sum of the subarray in a given array
     */
    public int maxXorSubarray(int[] nums) {
        if(nums == null || nums.length < 1){
            return Integer.MIN_VALUE;
        }

        TrieNode root = new TrieNode();
        add(root, 0);

        int max = Integer.MIN_VALUE;
        int x = 0;
        int y;
        for(int num : nums){
            x ^= num;

            y = getTheMax(root, x);

            max = Math.max(max, y);

            add(root, x);
        }

        return max;
    }

    private void add(TrieNode root, int num){

        TrieNode curr = root;

        int k;
        for(int i = 31; i >= 0; i-- ){
            k = ((num >> i) & 1);

            if(curr.nexts[k] == null){
                curr.nexts[k] = new TrieNode(); 
            }

            curr = curr.nexts[k];
        }
    } 

    private int getTheMax(TrieNode root, int num){
        int result = 0;
        TrieNode curr = root;

        int k;
        
        for(int i = 31; i >= 0; i-- ){
            k = ((num >> i) & 1);

            if((i != 31 && curr.nexts[k^1] != null) || (i == 31 && curr.nexts[k] == null)){ //specail case, num is negative
                k ^= 1;
                result |= (1 << i);
            }

            curr = curr.nexts[k];
        }

        return result;
    }

    class TrieNode{
        TrieNode[] nexts = new TrieNode[2]; // 
        //boolean isLeaf = false;
    }
    
    public static void main(String[] args){
        
        int[][][] inputs = {
            //{ nums, expect }
            {
                {1, 2, 3, 4},
                {7}
            },
            {
                {8, 1, 2, 12, 7, 6},
                {15}
            },
            {
                {4, 6},
                {6}
            },
            {
                {-4, -6},
                {6}
            },
            {
                {-4, 6},
                {6}
            }
        };
        
        MaxSubarrayXOR sv = new MaxSubarrayXOR();
        
        for(int[][] input : inputs){
            System.out.println(String.format("\n nums: %s", Arrays.toString(input[0])));
            
            Assert.assertEquals(input[1][0], sv.maxXorSubarray(input[0]));
        }
    }
}
