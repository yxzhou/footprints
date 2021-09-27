package leetcode.facebook;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * A positive integer is magical if it is divisible by either A or B.

 Return the N-th magical number.  Since the answer may be very large, return it modulo 10^9 + 7.

 Example 1:
 Input: N = 1, A = 2, B = 3
 Output: 2

 Example 2:
 Input: N = 4, A = 2, B = 3
 Output: 6

 Example 3:
 Input: N = 5, A = 2, B = 4
 Output: 10

 Example 4:
 Input: N = 3, A = 6, B = 4
 Output: 8


 Note:

 1 <= N <= 10^9
 2 <= A <= 40000
 2 <= B <= 40000
 *
 */

public class NthMagicalNumber {

    /**
     *  time O(max(A + B))
     *
     *  (the time complexity is decided by r -> m,  m = lcm / A + lcm / B - 1, when lcm = A * B,  m = A + B - 1;  )
     */
    public int nthMagicalNumber(int N, int A, int B) {
        if(A > B){
            return nthMagicalNumber(N, B, A);
        }

        int mod = 1_000_000_007;
        int lcm = lcm(A, B);
        int m = lcm / A + lcm / B - 1;
        int q = N / m;
        int r = N % m;

        long result = (long) q * lcm % mod;

        if(r == 0){
            return (int)result;
        }

        int[] next = {A, B};
        for( ; r > 1; r--){
            if(next[0] <= next[1]){
                next[0] += A;
            }else{
                next[1] += B;
            }
        }

        result += Math.min(next[0], next[1]);

        return (int) (result % mod);
    }

    // x <= y
    private int gcd(int x, int y){
        if(x == 0){
            return y;
        }

        return gcd(y % x, x);
    }

    private int lcm(int x, int y){
        return x * y / gcd(x, y);
    }

    /**
     *  time O(max( log(N * A * B))
     *
     *  (the time complexity is decided by r -> m,  m = lcm / A + lcm / B - 1, when lcm = A * B,  m = A + B - 1;  )
     */
    public int nthMagicalNumber_n(int N, int A, int B) {
        if (A > B) {
            return nthMagicalNumber(N, B, A);
        }

        int mod = 1_000_000_007;
        int lcm = lcm(A, B);

        long low = 0;
        long high = 40000l * 40000l * 1_000_000_000;
        long middle;
        while(low < high){
            middle = low + (high - low) / 2;

            if(middle / A + middle / B - middle / lcm < N){
                low = middle + 1;
            }else{
                high = middle;
            }
        }

        return (int)(low % mod);

    }

    @Test public void test(){
        Assert.assertEquals(2, nthMagicalNumber(1, 2, 3));
        Assert.assertEquals(6, nthMagicalNumber(4, 2, 3));
        Assert.assertEquals(10, nthMagicalNumber(5, 2, 4));
        Assert.assertEquals(8, nthMagicalNumber(3, 6, 4));

        Assert.assertEquals(2, nthMagicalNumber_n(1, 2, 3));
        Assert.assertEquals(6, nthMagicalNumber_n(4, 2, 3));
        Assert.assertEquals(10, nthMagicalNumber_n(5, 2, 4));
        Assert.assertEquals(8, nthMagicalNumber_n(3, 6, 4));
    }

}
