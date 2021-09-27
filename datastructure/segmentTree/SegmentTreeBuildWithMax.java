package datastructure.segmenttree;

/**
 *
 * The structure of Segment Tree is a binary tree which each node has two
 * attributes start and end denote an segment / interval.
 *
 * start and end are both integers, they should be assigned in following rules:
 *
 * The root's start and end is given by build method. The left child of node A
 * has start=A.left, end=(A.left + A.right) / 2. The right child of node A has
 * start=(A.left + A.right) / 2 + 1, end=A.right. if start equals to end, there
 * will be no children for this node. Implement a build method with a given
 * array, so that we can create a corresponding segment tree with every node
 * value represent the corresponding interval max value in the array, return the
 * root of this segment tree.
 *
 * Example
 * Given [3,2,1,4]. The segment tree will be:
 *
 *                  [0,  3] (max = 4)
 *                   /            \
 *         [0,  1] (max = 3)     [2, 3]  (max = 4)
 *         /        \               /             \
 * [0, 0](max = 3)  [1, 1](max = 2)[2, 2](max = 1) [3, 3] (max = 4)
 * Clarification
 * Segment Tree (a.k.a Interval Tree) is an advanced data structure which can support queries like:
 *
 * which of these intervals contain a given point
 * which of these points are in a given interval
 *
 */

public class SegmentTreeBuildWithMax {

    /**
     * Definition of Node:
     */
    public class SegmentTreeNode {
        int start;
        int end;
        int max;
        SegmentTreeNode left;
        SegmentTreeNode right;

        public SegmentTreeNode(int start, int end, int max) {
            this.start = start;
            this.end = end;
            this.max = max;
            this.left = this.right = null;
        }
    }

    /**
     * @param nums
     *            : a list of integer
     * @return: The root of Segment Tree
     */
    public SegmentTreeNode build(int[] nums) {
        // check
        if (null == nums || 0 == nums.length) {
            return null;
        }

        return build(nums, 0, nums.length - 1);
    }

    private SegmentTreeNode build(int[] nums, int start, int end) {
        SegmentTreeNode curr = new SegmentTreeNode(start, end, -1);

        if (start == end) {
            curr.max = nums[start];
        } else {
            int mid = start + ((end - start) >> 1);
            curr.left = build(nums, start, mid);
            curr.right = build(nums, mid + 1, end);

            curr.max = Math.max(curr.left.max, curr.right.max);
        }

        return curr;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
