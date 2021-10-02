package linkedlist;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Write a program to find the node at which the intersection of two singly linked lists begins.

    Example
    The following two linked lists:

    A:     a1 → .. → a2
                       ↘
                         c1 → .. → c3
                       ↗            
    B:     b1 → .. → b3
    begin to intersect at node c1.

    Note
        If the two linked lists have no intersection at all, return null.
        The linked lists must retain their original structure after the function returns.
        You may assume there are no cycles anywhere in the entire linked structure.

    Challenge
        Your code should preferably run in O(n) time and use only O(1) memory.
 *
 *  Solution #1, fast catch up to slow 
 *     to the above example, if c3.next = b1, 
 * 
 *                       b3 ← .. ← bi ← ..
 *                      ↙                ↖
 *          a1 → .. → a2                  b1
 *                      ↘               ↗  
 *                        c1 → .. →  c3
 * 
 *     start from a1, p move one node every time, q move two node every time, let assume they meet at bi, 
 *     distance from a1 to a2 is x
 *     distance from a2 to bi is m, 
 *     distance from bi to a2 is n
 *    
 *     so the p moved x + m, q moved x + m + n + m, because q moved twice of p
 *       x + m + n + m = 2 * (x + m)  =>  x == n, 
 *     It means if r move from a1 and p move from bi, move one node every time, they will meet at a2. 
 * 
 *     edge-case, if there is no intersection at all, 
 *       a1 → .. → a2
 *                ↙ 
 *               b1 → .. → b3, 
 *      the slow p and fast q will not meet at bi 
 *    
 *  Solution #2, switch 
    A:          a1 → a2
                           ↘
                             c1 → c2 → c3
                           ↗            
        B:     b1 → b2 → b3
 * 
 *  define d(x, y) as the distance from node x to node y,
 *    d(a1, c3) + d(b1, c1) == d(b1, c3) + d(a1, c1)
 * 
 *  it means if when p move start from a1, q move start from b1, both move one node every time. 
 *  when p moved in c3, let it jump to b1, when q move in c3, let it jump to a1, p and q will meet at c1. 
 * 
 *  edge-case, 
 *     1) if there is no intersection at all, need a counter when to switch . 
 *     2) a1 → .. → a2 → b1 → .. → c1 → .. → c3,  the LCA (a1, b1), 
 *        a1 → .. → a2 → b1 → .. → c1 → .. → c3,  the LCA (b1, c1),  d(b1, c3) + 1 == d(c1, c3) + 1 + d(b1, c1)
 *  
 */

public class Intersection {

    /**
     * Solution #1
     * 
     * case 1, if there is no intersection, 
     * 
     */
    public ListNode getIntersectionNode_FastAndSlow(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        //find tail of headA, connect as a cycle
        ListNode p = headA;
        while (p.next != null) {
            p = p.next;
        }
        p.next = headA;

        ListNode fast = headB;
        ListNode slow = headB;
        ListNode result = null;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) {
                p = headB;
                while (p != slow) {
                    p = p.next;
                    slow = slow.next;
                }

                result = slow;
                break;
            }
        }

        p.next = null;
        
        return result;
    }
    
    /**
     * 
     * Solution #2. 
     * 
     */
    public ListNode getIntersectionNode_switch(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        
        ListNode p = headA;
        ListNode q = headB;
        while(p != q ){
            p = (p.next == null ? headB : p.next);
            q = (q.next == null ? headA : q.next);
        }
        
        return p;
    }
        
    /**
     * 
     * cache path
     */
    public ListNode getIntersectionNode_cache(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        
        Set<ListNode> path = new HashSet<>();
        
        ListNode p = headA;
        while( p != null ){
            path.add(p);
        }
        
        ListNode q = headB;   
        while( q != null ){
            if(path.contains(q)){
                return q;
            }
        }
        
        return null;
    }
            

    public static void main(String[] args) {
        Intersection sv = new Intersection();

        int[][][] x = {
            {{1, 2, 3}, {1, 2, 3}},
            {{1, 2, 3, 4}, {1, 2, 3, 4}},
            {{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}, {7, 8, 9, 10, 11, 12, 13}},
            {{7, 8, 9, 10, 11, 12, 13}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}}
        };

        ListNode[] heads;
        for (int i = 0; i < x.length; i++) {
            heads = sv.build(x[i][0], x[i][1]);

            System.out.println("\nInput: ");
            ListNode.printList(heads[0]);
            ListNode.printList(heads[1]);

            ListNode intersection = sv.getIntersectionNode_FastAndSlow(heads[0], heads[1]);

            System.out.println("  Result: " + (intersection == null ? null : intersection.val));
        }
    }

    private ListNode[] build(int[] A, int[] B) {
        Map<Integer, ListNode> map = new HashMap<>();

        ListNode virtualHeadA = new ListNode(-1);
        ListNode curr = virtualHeadA;
        if (A != null) {
            for (int j : A) {
                curr.next = new ListNode(j);
                curr = curr.next;

                map.put(j, curr);
            }
        }

        ListNode virtualHeadB = new ListNode(-1);
        curr = virtualHeadB;
        if (B != null) {
            for (int j : B) {
                if (map.containsKey(j)) {
                    curr.next = map.get(j);
                } else {
                    curr.next = new ListNode(j);
                    map.put(j, curr.next);
                }

                curr = curr.next;
            }
        }

        return new ListNode[]{virtualHeadA.next, virtualHeadB.next};
    }
}
