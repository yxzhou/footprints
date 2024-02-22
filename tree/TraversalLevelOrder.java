package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import util.Misc;


public class TraversalLevelOrder {
    
    /*
     * Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
     *  <br>
     * For example:<br>
     * Given binary tree {3,9,20,#,#,15,7},<br>
     *     3          <br>
     *    / \         <br>
     *   9  20        <br>
     *     /  \       <br>
     *    15   7      <br>
     * return its level order traversal as: <br>
     * [  
     *   [3], 
     *   [9,20],
     *   [15,7]
     * ]
     */
    public List<List<Integer>> levelorder_BFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        //check
        if (root == null) {
            return result;
        }

        //init
        Queue<TreeNode> currLevel = new LinkedList<>();
        Queue<TreeNode> nextLevel = new LinkedList<>();
        List<Integer> currList = new ArrayList<>();

        currLevel.add(root);
        TreeNode node;

        //main
        while (!currLevel.isEmpty()) {
            while (!currLevel.isEmpty()) {
                node = currLevel.poll();

                currList.add(node.val);

                if (node.left != null) {
                    nextLevel.add(node.left);
                }
                if (node.right != null) {
                    nextLevel.add(node.right);
                }

            }
            result.add(currList);
            currList = new ArrayList<Integer>();

            Queue<TreeNode> tmp = currLevel;
            currLevel = nextLevel;
            nextLevel = tmp;
//        swap(currLevel, nextLevel);    // it's not work,  pass by value
        }

        return result;
    }

    public List<List<Integer>> levelOrder_BFS_n(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (null == root) {
            return ret;
        }

        ret.add(new ArrayList<>());
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int count = 0, size = 1;
        TreeNode node;
        while (count < size) {
            node = queue.pop();
            ret.get(ret.size() - 1).add(node.val);

            if (null != node.left) {
                queue.add(node.left);
            }
            if (null != node.right) {
                queue.add(node.right);
            }

            if (++count == size && !queue.isEmpty()) {
                ret.add(new ArrayList<Integer>());
                size = queue.size();
                count = 0;
            }
        }

        return ret;
    }
      
    /**
     * @param root: The root of binary tree.
     * @return: Level order a list of lists of integer
     */
    public List<List<Integer>> levelOrder_BFS_n2(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        //check
        if (null == root) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);

        ArrayList<Integer> list;
        TreeNode node;
        while (!queue.isEmpty()) {
            list = new ArrayList<Integer>();
            result.add(list);

            for (int count = queue.size(); count > 0; count--) {
                node = queue.poll();
                list.add(node.val);

                if (null != node.left) {
                    queue.add(node.left);
                }
                if (null != node.right) {
                    queue.add(node.right);
                }
            }
        }

        return result;
    }

    public List<List<Integer>> levelOrder_DFS_n(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if (null == root) {
            return result;
        }

        Stack<MyNode> stack = new Stack<>();
        stack.add(new MyNode(0, root));

        MyNode node;
        TreeNode treeNode;
        while (!stack.isEmpty()) {
            node = stack.pop();
            treeNode = node.treeNode;

            while (node.level >= result.size()) {
                result.add(new ArrayList<Integer>());
            }
            result.get(node.level).add(treeNode.val);

            //put the right before the left, so the left will be pop before the right
            if (null != treeNode.right) {
                stack.push(new MyNode(node.level + 1, treeNode.right));
            }
            if (null != treeNode.left) {
                stack.push(new MyNode(node.level + 1, treeNode.left));
            }
        }

        return result;
    }
      
    class MyNode {

        int level;
        TreeNode treeNode;

        MyNode(int level, TreeNode node) {
            this.level = level;
            this.treeNode = node;
        }
    }
      
      
    /**
     * 
     * Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).
     * 
     * For example:
     * Given binary tree {3,9,20,#,#,15,7},
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * return its zigzag level order traversal as:
     * [
     *   [3],
     *   [20,9],
     *   [15,7]
     * ]
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ret = levelOrder_BFS_n(root);

        for (int i = 0; i < ret.size(); i++) {
            if (1 == (i & 1)) {
                reverse(ret.get(i));
            }
        }

        return ret;
    }

    private void reverse(List<Integer> list) {
        int tmp;
        for (int i = 0, j = list.size() - 1; i < j; i++, j--) {
            //swap
            tmp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, tmp);
        }
    }

    //similar with levelOrder_BFS_n2
    public List<List<Integer>> zigzagLevelOrder_n(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        //check
        if (null == root) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        ArrayList<Integer> list;
        TreeNode node;
        boolean direction = false;
        while (!queue.isEmpty()) {
            list = new ArrayList<>();
            result.add(list);
            direction = !direction;

            for (int count = queue.size(); count > 0; count--) {
                node = queue.poll();
                if (direction) {
                    list.add(node.val);
                } else {
                    list.add(0, node.val);
                }

                if (null != node.left) {
                    queue.add(node.left);
                }
                if (null != node.right) {
                    queue.add(node.right);
                }
            }
        }

        return result;
    }
      
      
    /*
     * Given a binary tree, return the bottom-up level order traversal of its nodes' values. (ie, from left to right, level by level from leaf to root).
     * <br>
     * For example:<br>
     * Given binary tree {3,9,20,#,#,15,7}, <br>
     *     3      <br>
     *    / \     <br>
     *   9  20    <br>    
     *     /  \   <br>
     *    15   7  <br>
     * return its bottom-up level order traversal as:  <br>
     * [
     *   [15,7]
     *   [9,20],
     *   [3],
     * ]
     */
     
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        // check
        if (root == null) {
            return result;
        }

        // init
        Queue<TreeNode> currLevel = new LinkedList<>();
        Queue<TreeNode> nextLevel = new LinkedList<>();
        List<Integer> currList = new ArrayList<>();

        currLevel.add(root);
        TreeNode node = null;

        // main
        while (!currLevel.isEmpty()) {
            while (!currLevel.isEmpty()) {
                node = currLevel.poll();

                currList.add(node.val);

                if (node.left != null) {
                    nextLevel.add(node.left);
                }
                if (node.right != null) {
                    nextLevel.add(node.right);
                }

            }
            result.add(0, currList); // the difference with levelorder_BFS()
            currList = new ArrayList<>();

            Queue<TreeNode> tmp = currLevel;
            currLevel = nextLevel;
            nextLevel = tmp;
            // swap(currLevel, nextLevel); // it's not work, pass by value
        }

        return result;
    }

    public List<List<Integer>> levelOrderBottom_x(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (null == root) {
            return ret;
        }

        ret.add(new ArrayList<>());
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int count = 0, size = 1;
        TreeNode node;
        while (count < size) {
            node = queue.pop();
            ret.get(0).add(node.val);

            if (null != node.left) {
                queue.add(node.left);
            }
            if (null != node.right) {
                queue.add(node.right);
            }

            if (++count == size && !queue.isEmpty()) {
                ret.add(0, new ArrayList<>());
                size = queue.size();
                count = 0;
            }
        }

        return ret;
    }

    // similar with levelOrder_BFS_n2
    public List<List<Integer>> levelOrderBottom_BFS_n2(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        // check
        if (null == root) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        ArrayList<Integer> list;
        TreeNode node;
        while (!queue.isEmpty()) {
            list = new ArrayList<>();
            result.add(0, list);

            for (int count = queue.size(); count > 0; count--) {
                node = queue.poll();
                list.add(node.val);

                if (null != node.left) {
                    queue.add(node.left);
                }
                if (null != node.right) {
                    queue.add(node.right);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {

        List<TreeNode> list = BinaryTree.initBTs();

        // traverse
        TraversalLevelOrder sv = new TraversalLevelOrder();
        Print pnt = new Print();

        TreeNode root;
        for (int i = 0; i < list.size(); i++) {
            root = (TreeNode) list.get(i);
            System.out.print("\n" + i + " ===  The tree === \n");
            pnt.printTreeShape(root);

            System.out.println("\t levelorder_BFS: ");
            Misc.printListList(sv.levelorder_BFS(root));

            System.out.println("\t levelOrderBottom_BFS: ");
            Misc.printListList(sv.levelOrderBottom_BFS_n2(root));

            System.out.println("\t   zigzagLevelOrder: ");
            Misc.printListList(sv.zigzagLevelOrder(root));
        }

    }
}
