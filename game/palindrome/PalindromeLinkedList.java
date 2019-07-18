package fgafa.game.palindrome;

import fgafa.linkedlist.ListNode;

/**
 * 
 * Given a singly linked list, determine if it is a palindrome.
 * 
 * Follow up: Could you do it in O(n) time and O(1) space?
 *
 */
public class PalindromeLinkedList {

	public boolean isPalindrome(ListNode head) {
		//check
		if(null == head || null == head.next){
			return true;
		}
			
		//find out the middle node to split to two parts
		ListNode slow = head;
		ListNode fast = head;
		while(null != fast && null != fast.next){
			slow = slow.next;
			fast = fast.next.next;
		}
		
		//reverse the first part
		ListNode tempHead = new ListNode(-1);
		tempHead.next = head;
		ListNode curr = head.next;
		while( curr != slow){
			//insert curr after tempHead
			head.next = curr.next;
			curr.next = tempHead.next;
			tempHead.next = curr;
			
			curr = head.next;
		}
		
		//compare the first part with the second part
		ListNode first = tempHead.next;
		ListNode second = slow;
		if(null != fast){
			second = slow.next;//when fast is not null, the total number of nodes are odd and the slow is at the middle
		}
		while( null != second ){
			if(first.val != second.val){
				return false;
			}
			
			first = first.next;
			second = second.next;
		}
		
		return true;
	}

}
