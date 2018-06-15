package fgafa.sorting;

import java.util.Arrays;
import fgafa.util.Misc;

/**
 * 
 * Q1, Given an array of citations (each citation is a non-negative integer) of a researcher, 
 * write a function to compute the researcher's h-index.

    According to the definition of h-index on Wikipedia: 
    "A scientist has index h if h of his/her N papers have at least h citations each, 
    and the other N − h papers have no more than h citations each."
    
    For example, given citations = [3, 0, 6, 1, 5], 
    which means the researcher has 5 papers in total and each of them had received 3, 0, 6, 1, 5 citations respectively. 
    Since the researcher has 3 papers with at least 3 citations each and the remaining two with no more than 3 citations each, his h-index is 3.
    
    Note: If there are several possible values for h, the maximum one is taken as the h-index.
    
    more cases:
    [3, 0, 6, 1, 5],  return 3
    [4, 0, 6, 1, 5],  return 3
    
    
 *
 * Q2, What if the citations array is sorted in ascending order? Could you optimize your algorithm?
 *
 *
 */

public class HIndex {

    /*from WIKI, 
     *  Time O(nlogn) Space O(1)
     * */
    public int hIndex(int[] citations) {
        if(null == citations || 0 == citations.length){
            return 0;
        }
        
        //sort from low to high
        Arrays.sort(citations);
        
        //reverse, from high to low
        for(int left = 0, right = citations.length - 1; left < right; left++, right--){
            swap(citations, left, right);
        }
        
        for (int i = 0; i < citations.length; i++) {
            if (i >= citations[i]){
                return i;
            }
        }
        
        return citations.length;
    }
    
    
    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    
    /*Time O(n) Space O(n)*/
    public int hIndex_n(int[] citations) {
        if(null == citations || 0 == citations.length){
            return 0;
        }
         
        int n = citations.length;
        int[] buckets = new int[n + 1];
         
        // Step 1: Accumulate the number of citations for each bucket
        for (int i = 0; i < n; i++) {
            if (citations[i] <= n) {
                buckets[citations[i]]++; 
            } else {
                buckets[n]++;
            }
        }
         
        // Step 2: iterate the citations from right to left.
        int numPapers = 0;
        for (int i = n; i >= 0; i--) {
            numPapers += buckets[i];
            if (numPapers >= i) {
                return i;
            }
        }
         
        return 0;
    }
    
    /**
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
        int[][] input = {
                    {},
                    {1},
                    {1,1},
                    {1,1,1},
                    {2},
                    {2,2},
                    {2,2,2},
                    {2,2,2,2},
                    {3, 0, 6, 1, 5},
                    {4, 0, 6, 1, 5},
                    {4, 0, 6, 2, 5}
        };
        
        HIndex sv = new HIndex();
        
        for(int i = 0; i < input.length; i++){
            System.out.println( "Input: " + Misc.array2String(input[i]));
            
            System.out.println( "Output: " + sv.hIndex(input[i]) +"  "+ sv.hIndex_n(input[i]));
        }
    }
    
    
    
}
