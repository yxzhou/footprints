/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.queue;

/**
 * 
 * Implement a Queue by linked list. Support the following basic methods:
 * 
 * enqueue(item). Put a new item in the queue.
 * dequeue(). Move the first item out of the queue, return it. If the queue is empty, returned. -1.、
 *
 * Example 1:
 * Input:
 * enqueue(1)
 * enqueue(2)
 * enqueue(3)
 * dequeue() // return 1
 * enqueue(4)
 * dequeue() // return 2
 * 
 * @author yuanxi
 */
public class MyQueueII {
    class Node{
        int value;
        Node next = null;
        
        Node(int value){
            this.value = value;
        }
    }
    
    Node head = new Node(-1); // a dummy node
    Node tail = head;
    
    /*
     * @param item: An integer
     * @return: nothing
     */
    public void enqueue(int item) {
        tail.next = new Node(item);
        tail = tail.next;
    }
    
    /*
     * @return: An integer
     */
    public int dequeue() {
        if(head.next == null){
            return -1;
        }
        
        Node first = head.next;
        head.next = first.next;
        first.next = null;
        
        if(head.next == null){ //forcase the tail is dequeued;
            tail = head;
        }
        
        return first.value;
    }
}
