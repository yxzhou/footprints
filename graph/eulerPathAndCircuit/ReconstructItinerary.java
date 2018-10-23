package fgafa.graph.eulerPathAndCircuit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * 
 * Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], 
 * reconstruct the itinerary in order. 
 * All of the tickets belong to a man who departs from JFK. Thus, the itinerary must begin with JFK.

    Note:
    If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string. 
    For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
    All airports are represented by three capital letters (IATA code).
    You may assume all tickets form at least one valid itinerary.
    
    Example 1:
    tickets = [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
    Return ["JFK", "MUC", "LHR", "SFO", "SJC"].
    
    Example 2:
    tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
    Return ["JFK","ATL","JFK","SFO","ATL","SFO"].
    Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"]. But it is larger in lexical order.
 *
 */

public class ReconstructItinerary {
    
    //You may assume all tickets form at least one valid itinerary. (Euler Path)
    
    public List<String> findItinerary(String[][] tickets) {
        Map<String, PriorityQueue<String>> map = new HashMap<>();
        for (String[] sa : tickets) {
            if (!map.containsKey(sa[0])) {
                map.put(sa[0], new PriorityQueue<String>());
            }
            map.get(sa[0]).add(sa[1]);
        }
        
        String cur = "JFK";
        Stack<String> stack = new Stack<String>();
        LinkedList<String> result = new LinkedList<>();

        //dfs
        for(int i = 0; i < tickets.length; i++) {
            while (!map.containsKey(cur) || map.get(cur).isEmpty()) {
                stack.push(cur);
                cur = result.removeLast();
            }
            result.add(cur);
            cur = map.get(cur).poll();
        }
        
        result.add(cur);
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }


}
