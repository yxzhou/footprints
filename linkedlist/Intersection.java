package fgafa.linkedlist;

import fgafa.util.Misc;

/**
 * 
 * Write a program to find the node at which the intersection of two singly linked lists begins.

	Example
	The following two linked lists:
	
	A:          a1 → a2
	                   ↘
	                     c1 → c2 → c3
	                   ↗            
	B:     b1 → b2 → b3
	begin to intersect at node c1.
	
	Note
	If the two linked lists have no intersection at all, return null.
	The linked lists must retain their original structure after the function returns.
	You may assume there are no cycles anywhere in the entire linked structure.
	Challenge
	Your code should preferably run in O(n) time and use only O(1) memory.
 *
 */

public class Intersection {

	/**
	 * @param headA: the first list
	 * @param headB: the second list
	 * @return: a ListNode
	 */
	public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		ListNode result = null;
		//check
		if(null == headA || null == headB){
			return result;
		}
		
		ListNode tail = headA;
		while(null != tail.next){
			tail = tail.next;
		}
		tail.next = headB;
		
		ListNode fast = headA;
		ListNode slow = headA;
		while(null != fast && null != fast.next){
			fast = fast.next.next;
			slow = slow.next;
			
			if(slow == fast){
				fast = headA;
				while(fast != slow){
					fast = fast.next;
					slow = slow.next;
				}
				result = fast;
				break;
			}
		}
		
		tail.next = null;
		return result;
	}

	public static void main(String[] args) {
		Intersection sv = new Intersection();

		int[][] x = { { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 } };

		ListNode virtualHead, curr;
		for (int i = 0; i < x.length; i++) {
			System.out.println("\nArray 000:" + Misc.array2String(x[i]));

			virtualHead = new ListNode(-1);
			curr = virtualHead;
			if (x[i] != null) {
				for (int j : x[i]) {
					curr.next = new ListNode(j);
					curr = curr.next;
				}
			}

		    System.out.println("List 001: ");   
		    ListNode.printList(virtualHead.next);
		      
			System.out.println("  Result: " + sv.getIntersectionNode(virtualHead.next, virtualHead.next));
		}
	}
	

}
