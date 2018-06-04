package fgafa.array.subArraySum;


/**
 * 
 * Given an array of n positive integers and a positive integer s, find the
 * minimal length of a subarray of which the sum ≥ s. If there isn't one, return
 * -1 instead.
 * 
 * Example Given the array [2,3,1,2,4,3] and s = 7, the subarray [4,3] has the
 * minimal length under the problem constraint.
 * 
 * Challenge If you have figured out the O(n) solution, try coding another
 * solution of which the time complexity is O(n log n).
 *
 */
public class MinLengthSubarraySumBiggerThan {

    /*Time O(n)*/
    public int minSubArrayLen(int[] nums, int s) {
        // check
        if(null == nums || 0 == nums.length || 1 > s){
            return -1;
        }
        
        int minSize = Integer.MAX_VALUE;
        int sum = 0;
        int left = 0;
        int right = 0;
        while( right <= nums.length ){
            if(sum < s){
                if(right < nums.length){
                    sum += nums[right];
                }

                right++;
            }else{// >=
                minSize = Math.min(minSize, right - left);

                sum -= nums[left];
                left++;
            }
        }
        
        return minSize == Integer.MAX_VALUE ? -1 : minSize;
    }
    
    
    /**
     * 
     * Case #1,  {1, 1}  3
     * Case #2,  {3, 4}  2
     * Case #3,  {2,3,1,2,4,3}  7
     * 
      */
    
    public int minSubArrayLen_n(int s, int[] nums) {
        //check input
        if(null == nums || 0 == nums.length || s < 0){
            return 0;
        }
        
        int result = Integer.MAX_VALUE;
        int sum = 0;
        for(int left = -1, right = 0; right < nums.length; right++){
            if(sum < s){
                sum += nums[right];
            }
            
            while( sum >= s){
                result = Math.min(result, right - left);
                
                left++;
                sum -= nums[left];
            }
            
        }
        
        return result == Integer.MAX_VALUE ? 0 : result;  //**
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
