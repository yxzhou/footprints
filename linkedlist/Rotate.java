package linkedlist;

/**
 *
 * Given a list, rotate the list to the right by k places, where k is non-negative.
 *
 * Example 1:
 * Input:  1->2->3->4->5->null and k = 2
 * Output: 4->5->1->2->3->null
 * 
 * Example 2:
 * Input: 3->2->1->null and k = 1 
 * Output:1->3->2->null
 *
 */
public class Rotate {

    public ListNode rotateRight(ListNode head, int k) {
        if(head == null || k < 1){
            return head;
        }

        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode r = dummy;
        ListNode lPre = dummy;
        int i = 0;
        for( ; i < k && r != null; i++, r = r.next );

        if( i < k || r.next == null ){ // for case list size < k
            return rotateRight(head, k %= i - (r == null? 1 : 0));
        }

        for( r = r.next; r != null; lPre = lPre.next, r = r.next );

        head = dummy;
        ListNode l = lPre.next;
        lPre.next = null;

        while(l != null){
            r = l.next;

            l.next = head.next;
            head.next = l;

            head = l;
            l = r;
        }

        return dummy.next;
    }

    public ListNode rotateRight_n(ListNode head, int k) {
        if (null == head || null == head.next || k < 1) {
            return head;
        }

        int count = 0;
        ListNode oldTail = head;

        for ( ; count < k && oldTail.next != null; count++) {
            oldTail = oldTail.next;
        }

        if (count < k) {
            k = k % (count + 1);

            return rotateRight_n(head, k);
        }

        ListNode newTail = head;
        while (oldTail.next != null) {
            oldTail = oldTail.next;
            newTail = newTail.next;
        }
        ListNode newHead = newTail.next;

        oldTail.next = head;
        newTail.next = null;

        return newHead;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        Rotate sv = new Rotate();

        /* fundmetal testing  
    
    ListNode a1 = sv.new ListNode(11);
    ListNode b1 = sv.new ListNode(22);
    a1.next = b1;
    
    while(a1 != b1){
      System.out.println("== "+a1.val);
      a1 = a1.next;
    }
         */
    }
  
}
