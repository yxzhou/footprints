package sorting.slidingwindow;

import util.Misc;

import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

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
  public int[] maxSlidingWindow_n(int[] nums, int k) {
      if (null == nums || nums.length < k || k <= 0) {
          return new int[0];
      }

      int length = nums.length;
      int[] result = new int[length - k + 1];

      Deque<Integer> deque = new LinkedList<>();

      for(int i = 0, j = 0, s = k - 1; i < length; i++){
          if(!deque.isEmpty() && deque.getFirst() <= (i - k)){
              deque.removeFirst();
          }

          while(!deque.isEmpty() && nums[i] >= nums[deque.getLast()]){
              deque.removeLast();
          }

          deque.addLast(i);

          if(i >= s){
              result[j++] = nums[deque.getFirst()];
          }
      }

      return result;
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    int[][] input = {
            {},
            {1},
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
    int[] k = {0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 8};

    MaxSlideWindow sv = new MaxSlideWindow();
    for(int i = 0; i < input.length; i++){
    	
    	System.out.println(String.format("Input: %s k=%d", Misc.array2String(input[i]), k[i]));
    	System.out.print("Output: ");
        Misc.printArray_Int(sv.maxSlidingWindow_n(input[i], k[i]));
        
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
