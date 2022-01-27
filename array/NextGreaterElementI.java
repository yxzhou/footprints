/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * _https://www.lintcode.com/problem/1206
 *
 * You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset of nums2. Find all
 * the next greater numbers for nums1's elements in the corresponding places of nums2.
 *
 * The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2. If it does not
 * exist, output -1 for this number.
 *
 *   1.All elements in nums1 and nums2 are unique.
 *   2.The length of both nums1 and nums2 would not exceed 1000.
 * 
 * Example 1:
 * Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
 * Output: [-1,3,-1]
 * Explanation:
 *     For number 4 in the first array, you cannot find the next greater number for number 4 in the second array, so output -1.
 *     For number 1 in the first array, the next greater number for number 1 in the second array is 3.
 *     For number 2 in the first array, there is no next greater number for number 2 in the second array, so output -1.
 * 
 * Example 2:
 * Input: nums1 = [2,4], nums2 = [1,2,3,4].
 * Output: [3,-1]
 * Explanation:
 *    For number 2 in the first array, the next greater number for it in the second array is 3.
 *    For number 4 in the first array, there is no next greater number for it in the second array, so output -1.
 * 
 */
public class NextGreaterElementI {
    /**
     * @param nums1: an array
     * @param nums2: an array
     * @return  find all the next greater numbers for nums1's elements in the corresponding places of nums2
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        if(nums1 == null || nums2 == null){
            return new int[0];
        }

        Map<Integer, Integer> nextGreaters = new HashMap<>(); // <value, next greater value>
        Stack<Integer> stack = new Stack<>(); // <value>

        for(int i = 0; i < nums2.length; i++){
            while(!stack.isEmpty() && stack.peek() < nums2[i]){
                nextGreaters.put(stack.pop(), nums2[i]);
            }

            stack.add(nums2[i]);
        }        

        int[] result = new int[nums1.length];
        for(int i = 0; i < nums1.length; i++){
            result[i] = nextGreaters.getOrDefault(nums1[i], -1);
        }

        return result;
    }
}
