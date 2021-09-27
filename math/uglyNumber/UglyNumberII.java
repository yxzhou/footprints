package math.uglyNumber;

import java.util.PriorityQueue;

/**
 * Leetcode #264
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
                        minHeap.add(x * factors[k]);
                    }

                    break;
                }
            }
        }

        return (int)x;
    }

    /**  Time O(n) ,  Space O(n)  n < 1690*/
    public int nthUglyNumber_dp(int n) {
        if (n < 1) {
            return -1;
        }
        if (n == 1) {
            return 1;
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


    /**
     * Write a program to find the nth super ugly number.

     Super ugly numbers are positive numbers whose all prime factors are in the given prime list primes of size k.
     For example, [1, 2, 4, 7, 8, 13, 14, 16, 19, 26, 28, 32] is the sequence of the first 12 super ugly numbers given primes = [2, 7, 13, 19] of size 4.

     Note:
     (1) 1 is a super ugly number for any given primes.
     (2) The given numbers in primes are in ascending order.
     (3) 0 < k ≤ 100, 0 < n ≤ 106, 0 < primes[i] < 1000.
     */

    public int nthSuperUglyNumber(int n, int[] primes) {
        if(null == primes || 0 == primes.length || n < 1){
            return 1;
        }

        int k = primes.length;
        int[] indexes = new int[k];
        int[] nexts = new int[k];

        int[] result = new int[n];
        result[0] = 1;

        int min;
        for (int i = 1; i < n; i++) {
            min = Integer.MAX_VALUE;
            for (int j = 0; j < k; j++) {
                nexts[j] = primes[j] * result[indexes[j]];
                min = Math.min(min, nexts[j]);
            }

            result[i] = min;

            // update the index
            for (int j = 0; j < k; j++) {
                if ( min == nexts[j]) {
                    indexes[j]++;
                }
            }
        }

        return result[n - 1];
    }


    public static void main(String[] args) {

        UglyNumberII sv = new UglyNumberII();

        int n = 10;
        int[] primes = {3,5,7}; //{3,5,7,11,19,23,29,41,43,47};

        sv.nthSuperUglyNumber(n, primes);

    }
}
