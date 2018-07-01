package fgafa.datastructure.segmenttree;

public class SegmentTreeBuild {

	/**
	 * 
	 * The structure of Segment Tree is a binary tree which each node has two
	 * attributes start and end denote an segment / interval.
	 * 
	 * start and end are both integers, they should be assigned in following
	 * rules:
	 * 
	 * The root's start and end is given by build method. 
	 * The left child of node A has start=A.left, end=(A.left + A.right) / 2. 
	 * The right child of node A has start=(A.left + A.right) / 2 + 1, end=A.right. 
	 * if start equals to end, there will be no children for this node. 
	 * 
	 * Implement a build method with two parameters start and end, so that we can 
	 * create a corresponding segment tree with every node has the correct start 
	 * and end value, return the root of this segment tree.
	 * 
	 * Example
	 * Given start=0, end=3. The segment tree will be:
	 * 
	 *                [0,  3]
	 *              /        \
	 *       [0,  1]           [2, 3]
	 *       /     \           /     \
	 *    [0, 0]  [1, 1]     [2, 2]  [3, 3]
	 *    
	 * Given start=1, end=6. The segment tree will be:
	 * 
	 *                [1,  6]
	 *              /        \
	 *       [1,  3]           [4,  6]
	 *       /     \           /     \
	 *    [1, 2]  [3,3]     [4, 5]   [6,6]
	 *    /    \           /     \
	 * [1,1]   [2,2]     [4,4]   [5,5]
	 * 
	 * Clarification
	 * Segment Tree (a.k.a Interval Tree) is an advanced data structure which can support queries like:
	 * which of these intervals contain a given point
	 * which of these points are in a given interval
	 * See wiki: Segment Tree Interval Tree
	 */
	
	/**
	 * Definition of Node:
	 */
	class TreeNode {
		int start;
		int end;

		public TreeNode(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	/**
	 * @param start, end: Denote an segment / interval
	 *
	 * @return: Segment Tree
	 */
	public TreeNode[] build(int start, int end) {
		int length = end - start + 1;
		TreeNode[] tree = new TreeNode[length * 2 - 1]; //it's a full BST

		build(tree, 0, start, end);

		return tree;
	}

	private void build(TreeNode[] tree, int nodeIndex, int start, int end) {
		tree[nodeIndex] = new TreeNode(start, end);
		
		if(start < end){
			int mid = start + ((end - start) >> 1);
			int leftSon = nodeIndex * 2 + 1;

			build(tree, leftSon, start, mid);
			build(tree, leftSon + 1, mid + 1, end);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
