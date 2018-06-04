package fgafa.linkedlist;

public class Nth2LastNode {

	/**
	 * Find the nth to last element of a singly linked list.
	 * 
	 * The minimum number of nodes in list is n.
	 * 
	 * Example Given a List 3->2->1->5->null and n = 2, return node whose value
	 * is 1.
	 */
	/**
	 * @param head: The first node of linked list.
	 * @param n: An integer.
	 * @return: Nth to last node of a singly linked list.
	 */
	ListNode nthToLast(ListNode head, int n) {
		// check
		if (null == head || 1 > n) {
			return head;
		}

		ListNode right = head;
		for (; n > 0; n--) {
			right = right.next;
		}

		ListNode left = head;
		while (null != right) {
			right = right.next;
			left = left.next;
		}

		return left;
	}

}
