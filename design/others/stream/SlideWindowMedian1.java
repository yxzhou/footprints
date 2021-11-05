package design.others.stream;

/**
 * 
    * Given an array of n integer, and a moving window(size k), move the window at each iteration from the start of the
 * array, find the median of the element inside the window at each moving. (If there are even numbers in the array,
 * return the N/2-th number after sorting the element in the window. )
 *
 * Example #1
 * Input array [1,2,7,8,5], moving window size k = 3. 
 * Output [2,7,7] 
 * Explanation: 
 *   At first the window is at the start of the array like this [ | 1,2,7 | ,8,5] , return the median 2; 
 *   then the window move one step forward. [1, | 2,7,8 | ,5], return the median 7; 
 *   then the window move one step forward again. [1,2, | 7,8,5 | ], return the median 7;
 *
 * Example #2, 
 * Input array [1,3,-1,-3,5,3,6,7], and k = 3.
 *
 * Window position        Median
 * ---------------        ----- 
 * [1 3 -1] -3 5 3 6 7      1 
 * 1 [3 -1 -3] 5 3 6 7      -1 
 * 1 3 [-1 -3 5] 3 6 7      -1 
 * 1 3 -1 [-3 5 3] 6 7      3 
 * 1 3 -1 -3 [5 3 6] 7      5 
 * 1 3 -1 -3 5 [3 6 7]      6 
 * Therefore, return the median sliding window as [1,-1,-1,3,5,6].
 *
 * Note: 
 *   You may assume k is always valid, ie: 1 ≤ k ≤ input array's size for non-empty array.
 *
 * Challenge O(nlog(n)) time
 * 
 * Solution #1, minHeap + maxHeap, PriorityQueue, Time O(n*k)
 * Solution #2, minHeap + maxHeap, TreeSet, Time O(n*logk)
 * 
 */

import java.util.Collections;
import java.util.PriorityQueue;
import org.junit.Assert;
import util.Misc;

public class SlideWindowMedian1 {
    
    class MyMedian {

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(10, Collections.reverseOrder());

        public void add(int num) {
            maxHeap.add(num);

            rebalance();
        }

        /**
         * Time O(logk)
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

        for (int i = 0, j = 0, s = k - 1; i < nums.length; i++) {
            median.add(nums[i]);

            if (i >= s) {
                result[j++] = median.findMedian();
                median.delete(nums[i - s]);
            }
        }

        return result;
    }
    
    
    public static void main(String[] args) {
        int[][] nums = {
            {1},            // k = 1
            {1, 2},         // k = 2
            {1, 2, 3},      // k = 2
            {1, 2, 3, 4},   // k = 2
            {1, 2, 3, 4, 5}, // k = 2
            {2, 1},         // k = 2
            {3, 2, 1},      // k = 2
            {4, 3, 2, 1},       // k = 2
            {5, 4, 3, 2, 1},    // k = 2
            {1, 2, 3},      // k = 3
            {1, 2, 3, 4},   // k = 3
            {1, 2, 3, 4, 5},    // k = 3
            {3, 2, 1},      // k = 3
            {4, 3, 2, 1},   // k = 3
            {5, 4, 3, 2, 1},    // k = 3
            {1,2,7,7,2},    // k = 1
            {1,2,7,7,2},    // k = 2
            {1,2,7,7,2},    // k = 3
            {1,3,-1,-3,5,3,6,7},    // k = 3
            {6,5,9,5,4,9,1,7,5,5}   // k = 4
        };
        int[] ks = {1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 1, 2, 3, 3, 4};
        int[][] expects = {
            {1},
            {1},
            {1, 2},
            {1,2,3},
            {1,2,3,4},
            {1},
            {2,1},
            {3,2,1},
            {4,3,2,1},
            {2},
            {2,3},
            {2,3,4},
            {2},
            {3,2},
            {4,3,2},
            {1,2,7,7,2},
            {1,2,7,2},
            {2,7,7},
            {1, -1, -1, 3, 5, 6},
            {5, 5, 5, 4, 4, 5, 5}
        };

        SlideWindowMedian1 sv = new SlideWindowMedian1();
        SlideWindowMedian2 sv2 = new SlideWindowMedian2();
        for(int i = 0; i < nums.length; i++){
            System.out.println(String.format(" Input: %s, %d", Misc.array2String(nums[i]), ks[i]));
            
            //System.out.println(String.format("Output: %s \n\t\t%s", Misc.array2String(sv.medianSlidingWindow(nums[i], ks[i])), Misc.array2String(expects[i])));
            Assert.assertArrayEquals(expects[i], sv.medianSlidingWindow(nums[i], ks[i]));
            
            System.out.println(String.format("Output: %s -- \t%s", Misc.array2String(sv2.medianSlidingWindow(nums[i], ks[i])), Misc.array2String(expects[i])));
            //Assert.assertArrayEquals(expects[i], sv2.medianSlidingWindow(nums[i], ks[i]));
        }
    }
    
}