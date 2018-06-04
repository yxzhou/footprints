package fgafa.linkedlist;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;


public class RemoveDuplicate
{
  
  
  /*
   * careercup  linkedlist 122
   * 
   * remove duplicates from an unsorted linked list with a Hashtable 
   * 
   * For example,
   * Given 1->1->2, return 1->2.
   * Given 1->2->1->3->2, return 1->2->3.
   * 
   *  Time O(n), Space O(n)     
   */
    public static void deleteDuplicates_unsorted(ListNode head) {
        // check
        if (null == head) {
            return;
        }

        // init, Hashtable<Node.value, isExisted>
        HashSet<Integer> ht = new HashSet<Integer>();
        ht.add(head.val);

        ListNode curr = head;
        ListNode next;
        while (null != curr.next) {
            next = curr.next;

            if (ht.contains(next.val)) { 
                curr.next = next.next;
            } else { 
                ht.add(next.val);

                curr = next;
            }
        }
    }
  
  public static void main(String[] args) {

    System.out.println("------------Start-------------- " );
    
    //init a LinkedList
    int n = 15;  
    
    Random random = new Random();
    LinkedList<Integer> list = new LinkedList<Integer>();
    
    for(int i = 0; i< n; i++){
      list.add(random.nextInt(10));
    }
    
    System.out.println("remove duplicates from: "+list.toString()); 

    
    ListNode list3 = ListNode.buildLL(list);
    
    //System.out.println("remove duplicates from: "+list3.toString()); 
    deleteDuplicates_unsorted(list3);
    System.out.println("Result: "+list3.toString());      
    
    System.out.println("------------End-------------- " );


  }
  
  /**
   * 
   * Remove Duplicates from Sorted List
   * Given a sorted linked list, delete all duplicates such that each element appear only once.
   * 
   * For example,
   * Given 1->1->2, return 1->2.
   * Given 1->1->2->3->3, return 1->2->3.
   * 
   * @param head
   * @return
   */  
  public ListNode deleteDuplicates_sorted(ListNode head) {
      if(null == head){
          return head;
      }
      
      ListNode curr = head;
      ListNode next ;
      while(null != curr.next){
          next = curr.next;
          
          if(curr.val == next.val){
              curr.next = next.next;
          }else{
              curr = next;
          }
      }
      
      return head;
  }
  
  /**
   * 
   * Remove Duplicates from Sorted List II
   * Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.
   * 
   * For example,
   * Given 1->2->3->3->4->4->5, return 1->2->5.
   * Given 1->1->1->2->3, return 2->3.
   * 
   * @param head
   * @return
   */
  public ListNode deleteDuplicates_sorted_all(ListNode head) {
//      if(null == head){
//          return head;
//      }
      
      //init
      ListNode dummy = new ListNode(0);
      dummy.next = head;
      
      ListNode pre = dummy;
      ListNode curr = head;
      ListNode next;
      
      while(null != curr){
          next = curr.next;
          
          for( ; null != next && curr.val == next.val; next = next.next);
          
          if(next == curr.next){
              pre = curr;
          }else{
              pre.next = next;
          }
          
          curr = next;
      }
      
      return dummy.next;
  }
  
  /**
   * 
   * Remove Nth Node From End of List
   * Given a linked list, remove the nth node from the end of list and return its head.
   * 
   * For example,
   * 
   * Given linked list: 1->2->3->4->5, and n = 2.
   * 
   * After removing the second node from the end, the linked list becomes 1->2->3->5.
   * Note:
   * Given n will always be valid.
   * Try to do this in one pass.
   * 
   * testcases:
   * {null, 1}
   * {1-2, -1}
   * {1-2, 0}
   * {1, 1}
   * {1, 2}
   * {1-2, 1}
   * {1-2, 2}
   * {1-2, 3}
   * {1-2-3, 1}
   * {1-2-3, 2}
   * {1-2-3, 3}
   * {1-2-3, 4}
   * {1-2-3-4-5, 2}
   */
  
  public ListNode removeNthFromEnd(ListNode head, int n) {
    //check
    if(head == null || n<1)
      return null;
    
    ListNode left = head, right = head;
    int i=0;
    for( ; i<n && right != null; i++){
      right = right.next;
    }
    
    if(i<n)
      return null;  // n is larger than list length 
    else if(i==n && right == null) // n is equal to the list length
      return head.next;
    
    /* n is smaller than the list length */
    while(right.next != null){
      left = left.next;
      right = right.next;
    }
    
    left.next = left.next.next;
        
    return head;
  }
}
