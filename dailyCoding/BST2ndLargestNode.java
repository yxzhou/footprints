package fgafa.dailyCoding;

/**
 *
 * Given the root to a binary search tree, find the second largest node in the tree.
 *
 * tags: dropbox
 *
 */

public class BST2ndLargestNode {
    class Node<V>{
        V value;
        Node left;
        Node right;
    }

    public Node secondLargest(Node root){
        Node curr = root;
        Node secondLargest = null;
        while(null != curr){
            if(null != curr.right){
                secondLargest = curr;
                curr = curr.right;
            }else if(null != curr.left){
                curr = curr.left;
                secondLargest = curr;
            }else{
                break;
            }
        }

        return secondLargest;
    }
}
