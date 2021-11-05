/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkedlist;

/**
 * Given a linked list and two values v1 and v2. Swap the two nodes in the linked list with values v1 and v2. It's
 * guaranteed there is no duplicate values in the linked list. If v1 or v2 does not exist in the given linked list, do
 * nothing.
 *
 * You should swap the two nodes with values v1 and v2. Do not directly swap the values of the two nodes. 
 * 
 * Example 1:
 * Input: 1->2->3->4->null, v1 = 2, v2 = 4 
 * Output: 1->4->3->2->null 
 * 
 * Example 2:
 * Input: 1->null, v1 = 2, v2 = 1 
 * Output: 1->null
 *
 */
public class SwapTwoNodes {
    
    /**
     * 
     * 
     * @param head
     * @param v1
     * @param v2
     * @return 
     */
    public ListNode swapNodes(ListNode head, int v1, int v2) {
        if (head == null || v1 == v2 || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(Math.max(v1, v2) + 1);
        dummy.next = head;

        ListNode curr = dummy;
        ListNode next;
        ListNode pre1 = null;
        ListNode pre2 = null;
        while (curr != null) {
            next = curr.next;

            if (next != null) {
                if (next.val == v1) {
                    pre1 = curr;
                } else if (next.val == v2) {
                    pre2 = curr;
                }
            }

            curr = next;
        }

        if (pre1 == null || pre2 == null) {
            dummy.next = null;
            return head;
        }

        ListNode curr1 = pre1.next;
        ListNode curr2 = pre2.next;
        ListNode next1 = curr1.next;
        ListNode next2 = curr2.next;
            
        if (pre1.next == pre2) { //for case: [10->8->7->6->null], v1=8, v2=7
            pre1.next = curr2;
            curr1.next = next2;
            curr2.next = curr1;
        } else if( pre2.next == pre1 ){//for case: [10->8->7->6->null], v1=7, v2=8
            pre2.next = curr1;
            curr2.next = next1;
            curr1.next = curr2;
        }else { //for case: [10->8->7->6->null], v1=6, v2=8
            pre1.next = curr2;
            pre2.next = curr1;
            
            curr1.next = next2;
            curr2.next = next1;
        }

        head = dummy.next;
        dummy.next = null;
        return head;
    }
    
    /**
     * simple
     * 
     * @param head
     * @param v1
     * @param v2
     * @return 
     */
    public ListNode swapNodes_n(ListNode head, int v1, int v2) {
        if (head == null || v1 == v2 || head.next == null) {
            return head;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode pre1 = dummy;
        ListNode pre2 = dummy;
        
        while (pre1.next != null && pre1.next.val != v2) {
            pre1 = pre1.next;
        }
        while (pre2.next != null && pre2.next.val != v1) {
            pre2 = pre2.next;
        }
        
        if (pre1.next == null || pre2.next == null) {
            return head;
        }
        
        ListNode curr1 = pre1.next;
        ListNode curr2 = pre2.next;
        pre1.next = curr2;
        pre2.next = curr1;

        ListNode next1 = curr1.next;
        curr1.next = curr2.next;
        curr2.next = next1;

        return dummy.next;
    }
    
}
