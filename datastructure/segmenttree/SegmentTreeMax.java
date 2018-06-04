package fgafa.datastructure.segmenttree;

public class SegmentTreeMax {
	/**
	 * For an integer array (index from 0 to n-1, where n is the size of this
	 * array), in the corresponding SegmentTree, each node stores an extra
	 * attribute max to denote the maximum number in the interval of the array
	 * (index from start to end).
	 * 
	 * Design a query method with three parameters root, start and end, find the
	 * maximum number in the interval [start, end] by the given root of segment
	 * tree.
	 * 
	 * Example
	 * For array [1, 4, 2, 3], the corresponding Segment Tree is:
	 * 
  	 *                   [0, 3, max=4]
	 *                  /             \
	 *           [0,1,max=4]        [2,3,max=3]
	 *           /         \        /         \
	 *    [0,0,max=1] [1,1,max=4] [2,2,max=2], [3,3,max=3]
	 *    
	 * query(root, 1, 1), return 4
	 * query(root, 1, 2), return 4
	 * query(root, 2, 3), return 3
	 * query(root, 0, 2), return 4
	 */
	/**
	 * Definition of SegmentTreeNode: */
	public class SegmentTreeNode {
		public int start, end, max;
		public SegmentTreeNode left, right;

		public SegmentTreeNode(int start, int end, int max) {
			this.start = start;
			this.end = end;
			this.max = max;
			this.left = this.right = null;
		}
	}

	/**
	 * @param root, start, end: The root of segment tree and an segment interval
	 * @return: The maximum number in the interval [start, end]
	 */
	public int query(SegmentTreeNode root, int start, int end) {
		// check
		if (null == root) {
			return Integer.MIN_VALUE;
		}

		if (start <= root.start && end >= root.end) {
			return root.max;
		} else if (start > root.end || end < root.start){
			return Integer.MIN_VALUE;
		}

		return Math.max(query(root.left, start, end),
				query(root.right, start, end));
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
