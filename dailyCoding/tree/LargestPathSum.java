package dailyCoding.tree;

/**
 *
 * Given a binary tree of integers, find the maximum path sum between two nodes. The path must go through at least one node, and does not need to go through the root.
 *
 * tags: goo
 *
 */

public class LargestPathSum {

    class Node{
        int value;
        Node left;
        Node right;

        int largestPathSumWithThis = value;
        int largestPathSumWithoutThis = 0;
    }

    public int largestPathSum(Node root){

        if(null == root){
            return 0;
        }

        //init
        //root.largestPathSumWithThis = root.value;
        //root.largestPathSumWithoutThis = 0;

        if(root.left != null){
            largestPathSum(root.left);

            int max = Math.max(root.left.largestPathSumWithoutThis, root.left.largestPathSumWithThis);
            root.largestPathSumWithoutThis = Math.max(root.largestPathSumWithoutThis, max);
            root.largestPathSumWithThis += root.left.largestPathSumWithThis;
        }

        if(root.right != null){
            largestPathSum(root.right);

            int max = Math.max(root.right.largestPathSumWithoutThis, root.right.largestPathSumWithThis);
            root.largestPathSumWithoutThis = Math.max(root.largestPathSumWithoutThis, max);
            root.largestPathSumWithThis += root.right.largestPathSumWithThis;
        }

        return Math.max(root.largestPathSumWithoutThis, root.largestPathSumWithThis);
    }


}
