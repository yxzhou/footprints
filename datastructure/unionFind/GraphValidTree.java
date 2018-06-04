package fgafa.datastructure.unionFind;

/**
 * 
 * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), 
 * write a function to check whether these edges make up a valid tree.

	Example
	Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
	
	Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.
	
	Note
	You can assume that no duplicate edges will appear in edges. Since all edges are undirected, 
	[0, 1] is the same as [1, 0] and thus will not appear together in edges.
 *
 */

public class GraphValidTree {

    /**
     * @param n an integer
     * @param edges a list of undirected edges
     * @return true if it's a valid tree, or false
     */
    public boolean validTree(int n, int[][] edges) {
        //check, to Tree, n vertices only have n-1 edges
        if(edges == null || n - 1 != edges.length ){
        	return false;
        }
        
        int[] parent = new int[n];
        for(int i = 0; i < n; i++){
        	parent[i] = i;
        }
        
        for(int[] pair : edges){
        	union(parent, pair[0], pair[1]);
        }
        
        return true;
    }
	
    
    private void union(int[] parent, int p, int q){
    	int pRoot = findUnionId(parent, p);
    	int qRoot = findUnionId(parent, q);
		
    	if(pRoot == qRoot){ //has loop
    		return;
    	}
    	
    	parent[qRoot] = pRoot;
    }
    
    private int findUnionId(int[] parent, int p){
		while(parent[p] != p){
			p = parent[p];
		}
		
    	return p;
    }
}
