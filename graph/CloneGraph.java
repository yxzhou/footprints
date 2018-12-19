package fgafa.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;


/**
 * 
 * Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.

    How we serialize an undirected graph:
    
    Nodes are labeled uniquely.
    
    We use # as a separator for each node, and , as a separator for node label and each neighbor of the node.
    As an example, consider the serialized graph {0,1,2#1,2#2,2}.
    
    The graph has a total of three nodes, and therefore contains three parts as separated by #.
    
    First node is labeled as 0. Connect node 0 to both nodes 1 and 2.
    Second node is labeled as 1. Connect node 1 to node 2.
    Third node is labeled as 2. Connect node 2 to node 2 (itself), thus forming a self-cycle.
    Visually, the graph looks like the following:
    
       1
      / \
     /   \
    0 --- 2
         / \
         \_/
    
    Example
    return a deep copied graph.
 *
 */

public class CloneGraph {

	public UndirectedGraphNode cloneGraph_dfs(UndirectedGraphNode node) {
		//check input
		if(null == node){
			return null;
		}
		
		//dfs, create all vertex and map<oldVertex, newVertex>
		Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
		Stack<UndirectedGraphNode> stack = new Stack<>();
		stack.push(node);
		Map<UndirectedGraphNode, Integer> status = new HashMap<>();
		UndirectedGraphNode oldNode;
		while(!stack.isEmpty()){
			oldNode = stack.peek();
			
			//create the new vertex to every old vertex
			map.put(oldNode, new UndirectedGraphNode(oldNode.label));
			
			//add the neighbor in stack,  if no neighbor any more, pop up
			if(!status.containsKey(oldNode) || ( null != oldNode.neighbors && status.get(oldNode) < oldNode.neighbors.size())){
				if(!status.containsKey(oldNode)){
					status.put(oldNode, 0);
				}
				
				UndirectedGraphNode key = oldNode.neighbors.get(status.get(oldNode));
				if(null != key && !status.containsKey(key)){//avoid loop
					stack.push(key);
				}

				status.put(oldNode, status.get(oldNode) + 1);
			}else{
				stack.pop();
			}
		}
		
		//dfs, create the edges between the newVertex.
		stack.push(node);
		status.clear();
		UndirectedGraphNode newNode;
		while(!stack.isEmpty()){
			oldNode = stack.peek();
			
			//copy the edges between the oldNode to neighbors
			if(null != oldNode.neighbors){
				newNode = map.get(oldNode);
				newNode.neighbors = new ArrayList<>();
				for(UndirectedGraphNode neighbor : oldNode.neighbors){
					newNode.neighbors.add(map.get(neighbor));
				}
			}
			
			//add the neighbor in stack,  if no neighbor any more, pop up
			if(!status.containsKey(oldNode) || ( null != oldNode.neighbors && status.get(oldNode) < oldNode.neighbors.size())){
				if(!status.containsKey(oldNode)){
					status.put(oldNode, 0);
				}
				
				UndirectedGraphNode key = oldNode.neighbors.get(status.get(oldNode));
				if(null != key && !status.containsKey(key)){//avoid loop
					stack.push(key);
				}

				status.put(oldNode, status.get(oldNode) + 1);
			}else{
				stack.pop();
			}
		}
		
		return map.get(node);
	}

	public UndirectedGraphNode cloneGraph_bfs(UndirectedGraphNode node) {
		//check input
		if(null == node){
			return null;
		}
		
		//bfs, create all vertex and map<oldVertex, newVertex>
		Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
		Queue<UndirectedGraphNode> queue = new LinkedList<>();
		queue.offer(node);

		UndirectedGraphNode oldNode;
		while(!queue.isEmpty()){
			oldNode = queue.poll();
			
			//create the new vertex to every old vertex
			map.put(oldNode, new UndirectedGraphNode(oldNode.label));
			
			//add the valid neighbor in queue
			if( null != oldNode.neighbors ){
				for(UndirectedGraphNode neighbor : oldNode.neighbors){
					if(null != neighbor && !map.containsKey(neighbor)){ //avoid loop
						queue.offer(neighbor);
					}
				}
			}
		}
		
		//bfs, create the edges between the newVertex.
		queue.offer(node);
		UndirectedGraphNode newNode;
		Map<UndirectedGraphNode, Boolean> isVisited = new HashMap<>();
		while(!queue.isEmpty()){
			oldNode = queue.poll();
			isVisited.put(oldNode, true);			
			//copy the edges between the oldNode to neighbors, add the valid neighbor in queue
			if(null != oldNode.neighbors){
				newNode = map.get(oldNode);
				newNode.neighbors = new ArrayList<>();
				for(UndirectedGraphNode neighbor : oldNode.neighbors){
					newNode.neighbors.add(map.get(neighbor));
					
					if(null != neighbor && !isVisited.containsKey(neighbor)){ //avoid loop
						queue.offer(neighbor);
					}
				}
			}
		}
		
		return map.get(node);
	}
	
	public UndirectedGraphNode cloneGraph_bfs_x(UndirectedGraphNode node) {
		//check input
		if(null == node){
			return null;
		}
		
		//
		UndirectedGraphNode newNode = new UndirectedGraphNode(node.label);
		
		//bfs, create all vertex and map<oldVertex, newVertex>
		Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
		map.put(node, newNode);
		Queue<UndirectedGraphNode> queue = new LinkedList<>();
		queue.offer(node);

		UndirectedGraphNode oldNode;
        while (!queue.isEmpty()) {
            oldNode = queue.poll();

            // add the valid neighbor in queue

            for (UndirectedGraphNode neighbor : oldNode.neighbors) {

                if (!map.containsKey(neighbor)) { // avoid loop
                    queue.offer(neighbor);

                    map.put(neighbor, new UndirectedGraphNode(neighbor.label));
                }

                map.get(oldNode).neighbors.add(map.get(neighbor));
            }

        }
		
		return newNode;
	}
	
    /**
     * @param node: A undirected graph node
     * @return: A undirected graph node
     */
    public UndirectedGraphNode cloneGraph_n(UndirectedGraphNode node) {
        UndirectedGraphNode result = null;

        if(null == node){
            return result;
        }
        
        result = new UndirectedGraphNode(node.label);
        Queue<UndirectedGraphNode> queue = new LinkedList<UndirectedGraphNode>();
        Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
        map.put(node, result);
        queue.add(node);
        
        UndirectedGraphNode newNode;
        UndirectedGraphNode newNeighbor;
        while(!queue.isEmpty()){
            node = queue.poll();
            newNode = map.get(node);
            
            for(UndirectedGraphNode neighbor : node.neighbors){
                newNeighbor = map.get(neighbor);
                
                if(null == newNeighbor){
                    newNeighbor = new UndirectedGraphNode(neighbor.label);
                    map.put(neighbor, newNeighbor);
                    queue.add(neighbor);
                }
                
                newNode.neighbors.add(newNeighbor);
            }
        }
        
        return result;
    }


	public UndirectedGraphNode cloneGraph_n_dfs(UndirectedGraphNode node) {
		if(null == node){
			return null;
		}

		return cloneGraph_n_dfs(node, new HashMap<Integer, UndirectedGraphNode>()); //Map<label, node>
	}

	private UndirectedGraphNode cloneGraph_n_dfs(UndirectedGraphNode node, Map<Integer, UndirectedGraphNode> map){
		if(map.containsKey(node.label)){
			return map.get(node.label);
		}

		UndirectedGraphNode newNode = new UndirectedGraphNode(node.label);
		map.put(node.label, newNode);

		for(UndirectedGraphNode neighbor : node.neighbors){
			newNode.neighbors.add(cloneGraph_n_dfs(neighbor, map));
		}

		return newNode;
	}

	
	public static void main(String[] args) {
		CloneGraph sv = new CloneGraph();
		UndirectedGraphNode node = sv.new UndirectedGraphNode(0);
		node.neighbors = new ArrayList<>();
		node.neighbors.add(node);
		node.neighbors.add(node);
		
		UndirectedGraphNode newNode = sv.cloneGraph_bfs(node);
		UndirectedGraphNode newNode2 = sv.cloneGraph_bfs_x(node);
	}

	/* Definition for undirected graph. */
	class UndirectedGraphNode {
		int label;
		List<UndirectedGraphNode> neighbors;

		UndirectedGraphNode(int x) {
			label = x;
			neighbors = new ArrayList<UndirectedGraphNode>();
		}
	};
}
