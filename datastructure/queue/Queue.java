package datastructure.queue;

public class Queue<V> 
{
    
    class Node<V>{
        V value;
        Node<V> next = null;
        
        Node(V value){
            this.value = value;
        }
    }
    
    private Node<V> header;
    private Node<V> tail;
    
    public Queue(){
        init();
    }
    
    private void init(){
        header = new Node<V>(null);
        tail = header;
    }
    
    // Push element x to the back of queue.
    public void push(V x) {
        Node<V> node = new Node<V>(x);
        
        tail.next = node; 
        tail = node;
    }

    // Removes the element from in front of queue.
    public void pop() {
        
        if(!empty()){
            header.next = header.next.next;
        }
    }

    // Get the front element.
    public V peek() {

        if(!empty()){
            return header.next.value;
        }
        
        return null;
    }
    
    // Return whether the queue is empty.
    public boolean empty() {
        return header == tail;
    }
    

    
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
