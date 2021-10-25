/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.subSum;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;

/**
 *
 * Given an array of integers that is already sorted in ascending absolute order, find two numbers so that the sum of them equals a specific number.
 * 
 * The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be 
 * less than index2. Note: the subscript of the array starts with 0
 * 
 * Notes:
 *   You are not allowed to sort this array.
 *   It is guaranteed that all numbers in the nums is distinct.
 *   The length of nums is <= 100000.
 *   The number in nums is <= 10^9
 * 
 * Example
 * Input:  [0,-1,2,-3,4], 1
 * Output:  [[1,2],[3,4]]
 * Explanation:  nums[1] + nums[2] = -1 + 2 = 1, nums[3] + nums[4] = -3 + 4 = 1
 * You can return [[3,4],[1,2]], the system will automatically help you sort it to [[1,2],[3,4]]. But [[2,1],[3,4]] is invaild.
 * 
 * Challenge O(n) time complexity and O(1) extra space
 * 
 */
public class TwoSumVII {
    /**
     * binary search with the absolute value, 
     * Time O(n*logn) Space O(1)
     * 
     * @param nums: the input array
     * @param target: the target number
     * @return: return the target pair
     */
    public List<List<Integer>> twoSumVII_binarysearch(int[] nums, int target) {
        if (nums == null) {
            return Collections.EMPTY_LIST;
        }

        List<List<Integer>> result = new LinkedList<>();

        int l;
        for (int r = nums.length - 1; r > 0; r--) {
            l = binarySearch(nums, 0, r - 1, target - nums[r]);

            if (l > -1) {
                result.add(Arrays.asList(l, r));
            }
        }

        return result;
    }

    private int binarySearch(int[] nums, int l, int r, int x) {
        int mid;
        int y = Math.abs(x);
        int diff;
        while (l <= r) {
            mid = l + (r - l) / 2;

            if (nums[mid] == x) {
                return mid;
            } else {
                diff = Math.abs(nums[mid]) - y;
                if (diff == 0) {
                    if (nums[mid - 1] == x) {
                        return mid - 1;
                    } else if (nums[mid + 1] == x) {
                        return mid + 1;
                    } 
                    
                    return -1;
                    
                } else if (diff < 0) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }

        }

        return -1;
    }
    
    /**
     * 1) the two sum algorithm is:
     *    to sorted array A and target T, every element is distinct.
     *    step #1, check A[0] and A[n-1], 
     *      if the sum == target, exclude them from the array , check A[1] and A[n-2], 
     *      if the sum < target, exclude A[0] from the array.
     *      if the sume > target, exclude A[n-1] from the array, 
     *     every time it excludes one element, so the time complexity is O(n)  
     * 
     * 2) Think about the cases:
     *    -1 -2 -4
     *     1 2 4
     *    -1 2 -3 4 
     *     1 -2 4
     *  They are sorted in ascending absolute order.
     *  Same algorithm if it can find out the minIndex (virtual A[0] A[1] ...) and maxIndex (A[n-1] A[n-2] ...) 
     * 
     * Time O(n), Space O(1)
     * 
     * @param nums
     * @param target
     * @return 
     */
    public List<List<Integer>> twoSumVII_n(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return Collections.EMPTY_LIST;
        }

        List<List<Integer>> result = new LinkedList<>();
        
        //find the maxIndex and minIndex
        int maxIndex = 0;
        int minIndex = 0;
        for(int i = nums.length - 1; i > 0; i--){
            if(nums[i] > nums[i - 1]){//the maxIndex is the first bigger. 
                maxIndex = i;
                break;
            }
        }
        for(int i = nums.length - 1; i > 0; i--){
            if(nums[i] < nums[i - 1]){//the minIndex is the first smaller.
                minIndex = i;
                break;
            }
        }
        
        
        int diff;
        while( nums[minIndex] < nums[maxIndex] ){
            diff = nums[maxIndex] + nums[minIndex] - target;
            
            if(diff == 0){
                result.add(Arrays.asList(Math.min(minIndex, maxIndex), Math.max(minIndex, maxIndex)));
                minIndex = nextMinIndex(nums, minIndex);
                maxIndex = nextMaxIndex(nums, maxIndex);
            }else if(diff < 0){
                minIndex = nextMinIndex(nums, minIndex);
            }else {
                maxIndex = nextMaxIndex(nums, maxIndex);
            }
        }
        
        return result;
    }
    
    /**
     * default it's from right to left to find out the next max, ( nums[i] < nums[i - 1] )
     *   only when s == 0 or nums[s] <= 0, the next max is from left to right.
     */    
    private int nextMaxIndex(int[] nums, int s){
        int i;
        if(s == 0 || nums[s] <= 0){
            i = s + 1;
            while ( i < nums.length && nums[i] > 0 ){
                i++;
            }
        }else{ //s > 0 && nums[s] > 0
            i = s - 1;
            while ( i > 0 && nums[i] < nums[i - 1] ){
                i--;
            }
        }
        
        return i;
    }
    
    private int nextMinIndex(int[] nums, int s){
        int i;
        if(s == 0 || nums[s] >= 0){
            i = s + 1;
            while ( i < nums.length && nums[i] < 0 ){
                i++;
            }
        }else{ //s > 0 && nums[s] < 0
            i = s - 1;
            while ( i > 0 && nums[i] > nums[i - 1] ){
                i--;
            }
        }
        
        return i;
    }
    
    
    public static void main(String[] args){
        TwoSumVII sv = new TwoSumVII();
        
        //Assert.assertArrayEquals(message, expecteds, actuals);
        
    }
}
