package fgafa.math;

/*
 * Write a program to check whether a given number is an ugly number.
 * 
 * Ugly numbers are positive numbers whose prime factors only include 2, 3,
 * 5. For example, 6, 8 are ugly while 14 is not ugly since it includes
 * another prime factor 7.
 * 
 * Note that 1 is typically treated as an ugly number.
 */

public class UglyNumber2 {

	public boolean isUgly(int num) {
		//check
		if(num < 1){
			return false;
		}
		
		//divide by 2 
		while(num > 1 && (num & 1) == 0){
			num >>= 1;
		}
		
		//divide by 3
		while(num > 1 && num % 3 == 0){
			num /= 3;
		}
		
		//divide by 5
		while(num > 1 && num % 5 == 0){
			num /= 5;
		}
		
		return num == 1;
	}
    
    
    
	public static void main(String[] args) {
		UglyNumber2 sv = new UglyNumber2();

		for(int k = 0; k < 31; k++ ){
			System.out.println(String.format("Input: %d, \tOutput:%b", k, sv.isUgly(k)));
		}
	}

}
