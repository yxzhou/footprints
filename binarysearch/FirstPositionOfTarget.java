package binarysearch;

import util.Misc;

/**
 * 
 * For a given sorted array (ascending order) and a target number, find the first index of this number in O(log n) time complexity.

	If the target number does not exist in the array, return -1.
	
	Example
	If the array is [1, 2, 3, 3, 4, 5, 10], for given target 3, return 2.
	
	Challenge
	If the count of numbers is bigger than 2^32, can your code work properly?
 *
 */

public class FirstPositionOfTarget {

    /**
     * @param nums: The integer array.
     * @param target: Target to find.
     * @return: The first position of target. Position starts from 0.
     */
    public int binarySearch(int[] nums, int target) {
        //check
    	if(null == nums || 0 == nums.length){
    		return -1;
    	}
    	
    	int low = 0;
    	int high = nums.length - 1;
    	int mid;
    	while(low <= high){
    		mid = low + ((high - low) >> 1);
    		
    		if(nums[mid] >= target){
    			high = mid - 1;
    		}else{
    			low = mid + 1;
    		}
    	}
    	
    	return low < nums.length && nums[low] == target ? low : -1;
    }
	
    
    public static void main(String[] args){
    	FirstPositionOfTarget sv = new FirstPositionOfTarget();
    	
    	int[][] inputs = {
    			{1},
    			{1},
    			{1},
    			{1, 2},
    			{1, 2},
    			{1, 2},
    			{1, 2},
    			{2, 2},
    			{2, 2},
    			{2, 2},
    			{1, 2, 2},
    			{1, 2, 2},
    			{1, 2, 2},
    			{2, 2, 3},
    			{2, 2, 3},
    			{2, 2, 3},
    			{2, 2, 2},
    			{2, 2, 2},
    			{2, 2, 2}
    	};
    	
    	int[] targets = {
    			0,
    			1, 
    			2,
    			0,
    			1,
    			2,
    			3,
    			1,
    			2,
    			3,
    			0,
    			2,
    			3,
    			0,
    			2,
    			4,
    			1,
    			2,
    			3
    	};
    	
    	for(int i = 0; i < targets.length; i++){
    		System.out.println(String.format("Input: %s, %d", Misc.array2String(inputs[i]), targets[i]));
    		System.out.println(String.format("Output: %d", sv.binarySearch(inputs[i], targets[i])));
    	}
    }
}
