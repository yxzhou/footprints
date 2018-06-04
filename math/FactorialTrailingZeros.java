package fgafa.math;

/*
 * Given an integer n, return the number of trailing zeroes in n!.
 * 
 * Note: Your solution should be in logarithmic time complexity.
 */
public class FactorialTrailingZeros {

	/*
	 * CC 17.3
	 * 
	 * 1) the number of trailing zeros in n! depends on the number of 5 in [1, n]
	 * 2) the number of 5 in [1, n] is:  
	 *      n/5 + n/25 + --
	 * 
	 */
    public int trailingZeroes_WRONG(int n) {
        //check
//    	if( n < 1)
//    		return 0;
    	
    	int count = 0;
    	for(int i = 5; n/i > 0 ; i *= 5){
    		System.out.println(n + " " + i + " " + n/i);
    		count += n/i;
    	}
    		
    	return count;
    }
    

	public int trailingZeroes_X(int n) {
		int c = 0;

		while (n / 5 > 0) {
			n /= 5;
			//System.out.println(n);
			c += n;
		}

		return c;
	}

    /*
     * param n: As desciption
     * return: An integer, denote the number of trailing zeros in n!
     */
	public long trailingZeros_n(long n) {
		// check
		long sum = 0;
		while (n > 0) {
			n /= 5;

			sum += n;
		}

		return sum;
	}
    
	public static void main(String[] args){
		int[] input = {
				29,
				129,
				1808548329				
		};
		
		int[] result = {
				6,
				31,
				452137076
		};
		
		FactorialTrailingZeros sv = new FactorialTrailingZeros();
		
		for(int i = 0; i< input.length; i++ ){
			System.out.println(
					String.format("Input: %d, \t Output1: %d, \t Output2: %d, \t Output3: %d, \t Result: %d", 
					input[i],
					sv.trailingZeroes_WRONG(input[i]),
					sv.trailingZeroes_X(input[i]),
					sv.trailingZeros_n(input[i]),
					result[i]
					));
		}
	}
}
