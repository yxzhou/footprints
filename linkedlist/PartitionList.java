package fgafa.linkedlist;

import java.util.Stack;

/**
 * 
 * Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.
 * 
 * You should preserve the original relative order of the nodes in each of the two partitions.
 * 
 * For example,
 * Given 1->4->3->2->5->2 and x = 3,
 * return 1->2->2->4->3->5.
 * 
 * @author yxzhou
 *
 */

public class PartitionList
{
   public ListNode partition_n3(ListNode head, int x) {
     //check
     if(head == null)
       return head;
     
     ListNode smallerHead = new ListNode(Integer.MIN_VALUE);
     ListNode smallerTail = smallerHead;
     ListNode notSmallerHead = new ListNode(Integer.MAX_VALUE);
     ListNode notSmallerTail = notSmallerHead;
     
     ListNode curr = head;
     ListNode next;
     while(curr != null){
       next = curr.next;
       
       if(curr.val < x){ //move it to the small list 
         smallerTail.next = curr;
         smallerTail = curr;
       }else{ // curr.val >= x, move it to the notSmall list  
         notSmallerTail.next = curr;
         notSmallerTail = curr;         
       }  
         
       curr = next;
     }
     
     //merge
     smallerTail.next = notSmallerHead.next;
     head = smallerHead.next;
     notSmallerTail.next = null;
     
     //for GC
     //smallerHead.next = null;
     //notSmallerHead.next = null;
       
     return head;
   }
   
   /* smiliar with partition(ListNode head, int x) */
   public ListNode partition_n(ListNode head, int x) {
     //check
     if(head == null)
       return head;
     
     ListNode smallStart = new ListNode(-1);
     smallStart.next = head;
     ListNode notSmallStart = new ListNode(-1);
     ListNode notSmallEnd = notSmallStart;
     
     ListNode curr = smallStart;
     ListNode next;
     while(null != curr.next){
       next = curr.next;
       
       if(next.val < x){ //leave it in small list
         curr = next;
       }else{ // move it to the notSmall list
         curr.next = next.next;
         
         notSmallEnd.next = next;
         notSmallEnd = next;         
       } 
     }
     
     //merge
     curr.next = notSmallStart.next;
     notSmallEnd.next = null; // **
     
     return smallStart.next;
   }
   
   
   /**
    * 
    * merge sort 
    * 
    * refer to Sorting2.midSplit_mergeSort(int[] arr) and MergeSortedLists.mergeLists(ListNode list1, ListNode list2)
    * 
    * Time O()  Space O(n)
    */ 
   
   public ListNode partition_stack(ListNode head, int x) {
    
    //check
    if(head == null)
      return head;
    
    Stack<ListNode> stack = new Stack<ListNode>();
    stack.add(head);
    ListNode curr = head;   
    ListNode pre = null;
    ListNode big = null, small = null;
    while(curr != null){  
      
      if(pre != null){
        if(pre.val < x){
          if(curr.val < x){  // small - small
            pre = curr;
          }else{  // small - big
            stack.add(pre);
            stack.add(curr);
            
            pre = curr;
          }
          
        }else{
          if(curr.val < x){  // big - small
            /* insert curr  */
            big = stack.pop(); 
            
            if(!stack.isEmpty()){
                small = stack.pop();          
            }
            
            if(small == null){
              /* insert curr before big,  pre.next = curr.next  */ 
              pre.next = curr.next;
              curr.next = big;
              
              head = curr;
              
              stack.add(curr);
              stack.add(big);
              
              curr = pre;
              
            }else{
              /* insert curr before big,  pre.next = curr.next  */ 
              pre.next = curr.next;
              curr.next = big;
              
              small.next =  curr;
              
              stack.add(curr);
              stack.add(big);
              
              curr = pre;
            }
            
          }else  // big - big
            pre = curr;
        }
      }else
        pre = curr;
      
      curr = curr.next;
    }
    
    return head;
  }
  
   /**
    * x = 3;
    * case #1: 4->2---  (4, big and pre; 2, curr )  ==>  2->4---      
    * case #2: 4-->3->2--- (4, big; 3, pre; 2, curr) ==> 2->4-->3---   
    * case #3: 1->4->2--- (1, small; 4, big, pre; 2, curr) ==> 1->2->4---
    * case #4: 1->4-->3->2---(1, small; 4, big; 3, pre; 2, curr) ==> 1->2->4-->3--- 
    * 
    * Time O()  Space O(n)
    */
   public ListNode partition_stack2(ListNode head, int x) {
     
     //check
     if(head == null)
       return head;
     
     Stack<ListNode> stack = new Stack<ListNode>();
     stack.add(head);
     ListNode curr = head;   
     ListNode pre = null;
     ListNode big = null, small = null;
     while(curr != null){  
       if(pre != null){
         if(pre.val < x){
           if(curr.val >= x){// small - big
             stack.add(pre);
             stack.add(curr);
           }
         }else{
           if(curr.val < x){  // big - small
             big = stack.pop(); 
             if(!stack.isEmpty())                 
               small = stack.pop();          

             /* insert curr before big,  pre.next = curr.next  */              
             pre.next = curr.next;
             curr.next = big;
             stack.add(curr);
             stack.add(big);

             if(small == null)
               head = curr;
             else
               small.next =  curr;

             curr = pre;
           }
         }
       }
       
       pre = curr;
       curr = curr.next;
     }
     
     return head;
   } 

   
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    PartitionList sv = new PartitionList();
    
    int[] arr = {1,4,3,2,5,2};
    
    ListNode header = ListNode.buildLL(arr);
    ListNode.printList(header);
    
    ListNode.printList(sv.partition_stack(header, 3));

  }


  
  
  /**
   * @param head: The first node of linked list.
   * @param x: an integer
   * @return: a ListNode 
   */
  public ListNode partition_n4(ListNode head, int x) {
      //check
      if(null == head){
          return head;
      }
      
      LinkedList smaller = new LinkedList();
      LinkedList notSmaller = new LinkedList();

      while(null != head){
          if(head.val < x){
              smaller.add(head);
          }else{
              notSmaller.add(head);
          }
          
          head = head.next;
      }
      
      smaller.tail.next = null;
      notSmaller.tail.next = null;
      
      if(smaller.isEmpty()){
         head = notSmaller.head.next;
      }else{
          head = smaller.head.next;
          smaller.tail.next = notSmaller.head.next;
      }
      
      smaller.destory();
      notSmaller.destory();
      
      return head;
  }
  
  class LinkedList{
      ListNode head;
      ListNode tail;
      
      LinkedList(){
          head = new ListNode(-1);
          tail = head;
          head.next = tail;
      }
      
      boolean isEmpty(){
          return head == tail;
      }
      
      void add(ListNode node){
          tail.next = node;
          tail = node;
      }
      
      void destory(){
          head.next = null;
          head = null;
          tail = null;
      }
  }
}
