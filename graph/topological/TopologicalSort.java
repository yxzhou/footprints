package fgafa.graph.topological;

import java.util.ArrayList;
import java.util.List;

public class TopologicalSort {

	/**Definition for Directed graph. */
	class DirectedGraphNode {
		int label;
		ArrayList<DirectedGraphNode> neighbors;

		DirectedGraphNode(int x) {
			label = x;
			neighbors = new ArrayList<DirectedGraphNode>();
		}
	};

	/**
	 * @param graph
	 *            : A list of Directed graph node
	 * @return: Any topological order for the given graph.
	 */
	public List<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
		List<DirectedGraphNode> ret = new ArrayList<>();
		//check
		if(null == graph){
			return ret;
		}
		
		int[] inDegree = new int[graph.size()];// label is not duplicated, it's from 0 to graph.size - 1, Default all are 0
		for(DirectedGraphNode node : graph){
			for(DirectedGraphNode child : node.neighbors){
				inDegree[child.label]++;
			}
		}
		
		for(DirectedGraphNode node : graph){
			if(0 == inDegree[node.label]){
				ret.add(node);
			}
		}
		
		for(int i = 0; i < ret.size(); i++){
			for(DirectedGraphNode child : ret.get(i).neighbors){
				inDegree[child.label]--;
				
				if(0 == inDegree[child.label]){
					ret.add(child);
				}
			}
		}
		
		return ret;
	}
	    
	public static void main(String[] args) {
		System.out.println(2 + (5-1) >> 1);
		
		System.out.println(2 + ((5-1) >> 1));
		
		List<String> list = null;
		
		for(String s : list){ //  java.lang.NullPointerException
			System.out.println(s);
		}

		String s = "";
		int strLen = s.length();
		
		int[] array = {};
		int arrLen = array.length;
		
		List<String> list1 = new ArrayList<>();
		int listLen = list1.size();
		
		

	}

}
