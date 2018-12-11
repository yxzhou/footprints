package fgafa.dailyCoding.tree;


import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * Invert a binary tree.

 For example, given the following tree:

       a
      / \
     b   c
    / \  /
   d   e f

 should become:

     a
    / \
   c   b
   \  / \
   f e  d

 *
 * Tags: Goo
 *
 */


public class InvertBinaryTree {

    class Node<V>{
        V value;
        Node left;
        Node right;
    }

    public void invertLevel(Node root){
        if(null == root){
            return ;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while( !queue.isEmpty()){
            Node curr = queue.poll();

            Node left = curr.left;
            if(null != left){
                queue.add(left);
            }

            Node right = curr.right;
            if(null != right){
                queue.add(right);
            }

            curr.left = right;
            curr.right = left;
        }

    }

}
