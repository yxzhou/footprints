package linkedlist;

import tree.TreeNode;
import util.Misc;

/**
 * Given a node from a cyclic linked list which has been sorted, 
 * write a function to insert a value into the list such that it remains a cyclic sorted list. 
 * The given node can be any single node in the list.
 * 
 * If the cyclic means {0,1,2,0,1,2,--}
 * e.g. 
 * 
 * null,   1    =>   Result: 1,           //case 1, add the new Node in a empty list
 * {1},    0    =>   Result: 0, 1,        
 * {1},    2    =>   Result: 1, 2,       
 * {1,3},  0    =>   Result: 0, 1, 3,     //case 2, add the new Node at the front of the list, replace the header.
 * {1,3},  2    =>   Result: 1, 2, 3,    
 * {1,3},  4    =>   Result: 1, 3, 4,     //case 3, add the new Node at the end of the list
 * {1,3,5}, 0   =>   Result: 0, 1, 3, 5, 
 * {1,3,5}, 2   =>   Result: 1, 2, 3, 5, 
 * {1,3,5}, 4   =>   Result: 1, 3, 4, 5, 
 * {1,3,5}, 6   =>   Result: 1, 3, 5, 6, 
 * {3,1}, 0     =>   Result: 3, 1, 0,    
 * {3,1}, 2     =>   Result: 3, 2, 1,    
 * {3,1}, 4     =>   Result: 4, 3, 1,    
 * {5,3,1}, 0   =>   Result: 5, 3, 1, 0, 
 * {5,3,1}, 2   =>   Result: 5, 3, 2, 1, 
 * {5,3,1}, 4   =>   Result: 5, 4, 3, 1, 
 * {5,3,1}, 6   =>   Result: 6, 5, 3, 1, 
 * {2,2,2}, 0  
 * {2,2,2}, 0  
 * {2,2,2}, 0
 *     
 */

public class InsertNodeByOrder
{

    public ListNode insert(ListNode head, int x) {
        ListNode newNode = new ListNode(x);
        if (head == null) {
            newNode.next = newNode;
            return newNode;
        }

        ListNode curr = head;
        ListNode next;
        while (true) { // for case: all elements of the array are same
            next = curr.next;

            if (next == head) {
                break;
            }

            if ((curr.val <= x && x <= next.val) || (curr.val >= x && x >= next.val)) { // = is special for case: x is equal to a element in the array
                break;
            }

            curr = next;
        }

        // insert x after the curr node
        newNode.next = next;
        curr.next = newNode;

        if ((next.val < curr.val && x < next.val) || (next.val > curr.val && x > next.val)) {
            return newNode;
        } else {
            return head;
        }
    }
 
  
  public static void main(String[] args) {
    System.out.println("------------Start-------------- " );
    InsertNodeByOrder sv = new InsertNodeByOrder(); 
   
    //init a LinkedList
    int[][] n = {   null, 
                    {1}, 
                    {1,3},  
                    {1,3,5}, 
                    {3,1}, 
                    {5,3,1}, 
                    {2,2,2}
                 };
    int[][] x = {   {1}, 
                    {0, 2}, 
                    {0, 2, 4}, 
                    {0, 2, 4, 6}, 
                    {0, 2, 4}, 
                    {0, 2, 4, 6}, 
                    {0, 1, 2, 3}
               };
    
    ListNode head;
    for(int i = 0; i< n.length; i++){
      System.out.println("\nInput: "+ Misc.array2String(n[i]) + "\t ---x: " + Misc.array2String(x[i])); 
           
      head = ListNode.buildLL(n[i], true);

      for(int j = 0; j < x[i].length; j++){
          head = sv.insert(head, x[i][j]);
          
          System.out.println(ListNode.toString(head)); 
      }
      

    }

    System.out.println("------------End-------------- " );

  }

  
}
