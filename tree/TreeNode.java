package tree;


/*Definition for binary tree*/
public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int x) {
        val = x;
    }

    public TreeNode(int key, TreeNode left, TreeNode right) {
        this.val = key;
        this.left = left;
        this.right = right;
    }

    public String toString() {
        return String.valueOf(this.val);
    }
}
