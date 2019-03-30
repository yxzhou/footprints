package fgafa.graph.eulerPathAndCircuit;

import fgafa.util.Misc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;


/**
 *
 * Given an unordered list of flights taken by someone, each represented as (origin, destination) pairs, and a starting airport, compute the person's itinerary.
 * If no such itinerary exists, return null.
 * If there are multiple possible itineraries, return the lexicographically smallest one. All flights must be used in the itinerary.
 *
 * For example,
 *   Given the list of flights [{"SFO", "HKO"}, {"YYZ", "SFO"}, {"YUL", "YYZ"}, {"HKO", "ORD"}] and starting airport 'YUL',
 *   Return the list ['YUL", "YYZ", "SFO", "HKO", "ORD'].
 *
 *   Given the list of flights [{"SFO", "COM"}, {"COM", "YYZ"}] and starting airport 'COM',
 *   Return null.
 *
 *   Given [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]] and starting 'JFK',
 *   Return ["JFK","ATL","JFK","SFO","ATL","SFO"].
 *   Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"]. But it is larger in lexical order.
 *
 *   Given the list of flights [{"A", "B"}, {"A", "C"}, {"B", "C"}, {"C", "A"}] and starting airport 'A',
 *   Return the list ['A", "B", "C", "A", "C'] even though ['A", "C", "A", "B", "C'] is also a valid itinerary.
 *   However, the first one is lexicographically smaller.
 *
 */

public class ReconstructItinerary {

    /**
     *  It's based on itineraryPath_2().
     *
     *  When there is a valid itinerary, the itinerary will be got with itineraryPath_2().
     *  check the itinerary and the input flights, return null if not match:
     *    1 itinerary's size should equals to flights + 1
     *    2 the path in itinerary should be in the input flights
     *
     */
    public List<String> itineraryPath(String[][] flights, String startPoint){
        if(null == flights || flights.length == 0){
            return new LinkedList<>();
        }

        Set<String> flightsSet = new HashSet<>(flights.length);
        Map<String, PriorityQueue<String>> adjacencies = new HashMap<>();
        for(String[] flight : flights){
            adjacencies.computeIfAbsent(flight[0], k -> new PriorityQueue<>()).add(flight[1]);

            flightsSet.add(flight[0] + "-"+ flight[1]);
        }

        Stack<String> stack = new Stack<>();
        LinkedList<String> path = new LinkedList<>();
        String curr = startPoint;

        for(int i = 0; i < flights.length; i++){
            while(!adjacencies.containsKey(curr) || adjacencies.get(curr).isEmpty()){
                if(path.isEmpty()){
                   return null;
                }

                stack.push(curr);
                curr = path.removeLast();
            }

            path.add(curr);
            curr = adjacencies.get(curr).poll();
        }

        path.add(curr);
        while(!stack.isEmpty()){
            path.add(stack.pop());
        }

        if(path.size() != flights.length + 1 ){
            return null;
        }

        Iterator cities = path.iterator();
        String from = (String)cities.next();
        while( cities.hasNext() ){
            String to = (String)cities.next();

            if(!flightsSet.contains(from + "-" + to)){
                return null;
            }

            from = to;
        }

        return path;
    }

    /**
     *  This is only ok for some cases, especially when there is a valid itinerary.
     *
     *  Not work at case such as {A->B, B->C, B->D, start from A}
     */
    private List<String> itineraryPath_2(String[][] tickets, String startPoint) {
        Map<String, PriorityQueue<String>> map = new HashMap<>();
        for (String[] ticket : tickets) {
            map.computeIfAbsent(ticket[0], k -> new PriorityQueue<>()).add(ticket[1]);
        }

        String cur = startPoint;
        Stack<String> stack = new Stack<>();
        java.util.LinkedList<String> res = new java.util.LinkedList<>();

        //dfs
        for(int i = 0; i < tickets.length; i++) {
            while (!map.containsKey(cur) || map.get(cur).isEmpty()) {
                stack.push(cur);

                if(res.isEmpty()){
                    return null;
                }else{
                    cur = res.removeLast();
                }
            }
            res.add(cur);
            cur = map.get(cur).poll();
        }

        res.add(cur);
        while (!stack.isEmpty()) {
            res.add(stack.pop());
        }
        return res;
    }

    public List<String> findItinerary(String[][] tickets, String startPoint) {

        Set<String> flightsSet = new HashSet<>(tickets.length);
        for (String[] ticket : tickets) {
            flightsSet.add(ticket[0] + " " + ticket[1]);
        }

        List<String> result = findItinerary_2(tickets, startPoint);

        if(result.size() == flightsSet.size() + 1){
            Iterator iterator = result.iterator();
            String from = (String)iterator.next();
            String to;
            while( iterator.hasNext() ){
                to = (String)iterator.next();

                if(!flightsSet.contains(from + " " + to)){
                    return null;
                }

                from = to;
            }

            return result;
        }

        return null;
    }

    /**
     *  This is only ok when it assure there is a valid itinerary.
     *  Not work at case such as {A->B, B->C, start from B }
     */
    public List<String> findItinerary_2(String[][] tickets, String startPoint) {
        Map<String, PriorityQueue<String>> map = new HashMap<>();
        for (String[] ticket : tickets) {
            map.computeIfAbsent(ticket[0], k -> new PriorityQueue<>()).add(ticket[1]);
        }

        Stack<String> stack = new Stack<>();
        stack.push(startPoint);
        LinkedList<String> res = new LinkedList<>();

        while(!stack.isEmpty()) {
            while (map.containsKey(stack.peek()) && !map.get(stack.peek()).isEmpty()) {
                stack.push(map.get(stack.peek()).poll());
            }

            res.add(0, stack.pop());
        }

        return res;
    }

    public static void main(String[] args){

        String[][][][] cases = {
                { { { "SFO", "HKO" }, { "YYZ", "SFO" }, { "YUL", "YYZ" }, { "HKO", "ORD" } }, { { "YUL" } } },
                { { { "SFO", "COM" }, { "COM", "YYZ" } }, { { "COM" } } },
                { { { "JFK", "SFO" }, { "JFK", "ATL" }, { "SFO", "ATL" }, { "ATL", "JFK" }, { "ATL", "SFO" } }, { { "JFK" } } },
                { { { "A", "B" }, { "A", "C" }, { "B", "C" }, { "C", "A" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "C" }, { "C", "D" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "C" }, { "B", "D" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "C" }, { "C", "D" } }, { { "B" } } },
                { { { "A", "B" }, { "B", "C" }, { "C", "D" } }, { { "C" } } },
                { { { "A", "B" }, { "B", "C" }, { "C", "D" } }, { { "D" } } },
                { { { "A", "B" }, { "B", "C" }, { "C", "D" } }, { { "E" } } },
                { { { "A", "B" }, { "B", "C" }, { "C", "A" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "C" }, { "B", "D" }, { "C", "A" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "C" }, { "B", "D" }, { "D", "A" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "C" }, { "B", "D" }, { "C", "A" }, { "D", "A" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "D" }, { "D", "B" }, { "B", "A" }, { "A", "C" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "C" }, { "C", "D" }, { "C", "E" }, { "D", "B" } }, { { "A" } } }
        };

        String[] expects = {
                "YUL, YYZ, SFO, HKO, ORD",
                "null",
                "JFK, ATL, JFK, SFO, ATL, SFO",
                "A, B, C, A, C",
                "A, B, C, D",
                "null",
                "null",
                "null",
                "null",
                "null",
                "A, B, C, A",
                "null", //"A, B, C, A, B, D",
                "null", //"A, B, D, A, B, C",
                "null", //"A, B, C, A, B, D, A",
                "A, B, D, B, A, C",
                "null", //"A, B, C, D, B, C, E"
        };

        ReconstructItinerary sv = new ReconstructItinerary();

        for(int i = 0; i < cases.length; i++){
            //List<String> result = sv.itineraryPath(cases[i][0], cases[i][1][0][0]);
            //List<String> result = sv.itineraryPath_2(cases[i][0], cases[i][1][0][0]);

            List<String> result = sv.findItinerary(cases[i][0], cases[i][1][0][0]);
            //List<String> result = sv.findItinerary_2(cases[i][0], cases[i][1][0][0]);

            boolean isMatch = expects[i].equals(Misc.array2String(result).toString());

            System.out.println(String.format("\n%d:  %s\n start from: %s\n%b", i, Misc.array2String(cases[i][0]), cases[i][1][0][0], isMatch));
            if(!isMatch){
                System.out.println(expects[i]);
                System.out.println(Misc.array2String(result).toString());
            }

        }

        boolean flag = true;
        flag = !flag;
    }
}
