package fgafa.dp.sequence;

import fgafa.tree.TreeNode;

public class HouseRobber {

	/**
	 * 
	 * You are a professional robber planning to rob houses along a street. Each
	 * house has a certain amount of money stashed, the only constraint stopping
	 * you from robbing each of them is that adjacent houses have security
	 * system connected and it will automatically contact the police if two
	 * adjacent houses were broken into on the same night.
	 * 
	 * Given a list of non-negative integers representing the amount of money of
	 * each house, determine the maximum amount of money you can rob tonight
	 * without alerting the police.
	 */
    /*
     * DP
     *    init: 
     *      f(0) = num[0];
     *      f(1) = max(num[0], num[1])
     *    transit function:
     *      f(i) = max( f(i-1),  f(i-2) + num[i])
     */
	public int rob(int[] num) {
		//check
		if(null == num || 0 == num.length){
			return 0;
		}
		
		//dp
		int evenMax = 0;
		int oddMax = 0;
		for(int i=0; i<num.length; i++){
			if( 0 == (i & 1)){// even
				evenMax = Math.max(oddMax, evenMax + num[i]); 
			}else{// odd
				oddMax = Math.max(evenMax, oddMax + num[i]);
			}
		}
		
		//return
		return Math.max(evenMax, oddMax);
	}

	// start, inclusion; end, exclusion;
	private int rob(int[] num, int start, int end) {
		//dp
		int evenMax = 0;
		int oddMax = 0;
		for(int i=start; i<end; i++){
			if( 0 == (i & 1)){// even
				evenMax = Math.max(oddMax, evenMax + num[i]); 
			}else{// odd
				oddMax = Math.max(evenMax, oddMax + num[i]);
			}
		}
		
		//return
		return Math.max(evenMax, oddMax);
	}
	
	/**
	 * 
	 * After robbing those houses on that street, the thief has found himself a
	 * new place for his thievery so that he will not get too much attention.
	 * This time, all houses at this place are arranged in a circle. That means
	 * the first house is the neighbor of the last one. Meanwhile, the security
	 * system for these houses remain the same as for those in the previous
	 * street.
	 * 
	 * Given a list of non-negative integers representing the amount of money of
	 * each house, determine the maximum amount of money you can rob tonight
	 * without alerting the police.
	 */
	/*
	 * split the circle to line: it's 2 cases, 
	 *   1) include house #0, meanwhile it's exclude house #(n-1), the scope is [0, n-1) 
	 *   2) exclude house #0, the scope is [1, n)   
	 */
	public int rob_circle(int[] num) {
		//check
		if(null == num || 1 > num.length){
			return 0;
		}
		if(1 == num.length){
		    return num[0];
		}
				
		//return
		return Math.max(rob(num, 0, num.length - 1), rob(num, 1, num.length));
	}
	
	
	/**
	 * The thief has found himself a new place for his thievery again. There is only one entrance to this area, called the "root." 
	 * Besides the root, each house has one and only one parent house. After a tour, 
	 * the smart thief realized that "all houses in this place forms a binary tree". 
	 * It will automatically contact the police if two directly-linked houses were broken into on the same night.

        Determine the maximum amount of money the thief can rob tonight without alerting the police.
        
        Example 1:
             3
            / \
           2   3
            \   \ 
             3   1
        Maximum amount of money the thief can rob = 3 + (3 + 1) = 7.  the first level + the third level
        Example 2:
             3
            / \
           4   5
          / \   \ 
         1   3   1
        Maximum amount of money the thief can rob = 4 + 5 = 9.  the second level
        Example 2:
             3
            / \
           4   5
          / \   \ 
         2   3   1
           
        Maximum amount of money the thief can rob = 5 + (2 + 3)  = 10.  the 
	 */


    public int rob_native(TreeNode root) {
        if(null == root){
            return 0;
        }
        
        int max = root.val;
        if(null != root.left){
            max += rob_native(root.left.left) + rob_native(root.left.right);
        }
        if(null != root.right){
            max += rob_native(root.right.left) + rob_native(root.right.right);
        }
        
        return Math.max(max, rob_native(root.left) + rob_native(root.right));

    }

	/**
	 * Time O(n)
	 */
	public int rob_dp(TreeNode root) {
        if(null == root){
            return 0;
        }
        
        int[] result = helper(root);
        return Math.max(result[0], result[1]);
    }
    
    private int[] helper(TreeNode node){
        int[] curr = new int[2]; //default both are 0
        
        if (node == null) {
            return curr; 
        }
         
        int[] left = helper(node.left);
        int[] right = helper(node.right);
         
        curr[0] = node.val + left[1] + right[1];
        curr[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
         
        return curr;   
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
