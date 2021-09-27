package linkedlist;

import java.util.HashMap;
import java.util.Map;

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

	public ListNode getIntersectionNode_2(ListNode headA, ListNode headB) {
		if(headA == null || headB == null){
			return null;
		}

		ListNode tailA = headA;
		while(tailA.next != null){//assume there is no cycle in A and B
			tailA = tailA.next;
		}

		tailA.next = headA;

		ListNode fast = headB;
		ListNode slow = headB;
		while(fast != null && fast.next != null){
			fast = fast.next.next;
			slow = slow.next;

			if(fast == slow){
				fast = headB;
				while(fast != slow){
					slow = slow.next;
					fast = fast.next;
				}

				tailA.next = null;
				return fast;
			}
		}

		tailA.next = null;
		return null;
	}

	public ListNode getIntersectionNode_n(ListNode headA, ListNode headB) {
		if(headA == null || headB == null){
			return null;
		}

		//find tail of headA, connect as a cycle
		ListNode tailA = headA;
		while(tailA.next != null){
			tailA = tailA.next;
		}
		tailA.next = headA;

		ListNode fast = headB;
		ListNode slow = headB;
		ListNode result = null;
		while(fast.next != null && fast.next.next != null ){
			fast = fast.next.next;
			slow = slow.next;

			if(fast == slow){
				while(headB != slow){
					headB = headB.next;
					slow = slow.next;
				}

				result = slow;
				break;
			}
		}

		tailA.next = null;
		return result;
	}

	public static void main(String[] args) {
		Intersection sv = new Intersection();

		int[][][] x = {
				{ { 1, 2, 3 }, {1, 2, 3} },
				{ { 1, 2, 3, 4 }, {1, 2, 3, 4} },
				{ { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 }, {7, 8, 9, 10, 11, 12, 13} },
				{ {7, 8, 9, 10, 11, 12, 13}, { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 } }
		};

		ListNode[] heads;
		for (int i = 0; i < x.length; i++) {
			heads = sv.build(x[i][0], x[i][1]);

		    System.out.println("\nInput: ");
		    ListNode.printList(heads[0]);
			ListNode.printList(heads[1]);

			ListNode intersection = sv.getIntersectionNode_n(heads[0], heads[1]);

			System.out.println("  Result: " + (intersection == null ? null : intersection.val));
		}
	}
	

	private ListNode[] build(int[] A, int[] B){
		Map<Integer, ListNode> map = new HashMap<>();

		ListNode virtualHeadA = new ListNode(-1);
		ListNode curr = virtualHeadA;
		if (A != null) {
			for (int j : A) {
				curr.next = new ListNode(j);
				curr = curr.next;

				map.put(j, curr);
			}
		}

		ListNode virtualHeadB = new ListNode(-1);
		curr = virtualHeadB;
		if (B != null) {
			for (int j : B) {
				if(map.containsKey(j)){
					curr.next = map.get(j);
				}else{
					curr.next = new ListNode(j);
					map.put(j, curr.next);
				}

				curr = curr.next;
			}
		}

		return new ListNode[]{virtualHeadA.next, virtualHeadB.next};
	}
}
