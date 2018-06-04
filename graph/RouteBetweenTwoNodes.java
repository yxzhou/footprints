package fgafa.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;


/**
 * 
 * Given a directed graph, design an algorithm to find out whether there is a route between two nodes.
 *
 *Have you met this question in a real interview? Yes
 *Example
 *Given graph:
 *
 *A----->B----->C
 * \     |
 *  \    |
 *   \   |
 *    \  v
 *     ->D----->E
 *for s = B and t = E, return true
 *
 *for s = D and t = C, return false
 *
 */
public class RouteBetweenTwoNodes {

	
	/**
	 * Definition for Directed graph. */
	 class DirectedGraphNode {
	    int label;
	     ArrayList<DirectedGraphNode> neighbors;
	     DirectedGraphNode(int x) {
	         label = x;
	         neighbors = new ArrayList<DirectedGraphNode>();
	     }
	 };
	 

	   /**
	     * @param graph: A list of Directed graph node
	     * @param s: the starting Directed graph node
	     * @param t: the terminal Directed graph node
	     * @return: a boolean value
	     */
	    public boolean hasRoute(ArrayList<DirectedGraphNode> graph, 
	                            DirectedGraphNode s, DirectedGraphNode t) {
	        // check
	        if(null == graph){
	            return false;
	        }
	        if(s == t){
	            return true;
	        }
	        
	        Set<Integer> isVisited = new HashSet<>(); //Set<label>
	        Stack<DirectedGraphNode> stack = new Stack<>();
	        stack.add(s);
	        isVisited.add(s.label);
	        
	        DirectedGraphNode curr;
	        while( !stack.isEmpty() ){
	            curr = stack.pop();
	            
	            if(null != curr.neighbors){
	                for(DirectedGraphNode node : curr.neighbors){
	                    if(node == t){
	                        return true;
	                    }else if(!isVisited.contains(node.label)){
	                         stack.add(node);
	                         isVisited.add(node.label);
	                    }
	                }
	            }

	        }
	        
	        return false;
	    }
}
