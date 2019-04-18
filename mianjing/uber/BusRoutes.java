package fgafa.mianjing.uber;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 *
 * We have a list of bus routes. Each routes[i] is a bus route that the i-th bus repeats forever. For example if routes[0] = [1, 5, 7], this means that the first bus (0-th indexed) travels in the sequence 1->5->7->1->5->7->1->... forever.
 *
 * We start at bus stop S (initially not on a bus), and we want to go to bus stop T. Travelling by buses only, what is the least number of buses we must take to reach our destination? Return -1 if it is not possible.
 *
 * Example:
 * Input:
 * routes = [[1, 2, 7], [3, 6, 7]]
 * S = 1
 * T = 6
 * Output: 2
 * Explanation:
 * The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.
 * Note:
 *
 * 1 <= routes.length <= 500.
 * 1 <= routes[i].length <= 500.
 * 0 <= routes[i][j] < 10 ^ 6.
 *
 */

public class BusRoutes {

    public int numBusesToDestination(int[][] routes, int S, int T) {
        Map<Integer, Set<Integer>> stations = new HashMap<>(); //<station, Set<bus>>
        for(int b = 0; b< routes.length; b++){
            for(int s : routes[b]){
                stations.putIfAbsent(s, new HashSet<>());
                stations.get(s).add(b);
            }
        }

        if(!stations.containsKey(S)){
            return -1;
        }
        if(!stations.containsKey(T)){
            return -1;
        }

        if(S == T){
            return 0;
        }

        //bfs
        Set<Integer> visited = new HashSet<>(); //Set<bus>
        Queue<Integer> queue = new LinkedList<>(); //Queue<bus>
        for(Integer b : stations.get(S)){
            queue.add(b);
            visited.add(b);
        }

        int count = 1;
        int bus;
        while(!queue.isEmpty()){
            for(int i = queue.size(); i > 0; i--){
                bus = queue.poll();

                for(Integer s : routes[bus]){
                    if(s == T){
                        return count;
                    }

                    for(Integer b : stations.get(s)){
                        if(!visited.contains(b)){
                            queue.add(b);
                            visited.add(b);
                        }
                    }
                }
            }

            count++;
        }

        return -1;
    }

    public int numBusesToDestination_n(int[][] routes, int S, int T) {
        Map<Integer, List<Integer>> stations = new HashMap<>(); //<station, List<bus>>
        int length = routes.length;
        for(int b = 0; b< length; b++){
            for(int s : routes[b]){
                stations.putIfAbsent(s, new ArrayList<>());
                stations.get(s).add(b);
            }
        }

        if(!stations.containsKey(S)){
            return -1;
        }
        if(!stations.containsKey(T)){
            return -1;
        }

        if(S == T){
            return 0;
        }

        //build the graph, bus is the vertex, two bus are connected if they share at least one station.
        List<Integer>[] buses = new ArrayList[length];
        for(int i = 0; i < length; i++){
            buses[i] = new ArrayList<>();
        }

        for(List<Integer> list : stations.values()){
            for(int i = list.size() - 1; i > 0; i--){
                for(int j = i - 1; j >= 0; j-- ){
                    buses[list.get(i)].add(list.get(j));
                    buses[list.get(j)].add(list.get(i));
                }
            }
        }

        //bfs
        Set<Integer> visited = new HashSet<>(); //Set<bus>
        Queue<Integer> queue = new LinkedList<>(); //Queue<bus>
        for(Integer b : stations.get(S)){
            queue.add(b);
            visited.add(b);
        }

        Set<Integer> target = new HashSet<>();  //<bus>
        target.addAll(stations.get(T));

        int count = 1;
        int bus;
        while(!queue.isEmpty()){
            for(int i = queue.size(); i > 0; i--){
                bus = queue.poll();

                if(target.contains(bus)){
                    return count;
                }

                for(Integer b : buses[bus]){
                    if(!visited.contains(b)){
                        queue.add(b);
                        visited.add(b);
                    }
                }
            }

            count++;
        }

        return -1;
    }


    @Test public void test(){
        Assert.assertEquals(2, numBusesToDestination(new int[][]{{1, 2, 7}, {3, 6, 7}}, 1, 6));

        Assert.assertEquals(-1, numBusesToDestination(new int[][]{{7,12},{4,5,15},{6},{15,19},{9,12,13}}, 15, 12));

    }

}
