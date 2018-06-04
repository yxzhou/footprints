package fgafa.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Find the number Weak Connected Component in the directed graph. Each node in the graph contains a label and a list of its neighbors. (a connected set of a directed graph is a subgraph in which any two vertices are connected by direct edge path.)

	Example
	Given graph:
	
	A----->B  C
	 \     |  | 
	  \    |  |
	   \   |  |
	    \  v  v
	     ->D  E <- F
	Return {A,B,D}, {C,E,F}. Since there are two connected component which are {A,B,D} and {C,E,F}
	
	Note
	Sort the element in the set in increasing order
 *
 */

public class ConnectedComponentsDG {

	    /**
	     * @param nodes a array of Directed graph node
	     * @return a connected set of a directed graph
	     */
	    public List<List<Integer>> connectedSet2(ArrayList<DirectedGraphNode> nodes) {
	        List<List<Integer>> result = new ArrayList<List<Integer>>();
	        
	        //check
	        if(null == nodes || 0 == nodes.size()){
	            return result;
	        }
	        
	        Map<Integer, Set<Integer>> pool = new HashMap<Integer, Set<Integer>>();
	        Integer key1;
	        Set<Integer> connectedComponent1;
	        Integer key2;
	        Set<Integer> connectedComponent2;
	        for(DirectedGraphNode node : nodes){
	            key1 = getConnectedComponent(pool, node.label);
	            
	            if(null == key1){
	                connectedComponent1 = new HashSet<Integer>();
	                connectedComponent1.add(node.label);
	                key1 = node.label;
	                pool.put(key1, connectedComponent1);
	            }else{
	            	connectedComponent1 = pool.get(key1);
	            }
	            
	            for(DirectedGraphNode neighbor : node.neighbors){
	                key2 = getConnectedComponent(pool, neighbor.label);
	                
	                if(null == key2){
	                    connectedComponent1.add(neighbor.label);
	                }else if(!key1.equals(key2)){  // key point
	                	connectedComponent2 = pool.get(key2);
	                    connectedComponent1.addAll(connectedComponent2);
	                    pool.remove(key2);
	                }
	            }
	        }
	        
	        List<Integer> list;
	        for(Set<Integer> connectedComponent : pool.values()){
	            list = new ArrayList<>(connectedComponent);
	            Collections.sort(list);
	            result.add(list);
	        }
	        
	        return result;
	    }
	    
	    private Integer getConnectedComponent(Map<Integer, Set<Integer>> pool, int label){
	        for(Integer key : pool.keySet()){
	            if(pool.get(key).contains(label)){
	                return key;
	            }
	        }
	        
	        return null;
	    }
	
	    
		/**
		 * Definition for Directed graph.  */
		  class DirectedGraphNode {
		      int label;
		      ArrayList<DirectedGraphNode> neighbors;
		      DirectedGraphNode(int x) { label = x; neighbors = new ArrayList<DirectedGraphNode>(); }
		 };
}
