package dailyCoding.tree;

/**
 *
 * Given the root of a binary tree, return a deepest node. For example, in the following tree, return d.

     a
    / \
   b   c
  /
 d

 *
 *  Tags: goo
 *
 */

public class DeepestNode {

    class Node<V>{
        V value;
        Node left;
        Node right;

        int depth = 0;
    }

    public Node deepestNode(Node root){
        return deepestNode(root, 0);
    }

    private Node deepestNode(Node node, int depth){
        if(null == node ){
            return null;
        }

        node.depth = depth;

        if(null == node.left && null == node.right){
            return node;
        }

        Node left = deepestNode(node.left, depth + 1);
        Node right = deepestNode(node.right, depth + 1);

        if(null == left){
            return right;
        }else if(null == right){
            return left;
        }else{
            return left.depth > right.depth ? left : right;
        }
    }

}
