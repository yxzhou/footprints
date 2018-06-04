package fgafa.easy;

import java.util.HashSet;
import java.util.Set;

public class HappyNumber {
	
	/**
	 * Write an algorithm to determine if a number is "happy".
	 * 
	 * A happy number is a number defined by the following process: Starting
	 * with any positive integer, replace the number by the sum of the squares
	 * of its digits, and repeat the process until the number equals 1 (where it
	 * will stay), or it loops endlessly in a cycle which does not include 1.
	 * Those numbers for which this process ends in 1 are happy numbers.
	 * 
	 * Example: 19 is a happy number
	 * 
	 * 1^2 + 9^2 = 82 
	 * 8^2 + 2^2 = 68 
	 * 6^2 + 8^2 = 100 
	 * 1^2 + 0^2 + 0^2 = 1
	 * 
	 */
	public boolean isHappy(int n) {
		//check input
		if(n <1 ){
			return false;
		}
			
		Set<Integer> visited = new HashSet<>();
		int next;
		int digit;
		while(1 != n){
			if(visited.contains(n)){
				return false;
			}
			
			visited.add(n);
			
			next = 0;
			while( n> 0 ){
				digit = n%10;
				n = n/10;
				
				next += digit * digit;
			}
			n = next;
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		HappyNumber sv = new HappyNumber();
		
		int[] input = {0, 1, 2, 3, 4, 11,12, 13, 14, 15, 16, 17, 18, 19, 82, 68};
		
		for(int n : input){
			System.out.println( n + " \t " + sv.isHappy(n));

		}

	}

}
