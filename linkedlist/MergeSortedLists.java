package fgafa.linkedlist;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 
 * Merge k sorted linked lists and return it as one sorted list. 
 * Analyze and describe its complexity.
 * 
 * @author yxzhou
 *
 */

public class MergeSortedLists
{
  
	/**
	 * 
	 * Merge two sorted linked lists and return it as a new list. The new list
	 * should be made by splicing together the nodes of the first two lists.
	 * 
	 * Time O(2n)
	 */
	public ListNode mergeTwoLists_X(ListNode l1, ListNode l2) {
		if(null == l1)
			return l2;
		else if(null == l2)
			return l1;
		
		if(l1.val > l2.val)
			return mergeTwoLists_X(l2, l1);
		
		ListNode header = l1;
		ListNode pre = l1;
		ListNode curr = pre.next;
		ListNode newNode = l2;
		while( null != curr && null != newNode){
			if(curr.val > newNode.val){//insert
				pre.next = newNode;
				pre = newNode;
				newNode = newNode.next;
				pre.next = curr;
			}else{
				pre = curr;
				curr = curr.next;
			}
		}
		
		if(null != newNode){
			pre.next = newNode;
		}
			
		return header;
	}
	
    public ListNode mergeTwoLists_n(ListNode l1, ListNode l2) {
        //check
        if(null == l1){
            return l2;
        }
        if(null == l2){
            return l1;
        }
        
        ListNode newHead;
        ListNode other;
        if(l1.val < l2.val){
            newHead = l1;
            other = l2;
        }else{
            newHead = l2;
            other = l1;
        }
        
        ListNode curr = newHead;
        ListNode otherNext;
        while(null != curr.next && null != other){
            if(curr.next.val > other.val){
                //insert the node "other" after curr
                otherNext = other.next;
                
                other.next = curr.next;
                curr.next = other;
                
                other = otherNext;
            }
                
            curr = curr.next;
        }
        
        if(null == curr.next){
            curr.next = other;
        }
        
        return newHead;
    }
	
 /* merge 2 list */
  public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    ListNode result = list1;

    // check
    if (list2 == null)
      return result;

    ListNode curr = result, pre = null;
    ListNode newNode = list2;
    int tmpValue;
    while (newNode != null && curr != null) {

      // while(){

      if (newNode.val > curr.val) {

        pre = curr;
        curr = pre.next;

      }
      else {
        /*
         * add newNode between pre and curr. When pre is null, add newNode after
         * curr with switching their value
         */
        if (pre == null) {
          tmpValue = curr.val;
          curr.val = newNode.val;
          newNode.val = tmpValue;

          pre = curr;
          curr = pre.next;
        }
        pre.next = newNode;
        newNode = newNode.next;
        pre.next.next = curr;

        curr = pre.next;
      }
    }

    if (newNode != null) { // && curr == null
      pre.next = newNode;
    }

    return result;
  }
  
  
  private static ListNode createList(int[] arr){
    ListNode result = null;
    
    if(arr == null || arr.length == 0)
      return result;
    else
      result = new ListNode(arr[0]);
    
    ListNode curr = result;
    
    for(int i = 1; i< arr.length; i++){
       ListNode tmp = new ListNode(arr[i]);
       
       curr.next = tmp;
       curr = tmp;
    }
    
    return result;
  }
  
  /*
   * Suppose there are m sorted linked list, everyone has n node.
   * 
   * Solution #1, 
   *  1 result = list[1]
   *  2 for(i = 2; i<m; i++)
   *      merge list[i] into result. 
   * 
   * Time O(k*n*n), Space O(1)
   */  
   public ListNode mergeKLists(List<ListNode> lists) {
       if(null == lists )
           return null;
       
       ListNode ret = null;
       for(int i=0; i<lists.size(); i++){
           ret = mergeTwoLists_X(ret, lists.get(i));
       }
       
       return ret;
   }
   
   /**
    * Time O( n*k* logk )  k lists, every list has n nodes
    */
     public ListNode mergeKLists_heap(List<ListNode> lists) {
         if (null == lists || 0 == lists.size())
             return null;

         // PriorityQueue is a sorted queue
         PriorityQueue<ListNode> minHeap = new PriorityQueue<ListNode>(lists.size(),
                 new Comparator<ListNode>() {
                     public int compare(ListNode a, ListNode b) {
                         return a.val - b.val;
                     }
                 });

         // add first node of each list to the queue
         for (ListNode list : lists) {
             if (list != null){
                 minHeap.add(list);
             }
         }

         ListNode dummy = new ListNode(0);
         ListNode tail = dummy; // serve as a pointer/cursor
         while (!minHeap.isEmpty()) {
             tail.next = (ListNode)minHeap.poll();
             tail = tail.next;
             
             if(null != tail.next){
                 minHeap.add(tail.next);
             }
         }

         return dummy.next;
     }

    public ListNode mergeKLists_heap_n(List<ListNode> lists) {
        if (null == lists || 0 == lists.size()){
            return null;
        }

        // PriorityQueue is a sorted queue
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(lists.size(),
                (n1, n2)-> Integer.compare(n1.val, n2.val));

        // add first node of each list to the queue
        for (ListNode list : lists) {
            if (list != null){
                minHeap.add(list);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy; // serve as a pointer/cursor
        ListNode min;
        while (!minHeap.isEmpty()) {
            min = (ListNode)minHeap.poll();

            tail.next = new ListNode(min.val);
            tail = tail.next;

            if(null != min.next){
                minHeap.add(min.next);
            }
        }

        return dummy.next;
    }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
      int[][] arr = //{{1},{0}};
              //{{},{-1,5,11},{},{6,10}};
              {{-5, -2, 0, 1, 1, 2}, {-7, -6, -3}, {-8, -7, -4, -4, 0, 2, 3, 4}, {}};

      ArrayList<ListNode> lists = new ArrayList<ListNode>();
      for (int i = 0; i < arr.length; i++) {
          lists.add(createList(arr[i]));
      }

      MergeSortedLists sv = new MergeSortedLists();

      ListNode result2 = sv.mergeKLists_heap_n(lists);
      ListNode result = sv.mergeKLists_heap(lists);

      while (result != null) {
          System.out.print(result.val);
          System.out.print("->");

          result = result.next;
      }

      System.out.println("");
      while (result2 != null) {
          System.out.print(result2.val);
          System.out.print("->");

          result2 = result2.next;
      }


  }

}
