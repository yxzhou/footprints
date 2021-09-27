package tree;


/**
 * 
 * Given a binary tree, find the largest subtree which is a Binary Search Tree (BST), 
 * where largest means subtree with largest number of nodes in it.
    
    Note:
    A subtree must include all of its descendants.
    Here's an example:
        10
        / \
       5  15
      / \   \ 
     1   8   7
    The Largest BST Subtree in this case is the highlighted one. 
    The return value is the subtree's size, which is 3.
    
    Hint:
    You can recursively use algorithm similar to 98. Validate Binary Search Tree at each node of the tree, 
    which will result in O(nlogn) time complexity.
    
    Follow up:
    Can you figure out ways to solve it with O(n) time complexity?
 *
 */

public class LargestBSTII {

    public int largestBSTSubtree(TreeNode root) {
       //check
        if(null == root){
            return 0;
        }
        
       Node node = largestBSTSub_n_help(root);
       return node.max;
        
    }
    
    private Node largestBSTSub_n_help(TreeNode node){

        
        Node left = null;
        if(null == node.left){
            left = Node.Node_NULL;
        }else{
            left = largestBSTSub_n_help(node.left);
        }
        
        Node right = null;
        if(null == node.right){
            right = Node.Node_NULL;
        }else{
            right = largestBSTSub_n_help(node.right);
        }
        
        Node curr = new Node(0, false, 0, 0);
        curr.max = Math.max(left.max, right.max);
        
        if( (left.isRST &&  left.rightLimit < node.val) && 
           (right.isRST && right.leftLimit > node.val)){
            curr.count = 1 + left.count + right.count;
            curr.isRST = true;
            curr.leftLimit = Math.min(left.leftLimit, node.val);
            curr.rightLimit = Math.max(node.val, right.rightLimit);
            
            if(curr.count > curr.max){
                curr.max = curr.count;
            }
        }
        
        return curr;
    }
    
    static class Node{
        int count;
        boolean isRST = false;
        
        int leftLimit = Integer.MAX_VALUE;
        int rightLimit = Integer.MIN_VALUE;
        
        int max = 0;
        
        Node(int num, boolean isBST, int leftLimit, int rightLimit){
            this.count = num;
            this.isRST = isBST;
            this.leftLimit = leftLimit;
            this.rightLimit = rightLimit;
        }
        
        final static Node Node_NULL = new Node(0, true, Integer.MAX_VALUE, Integer.MIN_VALUE);
    }
    
}
