/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

/**
 * Problem #1
 * Given a non-negative integer x, compute and return the square root of x.
 * Since the return type is an integer, the decimal digits are truncated, and only the integer part of the result is returned.
 * 
 * Note: You are not allowed to use any built-in exponent function or operator, such as pow(x, 0.5) or x ** 0.5
 * 
 * Problem #2
 * Implement double sqrt(double x) and x >= 0. Compute and return the square root of x.
 * The accuracy is kept at 12 decimal places.
 * 
 * Example 1:
 * Input: n = 2 
 * Output: 1.41421356
 * 
 * Example 2:
 * Input: n = 3
 * Output: 1.73205081
 * 
 * Solution #1, binary search,  Time O(log x)
 * 
 * It need pay attention to:
 *   1 to avoid value overflow on area = mid*mid,  
 *     when sqrt(int n),  the area need to be long,  or it can use mid == n / mid instead of area = mid * mid
 *     when sqrt(float n), the area need to be double
 *     when sqrt(double n), 
 *    
 *   3 to sqrt(float n) and sqrt(double),  it need epsilon check "equality" instead of "=="
 *   4 to sqrt(int n), to avoid dead loop, it's min = mid + 1. 
 *     to sqrt(float n) and sqrt(double), it's min = mid;
 * 
 * Solution #2  Newton's Method,  http://en.wikipedia.org/wiki/Newton%27s_method#Square%5Froot%5Fof%5Fa%5Fnumber
 * 
 * 
 */
public class SquareRoot {
    /**
     * test-cases:
     * 
     * input	output
     * 0		0
     * -1		0
     * 4		2
     * 5		2
     * 
     */

    public int sqrt_binarysearch(int x) {
//        if(x < 0){
//            throw new IllegalArgumentException(" x should not smaller than 0 ");
//        }
//        if(x <= 1){ // 0 or 1
//            return x;
//        }
        
        int l = 0;
        int r = x;
        int mid;
        long area;
        while (l < r) {
            mid = l + (r - l) / 2;
            area = (long) mid * mid; //(long) is very important
            
            if (area <= x && area + mid * 2 + 1 > x) {
                return mid;
            } else if (area > x) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        return l;
    }
    
    public int sqrt_binarysearch_1(int x) {
        if(x < 0){
            throw new IllegalArgumentException(" x should not smaller than 0 ");
        }
        if(x <= 1){ // 0 or 1
            return x;
        }
        
        int l = 0;
        int r = x;
        int mid;
        int tmp;
        while (l < r) {
            mid = l + (r - l) / 2;
            tmp = x / mid; 
            
            if(mid == tmp){ // avoid overflow with (mid * mid)
                return mid;
            }else if(mid < tmp){
                l = mid + 1;
            }else{
                r = mid;
            }
        }

        return r > x / r ? l : r;
    }

    public double sqrt_binarysearch(float n, final float e) {
        if (n < 0) {
            return Double.NaN;
        }

        double left = 0;
        double right = n;

        if (n < 1) {
            left = n;
            right = 1;
        }

        double mid;
        double ret;
        while (left < right) {
            mid = left + (double) (right - left) / 2;
            ret = (double) mid * mid;

            //this maybe WRONG!!, beasue e maybe used to check epsilon of square root instead of area
            if (java.lang.Math.abs(ret - n) < e) {
                return mid;
            }

            if (ret < n) {
                left = mid;
            } else {
                right = mid;
            }

            //System.out.println("left="+left+", right="+right+", mid="+mid);
        }

        return Double.NaN;

    }
  

    /**
     * @param x: a double
     * @return: the square root of x
     */
    public double sqrt_binarysearch(double x) {
        if (x < 0) {
            return Double.NaN;
        }        
        
        double epsilon = 0.000_000_000_001d;
        double min = 0d;
        double max = (x < 1? 1d : x); //for case: x < 1, x > 1 
        
        double mid;
        double area;
        while(Math.abs(max - min) > epsilon ){
            mid = min + (max - min) / 2;
            //System.out.println(String.format("max - %.3f, \tmin - %.3f, \tmid - %.3f ", max, min, mid));

            area = mid * mid;

            //if(Math.abs(area - x) < epsilon){ //this is not work because the epsilon for area is not enough
            //    return mid;
            //}else 
            if(area > x){
                max = mid;
            }else{
                min = mid;
            }
        }

        return max;
    }
  
   /*
   * There are many method of computing square roots,  here it's Newton's method
   *
   *  input, int n.
   * output, double x, x^2 is close to n.
   * 
   *  x^2 = n;
   *  f(x) = x^2 - n;  
   *  f'(x) = 2x;   
   * 
   *  f'(x0) = (f(x0) - 0)/(x0 - x1)  // 2 points of (x0, f(x0)) and (x1, 0),  y' = Δy / Δx
   *
   *  => x1 = x0 - f(x0)/f'(x0)
   *  => x1 = x0 - (x0^2 - n) / (2x0)
   *  
   *  estimate x0 = n/4;  
   *  x1 = x0 - (x0^2 - n) / (2x0);
   *  x2 = x1 - (x1^2 - n) / (2x1);
   *  ---  
   * 
   *  e.g: 
   *  x^2 = 612
   *  estimate x0 = 10;
   *  x1 = x0 - f(x0)/f'(x0) = 10 -  (10*10 - 612)/(2*10)       = 35.6
   *  x2 = x1 - f(x1)/f'(x1) = 35.6- (35.6*35.6 - 612)/(2*35.6) = 26.395505617
   *  x3 = x2 - f(x2)/f'(x2) = ---                              = 24.790635492
   *  x4 = x3 - f(x3)/f'(x3) = ---                              = 24.738688294
   *  x5 = x4 - f(x4)/f'(x4) = ---                              = 24.738633753
   *  ---
   */
    public double sqrt_Newton_1(float n) {
        if (n < 0) {
            return Double.NaN;
        }

        double x = Math.max((double) n / 4, 1);

        double area;
        while (true) {
            x = x - (double) (x * x - n) / (double) (2 * x);

            area = (double) x * x;
            if (Math.abs(area - n) < 0.00001) {
                return x;
            }

            //System.out.println( i + " --- " + x);
        }
    }
    
    public double sqrt_Newton_1(double n) {
        if (n < 0) {
            return Double.NaN;
        }
        
        double epsilon = 0.000_000_000_001d;
        double x = Math.max( n / 4, 1);

        while (true) {
            x = x - (x * x - n) / (2 * x);

            if (Math.abs(x * x - n) < epsilon) {
                return x;
            }

            //System.out.println( i + " --- " + x);
        }
    }
    
    /**
     * 
     * 
     *  Newton
     *    define t = sqrt(n), 
     *    
     *    n = t * t
     *    n + t * t = 2 * t * t
     *    (n / t) + t = 2 * t
     *    t = (n / t + t) / 2
     * 
     */
    
    public int sqrt_Newton_n(int n) {
        if(n < 0){
            throw new IllegalArgumentException(" x should not smaller than 0 ");
        }
        
        double guess = 1.0;
        while (Math.abs(n - guess * guess) > 0.1 ) {
            guess = (n / guess + guess) / 2;
        }
        return (int)guess;
    }
    
    public int sqrt_Newton_n2(int n) {
        if(n < 0){
            throw new IllegalArgumentException(" x should not smaller than 0 ");
        }        
        if (n < 2) {
            return n;
        }

        double guess = n;
        double result = (guess + n / guess) / 2.0;
        while (Math.abs(result - guess) >= 1) {
            guess = result;
            result = (guess + n / guess) / 2.0;
        }

        return (int) result;
    }
    
        
    public double sqrt_Newton_n(double n) {
        if (n < 0) {
            return Double.NaN;
        }
        
        double epsilon = 0.000_000_000_001d;

        double guess = Math.max( n / 4, 1d);

        double result = (guess + n / guess) / 2.0;
        while (Math.abs(result - guess) > epsilon) {
            guess = result;
            result = (guess + n / guess) / 2.0;
        }

        return result;
    }

    
    public static void main(String[] args) {
        System.out.println(" 100/9: \t" + (100 / 9));  //11
        System.out.println(" -100/9: \t" + (-100 / 9));  // -11

        SquareRoot s = new SquareRoot();
    
        /* test square root    */
        int[] nums = {999999999};
        
        double sqrt;
        for (int i = 0; i < nums.length; i++) {
            System.out.print("\nThe square root of ");
            System.out.format("%d", nums[i]);

            sqrt = s.sqrt_Newton_n(nums[i]);
            System.out.println("\n with Newton's method: \t" + sqrt);

            sqrt = s.sqrt_binarysearch(nums[i]);
            System.out.println(" with binary search: \t" + sqrt);

            sqrt = s.sqrt_binarysearch_1(nums[i]);
            System.out.println(" with binary search: \t" + sqrt);
        }

        //float fn ;
        float[] fn = {-1, 0, 1, 3, 0.01f, 0.25f, 0.0000001f, 1.44f, 25, 100, 10000, 100000, 1000000, 10000000, 100000000};
        
        for (int i = 0; i < fn.length; i++) {
            System.out.print("\nThe square root of ");
            System.out.format("%,.3f", fn[i]);

            sqrt = s.sqrt_Newton_n(fn[i]);
            System.out.println(String.format("\n with Newton's method: %,.3f \t", sqrt));

            sqrt = s.sqrt_binarysearch(fn[i]);
            System.out.println(String.format(" with binary search: %,.3f \t", sqrt));

//            sqrt = s.sqrt_binarysearch_1(fn[i]);
//            System.out.println(String.format(" with binary search: %,.3f \t", sqrt));
        }
      
    }
}
