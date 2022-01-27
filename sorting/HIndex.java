package sorting;

import java.util.Arrays;
import java.util.Collections;
import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1304
 *
 * Q1, Given an array of citations (each citation is a non-negative integer) of a researcher, write a function to
 * compute the researcher's h-index.
 *
 * According to the definition of h-index on Wikipedia: "A scientist has index h if h of his/her N papers have at least
 * h citations each, and the other N − h papers have no more than h citations each."
 *
 * For example, given citations = [3, 0, 6, 1, 5], which means the researcher has 5 papers in total and each of them had
 * received 3, 0, 6, 1, 5 citations respectively. Since the researcher has 3 papers with at least 3 citations each and
 * the remaining two with no more than 3 citations each, his h-index is 3.
 *
 * Note: If there are several possible values for h, the maximum one is taken as the h-index.
    
    more cases:
    [3, 0, 6, 1, 5],  return 3
    [4, 0, 6, 1, 5],  return 3
    
    [100, 110, 120],  return 3
    [2, 2, 2, 2],     return 2
 * 
 * Thoughts:
 *   from the definition of h-index, and the above testcases, the h-index is decided by citations[i] and the amount of how many papers
 *   which citation is equal or bigger than citations[i]. 
 *   
 *   m1) sort the citations array in ascending order, define n as the length of the array
 *       from right to left, define i as the index,  n - i is the amount, check citations[i] and n - i, 
 *       citations[i] is smaller and smaller, n - i is bigger and bigger. 
 *       it need find out the max of n - i, which is <= citations[i]
 * 
 *   m2) sort the citations array in descending order, define n as the length of the array
 *       from left to right, define i as the index,  and i is the amount, check citations[i] and i, 
 *       citations[i] is smaller and smaller, i is bigger and bigger. 
 *       it need find out the min of i, which is >= citations[i], or 
 *                        the max of i, which is <= citations[i]
 *   
 *   m3) the above 2 methods both need O(n*logn) because the sorting, 
 *       a optimization method is bucket sort, it's O(n)
 * 
 * Q2, What if the citations array is sorted in ascending order? Could you optimize your algorithm?
 *   m1) In the above 3 methods all take O(n) to check the amount and the citations[i], it takes O(n), 
 *      a optimization method is quick selection. it takes O(logn)
 * 
 *
 */

public class HIndex {
    
    /**
     * from WIKI, Time O(nlogn) Space O(1)
     *
     */
    public int hIndex_m2(int[] citations) {
        if(null == citations || 0 == citations.length){
            return 0;
        }
    
        //sort from low to high
        Arrays.sort(citations);
        
        //reverse, from high to low
        for(int l = 0, r = citations.length - 1; l < r; l++, r--){
            swap(citations, l, r);
        }
        
        for (int i = 0; i < citations.length; i++) {
            if (i >= citations[i]){
                return i;
            }
        }
        
        return citations.length; //for case {5, 5}
       
          //the following is wrong 
//        int max = 0;
//        for (int i = 0; i < citations.length; i++) {
//            if ( i <= citations[i]) {
//                max = i;
//            }else{
//                break;
//            }
//        }
//        
//        return 0;
        
    }
    
    
    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    
    
    /**
     * from WIKI, 
     *  Time O(nlogn) Space O(1)
     * 
     */
    public int hIndex_m1(int[] citations) {
        if (null == citations) {
            return 0;
        }

        //sort from low to high
        Arrays.sort(citations);
        
        int n = citations.length;
        int max = 0;
        int num;
        for (int i = n - 1; i >= 0; i--) {
            // this is wrong
//            if ( (num = n - i) > citations[i]) {
//                return num;
//            }  
            
            if ( (num = n - i) <= citations[i]) {
                max = num;
            }else{
                break;
            }
        }

        return max;
    }
    
    
    /**
     *  m3, bucket sorting, 
     * 
     * Time O(n) Space O(n)
     */
    public int hIndex_n(int[] citations) {
        if(null == citations){
            return 0;
        }
         
        int n = citations.length;
        int[] buckets = new int[n + 1];
         
        // Step 1: Accumulate the number of citations for each bucket
        for (int i = 0; i < n; i++) {
            if (citations[i] < n) {
                buckets[citations[i]]++; 
            } else {
                buckets[n]++;
            }
        }
         
        // Step 2: iterate the citations from right to left. 
        int sum = 0; // define sum as the amount 
        for (int i = n; i >= 0; i--) { //define i as the citations 
            sum += buckets[i];
            
            if (sum >= i) {
                return i;
            }
        }
         
        return 0;
    }
    
    /**
     * binary search, 
     * 
     * @param citations, sorted in ascending order
     * @return
     */
    public int hIndexII(int[] citations) {
        int len = citations.length;
        int left = 0;
        int right = len - 1;
        int mid;
        while (left <= right) {
            mid = left + ((right - left) >> 1);
            
            if (citations[mid] == len - mid){
                return len - mid;
            }else if (citations[mid] > len - mid){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        
        return len - left;
    }
    
    
    public static void main(String[] args){
        int[][][] inputs = {
            {{},{0}},
            {{0}, {0}},
            {{1}, {1}},
            {{1,1}, {1}},
            {{1,1,1}, {1}},
            {{5}, {1}},
            {{5,5},  {2}},
            {{2,2,2}, {2}},
            {{2,2,2,2}, {2}},
            {{3, 0, 6, 1, 5}, {3}},
            {{4, 0, 6, 1, 5}, {3}},
            {{4, 0, 6, 2, 5}, {3}},
            {{100, 110, 120, 123},{4}},
        };
        
        HIndex sv = new HIndex();
        
        for(int[][] input : inputs){
            System.out.println( String.format("\nInput: %s, \t expect: %d", Misc.array2String(input[0]),  input[1][0]));
            
            //System.out.println( "Output: " + sv.hIndex_m1(input[0]) +"  "+ sv.hIndex_n(input[0]));
            
            Assert.assertEquals("", input[1][0], sv.hIndex_m1(input[0]));
            Assert.assertEquals("", input[1][0], sv.hIndex_m2(input[0]));
            Assert.assertEquals("", input[1][0], sv.hIndex_n(input[0]));
        }
    }
    
    
    
}
