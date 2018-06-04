package fgafa.linkedlist;

import fgafa.util.Misc;

/**
 * 
 * Given a list, split it into two sublists — one for the front half, and one for the back half. 
 * If the number of elements is odd, the extra element should go in the front list. 
 * e.g. 
 *  null or empty    => null or empty
 *  {1}              => {1}
 *  {1, 2}           => {1} and {2}
 *  {2, 3, 5, 7, 11} => {2, 3, 5} and {7, 11}.
 *  
 * 
 *
 */

public class Split
{
 /**
  * split a Linkedlist into 2 parts. The list has not loop, the tail.next is null.
  * 
  * @param head
  * @param front
  * @param back
  */

  public ListNode split(ListNode head) {
    if (head == null)
      return null;
    // if(hasLoop(header)) 
    //    throw new Exception(“ there is a loop in LinkedList: ” + header);

    ListNode slow = head;
    ListNode fast = head;
      
    while (fast != null && fast.next != null){  
      fast = fast.next.next;
      slow = slow.next;
    }
    
    if ( null != fast){
        slow = slow.next;
    }
    
    if( null != slow){
        slow.next = null;
    }
    
    return slow;
  }


  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Split s = new Split();
    
    //init
    int[][] n = {null, {}, {1}, {1, 2}, {2, 3, 5, 7, 11}};

    //main
    
    for(int i=0; i< n.length; i++){
      System.out.println("\n plan to split: "+ Misc.array2String(n[i]));
      
      ListNode head = ListNode.buildLL(n[i]);
      
      //System.out.println("\n plan to split: "+ Misc.array2String(n[i]));
      System.out.println("        parse to: ");      
      ListNode.printList(head);
      
      ListNode first = head;      
      ListNode second = s.split(head);
      
      System.out.println(" Front: " + ((first != null)? first.val : "" ) + "\t Back: " + ( (second != null)? second.val : "")  );
      
      
    }
  }

}
