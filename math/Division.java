package math;

/**
 * 
 * Divide two integers without using multiplication, division and mod operator.

	If it is overflow, return 2147483647
	
	Example
	Given dividend = 100 and divisor = 9, return 11.
 *
 */

public class Division {

	/*
	 * input n and m, both are positive, return the n/m Time O(log(n/m))
	 * 
	 * Tips: any int can be shown in binary, example: 10 = 2^3 + 2^1, 10/2 = 2^2
	 * + 2^0; 9 = 2^3 + 2^0, 9/2 = 2^2 + 0; so is it here, any n can be shown in
	 * m-nary, example: n = m^k1 + m^k2 + ---, n/m = m^(k1 - 1) + m^(k2 - 1) +
	 * --- k1 and k2 is the key.
	 */
	public int divide(int dividend, int divisor) {
		// check input
		if (0 == divisor)
			// throw new IllegalArgumentException("can't be divided by zero .");
			return Integer.MIN_VALUE;

		long n = abs((long) dividend);
		long m = abs((long) divisor);

		if (0 == m || n < m)
			return 0;

		int k = 0;
		long md = m;
		while (md < n) {
			md = md << 1;
			k++;
		}

		long r = 0; // store n/m
		while (n >= m) {
			if (n >= md) {
				r += (long) 1 << k;
				n -= md;
			}
			md = md >> 1;
			k--;
		}

		if ((dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0))
			r = 0 - r;

		if (r > Integer.MAX_VALUE)
			r = Integer.MAX_VALUE;
		if (r < Integer.MIN_VALUE)
			r = Integer.MIN_VALUE;

		return (int) r;
	}

	private long abs(long x) {
		return (x > 0) ? x : 0 - x;

	}

	public int divide_n(int dividend, int divisor) {
		// check input
		if (0 == divisor) {
			//throw new IllegalArgumentException("can't be divided by zero .");
			return Integer.MIN_VALUE;
		}

		boolean isNegative = false;
		if ((dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0)) {
			isNegative = true;
		}
		long n = Math.abs((long) dividend);
		long m = Math.abs((long) divisor);
		
		long result = 0;
//		if (n == 0 || m == 1) {
//			result = isNegative ? 0 - n : n;
//			
//			result = Math.max(Integer.MIN_VALUE, result);
//			result = Math.min(Integer.MAX_VALUE, result);
//			
//			return (int)result;
//		}

		long factor = m; // long instead of int
		long i = 1;
		while (n >= factor) {
			factor <<= 1;
			i <<= 1;
		}

		while (n >= m) {
			if (n >= factor) {
				result += i;
				n -= factor;
			}

			i >>= 1;
			factor >>= 1;
		}
		
		result = isNegative ? 0 - result : result;
		
		result = Math.max(Integer.MIN_VALUE, result);
		result = Math.min(Integer.MAX_VALUE, result);
		
		return (int)result;
		//return (int)(isNegative ? 0 - result : result);
	}
	
	public static void main(String[] args){
		Division sv = new Division();
		
	    /* test divide  */
	    int[] divident = {0, 0, 8, 8, -8, -8, 2147483647, -2147483648,-2147483648, -2147483648, -2147483648};    
	    int[] divisor = {0, 1, 3, -3, 3, -3, 2, -2147483648, 2, -3, -1};   
	    
	    for(int i=0; i<divident.length; i++){
	      
	      System.out.println(i + " divident: "+divident[i]+" divisor: "+ divisor[i]);
	      
	      System.out.println(String.format(" quotient: %d, %d" , sv.divide(divident[i], divisor[i]), sv.divide_n(divident[i], divisor[i])));
	      
	    } 
	}
}
