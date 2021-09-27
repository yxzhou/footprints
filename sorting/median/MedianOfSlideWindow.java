package sorting.median;

/**
 * 
 * Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.

Examples: 
[2,3,4] , the median is 3

[2,3], the median is (2 + 3) / 2 = 2.5

Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Your job is to output the median array for each window in the original array.

For example,
Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.

Window position                Median
---------------               -----
[1  3  -1] -3  5  3  6  7       1
 1 [3  -1  -3] 5  3  6  7       -1
 1  3 [-1  -3  5] 3  6  7       -1
 1  3  -1 [-3  5  3] 6  7       3
 1  3  -1  -3 [5  3  6] 7       5
 1  3  -1  -3  5 [3  6  7]      6
Therefore, return the median sliding window as [1,-1,-1,3,5,6].

Note: 
You may assume k is always valid, ie: 1 ≤ k ≤ input array's size for non-empty array.
 * 
 */

import datastructure.heap.HashHeap;
import util.Misc;

public class MedianOfSlideWindow {

    public double[] medianSlidingWindow(int[] nums, int k) {
        if(null == nums || 0 == nums.length || k <= 0){
            return new double[0];
        }

        MyMedian median = new MyMedian();
        k--;
        double[] result = new double[nums.length - k];
        
        for(int i = 0; i < nums.length; i++){
            median.add(nums[i]);
            
            if(i >= k){
                result[i - k] = median.findMedian();  
                median.delete(nums[i - k]);    
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        int[][] nums = {{1,3,-1,-3,5,3,6,7}, {6,5,9,5,4,9,1,7,5,5} };
        int[] ks = {3, 4};

        MedianOfSlideWindow sv = new MedianOfSlideWindow();
        for(int i = 0; i < nums.length; i++){
            System.out.println(String.format(" Input: %s, %d", Misc.array2String(nums[i]), ks[i]));
            System.out.println(String.format("Output: %s", Misc.array2String(sv.medianSlidingWindow(nums[i], ks[i]))));
        }
    }
    
}

class MyMedian{
    HashHeap minHashHeap = new HashHeap("min");
    HashHeap maxHashHeap = new HashHeap("max");
    
    public void add(int num){
        if(minHashHeap.isEmpty() || minHashHeap.peak() <= num){
            minHashHeap.add(num);
        }else{
            maxHashHeap.add(num);
        }
        
        rebalance();
    }
    
    public void delete(int num){
        if(minHashHeap.containsKey(num)){
            minHashHeap.delete(num);
        }else{
            maxHashHeap.delete(num);
        }
        
        rebalance();
    }
    
    private void rebalance(){
        while(minHashHeap.size() > maxHashHeap.size() + 1){
            maxHashHeap.add(minHashHeap.poll());
        }
        
        while(maxHashHeap.size() > minHashHeap.size()){
            minHashHeap.add(maxHashHeap.poll());
        }
    }
    
    public double findMedian(){
        
        if(0 == minHashHeap.size() && 0 == maxHashHeap.size()){
            throw new IllegalArgumentException("No data input");
        }else if(minHashHeap.size() == maxHashHeap.size()){
            return ((double)minHashHeap.peak() + maxHashHeap.peak()) / 2;
        }else{
            return minHashHeap.peak();
        }
    }
}