package array.subArraySum;

import util.Misc;

public class MinSubarraySum {

    /**
     * Given an array of integers, find the subarray with smallest sum.
     * Return the sum of the subarray
     * 
     * Note
     * The subarray should contain at least one integer.
     * 
     * Example
     * For[1, -1, -2, 1],  return -3
     * 
     * 
     * maxSubSum 反过来 
     */
    
    public int minSubArray_n(int[] nums) {
        // check
        if (null == nums || 0 == nums.length){
            return Integer.MAX_VALUE;
        }

        int subSum = 0;
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            subSum = Math.min(subSum, 0) + num;
            min = Math.min(min, subSum);
        }

        return min;
    }
    
    public static void main(String[] args) {

        MinSubarraySum sv = new MinSubarraySum();
    /**  **/
        //int[] arr = {-1, 1, -2, 3, 5, -1, 0};
        int[][] arr = {{3,-1,-3,3,-4}, {-3,1,3,-3,4}, {-1, 1, -2, 3, 5, -1, 3, 0}, {0,-1}, {-2,-1, -3}, {2,1}, {-2,0}, {-1,2}, {0,0}, {1, -2, 3, 5, -3, 2 }, 
            {0, -2, 3, 5, -1, 2 },  {-9, -2, -3, -5, -3 }, {1, -1, -2, 1}};


        for(int i = 0; i< arr.length; i++){
          System.out.println("\nThe original array is: "+Misc.array2String(arr[i]) );
          
          System.out.println("The value of min sub array is: "+ sv.minSubArray_n(arr[i]) );
        }
    }
}
