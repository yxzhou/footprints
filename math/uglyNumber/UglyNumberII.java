package math.uglyNumber;

import java.util.Arrays;
import java.util.PriorityQueue;
import junit.framework.Assert;

/**
 * Leetcode #264
 * _https://www.lintcode.com/problem/4
 *
 * Write a program to find the n-th ugly number.
 *
 * Ugly numbers are positive numbers whose prime factors only include 2, 3, 5.
 *
 * Example:
 * Input: n = 10
 * Output: 12
 * Explanation: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 is the sequence of the first 10 ugly numbers.
 *
 * Note:
 * 1 is typically treated as an ugly number.
 * n does not exceed 1690.
 *
 */

public class UglyNumberII {

    /**  Time O(n * logn) ,  Space O(n * 2)  n < 1690*/
    public int nthUglyNumber_heap(int n) {
        if(n < 1){
            return -1;
        }
        if(n == 1){
            return 1;
        }

        final int[] factors = {2, 3, 5};

        PriorityQueue<Long> minHeap = new PriorityQueue<>();

        for(long f : factors){
            minHeap.add((long)f);
        }

        long x = 2;
        for(int i = 2; i <= n; i++){
            x = minHeap.poll();

            for(int j = factors.length - 1; j >= 0; j--){
                if(x % factors[j] == 0){
                    for(int k = j; k < factors.length; k++){
                        minHeap.add(x * factors[k]); //there are duplicated, example 6*2 and 4*3
                    }

                    break;
                }
            }
        }

        return (int)x;
    }
    
    /**
     * Time O(n), Space O(n * 3)
     * 
     * @param n
     * @return 
     */
    public int nthUglyNumber_n(int n) {
       // 1 <= n <= 100_000
        
        long[][] nums = new long[3][n];
        int[] head = new int[3];  // the head in nums[i]
        int[] tail = new int[3];  // the tail in nums[i]
        nums[0][0] = 2l;
        nums[1][0] = 3l;
        nums[2][0] = 5l;

        int min;
        long curr = 1l; 
        for( int i = 1; i < n; i++ ){
            min = 0;
            for(int k = 1; k < 3; k++){
                if( nums[min][head[min]] > nums[k][head[k]] ){
                    min = k;
                }
            }

            curr = nums[min][head[min]];

            for(int k = min; k < 3; k++ ){
                nums[k][++tail[k]] = curr * nums[k][0];
            }

            head[min]++;
        }

        return (int)curr;
    }

    /**
     * 
     *  Time O(n) ,  Space O(n)  
     * 
     *  int limit  n <- 1690
     * 
     * @param n
     * @return 
     */
    public int nthUglyNumber_dp(int n) {
        if (n < 1) {
            return -1;
        }

        final int[] factors = {2, 3, 5};
        int[] indexes = {0, 0, 0};

        int[] dp = new int[n];
        dp[0] = 1;

        int min;
        int[] nexts = new int[3];
        for(int i = 1; i < n; i++) {
            min = Integer.MAX_VALUE;
            for(int k = 0; k < factors.length; k++){
                nexts[k] = dp[indexes[k]] * factors[k];

                min = Math.min(min, nexts[k]);
            }

            dp[i] = min;

            for(int k = 0; k < factors.length; k++){
                if(min == nexts[k]){
                    indexes[k]++;
                }
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        
        int[][] inputs = {
            {1, 1},
            {2, 2},
            {3, 3},
            {4, 4},
            {5, 5},
            {9, 10},
            {11, 15},
            {19, 32},
            {23, 48},
            {29, 75},
            {41, 150},
            {43, 162},
            {47, 216},
            {1690, 2123366400},
            //{1790, 2123366400},
        };

        UglyNumberII sv = new UglyNumberII();
        
        for(int[] input : inputs){
            System.out.println(String.format("n = %d, expect : %d", input[0], input[1] ));
            
            Assert.assertEquals(input[1], sv.nthUglyNumber_n(input[0]) );
            
            Assert.assertEquals(input[1], sv.nthUglyNumber_heap(input[0]) );
            
            Assert.assertEquals(input[1], sv.nthUglyNumber_dp(input[0]) );
        }

    }
}
