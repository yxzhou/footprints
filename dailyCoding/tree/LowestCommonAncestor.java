package dailyCoding.tree;

/**
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree. Assume that each node in the tree also has a pointer to its parent.
 *
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes v and w as the lowest node in T that has both v and w as descendants (where we allow a node to be a descendant of itself).”
 *
 * Tags: twitter
 *
 */

public class LowestCommonAncestor {

    class Node<V>{
        V value;

        Node<V> left;
        Node<V> right;
     }


     public Node LCA(Node root, Node node1, Node node2){
        if(root == null){
            return null;
        }

        if(root == node1 || root == node2){
            return root;
        }

         Node leftLCA = LCA(root.left, node1, node2);
         Node rightLCA = LCA(root.right, node1, node2);

         if(leftLCA != null && rightLCA != null){
             return root;
         }else if(leftLCA != null){
             return leftLCA;
         }else{
             return rightLCA;
         }
     }

}
