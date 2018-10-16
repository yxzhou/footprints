package fgafa.uva.graph.FollowingOrderN124;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

class Main
{

  class Node{
    String name;  // in fact, it's a character. 
    //boolean hasNext = false;  //true when there was constraint that someone is bigger than this, 
    ArrayList<String> pre = new ArrayList<String>();    //store all node when existed constraint that someone is smaller than this
    boolean visited = false;
    
    Node(String name){
      this.name = name;
    }
  }
  
  private void getOrderBFS(HashMap<String, Node> nodesHM, ArrayList<String> nodesList, String path){
    
    if(nodesList.size() == path.length())
      System.out.println(path);
    else{
      Node curr;
      
      outer: for(String node : nodesList){
        curr = nodesHM.get(node);
        if( !curr.visited ){
          for(String parentNode: curr.pre){
            if(!nodesHM.get(parentNode).visited)
             continue outer; 
          }

          curr.visited = true;
          getOrderBFS(nodesHM, nodesList, path+node);
          curr.visited = false;  //backtracking
        }
      }
    }
      
  }


  public void followingOrder(String variable, String constraint){
    //init
    ArrayList<String> nodesList = new ArrayList<String>();
    HashMap<String, Node> nodesHM = new HashMap<String, Node>();

    String var, left, right;
    StringTokenizer st;    
    //add all variable
    st = new StringTokenizer(variable, " ");
    while( st.hasMoreTokens()){
      var = st.nextToken();
      nodesHM.put(var, new Node(var));
      nodesList.add(var);
    }
    
    // add constraint
    st = new StringTokenizer(constraint, " ");   
    while( st.hasMoreTokens()){
      left = st.nextToken();
      right = st.nextToken();
      
      nodesHM.get(right).pre.add(left);
      //nodesHM.get(left).hasNext = true;
    }

    //output the ordering in lexicographical (alphabetical) order 
    Collections.sort(nodesList);

    getOrderBFS(nodesHM, nodesList, "");

  }
  

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    Main sv = new Main();

    // init
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    String variable = null, constraint = null;
    int count = 0;
    
    try {
      while (in.hasNext()) {
          variable = in.nextLine();
          constraint = in.nextLine();

          if(count++ != 0)
            System.out.println();    
          
          sv.followingOrder(variable, constraint);
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
