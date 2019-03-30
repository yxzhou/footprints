package fgafa.tree;

import java.util.ArrayList;

import java.util.Stack;


/*
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 * For example, this binary tree is symmetric:
 *          1
 *        /   \ 
 *       2     2 
 *      / \   / \ 
 *     3   4  4  3 
 *     
 * But the following is not: 
 *           1 
 *         /   \ 
 *        2     2 
 *         \     \ 
 *          3     3
 */

public class SymmetricTree
{

  /*
   * 1) check if root.left and root.right is symmetric 1.1) are both null? or
   * are same value? 1.2) are left.left and right.right, left.right and
   * right.left symmetric? Time O(n), Space O(1)
   */
  public boolean isSymmetric_R(TreeNode root) {
    if (root == null) {
        return true;
    }

    return helper(root.left, root.right);
  }



  private boolean helper(TreeNode tree, TreeNode tree2) {
    if (tree == null || tree2 == null) {
        return tree == tree2;
    }

    if (tree.val != tree2.val) {
        return false;
    }

    return helper(tree.right, tree2.left)
        && helper(tree.left, tree2.right);
  }



  /*
   * in-order recursive travel <br> 1) in-order travel the tree, add the val in
   * ArrayList<String>, add "#" if it's null 2) check if it's symmetric in the
   * list. Time O(n), Space O(n)
   */
  public boolean isSymmetric_inOrder(TreeNode root) {
    if (root == null) {
        return true;
    }

    // main, travel by in-order
    ArrayList<String> inOrder = new ArrayList<String>();
    inOrder_recursive(root, inOrder);

    return isSymmetric(inOrder);
  }

  private void inOrder_recursive(TreeNode node, ArrayList<String> list) {
    if (node.left != null) {
        inOrder_recursive(node.left, list);
    }else {
        list.add("#");
    }

    list.add(String.valueOf(node.val));

    if (node.right != null) {
        inOrder_recursive(node.right, list);
    }else {
        list.add("#");
    }
  }


  private void inOrder_iterative(TreeNode node, ArrayList<String> list) {
    TreeNode curr = node;
    Stack<TreeNode> stack = new Stack<TreeNode>();

    while (curr != null || !stack.isEmpty()) {

      if (curr != null) {
        stack.add(curr);
        curr = curr.left;
        continue;
      }

      if (!stack.isEmpty()) {
        curr = stack.pop();

        if (curr.left == null)
          list.add("#");

        list.add(String.valueOf(curr.val));

        if (curr.right == null)
          list.add("#");

        curr = curr.right;
      }
    }
  }


  /*
   * level-order travel <br> Time O(n) Space O(n) (better than in-order travel)
   */
	public boolean isSymmetric_levelOrder(TreeNode root) {
		if (root == null) {
            return true;
        }

		// main, travel by level-order
		return levelOrder_iterative(root);
	}

	private boolean levelOrder_iterative(TreeNode node) {

		Stack<TreeNode> currLevel = new Stack<TreeNode>();
		currLevel.add(node);
		Stack<TreeNode> nextLevel = new Stack<TreeNode>();

		ArrayList<String> list = new ArrayList<String>();

		TreeNode currNode;
		while (!currLevel.isEmpty()) {
			currNode = currLevel.pop();
			if (currNode.left != null) {
				nextLevel.add(currNode.left);
				list.add(String.valueOf(currNode.left.val));
			} else {
                list.add("#");
            }

			if (currNode.right != null) {
				nextLevel.add(currNode.right);
				list.add(String.valueOf(currNode.right.val));
			} else {
                list.add("#");
            }

			if (currLevel.isEmpty()) {
				if (!isSymmetric(list)) {
                    return false;
                }

				currLevel = nextLevel;
				list = new ArrayList<String>();
				nextLevel = new Stack<TreeNode>();
			}

		}

		return true;
	}

	private boolean isSymmetric(ArrayList<String> list) {
		int i = 0, j = list.size() - 1;
		while (i < j) {
			if (!list.get(i).equals(list.get(j))) {
				return false;
			}
			i++;
			j--;
		}

		return true;
	}

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
