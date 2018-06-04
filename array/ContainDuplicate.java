package fgafa.array;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import fgafa.util.Misc;

public class ContainDuplicate {

	/**
	 * 
	 * Given an array of integers, find if the array contains any duplicates.
	 * Your function should return true if any value appears at least twice in
	 * the array, and it should return false if every element is distinct.
	 */
	/*Time O(n), Space O(n)*/
	public boolean containsDuplicate_hash(int[] nums) {
		//check the input
		if(null == nums){
			return false;
		}
		
		Set<Integer> set = new HashSet<>(nums.length);
		for(int num : nums){
			if(!set.add(num)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * Given an array of integers and an integer k, find out whether there
	 * are two distinct indices i and j in the array such that nums[i] = nums[j]
	 * and the difference between i and j is at most k.
	 */
	public boolean containsNearbyDuplicate(int[] nums, int k) {
		//check the input
		if(null == nums || 0 == nums.length || k <= 0){
			return false;
		}
//		if(0 == k){
//			return true;
//		}
		
		
		Set<Integer> set = new HashSet<>(k+1);
		for(int i = 0; i< nums.length; i++){
			if(i > k){
				set.remove(nums[i - k - 1]);
			}
				
			if(!set.add(nums[i])){
				return true;
			}
		}
		
		return false;	
	}

	/**
	 * 
	 * Given an array of integers, find out whether there are two distinct
	 * indices i and j in the array such that the difference between nums[i] and
	 * nums[j] is at most t and the difference between i and j is at most k.
	 */
	public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
		//check the input
		if(null == nums || 0== nums.length || k <= 0 || t < 0){
			return false;
		}
		
		PriorityQueue<Integer> minHeap=new PriorityQueue<>(k+1);
		PriorityQueue<Integer> maxHeap=new PriorityQueue<>(k+1, new Comparator<Integer>(){
			@Override
			public int compare( Integer x, Integer y )
		    {
		        return y - x;
		    }
		});
		
		for(int i = 0; i< nums.length; i++){
			if(i > k){
				minHeap.remove(nums[i - k - 1]);
				maxHeap.remove(nums[i - k - 1]);
			}

			if(!minHeap.isEmpty() || !maxHeap.isEmpty()){
				if(Math.abs(nums[i] - (int)minHeap.peek()) <= t || Math.abs(nums[i] - (int)maxHeap.peek()) <= t){
					return true;
				}
			}
				
			minHeap.add(nums[i]);
			maxHeap.add(nums[i]);
		}
		
		return false;	
	}
	
	public boolean containsNearbyAlmostDuplicate_n(int[] nums, int k, int t) {
		//check the input
		if(null == nums || 0== nums.length || k < 1 || t < 0){
			return false;
		}

		TreeSet<Integer> set = new TreeSet<>();

		for (int i = 0; i < nums.length; i++) {
			int c = nums[i];
			if ((set.floor(c) != null && c <= set.floor(c) + t)
					|| (set.ceiling(c) != null && c >= set.ceiling(c) - t)){
				return true;
			}

			set.add(c);

			if (i >= k)
				set.remove(nums[i - k]);
		}

		return false;
	}
    
	public static void main(String[] args) {
		ContainDuplicate sv = new ContainDuplicate();
		
		int[][] input = {
				null,
				{1},
				
		};

		int[] k = {1, 1, };
		int[] t = {};
		
		for(int[] nums : input){
			System.out.println(" Input: " + Misc.array2String(nums));
			System.out.println("Output: " + sv.containsNearbyAlmostDuplicate(nums, 1, 1));
		}
		
	}

}
