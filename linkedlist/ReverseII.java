/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkedlist;

/**
 *
 * Reverse a linked list from position m to n. Do it in-place and in one-pass.
 *
 * Example 1: 
 * Given 1->2->3->4->5->NULL, m = 2 and n = 4,
 * return 1->4->3->2->5->NULL.
 *
 * Note: Given m, n satisfy the following condition: 1 <= m <= n <= length of list.
 *
 */
public class ReverseII {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || m >= n || n < 2 || m < 1) {
            return head;
        }

        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode pre = dummy;  // the node before the m_th node 
        for (int i = 1; i < m && pre != null; i++) {
            pre = pre.next;
        }

        if (pre == null || pre.next == null) {
            return head;
        }

        ListNode tail = pre.next;
        ListNode curr = tail.next;

        for (; m < n && curr != null; m++) {
            tail.next = curr.next;
            
            //insert curr after pre
            curr.next = pre.next;
            pre.next = curr;

            curr = tail.next;
        }

        head = m < n ? null : dummy.next;
        dummy.next = null; // make dummy ready for GC
        return head;
    }
}
