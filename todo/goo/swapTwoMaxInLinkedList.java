package todo.goo;

import linkedlist.ListNode;

/**
 *  Google onsite
 *  
 *  Give a linked list, find the max two, swap the reference
 *
 */

public class swapTwoMaxInLinkedList {
    /**
     * Test cases, <br>
     * Given 1->3->2->4, 
     * return the list as 1->4->2->3.
     * <br>
     * Given 4->2->3->1,  
     * return the list as 3->2->4->1.
     * <br>
     * Given 4->3->2->1,  
     * return the list as 3->4->2->1.
     * <br>
     */
    
    public ListNode swapTwoMax(ListNode head){
        if(null == head || null == head.next){
            throw new IllegalArgumentException();
        }
        
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        
        ListNode[] preMax = new ListNode[2];
        if(head.val < head.next.val){
            preMax[0] = head;
            preMax[1] = dummy;
        }else{
            preMax[0] = dummy;
            preMax[1] = head;            
        }
        
        for(ListNode cursor = head.next; null != cursor.next; cursor = cursor.next){
            if(cursor.next.val > preMax[0].next.val){
                preMax[1] = preMax[0];
                preMax[0] = cursor;
            }else if(cursor.next.val > preMax[1].next.val){
                preMax[1] = cursor;
            }
        }

        if(preMax[0].next == preMax[1]){
            swapAdjacentPairAdjoin(preMax[0]);
        }else if (preMax[1].next == preMax[0]){
            swapAdjacentPairAdjoin(preMax[1]);
        }else{
            swapNotAdjacentPair(preMax[0], preMax[1]);
        }
        
        return dummy.next;
    }
    
    
    private void swapAdjacentPairAdjoin(ListNode preNode1){
        ListNode node1 = preNode1.next;
        ListNode node2 = node1.next;
        
        node1.next = node2.next;
        node2.next = node1;
        
        preNode1.next = node2;
    }
    
    private void swapNotAdjacentPair(ListNode preNode1, ListNode preNode2){
        ListNode node1 = preNode1.next;
        ListNode node2 = preNode2.next;
        
        ListNode tmp = node1.next;
        node1.next = node2.next;
        node2.next = tmp;
        
        preNode1.next = node2;
        preNode2.next = node1;
    }
    
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
