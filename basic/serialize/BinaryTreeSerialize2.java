package basic.serialize;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

 Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.

 Example:

 You may serialize the following tree:

       1
      / \
     2   3
        / \
       4   5

 as "[1,2,3,null,null,4,5]"
 Clarification: The above format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.

 Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.
 *
 */

public class BinaryTreeSerialize2 {

    /**
     * Definition for a binary tree node.
     */
     public class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode(int x) { val = x; }
     }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder result = new StringBuilder();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        TreeNode top;
        while(!queue.isEmpty()){
            top = queue.poll();

            if(top == null){
                result.append("n,");
                continue;
            }

            result.append(top.val).append(',');

            queue.add(top.left);
            queue.add(top.right);
        }

        return result.deleteCharAt(result.length() - 1).toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] tokens = data.split(",");

        TreeNode root = tokens[0].equals("n") ? null : new TreeNode(Integer.parseInt(tokens[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        TreeNode top;

        for(int i = 1, limit = tokens.length - 1; !queue.isEmpty() && i < limit; ){
            top = queue.poll();

            if(top == null){
                continue;
            }

            top.left = tokens[i].equals("n") ? null : new TreeNode(Integer.parseInt(tokens[i]));
            i++;
            top.right = tokens[i].equals("n") ? null : new TreeNode(Integer.parseInt(tokens[i]));
            i++;

            queue.add(top.left);
            queue.add(top.right);
        }

        return root;
    }

    public static void main(String[] args){

        BinaryTreeSerialize2 sv = new BinaryTreeSerialize2();

        String[] inputs = {"1,2,3,n,n,4,5,n,n,n,n"};

        for(String input : inputs){
            TreeNode root = sv.deserialize(input);
            String result = sv.serialize(root);

            if(!input.equals(result)){
                System.out.println(input + "\n" + result);
            }
        }

    }



}
