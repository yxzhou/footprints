package tree.traversal;

import tree.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class IterativeTraversal2 {

    /**
     * inorder with stack and a Node
     *
     * 1 If node is not null, put node and node left child in stack
     * 2 pop stack, print it, set right node  ---
     *
     *
     *  Time O(n)  Space O(n)
     */
    public static List<Integer> inorder_iterative_2(TreeNode p) {
        List<Integer> result = new ArrayList<Integer>();

        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode node = p;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            if (!stack.isEmpty()) {
                node = stack.pop();
                result.add(node.val);
                node = node.right;
            }
        }

        return result;
    }

    /**
     * inorder with 2 Nodes, Threaded binary tree.
     *
     * 1. Initialize current as root
     * 2. While current is not NULL
     *    If current does not have left child
     *       a) Print current’s data
     *       b) Go to the right, i.e., current = current->right
     *    Else
     *       a) Make current as right child of the rightmost node in current's left subtree
     *       b) Go to this left child, i.e., current = current->left
     *
     *  Time O(n)  Space O(1)
     *
     */
    public List<Integer> inorder_iterative_3(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();

        TreeNode curr = root;
        TreeNode pre;
        while (curr != null) {
            if (curr.left == null) {
                result.add(curr.val);
                curr = curr.right;
            } else {
                /* Find the inorder predecessor of current */
                pre = curr.left;
                while (pre.right != null && pre.right != curr) {
                    pre = pre.right;
                }

                /*Make current as right child of its inorder predecessor*/
                if (pre.right == null) {
                    pre.right = curr;
                    curr = curr.left;
                } else {
                    pre.right = null;
                    result.add(curr.val);
                    curr = curr.right;
                }
            }
        }

        return result;
    }

    /**
     * inorder with stack and a Node
     */
    public static void  inorder_iterative_n(TreeNode root){
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        while(curr != null || !stack.isEmpty()){
            if(null == curr){
                curr = stack.pop();
                print(curr);
                curr = curr.right;
            }else{
                stack.add(curr);
                curr = curr.left;
            }
        }
    }

    public static void  preorder_iterative(TreeNode root){
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        while(curr != null || !stack.isEmpty()){
            if(null == curr){
                curr = stack.pop();
            }else{
                print(curr);

                stack.add(curr.right);
                curr = curr.left;
            }
        }
    }

    /**
     * postorder with one Stack and 2 node
     */
    public static void postorder_iterative(TreeNode p) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode node = p, prev = p;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            if (!stack.isEmpty()) {
                node = stack.peek().right;
                if (node == null || node == prev) {
                    node = stack.pop();
                    print(node);
                    prev = node;
                    node = null;
                }
            }

        }
    }

    private static void print(TreeNode p) {
        System.out.print(p.val + " ");
    }
}
