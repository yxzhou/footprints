package fgafa.math.uglyNumber;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Leetcode #1201
 *
 * Write a program to find the n-th ugly number.
 *
 * Ugly numbers are positive integers which are divisible by a or b or c.
 *
 *
 * Example 1:
 * Input: n = 3, a = 2, b = 3, c = 5
 * Output: 4
 * Explanation: The ugly numbers are 2, 3, 4, 5, 6, 8, 9, 10... The 3rd is 4.
 *
 * Example 2:
 * Input: n = 4, a = 2, b = 3, c = 4
 * Output: 6
 * Explanation: The ugly numbers are 2, 3, 4, 6, 8, 9, 10, 12... The 4th is 6.
 *
 * Example 3:
 * Input: n = 5, a = 2, b = 11, c = 13
 * Output: 10
 * Explanation: The ugly numbers are 2, 4, 6, 8, 10, 11, 12, 13... The 5th is 10.
 *
 * Example 4:
 * Input: n = 1000000000, a = 2, b = 217983653, c = 336916467
 * Output: 1999999984
 *
 *
 * Constraints:
 * 1 <= n, a, b, c <= 10^9
 * 1 <= a * b * c <= 10^18
 * It's guaranteed that the result will be in range [1, 2 * 10^9]
 *
 */

public class UglyNumberIII {

    /**  Time O(n)  Space O(1)   n <= 10^9*/
    public int nthUglyNumber(int n, int a, int b, int c) {

        int[] factors = {a, b, c};
        Arrays.sort(factors);

        int m = 3;
//        for(int i = 1; i < m; i++){
//            for(int j = i - 1; j >= 0; j--){
//                if(factors[i] % factors[j] == 0 ){
//                    System.arraycopy(factors, i + 1, factors, i, m - i - 1);
//                    m--;
//                    break;
//                }
//            }
//        }

        long[] nexts = new long[m];
        long[] indexes = new long[m];
        Arrays.fill(indexes, 1l);

        long min = Integer.MAX_VALUE;
        for(int i = 1; i <= n; i++){
            min = Integer.MAX_VALUE;
            for(int k = 0; k < m; k++){
                nexts[k] = indexes[k] * factors[k];

                min = Math.min(min, nexts[k]);
            }

            for(int k = 0; k < m; k++){
                if(min == nexts[k]){
                    indexes[k]++;
                }
            }
        }

        return (int)min;
    }

    /**  Time O( logan )  Space O(1)   n <= 10^9*/
    public int nthUglyNumber_binarySearch(int n, int a, int b, int c) {

        long[] factors = {a, b, c};
        Arrays.sort(factors);

        long low = factors[0];
        long high = (int) Math.min(2l * 1e9, a * n );

        long ab = lcm(factors[0], factors[1]);
        long bc = lcm(factors[1], factors[2]);
        long ac = lcm(factors[0], factors[2]);
        long abc = lcm(ab, factors[2]);

        long mid;
        long x;
        while(low < high){
            mid = low + (high - low) / 2;

            x = mid / a + mid / b + mid / c - mid / ab - mid / bc - mid / ac + mid / abc;

//            if(x == n){
//                return (int)mid;
//            } else
            if( x < n){
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return (int)low;
    }

    private long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    private long gcd(long a, long b){
        if(b == 0){
            return a;
        }

        return gcd(b,  a%b);
    }


    @Test public void test(){
        Assert.assertEquals(4, nthUglyNumber(3, 2, 3, 5));
        Assert.assertEquals(6, nthUglyNumber(4, 2, 3, 4));
        Assert.assertEquals(10, nthUglyNumber(5, 2, 11, 13));
        Assert.assertEquals(1999999984, nthUglyNumber(1000000000, 2, 217983653, 336916467));

        Assert.assertEquals(4, nthUglyNumber_binarySearch(3, 2, 3, 5));
        Assert.assertEquals(6, nthUglyNumber_binarySearch(4, 2, 3, 4));
        Assert.assertEquals(10, nthUglyNumber_binarySearch(5, 2, 11, 13));
        Assert.assertEquals(1999999984, nthUglyNumber_binarySearch(1000000000, 2, 217983653, 336916467));
    }
}
