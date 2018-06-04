package fgafa.linkedlist;


/**
 * 
 * Given a singly linked list, group all odd nodes together followed by the even nodes. Please note here we are talking about the node number and not the value in the nodes.

    You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.
    
    Example:
    Given 1->2->3->4->5->NULL,
    return 1->3->5->2->4->NULL.
    
    Note:
    The relative order inside both the even and odd groups should remain as it was in the input. 
    The first node is considered odd, the second node even and so on ...
 *
 */

public class OddEvenLinkedList {

    public ListNode oddEvenList(ListNode head) {
        if(null == head){
            return head;
        }
        
        ListNode oddHead = new ListNode(1);
        ListNode evenHead = new ListNode(2);
        ListNode oddTail = oddHead;
        ListNode evenTail = evenHead;
        ListNode curr = head;
        ListNode next;
        for(int i = 1; null != curr; i++){
            next = curr.next;
            curr.next = null;
            
            if((i & 1) == 1){
                oddTail.next = curr;
                oddTail = curr;
            }else{
                evenTail.next = curr;
                evenTail = curr;
            }
            
            curr = next;
        }
          
        oddTail.next = evenHead.next;
        return oddHead.next;
    }
    
    public ListNode oddEvenList_n(ListNode head) {
        if(null == head){
            return head;
        }
        
        ListNode evenHead = new ListNode(2);
        ListNode oddTail = head;
        ListNode evenTail = evenHead;

        while(null != oddTail.next && null != oddTail.next.next){
            evenTail.next = oddTail.next;
            oddTail.next = oddTail.next.next;
           
            evenTail = evenTail.next;
            oddTail = oddTail.next;
        }
          
        evenTail.next = oddTail.next;
        oddTail.next = evenHead.next;
        return head;
    }
    
}
