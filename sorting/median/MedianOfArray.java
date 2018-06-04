package fgafa.sorting.median;

import java.util.Random;

/**
 * Given a sorted integer array, find the median.
 * Given a unsorted array with integers, find the median.
 * 
 * A median is the middle number of the array after it is sorted.
 * 
 * If there are even numbers in the array, return the N/2-th number after
 * sorted.
 * 
 * Example Given [4, 5, 1, 2, 3], return 3
 * 
 * Given [7, 9, 4, 5], return 5
 * 
 * @param nums: A list of integers.
 * @return: An integer denotes the middle number of the array.
 */

public class MedianOfArray {

	/**
	 * @param nums: A list of sorted integers.
	 * @return: An integer denotes the middle number of the array.
	 */
	public double medianOfSortedArray(int[] A) {
		if (A == null || A.length == 0)
			throw new IllegalArgumentException("");

		int n = A.length;
		int mid = (n >> 1);
		if (isOdd(n)){
			return A[mid];
		}else{
			return (A[mid] + A[mid - 1]) >> 1;
		}
	}

	private boolean isOdd(int x) {
		return (x & 1) == 1;
	}

	private boolean isEven(int x) {
		return (x & 1) == 0;
	}
	
	/**
	 * @param nums: A list of integers. (unsorted)
	 * @return: An integer denotes the middle number of the array.
	 */
	/* Time O(n) Space O(1) */
	public int medianOfUnsortedArray_quicksort(int[] nums) {
		// check
		if (null == nums || 0 == nums.length) {
			return Integer.MIN_VALUE; // error
		}

		int start = 0;
		int end = nums.length - 1;
		int mid = end >> 1;
		int pivot;

		while (start < end) {
			pivot = quickSort(nums, start, end);

			if (mid == pivot) {
				return nums[mid];
			} else if (mid < pivot) {
				end = pivot - 1;
			} else {
				start = pivot + 1;
			}
		}

		return nums[start];
	}
	
	private int quickSort(int[] nums, int start, int end){
		int pivot = start + new Random().nextInt(end - start + 1);
		
		swap(nums, pivot, start);
		pivot = start;
		start++;
		while(start < end){
			if(nums[pivot] >= nums[start]){
				start++;
			}else{
				swap(nums, start, end);
				end--;
			}
		}

		if(nums[pivot] < nums[start]){
			start-- ;
		}
		swap(nums, pivot, start);
		return start;
	}
	
	private void swap(int[] nums, int i, int j){
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}
  
 
}
