package fgafa.leetcode.airbnb;

import org.junit.Test;

import java.util.*;

/**
 * https://leetcode.com/problems/cheapest-flights-within-k-stops/    787
 *
 * There are n cities connected by m flights. Each fight starts from city u and arrives at v with a price w.

 Now given all the cities and flights, together with starting city src and the destination dst, your task is to find the cheapest price from src to dst with up to k stops. If there is no such route, output -1.

 Example 1:
 Input:
 n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 src = 0, dst = 2, k = 1
 Output: 200
 Explanation:
 The graph looks like this:


 The cheapest price from city 0 to city 2 with at most 1 stop costs 200, as marked red in the picture.
 Example 2:
 Input:
 n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 src = 0, dst = 2, k = 0
 Output: 500
 Explanation:
 The graph looks like this:


 The cheapest price from city 0 to city 2 with at most 0 stop costs 500, as marked blue in the picture.
 Note:

 The number of nodes n will be in range [1, 100], with nodes labeled from 0 to n - 1.
 The size of flights will be in range [0, n * (n - 1) / 2].
 The format of each flight will be (src, dst, price).
 The price of each flight will be in the range [1, 10000].
 k is in the range of [0, n - 1].
 There will not be any duplicated flights or self cycles.
 *
 */

public class CheapestFlightsWithKStops {

    /**
     * BFS
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        if(src == dst){
            return 0;
        }
        if(null == flights || flights.length == 0){
            return -1;
        }

        Map<Integer, Map<Integer, Integer>> edges = new HashMap<>();
        for(int[] f : flights){
            edges.putIfAbsent(f[0], new HashMap<>());
            edges.get(f[0]).put(f[1], f[2]);
        }

        if(!edges.containsKey(src)){
            return -1;
        }

        //bfs
        int[][] costs = new int[2][n];
        Arrays.fill(costs[0], Integer.MAX_VALUE);
        costs[0][src] = 0;
        System.arraycopy(costs[0], 0, costs[1], 0, n);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(src);

        for(int i = 0; i <= K; i++){
            for(int j = queue.size(); j > 0; j--){
                int curr = queue.poll();

                Map<Integer, Integer> next = edges.get(curr);
                for(Integer k : next.keySet()){
                    if(costs[1][k] > costs[0][curr] + next.get(k)){
                        costs[1][k] = costs[0][curr] + next.get(k);

                        if(edges.containsKey(k)){
                            queue.add(k);
                        }
                    }
                }
            }
            System.arraycopy(costs[1], 0, costs[0], 0, n);
        }

        return costs[0][dst] == Integer.MAX_VALUE ? -1 : costs[0][dst];
    }

    /**
     * Similar wiht Dijkstra,  with PriorityQuery
     */
    public int findCheapestPrice_n(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, Map<Integer, Integer>> prices = new HashMap<>();
        for (int[] f : flights) {
            prices.putIfAbsent(f[0], new HashMap<>());
            prices.get(f[0]).put(f[1], f[2]);
        }
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> (Integer.compare(a[0], b[0])));
        pq.add(new int[] {0, src, k + 1});

        while (!pq.isEmpty()) {
            int[] top = pq.poll();

            int price = top[0];
            int city = top[1];
            int stops = top[2];

            if (city == dst){
                return price;
            }

            if (stops > 0) {
                Map<Integer, Integer> adj = prices.getOrDefault(city, new HashMap<>());
                for (int a : adj.keySet()) {
                    pq.add(new int[] {price + adj.get(a), a, stops - 1});
                }
            }
        }
        return -1;
    }

    @Test public void test(){

    }

}
