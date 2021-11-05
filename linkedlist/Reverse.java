package linkedlist;

import util.Misc;

/**
 *
 * Reverse a linked list.
 * 
 * Example 1:
 * Input:  1->2->3->null 
 * Output: 3->2->1->null 
 *
 * Example 2:
 * Input: 1->2->3->4->null 
 * Output: 4->3->2->1->null 
 *
 * Challenge: Reverse it in-place and in one-pass
 * 
 */

public class Reverse {

    /**
     * input  list: a->b->c 
     * output list: c->b->a (in place)
     *
     */
    public ListNode reverse(ListNode head) {
        if (null == head) {
            return head;
        }

        ListNode curr = head.next;
        head.next = null;
        ListNode next;
        while (null != curr) {
            next = curr.next;

            //insert curr before pre
            curr.next = head;
            head = curr;

            curr = next;
        }

        return head;
    }

    /**
     * create a newHead(-1), for every node (exclude the last node) insert it after newHead
     *
     */
    public ListNode reverse_n(ListNode head) {
        ListNode dummy = new ListNode(-1);
        
        ListNode curr = head;
        ListNode next;

        while (curr != null) {
            next = curr.next;

            //insert curr after newHead
            curr.next = dummy.next;
            dummy.next = curr;

            curr = next;
        }

        return dummy.next;
    }

    public ListNode reverse_recursive(ListNode head) {
        if (null == head || null == head.next) {
            return head;
        }

        ListNode next = head.next;
        head.next = null;

        ListNode newHead = reverse_recursive(next);

        next.next = head;

        return newHead;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        Reverse sv = new Reverse();

        //init
        int[][] n = {null, {}, {1}, {1, 2}, {2, 3, 5, 7, 11}};

        //main
        for (int i = 0; i < n.length; i++) {
            System.out.println("\n the original array: " + Misc.array2String(n[i]));

            ListNode list = ListNode.buildLL(n[i]);

            System.out.println("   the original list: " + list.toString());

        }

    }

}
