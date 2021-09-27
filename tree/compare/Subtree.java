package tree.compare;

import tree.TreeNode;

import java.util.*;

/**
 * _https://www.lintcode.com/problem/1165/description?_from=ladder&fromId=29
 *
 * Subtree of Another Tree
 *
 * Description
 * Given two non-empty binary trees s and t, check whether tree t has exactly the same structure and node values with a subtree of s. A subtree of s is a tree consists of a node in s and all of this node's descendants. The tree s could also be considered as a subtree of itself.
 *
 * Example 1:
 * Given tree s:
 *
 *      3
 *     / \
 *    4   5
 *   / \
 *  1   2
 * Given tree t:
 *    4
 *   / \
 *  1   2
 * Return true, because t has the same structure and node values with a subtree of s.
 *
 *
 * Example 2:
 * Given tree s:
 *
 *      3
 *     / \
 *    4   5
 *   / \
 *  1   2
 *     /
 *    0
 * Given tree t:
 *    4
 *   / \
 *  1   2
 * Return false.
 *
 * Solution #1, recursive, check every node if it equals to the target tree
 *
 *
 *
 * Solution #2,
 *    find out all subtree's depth
 *    check all subtree which depth equals to the target tree, compare if they are same.
 *
 */

public class Subtree {
    /**
     * s1, check every node if it equals to the target tree
     *
     * @param s: the s' root
     * @param t: the t' root
     * @return: whether tree t has exactly the same structure and node values with a subtree of s
     */
    public boolean isSubtree_1(TreeNode s, TreeNode t) {
        if(s == null) {
            return t == null;
        }

        if(s.val == t.val && equal(s, t)){
            return true;
        }

        return isSubtree_1(s.left, t) || isSubtree_1(s.right, t);
    }

    private boolean equal(TreeNode s, TreeNode t){
        if(s == null || t == null){
            return s == t;
        }

        return s.val == t.val && equal(s.left, t.left) && equal(s.right, t.right);
    }


    /**
     * s2
     */

    public boolean isSubtree_2(TreeNode s, TreeNode t){
        if(s == null){
            return t == null;
        }

        Map<Integer, Integer> sh = new HashMap<>();
        Map<Integer, Integer> th = new HashMap<>();

        getHeight(s, sh);
        getHeight(t, th);

        return isSubtree(s, t, sh, th);
    }

    //return Map<node value,  height >
    private int getHeight(TreeNode n, Map<Integer, Integer> heights){
        if(n == null){
            return 0;
        }

        int h = Math.max( getHeight(n.left, heights), getHeight(n.right, heights) ) + 1;
        heights.put(n.val, h);
        return h;
    }

    private boolean isSubtree(TreeNode s, TreeNode t, Map<Integer, Integer> sh, Map<Integer, Integer> th){
        int diff = sh.get(s.val) - th.get(t.val);

        if(diff < 0){
            return false;
        }
        if(diff == 0){
            return s.val == t.val && isSame(s, t, sh, th);
        }

        return (s.left != null && isSubtree(s.left, t, sh, th)) || (s.right != null && isSubtree(s.right, t, sh, th));
    }

    private boolean isSame(TreeNode s, TreeNode t, Map<Integer, Integer> sh, Map<Integer, Integer> th){
        if(s == null || t == null){
            return s == t;
        }

        return s.val == t.val && sh.get(s.val) == th.get(t.val) && isSame(s.left, t.left, sh, th) && isSame(s.right, t.right, sh, th);
    }

}
