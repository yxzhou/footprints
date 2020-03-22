package fgafa.math.uglyNumber;

/**
 * Leetcode #263
 *
 *
 * Write a program to check whether a given number is an ugly number.
 * 
 * Ugly numbers are positive numbers whose prime factors only include 2, 3, 5.
 *
 * For example, 6, 8 are ugly while 14 is not ugly since it includes another prime factor 7.
 * 
 * Note that 1 is typically treated as an ugly number.
 *
 */

public class UglyNumber {

	public boolean isUgly(int num) {
		if(num < 1){
			return false;
		}
		if(num == 1){
			return true;
		}
		
		//divide by 2 
		while((num & 1) == 0){
			num >>= 1;
		}
		
		//divide by 3
		while(num % 3 == 0){
			num /= 3;
		}
		
		//divide by 5
		while(num % 5 == 0){
			num /= 5;
		}
		
		return num == 1;
	}
    
    
    
	public static void main(String[] args) {
		UglyNumber sv = new UglyNumber();

		for(int k = 0; k < 31; k++ ){
			System.out.println(String.format("Input: %d, \tOutput:%b", k, sv.isUgly(k)));
		}
	}

}
