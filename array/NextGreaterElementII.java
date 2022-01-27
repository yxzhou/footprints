/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array;

import java.util.Arrays;
import java.util.Stack;

/**
 * _https://www.lintcode.com/problem/1201
 *
 * Given a circular array (the next element of the last element is the first element of the array), print the Next
 * Greater Number for every element. The Next Greater Number of a number x is the first greater number to its
 * traversing-order next in the array, which means you could search circularly to find its next greater number. If it
 * doesn't exist, output -1 for this number.
 *
 * The length of given array won't exceed 10000.
 *
 * Example 1: 
 * Input: [1,2,1] 
 * Output: [2,-1,2] 
 * Explanation: 
 * The first 1's next greater number is 2; 
 * The number 2 can't find next greater number; 
 * The second 1's next greater number needs to search circularly, which is also 2.
 *
 * Example 2: 
 * Input: [1] 
 * Output: [-1] 
 * Explanation: The number 1 can't find next greater number.
 * 
 *
 */
public class NextGreaterElementII {

    /**
     * 
     * @param nums: an array
     * @return the Next Greater Number for every element
     */
    public int[] nextGreaterElements(int[] nums) {
        if (nums == null) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);

        Stack<Integer> stack = new Stack<>(); //<index>
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                result[stack.pop()] = nums[i];
            }

            stack.add(i);

            if (max < nums[i]) {
                max = nums[i];
            }
        }

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                result[stack.pop()] = nums[i];
            }

            if (max == nums[i]) {
                break;
            }
        }

        return result;
    }
}
