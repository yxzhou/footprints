package leetcode;

/**
 * 
 * Given an integer n, count the total number of digit 1 appearing in all
 * non-negative integers less than or equal to n.
 * 
 * For example: Given n = 13, Return 6, because digit 1 occurred in the
 * following numbers: 1, 10, 11, 12, 13.
 *
 */

public class NumberOfDigitOne {

	/**
	 * Think about the NOD1 ( number of digit 1) at every position.
	 * when n = abcde, to c, the factor is 2, 
	 *   c=0,  NOD1 is ab * 10^2 
	 *   c=1,  NOD1 is ab * 10^2 + de +1
	 *   c>1,  NOD1 is (ab+1) * 10^2
	 * 
	 */
    public int countDigitOne(int n) {
        //check
    	if(n<1){
    		return 0;
    	}
    	
    	int count = 0;
    	int lowDigits = 0;  // in the above example, it's de
    	int highDigits = 0; // in the above example, it's ab
    	int currDigit = 0;  // in the above example, it's c.
    	for(long factor = 1; n >= factor; factor *= 10){  // note, it's long instead of int
    		lowDigits = n % (int)factor;
    		
    		highDigits = n / (int)factor;
    		currDigit = highDigits % 10 ;
    		highDigits = highDigits / 10;
    		
    		switch(currDigit){
    			case 0:
    				count += highDigits * factor;
    				break;
    			case 1:
    				count += highDigits * factor + lowDigits + 1;
    				break;
    			default:
    				count += highDigits * factor + factor;
    				break;
    		}
    		
    		//System.out.println(String.format("highDigits: %d - currDigit: %d - lowDigits: %d - factor: %d - count: %d", highDigits, currDigit, lowDigits, factor, count));
    	}
    	
    	return count;
    }
    
    
	public static void main(String[] args) {
		NumberOfDigitOne sv = new NumberOfDigitOne();
		int[] input = {
//				-1,
//				0,
//				1,
//				2,
//				9,
//				10,
//				11,
//				12,
//				20,
//				21,
//				22,
//				99,
//				109,
//				110,
//				111,
//				112,
//				120,
//				121,
//				122,
//				199,
//				999,
//				1000,
//				9999,
//				99999,
				1410065408
		};

		for(int n : input){
			System.out.println(String.format("Input: %d - %d", n, sv.countDigitOne(n) ));
		}
	}

}
