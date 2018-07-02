package fgafa.datastructure.segmentTree;

import fgafa.util.Misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 
 * Given an integer array (index from 0 to n-1, where n is the size of this array), and an query list.
 * Each query has two integers [start, end]. For each query, calculate the minimum number between index start and end in the given array, return the result list.

	Example
	For array [1,2,7,8,5], and queries [(1,2),(0,4),(2,4)], return [2,1,5]
	
	Note
	We suggest you finish problem Segment Tree Build, Segment Tree Query and Segment Tree Modify first.
	
	Challenge
	O(logN) time for each query
 *
 */

public class IntervalMin {
	
	/**
	 * @param nums, queries: Given an integer array and an query list
	 * @return: The result list
	 */
	public List<Integer> intervalMinNumber(int[] nums, List<Interval> queries) {
		List<Integer> result = new ArrayList<Integer>();
		//check
        if(null == nums || 0 == nums.length || null == queries){
        	return result;
        }

        int length = nums.length;
		int[] tree = new int[length * 2 - 1]; // tree[i] is the min value in the interval
        initTree(tree, 0, 0, length - 1, nums);

        for(Interval interval : queries){
        	result.add(query(tree, 0, 0, length - 1, interval));
        }
		
		return result;
	}

	private int initTree(int[] tree, int nodeIndex, int start, int end, int[] nums){
		if(start == end ){
			tree[nodeIndex] = nums[start];
		}else{
			int mid = start + ((end - start) >> 1);
			int leftSon = nodeIndex * 2 + 1;
			tree[nodeIndex] = initTree(tree, leftSon, start, mid, nums);
			tree[nodeIndex] = Math.min(tree[nodeIndex], initTree(tree, leftSon + 1, mid + 1, end, nums));
		}

		return tree[nodeIndex];
	}

	private int query(int[] tree, int nodeIndex, int nodeStart, int nodeEnd, Interval interval){
		int min = Integer.MAX_VALUE;

		if(nodeStart <= nodeEnd) {
		    if(interval.end < nodeStart || nodeEnd < interval.start){
		    	//do nothing
		    }else if(interval.start <= nodeStart && nodeEnd <= interval.end){
				min = tree[nodeIndex];
			}else{ //intersection
				int leftSon = nodeIndex * 2 + 1;
				int middle = nodeStart + (nodeEnd - nodeStart) / 2;

				min = Math.min(min, query(tree, leftSon, nodeStart, middle, interval));
				min = Math.min(min, query(tree, leftSon + 1, middle + 1, nodeEnd, interval));
			}
		}

		return min;
	}


	/**
	 * Definition of Node:
	 */
//	public class SegmentTreeNode {
//		int start;
//		int end;
//		int min = Integer.MAX_VALUE;
//
//		SegmentTreeNode(int start, int end){
//			this.start = start;
//			this.end = end;
//		}
//	}

	/**
	 * Definition of Interval:
	 */
	public static class Interval {
		int start;
		int end;

		Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}


	public static void main(String[] args){
		int[] input = {1,2,7,8,5};

		Interval[] queries = {
			new Interval(1, 2),
			new Interval(0, 4),
			new Interval(2, 4)
		};

		IntervalMin sv = new IntervalMin();

		//for(int[] nums : input){
			System.out.println(String.format("%s", Misc.array2String(input)));

			Misc.printList(sv.intervalMinNumber(input, Arrays.asList(queries)));
		//}
	}

}
