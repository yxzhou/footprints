package fgafa.array;

import java.util.ArrayList;

/**
 * 
 * Given a rotated sorted array, recover it to sorted array in-place.
 * 
 * Example
 * [4, 5, 1, 2, 3] -> [1, 2, 3, 4, 5]
 * 
 * Challenge
 * In-place, O(1) extra space and O(n) time.
 * 
 * Clarification
 * What is rotated array?
 * 
 * For example, the orginal array is [1,2,3,4], The rotated array of it can be [1,2,3,4], [2,3,4,1], [3,4,1,2], [4,1,2,3]
 *
 */

public class RotateRecovery {
    /**
     * @param nums: The rotated sorted array
     * @return: void
     */
    public void recoverRotatedSortedArray(ArrayList<Integer> nums) {
        // check
        if(null == nums || 2 > nums.size()){
            return;
        }
        
        int rotatedPoint = 0;        	
        for( ; rotatedPoint < nums.size() - 1 && nums.get(rotatedPoint) <= nums.get(rotatedPoint + 1); rotatedPoint++);
        if(rotatedPoint == nums.size() - 1){
        	return;
        }
        
        reverse(nums, 0, rotatedPoint);
        reverse(nums, rotatedPoint + 1, nums.size() - 1);
        reverse(nums, 0, nums.size() - 1);
    }
    
    private void reverse(ArrayList<Integer> nums, int s, int e){
        while(s < e){
        	swap(nums, s, e);
        	s++;
        	e--;
        }
    }
    
    private void swap(ArrayList<Integer> nums, int i, int j){
        int tmp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, tmp);
    }
}
