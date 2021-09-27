package graph;

import java.util.ArrayList;

public class Graph
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
  public Vertex[] getVertex() {
    return vertex;
  }

  public void setVertex(Vertex[] vertex) {
    this.vertex = vertex;
  }

  public static Vertex getStart() {
    return start;
  }

  public static void setStart(Vertex start) {
    Graph.start = start;
  }

  private static Vertex start;
  private int num;  // vertex amount
  
  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public Graph(){
    
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
  public Vertex addVertex(int key) {
    Vertex returnV = new Vertex(key);
    vertex[num++] = returnV;
    return returnV;
  }


  // add Edge
  public void addEdge(Vertex v, Vertex next) {
    v.next.add(next);
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
