package design.others.stream;

/**
 * Another solution on SlideWindowMedian1,  minHeap + maxHeap, datastructure.heap.HashHeap
 * 
 *  Wrong !!
 *  TODO, fix the issue. when there are duplicated num in the HashHeap, it will delete all instead of one.  
 * 
 */

import datastructure.heap.HashHeap;
import java.util.Collections;

public class SlideWindowMedian3 {

    class Median4Stream2 {

        HashHeap minHashHeap = new HashHeap(null);
        HashHeap maxHashHeap = new HashHeap(Collections.reverseOrder());

        Median4Stream2(int capacity) {

        }

        public void add(int num) {
            maxHashHeap.add(num);

            rebalance();
        }

        public void delete(int num) { //bug,  when there are duplicated num in the HashHeap, it will delete all instead of one. 
            minHashHeap.delete(num);
            maxHashHeap.delete(num);
            
            rebalance();
        }

        private void rebalance(){
            if (minHashHeap.isEmpty() && maxHashHeap.isEmpty()) {
                return; // for case: nums = {1, 2}, k = 1
            }

            while (maxHashHeap.size() >= minHashHeap.size()) {
                minHashHeap.add(maxHashHeap.poll());
            }
                        
            while (minHashHeap.size() > maxHashHeap.size()) {
                maxHashHeap.add(minHashHeap.poll());
            }

        }
                
        public int findMedian() {

            if (0 == maxHashHeap.size()) {
                throw new IllegalArgumentException("No data input");
                //}else if(minHeap.size() == maxHeap.size()){
                //    return ((double)minHeap.peek() + maxHeap.peek()) / 2;
            } else {
                System.out.println( String.format(" minHeap: peek() = %d, size = %d, \tmaxHeap: peek() = %d size = %d", 
                        minHashHeap.isEmpty()? -1 : minHashHeap.peak(), minHashHeap.isEmpty()? -1 : minHashHeap.size(), 
                        maxHashHeap.peak(), maxHashHeap.size() ) );
                
                return maxHashHeap.peak();
                //return minHashHeap.size() == maxHashHeap.size()? maxHashHeap.peak() : minHashHeap.peak();
            }
        }
    }
    
    public int[] medianSlidingWindow(int[] nums, int k) {
        if(null == nums || 0 == nums.length || k <= 0){
            return new int[0]; 
        }
        
        Median4Stream2 median = new Median4Stream2(10);
        int n = nums.length;
        int[] result = new int[n - k + 1];
        
        for (int i = 0, j = 0, s = k - 1; i < n; i++) {
            median.add(nums[i]);

            if (i >= s) {
                result[j++] = median.findMedian();
                median.delete(nums[i - s]);
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        
        // see SlideWindowMedian1

    }
    
}

