package game.palindrome;

import linkedlist.ListNode;

/**
 *
 * Given a singly linked list, determine if it is a palindrome.
 *
 * Follow up: Could you do it in O(n) time and O(1) space?
 * 
 * Solution #1
 *     
 * 
 */
public class PalindromeLinkedList {

    public boolean isPalindrome(ListNode head) {
        if (null == head || null == head.next) {
            return true;
        }

        //find out the middle node to split to two parts
        ListNode slow = head;
        ListNode fast = head;
        while (null != fast && null != fast.next) {
            slow = slow.next;
            fast = fast.next.next;
        }

        //reverse the first part
        ListNode tempHead = new ListNode(-1);
        tempHead.next = head;
        ListNode curr = head.next;
        while (curr != slow) {
            //insert curr after tempHead
            head.next = curr.next;
            curr.next = tempHead.next;
            tempHead.next = curr;

            curr = head.next;
        }

        //compare the first part with the second part
        ListNode first = tempHead.next;
        ListNode second = slow;
        if (null != fast) {
            second = slow.next;//when fast is not null, the total number of nodes are odd and the slow is at the middle
        }
        while (null != second) {
            if (first.val != second.val) {
                return false;
            }

            first = first.next;
            second = second.next;
        }

        return true;
    }
    /** same as isPalindrome */
    public boolean isPalindrome_n(ListNode head) {
        ListNode dummy = new ListNode(-1);
        ListNode move1 = head;
        ListNode move2 = head;
        ListNode tmp;

        while( move2 != null && move2.next != null ){
            move2 = move2.next.next;

            tmp = move1.next;
            move1.next = dummy.next;
            dummy.next = move1;
            move1 = tmp;
        }

        ListNode n1 = dummy.next;
        ListNode n2 = (move2 == null ? move1 : move1.next);

        while(n1 != null && n1.val == n2.val){
            n1 = n1.next;
            n2 = n2.next;
        }

        return n1 == null? true : false;
    }
    
    
}
