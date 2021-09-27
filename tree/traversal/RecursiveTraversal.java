package tree.traversal;

import tree.TreeNode;

public class RecursiveTraversal {

    public static void inorder_R(TreeNode p) {
        if (p != null) {
            inorder_R(p.left);
            print(p);
            inorder_R(p.right);
        }
    }

    public static void preorder_R(TreeNode p) {
        if (p != null) {
            print(p);
            preorder_R(p.left);
            preorder_R(p.right);
        }
    }

    public static void postorder_R(TreeNode p) {
        if (p != null) {
            postorder_R(p.left);
            postorder_R(p.right);
            print(p);
        }
    }

    static void print(TreeNode p) {
        System.out.print(p.val + " ");
    }
}
