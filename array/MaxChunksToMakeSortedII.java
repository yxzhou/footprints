/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array;

/**
 * _https://www.lintcode.com/problem/1040
 * 
 * Given an array arr of integers (not necessarily distinct), we split the array into some number of "chunks"
 * (partitions), and individually sort each chunk. After concatenating them, the result equals the sorted array.
 *
 * What is the most number of chunks we could have made?
 *
 * Constraints:
 *   arr will have length in range [1, 2000]. 
 *   arr[i] will be an integer in range [0, 10^8].  
 * 
 * Example 1:
 * Input: arr = [5,4,3,2,1] 
 * Output: 1 
 * Explanation: 
 * Splitting into two or more chunks will not return the required result. 
 * For example, splitting into [5, 4], [3, 2, 1] will result in [4, 5, 1, 2, 3], which isn't sorted. 
 * 
 * Example 2:
 * Input: arr = [2,1,3,4,4] 
 * Output: 4 
 * Explanation: 
 * We can split into two chunks, such as [2, 1], [3, 4, 4]. 
 * However, splitting into [2, 1], [3], [4], [4] is the highest number of chunks possible.
 * 
 */
public class MaxChunksToMakeSortedII {
    /**
     * @param arr: an array of integers
     * @return the max number of chunks
     */
    public int maxChunksToSorted(int[] arr) {
        int n = arr.length;

        int[] minOfRight = new int[n];
        minOfRight[n - 1] = arr[n - 1];
        for(int i = n - 2; i >= 0; i--){
            minOfRight[i] = Math.min(arr[i], minOfRight[i + 1]);
        }

        int count = 0;

        int maxOfLeft = arr[0];
        for(int i = 0; i < n; i++ ){
            maxOfLeft = Math.max(arr[i], maxOfLeft);

            if(i == n - 1 || maxOfLeft <= minOfRight[i + 1] ){
                count++;
            }
        }

        return count;
    }
}
