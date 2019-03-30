package fgafa.Leetcode.linkedIn;

import org.junit.Assert;
import org.junit.Test;

/**
 * Given a non-empty binary search tree and a target value, find the value in the BST that is closest to the target.

 Note:

 Given target value is a floating point.
 You are guaranteed to have only one unique value in the BST that is closest to the target.

 Example:
 Input: root = [4,2,5,1,3], target = 3.714286

     4
    / \
   2   5
  / \
 1   3

 Output: 4
 *
 *
 */

public class BSTClosestValue {

    /**
     * Definition for a binary tree node.
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public int closestValue(TreeNode root, double target) {
        double min = Double.MAX_VALUE;
        TreeNode minNode = root;

        TreeNode curr = root;
        TreeNode next;
        double diff;
        while (null != curr) {
            diff = target - curr.val;

            if (diff == 0) {
                return curr.val;
            } else if (diff > 0) {
                next = curr.right;
            } else {
                next = curr.left;
                diff = -diff;
            }

            if (diff < min) {
                min = diff;
                minNode = curr;
            }

            curr = next;
        }

        return minNode.val;
    }

    @Test
    public void test(){

        // {1},  4.425871
        TreeNode root = new TreeNode(1);
        Assert.assertEquals(1, closestValue(root, 4.425871));

        //{1, null, 8}  3.0

    }

}
