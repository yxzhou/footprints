package fgafa.dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Given n and k, return the k-th permutation sequence.
 * 
 * Example
 * For n = 3, all permutations are listed as follows:
 * 
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * If k = 4, the fourth permutation is "231"
 * 
 * Note
 * n will be between 1 and 9 inclusive.
 * 
 * Challenge
 * O(n*k) in time complexity is easy, can you do it in O(n^2) or less?
 *
 */

public class PermutationSequence {
	
    /**
     * 康拓编码
     * 
     * @param n: n
     * @param k: the kth permutation
     * @return: return the k-th permutation
     */
   public String getPermutation(int n, int k) {
       //check
	   if(n < 1 || n > 9 || k < 1){
		   return null;
	   }
	   
	   int[] factorial = new int[n+1];
	   factorial[0] = 1;
	   List<Integer> digits = new LinkedList<Integer>();
	   for(int i = 1; i <= n; i++){
		   factorial[i] = factorial[i - 1] * i;
		   
		   digits.add(i);
	   }
	   
		k--;// to transfer it as begin from 0 rather than 1
		k = k % factorial[n];// to narrow it down into [0, n-1]
	   
	   StringBuilder result = new StringBuilder();
		for (int i = n - 1, j = 0; i >= 0; i--) {
			j = k / factorial[i];
			k = k % factorial[i];

			result.append(digits.get(j));
			digits.remove(j);
		}
	   
	   return result.toString();
   }

	public static void main(String[] args) {
		PermutationSequence sv = new PermutationSequence();
		
		for (int n = 4, k = 1; k < 32; k++) {
			System.out.println(sv.getPermutation(n, k));
		}

	}

}
