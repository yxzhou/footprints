package math;

/**
 * Super Pow  _https://www.lintcode.com/problem/1275
 *
 * Your task is to calculate a^b mod 1337 where a is a positive integer and b is an extremely large positive integer given in the form of an array.
 *
 * The length of b is in range [1, 1100]
 *
 * Example1:
 * Input: a = 2  b = [3]
 * Output: 8
 *
 * Example2:
 * Input: a = 2  b = [1,0]
 * Ouput: 1024
 *
 * Example 3:
 * Input: a = 1, b = [4,3,3,8,5,2]
 * Output: 1
 *
 * Example 4:
 * Input: a = 2147483647, b = [2,0,0]
 * Output: 1198
 *
 */

public class PowerAndModII {

    final int MOD = 1337;

    public int superPow(int a, int[] b) {
        int r = 1;
        int[] x = {a%MOD};
        for(int i = b.length - 1; i >= 0; i--){
            r = (superPow(b, i, x) * r) % MOD;
        }

        return r;
    }

    private int superPow(int[] b, int i, int[] x){
        int a = x[0];
        int n = b[i];

        int result = a;
        int t = 1;
        for(int m = 0; m < 10; m++){
            if(n == m){
                result = t;
            }

            t = (t * a) % MOD;
        }

        x[0] = t;
        return result;
    }

    public int superPow_n(int a, int[] b) {
        int r = 1;
        int x = a % MOD;
        for(int i = b.length - 1; i >= 0; i--){
            r = (superPow(x, b[i]) * r) % MOD;

            x = superPow(x, 10);
        }

        return r;
    }

    private int superPow(int a, int b){
        int r = 1;

        while(b > 0){
            if( (b & 1) == 1 ){
                r = (r * a) % MOD;
            }

            a = (a * a) % MOD;
            b >>>= 1;
        }

        return r;
    }


}
