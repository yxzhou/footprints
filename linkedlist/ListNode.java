package linkedlist;

import java.util.List;

public class ListNode {

    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
        next = null;
    }

    public static ListNode buildLL(List<Integer> arr) {
        if (arr == null || arr.size() == 0)
            return null;

        ListNode virtualHead = new ListNode(-1);
        ListNode curr = virtualHead;
        for (int j = 0; j < arr.size(); j++, curr = curr.next)
            curr.next = new ListNode(arr.get(j));

        return virtualHead.next;
    }

    // return 1->-- ->n
    public static ListNode buildLL(int n) {
        int[] arr = new int[n];
        for(int i = 0; i < n; ){
            arr[i] = ++i;
        }
        
        return buildLL(arr);
    }
    
    public static ListNode buildLL(int[] arr) {
        return buildLL(arr, false);
    }
    
    public static ListNode buildLL(int[] arr, boolean isCircle) {
        if (arr == null || arr.length == 0)
            return null;

        ListNode virtualHead = new ListNode(-1);
        ListNode curr = virtualHead;
        for (int j = 0; j < arr.length; j++, curr = curr.next){
            curr.next = new ListNode(arr[j]);
        }

        if(isCircle){
            curr.next = virtualHead.next;
        }
        
        return virtualHead.next;
    }

    public static void printList(ListNode node) {
        System.out.println(toString(node));
    }
    
    public static String toString(ListNode node) {
        StringBuffer sb = new StringBuffer();
        
        if(null == node){
            return sb.toString();
        }
        
        ListNode curr = node;
        
        do {
            sb.append(curr.val);
            sb.append("->");

            curr = curr.next;
        }while (curr != null && curr != node);
        
        return sb.toString();
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
