/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.queue;

/**
 *
 * @author yuanxi
 */
public class MyQueueII2 {
    class Node{
        int value;
        Node next;

        Node(int value){
            this.value = value;
        }
    }

    Node head = null; 
    Node tail = null;

    //int size = 0;

    /*
     * @param item: An integer
     * @return: nothing
     */
    public void enqueue(int item) {

        if(head == null){
            head = new Node(item);
            tail = head;
        }else{
            tail.next = new Node(item);
            tail = tail.next;
        }
    }

    /*
     * @return: An integer
     */
    public int dequeue() {
        if( head == null ){    
            return -1;
        }

        Node first = head;
        head = first.next;
        first.next = null;

        return first.value;
    }
}
