package fgafa.sorting.slidingwindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import fgafa.util.Misc;

class MaxSlideWindow
{

  /**
   *
   * A long array A[] is given to you. There is a sliding window of size w which is moving from the very left of the array to the very right. You can only see the w numbers in the window. Each time the sliding window moves rightwards by one position. Following is an example:
   * The array is [1 3 -1 -3 5 3 6 7], and w is 3.
   * 
   * Window position                Max
   * ---------------               -----
   * [1  3  -1] -3  5  3  6  7       3
   * 1 [3  -1  -3] 5  3  6  7       3
   * 1  3 [-1  -3  5] 3  6  7       5
   * 1  3  -1 [-3  5  3] 6  7       5
   * 1  3  -1  -3 [5  3  6] 7       6
   * 1  3  -1  -3  5 [3  6  7]      7
   * Input: A long array A[], and a window width w
   * Output: An array B[], B[i] is the maximum value of from A[i] to A[i+w-1]
   * Requirement: Find a good optimal way to get B[i]
   * 
   * 
   */

   /* Time O(nlog(w))  Space O(w) */
	public int[] maxSlidingWindow_heap(int input[], int w) {
		// check
		if (null == input || input.length < w || 0 == w) {
			return new int[0];
		}
		PriorityQueue<Pair> Q = new PriorityQueue<Pair>(); // max heap

		Pair curr;
		for (int i = 0; i < w; i++) {
			Q.add(new Pair(input[i], i));
		}

		int n = input.length;
		int[] output = new int[input.length - w + 1];
		for (int i = w; i < n; i++) {
			/* get the max in the window */
			curr = Q.peek();
			output[i - w] = curr.val;

			/* remove the old element from PQ, constant time to peek and poll */
			while (curr.index <= i - w) {
				Q.poll();
				curr = Q.peek();
			}

			/* add in the right one in the window */
			Q.add(new Pair(input[i], i)); // O(log(w)) time for insertion, while
											// w will be n when input is in
											// ascend order.
		}

		output[n - w] = Q.peek().val;
		return output;
	}
  
  /*
   * with double-ended queue, that is the perfect data structure 
   * 
   * Time O(n) Space O(w) 
   */ 
  public int[] maxSlidingWindow(int[] nums, int w) {
	  //check
	  if(null == nums || nums.length < w || 0 == w){
		  return new int[0];
	  }
	  
      LinkedList<Integer> queue = new LinkedList<Integer>();  //store the index instead of value
      for (int i = 0; i < w; i++) {
        while (!queue.isEmpty() && nums[i] >= nums[queue.getLast()])
          queue.removeLast();

        queue.addLast(i);
      }
      
      int[] output = new int[nums.length - w + 1];
      for (int i = w; i < nums.length; i++) {
        /* get the max in the window */
        output[i-w] = nums[queue.getFirst()];
        
        /*slide the window, remove by the value */
        while (!queue.isEmpty() && nums[i] >= nums[queue.getLast()])
          queue.removeLast();
        /*slide the window, remove by the position */
        while (!queue.isEmpty() && queue.getFirst() <= i-w)
          queue.removeFirst();
        
        /*slide the window, add in the the last of list */
        queue.addLast(i);
      }
      
      output[nums.length-w] = nums[queue.getFirst()];        
      return output;
  }
  
  
  /**
   * @param nums: A list of integers.
   * @return: The maximum number inside the window at each moving.
   */
  /*Time O(n) Space O(w) */
  public ArrayList<Integer> maxSlidingWindow_n(int[] nums, int k) {
      ArrayList<Integer> result = new ArrayList<>();
      //check
      if(null == nums || 0 >= k || k > nums.length){
          return result;
      }
      
      //define a deque to store the index of subarray max
      LinkedList<Integer> deque = new LinkedList<>();
      for(int i = 0; i < nums.length; i++){
          if(i >= k){
              result.add(nums[deque.getFirst()]);
              
              if(deque.getFirst() <= i - k){
                  //remove the previor slide num
                  deque.removeFirst();
              }
          }
          
          //add the max in deque
          //if(deque.isEmpty() || nums[deque.getLast()] <= nums[i]){
              while(!deque.isEmpty() && nums[deque.getLast()] <= nums[i]){
                  deque.removeLast();
              }
              deque.add(i);
          //}
      }
      
      if(!deque.isEmpty()){
          result.add(nums[deque.pop()]);
      }
      
      return result;
  }
  
  /**
   * @param args
   */
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
    		{1577,330,1775,206,296,356,219,999,790,1435,1218,1046,745,650,1199,1290,442,1767,1098,521,854,1718,528,1011}
    		
    };
    int[] k = {1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 8};

    MaxSlideWindow sv = new MaxSlideWindow();
    for(int i = 0; i < input.length; i++){
    	
    	System.out.println(String.format("Input: %s k=%d", Misc.array2String(input[i]), k[i]));
    	System.out.print("Output: ");
        Misc.printArrayList_Integer(sv.maxSlidingWindow_n(input[i], k[i]));
        
        System.out.println();
    }
    
  }

  
  class Pair implements Comparable<Pair> {
    int val;
    int index;
    
    Pair(int val, int i){
      this.val = val;
      this.index = i;
    }

    @Override
    public int compareTo(Pair o) {
      if (this.val > o.val) {
        return 1;
      }else if(this.val > o.val)
        return 0;
      else
        return -1;
    }
    
  }
  
}
