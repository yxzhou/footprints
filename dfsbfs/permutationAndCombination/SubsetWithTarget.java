package dfsbfs.permutationAndCombination;

import java.util.Arrays;

/**
 * _https://www.lintcode.com/problem/818
 *
 * Give an array and a target. We need to find the number of subsets which meet the following conditions:
 *   The sum of the minimum value and the maximum value in the subset is less than the target.
 *   The length of the given array does not exceed 50. target <= 100000. 
 * 
 * Example 1, Input: array = [1,5,2,4,3] target = 4 Output: 2 
 *   Explanation: Only subset [1],[1,2] satisfy the condition, so the answer is 2. 
 * Example 2, Input: array = [1,5,2,4,3] target = 5 Output: 5 
 *   Explanation: Only subset [1],[2],[1,3],[1,2],[1,2,3] satisfy the condition, so the answer is 5.
 *
 *
 */

public class SubsetWithTarget {
    /**
     * @param nums: the array
     * @param target: the target
     * @return the number of subsets which meet the following conditions
     */
    public long subsetWithTarget(int[] nums, int target) {
        
        //check array nums, move all number that smaller than target to the left  
        int i = 0;
        for(int j = 0, n = nums.length; j < n; j++){
            if( nums[j] < target ){
                nums[i++] = nums[j];
            }
        }

        Arrays.sort(nums, 0, i);

        long result = 0;
        int sum;
        for(int b = i - 1; b >= 0; b--){
            for(int s = 0; s < b; s++){
                sum = nums[s] + nums[b];

                if(sum < target){
                    //result +=  1 << (b - s - 1);   // wrong
                    //result +=  (long)1 << (b - s - 1);
                    result += (long)Math.pow(2, b - s - 1);
                }
            }

            result += (nums[b] << 1) < target ? 1 : 0;
        }

        return result;
    }
    
    public long subsetWithTarget_n(int[] nums, int target){
        if(null == nums || 0 == nums.length){
            return 0;
        }

        //check nums, move all number that smaller than target to the left  
        int low = 0;
        for(int j = 0, n = nums.length; j < n; j++){
            if( nums[j] < target ){
                nums[low++] = nums[j];
            }
        }

        Arrays.sort(nums, 0, low);

        long result = 0;
        int high = low;
        low = 0;
        while(low <= high) {
            if (nums[low] + nums[high] >= target) {
                high--;
            } else { // plus all valid subsets that includes the nums[low]
                result += (long)1 << (high - low);
                                
                low++;
            }
        }

        return result;
    }

}
