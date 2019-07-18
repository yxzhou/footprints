package fgafa.math;

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
 * n is a 32-bit signed integer, within the range [−231, 231 − 1]
 *
 */

public class PowerII {

    final static double EXP = 0.000_000_000_000_1d;

    public double myPow(double x, int n) {
        if(n == 1){
            return x;
        }else if(n == 0){
            //0^0 ?
            return 1d;
            //return 1.0;
        }

        if( Math.abs(x - 0) < EXP ){
            if(n < 0){
                return Double.MAX_VALUE;
            }else{ //n > 1
                return 0d;
            }
        }

        boolean sign = false;
        if(x < 0 && ( (n & 1) == 1 )){
            sign = true;
        }

        boolean flag = (n < 0) ? true : false;

        x = Math.abs(x);
        long m = Math.abs((long)n); // avoid overflow when n is Integer.MIN_VALUE

        double factor = x;
        double base = 1;

        while(m > 0){
            if( (m & 1) == 1 ){
                base *= factor;
            }

            factor *= factor;

            m >>= 1;
        }

        if(flag){
            if(Double.isInfinite(base)){
                base = 0;
            }else{
                base = 1 / base;
            }
        }

        if(sign){
            base = -base;
        }

        return base;
    }


    public static void main(String[] args){

        PowerII sv = new PowerII();

        double[][] cases = {
                {0d, 0},
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
            System.out.println(String.format("pow(%f, %d) = %.3f", cases[i][0], (int)cases[i][1], sv.myPow(cases[i][0], (int)cases[i][1])));
        }
        //for(int x = 2, y = 1; y < 11; y++){
        //    System.out.println(String.format("pow(%d, %d) = %d", x, y, sv.myPow(x, y)));
        //}

    }

}
