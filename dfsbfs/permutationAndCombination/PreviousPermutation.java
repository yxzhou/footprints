package fgafa.dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * 
 * Given a list of integers, which denote a permutation.
 * 
 * Find the previous permutation in ascending order.
 * 
 * Example
 * For [1,3,2,3], the previous permutation is [1,2,3,3]
 * 
 * For [1,2,3,4], the previous permutation is [4,3,2,1]
 * 
 * Note
 * The list may contains duplicate integers.
 *
 */
public class PreviousPermutation {
    /**
     * @param nums: A list of integers
     * @return: A list of integers that's previous permuation
     */
	/**
	 * cases: (use <= mark the previous permutation)
	 * 
	 * 112 <= 211 <= 121 <= 112
	 * 1323 <= 1233 <= 3321 <= 3312 <= 3231 <= 3213 <= 3132 <= 3123 <= 2331 <= 2313 <= 2133 <= 1332 <= 1323 
	 * 
	 * 
	 */
    public ArrayList<Integer> previousPermuation(ArrayList<Integer> nums) {
        
		//check
		if(null == nums){
		    return null;
		}
		
		ArrayList<Integer> result = new ArrayList<>(nums);
		if(2 > result.size()){
			return result;
		}
		
		int i = result.size() - 2;
		for( ; i >= 0 && result.get(i) <= result.get(i + 1); i--);
		
		if( -1 == i){
			reverse(result, 0, result.size() - 1);
		}else{
			int j = result.size() - 1;
			for( ; j > i && result.get(j) >= result.get(i); j--);
			
			swap(result, i, j);
			reverse(result, i + 1, result.size() - 1);
		}
		
		return result;
    }
    
    private void reverse(ArrayList<Integer> nums, int i, int j){
    	while(i < j){
    		swap(nums, i, j);
    		i++;
    		j--;
    	}
    }
    
    private void swap(ArrayList<Integer> nums, int i, int j){
    	int tmp = nums.get(i);
    	nums.set(i, nums.get(j));
    	nums.set(j, tmp);
    }
}
