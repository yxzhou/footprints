package math;

/**
 *
 * Implement pow(x, n), which calculates x raised to the power n (xn).
 *
 * Example 1:
 * Input: 2.00000, 10
 * Output: 1024.00000
 *
 * Example 2:
 * Input: 2.10000, 3
 * Output: 9.26100
 *
 * Example 3:
 * Input: 2.00000, -2
 * Output: 0.25000
 * Explanation: 2-2 = 1/22 = 1/4 = 0.25
 *
 * Note:
 * -100.0 < x < 100.0
 * n is a 32-bit signed integer, within the range [−2^31, 2^31 − 1]
 *
 * Solution:
 *   when n = 5,  5 = 0x101
 *       1      0       1
 *       x      x^2     x^4
 *
 *       x^5 = x * x^4
 *
 *   when n = 6, 6 = 0x110
 *       0      1       1
 *       x      x^2     x^4
 *
 *       x^6 = x^2 * x^4
 *
 * Special cases:
 *    case 1: 0^0, 2^0
 *    case 2: 0^-1
 *    case 3: 0^2
 *    case 4: 2^Integer.MIN_VALUE
 *
 */

public class PowerII {


    public double myPow(double x, int n) {
        long m = n;
        if(n < 0){
            x = 1/x;
            m = 0l - n;
        }

        double y = 1d;
        while(m > 0){
            if( (m & 1) == 1 ){
                y *= x;
            }
            x *= x;
            m >>>= 1;
        }

        return y;
    }


    public double myPow_n(double x, int n) {
        if(n == 0){ // case: 0^0 or 2^0
            return 1d;
        }

        final double THRESHOLD = .0000001d;
        long m = n;
        if(n < 0){
            if(Math.abs(x - 0) < THRESHOLD){ ///case: 0^-1
                return Double.MAX_VALUE;
            }

            x = 1 / x;
            m = -(long)n;
        }

        double y = 1d;

        while(m > 0){
            if((m & 1) == 1){
                y *= x;
            }

            x *= x;
            m >>>= 1;
        }

        return y;
    }

    public static void main(String[] args){

        PowerII sv = new PowerII();

        double[][] cases = {
                {0d, 0},
                {2d, 0},
                {0d, -2},
                {0d, -1},
                {0d, 1},
                {0d, 2},
                {2d, 5},
                {2d, 6},
                {2d, 7},
                {2d, 8},
                {2d, 9},
                {2d, 10},
                {2d, Integer.MIN_VALUE},
                {2d, Integer.MAX_VALUE},
                {-2d, Integer.MIN_VALUE},
                {-2d, Integer.MAX_VALUE}
        };

        for(int i = 0; i < cases.length; i++){
            System.out.println(String.format("\npow(%f, %d) = %.3f", cases[i][0], (int)cases[i][1], sv.myPow(cases[i][0], (int)cases[i][1])));
            System.out.println(String.format("pow(%f, %d) = %.3f", cases[i][0], (int)cases[i][1], sv.myPow_n(cases[i][0], (int)cases[i][1])));
        }
        //for(int x = 2, y = 1; y < 11; y++){
        //    System.out.println(String.format("pow(%d, %d) = %d", x, y, sv.myPow(x, y)));
        //}

    }

}
