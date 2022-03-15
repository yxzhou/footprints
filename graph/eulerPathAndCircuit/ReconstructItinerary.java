/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graph.eulerPathAndCircuit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1288
 *
 * Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], reconstruct the
 * itinerary in order. All of the tickets belong to a man who departs from JFK. Thus, the itinerary must begin with JFK.
 *
 * Notes:
 *   1.If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when
 * read as a single string. For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
 *   2.All airports are represented by three capital letters (IATA code). 
 *   3.You may assume all tickets form at least one valid itinerary and all tickets must be used.
 *
 * Example 1: 
 * Input: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]] 
 * Output: ["JFK", "MUC", "LHR", "SFO", "SJC"].
 *
 * Example 2: 
 * Input: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]] 
 * Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]. 
 * Explanation: 
 *   Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"]. But it is larger in lexical order'
 * 
 * Thoughts:
 *    traversal the flights. 
 *    
 */
public class ReconstructItinerary {
    
   /**
     * @param tickets: 
     * @return the itinerary in order
     */
    public List<String> findItinerary(List<List<String>> tickets, String startPoint) {
        
        Map<String, Integer> visited = new HashMap<>();
        Map<String, List<String>> graph = new HashMap<>();
        String ticket;
        for(List<String> edge : tickets){
            graph.computeIfAbsent(edge.get(0), x->new ArrayList<>()).add(edge.get(1));

            ticket = edge.get(0) + edge.get(1);
            visited.put(ticket, visited.getOrDefault(ticket, 0) + 1);
        }

        for( List<String> nexts : graph.values() ){
            Collections.sort(nexts);
        }

        List<String> result = new LinkedList<>();

        dfs(graph, visited, startPoint, result);

        return result;
    }

    private boolean dfs(Map<String, List<String>> graph, Map<String, Integer> tickets, String from, List<String> result){
        
        result.add(from);

        if( graph.containsKey(from) ){
            String ticket;
            for(String next : graph.get(from) ){
                ticket = from+next;
                if( tickets.containsKey(ticket) ){
                    if(tickets.get(ticket) == 1){
                        tickets.remove(ticket);
                    }else{
                        tickets.put(ticket, tickets.get(ticket) - 1);
                    }

                    if(dfs(graph, tickets, next, result)){
                        break;
                    }

                    tickets.put(ticket, tickets.getOrDefault(ticket, 0) + 1);
                }
            }
        }

        if(tickets.isEmpty()){
            return true;
        }

        result.remove(result.size() - 1);
        return false;
    }
    
    /**
     * flood-fill,  all edges
     * 
     * @param tickets
     * @param startPoint
     * @return 
     */
    public List<String> findItinerary_dfs(List<List<String>> tickets, String startPoint) {
        List<String> itinerary = new ArrayList<>();
        Map<String, PriorityQueue<String>> graph = new HashMap<>();

        for (List<String> ticket : tickets) {
            graph.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>()).offer(ticket.get(1));
        }

        dfs(graph, itinerary, startPoint);

        Collections.reverse(itinerary);
        return itinerary;
    }

    private void dfs(Map<String, PriorityQueue<String>> graph, List<String> itinerary, String from) {
        PriorityQueue<String> nexts = graph.get(from);
        if (nexts != null) {
            while (!nexts.isEmpty()) {
                dfs(graph, itinerary, nexts.poll());
            }
        }
        itinerary.add(from);
    }
    
    /**
     *  This is only ok for some cases, especially when there is a valid itinerary.
     *
     *  Not work at case such as {A->B, B->C, B->D, start from A}
     */
    public List<String> itineraryPath_bfs(String[][] tickets, String startPoint) {
        Map<String, PriorityQueue<String>> map = new HashMap<>();
        for (String[] ticket : tickets) {
            map.computeIfAbsent(ticket[0], k -> new PriorityQueue<>()).add(ticket[1]);
        }

        LinkedList<String> result = new LinkedList<>();
                
        String curr = startPoint;
        Stack<String> stack = new Stack<>();

        //bfs
        PriorityQueue<String> nexts;
        for(int i = 0; i < tickets.length; i++) {
            while ((nexts = map.get(curr)) == null || nexts.isEmpty()) {
                stack.push(curr);

                if(result.isEmpty()){
                    return null; //it will not happened if there is at least one valid itinerary
                }else{
                    curr = result.removeLast();
                }
            }
            result.add(curr);
            curr = nexts.poll();
        }

        result.add(curr);
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }
    
    /**
     *  This is only ok when it assure there is a valid itinerary.
     *  Not work at case such as {A->B, B->C, start from B }
     */
    public List<String> findItinerary_x(String[][] tickets, String startPoint) {
        Map<String, PriorityQueue<String>> map = new HashMap<>();
        for (String[] ticket : tickets) {
            map.computeIfAbsent(ticket[0], k -> new PriorityQueue<>()).add(ticket[1]);
        }

        Stack<String> stack = new Stack<>();
        stack.push(startPoint);
        
        LinkedList<String> result = new LinkedList<>();

        PriorityQueue<String> nexts;
        while(!stack.isEmpty()) {            
            while ( ( nexts = map.get(stack.peek()) ) != null && !nexts.isEmpty()) {
                stack.push( nexts.poll() );
            }

            result.add(0, stack.pop());
        }

        return result;
    }
    
    public static void main(String[] args){
        String[][][][] inputs = {
            {
                /**
                 *      KUL
                 *     /
                 *  JFK <-> NRT
                 */
                {{"JFK","KUL"},{"JFK","NRT"},{"NRT","JFK"}},
                {{"JFK"}},
                {{"JFK","NRT","JFK","KUL"}}
            },
            {
                /**
                 * JFK -> MUC -> LHR-> SFO -> SJC
                 */
                {{"MUC", "LHR"}, {"JFK", "MUC"}, {"SFO", "SJC"}, {"LHR", "SFO"}},
                {{"JFK"}},
                {{"JFK", "MUC", "LHR", "SFO", "SJC"}}
            },
            {
                /**
                 * JFK <-> ATL 
                 *   \     //
                 *     SFO
                 */
                {{"JFK","SFO"},{"JFK","ATL"},{"SFO","ATL"},{"ATL","JFK"},{"ATL","SFO"}},
                {{"JFK"}},
                {{"JFK","ATL","JFK","SFO","ATL","SFO"}}
            },
            {
                /**
                 * JFK -> ATL
                 *  \\   //
                 *    SFO
                 */
                {{"JFK", "SFO"}, {"JFK", "ATL"}, {"SFO", "ATL"}, {"SFO", "JFK"}, {"ATL", "SFO"}},
                {{"JFK"}},
                {{"JFK", "ATL", "SFO", "JFK", "SFO", "ATL"}}
            },
            {
                /**
                 * JFK <-> ANU - EZE
                 *   \\  ///       \
                 *     TIA   <---  AXA 
                 * 
                 */
                {{"EZE","AXA"},{"TIA","ANU"},{"ANU","JFK"},{"JFK","ANU"},{"ANU","EZE"},{"TIA","ANU"},{"AXA","TIA"},{"TIA","JFK"},{"ANU","TIA"},{"JFK","TIA"}}, //duplicated flight {"TIA","ANU"}
                {{"JFK"}},
                {{"JFK","ANU","EZE","AXA","TIA","ANU","JFK","TIA","ANU","TIA","JFK"}}
            },
            {
                /**
                 *  A -> B
                 *   \\  /
                 *     C
                 */
                {{"A", "B"}, {"A", "C"}, {"B", "C"}, {"C", "A"}},
                {{"A"}},
                {{"A", "B", "C", "A", "C"}}
            }
        };
        
        ReconstructItinerary sv2 = new ReconstructItinerary();

        for(String[][][] input : inputs){
            System.out.println(String.format("\n[%s]", Misc.array2String(input[0], true) ));
            
            Assert.assertArrayEquals(input[2][0], sv2.findItinerary(Misc.convert(input[0]), input[1][0][0]).toArray(new String[0]) );
            Assert.assertArrayEquals(input[2][0], sv2.findItinerary_dfs(Misc.convert(input[0]), input[1][0][0]).toArray(new String[0]) );
            
            Assert.assertArrayEquals(input[2][0], sv2.itineraryPath_bfs(input[0], input[1][0][0]).toArray(new String[0]) );
            
            Assert.assertArrayEquals(input[2][0], sv2.findItinerary_x(input[0], input[1][0][0]).toArray(new String[0]) );
        }
    }
    
}
