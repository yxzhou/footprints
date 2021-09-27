package math;

/*
 * 
 * A Fibonacci sequence is defined as follow:

 The first two numbers are 0 and 1. 	F(0) = 0, F(1) = 1
 The i th number is the sum of i-1 th number and i-2 th number.   F(n) = F(n-1) + F(n-2)

 The first ten numbers in Fibonacci sequence is:    0, 1, 1, 2, 3, 5, 8, 13, 21, 34 ...

 Example
 Given 0, return 0
 Given 1, return 1
 Given 2, return 1
 Given 10, return 55
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

	/*
	 * matrix1 = |(m1+m2), m1| | m1, m2|
	 * 
	 * matrix2 = |(f1+f2), f1| | f1, f2| so it records matrix1 with 2 variables
	 * (m1, m2), records matrix2 with 2 variables (f1, f2)
	 * 
	 * matrix3 = = matrix1 * matrix2 ==> |(m1+m2)*(f1+f2) + m1*f1, (m1+m2)*f1 +
	 * m1*f2| | m1*(f1+f2) + m2*f1, m1*f1+m2*f2 | It still can records matrix3
	 * with 2 variables (r1, r2) r1 = f1 * (m1 + m2) + f2 * m1; r2 = f1 * m1 +
	 * f2 * m2;
	 */
	private static void matrix_multiply(UInt m1, UInt m2, UInt f1, UInt f2) {
		int r1 = f1.value * m1.value + f1.value * m2.value + f2.value
				* m1.value;
		int r2 = f1.value * m1.value + f2.value * m2.value;

		m1.value = r1;
		m2.value = r2;
	}

	/*
	 * 
	 * detail see <fibonacci.pdf>
	 * 
	 * F(n) = F(n-1) + F(n-2), F(1) = 1, F(0) = 0
	 * 
	 * (F(n+1), F(n)) = (F(n), F(n-1)) * A = (F(n-1), F(n-2)) * A * A = ( F(1),
	 * F(0)) * (A^n) A = |1 1| = |(a1+a2), a1| (a1 = 1, a2 = 0) |1, 0| | a1, a2|
	 * 
	 * A^n = A^m * A^m // if n is Even ( n = 2m, m>=1) = A * ( A^m * A^m ) // if
	 * n is Odd ( n = 2m + 1, m>=1) e.g. A^13 = A * (A^12) = A * (A^6 * A^6) = A
	 * * ((A^3 * A^3)*(A^3 * A^3))
	 * 
	 * Time O(log n)
	 */

	public long fib_matrix(int n) {
		UInt r1 = new UInt(0);
		UInt r2 = new UInt(1);

		UInt a1 = new UInt(1);
		UInt a2 = new UInt(0);

		for (; n > 0; n >>= 1) {

			if (1 == (n & 1))
				matrix_multiply(r1, r2, a1, a2);

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
	private long fib_matrix_n(int n) {
		// check
		if (n < 0) {
			return -1; // ERROR CODE
		}
		if (n < 2) {
			return n;
		}

		int flag = n; //flag = 2^k,(flag < n, and k is the max, example, when n = 17, flag = 16)
		for (int value = n; (value &= value - 1) > 0; ){
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
	 * Time O(n)
	 */
	public long fib_itera(int n) {

		if (n < 0)
			return -1; // ERROR CODE
		if (n < 2)
			return n; // f0=0, f1=1

		/* f(n) = f(n-1) + f(n-2), n>=2 */
		long f0 = 0;
		long f1 = 1;
		long result = 0;
		for (long i = 2; i <= n; i++) {
			result = f0 + f1;
			f0 = f1;
			f1 = result;
		}
		return result;
	}

	/**
	 * 
	 * You are climbing a stair case. It takes n steps to reach to the top.

		Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
		
		
		Example
		Given an example n=3 , 1+1+1=2+1=1+2=3
		
		return 3
	 */
	
    public int climbStairs(int n) {
        //check
        if(n < 0 ){
            return 0;
        }else if(n < 2){
            return n;
        }
        
        int f0 = 1;
        int f1 = 1;
        int fx;
        for( ; n > 1; n--){
            fx = f0 + f1;
            
            f0 = f1;
            f1 = fx;
        }
        
        return f1;
    
    }
	
	/*
	 * Time O(2^n)
	 */
	public long fib_recursive(int n) {
		if (n < 0)
			return -1; // ERROR CODE
		if (n < 2)
			return n; // f0=0, f1=1

		return fib_recursive(n - 1) + fib_recursive(n - 2);

	}

	/*
	 * double s = sqrt(5); f(n) = floor((pow((1+s)/2,n)-pow((1-s)/2,n))/s+0.5);
	 * 
	 * Time O(1)
	 */
	public long fib_math(int n) {
		if (n < 0)
			return -1; // ERROR CODE
		if (n < 2)
			return n; // f0=0, f1=1

		double sqrOf5 = Math.sqrt(5);

		return (long) Math.floor((Math.pow((1+sqrOf5)/2,n)-Math.pow((1-sqrOf5)/2,n))/sqrOf5+0.5);
	}

	/*
	 *
	 * @param args
	 */
	public long fib_negative(int n){
		if(n >= 0){
			return fib_matrix(n);
		}

		/* f(n) = f(n+2) - f(n+1), n<0 */
		long f1 = 1;
		long f0 = 0;

		long fx;
		for( int i = -1; i >= n; i--){
			fx = f1 - f0;

			f1 = f0;
			f0 = fx;
		}

		return f0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Fibonacci sv = new Fibonacci();

		for (int input = 8; input <= 20; input++) {
			System.out.println("\n The fib_recursive(" + input + ") is:"
					+ sv.fib_recursive(input));

			System.out.println(" The fib_itera(" + input + ") is:"
					+ sv.fib_itera(input));

			System.out.println(" The fib_matrix(" + input + ") is:"
					+ sv.fib_matrix(input));

			System.out.println(" The fib_matrix_n(" + input + ") is:"
					+ sv.fib_matrix_n(input));
		}

		int[] inputs = {10000, 100000, 1000000};

		for (int input : inputs) {

			System.out.println("\n The fib_matrix(" + input + ") is:"
					+ sv.fib_matrix(input));

			System.out.println(" The fib_matrix_n(" + input + ") is:"
					+ sv.fib_matrix_n(input));
		}


		for(int input = -1; input >= -10; input--){
			System.out.println("\n The fib_negative(" + input + ") is:"
					+ sv.fib_negative(input));
		}

	}

}
