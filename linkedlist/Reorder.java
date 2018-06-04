package fgafa.linkedlist;

public class Reorder {

	/**
	 * 
	 * Given a singly linked list L: L0→L1→…→Ln-1→Ln, reorder it to:
	 * L0→Ln→L1→Ln-1→L2→Ln-2→…
	 * 
	 * You must do this in-place without altering the nodes' values.
	 * 
	 * For example, Given {1,2,3,4}, reorder it to {1,4,2,3}.
	 */
    public void reorderList(ListNode head) {
        if (null == head || null == head.next || null == head.next.next) {
            return;
        }

        // find the middle, split it into two half
        ListNode middle = findMiddle(head);

        ListNode second = middle.next;
        middle.next = null; // split

        // reverse the second half
        second = reverse(second);

        // merge the first half and reversed second half
        merge(head, second);
    }

    private ListNode findMiddle(ListNode head) {

        ListNode slow = head;
        ListNode fast = head.next;
        while (null != fast && null != fast.next) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    private ListNode reverse(ListNode node) {
        ListNode curr = node;
        ListNode next = curr.next;

        curr.next = null; // the tail

        ListNode tmp;
        while (null != next) {
            tmp = next.next;
            next.next = curr;

            curr = next;
            next = tmp;
        }

        return curr;
    }

    private ListNode merge(ListNode first,
                           ListNode second) {
        ListNode next1;
        ListNode next2;

        while (null != second) {
            next1 = first.next;
            next2 = second.next;

            first.next = second;
            second.next = next1;

            first = next1;
            second = next2;
        }

        return first;
    }
	   
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    Reorder sv = new Reorder();
	    
	    int[][] input = {
	    		{1,2},
	    		{1,2,3},
	    		{1,2,3,4},
	    		{1,2,3,4,5},
	    		{1,2,3,4,5,6},
	    		{1,2,3,4,5,6,7},
	    		{1,2,3,4,5,6,7,8},
	    		{1,2,3,4,5,6,7,8,9}
	    		};
	    
	    for(int[] arr : input ){
	    	System.out.print("\n");
	    	
		    ListNode header = ListNode.buildLL(arr);
		    ListNode.printList(header);
		    sv.reorderList(header);
		    ListNode.printList(header);
	    }

	}


}
