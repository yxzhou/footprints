package datastructure.unionFind;

/**
 * 
 * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), 
 * implements the following functions to find out all the connected component.
 *  class UF{
 *       UF(int n)  //initialize n sites with integer names (0 to n-1)
 *       void union(int p, int q)  // add connection between p and q
 *       int find(int p)  // return component identifier for p (0 to n-1)
 *       boolean isConnected(int p, int q) //return true if p and q are in the same component
 *       int numberOfUnions()   // return number of components
 *  }
 */

public class QuickUnionUF implements UF {
	private int[] parents;  //parents[i] = the parents of i
	private int numberOfUnions; // number of components
	
	public QuickUnionUF(int capacity){
		if(capacity < 1){
			throw new IllegalArgumentException("The valid capacity is larger than 0");
		}

		numberOfUnions = capacity;
        parents = new int[capacity];
        for(int i = 0; i < capacity; i++){
        	parents[i] = i;
        }
	}
	
	@Override
    public void union(int p, int q){
    	int pRoot = find(p);
    	int qRoot = find(q);
		
    	if(pRoot == qRoot){
    		return;
    	}
    	
    	parents[qRoot] = pRoot;
    	numberOfUnions--;
    }
    
	@Override
    public int find(int p){
		validate(p);
		while(parents[p] != p){
			p = parents[p];
		}
		
    	return p;
    }
    
	@Override
    public boolean isConnected(int p, int q){
    	return find(p) == find(q);
    }
    
	@Override
    public int count(){
        return numberOfUnions;
    }
	
	//validate the p is a valid index
	private void validate(int p){
		int n = parents.length;
		if(p < 0 || p >= n){
			throw new IllegalArgumentException(String.format("index %d is not between 0 and %d", p, n));
		}
	}
}
