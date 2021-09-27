package tree;

import java.util.Stack;

/**
 * 
 * Given an integer array with no duplicates. A max tree building on this array is defined as follow:

The root is the maximum number in the array
The left subtree and right subtree are the max trees of the subarray divided by the root number.
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

 *Challenge  O(n) time and memory.
 */

public class MaxTree {

    /**
     * @param A: Given an integer array with no duplicates.
     * @return: The root of max tree.
     */
	/*Time O(n* logn),  Space O()*/
    public TreeNode maxTree(int[] A) {
        //check
        if(null == A || 0 == A.length){
            return null;
        }
        
        return maxTree(A, 0, A.length);
    }
    
    /** start-include, end-exclude */
    private TreeNode maxTree(int[] A, int start, int end){

        int maxIndex = -1;
        int max = Integer.MIN_VALUE;
        for(int i = start; i < end; i++){
            if(max < A[i]){
                max = A[i];
                maxIndex = i;
            }
        }
        
        TreeNode node = new TreeNode(max);
        if(start < maxIndex){
            node.left = maxTree(A, start, maxIndex);
        }

        if(maxIndex + 1 < end){
            node.right = maxTree(A, maxIndex + 1, end);
        }
        
        return node;
    }
	
    
	/*Time O(n),  Space O(n)*/
	public TreeNode maxTree_n(int[] A) {
		if (null == A || 0 == A.length) {
			return null;
		}

		Stack<TreeNode> st = new Stack<TreeNode>();
		TreeNode node;
		TreeNode preRoot;

		for (int num : A) {
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
}
