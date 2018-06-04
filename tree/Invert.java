package fgafa.tree;

import java.util.LinkedList;
import java.util.Queue;

/*
 * Invert a binary tree.
 *
 *     4
 *   /   \
 *  2     7
 * / \   / \
 *1   3 6   9
 * to
 *     4
 *   /   \
 *  7     2
 * / \   / \
 *9   6 3   1
 */
public class Invert {


	public TreeNode invertTree(TreeNode root) {
		if(null != root){
			invertTree_recursive(root);
		}
		
		return root;
	}

	private void invertTree_recursive(TreeNode node) {

		TreeNode right = node.right;
		node.right = node.left;
		node.left = right;

		if(null != node.right){
			invertTree(node.right);
		}
		if(null != node.left){
			invertTree(node.left);
		}
	}
	
	private void invertTree_iterative(TreeNode node){
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(node);
		
		TreeNode curr;
		TreeNode right;
		while(!queue.isEmpty()){
			curr = queue.poll();
			
			//swap
			right = curr.right;
			curr.right = curr.left;
			curr.left = right;
			
			//
			if(null != node.right){
				queue.offer(node.right);
			}
			if(null != node.left){
				queue.offer(node.left);
			}
		}
	}
	
	
    public void invertBinaryTree(TreeNode root) {
        //check
        if (null == root){
            return;
        }

        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        invertBinaryTree(root.left);
        invertBinaryTree(root.right);
    }
    
    public void invertBinaryTree_2(TreeNode root) {
        //check
        if (null == root){
            return;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        TreeNode curr;
        TreeNode tmp;
        while(!queue.isEmpty()){
        	curr = queue.poll();
        	
        	tmp = curr.left;
        	curr.left = curr.right;
        	curr.right = tmp;
        	
        	if(null != curr.left){
        		queue.offer(curr.left);
        	}
        	if(null != curr.right){
        		queue.offer(curr.right);
        	}
        }
    }    
}
