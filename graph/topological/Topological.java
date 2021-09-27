package graph.topological;

import java.util.ArrayList;
import java.util.PriorityQueue;


public class Topological
{ 

  public class Vertex implements Comparable<Vertex>
  {
    int key;
    String color;
    Vertex parent;
    // store all adjacency vertex,  adjacency list
    ArrayList<Vertex> next = new ArrayList<Vertex>();

    // flag
    int time;



    public Vertex(int key) {
      this.key = key;
    }

    @Override
    public int compareTo(Vertex o) {
      if (time > o.time) {
        return -1;
      }
      return 1;
    }
  }

  private Vertex[] vertex;
  private int num;
  private static PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();


  public Topological() {
  }


  // 
  private void buildGraph() {
    vertex = new Vertex[10];

    Vertex v1 = addVertex(1);
    Vertex v2 = addVertex(2);
    Vertex v3 = addVertex(3);
    Vertex v4 = addVertex(4);
    Vertex v5 = addVertex(5);
    Vertex v6 = addVertex(6);

    //directed graph, acycline graph 
    addEdge(v1, v2);
    addEdge(v1, v3);
    addEdge(v2, v3);
    addEdge(v1, v4);
    addEdge(v4, v5);
    addEdge(v3, v5);
    addEdge(v6, v5);
    addEdge(v6, v4);

  }


  // add vertex
  private Vertex addVertex(int key) {
    Vertex returnV = new Vertex(key);
    vertex[num++] = returnV;
    return returnV;
  }


  // add Edge
  private void addEdge(Vertex v, Vertex next) {
    v.next.add(next);
  }

  // toplogical, directed acycline graph 
  public void toplogicalSort() {
    for (int i = 0; i < vertex.length; i++) {
      queue.add(vertex[i]);
    }
  }



  public static void main(String[] args) {
    Topological top = new Topological();
    top.buildGraph();

    
    top.toplogicalSort();
    
    while (!queue.isEmpty()) {
      System.out.print(queue.poll().key + " ");
    }
  }

}
