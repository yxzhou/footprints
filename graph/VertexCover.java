package fgafa.graph;

import java.util.Stack;

/*
 * vertex cover:  it is the smallest subset S (the subset V) such that each e \in E contains at least one vertex of S
 * 
 */
public class VertexCover extends Graph
{
  // not a directed graph
  public class Edge{
    private Vertex U;
    public Vertex getU() {
      return U;
    }

    public void setU(Vertex u) {
      U = u;
    }

    public Vertex getV() {
      return V;
    }

    public void setV(Vertex v) {
      V = v;
    }

    private Vertex V;
    
    boolean covered = false;
  }
  
  /**
   * Compute a vertex cover with Gavril's algorithm
   * It computes a set of edges defining a maximal matching:
   * the incident vertices provide the vertex cover.
   */
  public void VertexCover_Gavril() {
      // throw new Error("a' completer: exo 3");
      System.out.println("running Gavril algorithm...");
      
      Stack<Edge> L=new Stack<Edge>();
      for(int i=0;i<this.getNum();i++) { // ajout des aretes dans L
          for(Edge e: this.getVertex()[i])
              L.push(e);
      }
      
      while(L.isEmpty()==false) {
          Edge e=L.pop();
          if(e.covered==false) {
              System.out.println("edge covered: "+e);
              int u=e.getU();
              int v=e.getV();
              this.addVertexToCover(u); // add vertex u to the cover
              this.addVertexToCover(v); // add vertex v to the cover
              
              for(Edge a:this.g.getEdges(u)) // mark the edges incident to vertex u
                  a.covered=true;
              
              
              for(Edge a: this.g.getEdges(v)) 
                  a.covered=true; // mark the edges incident to vertex v
              }
          }
      }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
