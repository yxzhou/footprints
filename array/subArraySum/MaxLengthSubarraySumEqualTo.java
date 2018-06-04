package fgafa.array.subArraySum;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Given an array nums and a target value k, find the maximum length of a subarray that sums to k. 
 * If there isn't one, return 0 instead.
    
    Example 1:
    Given nums = [1, -1, 5, -2, 3], k = 3,
    return 4. (because the subarray [1, -1, 5, -2] sums to 3 and is the longest)
    
    Example 2:
    Given nums = [-2, -1, 2, 1], k = 1,
    return 2. (because the subarray [-1, 2] sums to 1 and is the longest)
    
    Follow Up:
    Can you do it in O(n) time?
 *
 */

public class MaxLengthSubarraySumEqualTo {

    public int maxSubArrayLen(int[] nums, int k) {
        if(null == nums || 0 == nums.length){
            return 0;
        }
        
        int sum = 0;
        int max = 0;
        Map<Integer, Integer> value2Position = new HashMap<>();
        value2Position.put(0, -1); // note
        
        for(int i = 0; i < nums.length; i++){
            sum += nums[i];
            
            if(value2Position.containsKey(sum - k)){
                max = Math.max(max, i - value2Position.get(sum - k));
            }
            
            if(!value2Position.containsKey(sum)){
                value2Position.put(sum, i);
            }
            
        }
        
        return max;
    }
}
