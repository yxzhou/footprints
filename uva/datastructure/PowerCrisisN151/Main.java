package fgafa.uva.datastructure.PowerCrisisN151;

import java.io.*;
import java.util.*;

class Main
{
 
  class Node{
    int val;  //   
    Node next;   //exmaple 1->2->3->1
    
    Node(int val){
      this.val = val;   
    }
    
    Node(int val, Node pre){
      this.val = val;   
      pre.next = this; 
    }
    
    @Override
    public String toString(){
      return this.val + "," + this.next; 
    }
  }
  
  /* exampale 1->2->3->4->...->13->2 */
  class SingleLinkedLoop{
    Node head = null;
    Node tail = null;
    int initSize = 0;

    public SingleLinkedLoop(int size){
      this.initSize = size; 
      
      this.head = new Node(1);
      tail = head;
      
      for(int i=2; i<= size; i++){        
        tail = new Node(i, tail);
        tail.next = head.next;
      }
    }
        
    void removeNode(Node node) {
      node.val = node.next.val;
      node.next = node.next.next;
    }
    
    /*
     * if n = 13,  m = 1
     * if n > 13,  m >=5,  because  (13-1) = 2p, 3q, 4r.  and m != 13
     * 
     */
    public boolean lastIs13(int m) {
      Node curr = head.next;

      for (int n = this.initSize - 1; n > 1; n--) {
        for (int i = m % n; i > 0; i--)
          curr = curr.next;

        if (curr.val == 13)
          return false;

        removeNode(curr);
      }

      return true;
    }
  }

  
  public static void main(String[] args) throws Exception {
    Main sv = new Main();
    
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    int n, m = 0;
  
    //long start = System.currentTimeMillis();
    try {
      while (in.hasNext()) {        
        //read
        n = in.nextInt();
        m=0;
        
        //exit when it's 0
        if(n==0)
          return;
        
        //main
        if (n > 13) {
          SingleLinkedLoop loop;
          for (m = 4; m < Integer.MAX_VALUE; m++) {         
            loop = sv.new SingleLinkedLoop(n);
            if (loop.lastIs13(m))
              break;
          }
        }
                   
        System.out.println(m+1);
      }
    }
    catch (Exception e) {
      //e.printStackTrace();
    }
    finally {
      in.close();
      //System.out.println(System.currentTimeMillis() - start);
    }
  }
}
