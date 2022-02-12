package design.others.stream;

/**
 * continue on SlideWindowMedian
 * 
 * Solution: minHeap + maxHeap, PriorityQueue, Time O(n*k)
 * 
 * Note:
 *   PriorityQueue.remove(Object o),  More formally, removes an element e such that o.equals(e) if this PriorityQueue 
 *   contains one or more such elements. 
 */

import java.util.Collections;
import java.util.PriorityQueue;

public class SlideWindowMedian1 {
    
    class MyMedian {

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(10, Collections.reverseOrder());

        public void add(int num) {
            maxHeap.add(num);

            rebalance();
        }

        /**
         * Time O( k ) instead of O( logk )
         * It takes O(k) to locate the element, and O(logk) to re-organize the heap.  
         *
         * @param num
         */
        public void delete(int num) {
            if (!minHeap.isEmpty() && minHeap.peek() <= num) {
                minHeap.remove(num);
            } else {
                maxHeap.remove(num);
            }

            rebalance();
        }

        private void rebalance() {
            if (minHeap.isEmpty() && maxHeap.isEmpty()) {
                return; // for case: nums = {1, 2}, k = 1
            }

            while (maxHeap.size() >= minHeap.size()) {
                minHeap.add(maxHeap.poll());
            }
            
            while (minHeap.size() > maxHeap.size()) {
                maxHeap.add(minHeap.poll());
            }

        }

        public int findMedian() {

            if (0 == minHeap.size() && 0 == maxHeap.size()) {
                throw new IllegalArgumentException("No data input");
            } else {
                System.out.println( String.format(" minHeap: peek() = %d, size = %d, \tmaxHeap: peek() = %d size = %d", 
                        minHeap.isEmpty()? -1 : minHeap.peek(), minHeap.isEmpty()? -1 : minHeap.size(), 
                        maxHeap.peek(), maxHeap.size() ) );
                
                return maxHeap.peek();
            }
        }
    }
    
    public int[] medianSlidingWindow(int[] nums, int k) {
        if (null == nums || 0 == nums.length || k <= 0) {
            return new int[0];
        }

        MyMedian median = new MyMedian();
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
    
    

    
}