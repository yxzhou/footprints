package fgafa.dailyCoding;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The area of a circle is defined as πr^2. Estimate π to 3 decimal places using a Monte Carlo method.

 Hint: The basic equation of a circle is x2 + y2 = r2.
 *
 * Tags: Google,
 */
public class CalculatePi {

    final double R = 1;

    /**
     *
     * @param n,  n between 1 to 31
     * @return
     */
    public double calculatePi_area(int n){
        double a = R;
        double b = R;

        int factor = 2;

        double pi = 0;

        for(int i = 0; i < n; i++, factor <<= 1){
            pi += a * b * factor;

            //System.out.println(i + " " + pi);
            double cc = a * a + b * b;
            a = Math.sqrt(cc) / 2;
            b = R - Math.sqrt( R * R - cc / 4) ;
        }

        return pi;
    }

    /**
     *
     * @param n,  n between 1 to 31
     * @return
     */
    public double calculatePi_perimeter(int n){
        double a = R;
        double b = R;

        int factor = 2;

        double pi = 0;

        for(int i = 0; i < n; i++, factor <<= 1){
            double cc = a * a + b * b;
            double c = Math.sqrt(cc);

            pi =  c * factor;

            //System.out.println(i + " " + pi);
            a = c / 2;
            b = R - Math.sqrt( R * R - cc / 4) ;
        }

        return pi;
    }

    public BigDecimal calculatePi_perimeter_bigdecimal(int n){
        BigDecimal a = new BigDecimal(R);
        BigDecimal b = new BigDecimal(R);

        BigDecimal bd1 = new BigDecimal(1);
        BigDecimal bd2 = new BigDecimal(2);
        BigDecimal bd4 = new BigDecimal(4);
        BigDecimal factor = bd2;

        BigDecimal pi = new BigDecimal(0);

        for(int i = 0; i < n; i++, factor = factor.multiply(bd2)){
            a = a.multiply(a);
            b = b.multiply(b);

            BigDecimal cc = a.add(b);
            BigDecimal c = bigSqrt(cc);

            pi =  c.multiply(factor);

            //System.out.println(i + " " + pi);

            a = c.divide(bd2);
            b = bd1.subtract(bigSqrt( bd1.subtract(cc.divide(bd4))));
        }

        return pi;
    }

    private static final BigDecimal SQRT_DIG = new BigDecimal(150);
    private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());

    /**
     * Private utility method used to compute the square root of a BigDecimal.
     *
     * @author Luciano Culacciatti
     * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
     */
    private static BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision){
        BigDecimal fx = xn.pow(2).add(c.negate());
        BigDecimal fpx = xn.multiply(new BigDecimal(2));
        BigDecimal xn1 = fx.divide(fpx,2*SQRT_DIG.intValue(), BigDecimal.ROUND_HALF_DOWN);
        xn1 = xn.add(xn1.negate());
        BigDecimal currentSquare = xn1.pow(2);
        BigDecimal currentPrecision = currentSquare.subtract(c);
        currentPrecision = currentPrecision.abs();
        if (currentPrecision.compareTo(precision) <= -1){
            return xn1;
        }
        return sqrtNewtonRaphson(c, xn1, precision);
    }

    /**
     * Uses Newton Raphson to compute the square root of a BigDecimal.
     *
     * @author Luciano Culacciatti
     * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
     */
    public static BigDecimal bigSqrt(BigDecimal c){
        return sqrtNewtonRaphson(c,new BigDecimal(1),new BigDecimal(1).divide(SQRT_PRE));
    }

    public static void main(String[] args){

        CalculatePi sv = new CalculatePi();

        final int TIMES = 60;

        long startTime = System.nanoTime();
        long endTime;
        double pi = sv.calculatePi_area(25);
        endTime = System.nanoTime();
        System.out.println( String.format("== cost_time(ns): %10d, pi=%.20f " , (endTime - startTime), pi ) );

        startTime = System.nanoTime();
        pi = sv.calculatePi_perimeter(25);
        endTime = System.nanoTime();
        System.out.println( String.format("== cost_time(ns): %10d, pi=%.20f " , (endTime - startTime), pi ) );

        startTime = System.nanoTime();
        BigDecimal bd_pi = sv.calculatePi_perimeter_bigdecimal(70);
        endTime = System.nanoTime();
        System.out.println( String.format("== cost_time(ns): %10d, pi=%.50f " , (endTime - startTime), bd_pi ) );
    }

}
