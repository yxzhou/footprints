package linkedlist;


public class ReverseNodeInKGroup
{
  
  /*
   * 
   * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
   * 
   * If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
   * You may not alter the values in the nodes, only nodes itself may be changed.
   * Only constant memory is allowed.
   * 
   * For example,
   * Given this linked list: 1->2->3->4->5
   * For k = 2, you should return: 2->1->4->3->5
   * For k = 3, you should return: 3->2->1->4->5
   * 
   */
  public ListNode reverseKGroup(ListNode head, int k) {
    ListNode returnValue = head;

    //check 
    if(head == null || head.next == null || k<2 )
      return head;
    
    //reverse
    int i=0;
    ListNode preGroupTail = null;
    ListNode curGroupHead = head;
    ListNode nextGroupHead = head;
    ListNode curGroupNewHead = null;
    
    do{
      if(i<k){
        nextGroupHead = nextGroupHead.next;
        i++;
      }
      
      if(i == k){ // i==k
        curGroupNewHead = reverseGroup(curGroupHead, k, nextGroupHead);
        
        if(returnValue == head)
          returnValue = curGroupNewHead;
        
        if(preGroupTail != null)
          preGroupTail.next = curGroupNewHead;
        preGroupTail = curGroupHead;
        curGroupHead = nextGroupHead;
        
        i = 0;
      }
    }while(nextGroupHead !=null);
    
    if(i != 0 && preGroupTail != null)
      preGroupTail.next = curGroupHead;   
    else if(i == 0 && preGroupTail != null)  
      preGroupTail.next = null; 
      
    return returnValue;
}
  
  private ListNode reverseGroup(ListNode curGroupHead, int length, ListNode nextGroupHead){
    
    ListNode pre ;
    ListNode current = curGroupHead;
    ListNode next = current.next;
    
    do{
      pre = current;
      current = next;
      next = current.next;
      
      current.next = pre;
    }while(next != nextGroupHead);
    
    return current;  // the new group head
  }
  
  public ListNode reverseKGroup_n(ListNode head, int k) {
      //check
      if(null == head || k <= 1){
          return head;
      }
      
      ListNode virtualHead = new ListNode(0);
      virtualHead.next = head;
      ListNode start = virtualHead;
      ListNode end = start;
      ListNode next;
      ListNode tmp;
      int count = 0;
      while(null != start && null != end){
          for(count = 0, end = start; count < k && null != end; count++){
              end = end.next;
          }
          
          if(null != end){
              next = start.next;

              start.next = end;
              start = next;
              
              while(start != end){
                  tmp = start.next;
                  
                  start.next = end.next;
                  end.next = start;
                  
                  start = tmp;
              }
              
              start = next;
          }
      }
      
      return virtualHead.next;
  }

  
  public static void main(String[] args) {
    
    ReverseNodeInKGroup sv = new ReverseNodeInKGroup();
    
    //init a Node list  1->2->3->4->5
    int n = 4;
    ListNode head = ListNode.buildLL(n);
    
//    //print the original Node list.
//    System.out.print("\nthe Node list is: ");
//    sv.printNodeList(head);
    
    //reverseKGroup
    for(n=2; n<6; n++){
      head = ListNode.buildLL(n);
      
      //print the original Node list.
      System.out.print("\nthe Node list is: ");
      ListNode.printList(head);
      
      for(int i=2; i<n+2; i++){
        
        head = sv.reverseKGroup(head, i);
        
        System.out.print("\nreverse by group " +i+ " :" );
        ListNode.printList(head);
        
        head = ListNode.buildLL(n);
      }      
    }


  }
  
  
  
}
