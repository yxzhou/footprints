package tree;


/**
 * _https://www.lintcode.com/problem/595
 * 
 * Given a binary tree, find the length of the longest consecutive sequence path.
    The path refers to any sequence of nodes from some starting node to any node
    in the tree along the parent-child connections. The longest consecutive path
    need to be from parent to child (cannot be the reverse).
    For example,
       1
        \
         3
        / \
       2   4
            \
             5
    Longest consecutive sequence path is 3-4-5, so return 3.
       2
        \
         3
        / 
       2    
      / 
     1
    Longest consecutive sequence path is 2-3,not3-2-1, so return 2.
 *
 */

public class LongestConsecutiveSequence {

    int max = 0;
        
    /**
     * @param root: the root of binary tree
     * @return: the length of the longest consecutive sequence path
     */
    public int longestConsecutive(TreeNode root) {
        helper(root, 0, 0);

        return max;
    }

    private void helper(TreeNode node, int target, int path){
        if(node == null){
            return;
        }

        path = (node.val == target ? path + 1 : 1); 
        max = Math.max(max, path);
        
        target = node.val + 1;
        helper(node.right, target, path);
        helper(node.left, target, path);
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
