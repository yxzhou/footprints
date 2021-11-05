package linkedlist;

/**
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
public class ReverseNodeInKGroup{

    public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null || k < 2){
            return head;
        }

        ListNode dummy = new ListNode(-1);
        
        ListNode tail = dummy;
        ListNode left = head;
        ListNode right = head;
        ListNode next;
        ListNode newTail;
        int i;
        while(right != null){

            for( i = 0; i < k && right != null; i++){
                right = right.next;
            }

            if(i == k){
                newTail = left;
                
                while(left != right){
                    next = left.next;

                    left.next = tail.next;
                    tail.next = left;

                    left = next; 
                }

                tail = newTail;
            }else{
                tail.next = left;
            }

        }

        return dummy.next;
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
        for (n = 2; n < 6; n++) {
            head = ListNode.buildLL(n);

            //print the original Node list.
            System.out.print("\nthe Node list is: ");
            ListNode.printList(head);

            for (int i = 2; i < n + 2; i++) {

                head = sv.reverseKGroup(head, i);

                System.out.print("\nreverse by group " + i + " :");
                ListNode.printList(head);

                head = ListNode.buildLL(n);
            }
        }

    }
  
  
  
}
