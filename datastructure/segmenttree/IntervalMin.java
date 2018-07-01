package fgafa.datastructure.segmenttree;

import java.util.ArrayList;
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

		SegmentTreeNode[] tree = new SegmentTreeNode[nums.length];
        initTree(nums, 0, nums.length - 1, tree, 0);
        for(Interval interval : queries){
        	result.add(query(tree, 0, interval));
        }
		
		return result;
	}

	private int initTree(int[] nums, int start, int end, SegmentTreeNode[] tree, int pos){
		tree[pos] = new SegmentTreeNode(start, end);

		if(start == end ){
			tree[pos].min = nums[start];
		}else{
			int mid = start + ((end - start) >> 1);

			int leftSon = pos * 2 + 1;
			tree[pos].min = Math.min(tree[pos].min, initTree(nums, start, mid, tree, leftSon));
			tree[pos].min = Math.min(tree[pos].min, initTree(nums, mid + 1, end, tree, leftSon + 1));
		}

		return tree[pos].min;
	}

	private int query(SegmentTreeNode[] tree, int pos, Interval interval){
		int min = Integer.MAX_VALUE;

		if(pos >= tree.length || interval.end < tree[pos].start || interval.start > tree[pos].end){
			//do nothing
		}else if(interval.start <= tree[pos].start && tree[pos].end <= interval.end){
			min = tree[pos].min;
		}else {
			int leftSon = pos * 2 + 1;
			min = Math.min(min, query(tree, leftSon, interval));
			min = Math.min(min, query(tree, leftSon + 1, interval));
		}
		
		return min;
	}

	
	/**
	 * Definition of Node:
	 */
	public class SegmentTreeNode {
		int start;
		int end;
		int min = Integer.MAX_VALUE;

		SegmentTreeNode(int start, int end){
			this.start = start;
			this.end = end;
		}
	}

	/**
	 * Definition of Interval:
	 */
	public class Interval {
		int start;
		int end;

		Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}
}
