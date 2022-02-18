package basic.serialize;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

import tree.TreeNode;



/**
 * 
 * Design an algorithm and write code to serialize and deserialize a binary tree. Writing the tree to a file is called 'serialization' and reading back from the file to reconstruct the exact same binary tree is 'deserialization'.

There is no limit of how you deserialize or serialize a binary tree, you only need to make sure you can serialize a binary tree to a string and deserialize this string to the original structure.

Example
An example of testdata: Binary tree {3,9,20,#,#,15,7}, denote the following structure:

  3
 / \
9  20
  /  \
 15   7
Our data serialization use bfs traversal. This is just for when you got wrong answer and want to debug the input.

You can use other method to do serializaiton and deserialization.
 *
 */

public class BinaryTreeSerialize {
    /**
     * preOrder
     * 
     * This method will be invoked first, you should design your own algorithm to serialize a binary tree which denote
     * by a root node to a string which can be easily deserialized by your own "deserialize" method later.
     */

    public String serialize(TreeNode root) {
        if (null == root) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        //preOrder traversal
        TreeNode p = root;
        Stack<TreeNode> stack = new Stack<TreeNode>();

        while (null != p || !stack.isEmpty()) {
            sb.append(',');
            if (null == p) {
                sb.append('#');
            } else {
                sb.append(p.val);
            }

            if (null != p) {
                stack.push(p.right);
                p = p.left;
            } else {
                p = stack.pop();
            }
        }

        return sb.substring(1);
    }

    /**
     * preOrder traversal
     * 
     * This method will be invoked second, the argument data is what exactly you serialized at method "serialize", that
     * means the data is not given by system, it's given by your own serialize method. So the format of data is designed
     * by yourself, and deserialize it here as you serialize it in "serialize" method.
     */
    public TreeNode deserialize(String data) {
        TreeNode root = null;

        if (null == data || 0 == data.length()) {
            return root;
        }

        StringTokenizer tokens = new StringTokenizer(data, ",");

        return deserialize(tokens);
    }

    private TreeNode deserialize(StringTokenizer tokens) {
        TreeNode node = null;

        if (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            if (token.equals("#")) {
                return null;
            }

            node = new TreeNode(Integer.valueOf(token));
            node.left = deserialize(tokens);
            node.right = deserialize(tokens);
        }

        return node;
    }

    // Encodes a tree to a single string.
    //level order
    public String serialize_2(TreeNode root) {
        StringBuilder result = new StringBuilder();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        TreeNode node;
        while (!queue.isEmpty()) {
            node = queue.poll();

            result.append(',');
            if (null == node) {
                result.append('#');
            } else {
                result.append(node.val);

                queue.add(node.left);
                queue.add(node.right);
            }
        }

        return result.toString().substring(1);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize_2(String data) {
        if (null == data || 0 == data.length()) {
            return null;
        }

        String[] tokens = data.split(",");
        int i = 0;
        TreeNode root = deserializeHelper_2(tokens[i++]);

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode parent;
        while (i < tokens.length - 1 && !queue.isEmpty()) {
            parent = queue.poll();

            parent.left = deserializeHelper_2(tokens[i++]);
            parent.right = deserializeHelper_2(tokens[i++]);

            if (null != parent.left) {
                queue.add(parent.left);
            }
            if (null != parent.right) {
                queue.add(parent.right);
            }
        }

        return root;
    }

    public TreeNode deserializeHelper_2(String token) {
        TreeNode node = null;

        if (null != token && !token.equals("#")) {
            return new TreeNode(Integer.valueOf(token));

        }

        return node;
    }
    
    
    /*
     * using pre-order traversal. use ‘#’ as the sentinel 
     * 
     * For example, 
     * 
     *        30 
     *      /   \ 
     *     2     50
     *    / \     \ 
     *   3   4     6 
     * 
     * 
     * serialization as: 30 2 3 # # 4 # # 50 # 6 # #
     */
    public StringBuffer writeBinaryTree(TreeNode root) {
        StringBuffer ret = new StringBuffer();
        if (root == null) {
            ret.append("# ");
        } else {
            ret.append(root.val);
            ret.append(" ");
            ret.append(writeBinaryTree(root.left));
            ret.append(writeBinaryTree(root.right));
        }
        return ret;
    }


    
}
