package tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * Given a complete binary tree, count the number of nodes.
 * 
 * Definition of a complete binary tree from Wikipedia: 
 * In a complete binary tree every level, except possibly the last, is completely
 * filled, and all nodes in the last level are as far left as possible. It can 
 * have between 1 and 2h nodes inclusive at the last level h.
 *
 */
public class CTCountNodes {

	/*Time: O(n)*/
	public int countNodes(TreeNode root) {
		int result = 0;
		if(null == root){
			return result;
		}
		
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		TreeNode curr;
		while(!queue.isEmpty()){
			curr = queue.poll();
			result ++;
			
			if(null != curr.left){
				queue.offer(curr.left);
			}
			if(null != curr.right){
				queue.offer(curr.right);
			}
		}
		return result;
	}
	
	/*Time: O(logn*logn)*/
	public int countNodes_n(TreeNode root) {
		if (null == root) {
			return 0;
		}
		int leftHeight = getLeftMaxHeight(root);
		int rightHeight = getRightMaxHeight(root);
		if (leftHeight == rightHeight) {
			return (2 << (leftHeight - 1)) - 1;
		} else {
			return countNodes(root.left) + countNodes(root.right) + 1;
		}
	}

	private int getLeftMaxHeight(TreeNode root) {
		int count = 0;
		while (null != root) {
			count++;
			root = root.left;
		}
		return count;
	}

	private int getRightMaxHeight(TreeNode root) {
		int count = 0;
		while (null != root) {
			count++;
			root = root.right;
		}
		return count;
	}
}
