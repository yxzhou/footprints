package fgafa.array.subArraySum;

import fgafa.util.Misc;

import java.util.ArrayList;
import java.util.Arrays;

public class MaxSubarraySumDiff {

	
	/**
	 * 
	 * Given an array with integers. Find two non-overlapping subarrays A and B, which |SUM(A) - SUM(B)| is the largest.
	 * Return the largest difference.
	 * 
	 * Example For [1, 2, -3, 1], return 6
	 * 
	 * Note The subarray should contain at least one number
	 * 
	 * Challenge O(n) time and O(n) space.
	 */
    public int maxDiffSubArrays(ArrayList<Integer> nums) {
    	if(null == nums || 2 > nums.size()){
    		return Integer.MAX_VALUE; //error
    	}
    	
    	int size = nums.size();
    	
    	//subSum[0, --, i] - min[i+1, --, n]
    	int[] min = new int[size]; //default all are 0
    	min[size - 1] = nums.get(size - 1);
    	int subSum = nums.get(size - 1);
    	
    	for(int i = size - 2; i >= 0; i--){
    		subSum = Math.min(subSum, 0) + nums.get(i);
    		min[i] = Math.min(min[i + 1], subSum);
    	}
    	
    	int maxDiff = Integer.MIN_VALUE;
    	subSum = 0;
    	for(int i = 0; i < size - 1; i++){
    		subSum = Math.max(subSum, 0) + nums.get(i);
    		maxDiff = Math.max(maxDiff, subSum - min[i + 1]);
    	}
    	
    	//subSum[i, --, n] - min[0, --, i - 1]
    	min[0] = nums.get(0);
    	subSum = nums.get(0);
    	for(int i = 1; i < size - 1; i++){
    		subSum = Math.min(subSum, 0) + nums.get(i);
    		min[i] = Math.min(min[i - 1], subSum);
    	}
    	
    	subSum = 0;
    	for(int i = size - 1; i > 0; i--){
    		subSum = Math.max(subSum, 0) + nums.get(i);
    		maxDiff = Math.max(maxDiff, subSum - min[i - 1]);
    	}
    	
    	return maxDiff;
    }
	
	public static void main(String[] args) {
	    MaxSubarraySumDiff sv = new MaxSubarraySumDiff();
	    
	    Integer[][] input2 = {
	    		{ -5,3,-4,0,0,0,-1,20,1,1,-1,-1,-1,-1,-1},
	    		{-1,-2,-3,-100,-1,-50}
	    };
	    for(int i = 0; i< 2; i++){
	        System.out.println("\nThe original array is: "+Misc.array2String(input2[i]) );
	        
	        System.out.println("The value of max sub array is: "+ sv.maxDiffSubArrays(new ArrayList<Integer>(Arrays.asList(input2[i]))) );
	    }

	}

}
