package math;

/**
 *
 * Fast Power   _https://www.lintcode.com/problem/140
 * Calculate the a^n % b where a, b and n are all 32bit integers.
 *
 * a, b and n are all 32-bit non-negative integers
 *
 *	Example
 *	For 2^31 % 3 = 2
 *	For 100^1000 % 1000 = 0
 *  For 3^5 % 7 = 5
 *  For 3^0 % 1 = 0
 *
 *	Challenge
 *	O(logn) time complexity
 *
 */

public class PowerAndMod {

    /*
     * @param a, b, n: 32bit integers
     * @return: An integer
     */
    public int fastPower(int a, int b, int n) {
        int r = 1;
        long c = a % b;

        while(n > 0){
            if((n & 1) == 1){
                r = (int)((c * r) % b);
            }

            c = (c * c) % b;
            n >>>= 1;
        }

        return r % b;
    }

}
