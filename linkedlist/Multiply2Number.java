/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkedlist;

/**
 * Given two numbers represented by linked lists, write a function that returns the multiplication of these two linked
 * lists.
 *
 * Example 1:
 * Input：9->4->6->null,8->4->null 
 * Output：79464 
 * Explanation：946*84=79464 Example 2:
 *
 * Input：3->2->1->null,1->2->null 
 * Output：3852 
 * Explanation：321*12=3852
 *
 */
public class Multiply2Number {
    /**
     * @param l1: the first list
     * @param l2: the second list
     * @return: the product list of l1 and l2
     */
    public long multiplyLists(ListNode l1, ListNode l2) {
        if(l1 == null || l2 == null){
            return 0l;
        }

        long num1 = helper(l1);
        long num2 = helper(l2);

        return num1 * num2;
    }

    private long helper(ListNode node){
        long r = 0;

        while(node != null){
            r = r * 10 + node.val;
            node = node.next;
        }

        return r;
    }
}
