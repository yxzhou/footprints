package fgafa.datastructure.unionFind;

/**
 * 
 * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), 
 * implements the following functions to find out all the connected component.
 *  class UF{
 *       UF(int n)  //initialize n sites with integer names (0 to n-1)
 *       void union(int p, int q)  // add connection between p and q
 *       int find(int p)  // return component identifier for p (0 to n-1)
 *       boolean isConnected(int p, int q) //return true if p and q are in the same component
 *       int count()   // return number of components
 *  }
 */

public interface UF {

	// add connection between p and q
	void union(int p, int q);

	// return component identifier for p (0 to n-1)
	int find(int p);

	// return true if p and q are in the same component
	boolean isConnected(int p, int q);

	// return number of components
	int count();
	
}
