/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkedlist;

import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/904
 * 
 * Related Problem 
 *   math.AddOne
 * 
 * Given a non-negative integer represented as non-empty a singly linked list of digits, plus one to the integer.
 *
 * You may assume the integer do not contain any leading zero, except the number 0 itself.
 *
 * The digits are stored such that the most significant digit is at the head of the list.
 * 
 * Example1
 * Input: 1 -> 2 -> 3 -> null
 * Output: 1 -> 2 -> 4 -> null
 * Explanation: 123 + 1 = 124
 * 
 * Example2
 * Input: 9 -> 9 -> null
 * Output: 1 -> 0 -> 0 -> null
 * Explanation: 99 + 1 = 100
 * 
 * Thoughts:
 *  m1) recursive,  
 *  m2) two points
 */
public class AddOne {
    /**
     * @param head: the first Node
     * @return the answer after plus one
     */
    public ListNode plusOne(ListNode head) {  
        int carry = helper(head);

        ListNode result = head;
        if(carry > 0){
            result = new ListNode(carry);
            result.next = head;
        }

        return result;
    }

    private int helper(ListNode node){
        if(node == null){
            return 1;
        }
        
        int sum = node.val + helper(node.next);

        node.val = sum % 10;
        return sum / 10;
    }
    
    /**
     * @param head: the first Node
     * @return the answer after plus one
     */
    public ListNode plusOne_m2(ListNode head) {
        ListNode pre9 = head;
        ListNode curr = head;
        while(curr.next != null){
            if(curr.val != 9 && curr.next.val == 9){
                pre9 = curr;
            }
            
            curr = curr.next;
        }
        
        if(curr.val < 9){
            curr.val++;
        }else{
            if(pre9.val == 9){
                pre9 = new ListNode(0);
                pre9.next = head;
                head = pre9;
            }
            
            pre9.val++;
            curr = pre9.next;
            while(curr != null){
                curr.val = 0;
                curr = curr.next;
            }
        }
        
        return head;
    }
    
    public ListNode plusOne_m2_n(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre9 = dummy;
        ListNode curr = head;
        while(curr != null){
            if(curr.val != 9){
                pre9 = curr;
            }
            
            curr = curr.next;
        }
        
        pre9.val++;
        curr = pre9.next;
        while(curr != null){
            curr.val = 0;
            curr = curr.next;
        }
        
        return dummy.val == 0 ? dummy.next : dummy;
    }
    
    public static void main(String[] args){
        int[][][] inputs = {
            { {1,2,3}, {1,2,4} },
            { {8,9,9,9}, {9,0,0,0} },
            { {9,9,9}, {1,0,0,0} }
        };
        
        AddOne sv = new AddOne();
        
        for(int[][] input : inputs){
            System.out.println(String.format("Input: %s", Misc.array2String(input[0]) ));
            
            Assert.assertEquals(ListNode.toString(ListNode.buildLL(input[1])), ListNode.toString(sv.plusOne(ListNode.buildLL(input[0])) ));
            
            Assert.assertEquals(ListNode.toString(ListNode.buildLL(input[1])), ListNode.toString(sv.plusOne_m2(ListNode.buildLL(input[0])) ));
            Assert.assertEquals(ListNode.toString(ListNode.buildLL(input[1])), ListNode.toString(sv.plusOne_m2_n(ListNode.buildLL(input[0])) ));
 
        }
        
    }
    
}
