package fgafa.math;

/**
 * 
 * Calculate the a^n % b where a, b and n are all 32bit integers.

	Example
	For 231 % 3 = 2
	
	For 1001000 % 1000 = 0
	
	Challenge
	O(logn)
 *
 */

public class FastPower {

    /*
     * @param a, b, n: 32bit integers
     * @return: An integer
     */
    public int fastPower(int a, int b, int n) {
        //check
        if(n < 0 || b <= 0){
            return -1; //error code
        }
        if(1 == b){ //fastPower(31, 1, 0) is 0 instead of 1
            return 0;
        }
        if(0 == n){
            return 1;
        }
        
        if(0 == a || 1 == a){
            return a;
        }
    
        long result = 1;
        long factor = a > b ? a % b : a; 
        for( ; n > 0 ; n >>= 1 ){
            if( 1 == (n & 1)  ){
                result *= factor;
                result %= b;
            }
            
            factor *= factor;
            factor %= b;
        }

        //return (int)(result & 0x7fffffff) % b;
        return (int)result;
    }
	
}
