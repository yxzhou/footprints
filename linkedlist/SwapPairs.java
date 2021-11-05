package linkedlist;

/*
 * Given a linked list, swap every two adjacent nodes and return its head.<br>
 * <br>
 * For example, <br>
 * Given 1->2->3->4, you should return the list as 2->1->4->3.<br>
 * <br>
 * Your algorithm should use only constant space. <br>
 * You may not modify the values in the list, only nodes itself can be changed.<br>
 * 
 */
public class SwapPairs {

    /*
   * Time O(n), Space O(1)
     */
    public ListNode swapPairs_changevalue(ListNode head) {

        ListNode curr = head;
        while (curr != null && curr.next != null) {
            int tmp = curr.val;
            curr.val = curr.next.val;
            curr.next.val = tmp;

            curr = curr.next.next;
        }

        return head;
    }

    /*
   * Time O(n), Space O(1)
     */
    public ListNode swapPairs_changenode(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode dummy = new ListNode(-1);

        ListNode curr = head;
        ListNode next;
        ListNode pairTail = dummy;
        ListNode newPairHead = null;

        while (curr != null && (next = curr.next) != null) {
            newPairHead = next.next;

            pairTail.next = next;
            next.next = curr;

            pairTail = curr;
            curr = newPairHead;
        }

        pairTail.next = curr;

        return dummy.next;

    }

    public ListNode swapPairs_changenode_recursive(ListNode head) {
        if (null == head || null == head.next) {
            return head;
        }

        ListNode curr = head;
        ListNode next = curr.next;

        curr.next = swapPairs_changenode_recursive(next.next);
        next.next = curr;

        return next;
    }

    public ListNode swapPairs_changenode_iterator(ListNode head) {

        ListNode newHead = new ListNode(0);
        newHead.next = head;
        ListNode pre = newHead;
        ListNode curr;
        ListNode next;

        while (null != pre.next && null != pre.next.next) {
            curr = pre.next;
            next = curr.next;

            //swap node
            curr.next = next.next;
            next.next = curr;
            pre.next = next;

            pre = curr;
        }

        return newHead.next;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
  
}
