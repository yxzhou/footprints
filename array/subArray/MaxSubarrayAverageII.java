/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.subArray;

/**
 *
 * Given an array with positive and negative numbers, find the maximum average subarray which length should be greater or equal to given length k.
 * 
 * It's guaranteed that the size of the array is greater or equal to k.
 * 
 * Example 1:
 * Input: [1,12,-5,-6,50,3], 3
 * Output: 15.667
 * Explanation:  (-6 + 50 + 3) / 3 = 15.667
 * 
 * Example 2:
 * Input: [5] 1
 * Output: 5.000
 * 
 * Solution #1,  TLE  Time O(n*n)
 * 
 *    define sum[i] as the sum of array from nums[0] to nums[i], both inclusive.  
 *    define ma(i) as the max average subarray which length >= k
 *    ma[k - 1] = sum[k - 1] / k
 *    ma[k] = max( ma[k - 1], sum[k] / (k + 1),  (sum[k] - sum[0]) / k ) 
 *    ma[k + 1] = max ( ma[k], sum[k + 1] / (k + 2), (sum[k + 1] - sum[0]) / (k + 1), (sum[k + 1] - sum[1]) / (k) )
 * 
 *    ma[i] = max ( ma[i - 1], sum[i] / (i + 1), (sum[i] - sum[0]) / (i), (sum[i] - sum[1]) / (i - 1), ..,  (sum[i] - sum[i - k]) / (k),)  (i >= k)
 * 
 * Solution #2,    Time O(n*n)
 * 
 *    calculate sum[k - 1]
 *    
 *    for ( ; k < nums.length; k++ )
 *       sum = sums[k - 1]
 *       max = sum
 *       for( l = 0, r = k; r < nums.length; l++, r++)
 *           sum += nums[r] - num[l];
 *           max = Math.max(max, sum);
 *       maxAvg = Math.max(maxAvg, max / k)     
 * 
 * Solution $3, binary search,  Time O(n * log(max - min) )  refer to MaxSubarraySumIV.maxSubarray4
 *
 * 
 */
public class MaxSubarrayAverageII {
    
    /**
     * Solution #1
     * 
     */
    public double maxAverage_s1(int[] nums, int k) {
        if(nums == null || nums.length == 0 || nums.length < k ){
            throw new IllegalArgumentException(" nums is null or the length is smaller than k ");
        }
        
        k = Math.max(k, 1);
        
        int n = nums.length;
        long[] sums = new long[n + 1]; //define sum[i] as the sum of array from nums[0] to nums[i - 1], both inclusive.  
        sums[0] = 0;
        for(int i = 0, j = 1; i < k; i = j, j++){
            sums[j] = sums[i] + nums[i];
        }
        
        double result = (double)sums[k] / k;
        
        for(int r = k, j = k + 1; r < n; r = j, j++){
            sums[j] = sums[r] + nums[r];
            
            for(int len = j; len >= k; len--){
                result = Math.max(result, (double)(sums[j] - sums[j - len]) / len );
            }
        }
        
        return result;
    }
    
    public double maxAverage_s2(int[] nums, int k) {
        if(nums == null || nums.length == 0 || nums.length < k ){
            throw new IllegalArgumentException(" nums is null or the length is smaller than k ");
        }
        
        k = Math.max(k, 1);
        
        long preSum = 0;
        for(int i = 0; i < k; i++){
            preSum += nums[i];
        }
        
        int n = nums.length;
                
        long sum = preSum;
        long max = preSum;
        double result = (double)preSum / k; // result = Double.MIN_VALUE is not work here, not know why yet. 
        for(; k < n; k++){
            sum = preSum;
            max = preSum;
            
            for(int l = 0, r = k; r < n; l++, r++){
                sum += nums[r] - nums[l];
                max = Math.max(max, sum);
            }
            
            result = Math.max(result, (double)max / k);
            if(k < n){
                preSum += nums[k];
            }              
        }
        
        return result;
    }
    
    /**
     * Solution #3, binary search,  
     */
    
    public double maxAverage_s3(int[] nums, int k) {
       if(nums == null || nums.length == 0 || nums.length < k ){
            throw new IllegalArgumentException(" nums is null or the length is smaller than k ");
        }
        
        k = Math.max(k, 1);
        
        double maxAvg = Double.MIN_VALUE;
        double minAvg = Double.MAX_VALUE;
        for(int x : nums){
            maxAvg = Math.max(maxAvg, x);
            minAvg = Math.min(minAvg, x);
        }
    //System.out.println(String.format("minAvg-%,.2f, \tmaxAvg-%,.2f", minAvg, maxAvg));    

        double midAvg;
        while(maxAvg - minAvg > 0.00001){
            midAvg = minAvg + (maxAvg - minAvg) / 2;
            if(found(nums, k, midAvg)){
                minAvg = midAvg;
            }else{
                maxAvg = midAvg;
            }
        }
        
        return maxAvg;
    }    
    
    /**
     * refer to MaxSubarraySumIV.maxSubarray4
     * 
     */
    private boolean found(int[] nums, int k, double average){
        double sum = 0;
        for(int i = 0; i < k; i++){
            sum += nums[i] - average;
        }
        
        double preSum = 0;
        double preMinSum = 0; //0 means nothing selected
        //double max = sum;
        
        for(int l = 0, r = k; r < nums.length; l++, r++){
            sum += nums[r] - average;
            
            preSum += nums[l] - average;
            preMinSum = Math.min(preMinSum, preSum);
            
            //max = Math.max(max, sum - preMinSum);
            if(sum > preMinSum){
                return true;
            }
        }
        
        return sum > 0; //for case k == nums.length
    }
}
