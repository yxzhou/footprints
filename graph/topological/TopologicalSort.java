package graph.topological;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class TopologicalSort {

    /**
     * Definition for Directed graph.
     */
    class DirectedGraphNode {

        int label;
        ArrayList<DirectedGraphNode> neighbors;

        DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    };

    /**
     * BFS
     * 
     * @param graph : A list of Directed graph node
     * @return: Any topological order for the given graph.
     */
    public ArrayList<DirectedGraphNode> topSort_BFS(ArrayList<DirectedGraphNode> graph) {
        ArrayList<DirectedGraphNode> result = new ArrayList<>();
        if (null == graph) {
            return result;
        }

        int[] inDegree = new int[graph.size()];// label is not duplicated, it's from 0 to graph.size - 1, Default all are 0
        for (DirectedGraphNode node : graph) {
            for (DirectedGraphNode child : node.neighbors) {
                inDegree[child.label]++;
            }
        }

        for (DirectedGraphNode node : graph) {
            if (0 == inDegree[node.label]) {
                result.add(node);
            }
        }

        for (int i = 0; i < result.size(); i++) {
            for (DirectedGraphNode child : result.get(i).neighbors) {
                inDegree[child.label]--;

                if (0 == inDegree[child.label]) {
                    result.add(child);
                }
            }
        }

        return result;
    }
    
    /**
     * DFS
     * 
     * @param graph
     * @return 
     */
    public ArrayList<DirectedGraphNode> topSort_DFS(ArrayList<DirectedGraphNode> graph) {
        ArrayList<DirectedGraphNode> result = new ArrayList<>(graph.size());
        if (null == graph) {
            return result;
        }
        
        Set<Integer> visited = new HashSet<>();
        
        for(DirectedGraphNode node : graph){
            if(!visited.contains(node.label)){
                dfs(node, visited, result);
            }
        }
        
        Collections.reverse(result);
        return result;
    }
    
    private void dfs(DirectedGraphNode curr, Set<Integer> visited, List<DirectedGraphNode> result){
        visited.add(curr.label);
        
        for(DirectedGraphNode node : curr.neighbors){
            if(!visited.contains(node.label)){
                dfs(node, visited, result);
            }
        }
        
        result.add(curr);
    }

    public static void main(String[] args) {
        System.out.println(2 + (5 - 1) >> 1);

        System.out.println(2 + ((5 - 1) >> 1));

        List<String> list = null;

        for (String s : list) { //  java.lang.NullPointerException
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
