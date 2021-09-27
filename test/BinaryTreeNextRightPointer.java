package test;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeNextRightPointer {
    
    class TreeNode{
        int value;
        TreeNode left;
        TreeNode right;
        TreeNode next;
    }
    
    public void connect(TreeNode root){
        if(null == root){
            return;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        TreeNode curr;
        TreeNode next;
        while(!queue.isEmpty()){
            curr = null;
            for(int i = queue.size(); i >= 0; i--){
                next = queue.poll();
                
                if(null != next.left){
                    queue.offer(next.left);
                }
                
                if(null != next.right){
                    queue.offer(next.right);
                }
                
                if(null != curr){
                    curr.next = next;
                }
                
                curr = next;
            }
            
        }
    }
    
    //constant space
    public void connect_2(TreeNode root){
        if(null == root){
            return;
        }
        
        TreeNode curr;
        TreeNode next;
        TreeNode currHead = root;
        TreeNode nextHead = null;
        while( null != currHead ){

            //locate nextHead
            for(curr = currHead; null != curr; curr = curr.next){
                if(null != curr.left){
                    nextHead = curr.left;
                } else if(null != curr.right){
                    nextHead = curr.right;
                }
            }
                
            if(null == nextHead){
                break;
            }
            
            //connect the next pointers
            for( next = nextHead ; null != curr; curr = curr.next){
                if(null != curr.left){
                    next.next = curr.left;
                    next = next.next;
                }
                
                if(null != next.right){
                    next.next = curr.right;
                    next = next.next;
                }
            }     
            if(next == next.next){
                next.next = null;
            }
            
            currHead = nextHead;
        }
    }
    


    
}
