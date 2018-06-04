package fgafa.tree;

import java.util.ArrayList;
import java.util.List;

public class BSTSearchRange {

	/**
	 * 
	 * Given two values k1 and k2 (where k1 < k2) and a root pointer to a Binary
	 * Search Tree. Find all the keys of tree in range k1 to k2. i.e. print all
	 * x such that k1<=x<=k2 and x is a key of given BST. Return all the keys in
	 * ascending order.
	 * 
	 * Example
	 * If k1 = 10 and k2 = 22, then your function should return [12, 20, 22].
	 * 
	 *     20
	 *    /  \
	 *   8   22
	 *  / \
	 * 4   12
	 * 
	 */

	/**
	 * @param root
	 *            : The root of the binary search tree.
	 * @param k1
	 *            and k2: range k1 to k2.
	 * @return: Return all keys that k1<=key<=k2 in ascending order.
	 */
	public List<Integer> searchRange(TreeNode root, int k1, int k2) {
		List<Integer> result = new ArrayList<>();
		// check
		if (null == root || k1 > k2) {
			return result;
		}

		// main
		searchRange(root, Integer.MIN_VALUE, Integer.MAX_VALUE, k1, k2, result);

		return result;
	}

	private void searchRange(TreeNode root, int min, int max, int k1, int k2,
			List<Integer> result) {

		if (min <= k2 && root.val >= k1 && null != root.left) {
			searchRange(root.left, min, root.val, k1, k2, result);
		}

		if (root.val >= k1 && root.val <= k2) {
			result.add(root.val);
		}

		if (root.val <= k2 && max >= k1 && null != root.right) {
			searchRange(root.right, root.val, max, k1, k2, result);
		}

	}
	
	
   public List<Integer> searchRange_2(TreeNode root, int k1, int k2) {
        List<Integer> result = new ArrayList<>();
        // check
        if (null == root || k1 > k2) {
            return result;
        }

        // main
        if(root.val <= k1){
            result.addAll(searchRange_2(root.right, k1, k2));
        }
        
        if(root.val >= k2){
            result.addAll(searchRange_2(root.left, k1, k2));
        }

        if( root.val >= k1 && root.val <= k2 ){
            result.add(root.val);
        }
        
        return result;
    }
	   
	   
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
