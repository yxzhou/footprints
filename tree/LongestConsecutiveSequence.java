package tree;


/**
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

    public int longestConsecutive(TreeNode root) {
        if(null == root){
            return 0;
        }
        
        return longestConsecutive(root, 0, root.val);
    }
    
    private int longestConsecutive(TreeNode node, int preLen, int target){
        if(null == node){
            return preLen;
        }
        
        if(node.val == target){
            preLen++;
        }else{
            preLen = 1;
        }
        
        int left = longestConsecutive(node.left, preLen, node.val + 1);
        int right = longestConsecutive(node.right, preLen, node.val + 1);
        
        return Math.max(preLen, Math.max(left, right));
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
