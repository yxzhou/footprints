package fgafa.datastructure.stack;

import java.util.EmptyStackException;

public class Stack<V>
{

  class Node<V>{
      V value;
      Node<V> next;
      
      Node(V value){
          this.value = value;
      }
  }
    
  Node<V> header;
  
  private void init(V value){
      header = new Node<V>(null);
  }
  
  // Push element x onto stack.
  public void push(V x) {
      
      Node<V> node = new Node<V>(x);
      node.next = header.next;
      header.next = node;
      
  }

  // Removes the element on top of the stack.
  public void pop() {
      if (!empty()) {
          header.next = header.next.next;
      }
  }

  // Get the top element.
  public V top() {
      if (!empty()) {
          return header.next.value;
      }

      return null;
  }

  
  // Return whether the stack is empty.
  public boolean empty() {
      return header.next == null;
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
