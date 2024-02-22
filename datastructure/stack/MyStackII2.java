/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.stack;

import java.util.LinkedList;

/**
 *
 * Implement a stack. You can use any data structure inside a stack except stack itself to implement it.
 * 
 * Example 1:
 * Input:
 * push(1)
 * pop()
 * push(2)
 * top()  // return 2
 * pop()
 * isEmpty() // return true
 * push(3)
 * isEmpty() // return false
 * 
 * 
 * Thoughts:
 * 1 implement it with LinkedList or ArrayList
 * 2 implement it with Node
 * 
 * @author yuanxi
 */
public class MyStackII2 {
    class Node{
        int value;
        Node pre;
        Node next;

        Node(int value){
            this.value = value;
        }
    }

    Node tail = null;

    /*
     * @param x: An integer
     * @return nothing
     */
    public void push(int x) {
        Node curr = new Node(x);

        if(tail == null){
            tail = curr;
        }else {
            tail.next = curr;
            curr.pre = tail;

            tail = tail.next;
        }
    }

    /*
     * @return nothing
     */
    public void pop() {
        if(isEmpty()){
            //throw new IllegalAccessException();
            return;
        }

        Node curr = tail;
        tail = tail.pre;

        curr.pre = null;

        if(tail != null){
            tail.next = null;
        }
        
    }

    /*
     * @return An integer
     */
    public int top() {
        if(isEmpty()){
            //throw new IllegalAccessException();
            System.out.println(" stack is empty. ");
        }

        return tail.value;
    }

    /*
     * @return True if the stack is empty
     */
    public boolean isEmpty() {
        return null == tail;
    }
}
