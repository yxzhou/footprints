package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * 
 * Two elements of a binary search tree (BST) are swapped by mistake.
 * 
 * Recover the tree without changing its structure.
 * 
 * Note:
 * A solution using O(n) space is pretty straight forward. Could you devise a constant space solution?
 * 
 * @author yxzhou
 *
 */

public class BSTRecover
{
  
  /*
   * the in-order travel of a BST is in ordered. So when we travel the "bad" BST,  we will found out of order issue
   *  
   * Define badNode as the first node that break the order.  
   * 1) in-order travel the tree, find out the badNode that value is not small than the Curr,  
   * 2) continue in-order travel the tree, find out the node Curr that is big than the badNode, and find out preCurr  
   * swap the value of badNode and preCurr  
   * 3) There are 2 special cases, one is the first node's value is MIN_VALUE, the other is bad #5
   * 
   * Right One:  1-2-3-4-5-6-7,  get with in-order travel
   *                             badNode.val < curr.val                   
   * case #1  :  2-1-3-4-5-6-7,  set 2, find 3, preCurr=1, swap(2,1)     
   * case #2  :  1-3-2-4-5-6-7,  set 3, find 4, preCurr=2, swap(3,2)     
   * case #3  :  3-2-1-4-5-6-7,  set 3, find 4, preCurr=1, swap(3,1)     
   * case #4  :  4-2-3-1-5-6-7,  set 4, find 5, preCurr=1, swap(4,1)     
   * case #5  :  1-7-3-4-5-6-2,  set 7, end,    preCurr=2, swap(7,2)     
   * 
   * Iterate InOrder with stack,  Time O(n)  Space O(n)
   */
    public void recoverTree_inOrder(TreeNode root) {
        //check
        if (root == null) {
            return;
        }

        TreeNode curr = root, preCurr = null, badNode = new TreeNode(Integer.MIN_VALUE);
        Stack<TreeNode> stack = new Stack<TreeNode>();
        boolean flag = true;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.add(curr);
                curr = curr.left;
            }

            if (!stack.isEmpty()) {
                curr = stack.pop();
                //BinaryTree.visit(curr); -start-
                if (badNode.val <= curr.val) { // the < is enough for most of cases, the = is for the first node is MIN_VALUE
                    if (flag) {
                        badNode = curr;
                    } else {
                        swap(badNode, preCurr);
                        return;           //ha, sometimes it can finish without travel the whole tree.
                    }
                } else {
                    flag = false;
                }

                preCurr = curr;
                /* -end- */
                curr = curr.right;
            }
        }

        swap(badNode, preCurr);
    }

    private void swap(TreeNode node1, TreeNode node2) {
        int tmp = node1.val;
        node1.val = node2.val;
        node2.val = tmp;
    }
  
  /**
   * InOrder with two Node (Threaded Binary Tree),  Time O(n)  Space O(1)
   * 
   * Right One:  1-2-3-4-5-6-7,  get with in-order travel
   *                             preCurr.val > curr.val
   * case #1  :  2-1-3-4-5-6-7,  find (2, 1), find none, swap(2,1) 
   * case #2  :  1-3-2-4-5-6-7,  find (3, 2), find none, swap(3,2) 
   * case #3  :  3-2-1-4-5-6-7,  find (3, 2), find (2,1), curr=1, swap(3,1)
   * case #4  :  4-2-3-1-5-6-7,  find (4, 2), find (3,1), curr=1, swap(4,1) 
   * case #5  :  1-7-3-4-5-6-2,  find (7, 3), find (6,2), curr=2, swap(7,2) 
   * 
   * iterate InOrder with 2 nodes, (Threaded BT),  Time O(n)  Space O(1)
   */
    public void recoverTree_inOrder_X(TreeNode root) {
        TreeNode badNode1 = null, badNode2 = null;
        TreeNode curr = root, threaded = null, preCurr = null;
        boolean isFound = false;
        while (curr != null) {
            if (curr.left == null) {
                // BinaryTree.visit(curr); -start-
                if (preCurr != null && preCurr.val > curr.val) {
                    if (!isFound) {
                        badNode1 = preCurr;
                        isFound = true;
                    }
                    badNode2 = curr;  //here it cann't be return, because the threaded have to removed. 
                }
                preCurr = curr;
                /* -end- */
                curr = curr.right;
            } else {
                /* Find the inorder predecessor of current */
                threaded = curr.left;
                while (threaded.right != null && threaded.right != curr) {
                    threaded = threaded.right;
                }

                /* Make current as right child of its inorder predecessor */
                if (threaded.right == null) {
                    threaded.right = curr;
                    curr = curr.left;
                } else {
                    threaded.right = null;
                    // BinaryTree.visit(curr); -start-
                    if (preCurr.val > curr.val) {
                        if (!isFound) {
                            badNode1 = preCurr;
                            isFound = true;
                        }
                        badNode2 = curr; //here it cann't be return, because the threaded have to removed.
                    }
                    preCurr = curr;
                    /* -end- */
                    curr = curr.right;
                }
            }
        }

        if (badNode1 != null && badNode2 != null) {
            swap(badNode1, badNode2);
        }
    }

   /**
    * iterate InOrder with 2 nodes 
    * 
    * Right One:  1-2-3-4-5-6-7,  get with in-order travel
    *                             preCurr.val > curr.val
    * case #1  :  2-1-3-4-5-6-7,  find (2, 1), p1 = 2, p2 = 1, no one smaller than 1, swap(2,1) 
    * case #2  :  3-2-1-4-5-6-7,  find (3, 2), p1 = 3, p2 = 2, find 2 > 1, p2=1, swap(3,1)
    * case #4  :  4-2-3-1-5-6-7,  find (4, 2), p1 = 4, p2 = 2, find 2 > 1, p2=1, swap(4,1) 
    * case #5  :  1-7-3-4-5-6-2,  find (7, 3), p1 = 2, p2 = 3, find 3 > 2, p2=2, swap(7,2) 
    * 
    * Time O(n)  Space O(n)
    */
    
    public TreeNode recoverTree_inOrder_n(TreeNode root) {
        TreeNode p1 = null; // problem node #1
        TreeNode p2 = null; // problem node #2

        TreeNode pre = null;
        TreeNode curr = root;
        Stack<TreeNode> stack = new Stack<>();
        while(curr != null || !stack.isEmpty()){
            if(curr == null){
                curr = stack.pop();
                if(p1 == null && pre != null && pre.val > curr.val){
                    p1 = pre;
                    p2 = curr;
                }else if( p2 != null && p2.val > curr.val) {
                    p2 = curr;
                }
                
                pre = curr;
                curr = curr.right;

            }else{
                stack.add(curr);
                curr = curr.left;
            }
        }

        if(p1 != null && p2 != null){
            int tmp = p1.val;
            p1.val = p2.val;
            p2.val = tmp;
        }

        return root;
    }
        
    /*
   * recursive InOrder,  Time O(n)  Space O(1)
   * 
     */
    public void recoverTree_inOrder_R(TreeNode root) {
        NodeList list = new NodeList();

        recoverTree_inOrder_R(root, list);

        if (list.badNode1 != null && list.badNode2 != null) {
            swap(list.badNode1, list.badNode2);
        }
    }

    private void recoverTree_inOrder_R(TreeNode root, NodeList list) {
        if (root == null) {
            return;
        }

        recoverTree_inOrder_R(root.left, list);

        if (list.preCurr != null && list.preCurr.val > root.val) {
            if (list.badNode1 == null) {
                list.badNode1 = list.preCurr;
            }

            list.badNode2 = root;
        }

        list.preCurr = root;

        recoverTree_inOrder_R(root.right, list);
    }

    class NodeList {

        TreeNode badNode1;
        TreeNode badNode2;
        TreeNode preCurr;
    }

   
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        /*
   * bad #1  :  2-1-3-4-5-6-7
   * bad #2  :  1-3-2-4-5-6-7
   * bad #3  :  3-2-1-4-5-6-7
   * bad #4  :  4-2-3-1-5-6-7
   * bad #5  :  1-7-3-4-5-6-2
         */
        int[][] nodes = {{2, 1, 3}, {3, 1, 2}, {3, 0, 1, 2}, {4, 1, 6, 2, 3, 5, 7}, {4, 3, 6, 1, 2, 5, 7}, {4, 2, 6, 3, 1, 5, 7}, {1, 2, 6, 4, 3, 5, 7}, {4, 7, 6, 1, 3, 5, 2}};

        BSTRecover bst = new BSTRecover();
        for (int i = 0; i < nodes.length; i++) {

            TreeNode root = BinarySearchTree.buildBST_levelorder(nodes[i]);
            printBT_inorder_R(root);

            System.out.print("----");

            //bst.recoverTree_inorder(root);
            //bst.recoverTree_inorder2(root);
            //bst.recoverTree_inorder_X(root);
            bst.recoverTree_inOrder_R(root);

            printBT_inorder_R(root);

            System.out.printf("%n");
        }
    }

    /*
   * inorder with recursive
     */
    private static void printBT_inorder_R(TreeNode p) {
        if (p != null) {
            printBT_inorder_R(p.left);
            visit(p);
            printBT_inorder_R(p.right);
        } else {
            System.out.print("# ");
        }
    }

    /**
     * print a Node
     *
     * @param p
     */
    static void visit(TreeNode p) {
        System.out.print(p.val + " ");
    }
}
