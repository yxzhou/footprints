package sorting;

import util.Misc;

/**
 * 
 * Given an unsorted array, find the maximum difference between the successive
 * elements in its sorted form.
 * 
 * Try to solve it in linear time/space.
 * 
 * Return 0 if the array contains less than 2 elements.
 * 
 * You may assume all elements in the array are non-negative integers and fit in
 * the 32-bit signed integer range.
 *
 *
 * Solution:
 * 1 quick/merge sorted and check the gap
 * O(nlogn)  space O(1)
 * 
 * 2 bucket sort. 
 * O(n)  space O(n)
 * 
 */
public class MaximumGap {

	/*
	 * bucket sort
	 */
    public int maximumGap(int[] nums) {
        if (null == nums || 2 > nums.length) {
            return 0;
        }

        // iterate through the nums, find the max and min
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i : nums) {
            max = Math.max(max, i);
            min = Math.min(min, i);
        }

        // calculate the bucket length and number of buckets
        int bucketLength = Math.max((max - min) / (nums.length - 1), 1); //for case (2-1)/5 = 0
        int bucketNum = (max - min) / bucketLength + 1; // **
        int[] bucketMax = new int[bucketNum]; // the max in the bucket
        int[] bucketMin = new int[bucketNum]; // the min in the bucket
        for (int i = 0; i < bucketNum; i++) {
            bucketMin[i] = Integer.MAX_VALUE;
            bucketMax[i] = Integer.MIN_VALUE;
        }

        // iterate through the nums and assign the number into the buckets
        for (int num : nums) {
            // if( max != i){
            int index = (num - min) / bucketLength;

            bucketMin[index] = Math.min(bucketMin[index], num);
            bucketMax[index] = Math.max(bucketMax[index], num);
            // }
        }

        // iterate through the buckets and get the max gap
        int maxGap = Integer.MIN_VALUE;
        int previous = min;
        for (int i = 0; i < bucketNum; i++) {
            if (Integer.MIN_VALUE != bucketMax[i]) {
                maxGap = Math.max(maxGap, bucketMin[i] - previous);
                previous = bucketMax[i];
            }
        }

        // maxGap = Math.max(maxGap, max - previous);
        return maxGap;
    }

	public static void main(String[] args) {
		MaximumGap sv = new MaximumGap();
		
		int[][] input = {
				null,
				{1},
				{1,2},
				{1,3},
				{2,3},
				{1,2,3},
				{1,2,4},
				{1,2,5},
				{1,3,4},
				{1,3,5},
				{1,3,6},
				{1,5,6},
				{1,9,10},
				{1,2,3,4},
				{1,5,6,7},
				{1,5,6,8},
				{1,1,1,1,1,5,5,5,5,5},
		};

		for(int[] num : input){
			System.out.println("\n Input:" + Misc.array2String(num)); 
			
			System.out.println(sv.maximumGap(num));
		}
		
	}

}
