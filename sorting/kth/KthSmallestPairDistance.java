/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sorting.kth;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * _https://www.lintcode.com/problem/1072
 *
 * Given an integer array, return the k-th smallest distance among all the pairs. The distance of a pair (A, B) is
 * defined as the absolute difference between A and B.
 *
 * Constraint:
 * 2 <= len(nums) <= 10_000. 
 * 0 <= nums[i] < 1000_000. 
 * 1 <= k <= len(nums) * (len(nums) - 1) / 2.
 *
 * Example
 * Input: nums = [1,3,1]  k = 1
 * Output: 0
 * Explanation:  
 *  Here are all the pairs: (1,3) -> 2 (1,1) -> 0 (3,1) -> 2 
 *  Then the 1st smallest distance pair is (1,1), and its distance is 0.
 * 
 * Thoughts:
 *  define n = len(nums) 
 *  m1, there are n * (n - 1) / 2 pairs, put them all into heap, get the k_th Smallest
 *     Time O(n * n * log (n * n) ) m2,
 * 
 *  m2, 
 *     Sort the nums, the max distance is nums[n - 1] - nums[0], 
 * 
 *         a0 a1 a2 a3
 *     a0  *        x             
 *     a1     *
 *     a2        *
 *     a3           * 
 * 
 *     define x = matrix[0][3] = nums[3]- nums[0], matrix[0][2] = nums[2]- nums[0], matrix[1][3] = nums[3]- nums[1]
 * 
 *     the up-right triangle is a sorted matrix.  
 * 
 *   refer to KthSmallestOfSortMatrix.kthSmallest, time O(k * log n )
 *   refer to refer to KthSmallestOfSortMatrix.kthSmallest_BS2, time O(n  * log(range) )
 *   O(10^4 * log(10^6) ) << O( 10^12 * log(10^4) )
 * 
 */
public class KthSmallestPairDistance {
    int[][] diffs = {{1, 0}, {0, -1}};

    /**
     * refer to KthSmallestOfSortMatrix.kthSmallest
     *
     * @param nums: a list of integers
     * @param k: a integer
     * @return the k-th smallest distance among all the pairs
     */
    
    public int smallestDistancePair(int[] nums, int k) {
        //  1 <= k <= len(nums) * (len(nums) - 1) / 2
        //assert nums == null || k < 1 || nums.length * nums.length < k;

        Arrays.sort(nums);

        int n = nums.length;

        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(nums[b[1]] - nums[b[0]], nums[a[1]] - nums[a[0]]));
        maxHeap.add(new int[]{0, n - 1});

        boolean[][] visited = new boolean[n][n];

        k = n * (n - 1) / 2 - k + 1;

        int[] p;
        int nr, nc;
        for( ;k > 1; k--){
            p = maxHeap.poll();

            for(int[] diff : diffs){
                nr = p[0] + diff[0];
                nc = p[1] + diff[1];

                if(nr < nc && !visited[nr][nc]){
                    visited[nr][nc] = true;
                    maxHeap.add(new int[]{nr, nc});
                }
            }
        }

        p = maxHeap.poll();
        return nums[p[1]] - nums[p[0]];
    }
    
    /**
     * refer to KthSmallestOfSortMatrix.kthSmallest_BS2
     *
     * @param nums: a list of integers
     * @param k: a integer
     * @return the k-th smallest distance among all the pairs
     */
    public int smallestDistancePair_BS(int[] nums, int k) {
        //  1 <= k <= len(nums) * (len(nums) - 1) / 2
        //assert nums == null || k < 1 || nums.length * nums.length < k;

        Arrays.sort(nums);

        int n = nums.length;

        int low = 0;
        int high = nums[n - 1] - nums[0];

        int mid;
        while( low < high ){
            mid = low + (high - low) / 2;

            if( countLessEqual(nums, mid) < k ){
                low = mid + 1;
            }else{
                high = mid;
            }
        }

        return low;
    }

    private int countLessEqual(int[] nums, int val){
        int count = 0;
        
        int n = nums.length;

        for(int r = n - 2, c = n - 1;  r < c && r >= 0; ){
            if(nums[c] - nums[r] > val){
                c--;

                if(c == r){
                    r--;
                }    
            }else{
                count += c - r;
                r--;
            }
        }

        return count;
    }
}
