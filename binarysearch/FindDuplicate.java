package fgafa.binarysearch;

/**
 * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), 
 * prove that at least one duplicate number must exist. Assume that there is only one duplicate number,
 * find the duplicate one.

    Note:
    You must not modify the array (assume the array is read only).
    You must use only constant, O(1) extra space.
    Your runtime complexity should be less than O(n2).
    There is only one duplicate number in the array, but it could be repeated more than once.
  *
 */   

/*
 * Understand the problem:
    The problem was simple if not comes with the constraints. Let's review the constraints. 
    1. No change the array -- Otherwise, we could simply use the bucket sorting to put element i to A[i]. 
    2. No extra memory -- Otherwise, we could use a hash map to count the freq of each number
    3. Runtime complexity less than O(n^2) - Otherwise, we could use the brute-force solution to choose either 2 numbers and see if they are the same.
    
    Solution:
    1）  Use binary search. 
    2）  find the intersection in a loop.  Think about the index and value in the array. 
    If there is no duplicate in the array, there is no loop. example 213,  it's 0->2, 2->3, 3->end
 *  or, there is loop. example 2131, it's 0->2, 2->3, 3->1, 1->1, --
 *  The duplicate number is the intersection of the loop
 */

public abstract class FindDuplicate {
    
    /*Time complexity O(nlogn)  Space complexity O(1)*/
    public int findDuplicate_binarysearch(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
       
        int low = 1; 
        int high = nums.length - 1;
        int mid;
        int count;
        while(low <= high){
            mid = low + (high - low) >> 1;
            
            count = count(nums, mid);
            
            if(count <= mid ){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }
        
        return low;
    }
    
    private int count(int[] nums, int target){
        int count = 0;
        for(int num : nums){
            if(num <= target){
                count++;
            }
        }
        return count;
    }
    
    /*Time complexity O(n)  Space complexity O(1)*/
    public int findDuplicate_loop(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int slow = 0;
        int fast = 0;
        while(fast < nums.length && nums[fast] < nums.length ){
            slow = nums[slow];
            fast = nums[nums[fast]];
            
            if(slow == fast){
                fast = 0;
                
                while(fast != slow){
                    slow = nums[slow];
                    fast = nums[fast];
                }
                
                return fast;
            }
        }
       
        //no duplicate number found
        return 0;
    }
}
