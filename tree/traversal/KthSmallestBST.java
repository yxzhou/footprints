package fgafa.tree.traversal;

import fgafa.tree.TreeNode;

import java.util.Stack;

public class KthSmallestBST {
    //inorder traversal
    public int kthSmallestBST(TreeNode root, int k) {
        //ignore check, You may assume k is always valid, 1 ≤ k ≤ BST's total element

        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        int result = 0;
        while(k > 0){
            if(null == curr){
                curr = stack.pop();
                result = curr.val;
                k--;
                curr = curr.right;
            }else{
                stack.add(curr);
                curr = curr.left;
            }
        }

        return result;
    }

    //someone say it cost space O(1)
    public int kthSmallestBST_R(TreeNode root, int k) {
        int leftCount = countNodes(root.left) + 1;
        if (leftCount == k) {
            return root.val;
        } else if (leftCount > k) {
            return kthSmallestBST_R(root.left, k);
        } else {
            return kthSmallestBST_R(root.right, k - leftCount);
        }
    }

    private int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return countNodes(root.left) + countNodes(root.right) + 1;
    }
}
