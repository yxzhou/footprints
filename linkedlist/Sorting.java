package fgafa.linkedlist;

import fgafa.util.Misc;

public class Sorting // extends ListNode
{

  public ListNode quickSort(ListNode header) {
    if (header == null || header.next == null)
      return header;

    ListNode virtualHeader = new ListNode(-1);
    virtualHeader.next = header;
    quickSort(virtualHeader, null);

    return virtualHeader.next;
  }



  /* (), the sort exclude the left and right */
  private void quickSort(ListNode left, ListNode right) {
    if (left == right || left.next== right || left.next.next == right)
      return;

    ListNode pivot = partition(left, right);

    quickSort(left, pivot); // ( ), exclude start and pivot
    quickSort(pivot, right); //
  }



  /*
   * pick the first valid one as the pivot, insert the smaller after left, insert
   * the bigger after pivot
   */
  private ListNode partition(ListNode left, ListNode right) {
    ListNode pivot = left.next; // pick the first valid one as the pivot
    ListNode smaller = left, bigger = pivot;
    for (ListNode curr = pivot.next, next; curr != right; curr = next) {
      next = curr.next;
      if (curr.val < pivot.val){         
        smaller.next = curr;
        smaller = smaller.next;
      }else{
        bigger.next = curr;
        bigger = bigger.next;
      }  
    }

    smaller.next = pivot;
    bigger.next = right;
        
    return pivot;
  }



  // private void swap(Node n1, Node n2){
  // int tmp = n1.val;
  // n1.val = n2.val;
  // n2.val = tmp;
  // }

  public ListNode mergeSort(ListNode header) {
    if (header == null || header.next == null)
      return header;

    ListNode virtualHeader = new ListNode(-1);
    virtualHeader.next = header;
    mergeSort(virtualHeader, null);

    return virtualHeader.next;
  }

  /* (), exclude the left and the right */
  private void mergeSort(ListNode left, ListNode right) {
    if (left == right || left.next == right || left.next.next == right)
      return;

    //find middle
    ListNode slower = left.next, faster = slower;
    while (faster != right && faster.next != right && faster.next.next != right) {
      slower = slower.next;
      faster = faster.next.next;
    }

    //sort the first and second half
    mergeSort(left, slower.next); //(), exclude the left and the right
    mergeSort(slower, right);

    //merge 
    merge(left, slower.next, slower, right);
  }


  private void merge(ListNode left1, ListNode right1, ListNode left2, ListNode right2) {
    ListNode curr1 = left1.next;
    ListNode curr2 = left2.next;
    ListNode tail = left1;

    while(curr1 != right1 && curr2 != right2){
      if(curr1.val < curr2.val){
        tail.next = curr1;
        curr1 = curr1.next;
      }else{ 
        tail.next = curr2;
        curr2 = curr2.next;
      }   
      
      tail = tail.next;
    }
      
    ListNode remain = (curr1 != right1) ? curr1 : curr2;
    ListNode end = (curr1 != right1) ? right1 : right2;
    
    while(remain != end){
      tail.next = remain;
      
      remain = remain.next;
      tail = tail.next;
    }
      
    tail.next = right2;
  }

  /**
   * Sort a linked list using insertion sort.
   * @param head
   * @return
   */
  public ListNode insertionSortList(ListNode head) {
      if(null == head)
          return null;
          
      ListNode tail = head;
      ListNode next = tail.next;
      ListNode cursor;
      int tmp;
      while(tail.next != null){
          cursor = head;
          next = tail.next;

          while(cursor != next){
              if( next.val < cursor.val ){
                  //insert after cursor,  swap value
                  tail.next = next.next;
                  next.next = cursor.next;
                  cursor.next = next;
                  
                  tmp = cursor.val;
                  cursor.val = next.val;
                  next.val = tmp;
                     
                  break;
              }else 
                 cursor = cursor.next;
          }
          
          if(cursor == next && tail.next != null){
              tail = tail.next;
          }
      }
      
      return head;
  }
  
  
  public ListNode insertionSortList_n(ListNode head) {
      //check
//      if(null == head){
//          return head;
//      }
      
      ListNode tail = head;

      ListNode cursor;
      ListNode curr;
      int value;
      while(null != tail && null != tail.next){
          curr = tail.next;
          
          cursor = head;
          while(cursor != curr ){
              
              if(cursor.val > curr.val){
                  //insert curr after cursor, then swap
            	  tail.next = curr.next;  //note
                  curr.next = cursor.next;
                  cursor.next = curr;
                  
                  value = cursor.val;
                  cursor.val = curr.val;
                  curr.val = value;
                  
                  break;
              }
              
              cursor = cursor.next;
          }
          
          if(curr == tail.next){
        	  tail = curr;
          }
      }
      
      return head;
  }
  /**
   * @param args
   */
  public static void main(String[] args) {
    Sorting sv = new Sorting();

    // init
    int[][] n = {null, {}, {1}, {1, 2}, {2, 1}, {2, 1, 5, 3, 6, 2, 11, 7, 9, 6}};

    // main

    for (int i = 0; i < n.length; i++) {
      System.out.print("\n the original array: \t" + Misc.array2String(n[i]));

      ListNode header = ListNode.buildLL(n[i]);
      
      System.out.print("\n the original list : \t");
      ListNode.printList(header);
      
//      header = sv.quickSort(header);
//
//      System.out.print("\n the new list : \t");
//      sv.printList(header);

      header = sv.mergeSort(header);

      System.out.print("\n the new list : \t");
      ListNode.printList(header);

    }

  }
}
