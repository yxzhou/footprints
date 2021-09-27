package sorting;

public class SortList {
	/**
	 * 
	 * Sort a linked list in O(n log n) time using constant space complexity.
	 */

	public ListNode sortList_merge(ListNode head) {
		if(null == head || null == head.next){
			return head;	
		}
		
		ListNode mid = findMiddle(head);
		ListNode secondHalf = sortList_merge(mid.next);
		mid.next = null;
		ListNode firstHalf = sortList_merge(head);

		return merge(firstHalf, secondHalf);
	}

	private ListNode findMiddle(ListNode head){
		ListNode fast = head;
		ListNode slow = head;
		while( null != fast.next && null != fast.next.next){
			fast = fast.next.next;
			slow = slow.next;
		}
		
		return slow;
	}
	
	private ListNode merge(ListNode left, ListNode right){
		ListNode vHead = new ListNode(-1);
		ListNode newList = vHead;
		while(null != left && null != right){
			if(left.val <= right.val){
				newList.next = left;
				left = left.next;
			}else{ // left.val > right.val,  insert right before left
				newList.next = right;
				right = right.next;
			}
			newList = newList.next;
		}
		
		if(null != right){
			newList.next = right;
		}
		if(null != left){
			newList.next = left;
		}
		
		return vHead.next;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SortList sv = new SortList();
		
		int[][] input = {
				{3},
				{3,2},
				{3,2,4},
				{3,2,4,5}
		};

		for(int[] arr : input ){
			ListNode head = sv.init(arr);
			System.out.println("\n");
			sv.printList(head);
			System.out.println();
			sv.printList(sv.sortList_merge(head));
		}
	}

	// Definition for singly-linked list.
	public class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}
	}

	private void printList(ListNode node) {
		while (node != null) {
			System.out.print(node.val);
			System.out.print("->");

			node = node.next;
		}
	}

	private ListNode init(int[] arr) {
		ListNode head = null;

		// check
		if (arr == null || arr.length == 0)
			return head;

		head = new ListNode(arr[0]);
		ListNode curr = head;
		ListNode tmp;
		for (int i = 1; i < arr.length; i++) {
			tmp = new ListNode(arr[i]);
			curr.next = tmp;

			curr = tmp;
		}

		return head;
	}

}
