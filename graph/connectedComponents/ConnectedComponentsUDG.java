package fgafa.graph.connectedComponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * 
 * Find the number connected component in the undirected graph. 
 * Each node in the graph contains a label and a list of its neighbors. 
 * (a connected component (or just component) of an undirected graph is a 
 * subgraph in which any two vertices are connected to each other by paths, 
 * and which is connected to no additional vertices in the supergraph.)

	Example
	Given graph:
	
	A------B  C
	 \     |  | 
	  \    |  |
	   \   |  |
	    \  |  |
	      D   E
	Return {A,B,D}, {C,E}. Since there are two connected component which is {A,B,D}, {C,E}
 *
 */

public class ConnectedComponentsUDG {
 

	/**
	 * @param nodes
	 *            a array of Undirected graph node
	 * @return a connected set of a Undirected graph
	 */
	public List<List<Integer>> connectedSet_bfs(ArrayList<UndirectedGraphNode> nodes) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		//check
		if( null == nodes || 0 == nodes.size()){
			return result;
		}
		
		Set<Integer> visited = new HashSet<Integer>();
		for(UndirectedGraphNode node : nodes){
			if(!visited.contains(node.label)){
				result.add(bfs(node, visited));
			}
		}
		
		return result;
	}

	private List<Integer> bfs(UndirectedGraphNode node, Set<Integer> visited){
		List<Integer> result = new ArrayList<Integer>();
		
		Queue<UndirectedGraphNode> queue = new LinkedList<UndirectedGraphNode>();
		queue.add(node);
		visited.add(node.label);
		
		UndirectedGraphNode top;
		while(!queue.isEmpty()){
			top = queue.poll();
			result.add(top.label);
			
			for(UndirectedGraphNode neighbor : top.neighbors){
				if(!visited.contains(neighbor.label)){
					queue.add(neighbor);
					visited.add(neighbor.label);
				}
			}
		}
		
		Collections.sort(result);
		
		return result;
	}
	
	public List<List<Integer>> connectedSet_dfs(ArrayList<UndirectedGraphNode> nodes) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		//check
		if( null == nodes || 0 == nodes.size()){
			return result;
		}
		
		Set<Integer> visited = new HashSet<Integer>();
		List<Integer> connectedComponent;
		for(UndirectedGraphNode node : nodes){
			if(!visited.contains(node.label)){
				connectedComponent = new ArrayList<>();
				dfs(node, visited, new ArrayList<>());
				Collections.sort(connectedComponent);
				
				result.add(connectedComponent);
			}
		}
		
		return result;
	}
	
	private void dfs(UndirectedGraphNode node, Set<Integer> visited, List<Integer> result){
		result.add(node.label);
		visited.add(node.label);
		
		for(UndirectedGraphNode neighbor : node.neighbors){
			if(!visited.contains(neighbor.label)){
				dfs(neighbor, visited, result);
			}
		}
	}
	
	/**
	 * Definition for Undirected graph.
	 */
	class UndirectedGraphNode {
		int label;
		ArrayList<UndirectedGraphNode> neighbors;

		UndirectedGraphNode(int x) {
			label = x;
			neighbors = new ArrayList<UndirectedGraphNode>();
		}
	};
}
