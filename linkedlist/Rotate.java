package fgafa.linkedlist;

public class Rotate
{
  
  /*
   * 
   * Given a list, rotate the list to the right by k places, where k is non-negative.
   * 
   * For example:
   * Given 1->2->3->4->5->NULL and k = 2,
   * return 4->5->1->2->3->NULL.
   * 
   */
	public ListNode rotateRight(ListNode head, int k) {
		// check
		if (null == head || k < 1) {
			return head;
		}

		ListNode left = head, right = head;
		int len = 0; // the length of list
		for (; len < k && null != right; len++) {
			right = right.next;
		}

		if (null == right) { // need 1+ full list rotate
			return rotateRight(head, k % len);
		}

		while (null != right.next) {
			left = left.next;
			right = right.next;
		}

		right.next = head;
		head = left.next;

		left.next = null;

		return head;
	}
  
  public ListNode rotateRight_n(ListNode head, int k) {
      if( null == head || k < 1 ){
    	  return head;
      }
      
      ListNode curr = head;
      int i=0;
      for( ; i<=k && null != curr; i++){
    	  curr = curr.next;
      }
      if(null == curr){
    	  k %= i;
    	  return rotateRight_n(head, k);
      }
      
      //move the nodes from after curr to after virtualHead
      ListNode virtualHead = new ListNode(-1);
      virtualHead.next = head;
      
      ListNode next;
      while(null != curr.next){
    	  next = curr.next;
    	  curr.next = next.next;
    	  
    	  next.next = virtualHead.next;
    	  virtualHead.next = next;
      }
      
      head = virtualHead.next;
      virtualHead.next = null;
      return head;
  }
  
  /**
   * @param head: the List
   * @param k: rotate to the right k places
   * @return: the list after rotation
   */
  public ListNode rotateRight_n2(ListNode head, int k) {
      //check
      if(null == head || null == head.next || k < 1){
          return head;
      }
      
      int count = 0;
      ListNode tail = head;
      ListNode newHead = head;
      
      for(count = 0; count < k && tail.next != null; count++){
          tail = tail.next;
      }
      
      if(count < k){
          k = k % (count + 1);
          
          return rotateRight(head, k);
      }
      
      for( count = 1; tail.next != null; count++){
          tail = tail.next;
      }
      
      while(count > 0){
          newHead = head.next;
          
          tail.next = head;
          head.next = null;
          tail = head;
          
          head = newHead;
          count--;
      }
      
      return head;
  }
  
  
  public ListNode rotateRight_n3(ListNode head, int k) {
      //check
      if(null == head || null == head.next || k < 1){
          return head;
      }
      
      int count = 0;
      ListNode oldTail = head;
      
      for(count = 0; count < k && oldTail.next != null; count++){
          oldTail = oldTail.next;
      }
      
      if(count < k){
          k = k % (count + 1);
          
          return rotateRight(head, k);
      }
      
      ListNode newTail = head;
      while(oldTail.next != null){
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
