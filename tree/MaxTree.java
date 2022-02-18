package tree;

import java.util.Arrays;
import java.util.Stack;
import org.junit.Assert;
import tree.traversal.IterativeTraversal2;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/126
 * 
 * Given an integer array with no duplicates. A max tree building on this array is defined as follow: 
 *   The root is the maximum number in the array 
 *   The left subtree and right subtree are the max trees of the subarray divided by the root number.
    
   Construct the max tree by the given array.

    Example
    Given [2, 5, 6, 0, 3, 1], the max tree constructed by this array is:

        6
       / \
      5   3
     /   / \
    2   0   1


    Given [8, 2, 5, 6, 0, 3, 1, 7], the max tree constructed by this array is:

        8
         \
          7
         /
        6
       / \
      5   3
     /   / \
    2   0   1

 * 
 * Challenge  O(n) time and memory.
 * 
 * Thoughts:
 *   The input array is the in-order traversal result. It need to deserialization
 *  
 * 
 */

public class MaxTree {

    /**
     * Time O(n* logn),  Space O()
     * 
     * @param nums: Given an integer array with no duplicates.
     * @return The root of max tree.
     * 
     */

    public TreeNode maxTree(int[] nums) {
        if (null == nums || 0 == nums.length) {
            return null;
        }

        return maxTree(nums, 0, nums.length);
    }

    /**
     * start-include, end-exclude
     */
    private TreeNode maxTree(int[] nums, int start, int end) {

        int maxIndex = -1;
        int max = Integer.MIN_VALUE;
        for (int i = start; i < end; i++) {
            if (max < nums[i]) {
                max = nums[i];
                maxIndex = i;
            }
        }

        TreeNode node = new TreeNode(max);
        if (start < maxIndex) {
            node.left = maxTree(nums, start, maxIndex);
        }

        if (maxIndex + 1 < end) {
            node.right = maxTree(nums, maxIndex + 1, end);
        }

        return node;
    }


    /**
     * Time O(n),  Space O(n)
     * 
     * @param nums
     * @return 
     */
    public TreeNode maxTree_n(int[] nums) {
        if (null == nums || 0 == nums.length) {
            return null;
        }

        Stack<TreeNode> st = new Stack<>();
        TreeNode node;
        TreeNode preRoot;

        for (int num : nums) {
            node = new TreeNode(num);

            if (!st.empty() && num > st.peek().val) {
                preRoot = st.pop();

                while (!st.empty() && num > st.peek().val) {
                    st.peek().right = preRoot;
                    preRoot = st.pop();
                }

                node.left = preRoot;
            }

            st.push(node);
        }

        node = st.pop();
        while (!st.empty()) {
            st.peek().right = node;
            node = st.pop();
        }
        return node;
    }

    public TreeNode maxTree_n2(int[] nums) {
        if (null == nums || 0 == nums.length) {
            return null;
        }

        Stack<TreeNode> nodes = new Stack<>();
        TreeNode curr;
        for (int num : nums) {
            curr = new TreeNode(num);

            if (!nodes.isEmpty() && num > nodes.peek().val) {
                TreeNode top = nodes.pop();

                while (!nodes.isEmpty() && num > nodes.peek().val) {
                    nodes.peek().right = top;
                    top = nodes.pop();
                }

                curr.left = top;
            }

            nodes.push(curr);
        }

        TreeNode top = nodes.pop();
        while (!nodes.isEmpty()) {
            nodes.peek().right = top;
            top = nodes.pop();
        }

        return top;
    }
    
    /**
     * @param nums: Given an integer array with no duplicates.
     * @return The root of max tree.
     */
    public TreeNode maxTree_x(int[] nums) {
        if(nums == null || nums.length < 1){
            return null;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        stack.add(new TreeNode(nums[0]));

        TreeNode curr;
        TreeNode top;
        TreeNode right;
        for(int i = 1; i < nums.length; i++){
            curr = new TreeNode(nums[i]);

            if( stack.peek().val < curr.val ){

                right = stack.pop();
                while(!stack.isEmpty() && stack.peek().val < curr.val ){
                    top = stack.pop();
                    top.right = right;

                    right = top;
                }

                curr.left = right;
            }

            stack.add(curr);
        }

        right = stack.pop();
        while(!stack.isEmpty() ){
            top = stack.pop();
            top.right = right;

            right = top;
        }

        return right;
    }
    
    public static void main(String[] args){
        
        int[][] inputs = {
            {6,4,20},
            {6,4,2,20},
            {2,5,6,0,3,1}  
        };
        
        MaxTree sv = new MaxTree();
        
        IterativeTraversal2 sv2 = new IterativeTraversal2();
        
        for(int[] input : inputs){
            System.out.println(String.format("\n%s", Arrays.toString(input) ));
            
            Assert.assertEquals( Misc.array2String(input).toString(), Misc.array2String( sv2.inorder_iterative_3(sv.maxTree_x(input))).toString() );
            
        }
        
    }
    
}
