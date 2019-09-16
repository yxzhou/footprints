package fgafa.Leetcode.linkedIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * Given a non-empty binary search tree and a target value, find k values in the BST that are closestPair to the target.

 Note:

 Given target value is a floating point.
 You may assume k is always valid, that is: k ≤ total nodes.
 You are guaranteed to have only one unique set of k values in the BST that are closestPair to the target.

 Example:
 Input: root = [4,2,5,1,3], target = 3.714286, and k = 2

     4
    / \
   2   5
  / \
 1   3

 Output: [4,3]

 Follow up:
 Assume that the BST is balanced, could you solve it in less than O(n) runtime (where n = total nodes)?

 *
 */

public class BSTClosestValueII {

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

    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        Stack<TreeNode> smaller = new Stack<>();
        Stack<TreeNode> larger = new Stack<>();
        pushSmaller(root, target, smaller);
        pushLarger(root, target, larger);

        List<Integer> result = new ArrayList<>();
        TreeNode curr = null;
        while(result.size() < k){
            if(smaller.isEmpty() || (!larger.isEmpty() && larger.peek().val - target < target - smaller.peek().val)) {
                curr = larger.pop();
                pushLarger(curr.right, target, larger);
            }else{
                curr = smaller.pop();
                pushSmaller(curr.left, target, smaller);
            }

            result.add(curr.val);
        }

        return result;
     }

     private void pushSmaller(TreeNode node, double target, Stack<TreeNode> stack){
        while(node != null){
            if(node.val < target){
                stack.push(node);
                node = node.right;
            }else{
                node = node.left;
            }
        }
     }

    private void pushLarger(TreeNode node, double target, Stack<TreeNode> stack){
        while(node != null){
            if(node.val < target){
                node = node.right;
            }else{
                stack.push(node);
                node = node.left;
            }
        }
    }
}
