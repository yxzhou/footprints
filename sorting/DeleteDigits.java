package sorting;

import java.util.Random;

/**
 * 
 * Given string A representative a positive integer which has N digits, remove any k digits of the number, the remaining digits are arranged according to the original order to become a new positive integer.

	Find the smallest integer after remove k digits.
	
	N <= 240 and k <= N,
	
	Example
	Given an integer A = "178542", k = 4
	
	return a string "12"
 *
 */

public class DeleteDigits {

    /**
     *@param A: A positive integer which has N digits, A is a string.
     *@param k: Remove k digits.
     *@return: A string
     */
    public String DeleteDigits(String A, int k) {
        //check
    	if(null == A || k < 0 || A.length() <= k){
    		return "";
    	}
    	
    	StringBuilder sb = new StringBuilder();
    	int start = -1;
    	int end = k;
    	k = A.length() - k;
    	char[] digits = A.toCharArray();
    	for(int i = 0; i < k; i++ ){
    		start = findSmallest(digits, start + 1, end++);
    		
    		sb.append(A.charAt(start));
    	}
    	
    	for(int i = 0; i < sb.length(); ){
    		if('0' == sb.charAt(i)){
    			sb.deleteCharAt(i);
    		}else{
    			break;
    		}
    	}
    	
    	return sb.toString();
    }
	
    private int findSmallest(char[] nums, int start, int end){
    	int min = start;
    	
    	for(int i = start + 1; i <= end; i++){
    		if(nums[min] > nums[i]){
    			min = i;
    		}
    	}
    	
    	return min;
    }
    

    
    public static void main(String[] args){
    	String[] input = {"178542", "254193", "90249", "10009876091", "124"};
    	int[] kk = {4, 1, 2, 4, 0};
    	
    	DeleteDigits sv = new DeleteDigits();
    	for(int i = 0; i < input.length; i++){
    		System.out.println(String.format("Input: %s and %d", input[i], kk[i]));
    		
    		System.out.println(String.format("Output: %s", sv.DeleteDigits(input[i], kk[i])));
    	}
    	
    }
    
}
