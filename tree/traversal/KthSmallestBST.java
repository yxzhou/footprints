package tree.traversal;

import tree.TreeNode;

import java.util.Stack;

public class KthSmallestBST {
    
    /**
     * inorder traversal
     * 
     * @param root: the given BST
     * @param k: the given k
     * @return the kth smallest element in BST
     */
    public int kthSmallest(TreeNode root, int k) {
        TreeNode curr = root;
        Stack<TreeNode> stack = new Stack<>();

        while(curr != null || !stack.isEmpty()){
            if(curr == null){
                curr = stack.pop();

                if(--k == 0){
                    return curr.val;
                }

                curr = curr.right;
            }else{
                stack.add(curr);
                curr = curr.left;
            }

        }

        //assume k is always valid
        return -1;
    }
    
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
