package fgafa.sorting.median;

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
	
	Clarification
	What's the definition of Median?
	- Median is the number that in the middle of a sorted array. If there are n numbers in a sorted array A, the median is A[(n - 1) / 2]. For example, if A=[1,2,3], median is 2. If A=[1,19], median is 1.
 *
 */

public class MedianFromDataStream {
    /**
	 * with maxHeap and minHeap
	 *
     * @param nums: A list of integers.
     * @return: the median of numbers
     */
    public int[] median(int[] nums) {
        //check
        if(null == nums || 0 == nums.length){
            return new int[0];
        }
        
        int[] result = new int[nums.length];
        
        PriorityQueue<Integer> minHeap=new PriorityQueue<Integer>();
        
		PriorityQueue<Integer> maxHeap=new PriorityQueue<Integer>(10, new Comparator<Integer>(){
			@Override
			public int compare( Integer x, Integer y )
		    {
		        return y - x;
		    }
		});
	    
		for(int i = 0; i < result.length; i++){
			if(0 == maxHeap.size() || nums[i] < maxHeap.peek()){
				maxHeap.add(nums[i]);
			}else{
				minHeap.add(nums[i]);
			}
			
			//rebalance
			while(maxHeap.size() <= minHeap.size()){
				maxHeap.add(minHeap.poll());
			}
			while(maxHeap.size() > minHeap.size() + 1){
				minHeap.add(maxHeap.poll());
			}
			
			result[i] = maxHeap.peek();
		}
		
        return result;
    }


	/**
	 * with segment tree, when the minValue and maxValue is known.
	 *   especially benifit when there are lots of duplicate numbers in the stream
	 *
	 * @param nums: A list of integers.
	 * @return: the median of numbers
	 */
	public int[] medianII(int[] nums) {
		//check
		if (null == nums || 0 == nums.length) {
			return new int[0];
		}

		int[] result = new int[nums.length];


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
