package fgafa.datastructure.segmenttree.rangeSum;


/**
 * 
 * Given an integer array in the construct method, implement two methods query(start, end) and modify(index, value):

	For query(start, end), return the sum from index start to index end in the given array.
	For modify(index, value), modify the number in the given index to value
	
	Example
	Given array A = [1,2,7,8,5].
	
	query(0, 2), return 10.
	modify(0, 4), change A[0] from 1 to 4.
	query(0, 1), return 6.
	modify(2, 1), change A[2] from 7 to 1.
	query(2, 4), return 14.
	Note
	We suggest you finish problem Segment Tree Build, Segment Tree Query and Segment Tree Modify first.
	
	Challenge
	O(logN) time for query and modify.
 *
 */

public class RangeSum_mutable {
	int[] A = null;
	SegmentTreeNode root = null;
	
	public RangeSum_mutable(int[] A){		
//        if(null == A || 0 == A.length ){
//        	throw new IllegalArgumentException("A cannot be null or empty");
//        }
        
        this.A = A;
		if(null != A && A.length > 0){
	        this.root = build(A, 0, A.length - 1);			
		}

	}
	
    /**
     * @param start, end: Indices
     * @return: The sum from start to end
     */
    public long query(int start, int end) {    		
        return query(this.root, start, end);
    }
    
    /**
     * @param index, value: modify A[index] to value.
     */
    public void modify(int index, int value) {
    	modify(this.root, index, value);
    	A[index] = value;
    }
    
    private void modify(SegmentTreeNode node, int index, int value){
    	while(null != node && index >= node.start && index <= node.end){
			node.sum += value - A[index];
			
			int mid = node.start + ((node.end - node.start) >> 1);
			if(index <= mid){
				node = node.left;
			}else{
				node = node.right;
			}
    	}
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
		int start, end;
		long sum = 0;
		SegmentTreeNode left;
		SegmentTreeNode right;

		public SegmentTreeNode(int start, int end) {
			this.start = start;
			this.end = end;
			this.left = this.right = null;
		}
	}

}
