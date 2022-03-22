/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sorting.kth;

/**
 * _https://www.lintcode.com/problem/1097
 *
 * Nearly every one have used the Multiplication Table. But could you find out the k-th smallest number quickly from the
 * multiplication table?
 *
 * Given the height m and the length n of a m * n Multiplication Table, and a positive integer k, you need to return the
 * k-th smallest number in this table.
 *
 * Notes:
 *   The m and n will be in the range [1, 30000]. 
 *   The k will be in the range [1, m * n]
 *
 * Example 1:
 * Input：m = 3, n = 3, k = 5 
 * Output：3 
 * Explanation： 
 * The Multiplication Table: 
 * 1	2	3 
 * 2	4	6 
 * 3	6	9 
 * The 5-th smallest number is 3 (1, 2, 2, 3, 3). 
 * 
 * Example 2:
 * Input：m = 2, n = 3, k = 6 
 * Output：6 
 * Explanation： 
 * The Multiplication Table: 
 * 1	2	3 
 * 2	4	6 
 * The 6-th smallest number is 6 (1, 2, 2, 3, 4, 6).
 * 
 * Thoughts:
 * The multiplication table is
 * 1*1, 1*2, ..., 1*n
 * 2*1, 2*2, ..., 2*n
 * ..., ..., ..., (m-1)*n   
 * m*1, m*2, ...,  m*n
 *
 * m1) minHeap,
 *     the smallest is up-left, the next is the right one or the down one. 
 *     
 * Time complexity is O( k * log( Math.min(2k, m*n)) ), Space O(Math.min(2k, m*n))
 * 
 * m2) binary search 
 *     the smallest is up-left, the biggest is bottom-right, init low = matrix[0][0], high = matrix[m - 1][n - 1]
 *     find how many elements is smaller than the mid 
 *     
 * Time complexity is O(m) to find out how many elements is smaller than the mid, 
 * Time complexity is O( log(m*n) * (m) ), Space O(1)
 * 
 * The k will be in the range [1, m * n]
 *   when k is small, such as k < m / 2, the m1 is better. when k is close to m*n, change m1 to get kth largest. 
 *   when k is in middle, such as k = m*n / 2, the m2 is better
 * 
 */
public class KthSmallestInMultiplicationTable {
    

    
    /**
     * 
     * 
     */
    public int findKthNumber_m2(int m, int n, int k) {
        int low = 1;
        int high = m * n;

        int middle;
        while(low < high){
            middle = low + (high - low) / 2;

            if(findPosition(m, n, middle) >= k){
                high = middle;
            }else{
                low = middle + 1;
            }
        }

        return high;
    }

    // find how many smaller and equals 
    private int findPosition(int m, int n, int target){
        int p = 0; 

        for(int r = 1; r <= m; r++){
            p += Math.min(target / r, n);
        }

        return p;
    }
}
