package fgafa.datastructure.segmenttree;

import java.util.ArrayList;


/**
 * 
 * Given an integer array (index from 0 to n-1, where n is the size of this array), and an query list. Each query has two integers [start, end]. For each query, calculate the minimum number between index start and end in the given array, return the result list.

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
	 * @param A, queries: Given an integer array and an query list
	 * @return: The result list
	 */
	public ArrayList<Integer> intervalMinNumber(int[] A, ArrayList<Interval> queries) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		//check
        if(null == A || 0 == A.length || null == queries){
        	return result;
        }
        
        SegmentTreeNode root = build(A, 0, A.length - 1);
        for(Interval interval : queries){
        	result.add(query(root, interval.start, interval.end));
        }
		
		return result;
	}

	private int query(SegmentTreeNode node, int start, int end){
		int min = Integer.MAX_VALUE;
		
//		if(null == node){ // it will not happen
//			return min;
//		}
		
		if(node.start >= start && node.end <= end){
			min = Math.min(min, node.min);
		}else if(node.start <= end && node.end >= start ){
			min = Math.min(min, query(node.left, start, end));
			min = Math.min(min, query(node.right, start, end));
		}
		
		return min;
	}
	
	private SegmentTreeNode build(int[] A, int start, int end){
		SegmentTreeNode node = new SegmentTreeNode(start, end);
				
		if(start < end){
			int mid = start + ((end - start) >> 1);
			
			node.left = build(A, start, mid);
			node.right = build(A, mid + 1, end);
			
			node.min = Math.min(node.left.min, node.right.min);
		}else{
			node.min = A[start];
		}

		return node;
	}
	
	/**
	 * Definition of SegmentTreeNode:
	 */
	public class SegmentTreeNode {
		public int start, end;
		public int min = Integer.MAX_VALUE;
		public SegmentTreeNode left, right;

		public SegmentTreeNode(int start, int end) {
			this.start = start;
			this.end = end;
			this.left = this.right = null;
		}
	}

	/**
	 * Definition of Interval:
	 */
	public class Interval {
		int start, end;

		Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}
}
