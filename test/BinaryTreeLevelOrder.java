package fgafa.test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeLevelOrder {

    class TreeNode{
        int value;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int v){
            this.value = v;
        }
    }
    
    public List<List<Integer>> levelOrder(TreeNode root){
        List<List<Integer>> result = new LinkedList<>();
        
        if(null == root){
            return result;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        TreeNode curr;
        while(!queue.isEmpty()){
            
            List<Integer> level = new LinkedList<>();
            for(int count = queue.size(); count > 0; count--){
                curr = queue.poll();    
                
                if(null != curr.left){
                    queue.offer(curr.left);
                }
                
                if(null != curr.right){
                    queue.offer(curr.right);
                }
                
                level.add(curr.value);
            }
            
            result.add(level);
        }
        
        return result;
    }
    
}
