package fgafa.datastructure.segmenttree.rangeSum;

import java.util.ArrayList;

/**
 * 
 * Given an integer array (index from 0 to n-1, where n is the size of this array), and an query list. Each query has two integers [start, end].
 * For each query, calculate the sum number between index start and end in the given array, return the result list.

	Example
	For array [1,2,7,8,5], and queries [(0,4),(1,2),(2,4)], return [23,9,20]
	
	Note
	We suggest you finish problem Segment Tree Build, Segment Tree Query and Segment Tree Modify first.
	
	Challenge
	O(logN) time for each query
 *
 */

public class RangeSum_immutableII {
	
	/**
	 * @param A, queries: Given an integer array and an query list
	 * @return: The result list
	 */
	public ArrayList<Long> intervalSum(int[] A, ArrayList<Interval> queries) {
		ArrayList<Long> result = new ArrayList<Long>();
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

	private long query(SegmentTreeNode node, int start, int end){
		long sum = 0;
		if(null == node){
			return sum;
		}
		
		if(node.start >= start && node.end <= end){
			sum += node.sum;
		}else if(node.start <= end && node.end >= start ){
			sum += query(node.left, start, end);
			sum += query(node.right, start, end);
		}
		
		return sum;
	}
	
	private SegmentTreeNode build(int[] A, int start, int end){

		SegmentTreeNode node = new SegmentTreeNode(start, end);
				
		if(start < end){
			int mid = start + ((end - start) >> 1);
			
			node.left = build(A, start, mid);
			node.right = build(A, mid + 1, end);
			
			node.sum = node.left.sum + node.right.sum;
		}else{
			node.sum = A[start];
		}

		return node;
	}
	
	
	/**
	 * Definition of SegmentTreeNode:
	 */
	public class SegmentTreeNode {
		public int start, end;
		public long sum = 0;
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
