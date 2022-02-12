package datastructure.queue;

import java.util.NoSuchElementException;

interface InterfaceQueue<V> {
    void push(V element);
    V pop();
    V top();
}

public class Queue<V> implements InterfaceQueue<V> {

    
    class Node<V>{
        V value;
        
        Node<V> next = null;
        
        Node(V value){
            this.value = value;
        }
    }
    
    private final Node<V> header;
    private Node<V> tail;
    
    public Queue(){
        header = new Node<>(null);
        tail = header;
    }
    
    // Push element x to the back of queue.
    @Override
    public void push(V x) {
        Node<V> node = new Node<>(x);
        
        tail.next = node; 
        tail = node;
    }

    // Removes the element from in front of queue.
    @Override
    public V pop() {
        
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        
        Node<V> curr = header.next;
        header.next = curr.next;
        
        if(tail == curr){
            tail = header;
        }
        
        return curr.value;
    }

    // Get the front element.
    @Override
    public V top() {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        
        return header.next.value;
    }
    
    // Return whether the queue is empty.
    public boolean isEmpty() {
        //return header == tail;
        return header.next == null;
    }
    
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
