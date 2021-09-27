package uva.datastructure.DoleQueueN133;

import java.io.*;
import java.util.*;

class Main
{
 
  /* double linked list */
  class Node{
    int val;  //   
 
    Node next;   //counter-clockwise
    Node pre ;   //clockwise  
    
    Node(int val, Node pre, Node next){
      this.val = val;
      
      this.pre = pre;     
      this.next = next;   //Starting from 1 and moving counter-clockwise
      
      if(pre != null)
        pre.next = this;
      if(next != null)
        next.pre = this;
    }
    
    public String toString(){
      return this.pre + "," + this.val + "," + this.next; 
    }
  }
  
  public String calDoleQueue(int n, int k, int m){

    //init a double linked list
    Node cw = new Node(1, null, null);
    cw.pre = cw;      // the start of clockwise
    cw.next = cw;
    Node ccw = cw;   // the start of counter-clockwise 
    
    for(int i=2; i<n+1; i++){
      cw = new Node(i, cw, ccw);
    }
    k--;
    m--;
    
    StringBuilder output = new StringBuilder();
    while(n > 0){
      /* one move k by counter-clockwise, the other move m by clockwise */
      for(int i= k%n ; i>0; i--)
        ccw = ccw.next;
      for(int i= m%n; i>0; i--)
        cw = cw.pre;      
      
      /* remove the selected person from the double linked list 
       * if both officials pick the same person, count add 1, or add 2 */
      if(ccw == cw){  // remove one person
        outputNode(output, ccw.val);

        if(n == 1)
          break;
        
        ccw = ccw.next;
        cw = cw.pre;
        
        removeNode(ccw.pre);
        n --;
      }else if(ccw.next == cw){  // remove the adjoining 2 person
        outputNodes(output, ccw.val, cw.val);
        
        if(n == 2)
          break;
        
        cw = ccw.pre;
        ccw = ccw.next.next;
        
        removeNode(cw.next);
        removeNode(ccw.pre);
        n -= 2;
      }else{  // remove the not adjoining 2 person
        outputNodes(output, ccw.val, cw.val);
        
        if(n == 2)
          break;
        
        ccw = ccw.next;
        cw = cw.pre;
        
        removeNode(cw.next);
        removeNode(ccw.pre);
        n -= 2;;
      }
      
    }
        
    return output.substring(0, output.length() - 1);
  }
  
  private void removeNode(Node node){
    Node pre = node.pre;
    Node next = node.next;
    
    if(pre != null )
      pre.next = next;
    if(next != null)
      next.pre = pre;
    
    node.next = null;
    node.pre = null;
  }

  private void outputNode(StringBuilder output, int x){
    appendNode(output, x);
    output.append(',');
  }

  private void outputNodes(StringBuilder output, int x, int y){
    appendNode(output, x);
    appendNode(output, y);
    output.append(',');
  }
  
  private void appendNode(StringBuilder output, int x){
    for(int i=(3-String.valueOf(x).length()); i>0; i--  )
      output.append(' ');
    
    output.append(x);
  }
  
  public static void main(String[] args) throws Exception {
    Main sv = new Main();
    
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    int n, k, m;
  
    try {
      while (in.hasNext()) { 
        //init
        
        //read
        n = in.nextInt();
        k = in.nextInt();
        m = in.nextInt();
        
        //main        
        if(n==0 && k==0 && m==0)
          return;
        
        //output
        System.out.println(sv.calDoleQueue(n, k, m));

      }
    }
    catch (Exception e) {
      //e.printStackTrace();
    }
    finally {
      in.close();
    }
    
  }
  
}
