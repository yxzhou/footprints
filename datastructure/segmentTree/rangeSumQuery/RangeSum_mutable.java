package fgafa.datastructure.segmentTree.rangeSumQuery;


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
	int[] tree;  //interval tree, store the sum of leftChildren tree
	
	public RangeSum_mutable(int[] nums){
        if(null == nums || 0 == nums.length ){
        	throw new IllegalArgumentException("origin cannot be null or empty");
        }
        
        this.origin = nums;

        int length = nums.length;
		int[] tree = new int[length * 2 - 1]; //defaul all are 0

        initTree(tree, 0, 0, length - 1, nums);
	}

	private long initTree(int[] tree, int nodeIndex, int start, int end, int[] nums){
		if(start == end){
			tree[nodeIndex] += nums[start];
		}else{
			int mid = start + ((end - start) >> 1);
			int leftSon = nodeIndex * 2 + 1;
			tree[nodeIndex] += initTree(tree, leftSon, start, mid, nums);
			tree[nodeIndex] += initTree(tree, leftSon + 1, mid + 1, end, nums);
		}

		return tree[nodeIndex];
	}

    /**
     * @param targetStart, end: Indices
     * @return: The sum from start to end
     */
    public long query(int targetStart, int targetEnd) {
        return query(tree, 0, 0, origin.length - 1, targetStart, targetEnd);
    }

	private long query(int[] tree, int nodeIndex, int nodeStart, int nodeEnd, int targetStart, int targetEnd){
		if(nodeStart == nodeEnd){
			return tree[nodeIndex];
		}

		if(targetStart <= nodeStart && nodeEnd <= targetEnd){
			return tree[nodeIndex];
		}else{
			int result = 0;
			int leftSon = nodeIndex * 2 + 1;
			int nodeMiddle = nodeStart + (nodeEnd - nodeStart) / 2;

			result += query(tree, leftSon, nodeStart, nodeMiddle, targetStart, targetEnd);
			result += query(tree, leftSon + 1, nodeMiddle = 1, nodeEnd, targetStart, targetEnd);
			return result;
		}
	}

    /**
     * @param index, value: modify origin[index] to value.
     */
    public void modify(int index, int value) {
    	modify(tree, 0, 0, origin.length - 1, value, value - origin[index]);
    	origin[index] = value;
    }
    
    private void modify(int[] tree, int nodeIndex, int nodeStart, int nodeEnd, int newValue, int diff){
    	if(nodeStart == nodeEnd){
			tree[nodeIndex] += diff;
			return;
		}

    	if(nodeStart <= newValue && newValue <= nodeEnd ){
			tree[nodeIndex] += diff;

			int leftSon = nodeIndex * 2 + 1;
			int nodeMiddle = nodeStart + (nodeEnd - nodeStart) / 2;
			modify(tree, leftSon, nodeStart, nodeMiddle, newValue, diff);
			modify(tree, leftSon + 1, nodeMiddle + 1, nodeEnd, newValue, diff);
		}
    }
}
