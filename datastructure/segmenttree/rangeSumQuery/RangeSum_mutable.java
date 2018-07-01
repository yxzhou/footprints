package fgafa.datastructure.segmenttree.rangeSumQuery;


/**
 * 
 * Given an integer array in the construct method, implement two methods query(start, end) and modify(index, value):

	For query(start, end), return the sum from index start to index end in the given array.
	For modify(index, value), modify the number in the given index to value
	
	Example
	Given array origin = [1,2,7,8,5].
	
	query(0, 2), return 10.
	modify(0, 4), change origin[0] from 1 to 4.
	query(0, 1), return 6.
	modify(2, 1), change origin[2] from 7 to 1.
	query(2, 4), return 14.
	Note
	We suggest you finish problem Segment Tree Build, Segment Tree Query and Segment Tree Modify first.
	
	Challenge
	O(logN) time for query and modify.
 *
 * Solution:
 *   Interval Tree,
 *   Define TreeNode{
 *       int start;
 *       int end;
 *       long sum;
 *   }
 *
 *   Search with a interval [start, end]
 *
 *
 */

public class RangeSum_mutable {
	int[] origin = null;
	Node[] tree;  //interval tree
	
	public RangeSum_mutable(int[] nums){
        if(null == nums || 0 == nums.length ){
        	throw new IllegalArgumentException("origin cannot be null or empty");
        }
        
        this.origin = nums;

        initTree(nums, 0, nums.length - 1, tree, 0);
	}

	private long initTree(int[] nums, int start, int end, Node[] tree, int nodeIndex){
		tree[nodeIndex] = new Node(start, end);

		if(start == end){
			tree[nodeIndex].sum += nums[start];
		}else{
			int mid = start + ((end - start) >> 1);
			int leftSon = nodeIndex * 2 + 1;
			tree[nodeIndex].sum  += initTree(nums, start, mid, tree, leftSon);
			tree[nodeIndex].sum  += initTree(nums, mid + 1, end, tree, leftSon + 1);
		}

		return tree[nodeIndex].sum ;
	}

    /**
     * @param start, end: Indices
     * @return: The sum from start to end
     */
    public long query(int start, int end) {    		
        return query(tree, 0, start, end);
    }

	private long query(Node[] tree, int nodeIndex, int start, int end){
		if(nodeIndex >= tree.length || tree[nodeIndex].start > end || tree[nodeIndex].end < start){
			return 0;
		}

		if(start <= tree[nodeIndex].start && tree[nodeIndex].end <= end){
			return tree[nodeIndex].sum;
		}else{
			int result = 0;
			int leftSon = nodeIndex * 2 + 1;

			result += query(tree, leftSon, start, end);
			result += query(tree, leftSon + 1, start, end);
			return result;
		}
	}

    /**
     * @param index, value: modify origin[index] to value.
     */
    public void modify(int index, int value) {
    	modify(tree, 0, value, value - origin[index]);
    	origin[index] = value;
    }
    
    private void modify(Node[] tree, int pos, int newValue, int diff){
    	if(pos < tree.length && tree[pos].start <= newValue && newValue <= tree[pos].end ){
			tree[pos].sum += diff;

			int leftSon = pos * 2 + 1;
			modify(tree, leftSon, newValue, diff);
			modify(tree, leftSon + 1, newValue, diff);
		}
    }

	
	/**
	 * Definition of Node:
	 */
	public class Node {
		int start;
		int end;
		long sum = 0;

		public Node(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

}
