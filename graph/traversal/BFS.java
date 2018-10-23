package fgafa.graph.traversal;

import java.util.ArrayList;
import java.util.LinkedList;

public class BFS
{

  public class Vertex
  {
      int key;
      String color;
      Vertex parent;
      // store all adjacency vertex,  adjacency list
      ArrayList<Vertex> next = new ArrayList<Vertex>();

      public Vertex(int key) {
        this.key = key;
      }
  }
  
  private static Vertex[] vertex;
  private static Vertex start;
  private int num = 0;

  public BFS() {

  }


/*
 * use queue to store the vertex
 * use color as flag, white is initial value, means for searching; gray mean in searching, it'll be added in the queue; 
 * black is after searching, and his adjacency vertex has been added in the queue.   
 * 
 */
  public void BFSearch() {
    //use queue to store the vertex
    LinkedList<Vertex> queue = new LinkedList<Vertex>();

    // initial, set all color to white, parent to NULL
    for (int i = 0; i < num; i++) {
      vertex[i].color = "white";
      vertex[i].parent = null;
    }

    // initial the start, set the color to gray, parent to NULL
    start.color = "gray";
    start.parent = null;
    queue.offer(start);   //in queue

    while (!queue.isEmpty()) {
      Vertex u = queue.poll(); // out queue 
      for (Vertex v : u.next) {
        //check all adjacency vertex
        if (v.color == "white") {
          v.color = "gray";
          v.parent = u;
          queue.offer(v);
        }
      }
      u.color = "black";
      System.out.println(u.key);
    }
  }


  private void buildGraph() {
    vertex = new Vertex[10];
        
    Vertex v1 = addVertex(1);
    Vertex v2 = addVertex(2);
    Vertex v3 = addVertex(3);
    Vertex v4 = addVertex(4);
    Vertex v5 = addVertex(5);
    Vertex v6 = addVertex(7);
    
    //undirected graph
//    addEdge(v1, v2);
//    addEdge(v1, v3);
//    addEdge(v1, v4);
//    addEdge(v2, v4);
//    addEdge(v2, v1);
//    addEdge(v2, v6);
//    addEdge(v3, v1);
//    addEdge(v4, v2);
//    addEdge(v4, v1);
//    addEdge(v4, v5);
//    addEdge(v5, v4);
//    addEdge(v6, v2);
    
    //directed graph
    addEdge(v1, v2);
    addEdge(v1, v3);
    addEdge(v1, v4);
    addEdge(v2, v6);
    addEdge(v2, v4);
    addEdge(v4, v5);

    start = vertex[0];  
    
  }


  // add Vertex
  private Vertex addVertex(int key) {
    Vertex returnV = new Vertex(key);
    
    vertex[num ++ ] = returnV;
    
    return returnV;
  }


  // add Edge
  private void addEdge(Vertex v, Vertex next) {
    v.next.add(next);
  }

  public static void main(String[] args) {
    BFS G = new BFS();
    G.buildGraph();
        
    G.BFSearch();
  }
}
