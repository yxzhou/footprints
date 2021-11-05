package design.others.stream;

/**
 * Another solution on SlideWindowMedian1,  minHeap + maxHeap, datastructure.heap.HashHeap
 * 
 */

import datastructure.heap.HashHeap;

public class SlideWindowMedian3 {

    class Median4Stream2 {

        HashHeap minHashHeap = new HashHeap("min");
        HashHeap maxHashHeap = new HashHeap("max");

        Median4Stream2(int capacity) {

        }

        public void add(int num) {
            minHashHeap.add(num);

            while (minHashHeap.size() >= maxHashHeap.size()) {
                maxHashHeap.add(minHashHeap.poll());
            }

            while (maxHashHeap.size() > minHashHeap.size() + 1) {
                minHashHeap.add(maxHashHeap.poll());
            }

        }

        public void delete(int num) {
            minHashHeap.delete(num);
            maxHashHeap.delete(num);
        }

        public int findMedian() {

            if (0 == maxHashHeap.size()) {
                throw new IllegalArgumentException("No data input");
                //}else if(minHeap.size() == maxHeap.size()){
                //    return ((double)minHeap.peek() + maxHeap.peek()) / 2;
            } else {
                return maxHashHeap.peak();
            }
        }
    }
    
    public int[] medianSlidingWindow(int[] nums, int k) {
        if(null == nums || 0 == nums.length || k <= 0){
            return new int[0]; 
        }
        
        Median4Stream2 median = new Median4Stream2(10);
        
        int[] result = new int[nums.length];
        
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
        
        // see SlideWindowMedian1

    }
    
}

