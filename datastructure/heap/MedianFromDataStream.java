package fgafa.datastructure.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

import fgafa.util.Misc;

/**
 * 
 * Numbers keep coming, return the median of numbers at every time a new number added.

	Example
	For numbers coming list: [1, 2, 3, 4, 5], return [1, 1, 2, 2, 3].
	
	For numbers coming list: [4, 5, 1, 3, 2, 6, 0], return [4, 4, 4, 3, 3, 3, 3].
	
	For numbers coming list: [2, 20, 100], return [2, 2, 20].
	
	Challenge
	Total run time in O(nlogn).
	
 *
 */

public class MedianFromDataStream {
    /**
     * @param nums: A list of integers.
     * @return: the median of numbers
     */
    public double[] medianII(int[] nums) {
        if (null == nums || 0 == nums.length) {
            return new double[0];
        }

        int length = nums.length;
        double[] result = new double[length];
        
        int half = length / 2 + 1;
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(half);
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(half,
                    new Comparator<Integer>() {
                        @Override
                        public int compare(Integer x, Integer y) {
                            return y - x;
                        }
                    });

        for (int i = 0; i < result.length; i++) {
            if ( maxHeap.isEmpty() || nums[i] < maxHeap.peek()) {
                maxHeap.add(nums[i]);
            } else {
                minHeap.add(nums[i]);
            }

            // rebalance
            while (maxHeap.size() <= minHeap.size()) {
                maxHeap.add(minHeap.poll());
            }
            while (maxHeap.size() > minHeap.size() + 1) {
                minHeap.add(maxHeap.poll());
            }

            if (maxHeap.size() == minHeap.size()) {
                result[i] = ((double) maxHeap.peek() + minHeap.peek()) / 2;
            } else {
                result[i] = minHeap.peek();
            }
        }

        return result;
    }
    
    
    public static void main(String[] args){
    	MedianFromDataStream sv = new MedianFromDataStream();
    	
    	int[][] input = {
    			{1, 2, 3, 4, 5},
    			{4, 5, 1, 3, 2, 6, 0},
    			{2, 20, 100}
    	};
    	
    	for(int[] nums : input){
    		System.out.println(String.format("Input: %s", Misc.array2String(nums)));
    		System.out.println(String.format("Output: %s", Misc.array2String(sv.medianII(nums))));
    	}
    }
}
