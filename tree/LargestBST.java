package tree;

import java.util.HashMap;
import java.util.Iterator;

import tree.traversal.RecursiveTraversal;


/**
 * 
 * Given a binary tree, find the largest Binary Search Tree (BST), where largest means BST with largest number of nodes in it. 
 * Largest BST,  ( when the largest BST may or may not include all of its descendants.)
 * Largest BST subtree, 
 * 
 * e.g: the binary tree is:
 * 
 *                ___________________15___________________
 *               /                                        \
 *   ___________10___________                             20
 *  /                        \
 *  5                   _____7____
 *                     /          \
 *                   __2__       __5
 *                  /     \     /
 *                 0      8    3 
 *
 * The largest BST (may or may not include all of its descendants) is:
 * 
 *         ____15____
 *        /          \
 *      _10          20
 *     /
 *     5
 * 
 * The largest BST subtree (must include all of its descendants) is:
 *
 *      __2_
 *     /    \
 *     0     8 
 *
 */

public class LargestBST {

    public HashMap<TreeNode, Integer> htC;  // record(Node, BST sub tree node amount )
    public HashMap<TreeNode, Integer> htMax; // record(Node, the max of this sub tree)
    public HashMap<TreeNode, Integer> htMin; // record(Node, the min of this sub tree)

    /**
     * Given a binary tree, find the largest subtree which is a Binary Search Tree (BST), where largest means subtree
     * with largest number of nodes in it.
     *
     * bottom-up approach Time O(n), Space O(n)
     *
     * @param root
     * @return the root of the largest BST subtree in a binary tree
     */
    public TreeNode largestBSTSubTree(TreeNode root) {
        //check
        if (root == null) {
            return null;
        }

        //main
        htC = new HashMap<>(); 
        htMax = new HashMap<>();
        htMin = new HashMap<>();
        TreeNode subRoot = largestBSTSub_R(root);

        //traverse the hashtable,  return one largest BST subtree.
//    int maxHeight = 0;
//    //Node subRoot = null;
//    Iterator iterator = ht.keySet().iterator();  
//
//    while (iterator.hasNext()) {  
//      Node key = (Node)iterator.next();  
//      int height = ht.get(key);
//      
//      if(height > maxHeight){
//        maxHeight = height;
//        subRoot = key;
//      }
//   }  
        return subRoot;
    }

    private TreeNode largestBSTSub_R(TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            saveCount(root, 1);
            saveMaxMin(root, root.val, root.val);
            return root;
        }

        TreeNode left = null;
        int leftH = 0;
        if (root.left != null) {
            left = largestBSTSub_R(root.left);
            leftH = getCount(left);
        }

        TreeNode right = null;
        int rightH = 0;
        if (root.right != null) {
            right = largestBSTSub_R(root.right);
            rightH = getCount(right);
        }

        if ((left == root.left && right == root.right)
                //&& Math.abs(leftH - rightH) <= 1   // AVL 
                && isBST(root, left, right)) {
            saveCount(root, (leftH + rightH + 1));
            return root;
        } else {
            if (leftH > rightH) {
                return left;
            } else {
                return right;
            }
        }
    }

    private boolean isBST(TreeNode p, TreeNode left, TreeNode right) {
        if (p == null) {
            return false;
        }

        int max = p.val;
        int min = max;

        if (left != null) {
            if (min > getMax(left)) {
                min = getMin(left);
            } else {
                return false;
            }
        }
        if (right != null) {
            if (max < getMin(right)) {
                max = getMax(right);
            } else {
                return false;
            }
        }

        saveMaxMin(p, max, min);
        return true;

    }

//  private boolean isBetween(Node p, Node left, Node right){
//    if(p == null) return false;
//      
//    boolean ret = true;
//    if(left != null)
//      ret = ret && Integer.valueOf(p.val) > Integer.valueOf(left.val); 
//    if(right != null)
//      ret = ret && Integer.valueOf(p.val) < Integer.valueOf(right.val); 
//      
//    return ret;
//  }
    private void saveCount(TreeNode p, int count) {
        htC.put(p, count);
    }

    private int getCount(TreeNode p) {
        if (htC.containsKey(p)) {
            return htC.get(p);
        } else {
            return 0;
        }
    }

    private void saveMaxMin(TreeNode p, int max, int min) {
        saveMax(p, max);
        saveMin(p, min);
    }

    private void saveMax(TreeNode p, int max) {
        htMax.put(p, max);
    }

    private int getMax(TreeNode p) {
        if (htMax.containsKey(p)) {
            return htMax.get(p);
        } else {
            return 0;
        }
    }

    private void saveMin(TreeNode p, int min) {
        htMin.put(p, min);
    }

    private int getMin(TreeNode p) {
        if (htMin.containsKey(p)) {
            return htMin.get(p);
        } else {
            return 0;
        }
    }

    /**
     * Given a binary tree, find the largest Binary Search Tree (BST), where largest means BST with largest number of
     * nodes in it. The largest BST may or may not include all of its descendants.
     *
     * bottom-up approach Time O(n), Space O(n)
     *
     * @param root
     * @return the height of the largest BST
     */
    public TreeNode largestBST(TreeNode root) {

        //check
        if (root == null) {
            return null;
        }

        //main
        htC.clear();
        htMax.clear();
        htMin.clear();
        TreeNode subRoot = largestBST_R(root);

        //traverse the hashtable,  return one largest BST subtree.
        int maxHeight = 0;
        //Node subRoot = null;
        for (TreeNode key : htC.keySet()) {
            int height = htC.get(key);

            if (height > maxHeight) {
                maxHeight = height;
                subRoot = key;
            }
        }

        return subRoot;
    }

    private TreeNode largestBST_R(TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            saveCount(root, 1);
            saveMaxMin(root, root.val, root.val);
            return root;
        }

        TreeNode left = null;
        int leftH = 0;
        if (root.left != null) {
            left = largestBST_R(root.left);
            leftH = getCount(left);
        }

        TreeNode right = null;
        int rightH = 0;
        if (root.right != null) {
            right = largestBST_R(root.right);
            rightH = getCount(right);
        }

        if (left == root.left && right == root.right && isBST(root, left, right)) {
            saveCount(root, leftH + rightH + 1);

        } else if (left == root.left && isBST(root, left, null)) {
            saveCount(root, leftH + 1);

        } else if (right == root.right && isBST(root, null, right)) {
            saveCount(root, rightH + 1);

        } else { //  ?? 
            saveCount(root, 1);
            saveMaxMin(root, root.val, root.val);
        }
        return root;
    }

    private void inorderBST_R(TreeNode p) {
        if (p != null) {
            if (p.left != null && p.val > getMax(p.left)) {
                inorderBST_R(p.left);
            }

            BinaryTree.visit(p);

            if (p.right != null && p.val < getMin(p.right)) {
                inorderBST_R(p.right);
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        LargestBST s = new LargestBST();
        RecursiveTraversal recursiveTraversal = new RecursiveTraversal();
                

        //init a binary tree
        BinaryTree tree = new BinaryTree();
        TreeNode n2 = new TreeNode(2, new TreeNode(0), new TreeNode(8));
        TreeNode n5 = new TreeNode(5, new TreeNode(3), null);
        TreeNode n7 = new TreeNode(7, n2, n5);
        TreeNode n10 = new TreeNode(10, new TreeNode(5), n7);
        TreeNode n15 = new TreeNode(15, n10, new TreeNode(20));
        tree.setRoot(n15);

        //print the tree in-order
        System.out.println("\nThe binary tree is (in-order): ");
        recursiveTraversal.inorder_R(tree.root);

        //get largest BST subtree 
        TreeNode subRoot = s.largestBSTSubTree(tree.root);

        System.out.println("\n\nhtC: " + s.htC.toString());
        System.out.println("htMax: " + s.htMax.toString());
        System.out.println("htMin: " + s.htMin.toString());

        System.out.println("\nThe Largest BST subtree is (in-order): ");
        recursiveTraversal.inorder_R(subRoot);

        //get largest BST 
        subRoot = s.largestBST(tree.root);

        System.out.println("\n\nhtC: " + s.htC.toString());
        System.out.println("htMax: " + s.htMax.toString());
        System.out.println("htMin: " + s.htMin.toString());

        System.out.println("\nThe Largest BST is (in-order): ");
        s.inorderBST_R(subRoot);

    }

}
