package fgafa.linkedlist;


public class RemoveSpecial {

	/**
	 * Remove all elements from a linked list of integers that have value val.
	 * 
	 * Example 
	 * Given: 1 --> 2 --> 6 --> 3 --> 4 --> 5 --> 6, val = 6 
	 * Return: 1--> 2 --> 3 --> 4 --> 5
	 * 
	 */
	public ListNode removeElements(ListNode head, int val) {
		//check input
//		if(null == head){
//			return null;
//		}

		ListNode virtualHead = new ListNode(-1);
		virtualHead.next = head;

		ListNode pre = virtualHead;
		ListNode curr;
		while( null != pre && null != pre.next ){
			curr = pre.next;

			if(val == curr.val){
				pre.next = curr.next;
				curr.next = null;
			}else{
				pre = pre.next;
			}
		}
		
		return virtualHead.next;
	}
	
    public ListNode removeElements_2(ListNode head, int val) {
        //check
//        if(null == head){
//            return head;
//        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode pre = dummy;
        ListNode curr;
        while(null != pre.next){
            curr = pre.next;
            if(val == curr.val){
                pre.next = curr.next;
                curr.next = null;  // for GC
            }else{
                pre = curr;
            }
        }
        
        head = dummy.next;
        //dummy.next = null;  //for GC
        return head;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
