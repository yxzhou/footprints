package fgafa.sorting.median;

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

import fgafa.datastructure.heap.HashHeap;

public class MedianOfSlideWindow2 {

    public double[] medianSlidingWindow(int[] nums, int k) {
        if(null == nums || 0 == nums.length || k <= 0){
            return new double[0]; 
        }
        
        Median median = new Median();
        
        double[] result = new double[nums.length];
        
        for(int i = 0; i < nums.length; i++){
            if(i >= k ){
                median.delete(nums[i - k]);
            }
            
            median.add(nums[i]);
            result[i] = median.findMedian();
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
    
}

class Median2{
    
    int[] sorted;
    Map<>
    
    public Median2(int capacity){
        sorted = new int[capacity];
    }
                
    public void add(int num){
        if(minHashHeap.isEmpty() || minHashHeap.peak() <= num){
            minHashHeap.add(num);
        }else{
            maxHashHeap.add(num);
        }
    }
    
    public void delete(int num){
        minHashHeap.delete(num);
        maxHashHeap.delete(num);
    }
    
    private void rebalance(){
        while(minHashHeap.size() > maxHashHeap.size()){
            maxHashHeap.add(minHashHeap.poll());
        }
        
        while(maxHashHeap.size() > minHashHeap.size()){
            minHashHeap.add(maxHashHeap.poll());
        }
    }
    
    public double findMedian(){
        rebalance();
        
        if(0 == minHashHeap.size()){
            throw new IllegalArgumentException("No data input");
        }else if(minHashHeap.size() == maxHashHeap.size()){
            return ((double)minHashHeap.peak() + maxHashHeap.peak()) / 2;
        }else{
            return minHashHeap.peak();
        }
    }
}