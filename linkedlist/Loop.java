package fgafa.linkedlist;

public class Loop {

	/*
	 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
	 */
	public ListNode detectCycle(ListNode head) {
		ListNode slow = head;
		ListNode fast = head;
		while(null != fast && null != fast.next){
			slow = slow.next;
			fast = fast.next.next;
			
			if(slow == fast){
				for(slow = head; ; slow = slow.next, fast = fast.next){
					if(slow == fast){
						return slow;
					}
				}
			}
		}
		
		return null;        
	}

	/*
	 * Given a linked list, determine if it has a cycle in it.
	 */
	public boolean hasCycle_2(ListNode head) {
		ListNode slow = head;
		ListNode fast = head;
		while (null != fast && null != fast.next) {
			slow = slow.next;
			fast = fast.next.next;

			if (slow == fast){
				return true;
			}
		}

		return false;
	}

	public boolean hasCycle(ListNode head) {
//		if(null == head)
//			return false;
		
		ListNode slow = head;
		ListNode fast = head;
		while(null != fast){
			slow = slow.next;
			if(null != fast.next)
				fast = fast.next.next;
			else 
				fast = null;
			
			if( null != fast && fast == slow)
				return true;
		}

		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
