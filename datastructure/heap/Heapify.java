package datastructure.heap;

import util.Misc;

/**
 * 
 * Given an integer array, heapify it into a min-heap array.
 * 
 * For a heap array A, A[0] is the root of heap, and for each A[i], A[i * 2 + 1]
 * is the left child of A[i] and A[i * 2 + 2] is the right child of A[i].
 * 
 * Example Given [3,2,1,4,5], return [1,2,3,4,5] or any legal heap array.
 * 
 * Challenge O(n) time complexity
 * 
 * Clarification What is heap?
 * 
 * Heap is a data structure, which usually have three methods: push, pop and
 * top. where "push" add a new element the heap, "pop" delete the
 * minimum/maximum element in the heap, "top" return the minimum/maximum
 * element.
 * 
 * What is heapify? Convert an unordered integer array into a heap array. If it
 * is min-heap, for each element A[i], we will get A[i * 2 + 1] >= A[i] and A[i * 2 + 2] >= A[i].
 * 
 * What if there is a lot of solutions? Return any of them.
 *
 */

public class Heapify {
    /*
     * Solution: 
     *   from bottom to up, to every sub-tree, do shiftDown
     * 
     * Time Complexity O(n)  
     * Why it's O(n) instead of O( nlogn ) ?  
     */
    public void heapify(int[] A) {
        //check
    	if(null == A){
    		return;
    	}
    	
    	for(int i = (A.length >> 1) - 1; i >= 0; i-- ){
    		shiftDown_recusive(A, i);
    	}
    }
    
    private void shiftDown_recusive(int[] A, int i){
		int left = (i << 1) + 1;
		if(left >= A.length){
			return;
		}
		
		int min = left;
		int right = left + 1;
		if(right < A.length && A[left] > A[right]){
			min = right;
		}
		
		if(A[i] > A[min]){
			swap(A, min, i);
			shiftDown_recusive(A, min);
		}
    }
    
    private void shiftDown_iteration(int[] A, int i){
    	int left = (i << 1) + 1;
    	int min;
    	while(left < A.length){
    		min = left;
    		int right = left + 1;
    		if(right < A.length && A[left] > A[right]){
    			min = right;
    		}
    		
    		if(A[i] <= A[min]){
    			break;
    		}
    		
			swap(A, min, i);
			i = min;
			left = (i << 1) + 1;
    	}
    }
    
    private void swap(int[] A, int i, int j){
    	int tmp = A[i];
    	A[i] = A[j];
    	A[j] = tmp;
    }
    
    public static void main(String[] args){
    	Heapify sv = new Heapify();
    	
    	int[][] input = {
//    			{2,1},
//    			{3,2,1},
//    			{4,3,2,1},
//    			{5,4,3,2,1},
//    			{6,5,4,3,2,1},
    			{42,30,27,93,8,34,47,64,82,76,70,79,23,5,67,9,97,29,7,61,73,3,44,85,51,49,90,59,38,55,39,62,54,81,26,50,22,71,52,41,77,32,2,96,84,20,48,17,87,94,12,13,89,24,6,74,69,78,65,35,15,28,25,16,33,63}
    	};
    	
    	for(int[] nums: input){
    		System.out.println(String.format("Input: %s", Misc.array2String(nums)));
    		
    		sv.heapify(nums);
    		System.out.println(String.format("Output: %s", Misc.array2String(nums)));
    	}
    }
}
