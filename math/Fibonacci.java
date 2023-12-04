package math;

/**
 *
 * A Fibonacci sequence is defined as follow:
 *
 * The first two numbers are 0 and 1. F(0) = 0, F(1) = 1 The i th number is the sum of i-1 th number and i-2 th number.
 * F(n) = F(n-1) + F(n-2)
 *
 * The first ten numbers in Fibonacci sequence is: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34 ...
 *
 *
 *
 *
 */

public class Fibonacci {

    class UInt {

        int value = 0;

        public UInt(int input) {
            this.value = input;
        }
    }

    /**
     * matrix1 = |(m1+m2), m1| | m1, m2|
     *
     * matrix2 = |(f1+f2), f1| | f1, f2| so it records matrix1 with 2 variables (m1, m2), records matrix2 with 2
     * variables (f1, f2)
     *
     * matrix3 = = matrix1 * matrix2 ==> |(m1+m2)*(f1+f2) + m1*f1, (m1+m2)*f1 + m1*f2| 
     *                                   | m1*(f1+f2) + m2*f1,           m1*f1+m2*f2 | 
     * It still can records matrix3 with 2 variables (r1, r2) r1 = f1 * (m1 + m2) + f2 * m1; r2 = f1 * m1 + f2 * m2;
     */
    private static void matrix_multiply(UInt m1, UInt m2, UInt f1, UInt f2) {
        int r1 = f1.value * m1.value + f1.value * m2.value + f2.value
                * m1.value;
        int r2 = f1.value * m1.value + f2.value * m2.value;

        m1.value = r1;
        m2.value = r2;
    }

    /**
     * 
     * detail see <fibonacci.pdf>
     * 
     * F(n) = F(n-1) + F(n-2), 
     * F(1) = 1, 
     * F(0) = 0
     * 
     * (F(n+1), F(n)) = (F(n), 
     * F(n-1)) * A = (F(n-1), 
     * F(n-2)) * A * A = ( F(1),
     * F(0)) * (A^n) 
     * A = |1 1| = |(a1+a2), a1| 
     * (a1 = 1, a2 = 0) |1, 0| | a1, a2|
     * 
     * A^n = A^m * A^m // 
     * if n is Even ( n = 2m, m>=1) = A * ( A^m * A^m ) // 
     * if n is Odd ( n = 2m + 1, m>=1) 
     * 
     * e.g. A^13 = A * (A^12) = A * (A^6 * A^6) = A * ((A^3 * A^3)*(A^3 * A^3))
     * 
     * Time O(log n)
     * 
     * @param n
     * @return 
     */
    public long fibonacci_matrix(int n) {
        if (n < 0) {
            return -1; // ERROR CODE
        }
        if (n < 2) {
            return n; // 0, 1, 1, 2, 3, ...
        }
        
        UInt r1 = new UInt(0);
        UInt r2 = new UInt(1);

        UInt a1 = new UInt(1);
        UInt a2 = new UInt(0);

        for ( ; n > 0; n >>= 1) {

            if (1 == (n & 1)) {
                matrix_multiply(r1, r2, a1, a2);
            }

            matrix_multiply(a1, a2, a1, a2);
        }

        return r1.value;
    }



    /*
    * F(2k) = F(k)*F(k+1) + F(k-1)*(k) = F(k)*F(k) + 2*F(k)*F(k-1)
    * F(2k-1) = F(k)*F(k) + F(k-1)*F(k-1)
    * 
    * Time O(log flag) ( close to logn )
     */
    private long fibonacci_matrix_n(int n) {
        if (n < 0) {
            return -1; // ERROR CODE
        }
        if (n < 2) {
            return n; // 0, 1, 1, 2, 3, ...
        }

        int flag = n; //flag = 2^k,(flag < n, and k is the max, example, when n = 17, flag = 16)
        for (int value = n; (value &= value - 1) > 0;) {
            // extract_leftmost_one,
            flag = value;
        }

        int f2 = 1, f1 = 0;
        int d2, d1;
        while ((flag >>= 1) > 0) {
            // F(2k) = F(k)*F(k+1) + F(k-1)*(k) = F(k)*F(k) + 2*F(k)*F(k-1)
            // F(2k-1) = F(k)*F(k) + F(k-1)*F(k-1)
            d2 = f2 * f2 + 2 * f2 * f1;
            d1 = f2 * f2 + f1 * f1;

            f2 = d2;
            f1 = d1;

            if ((n & flag) > 0) {
                f2 = d2 + d1;
                f1 = d2;
            }
        }

        return f2;
    }

    /*
     * Example 
     *   Given 0, return 0 
     *   Given 1, return 1 
     *   Given 2, return 1 
     *   Given 9, return 34
     *
     * Time O(n)
     */
    public long fibonacci_itera(int n) {
        if (n < 0) {
            return -1; // ERROR CODE
        }
        if (n < 2) {
            return n; // 0, 1, 1, 2, 3, ...
        }
        
        /* f(n) = f(n-1) + f(n-2) */
        long a = 0;
        long b = 1;
        long sum;
        for (long i = 2; i <= n; i++) {
            sum = a + b;
            a = b;
            b = sum;
        }
        return b;
    }

    /**
     *
     * You are climbing a stair case. It takes n steps to reach to the top.
     *
     * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
     *
     *
     * Example:
     * Given an example n=3 , 1+1+1=2+1=1+2=3
     * return 3
     * 
     * Thoughts: 
     *  n = 1, return 1;
     *  n = 2, return 2;
     *  n = 3, return 1 + 2 = 3;
     *  n = 4, return 2 + 3 = 5;
     * 
     * @param n
     * @return 
     */
    public int climbStairs(int n) {
        if (n < 3) {
            return n; // 1, 2, 3, 5, ...
        } 

        int a = 1;
        int b = 2;
        int sum;
        for (int i = 3; i <= n; i++) {
            sum = a + b;
            a = b;
            b = sum;
        }

        return b;

    }
	
    /**
     *   Given 0, return 0 
     *   Given 1, return 1 
     *   Given 2, return 1 
     *   Given 9, return 34
     * 
     * Time O(2^n)
     * 
     * @param n
     * @return 
     */
    public long fibonacci_recursive(int n) {
        if (n < 0) {
            return -1; // ERROR CODE
        }
        if (n < 2) {
            return n; // 0, 1, 1, 2, 3, ...
        }
        
        return fibonacci_recursive(n - 1) + fibonacci_recursive(n - 2);

    }

    /**
     * double s = sqrt(5); 
     * f(n) = floor((pow((1+s)/2,n)-pow((1-s)/2,n))/s+0.5);
     *
     * Time O(1)
     * 
     * @param n
     * @return 
     */
    public long fibonacci_math(int n) {
        if (n < 1) {
            return -1; // ERROR CODE
        }
        if (n < 3) {
            return n - 1; // 0, 1, 1, 2, 3, ...
        }
        
        double sqrOf5 = Math.sqrt(5);

        return (long) Math.floor((Math.pow((1 + sqrOf5) / 2, n) - Math.pow((1 - sqrOf5) / 2, n)) / sqrOf5 + 0.5);
    }


    /**
     *  fibonacci define:
     *   f(i) = f(i - 1) + f(i - 2) 
     *          n:            0  1  2  3  4 
     *  fibonacci:            0, 1, 1, 2, 3, ...
     *          n: -3, -2, -1,
     *  fibonacci:  2, -1,  1,
     * 
     * Example:
     *   Given -1, return  1
     *   Given -2, return -1 
     *   Given -3, return  2 
     * 
     * @param n
     * @return 
     */
    public long fibonacci_negative(int n) {
        if (n >= 0) {
            return fibonacci_matrix(n);
        }

        /* f(n) = f(n+2) - f(n+1), n<0 */
        long b = 1;
        long a = 0;

        long c;
        for (int i = -1; i >= n; i--) {
            c = b - a;

            b = a;
            a = c;
        }

        return a;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        Fibonacci sv = new Fibonacci();
        
        System.out.println("\n call fibonacci_recursive ");
        for (int i = -1; i < 20; i++) {
            System.out.print(sv.fibonacci_recursive(i) + ", ");
        }
        
        System.out.println("\n call fibonacci_itera ");
        for (int i = -1; i < 20; i++) {
            System.out.print(sv.fibonacci_itera(i) + ", ");
        }
        
        System.out.println("\n call fibonacci_matrix ");
        for (int i = -1; i < 20; i++) {
            System.out.print(sv.fibonacci_matrix(i) + ", ");
        }
        
        System.out.println("\n call fibonacci_matrix_n ");
        for (int i = -1; i < 20; i++) {
            System.out.print(sv.fibonacci_matrix_n(i) + ", ");
        }
        
        System.out.println("\n\n--performance test--");

        int[] inputs = {10000, 100000, 1000000};
        long startTime;
        
        for (int input : inputs) {
            
            startTime = System.nanoTime();
            System.out.println(String.format("when n = %d, \tcall fib_matrix, \tget %d, \tcost_time: %d", input, sv.fibonacci_matrix(input), System.nanoTime() - startTime ) );
            
            startTime = System.nanoTime();
            System.out.println(String.format("when n = %d, \tcall fib_matrix_n, \tget %d, \tcost_time: %d", input, sv.fibonacci_matrix_n(input), System.nanoTime() - startTime ) );
            
        }

        System.out.println("\n\n--fibonacci_negative test--");
        System.out.print("Input n:");
        for (int input = -10; input < 6; input++) {
            System.out.print("\t" + input + ",");
        }
        
        System.out.print("\nfibonacci:");
        for (int input = -10; input < 6; input++) {
            System.out.print("\t" + + sv.fibonacci_negative(input) + "," );
        }

        System.out.println("\n\n---test end--\n"); 
    }

}
