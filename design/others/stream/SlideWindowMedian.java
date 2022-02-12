package design.others.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import util.Misc;

/**
 * _https://www.lintcode.com/problem/360
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
 * Challenge O(nlog(n)) time
 * 
 * 
 * Solution #1, minHeap + maxHeap, PriorityQueue, Time O(n*k) , see SlideWindowMedian and SlideWindowMedian1
 * Solution #2, minHeap + maxHeap, TreeSet, Time O(n*logk), see SlideWindowMedian2
 * 
 */
public class SlideWindowMedian {

    /**
     * @param nums : A list of integers.
     * @return: The median of the element inside the window at each moving.
     */
    public List<Integer> medianSlidingWindow(int[] nums, int k) {

        if (null == nums || k <= 0 || nums.length == 0) {
            return Collections.EMPTY_LIST;
        }

        int n = nums.length;
        List<Integer> result = new ArrayList<>(n - k + 1);

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, Collections.reverseOrder());

        for (int i = 0, j = 1 - k; i < n; i++, j++) {

            if (maxHeap.isEmpty() || nums[i] <= maxHeap.peek()) {
                maxHeap.add(nums[i]);
            } else {
                minHeap.add(nums[i]);
            }

            balance(maxHeap, minHeap);

            if (j >= 0) {
                result.add(maxHeap.peek());

                if (!minHeap.isEmpty() && nums[j] >= minHeap.peek()) {
                    minHeap.remove(nums[j]);
                } else {
                    maxHeap.remove(nums[j]);
                }

                balance(maxHeap, minHeap);
            }

        }

        return result;
    }

    private void balance(PriorityQueue<Integer> maxHeap, PriorityQueue<Integer> minHeap) {
        if (minHeap.isEmpty() && maxHeap.isEmpty()) {
            return;
        }

        while (maxHeap.size() > minHeap.size()) {
            minHeap.add(maxHeap.poll());
        }

        while (minHeap.size() > maxHeap.size()) {
            maxHeap.add(minHeap.poll());
        }

    }

    public static void main(String[] args) {
    
        //see SlideWindowMedian2
    }

}
