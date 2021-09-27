package linkedlist;

import java.util.Stack;

/**
 *
 * You are given two non-empty linked lists representing two non-negative integers. The most significant digit comes first and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Example 1:
 * Input: l1 = [7,2,4,3], l2 = [5,6,4]
 * Output: [7,8,0,7]
 *
 * Example 2:
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [8,0,7]
 *
 * Example 3:
 * Input: l1 = [0], l2 = [0]
 * Output: [0]
 *
 * Constraints:
 * The number of nodes in each linked list is in the range [1, 100].
 * 0 <= Node.val <= 9
 * It is guaranteed that the list represents a number that does not have leading zeros.
 *
 *
 * Follow up: Could you solve it without reversing the input lists?
 *
 *
 * Solution #1, reverse the input with Stack.
 *
 * Solution #2, special for the follow up, use recursive instead of reversing the input lists
 *    e.g.
 *       l1,  7 - 2 - 4 - 3
 *       l2,      5 - 6 - 4
 *
 *   result,  a - b - c - d
 *            7 - 8 - 0 - 7
 *
 *       init a = node(7)               --a
 *       helper(a, node(2), node(5))
 *           a.next = new ListNode(2 + 5)   -- b
 *           helper(b, node(4), node(6))
 *               b.next = new ListNode(4 + 6)   -- c
 *               helper(c, node(3), node(4))
 *                   c.next = new ListNode(3 + 4)   -- d
 *                   helper(d, null, null)
 *                       found 'null',return
 *
 *                   int v = d.val          -- v = 7
 *                   c.val += v / 10;       -- c.val = 10 + 7 / 10 = 10
 *                   c.next.val = v % 10;   -- d.val = 7 % 10 = 7
 *
 *               int v = c.val              -- v = 10
 *               b.val += v / 10            -- b.val = 7 + 10 / 10 = 8
 *               b.next.val = v % 10;       -- c.val = 10 % 10 = 0
 *
 *           int v = b.val                  -- v = 8
 *           a.val +=  v / 10               -- a.val = 7 + 8 / 10
 *           a.next.val = v % 10            -- b.val = 8 % 10 = 8
 *
 *
 *
 */


public class Add2NumberII {


    class ListNode{
        int val;  // value
        ListNode next;

        ListNode(){ }
        ListNode(int x){
            this(x, null);
        }
        ListNode(int x, ListNode node){
            val = x;
            next = node;
        }

        public String toString(){
            return String.valueOf(this.val);
        }
    }


    public ListNode addTwoNumbers_stack(ListNode l1, ListNode l2) {
        Stack<Integer> s1 = build(l1);
        Stack<Integer> s2 = build(l2);

        ListNode head = new ListNode();
        ListNode curr;

        int sum = 0;
        while( !s1.isEmpty() || !s2.isEmpty() ){
            if(!s1.isEmpty()){
                sum += s1.pop();
            }

            if(!s2.isEmpty()){
                sum += s2.pop();
            }

            curr = new ListNode(sum % 10);
            curr.next = head.next;
            head.next = curr;

            sum /= 10;
        }

        if(sum > 0){
            curr = new ListNode(sum);
            curr.next = head.next;
            head.next = curr;
        }

        return head.next;
    }

    private Stack<Integer> build(ListNode l){
        Stack<Integer> result = new Stack<>();

        while(l != null){
            result.add(l.val);
            l = l.next;
        }

        return result;
    }

    public ListNode addTwoNumbers_recursive(ListNode l1, ListNode l2) {
        // align l1 and l2 on the right side
        ListNode s = l1;
        ListNode l = l2;

        while(s.next != null && l.next != null ){
            s = s.next;
            l = l.next;
        }

        ListNode r = l;  //remained
        if(s.next == null){
            s = l1;
            l = l2;
        } else { //l.next == null
            r = s;
            s = l2;
            l = l1;
        }

        //recursive addition
        ListNode dummy = new ListNode(0);

        helper(dummy, s, l, r);

        return dummy.val == 0 ? dummy.next : dummy;
    }

    private void helper(ListNode result, ListNode s, ListNode l, ListNode r ){
         if(r.next != null){
            result.next = new ListNode(l.val);

            helper(result.next, s, l.next, r.next);
        }else{
            if(s == null || l == null){
                return;
            }

            result.next = new ListNode(s.val + l.val);

            helper(result.next, s.next, l.next, r);
        }

        result.val += result.next.val / 10;
        result.next.val %= 10;
    }

}
