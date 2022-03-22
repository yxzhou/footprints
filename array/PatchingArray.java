package array;

import java.util.Arrays;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/1290
 * 
 * Given a sorted positive integer array nums and an integer n, add/patch elements to the array such that any number in
 * range [1, n] inclusive can be formed by the sum of some elements in the array. Return the minimum number of patches
 * required.
 *
 * Example 1: 
 * nums = [1, 3], n = 6 
 * Return 1.
 * Explanation: Combinations of nums are [1], [3], [1,3], which form possible sums of: 1, 3, 4. 
 * Now if we add/patch 2 to nums, the combinations are: [1], [2], [3], [1,3], [2,3], [1,2,3]. 
 * Possible sums are 1, 2, 3, 4, 5, 6, which now covers the range [1, 6]. So we only need 1 patch.
 *
 * Example 2: 
 * nums = [1, 5, 10], n = 20 
 * Return 2. 
 * The two patches can be [2, 4].
 *
 * Example 3: 
 * nums = [1, 2, 2], n = 5 
 * Return 0.
 * 
 * Thoughts: Greedy
 * m1)
 * step1: Define the possible sums, 
 *   boolean[] sums = new boolean[n + 1]
 * 
 * step2: Calculate all combinations of nums [1, 5, 10]
 * 
 *   sums[0] = true;
 *   for(int x : nums){
 *      for(int j = n - x; j >= 0; j--){
 *          if(sums[j]){
 *              sums[j + x] = true;
 *          }
 *      }
 *   }
 * 
 * step3: Count the patch and calculate all combinations
 *   int count = 0;
 *   for(int x = 1; x <= n; x++){
 *      if(sums[x]){
 *          continue;
 *      }
 * 
 *      count++; //greedy, found a patch
 * 
 *      for(int j = n - x; j >= 0; j--){
 *          if(sums[j]){
 *              sums[j + x] = true;
 *          }
 *      }
 *   }
 * 
 *  Time complexity is O(n * n), space complexity is O(n)
 * 
 *  Especially when n is Integer.MAX_VALUE, it got the NegativeArraySizeException
 * 
 * m2)To nums = [1, 5, 10], n = 20
 * 
 * define count as the the minimum number of patches required.
 * 
 * when 1, 1 is in nums, the possible sums are {1}, the max_sum is 1, 
 * when 2, 2 is not in nums, and 2 is not in the possible sum, count++, the possible sums are {1, 2, 3}, the max_sum is 3,  
 * when 3, 3 is not in nums, 3 <= max_sum, means it's already in possible sums, continue
 * when 4, 4 is not in nums, and 4 is not in the possible sum, count++, the possible sums are {1, 2, 3, ... 7}, the max_sum is 7
 * when 5, 5 is in nums, the possible sums are {1, 2, 3, 5, 6, 7, ... 12}, the max_sum is 12
 * ...
 * 
 *      int count = 0;
        
        int m = nums.length;
        int index = 0;
        long maxSum = 0;  // note, it is long
        for(int k = 1; maxSum < n; k++){ 
            
            if(index < m && k >= nums[index]){
                index++;
                
                maxSum += k;  // here maybe integer overflow 
            }else if(k > maxSum){
                count++;
                
                maxSum += k;  // here maybe integer overflow 
            }// else k <= maxSum

        }
 * 
 * Time complexity is O(n), space complexity is O(1)
 * 
 * m3) continue on m2
 * To nums = [1, 5, 10], n = 20
 * 
 * define count as the the minimum number of patches required.
 * 
 *      int count = 0;
        
        int m = nums.length;
        int index = 0;
        for(long maxSum = 1; maxSum < n;  ){ // note, j is long, for overflow hwen n is very big
            
            if(index < m && maxSum >= nums[index]){
                maxSum += nums[index];
                index++;
            }else{
                count++;
                maxSum <<= 1;
            }
        }
 * 
 *  Time complexity is O( nums.length + log(n - sum_nums) ), space complexity is O(1)
 * 
 */

public class PatchingArray {

    /** 
     * Time complexity is O(n * n), space complexity is O(n)
     * 
     * only works when n is not big
     * when n is Integer.MAX_VALUE, it got the NegativeArraySizeException
     * 
     * @param nums: an array
     * @param n: an integer
     * @return: the minimum number of patches required
     */
    public int minPatches_m1(int[] nums, int n) {
        if (n < 1) {
            return 0;
        }
        if (nums == null || nums.length == 0) {
            return (int) Math.ceil(Math.log(n + 1) / Math.log(2));
        }

        boolean[] full = new boolean[n + 1]; //default all are false
        
        full[0] = true;
        for (int k : nums) {
            for (int j = n - k; j >= 0; j--) {
                if (full[j]) {
                    full[j + k] = true;
                }
            }
        }

        int count = 0;

        for (int k = 1; k <= n; k++) {
            if (full[k]) {
                continue;
            }

            count++;

            for (int j = n - k; j >= 0; j--) {
                if (full[j]) {
                    full[j + k] = true;
                }
            }
        }

        return count;
    }

    /**
     * Time complexity is O(n), space complexity is O(1)
     * 
     * @param nums
     * @param n
     * @return 
     */
    public int minPatches_m2(int[] nums, int n) {
        if (n < 1) {
            return 0;
        }
        if (nums == null || nums.length == 0) {
            return (int) Math.ceil(Math.log(n + 1) / Math.log(2)); // the pathes are {1, 2, 4, 8, ... }
        }
        
        int count = 0;
        
        int m = nums.length;
        int index = 0;
        long maxSum = 0;  // note, it is long
        for(int k = 1; maxSum < n; k++){ 
            
            if(index < m && k >= nums[index]){
                index++;
                
                maxSum += k;  // here maybe integer overflow 
            }else if(k > maxSum){
                count++;
                
                maxSum += k;  // here maybe integer overflow 
            }// else k <= maxSum

        }
        
        return count;
    }
    
    /**
     * Time complexity is O( nums.length + log(n - sum_nums) ), space complexity is O(1)
     * 
     * @param nums
     * @param n
     * @return 
     */
    public int minPatches_m2_n(int[] nums, int n) {
        if (n < 1) {
            return 0;
        }
        if (nums == null || nums.length == 0) {
            return (int) Math.ceil(Math.log(n + 1) / Math.log(2)); // the pathes are {1, 2, 4, 8, ... }
        }

        int count = 0;
        
        int m = nums.length;
        int index = 0;
        for(long maxSum = 1; maxSum < n;  ){ // note, j is long, for overflow hwen n is very big
            
            if(index < m && maxSum >= nums[index]){
                maxSum += nums[index];
                index++;
            }else{
                count++;
                maxSum <<= 1;
            }
        }
        
        return count;
    }
    
    public static void main(String[] args) {
        int[][][] inputs = {
            //{ nums, {n}, {expect}}
            {
                {1, 3}, {6}, {1}
            },
            {
                {1, 5, 10}, {20}, {2}
            },
            {
                {1, 2, 2}, {5}, {0}
            },
            {
                {2}, {17483647}, {24}
            },
            {
                {1,2,31,33}, {Integer.MAX_VALUE}, {28}  //2147483647
            }
                                
        };
        
        PatchingArray sv = new PatchingArray();
        
        for(int[][] input : inputs){
            System.out.println(String.format("\nnums: %s, n = %d", Arrays.toString(input[0]), input[1][0]));
            
            //Assert.assertEquals(input[2][0], sv.minPatches_m1(input[0], input[1][0]));
            
           Assert.assertEquals(input[2][0], sv.minPatches_m2(input[0], input[1][0]));
           Assert.assertEquals(input[2][0], sv.minPatches_m2_n(input[0], input[1][0]));
        }
        
    }

}
