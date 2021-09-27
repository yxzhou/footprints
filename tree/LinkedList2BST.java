package tree;

import linkedlist.ListNode;

public class LinkedList2BST {
    
    ListNode cursor;
    
    /*
     * f(n) = f(n - 1) + 1
     *   
     *  time complexity  T(n) = n
     */
    public TreeNode linkedList2BST(ListNode head){
        if(null == head){
            return null;
        }
        
        int length = getLength(head);
        
        cursor = head;
        return linkedList2BST(length);
    }
    
    
    private int getLength(ListNode head){
        int length = 0;
        
        ListNode node = head;
        while(null != node){
            node = node.next;
            length++;
        }
        
        return length;
    }
    
    private TreeNode linkedList2BST(int length){
        if(length < 1 ){
            return null;
        }
        
        int mid = length / 2;
        TreeNode left = linkedList2BST(mid);
        
        TreeNode root = new TreeNode(cursor.val);
        cursor = cursor.next;
        
        root.left = left;
        root.right = linkedList2BST(length - mid - 1);
        
        return root;
    }

}
