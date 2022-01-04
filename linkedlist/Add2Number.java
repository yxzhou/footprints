package linkedlist;

/**
 * _https://leetcode.com/problems/add-two-numbers/
 *
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Example
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * 
 * Given 7->1->6 + 5->9->2. That is, 617 + 295.
 * Return 2->1->9. That is 912.
 * 
 * Given 3->1->5 and 5->9->2, return 8->0->8.
 *
 */
/**
 * case 1: l1 and l2 both are null, return null;
 * case 2: l1 and l2, either is null, return the other
 * case 3: create all new node, no change on the old ones.
 *     (2 4 3) + (5 6 4) => (7 0 8)
 *     (1) + (2) => (3)
 *     (6) + (7) => (3 1)
 *     (0) + (0 1) => (0 1)
 *     (1) + (9 9) => (0 0 1)
 *     (9) + (9 9) => (8 0 1)
 *         
 */
public class Add2Number {
  
    /**
     * @param l1: the first list
     * @param l2: the second list
     * @return the sum list of l1 and l2
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);

        ListNode curr = dummy;
        int sum = 0;
        while (l1 != null || l2 != null) {
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            curr.next = new ListNode(sum % 10);
            sum /= 10;
            curr = curr.next;
        }

        if (sum > 0) {
            curr.next = new ListNode(sum);
        }

        return dummy.next;
    }

    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Add2Number sv = new Add2Number();

        int[] a = {243, 578};
        int[] b = {564, 632};

        for (int i = 0; i < a.length; i++) {
            ListNode aLN = sv.initNodeList(a[i]);
            ListNode bLN = sv.initNodeList(b[i]);

            System.out.print("\nInput a: " + a[i] + "\t");
            sv.printNodeList(aLN);
            System.out.print("\nInput b: " + b[i] + "\t");
            sv.printNodeList(bLN);

            System.out.print("\nOutput : \t");
            ListNode result = sv.addTwoNumbers(aLN, bLN);
            sv.printNodeList(result);

        }

    }
  
    /*
   * int 243 ==> list nodes 3->4->2->null 
   * 
     */
    private ListNode initNodeList(int num) {
        ListNode preN = null;
        ListNode curN = null;
        ListNode head = null;

        int digit = 0;
        int factor = 10;
        while (num > 0) {
            digit = num % factor;
            num = num / factor;

            curN = new ListNode(digit);

            if (preN == null) {
                head = curN;
            } else {
                preN.next = curN;
            }

            preN = curN;

        }

        return head;
    }

    private void printNodeList(ListNode head) {
        while (head != null) {
            System.out.print(head.val);
            System.out.print("->");
            head = head.next;
        }
    }
  
}
