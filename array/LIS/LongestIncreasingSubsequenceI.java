/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.lis;

import java.util.Arrays;
import junit.framework.Assert;
import util.Misc;

/**
 *
 * Related question #1, 
 *   Give a sequence of integers, everytime it can move and insert a element in a certain position, what's the minimum movement. 
 *   Example to [5,4,1,2,3], the minimum movement is 2.  move 4 after 3, move 5 after 4.
 * 
 * Related question #2 
 *   Given an array of integers without duplicates, 
 *   remove elements such that the remaining elements are in ascending order. 
 *   Minimize the number of removed elements. 
 *   Your answer should consist of all possible combinations if more than one unique solution exists.
 * 
 * 
 * Given a sequence of integers, find the longest increasing subsequence (LIS).
 * You code should return the length of the LIS.
 * 
 * What's the definition of longest increasing subsequence?
 *   The longest increasing subsequence problem is to find a subsequence of a given sequence in which the subsequence's elements are in sorted order, lowest to highest, and in which the subsequence is as long as possible. This subsequence is not necessarily contiguous, or unique.
 *   https://en.wikipedia.org/wiki/Longest_increasing_subsequence
 * 
 * Example 1:
 * Input: nums = [5,4,1,2,3]
 * Output: 3
 * Explanation: LIS is [1,2,3]
 * 
 * Example 2:
 * Input: nums = [4,2,4,5,3,7]
 * Output: 4
 * Explanation: LIS is [2,4,5,7]
 * 
 * Challenge
 * Time complexity  O(n*n) or O(nlogn)
 * 
 * 
 * Solution #1, DP,  depth[i] = max{depth[i], depth[j] + 1}.
 *   List:    [4, 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15]
 *   depth[]: [1, 1, 2, 2, 3,  2,  3, 3,  4, 2, 4, 3,  5, 3,  5, 4,  6]
 *   The result is: 6
 * 
 *   Time is O(n^2),  while 
 *   It's easy to get all LIS string, to the above example, they are {0, 2, 6, 9, 13, 15} or {0, 4, 6, 9, 11, 15}
 *   It's easy to get the number of all LIST string, see NumberOfLongestIncreasingSubsequence
 *    
 *   
 * Solution #2, Greedy + Binary Search
 * 1) create a array, put the first element in it. ( this array will store the Longest Increasing Subsequence )
 * 2) travel the input list, to every element, binary search the position in the array. 
 *    If it's bigger than the last one of array, add it in the tail.  ( the LIS grows )
 *    Or replace it with the related element in the array.  ( the LIS not grow, while it's better for the future. )
 *   
 *   Time O(nlogn)   Space O(n), it's easy to get the LIS length, instead of the LIS strings
 * 
 * 
 */
public class LongestIncreasingSubsequenceI {
    
    /**
     * DP
     * Time O(n * n) Space O(n)
     * 
     * @param nums: An integer array
     * @return: The length of LIS (longest increasing subsequence)
     */
    public int longestIncreasingSubsequence_DP(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }

        int n = nums.length;
        int[] depths = new int[n];

        int max = 0;
        for(int i = 0; i < nums.length; i++){
            depths[i] = 1;
            for(int j = 0; j < i; j++){
                if(nums[i] > nums[j]){
                    depths[i] = Math.max(depths[i], depths[j] + 1);
                }
            }

            max = Math.max(max, depths[i]);
        }

        return max;
    }
    
    /**
     * Time O(n * n) Space O(n)
     * 
     * @param nums
     * @return 
     */
    public int longestIncreasingSubsequence(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        int r = 0;
        int[] sequence = new int[n];
        sequence[0] = nums[0];
        
        for(int i = 1; i < nums.length; i++){
            int j = r;
            while( j >= 0 && nums[i] <= sequence[j] ){
                j--;
            }
            sequence[++j] = nums[i];

            r = Math.max(r, j);
        }
        
        return r + 1;
    }
    
    /**
     * optimize longestIncreasingSubsequence()
     * Time O(nlogn) Space O(n)
     * 
     * @param nums
     * @return 
     */
    public int longestIncreasingSubsequence_n(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        int[] sequence = new int[n];
        int r = 0;
        sequence[0] = nums[0];
        
        int p;
        for(int i = 1; i < nums.length; i++){
            
            p = Arrays.binarySearch(sequence, 0, r + 1, nums[i]);
            
            if(p < 0){
                p = -p - 1;
                
                sequence[p] = nums[i];   
                r = Math.max(r, p);
            }

        }
        
        return r + 1;
    }
    
    public static void main(String[] args) {
        /**
         * test Arrays.binarySearch()
         */
        System.out.println(" --test Arrays.binarySearch()--22- ");
                
        int[] list = {};

        System.out.println(Arrays.binarySearch(list, 0, 0, 1)); //-1
        //System.out.println(Arrays.binarySearch(list, 0, 1, 1));  //ArrayIndexOutOfBoundsException

        list = new int[]{1, 3, 3};
        System.out.println(Arrays.binarySearch(list, 0, 2, 0)); //-1
        System.out.println(Arrays.binarySearch(list, 0, 2, 1)); //0
        System.out.println(Arrays.binarySearch(list, 0, 2, 2)); //-2
        System.out.println(Arrays.binarySearch(list, 0, 2, 3)); //1
        System.out.println(Arrays.binarySearch(list, 0, 2, 4)); //-3   
        
        
        int[][] inputs = {
            {5,4,1,2,3},
            {4,2,4,5,3,7},
            {1,5,8,3,6,7},
            {4,0,8,4,12,2,10,6,14,1,9,5,13,3,11,7,15,8}
        };
        
        int[] expects = {3, 4, 4,6};
        
        LongestIncreasingSubsequenceI sv = new LongestIncreasingSubsequenceI();
        
        for(int i = 0; i < inputs.length; i++){
            Misc.printArray_Int(inputs[i]);
            
            Assert.assertEquals(expects[i], sv.longestIncreasingSubsequence_DP(inputs[i]));
            Assert.assertEquals(expects[i], sv.longestIncreasingSubsequence(inputs[i]));
            Assert.assertEquals(expects[i], sv.longestIncreasingSubsequence_n(inputs[i]));
        }

    }
}
