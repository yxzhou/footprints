package fgafa.dailyCoding;

/**
 *
 * A unival tree (which stands for "universal value") is a tree where all nodes under it have the same value.
 *
 * Given the root to a binary tree, count the number of unival subtrees.
 *
 * For example, the following tree has 5 unival subtrees:
 *
 *       0
 *      / \
 *     1   0
 *    / \
 *   1   0
 *  / \
 * 1   1
 *
 *  Tags:  Google,  Tree
 */
public class UnivalTree<V> {
    class TreeNode<V>{
        V value;
        TreeNode left;
        TreeNode right;
    }

    private class Wrapper{
        int count = 0;
    }

    public int countUnivalTree(TreeNode root){
        Wrapper wrapper = new Wrapper();
        isUnivalTree(root, wrapper);

        return wrapper.count;
    }

    /**
     *
     * @param node
     * @return the same value if it's univalTree, null if it's not univalTree
     */
    private boolean isUnivalTree(TreeNode<V> node, Wrapper wrapper){
        if(node == null){
            return true;
        }

        V result = (V)node.value;

        if(!isUnivalTree(node.left, wrapper) || !isUnivalTree(node.right, wrapper)){
            return false;
        }

        if( (node.left != null && !node.left.value.equals(result)) ||
                (node.right != null && !node.right.value.equals(result))){
            return false;
        }

        wrapper.count++;
        return true;
    }


    public static void main(String[] args){


    }
}
