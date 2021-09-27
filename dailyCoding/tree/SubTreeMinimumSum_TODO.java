package dailyCoding.tree;

/**
 *
 * Given a binary tree, return the level of the tree with minimum sum.
 *
 * Tags: facebook
 *
 */

public class SubTreeMinimumSum_TODO {

    class Node<Integer>{
        Integer value;
        Node<Integer> left;
        Node<Integer> right;

        int sum;
        int level;

        int minSum;
        int minLevel;
    }

    public int miminumSum(Node root){
        if(null == root){
            return 0;
        }

        root.level = 1;
        root.sum += (Integer)root.value;
        root.minSum = root.sum;
        root.minLevel = root.level;

        if(root.left != null){
            miminumSum(root.left);

            root.level = Math.max(root.level, root.left.level + 1);
            root.sum += root.left.sum;
        }

        if(root.right != null){
            miminumSum(root.right);

            root.level = Math.max(root.level, root.right.level + 1);
            root.sum += root.right.sum;
        }

        //todo
        return -1;
    }
}
