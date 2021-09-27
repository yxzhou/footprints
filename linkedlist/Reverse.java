package linkedlist;

import util.Misc;


public class Reverse
{

  
  /*
   * 
   * Reverse a linked list from position m to n. Do it in-place and in one-pass.
   * 
   * For example:
   * Given 1->2->3->4->5->NULL, m = 2 and n = 4,
   * 
   * return 1->4->3->2->5->NULL.
   * 
   * Note:
   * Given m, n satisfy the following condition:
   * 1 <= m <= n <= length of list.
   * 
   */
  
  public ListNode reverseBetween(ListNode head, int m, int n) {
    //check
    if(head == null || m==n || m < 1){
      return head;
    }
    
    ListNode dummy = new ListNode(-1);
    dummy.next = head;
    
    ListNode left = dummy;
    ListNode curr = null;
    ListNode next = null;
    ListNode right = null;
    int i=1;
    for( ; i<m ; i++){
      left = left.next;
    }
    
    right = left.next;
    curr = right.next;
    for(i=m ; i<n; i++){
      next = curr.next;
      
      //insert curr after left
      curr.next = left.next;
      left.next = curr;
      
      curr = next;
    }
    
    right.next = next;
    
    return dummy.next;
  }
  
  /**
   * @param ListNode head is the head of the linked list 
   * @oaram m and n
   * @return: The head of the reversed ListNode
   */
  public ListNode reverseBetween_n2(ListNode head, int m , int n) {
      //check
      if(null == head){
          return head;
      }
      
      ListNode dummy = new ListNode(-1);
      dummy.next = head;
      
      ListNode pre = dummy;
      ListNode start = head;
      for(int i = 1; i < m; i++){
          pre = start;
          start = start.next;
      }
      
      ListNode curr = start.next;
      ListNode next;
      for(int i = m; i < n; i++){
          next = curr.next;
          
          //insert curr after pre
          curr.next = pre.next;
          pre.next = curr;
          
          curr = next;
      }
      start.next = curr;  //key point
      
      head = dummy.next;
      dummy.next = null;
      dummy = null;
      return head;
  }
  
  /* not one pass 
   * to reverse between the left (m) to the right (n) , insert the left back to the right
   * */
  public ListNode reverseBetween2(ListNode head, int m, int n) {
    //check
    if(head == null || m==n || m < 1)
      return head;
      
    ListNode preLeft = null;
    ListNode left = head;
    if(m>1){
      preLeft = getListNode(head, m-1);
      left = preLeft.next;
    }
    ListNode right = getListNode(left, n-m);
    if(right == null )
      return head;
    ListNode postRight = right.next;
    
    /*  add left between right and postRight */
    ListNode curr;
    while(left != right){
      curr = left; 
      left = left.next; 
      
      right.next = curr;
      curr.next = postRight;
      
      postRight = curr;
    }
    
    /*  */
    if(preLeft == null) // when m = 1
      head = right;
    else
      preLeft.next = right;
    
    return head;
  }
  
  private  ListNode getListNode(ListNode curr, int m){
    int count=1;  
    for( ; count < m && curr != null; count++){
      curr = curr.next;
    }
    
    if(count < m) // m is bigger than the list length.
      return null;
    
    return curr;
  }
  
  /*
   * Given m, n satisfy the following condition:
   * 1 <= m <= n <= length of list.
   */
	public ListNode reverseBetween_n(ListNode head, int m, int n) {
		if (m == n){
			return head;
		}

		ListNode virtualHead = new ListNode(0);
		virtualHead.next = head;

		ListNode pre = virtualHead;
		for (int i = 1; i < m; i++) {
			pre = pre.next;
		}
		
		ListNode curr = pre.next;
		if (null != curr) {
			ListNode next;
			for (int i = m; i < n && null != curr.next; i++) {
				next = curr.next;

				// insert the next after the pre
				curr.next = next.next;
				next.next = pre.next;
				pre.next = next;
			}
		}

		head = virtualHead.next;
		virtualHead.next = null; // make virtualHead ready for GC
		return head;
	}
	
    public ListNode reverseBetween_n3(ListNode head, int m, int n) {
        if (head == null || head.next == null || m == n) {
            return head;
        }

        ListNode virtualHead = new ListNode(Integer.MIN_VALUE);
        virtualHead.next = head;
        ListNode pre = virtualHead;
        for (int i = 1; i < m; i++) {
            pre = pre.next;
            if (pre.next == null){
                throw new IllegalArgumentException("The Linked List size is small than the input start: " + m);
            }
        }

        ListNode curr = pre.next, next;
        for (int i = m; i < n; i++) {
            next = curr.next;
            if (next == null){
                throw new IllegalArgumentException("The Linked List size is small than the input start: " + n);
            }

            // insert the ‘next’ after the ‘pre’
            curr.next = pre.next;
            pre.next = curr;

            curr = next;
        }

        head = virtualHead.next;
        virtualHead.next = null; // make virtualHead ready for GC
        return head;
    }

  
  /*
   * input list:  a->b->c
   * output list:  c->b->a  (in place)
   * 
   */
  public ListNode reverse_n(ListNode head) {
      if(null == head){
          return head;
      }
      
      ListNode curr = head.next;
      head.next = null;
      ListNode next;
      while(null != curr){
          next = curr.next;
          
          //insert curr before pre
          curr.next = head;
          head = curr;
          
          curr = next;
      }
      
      return head;
  }
  
  /*
   * create a newHead(-1),
   * for every node (exclude the last node) 
   *   insert it after newHead 
   *   
   * 
   */
  public ListNode reverse2(ListNode head){
    if(head == null || head.next == null){
      return head;
    }
    
    ListNode dummy = new ListNode(-1);
    ListNode curr = head;
    ListNode next = curr.next;

    while( curr != null ){
      next = curr.next;
      
      //insert curr after newHead
      curr.next = dummy.next;
      dummy.next = curr;
      
      curr = next;
    }
    
    return dummy.next;
  }
  
  public ListNode reverse_recursive(ListNode head) {
      if(null == head || null == head.next){
          return head;
      }
      
      ListNode next = head.next;
      head.next = null;
      
      ListNode newHead = reverse_recursive(next);
      
      next.next = head;
      
      return newHead;
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
   

    Reverse sv = new Reverse();
    
    //init
    int[][] n = {null, {}, {1}, {1, 2}, {2, 3, 5, 7, 11}};

    //main
    for(int i=0; i< n.length; i++){
      System.out.println("\n the original array: "+ Misc.array2String(n[i]));
           
      ListNode list = ListNode.buildLL(n[i]);
      
      System.out.println("   the original list: "+list.toString());      
      

    }
  
    
  }

}
