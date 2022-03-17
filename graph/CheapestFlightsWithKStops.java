package graph;


import java.util.*;
import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1029
 * _https://leetcode.com/problems/cheapest-flights-within-k-stops/    787
 *
 * There are n cities connected by m flights. Each fight starts from city u and arrives at v with a price w.
 *
 * Now given all the cities and flights, together with starting city src and the destination dst, your task is to find
 * the cheapest price from src to dst with up to k stops. If there is no such route, output -1.

 Example 1:
 Input: n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]] src = 0, dst = 2, k = 1
 Output: 200
 
 * 
 Example 2:
 Input: n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]] src = 0, dst = 2, k = 0
 Output: 500
 
 * Note: 
 *   The number of nodes n will be in range [1, 100], with nodes labeled from 0 to n - 1. 
 *   The size of flights will be in range [0, n * (n - 1) / 2]. 
 *   The format of each flight will be (src, dst, price). 
 *   The price of each flight will be in the range [1, 10000]. 
 *   k is in the range of [0, n - 1]. 
 *   There will not be any duplicated flights or self cycles.
 *
 * Thoughts:
 * 1 BFS
 * 2 Similar with Dijkstra,  with PriorityQuery
 */

public class CheapestFlightsWithKStops {

    /**
     * @param n: a integer
     * @param flights: a 2D array
     * @param src: a integer
     * @param dst: a integer
     * @param K: a integer
     * @return: return a integer
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        int[][] minPrices = new int[2][n]; // minPrices[i] is the cheapest price to i_th city
        Arrays.fill(minPrices[0], Integer.MAX_VALUE);
        Arrays.fill(minPrices[1], Integer.MAX_VALUE);
        minPrices[0][src] = 0;

        int[][] tickets = new int[n][n];
        for(int[] f : flights){
            tickets[f[0]][f[1]] = f[2];
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(src);

        int curr = 0;
        int next;

        int from;
        int tmp;
        for(int k = 0; k <= K; k++){
            next = (curr ^ 1);

            for(int i = queue.size(); i > 0; i--){
                from = queue.poll();

                minPrices[next][from] = Math.min(minPrices[next][from], minPrices[curr][from]);

                for(int to = 0; to < n; to++){
                    if( tickets[from][to] > 0 && ( tmp = minPrices[curr][from] + tickets[from][to]) < minPrices[next][to] ){
                        minPrices[next][to] = tmp;

                        queue.add(to);
                    }
                }

            }

            curr = next;
        }

        return minPrices[curr][dst] == Integer.MAX_VALUE? -1 : minPrices[curr][dst];
    }
    
    public int findCheapestPrice_2(int n, int[][] flights, int src, int dst, int K) {
        if(src == dst){
            return 0;
        }
        if(null == flights || flights.length == 0){
            return -1;
        }

        Map<Integer, Map<Integer, Integer>> edges = new HashMap<>();
        for(int[] f : flights){
            edges.computeIfAbsent(f[0], x -> new HashMap<>()).put(f[1], f[2]);
        }

        //bfs
        int[][] costs = new int[2][n];
        Arrays.fill(costs[0], Integer.MAX_VALUE);
        Arrays.fill(costs[1], Integer.MAX_VALUE);
        costs[0][src] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(src);

        int curr = 0;
        int next;

        int from;
        int tmp;
        Map<Integer, Integer> nexts;
        for(int i = 0; i <= K; i++){
            next = (curr ^ 1);

            for(int j = queue.size(); j > 0; j--){
                from = queue.poll();
                                
                costs[next][from] = Math.min(costs[next][from], costs[curr][from]);

                if(!edges.containsKey(from)){
                    continue;
                }

                nexts = edges.get(from);
                for(Integer to : nexts.keySet()){
                    if(costs[next][to] > ( tmp = costs[curr][from] + nexts.get(to))){
                        costs[next][to] = tmp;

                        queue.add(to);
                    }
                }
            }
            
            curr = next;
        }

        return costs[curr][dst] == Integer.MAX_VALUE ? -1 : costs[curr][dst];
    }

    /**
     * Similar with Dijkstra,  with PriorityQuery
     */
    public int findCheapestPrice_n(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, Map<Integer, Integer>> prices = new HashMap<>();
        for (int[] f : flights) {
            prices.computeIfAbsent(f[0], x -> new HashMap<>()).put(f[1], f[2]);
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

    public static void main(String[] args){
        int[][][][] inputs = {
            {
                {{1,2,10},{2,0,7},{1,3,8},{4,0,10},{3,4,2},{4,2,10},{0,3,3},{3,1,6},{2,4,5}},
                {{5,0,4,1}},
                {{5}}
            },
            {
                {{0,1,100},{1,2,100},{0,2,500}},
                {{3,0,2,0}},
                {{500}}
            },
            {
                {{3,4,4},{2,5,6},{4,7,10},{9,6,5},{7,4,4},{6,2,10},{6,8,6},{7,9,4},{1,5,4},{1,0,4},{9,7,3},{7,0,5},{6,5,8},{1,7,6},{4,0,9},{5,9,1},{8,7,3},{1,2,6},{4,1,5},{5,2,4},{1,9,1},{7,8,10},{0,4,2},{7,2,8}},
                {{10,6,0,7}},
                {{14}}
            }
        };

        CheapestFlightsWithKStops sv = new CheapestFlightsWithKStops();
        
        for(int[][][] input : inputs){
            System.out.println(String.format("\nn:%d, \nflights:%s, \nsrc:%d, dst:%d, K:%d", input[1][0][0], Misc.array2String(input[0], true), input[1][0][1], input[1][0][2], input[1][0][3] ));
            
            Assert.assertEquals(input[2][0][0], sv.findCheapestPrice(input[1][0][0], input[0], input[1][0][1], input[1][0][2], input[1][0][3]));
            Assert.assertEquals(input[2][0][0], sv.findCheapestPrice_2(input[1][0][0], input[0], input[1][0][1], input[1][0][2], input[1][0][3]));
            Assert.assertEquals(input[2][0][0], sv.findCheapestPrice_n(input[1][0][0], input[0], input[1][0][1], input[1][0][2], input[1][0][3]));
        }
        
    }

}
