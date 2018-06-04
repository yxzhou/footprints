package fgafa.array.subArraySum;

import java.util.ArrayList;
import java.util.Arrays;

import fgafa.util.Misc;

public class MaxSubarraySum
{

	/**
	 * 
	 * 
	 * You are given an array of integers (both positive and negative). Find the
	 * continuous sequence with the largest sum. Return the sum.
	 * 
	 * EXAMPLE input: {2, -8, 3, -2, 4, -10} output: 5 [ eg, {3, -2, 4} ]
	 */

	/**
	 * Want to get the max subsum of subarray. 
	 * Define a f[i] as the max subsum of subarray that end with nums[i]
	 * 
	 */
	/* Time O(N) Space O(1) */
	public int maxSubArray_n(int[] nums) {
		// check
		if (null == nums || 0 == nums.length)
			return Integer.MIN_VALUE;

		int localSubSum = 0;
		int globalSubSum = Integer.MIN_VALUE;
		for (int num : nums) {
			localSubSum = Math.max(localSubSum, 0) + num;
			globalSubSum = Math.max(globalSubSum, localSubSum);
		}

		return globalSubSum;
	}


    
	/*It's only available to that the array is not with all negative numbers */
	protected int getMaxSum_wrong(int[] a) {
		int maxsum = 0;
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
			if (maxsum < sum) {
				maxsum = sum;
			} else if (sum < 0) { // how about if the array is not with all
									// negative numbers
				sum = 0;
			}
		}

		return maxsum;
	}
  


	/**
	 * Given an array of integers, find two non-overlapping subarrays which have
	 * the largest sum.
	 * 
	 * The number in each subarray should be contiguous.
	 * 
	 * Return the largest sum.
	 * 
	 * Example For given [1, 3, -1, 2, -1, 2], the two subarrays are [1, 3] and
	 * [2, -1, 2] or [1, 3, -1, 2] and [2], they both have the largest sum 7.
	 * 
	 * Note The subarray should contain at least one number
	 * 
	 * Challenge Can you do it in time complexity O(n) ?
	 */

	/** 
	 * Solution:
	 *   to every point nums[i], calculate the subSum of [0, i] and [i+1, n]
	 *   
	 * Time O(N) Space O(n) 
	 * */
    public int maxTwoSubArrays(ArrayList<Integer> nums) {
    	//check
    	if(null == nums || 2 > nums.size()){
    		return Integer.MIN_VALUE; //exception return
    	}
    	
    	//init
    	int max = Integer.MIN_VALUE;
    	
    	//get the forward and backward subSums
    	int[] forwardSubSums = forwardMaxSubArray(nums);
    	int[] backwardSubSums = backwardMaxSubArray(nums);
    	
    	//find the lowest point
    	for(int i = 0; i < nums.size() - 1; i++){
    		max = Math.max(max, forwardSubSums[i] + backwardSubSums[i + 1]);
    	}
    	
    	return max;
	}

	private int[] forwardMaxSubArray(ArrayList<Integer> nums){
		int size = nums.size();
		int localSubSum = 0;
		int[] globalSubSum = new int[size];
		localSubSum = nums.get(0);
		globalSubSum[0] = localSubSum;
		for(int i = 1; i < size; i++){
			localSubSum = Math.max(localSubSum, 0) + nums.get(i);
			globalSubSum[i] = Math.max(globalSubSum[i - 1], localSubSum);
		}
		
		return globalSubSum;
	}
	
	private int[] backwardMaxSubArray(ArrayList<Integer> nums){
		int size = nums.size();
		int localSubSum = 0;
		int[] globalSubSum = new int[size];
		localSubSum = nums.get(size - 1);
		globalSubSum[size - 1] = localSubSum;
		for(int i = size - 2; i >= 0; i--){
			localSubSum = Math.max(localSubSum, 0) + nums.get(i);
			globalSubSum[i] = Math.max(globalSubSum[i+1], localSubSum);
		}
		
		return globalSubSum;
	}
	
	/**
	 * Solution:
	 *   Refer to maxSubArray_dp(), 
	 *   define a f[0][i] as the max subsum of subarray that end with nums[i]
	 *   define a f[1][i] as the max subsum of two non-overlapping subarrays that end with nums[i]
	 *   
	 *   f[0][0] = nums[0];
	 *   f[0][i] = max(f[1][i - 1], 0) + nums[i],  (i > 0)
	 *   f[1][1] = f[0][0] + nums[1]
	 *   f[1][i] = max(f[1][i - 1], ---, f[0][i - 1] ) + nums[i], (i > 1)
	 *   
	 * Time O(N) Space O(n) 
	 */
    public int maxTwoSubArrays_dp(ArrayList<Integer> nums) {
		// check
		if (null == nums || 2 > nums.size()) {
			return Integer.MIN_VALUE; // exception return
		}

		int size = nums.size();
		int[] f = new int[size];
		f[0] = nums.get(0);

		for (int i = 1; i < size; i++) {
			f[i] = Math.max(f[i - 1], 0) + nums.get(i);
		}

		int twoSubSum = nums.get(0);
		int oneMax = Integer.MIN_VALUE;
		int twoMax = Integer.MIN_VALUE;
		for (int i = 1; i < size; i++) {
			oneMax = Math.max(oneMax, f[i - 1]);
			
			twoSubSum = Math.max(twoSubSum, oneMax) + nums.get(i);
			twoMax = Math.max(twoMax, twoSubSum);
		}

		return twoMax;
	}
    
    /*Time O(N) Space O(1) */
    public int maxTwoSubArrays_dp2(ArrayList<Integer> nums) {
		// check
		if (null == nums || 2 > nums.size()) {
			return Integer.MIN_VALUE; // exception return
		}

		int size = nums.size();

		int oneSubSum = nums.get(0);
		int twoSubSum = nums.get(0);
		int oneMax = Integer.MIN_VALUE;
		int twoMax = Integer.MIN_VALUE;
		for (int i = 1; i < size; i++) {
			oneMax = Math.max(oneMax, oneSubSum);
			
			twoSubSum = Math.max(twoSubSum, oneMax) + nums.get(i);
			twoMax = Math.max(twoMax, twoSubSum);
			
			oneSubSum = Math.max(oneSubSum, 0) + nums.get(i);
		}

		return twoMax;
	}
    
    /*Time O(N) Space O(1) */
    public int maxTwoSubArrays_dp3(ArrayList<Integer> nums) {
		// check
		if (null == nums || 2 > nums.size()) {
			return Integer.MIN_VALUE; // exception return
		}

		int size = nums.size();

		int oneSubSum = 0;
		int twoSubSum = nums.get(0);
		int oneMax = Integer.MIN_VALUE;
		int twoMax = Integer.MIN_VALUE;
		for (int i = 1; i < size; i++) {
			oneSubSum = Math.max(oneSubSum, 0) + nums.get(i - 1);
			oneMax = Math.max(oneMax, oneSubSum);
			
			twoSubSum = Math.max(twoSubSum, oneMax) + nums.get(i);
			twoMax = Math.max(twoMax, twoSubSum);
		}

		return twoMax;
	}
    
    public int maxTwoSubArrays_dp4(ArrayList<Integer> nums) {
		// check
		if (null == nums || 2 > nums.size()) {
			return Integer.MIN_VALUE; // exception return
		}

		int size = nums.size();

		int oneSubSum = 0;
		int twoSubSum = nums.get(0);
		int oneMax = Integer.MIN_VALUE;
		int twoMax = Integer.MIN_VALUE;
		for (int i = 1; i < size; i++) {
			oneSubSum = Math.max(oneSubSum, 0) + nums.get(i - 1);
			oneMax = Math.max(oneMax, oneSubSum);
			
			twoSubSum = Math.max(twoSubSum, oneMax) + nums.get(i);
			twoMax = Math.max(twoMax, twoSubSum);
		}

		return twoMax;
	}
	/**
	 * Given an array of integers and a number k, find k non-overlapping
	 * subarrays which have the largest sum.
	 * 
	 * The number in each subarray should be contiguous.
	 * 
	 * Return the largest sum.
	 * 
	 * Example Given [-1,4,-2,3,-2,3], k=2, return 8
	 * 
	 * Note The subarray should contain at least one number
	 */
    /* Time O(K*n) space O(n) */
    public int maxSubArray(ArrayList<Integer> nums, int k) {
        //check
    	if(null == nums || k < 1 || nums.size() < k){
    		return Integer.MIN_VALUE; //
    	}
    	
        int size = nums.size();
        int[][] f = new int[k+1][size];//default all are 0, 
        //f[k][len - 1] is the sum of max k non-overlapping subarray that includes nums.get(len - 1) 
        
        
        //calculate f[i][i - 1], i is [1, k]
        f[1][0] = nums.get(0);
        //f[i][i - 1] = f[i - 1][i - 2] + nums.get(i)
        for (int i = 2, j = i - 1; i < k+1; i++, j++) {
            f[i][j] = f[j][i - 2] + nums.get(j);
        }
        
        //calculate f[1][i], i is [1, len)
        //f[1][i] = max(f[1][i - 1], 0) + nums.get(i)
        for (int i = 1; i < size; i++) {
        	f[1][i] = Math.max(f[1][i-1], 0) + nums.get(i);
        }
        
        //calculate f[i][n], i is [2, k], n is [i-2, len)
        //f[i][n] = max(f[i][n-1], max(f[i - 1][i - 2], f[i - 1][i - 3], ---, f[i - 1][n - 1])) + nums.get(n)
        int max = Integer.MIN_VALUE;
        for (int i = 2; i < k+1; i++) {
        	max = f[i - 1][i - 2];
            for (int n = i;  n < size; n++) {
                max = Math.max(max, f[i - 1][n - 1]);
                f[i][n] = Math.max(max, f[i][n - 1]) + nums.get(n);
            }
        }
        
        max = Integer.MIN_VALUE;
        for (int i = k-1; i < size; i++){
            max = Math.max(f[k][i], max);
        }
        return max;
    }
    
    
  /**
   * @param args
   */
  public static void main(String[] args) {

    MaxSubarraySum sv = new MaxSubarraySum();
/**  **/
    //int[] arr = {-1, 1, -2, 3, 5, -1, 0};
    int[][] arr = {{-3,1,3,-3,4}, {-1, 1, -2, 3, 5, -1, 3, 0}, {0,-1}, {-2,-1, -3}, {2,1}, {-2,0}, {-1,2}, {0,0}, {1, -2, 3, 5, -3, 2 }, 
        {0, -2, 3, 5, -1, 2 },  {-9, -2, -3, -5, -3 }};


    for(int i = 0; i< 1; i++){
      System.out.println("\nThe original array is: "+Misc.array2String(arr[i]) );
      
      System.out.println("The value of max sub array is: "+ sv.maxSubArray_n(arr[i]) );
    }

    
    Integer[][] input2 = {
    		{ 1, 3, -1, 2, -1, 2},
    		{-1,-2,-3,-100,-1,-50}
    };
    for(int i = 0; i< 2; i++){
        System.out.println("\nThe original array is: "+Misc.array2String(input2[i]) );
        
        System.out.println("The value of max sub array is: "+ sv.maxTwoSubArrays(new ArrayList<Integer>(Arrays.asList(input2[i]))) );
        System.out.println("The value of max sub array is: "+ sv.maxTwoSubArrays_dp(new ArrayList<Integer>(Arrays.asList(input2[i]))) );
        
        System.out.println("The value of max sub array is: "+ sv.maxTwoSubArrays_dp2(new ArrayList<Integer>(Arrays.asList(input2[i]))) );
        
        System.out.println("The value of max sub array is: "+ sv.maxTwoSubArrays_dp3(new ArrayList<Integer>(Arrays.asList(input2[i]))) );
      }
    
  }
}
