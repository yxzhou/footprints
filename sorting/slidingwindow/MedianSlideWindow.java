package fgafa.sorting.slidingwindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import fgafa.util.Misc;

public class MedianSlideWindow {

	/**
	 * Given an array of n integer, and a moving window(size k), move the window
	 * at each iteration from the start of the array, find the median of the
	 * element inside the window at each moving. (If there are even numbers in
	 * the array, return the N/2-th number after sorting the element in the
	 * window. )
	 * 
	 * Example
	 * For array [1,2,7,8,5], moving window size k = 3. return [2,7,7]
	 * 
	 * At first the window is at the start of the array like this [ | 1,2,7 | ,8,5] , return the median 2;
	 * then the window move one step forward. [1, | 2,7,8 | ,5], return the median 7;
	 * then the window move one step forward again. [1,2, | 7,8,5 | ], return the median 7;
	 * 
	 * Challenge
	 * O(nlog(n)) time
	 */
	
	
	/**
	 * @param nums
	 *            : A list of integers.
	 * @return: The median of the element inside the window at each moving.
	 */
	public ArrayList<Integer> medianSlidingWindow(int[] nums, int k) {
		ArrayList<Integer> result = new ArrayList<>();
		//check
		if(null == nums || 0 >= k || nums.length < k){
			return result;
		}
		
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(k);
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(k, Collections.reverseOrder());
		
        for(int i = 0; i < k / 2; i++){
        	maxHeap.add(nums[i]);
        }
        
        for(int i = k / 2; i < k ; i++){
        	minHeap.add(nums[i]);
        }
        balance(maxHeap, minHeap);
        
       for(int i = k; i < nums.length; i++){
    	   result.add(maxHeap.peek());
    	   
    	   remove(maxHeap, minHeap, nums[i - k]);
    	   
    	   add(maxHeap, minHeap, nums[i]);
       }
       
       if(null != maxHeap.peek()) {
    	   result.add(maxHeap.peek());
       }
        
		return result;
	}
    
	private void remove(PriorityQueue<Integer> maxHeap, PriorityQueue<Integer> minHeap, Integer toBeRemoved){
		if(!maxHeap.isEmpty() && toBeRemoved <= maxHeap.peek()){
			maxHeap.remove(toBeRemoved);
		}else{
			minHeap.remove(toBeRemoved);
		}
		
		balance(maxHeap, minHeap);
	}
	
	private void add(PriorityQueue<Integer> maxHeap, PriorityQueue<Integer> minHeap, Integer toBeAdded){
		if(maxHeap.isEmpty() || toBeAdded <= maxHeap.peek()){
			maxHeap.add(toBeAdded);
		}else{
			minHeap.add(toBeAdded);
		}
		
		balance(maxHeap, minHeap);
	}
    
	private void balance(PriorityQueue<Integer> maxHeap, PriorityQueue<Integer> minHeap){
	    while(!maxHeap.isEmpty() && !minHeap.isEmpty() && maxHeap.peek() > minHeap.peek()){
	    	maxHeap.add(minHeap.poll());
	    	minHeap.add(maxHeap.poll());
	    }
		
		while( maxHeap.size() < minHeap.size()){
			maxHeap.add(minHeap.poll());
		}
		
		while( maxHeap.size() - 1 > minHeap.size() ){
			minHeap.add(maxHeap.poll());
		}
		
	}
	
	public static void main(String[] args) {
	    int[][] input = {
	    		{1},
	    		{1, 2},
	    		{1, 2, 3},
	    		{1, 2, 3, 4},
	    		{1, 2, 3, 4, 5},
	    		{2, 1},
	    		{3, 2, 1},
	    		{4, 3, 2, 1},
	    		{5, 4, 3, 2, 1},
	    		{1, 2, 3},
	    		{1, 2, 3, 4},
	    		{1, 2, 3, 4, 5},
	    		{3, 2, 1},
	    		{4, 3, 2, 1},
	    		{5, 4, 3, 2, 1},
	    		{1,2,7,7,2},
	    		{1,2,7,7,2},
	    		{1,2,7,7,2}
	    		
	    };
	    int[] k = {1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 1, 2, 3};

	    MedianSlideWindow sv = new MedianSlideWindow();
	    for(int i = 0; i < input.length; i++){
	    	
	    	System.out.println(String.format("Input: %s k=%d", Misc.array2String(input[i]), k[i]));
	    	System.out.print("Output: ");
	        Misc.printArrayList_Integer(sv.medianSlidingWindow(input[i], k[i]));
	        
	        System.out.println();
	    }

	}

}
