/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package design.others.stream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/692
 *
 * Given an array and a window size that is sliding along the array, find the sum of the count of unique elements in
 * each window.
 *
 *
 * If the window size is larger than the length of array, just regard it as the length of the array (i.e., the window
 * won't slide).
 *
 * Example1 
 * Input: [1, 2, 1, 3, 3] 3 
 * Output: 5 
 * Explanation: 
 * First window [1, 2, 1], only 2 is unique, count is 1. 
 * Second window [2, 1, 3], all elements unique, count is 3. 
 * Third window [1, 3, 3], only 1 is unique, count is 1. 
 * sum of count = 1 + 3 + 1 = 5
 *
 * Example2 
 * Input: [1, 2, 1, 2, 1] 3 
 * Output: 3
 * 
 */
public class SlidingWindowUniqueElementSum {
    
    /**
     * Time O(n)  Space O(k) 
     * 
     * @param nums: the given array
     * @param k: the window size
     * @return the sum of the count of unique elements in each window
     */
    public int slidingWindowUniqueElementsSum(int[] nums, int k) {
        if(nums == null){
            return 0;
        }

        int n = nums.length; 

        Map<Integer, Integer> counts = new HashMap<>();
        int end = Math.min(n, k);
        int local = 0; // the unique number in current window
        
        int count;
        for(int i = 0; i < end; i++){
            count = counts.getOrDefault(nums[i], 0) + 1;
            counts.put(nums[i], count);
            if(count == 1){ // 0 -> 1
                local++; 
            }else if(count == 2){ // 1 -> 2
                local--;
            } //else count > 2, do nothing, for example 2 -> 3 
        }

        int total = local; //the total of unique number in each window, init as the sliding window [0, k)

        for(int i = end; i < n; i++ ){
            if(nums[i] != nums[i - k]){
                count = counts.get(nums[i - k]) - 1;
                counts.put(nums[i - k], count);
                if( count == 1){ // 2 -> 1
                    local++; 
                }else if( count == 0){ // 1 -> 0
                    local--;
                } // else count > 1, do nothing,  for example 3 -> 2 

                count = counts.getOrDefault(nums[i], 0) + 1;
                counts.put(nums[i], count);
                if(count == 1){ // 0 -> 1
                    local++; 
                }else if(count == 2){ // 1 -> 2
                    local--;
                } //else count > 2, do nothing, for example 2 -> 3 
            }

            total += local;
        }

        return total;
    }
    
    
    /**
     * Time O(n)  Space O(k) 
     * 
     * @param nums: the given array
     * @param k: the window size
     * @return the sum of the count of unique elements in each window
     */
    public int slidingWindowUniqueElementsSum_2(int[] nums, int k) {
        if(nums == null){
            return 0;
        }

        int n = nums.length; 
        int total = 0; //the total of unique number in each window

        Map<Integer, Integer> window = new HashMap<>(); // <value, count>
        int local = 0; // the unique number in current window
        
        int count;
        for(int i = 0; i < n; i++ ){
            if(i < k || nums[i] != nums[i - k]){
                if(i >= k){
                    count = window.get(nums[i - k]) - 1;
                    window.put(nums[i - k], count);
                    if( count == 1){ // 2 -> 1
                        local++; 
                    }else if( count == 0){ // 1 -> 0
                        local--;
                    } // else count > 1, do nothing,  for example 3 -> 2 
                }

                count = window.getOrDefault(nums[i], 0) + 1;
                window.put(nums[i], count);
                if(count == 1){ // 0 -> 1
                    local++; 
                }else if(count == 2){ // 1 -> 2
                    local--;
                } //else count > 2, do nothing, for example 2 -> 3 
            }

            if(i >= k - 1){
                total += local;
            }
        }

        return Math.max(total, local);
    }
    
    
    public static void main(String[] args){
        
        int[][][] inputs = {
            //{ nums, {k}, {expection} }
            {
                {1, 2, 1, 3, 3},
                {3},
                {5}
            },
            {
                {1, 2, 1, 2, 1},
                {3},
                {3}
            },
            {
                null,
                {1},
                {0}
            },
            {
                {1, 2, 1},
                {4},
                {1}
            }
              
        };
        
        SlidingWindowUniqueElementSum sv = new SlidingWindowUniqueElementSum();
        
        for(int[][] input : inputs){
            System.out.println(String.format("\n%s,\t%d, \t%d", Arrays.toString(input[0]), input[1][0], input[2][0] ));
            
            Assert.assertEquals(input[2][0], sv.slidingWindowUniqueElementsSum(input[0], input[1][0]));
            Assert.assertEquals(input[2][0], sv.slidingWindowUniqueElementsSum_2(input[0], input[1][0]));
        }
    }
}
