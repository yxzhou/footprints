package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import util.Misc;

/**
 * Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.
 *
 * For example:
 * Given the following binary tree,
 *    1            <---
 *  /   \
 * 2     3         <---
 *  \     \
 *   5     4       <---
 * You should return [1, 3, 4].
 */

public class RightSideView {

    /**
     *  level order
     *  
     *  Time O(n)  Space complexity O(n) 
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if(null == root){
            return ret;
        }
        
        ret.add(root.val);
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        int count=0;
        int size = 1;
        TreeNode node;
        while(count < size){
            node = queue.pop();
            if(null != node.left){
                queue.add(node.left);
            }
            if(null != node.right){
                queue.add(node.right);
            }
            
            if(++count == size && !queue.isEmpty()){
                count = 0;
                size = queue.size();
                
                ret.add(queue.getLast().val);
            }
        }
        
        return ret;
    }
    
    /**
     *  level order
     *  
     *  Time O(n)  Space complexity O(1) 
     */
    public List<Integer> rightSideView_n(TreeNode root) {
        List<Integer> ret = new ArrayList<>();

        helper(root, 0, ret);
        
        return ret;
    }
    
    private void helper(TreeNode node, int level, List<Integer> ret){
        if(null == node){
            return;
        }
        
        if(ret.size() <= level){
            ret.add(node.val);
        }
        
        helper(node.right, level + 1, ret);
        helper(node.left, level + 1, ret);
    }
    
    public static void main(String[] args) {
        RightSideView sv = new RightSideView();
        
        System.out.println("\n===create a binary search tree===");

        List<TreeNode> roots = BinaryTree.initBTs();
        
        Print print = new Print();
        
        for(TreeNode root : roots){
            print.printTreeShape(root);
            
            System.out.println(" right side view: ");
            Misc.printArrayList_Integer(sv.rightSideView_n(root));
        }

    }
}
