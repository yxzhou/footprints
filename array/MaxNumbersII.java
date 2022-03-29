/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array;

import java.util.Arrays;
import org.junit.Assert;

/**
 * Given two arrays of length m and n with digits 0-9 representing two numbers. Create the maximum number from digits of
 * the two. The relative order of the digits from the same array must be preserved. 
 * 
 * You should try to optimize your time and space complexity.
 * 
 * Example: 
 * 
 * Example 1: 
 * nums1 = [9, 8] nums2 = [9, 8, 3] 
 * return [9, 9, 8, 8, 3]
 *
 * Example 2: 
 * nums1 = [6, 7] nums2 = [6, 0, 4] 
 * return [6, 7, 6, 0, 4]
 * 
 * Thoughts: 
 *   greedy
 */
public class MaxNumbersII {
    
   public int[] maxNumber(int[] nums1, int[] nums2) {
       if(nums1 == null){
           return nums2 == null ? new int[0] : nums2;
       }
       if(nums2 == null){
           return nums1;
       }
       
       int n = nums1.length;
       int m = nums2.length;
       
       int[] result = new int[n + m];
       for(int p = 0, p1 = 0, p2 = 0; p1 < n || p2 < m; ){
           //System.out.println(String.format("--nums1: %s, p1 = %d \tnums2: %s, p2 = %d", Arrays.toString(nums1), p1, Arrays.toString(nums2), p2));
           
           result[p++] = compare(nums1, p1, nums2, p2) ? nums1[p1++] : nums2[p2++];
       }
       
       return result;
   }
   
    public boolean compare(int[] nums1, int start1, int[] nums2, int start2) { 
    
        //System.out.println(String.format("--nums1: %s, start1 = %d \tnums2: %s, start2 = %d", Arrays.toString(nums1), start1, Arrays.toString(nums2), start2));
        
        if(start1 == nums1.length){
            return false;
        }else if(start2 == nums2.length){
            return true;
        }
        
        int i = start1;
        int j = start2;
        for (; i < nums1.length && j < nums2.length; i++, j++) {   
            if (nums1[i] > nums2[j]) {
                return true;
            } else if (nums1[i] < nums2[j]) {
                return false;
            }
        }
        
        if(i == nums1.length && j == nums2.length){
            return true;  // nums1.equals(nums2), return true or false both ok
        }                
        
        if(i == nums1.length){
            int[] rests = new int[nums1.length + nums2.length - start2];
            
            System.arraycopy(nums2, j, rests, 0, nums2.length - j);
            System.arraycopy(nums1, start1, rests, nums2.length - j, nums1.length - start1);
            
            return compare(nums2, start2, rests, 0);
        }else if(j == nums2.length){
            int[] rests = new int[nums1.length + nums2.length - start1];
            System.arraycopy(nums1, i, rests, 0, nums1.length - i);
            System.arraycopy(nums2, start2, rests, nums1.length - i, nums2.length - start2);
            
            return compare(rests, 0, nums1, start1);
        }
        
        return true;
    }
    
   public int[] maxNumber_n(int[] nums1, int[] nums2) {
       if(nums1 == null){
           return nums2 == null ? new int[0] : nums2;
       }
       if(nums2 == null){
           return nums1;
       }
       
       int n = nums1.length;
       int m = nums2.length;
       
       int[] result = new int[n + m];
       for(int p = 0, p1 = 0, p2 = 0; p1 < n || p2 < m; ){
           //System.out.println(String.format("--nums1: %s, p1 = %d \tnums2: %s, p2 = %d", Arrays.toString(nums1), p1, Arrays.toString(nums2), p2));
           
           result[p++] = compare2(nums1, p1, nums2, p2) ? nums1[p1++] : nums2[p2++];
       }
       
       return result;
   }
    
    public boolean compare2(int[] nums1, int start1, int[] nums2, int start2) {
        
        
        for (; start1 < nums1.length && start2 < nums2.length; start1++, start2++) {
            if (nums1[start1] > nums2[start2]) {
                return true;
            }else if (nums1[start1] < nums2[start2]){
                return false;
            }
        }
        
        return start1 != nums1.length;
    }
    
    
    public static void main(String[] args) {
        
        MaxNumbersII sv = new MaxNumbersII();
        
        int[][][] inputs = {
            // nums1, nums2, expect
            {{9, 8, 3}, {9, 8}, {9, 9, 8, 8, 3}},
            {{9, 8}, {9, 8, 3}, {9, 9, 8, 8, 3}},
            {{6, 7}, {6, 0, 4}, {6, 7, 6, 0, 4}},
            {{6, 7}, {6, 7, 3}, {6, 7, 6, 7, 3}},
             {{6, 7, 3}, {6, 7}, {6, 7, 6, 7, 3}},
            {{6, 7}, {6, 7, 8}, {6, 7, 8, 6, 7}},
            {{6, 7, 8}, {6, 7}, {6, 7, 8, 6, 7}},
        };
          
        for(int[][] input : inputs){
            System.out.println( String.format("nums1: %s, nums2: %s", Arrays.toString(input[0]), Arrays.toString(input[1]) ));
            
            //System.out.println(Arrays.toString(sv.maxNumber(input[0], input[1])));
            //System.out.println(Arrays.toString(sv.maxNumber_n(input[0], input[1])));
            
            Assert.assertArrayEquals(input[2], sv.maxNumber(input[0], input[1]));
            Assert.assertArrayEquals(input[2], sv.maxNumber_n(input[0], input[1]));
            
        }
    }
}
