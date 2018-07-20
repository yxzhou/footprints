package fgafa.datastructure.segmentTree;

/**
 *
 *  1: How to store the tree in a array?
 *     when [1, 6], the tree is not "right"
 *                     [0, 5]
 *                  /          \
 *              [0, 2]        [3, 5]
 *            /       \      /       \
 *         [0, 1]  [2, 2] [3, 4]  [5, 5]
 *         /    \         /    \
 *      [0,0]  [1,1]   [3,3]  [4,4]
 *
 *  so to nums.length == 6, build a larger scrope [0, 7]
 *                     [0, 7]
 *                 /             \
 *             [0, 3]          [4, 7]
 *          /        \       /         \
 *       [0, 1]    [2, 3]  [4, 5]     [6, 7]
 *       /    \    /   \    /   \      /   \
 *    [0,0][1,1][2,2][3,3][4,4][5,5][6,6][7,7]
 *
 *
 *  2: for better performance, make leftson = nodeIndex * 2, instead of nodeIndex*2 + 1;
 *     the root nodeIndex is 1, 1 -> 2 and 3
 *
 */

public class SegmentTreeBuildWithMax2 {

	/**
	 * Definition of Node:
	 */
	class SegmentTreeNode {
		int start;
		int end;
		int max;

		SegmentTreeNode(int start, int end, int max) {
			this.start = start;
			this.end = end;
			this.max = max;
		}
	}

	/**
	 * @param nums
	 *            : a list of integer
	 * @return: The root of Segment Tree
	 */
	public int[] build(int[] nums) {
		// check
		if (null == nums || 0 == nums.length) {
			return null;
		}

		int length = nums.length;

		int n = 1;
		while (n < length){
			n <<= 1;
		}
		n -= 1;

		int[] tree = new int[n * 2];

		build(tree, 1, 0, length - 1, nums);
		return tree;
	}



	private void build(int[] tree, int nodeIndex, int start, int end, int[] nums) {

		if (start == end) {
			tree[nodeIndex] = nums[start];
		} else {
			int mid = start + ((end - start) >> 1);
			int leftSon = nodeIndex * 2 + 1;

			build(tree, leftSon, start, mid, nums);
			build(tree, leftSon = 1,  mid + 1, end, nums);

			tree[nodeIndex] = Math.max(tree[leftSon], tree[leftSon = 1]);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
