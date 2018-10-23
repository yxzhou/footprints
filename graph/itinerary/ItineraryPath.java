package fgafa.graph.itinerary;

import fgafa.util.Misc;

import java.util.*;

/**
 *
 * Given an unordered list of flights taken by someone, each represented as (origin, destination) pairs, and a starting airport, compute the person's itinerary.
 * If no such itinerary exists, return null. If there are multiple possible itineraries, return the lexicographically smallest one. All flights must be used in the itinerary.
 *
 * For example,
 *   Given the list of flights [{"SFO", "HKO"}, {"YYZ", "SFO"}, {"YUL", "YYZ"}, {"HKO", "ORD"}] and starting airport 'YUL', you should return the list ['YUL", "YYZ", "SFO", "HKO", "ORD'].
 *   Given the list of flights [{"SFO", "COM"}, {"COM", "YYZ"}] and starting airport 'COM', you should return null.
 *   Given the list of flights [{"A", "B"}, {"A", "C"}, {"B", "C"}, {"C", "A"}] and starting airport 'A', you should return the list ['A", "B", "C", "A", "C'] even though ['A", "C", "A", "B", "C'] is also a valid itinerary. However, the first one is lexicographically smaller.
 *
 */

//todo
public class ItineraryPath {

    public List<String> itineraryPath(String[][] flights, String startPoint){

        if(null == flights || flights.length == 0){
            return new ArrayList<>();
        }

        Map<String, PriorityQueue<String>> adjacencies = new HashMap<>();
        Map<String, Integer> edges = new HashMap<>();
        for(String[] flight : flights){
            if(!adjacencies.containsKey(flight[0])){
                adjacencies.put(flight[0], new PriorityQueue<>());
            }
            adjacencies.get(flight[0]).add(flight[1]);

            edges.put(flight[0] + "-" +flight[1], 1);
        }

        Stack<String> stack = new Stack<>();
        LinkedList<String> path = new LinkedList<>();
        String curr = startPoint;
        outer: for(int i = 0; i < flights.length; i++){
            while(!adjacencies.containsKey(curr) || adjacencies.get(curr).isEmpty()){
                if(path.isEmpty()){
                   break outer;
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

        Misc.printArrayList(path);

        int count = 0;
        Iterator<String> iterator = path.listIterator();
        String start = iterator.next();
        String end;
        while(iterator.hasNext()){
            end = iterator.next();

            String edge = start + "-" + end;
            if(!edges.containsKey(edge)){
                return null;
            }

            int n = edges.get(edge);
            if( n == 1){
                count++;
            }

            edges.put(edge, n - 1);

            start = end;
        }

        //System.out.println(count + " " + flights.length);
        return count == flights.length ? path : null;

    }

    public static void main(String[] args){

        String[][][][] cases = {
                //{{"SFO", "HKO"}, {"YYZ", "SFO"}, {"YUL", "YYZ"}, {"HKO", "ORD"}},
                //{{"SFO", "COM"}, {"COM", "YYZ"}, {"COM"}},
                { { { "A", "B" }, { "B", "C" }, { "C", "D" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "C" }, { "B", "D" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "C" }, { "C", "D" } }, { { "B" } } },
                { { { "A", "B" }, { "B", "C" }, { "C", "D" } }, { { "C" } } },
                { { { "A", "B" }, { "B", "C" }, { "C", "D" } }, { { "D" } } },
                { { { "A", "B" }, { "B", "C" }, { "C", "D" } }, { { "E" } } },
                { { { "A", "B" }, { "B", "C" }, { "C", "A" } }, { { "A" } } },
                { { { "A", "B" }, { "A", "C" }, { "B", "C" }, { "C", "A" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "C" }, { "B", "D" }, { "C", "A" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "C" }, { "B", "D" }, { "D", "A" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "C" }, { "B", "D" }, { "C", "A" }, { "D", "A" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "D" }, { "D", "B" }, { "B", "A" }, { "A", "C" } }, { { "A" } } },
                { { { "A", "B" }, { "B", "z" }, { "C", "D" }, { "C", "E" }, { "D", "B" } }, { { "A" } } }
        };

        String[] expects = {
                "A, B, C, D",
                "null",
                "null",
                "null",
                "null",
                "null",
                "A, B, C, A",
                "A, B, C, A, C",
                "A, B, C, A, B, D",
                "A, B, D, A, B, C",
                "A, B, C, A, B, D, A",
                "A, B, D, B, A, C",
                "A, B, C, D, B, C, E"
        };

        ItineraryPath sv = new ItineraryPath();

        for(int i = 0; i < cases.length; i++){
            List<String> result = sv.itineraryPath(cases[i][0], cases[i][1][0][0]);

            System.out.println(String.format("\n%d:  %s\n start from: %s\n %b", i, Misc.array2String(cases[i][0]), cases[i][1][0][0], expects[i].equals(Misc.array2String(result).toString())));
            Misc.printArrayList(result);
        }

    }

}
