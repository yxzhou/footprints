/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.lis;

import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;
import util.Misc;

/**
 * Given a integer array, divided it into subsequences that are in strictly descending order. find the least number of
 * divided subsequences .
 * 
 * Example 1
 * Input [5,2,4,3,1,6]
 * Output 3
 * Explanation the subsequences can be {[5, 2, 1], [4,3], [6]} or { [5,4,3,1], [2], [6] }
 * 
 * Example 2
 * Input [1,1,1]
 * Output 3
 * Explanation the subsequences can be {[1], [1], [1]}
 * 
 * Challenge: do it with time O(nlogn) 
 */
public class LeastDecreasingSubsequences {
    public int LeastSubsequences_greedy(int[] nums) {
        if(nums == null){
            return 0;
        }
        
        int n = nums.length;
        Integer[] tails = new Integer[n]; 
        int r = -1; 
        
        int p;
        for(int x : nums){
            p = Arrays.binarySearch(tails, 0, r + 1, x, (a, b) -> a == b? -1 : a - b);

            if(p < 0){
                p = -p - 1;     
            }else{
                //while(p <= r && tails[p] == x){
                    p++;
                //}
            }
             
            tails[p] = x;
            r = Math.max(r, p);
        }
        
        return r + 1;
    }
    
    public int LeastSubsequences_greedy_2 (int[] nums) {
        if(nums == null){
            return 0;
        }
        
        int n = nums.length;
        int[] tails = new int[n];
        int r = -1; 
        
        int p;
        for(int x : nums){
            p = binarySearch(tails, 0, r + 1, x);
             
            tails[p] = x;
            r = Math.max(r, p);
        }
        
        return r + 1;
    }
    
    /**
     * 
     * @param nums
     * @param l, the left index, inclusive 
     * @param r, the right index, exclusive. And r is a valid index in nums. 
     * @param target
     * @return the index of the first element that is large than target, r if not found
     */
    private int binarySearch(int[] nums, int l, int r, int target){
        int mid; 
        
        while(l < r){
            mid = l + (r - l) / 2;
            
            if(nums[mid] <= target){
                l = mid + 1;
            }else{
                r = mid; //nums[mid] > target, r should be included. 
            }
        }
        
        return nums[l] > target? l : r;
    }
    
    public static void main(String[] args){
        
        LeastDecreasingSubsequences sv = new LeastDecreasingSubsequences();
        
        int[][] inputs = {
            {},
            {1},
            {5,2,4,3,1,6},
            {1,1,1},
            {2,3,4,5,6}
        };
        
        int[] expects = {0, 1, 3, 3, 5};
        
        for(int i = 0; i < inputs.length; i++){
            Misc.printArray_Int(inputs[i]);
            
            Assert.assertEquals(expects[i], sv.LeastSubsequences_greedy(inputs[i]));
            Assert.assertEquals(expects[i], sv.LeastSubsequences_greedy_2(inputs[i]));
        }
        
    }
}
