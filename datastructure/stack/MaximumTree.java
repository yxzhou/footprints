package fgafa.datastructure.stack;

import java.util.Stack;

import fgafa.tree.TreeNode;

/**
 * http://www.lintcode.com/en/problem/ 
 * Given an array of integers. Define a MaxTree on this array which 
 * the root is the maximum number in the array, the left subtree is the MaxTree 
 * represent by the left subarray of the maximum number, and so is right subtree.
 * 
 * Example: 
 * Input [2,1,5,6,0,3]
 * Return 
 *              6
 *          /        \
 *         5          3 
 *        /          /
 *       2          0
 *        \
 *         1    
 *             
 * Further more
 * Construct a MaxTree by a given integer array in O(n) time and space.
 *
 */

public class MaximumTree {

    /*
     * Time O(n^2) 
     */
    public TreeNode maxTree(int[] nums){
        if(null == nums){
            return null;
        }
        
        return maxTree(nums, 0, nums.length - 1);
    }

    private TreeNode maxTree(int[] nums, int start, int end){
        if(start < end){
            return null;
        }
        if(start == end){
            return new TreeNode(nums[start]);
        }
        
        int maxIndex = indexOfMax(nums, start, end);
        TreeNode parent = new TreeNode(nums[maxIndex]);
        
        parent.left = maxTree(nums, start, maxIndex - 1);
        parent.right = maxTree(nums, maxIndex + 1, end);
        
        return parent;
    }
    
    private int indexOfMax(int[] nums, int start, int end){
        int maxIndex = 0;
        
        for(int i = 1; i < nums.length; i++){
            if(nums[maxIndex] < nums[i]){
                maxIndex = i;
            }
        }
        
        return maxIndex;
    }
    
    /*
     * with a Stack
     * 
     * f(n) = f(n - 1) + 1
     *   
     *  time complexity  T(n) = O(n)
     */

    public TreeNode maxTree_x(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        TreeNode root = new TreeNode(nums[0]);

        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        TreeNode next;
        TreeNode pre;
        for (int i = 1; i < nums.length; i++) {

            next = new TreeNode(nums[i]);

            if (curr.val < next.val) {
                if (stack.isEmpty() || stack.peek().val > next.val) {
                    next.left = curr;
                    curr = next;
                } else {
                    pre = stack.pop();
                    pre.right = curr;
                    curr = pre;
                }

            } else { // curr.val > next.val
                stack.push(curr);
                curr = next;
            }

        }

        while (!stack.isEmpty()) {
            pre = stack.pop();
            pre.right = curr;
            curr = pre;
        }

        return root;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
