/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array;

/**
 * _https://www.lintcode.com/problem/1039
 *
 * Given an array arr that is a permutation of [0, 1, ..., arr.length - 1], we split the array into some number of
 * "chunks" (partitions), and individually sort each chunk. After concatenating them, the result equals the sorted
 * array.
 *
 * What is the most number of chunks we could have made?
 *
 * Constraints:
 * arr will have length in range [1, 10]. 
 * arr[i] will be a permutation of [0, 1, ..., arr.length - 1]. 
 * 
 * Example
 * 1: Input: arr = [4,3,2,1,0] 
 * Output: 1
 * Explanation: Splitting into two or more chunks will not return the required result. 
 * For example, splitting into [4, 3], [2, 1, 0] will result in [3, 4, 0, 1, 2], which isn't sorted.
 *
 * Example 2: Input: arr = [1,0,2,3,4] 
 * Output: 4
 * Explanation: We can split into two chunks, such as [1, 0], [2, 3, 4]. 
 * However, splitting into [1, 0], [2], [3], [4] is the highest number of chunks possible.
 *
 * Thoughts:
 *    Because the arr[i] will be permutation of [0, 1, ..., arr.length - 1]. 
 *    a valid chuck [arr[i], ...,, arr[j]], the max is i and, the min is j.  
 *   
 *    Example, 
 *    [a, b, c, d], 
 *    when i is 0, arr[i] is a, in this chunk, the min is 0 and the max is a.
 *         if a == 0, [a] is a valid chuck.  
 *         if a == 1, the chunk is end at 1, if found arr[1] > arr[0], the chuck size is end at arr[1]. 
 *         so the chuck is end at the max.
 * 
 *         does it check if the min is included in the chuck? 
 *         no.  because the arr[i] will be permutation of [0, 1, ..., arr.length - 1]. 
 *    
 *    
 * 
 */
public class MaxChunksToMakeSorted {
    /**
     * @param arr: a permutation of N
     * @return the most number of chunks
     */
    public int maxChunksToSorted_n(int[] arr) {
        
        int count = 0;
        int n = arr.length;

        for(int i = 0; i < n; ){
            
            for(int max = arr[i]; i <= max; i++){
                max = Math.max(max, arr[i]);
            }

            count++;
        }

        return count;
    }
    
    public int maxChunksToSorted(int[] arr) {
        int n = arr.length;
        int[] maxOfLeft = new int[n];
        int[] minOfRight = new int[n];

        maxOfLeft[0] = arr[0];
        for (int i = 1; i < n; i++) {
            maxOfLeft[i] = Math.max(maxOfLeft[i - 1], arr[i]);
        }

        minOfRight[n - 1] = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            minOfRight[i] = Math.min(minOfRight[i + 1], arr[i]);
        }

        int count = 0;
        for (int i = 0; i < n - 1; i++) {
            if (maxOfLeft[i] <= minOfRight[i + 1]) {
                count++;
            }
        }

        return count + 1;
    }
}
