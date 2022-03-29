package array;

import java.util.Arrays;
import org.junit.Assert;

import util.Misc;
import util.TimeCost;


/**
 * _https://www.lintcode.com/problem/552
 * 
 * Create Maximum Number
 *
 * Given two arrays of length m and n with digits 0-9 representing two numbers. Create the maximum number of length 
 * k <= m + n from digits of the two. The relative order of the digits from the same array must be preserved. 
 * Return an array of the k digits. You should try to optimize your time and space complexity.
 *
 * Example 1: 
 * nums1 = [3, 4, 6, 5] nums2 = [9, 1, 2, 5, 8, 3] k = 5 
 * return [9, 8, 6, 5, 3]
 *
 * Example 2: 
 * nums1 = [6, 7] nums2 = [6, 0, 4] k = 5 
 * return [6, 7, 6, 0, 4]
 *
 * Example 3: 
 * nums1 = [3, 9] nums2 = [8, 9] k = 3 
 * return [9, 8, 9]
 *
 * Thoughts:
 * m1) 2D -> 1D
 *   maxNumber(nums1, nums2, k) = max{
 *       merge( maxNumber(nums1, 0),  maxNumber(nums2, k) ),
 *       merge( maxNumber(nums1, 1),  maxNumber(nums2, k - 1) ),
 *       ...
 *       merge( maxNumber(nums1, k),  maxNumber(nums2, 0) ),
 *   )
 * 
 *  Time complexity of maxNumber(nums, k) is O(n)
 *  Time complexity of merge( ) is O(k * k)
 *  
 *  Total complexity is O(k * ((n + m) + k * k + k)) space O(k)
 * 
 * m2)interval tree, optimize the maxNumber(nums1, k) from O(n) to O(logn)
 *  Total complexity is O((n + m) + k * ((logn + logm) + k * k + k)) space O(k)
 * 
 * m3)interval tree,
 *    
 * 
 * 
 * 
 * 
 */

public class MaxNumbersIII {

   public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        if((nums1 == null && nums2 == null) || k < 1){
            return new int[0];
        }else if(nums1 == null){
            return maxNumber(nums2, k);
        }else if(nums2 == null){
            return maxNumber(nums1, k);
        }
       
        int[] result = new int[k];
        
        int[] tmp= new int[k];
        int[] arr1;
        int[] arr2;
        for (int i = Math.max(k - nums2.length, 0); i <= Math.min(nums1.length, k); i++) {
            arr1 = maxNumber(nums1, i);
            arr2 = maxNumber(nums2, k - i);

            for (int p1 = 0, p2 = 0, p = 0; p1 < arr1.length || p2 < arr2.length; ) {
                tmp[p++] = compare(arr1, p1, arr2, p2) ? arr1[p1++] : arr2[p2++];
            }
            
            if (compare(tmp, 0, result, 0)){
                System.arraycopy(tmp, 0, result, 0, k);
            }
        }

        return result;
    }
 
    private boolean compare(int[] nums1, int start1, int[] nums2, int start2) { 
        
        for (; start1 < nums1.length && start2 < nums2.length; start1++, start2++) {
            if (nums1[start1] > nums2[start2]) {
                return true;
            }else if (nums1[start1] < nums2[start2]){
                return false;
            }
        }
        
        return start1 != nums1.length;
    }
 
    private int[] maxNumber(int[] nums, int k) {
        int[] result = new int[k];

        for (int i = 0, j = 0, n = nums.length;  i < n; i++) {
            while (j > 0 && i < n - k + j && result[j - 1] < nums[i]) {
                j--;
            }
            
            if (j < k){
                result[j++] = nums[i];
            }
        }
        return result;
    }

    
    public static void main(String[] args) {

        int[][][] inputs = {
            //{nums1, nums2, {k}, expect}
            {null, {9, 1, 2, 5, 8, 3}, {4}, {9, 5, 8, 3}},
            {{9, 1, 2, 5, 8, 3}, null, {4}, {9, 5, 8, 3}},
            {{}, {9, 1, 2, 5, 8, 3}, {3}, {9, 8, 3}},
            {{9, 1, 2, 5, 8, 3}, {}, {3}, {9, 8, 3}},
            {{9, 1, 2, 5, 8, 3}, {9, 8}, {5}, {9, 9, 8, 8, 3}},
            {{3, 4, 6, 5}, {9, 1, 2, 5, 8, 3}, {5}, {9, 8, 6, 5, 3}},
            {{6, 7}, {6, 0, 4}, {5}, {6, 7, 6, 0, 4}},
            {{3, 9}, {8, 9}, {3}, {9, 8, 9}},
            {{3, 9}, {8, 9, 2}, {3}, {9, 9, 2}},
            {{8, 9, 2}, {3, 9}, {4}, {9, 8, 9, 2}},
            {{4, 9, 6}, {9, 7, 6}, {3}, {9, 9, 7}},
            {
                {2, 8, 0, 4, 5, 1, 4, 8, 9, 9, 0, 8, 2, 9}, 
                {5, 9, 6, 6, 4, 1, 0, 7}, 
                {22}, 
                {5, 9, 6, 6, 4, 2, 8, 1, 0, 7, 0, 4, 5, 1, 4, 8, 9, 9, 0, 8, 2, 9}
            },
            {
                {6, 4, 7, 8, 6, 5, 5, 3, 1, 7, 4, 9, 9, 5, 9, 6, 1, 7, 1, 3, 6, 3, 0, 8, 2, 1, 8, 0, 0, 7, 3, 9, 3, 1, 3,
                    7, 5, 9, 4, 3, 5, 8, 1, 9, 5, 6, 5, 7, 8, 6, 6, 2, 0, 9, 7, 1, 2, 1, 7, 0, 6, 8, 5, 8, 1, 6, 1, 5, 8, 4},
                {3, 0, 0, 1, 4, 3, 4, 0, 8, 5, 9, 1, 5, 9, 4, 4, 4, 8, 0, 5, 5, 8, 4, 9, 8, 3, 1, 3, 4, 8, 9, 4, 9, 9, 6, 
                    6, 2, 8, 9, 0, 8, 0, 0, 0, 1, 4, 8, 9, 7, 6, 2, 1, 8, 7, 0, 6, 4, 1, 8, 1, 3, 2, 4, 5, 7, 7, 0, 4, 8, 4},
                {70},
                {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 5, 6, 5, 7, 8, 6, 6, 2, 0, 9, 7, 1, 2, 1, 7, 0, 8, 0, 6, 8, 5, 8, 
                    1, 6, 1, 5, 8, 4, 0, 0, 0, 1, 4, 8, 9, 7, 6, 2, 1, 8, 7, 0, 6, 4, 1, 8, 1, 3, 2, 4, 5, 7, 7, 0, 4, 8, 4}
            },
            {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
                    1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
                    1, 1, 1, 1, 1, 1, 1, 1},
                {100},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            },
            {
                {1, 2, 0, 1, 1, 2, 0, 1, 1, 1, 0, 2, 2, 0, 1, 2, 0, 0, 0, 2, 1, 1, 2, 0, 0, 2, 2, 2, 0, 1, 2, 1, 0, 1, 0, 
                    1, 1, 2, 1, 2, 1, 2, 0, 0, 0, 0, 2, 2, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 2, 0, 0, 1, 0, 2, 2, 2, 
                    2, 0, 2, 0, 2, 1, 1, 1, 1, 0, 2, 2, 1, 0, 1, 2, 2, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 2, 1, 2, 2, 0, 
                    2, 2, 0, 2, 2, 2, 2, 2, 2, 1, 1, 0, 1, 2, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 2, 1, 0, 2, 1, 1, 1, 1, 2, 1, 
                    1, 2, 0, 1, 1, 0, 0, 2, 2, 0, 2, 1, 0, 0, 0, 0, 2, 2, 2, 1, 2, 1, 1, 1, 0, 1, 1, 0, 2, 1, 0, 2, 0, 0, 
                    1, 0, 2, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 2, 2, 0, 1, 0, 0, 2, 0, 1, 0, 0, 1, 0, 2, 1, 0, 1, 1, 0, 2, 1, 
                    2, 1, 1, 2, 1, 0, 1, 0, 1, 0, 0, 1, 2, 0, 0, 1, 2, 2, 1, 1, 0, 0, 2, 1, 1, 0, 1, 2, 2, 1, 1, 2, 1, 0, 
                    0, 0, 2, 0, 2, 1, 2, 1, 2, 2, 1, 2, 0, 2, 0, 2, 2, 2, 0, 0, 2, 2, 0, 0, 0, 1, 2, 0, 2, 0, 0, 2, 0, 2, 
                    2, 2, 1, 2, 1, 2, 2, 0, 2, 2, 2, 2, 0, 1, 0, 2, 0, 0, 2, 1, 2, 0, 0, 1, 0, 1, 1, 2, 2, 2, 0, 0, 2, 0, 
                    0, 2, 2, 1, 1, 1, 1, 2, 2, 2, 0, 0, 1, 2, 2, 0, 0, 1, 1, 0, 0, 2, 2, 0, 2, 2, 0, 1, 2, 1, 2, 0, 0, 2, 
                    2, 2, 1, 1, 2, 1, 0, 0, 1},
                {1, 0, 2, 1, 2, 2, 1, 2, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 2, 2, 0, 2, 0, 2, 2, 1, 2, 
                    0, 0, 0, 2, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 2, 2, 1, 1, 2, 0, 1, 0, 2, 0, 2, 2, 0, 0, 0, 0, 2, 1, 1, 1, 
                    2, 0, 1, 0, 0, 2, 0, 2, 1, 1, 0, 1, 1, 1, 2, 0, 0, 2, 2, 2, 1, 1, 1, 2, 0, 1, 2, 2, 1, 0, 2, 2, 1, 2, 
                    0, 1, 0, 2, 2, 0, 0, 1, 0, 1, 0, 1, 2, 1, 1, 0, 1, 1, 2, 0, 1, 0, 2, 2, 1, 1, 0, 2, 1, 1, 2, 0, 1, 1, 
                    0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 0, 1, 2, 1, 1, 1, 1, 1, 0, 2, 0, 2, 0, 1, 2, 0, 2, 0, 2, 1, 
                    0, 2, 0, 2, 0, 1, 0, 2, 1, 1, 0, 1, 2, 0, 1, 1, 2, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 2, 2, 0, 1, 2, 
                    2, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 0, 0, 2, 0, 2, 0, 1, 1, 0, 0, 1, 2, 2, 1, 0, 2, 1, 2, 
                    0, 2, 2, 1, 2, 2, 2, 2, 1, 0, 0, 0, 1, 2, 1, 2, 2, 1, 2, 2, 2, 2, 1, 0, 1, 1, 0, 1, 0, 2, 0, 0, 1, 2, 
                    0, 0, 2, 2, 0, 0, 0, 2, 1, 2, 2, 0, 2, 1, 0, 1, 2, 2, 2, 2, 2, 0, 2, 2, 2, 1, 1, 1, 0, 0, 1, 1, 2, 2, 
                    0, 2, 0, 1, 0, 2, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 2, 0, 2, 2, 1, 1, 1, 2, 2, 1, 2, 1, 0, 2, 0, 1, 0, 
                    1, 2, 0, 1, 2, 0, 2, 0, 2},
                {700},
                {1, 2, 1, 0, 2, 1, 2, 2, 1, 2, 1, 0, 1, 1, 2, 0, 1, 1, 1, 0, 2, 2, 0, 1, 2, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 
                    0, 0, 0, 2, 1, 1, 2, 0, 0, 2, 2, 2, 0, 1, 2, 1, 0, 1, 0, 1, 1, 2, 1, 2, 1, 2, 0, 0, 0, 1, 1, 1, 1, 2, 
                    2, 0, 2, 0, 2, 2, 1, 2, 0, 0, 0, 2, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 2, 2, 1, 1, 2, 0, 1, 0, 2, 0, 2, 2, 
                    0, 0, 0, 0, 2, 2, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 2, 0, 0, 1, 0, 2, 2, 2, 2, 0, 2, 0, 2, 1, 1, 
                    1, 1, 0, 2, 2, 1, 0, 1, 2, 2, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 2, 1, 2, 2, 0, 2, 2, 0, 2, 2, 2, 2, 
                    2, 2, 1, 1, 0, 1, 2, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 2, 1, 0, 2, 1, 1, 1, 1, 2, 1, 1, 2, 0, 1, 1, 0, 0, 
                    2, 2, 0, 2, 1, 0, 0, 0, 0, 2, 2, 2, 1, 2, 1, 1, 1, 0, 1, 1, 0, 2, 1, 0, 2, 0, 0, 1, 0, 2, 0, 1, 0, 1, 
                    0, 1, 1, 0, 1, 1, 2, 2, 0, 1, 0, 0, 2, 0, 1, 0, 0, 1, 0, 2, 1, 0, 1, 1, 0, 2, 1, 2, 1, 1, 2, 1, 0, 1, 
                    0, 1, 0, 0, 1, 2, 0, 0, 1, 2, 2, 1, 1, 0, 0, 2, 1, 1, 0, 1, 2, 2, 1, 1, 2, 1, 0, 0, 0, 2, 0, 2, 1, 2, 
                    1, 2, 2, 1, 2, 0, 2, 0, 2, 2, 2, 0, 0, 2, 2, 0, 0, 0, 1, 2, 0, 2, 0, 0, 2, 0, 2, 2, 2, 1, 2, 1, 2, 2, 
                    0, 2, 2, 2, 2, 0, 1, 0, 2, 0, 0, 2, 1, 2, 0, 0, 1, 0, 1, 1, 2, 2, 2, 0, 0, 2, 0, 0, 2, 2, 1, 1, 1, 1, 
                    2, 2, 2, 0, 0, 1, 2, 2, 0, 0, 1, 1, 0, 0, 2, 2, 0, 2, 2, 0, 1, 2, 1, 2, 0, 0, 2, 2, 2, 1, 1, 2, 1, 0, 
                    0, 1, 0, 0, 0, 0, 2, 1, 1, 1, 2, 0, 1, 0, 0, 2, 0, 2, 1, 1, 0, 1, 1, 1, 2, 0, 0, 2, 2, 2, 1, 1, 1, 2, 
                    0, 1, 2, 2, 1, 0, 2, 2, 1, 2, 0, 1, 0, 2, 2, 0, 0, 1, 0, 1, 0, 1, 2, 1, 1, 0, 1, 1, 2, 0, 1, 0, 2, 2, 
                    1, 1, 0, 2, 1, 1, 2, 0, 1, 1, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 0, 1, 2, 1, 1, 1, 1, 1, 0, 2, 
                    0, 2, 0, 1, 2, 0, 2, 0, 2, 1, 0, 2, 0, 2, 0, 1, 0, 2, 1, 1, 0, 1, 2, 0, 1, 1, 2, 1, 1, 1, 1, 0, 0, 1, 
                    1, 0, 0, 0, 1, 2, 2, 0, 1, 2, 2, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 0, 0, 2, 0, 2, 0, 1, 1, 
                    0, 0, 1, 2, 2, 1, 0, 2, 1, 2, 0, 2, 2, 1, 2, 2, 2, 2, 1, 0, 0, 0, 1, 2, 1, 2, 2, 1, 2, 2, 2, 2, 1, 0, 
                    1, 1, 0, 1, 0, 2, 0, 0, 1, 2, 0, 0, 2, 2, 0, 0, 0, 2, 1, 2, 2, 0, 2, 1, 0, 1, 2, 2, 2, 2, 2, 0, 2, 2, 
                    2, 1, 1, 1, 0, 0, 1, 1, 2, 2, 0, 2, 0, 1, 0, 2, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 2, 0, 2, 2, 1, 1, 1, 
                    2, 2, 1, 2, 1, 0, 2, 0, 1, 0, 1, 2, 0, 1, 2, 0, 2, 0, 2}
            },


        };
       
        MaxNumbersIII sv = new MaxNumbersIII();
        
        MaxNumbersIII2 sv2 = new MaxNumbersIII2();
         MaxNumbersIII22 sv22 = new MaxNumbersIII22();
         
        MaxNumbersIII3 sv3 = new MaxNumbersIII3();
                
        int i = 0;
        for(int[][] input : inputs){
            
            System.out.println(String.format("\n-%d- \nnums1: %s \nnums2: %s \nk=%d \nresult: %s", i++, Misc.array2String(input[0]), Misc.array2String(input[1]), input[2][0], Misc.array2String(sv.maxNumber(input[0], input[1], input[2][0]))));
            
            //System.out.println(Misc.array2String(sv.maxNumber_n(input[0], input[1], input[2][0])));
            
            Assert.assertArrayEquals(input[3], sv.maxNumber(input[0], input[1], input[2][0]));
            
            Assert.assertArrayEquals(input[3], sv2.maxNumber(input[0], input[1], input[2][0]));
            Assert.assertArrayEquals(input[3], sv22.maxNumber(input[0], input[1], input[2][0]));
            
            //Assert.assertArrayEquals(input[3], sv3.maxNumber(input[0], input[1], input[2][0]));
 
        }
        
        /**
         * performance test
         */
        TimeCost tc = TimeCost.getInstance();
        tc.init();

        final int TIMES = 80; 
        int start = 12;
        int end = inputs.length;
        
        for (int k = 0; k < TIMES; k++) {
            for (int j = start; j < end; j++) {
                sv.maxNumber(inputs[j][0], inputs[j][1], inputs[j][2][0]);
            }
        }
        System.out.println("\nsv.maxNumber         timeCost:" + tc.getTimeCost());

        for (int k = 0; k < TIMES; k++) {
            for (int j = start; j < end; j++) {
                sv2.maxNumber(inputs[j][0], inputs[j][1], inputs[j][2][0]);
            }
        }
        System.out.println("\nsv2.maxNumber        timeCost:" + tc.getTimeCost());

        for (int k = 0; k < TIMES; k++) {
            for (int j = start; j < end; j++) {
                sv22.maxNumber(inputs[j][0], inputs[j][1], inputs[j][2][0]);
            }
        }
        System.out.println("\nsv22.maxNumber        timeCost:" + tc.getTimeCost());

        for (int k = 0; k < TIMES; k++) {
            for (int j = start; j < end; j++) {
                sv3.maxNumber(inputs[j][0], inputs[j][1], inputs[j][2][0]);
            }
        }
        System.out.println("\nsv3.maxNumber        timeCost:" + tc.getTimeCost());
        
    };

}
