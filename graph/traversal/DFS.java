package fgafa.graph;

import java.util.ArrayList;
import java.util.List;

public class DFS
{

  public class Vertex implements Comparable<Vertex>
  {
    int key;
    String color;
    Vertex parent;
    // store all adjacency vertex,  adjacency list

    List<Vertex> next = new ArrayList<Vertex>();

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
  private static Vertex start;
  private int num;

  public DFS() {
  }


  /*
   * Recurrence 
   * use color as flag, white is initial value, means for searching; gray mean in searching, it'll be added in the queue; 
   * black is after searching, and his adjacency vertex has been added in the queue.   
   * 
   */
  public void DFSearch() {
    //initial
    for (int i = 0; i < num; i++) {
      vertex[i].color = "white";
      vertex[i].parent = null;
    }

    DES_visit(start);

  }



  private void DES_visit(Vertex u) {
    u.color = "gray"; // 
    System.out.print(u.key + " ");
    for (Vertex v : u.next) {
      if (v.color == "white") {
        v.parent = u;
        // recurr
        DES_visit(v);
      }
    }
    u.color = "black"; //

  }



  // 
  private void buildGraph() {
    vertex = new Vertex[10];

    Vertex v1 = addVertex(1);
    Vertex v2 = addVertex(2);
    Vertex v3 = addVertex(3);
    Vertex v4 = addVertex(4);
    Vertex v5 = addVertex(5);
    Vertex v6 = addVertex(7);

    //undirected graph
    addEdge(v1, v2);
    addEdge(v1, v3);
    addEdge(v1, v4);
    addEdge(v2, v4);
    addEdge(v2, v1);
    addEdge(v2, v6);
    addEdge(v3, v1);
    addEdge(v4, v2);
    addEdge(v4, v1);
    addEdge(v4, v5);
    addEdge(v5, v4);
    addEdge(v6, v2);

    //directed graph, acycline graph 
//           addEdge(v1, v2);
//           addEdge(v3, v1);
//           addEdge(v2, v4);
//           addEdge(v1, v4);
//           addEdge(v5, v4);

    start = vertex[2];

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


  public static void main(String[] args) {
    DFS dfs = new DFS();
    dfs.buildGraph();

    dfs.DFSearch();
    System.out.println();

  }

}
