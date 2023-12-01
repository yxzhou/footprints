package companyTag.airbnb;


import java.util.*;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1029
 * _https://leetcode.com/problems/cheapest-flights-within-k-stops/    787
 *
 * There are n cities connected by m flights. Each fight starts from city u and arrives at v with a price w.
 *
 * Now given all the cities and flights, together with starting city src and the destination dst, your task is to find
 * the cheapest price from src to dst with up to k stops. If there is no such route, output -1.
 * 
 * Example 1:
 * Input: n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]] src = 0, dst = 2, k = 1
 * Output: 200
 * 
 * Example 2:
 * Input: n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]] src = 0, dst = 2, k = 0
 * Output: 500
 * 
 * Note: 
 *   The number of nodes n will be in range [1, 100], with nodes labeled from 0 to n - 1. 
 *   The size of flights will be in range [0, n * (n - 1) / 2]. 
 *   The format of each flight will be (src, dst, price). 
 *   The price of each flight will be in the range [1, 10000]. 
 *   k is in the range of [0, n - 1]. 
 *   There will not be any duplicated flights or self cycles.
 *
 * Thoughts:
 * 1 BFS, 
 * 2 Similar with Dijkstra,  with PriorityQuery
 * 
 */

public class CheapestFlightsWithKStops {
    
    /**
     * BFS, sparse matrix
     * 
     * Time Complexity O(V * logE)
     * 
     * @param n
     * @param flights
     * @param src
     * @param dst
     * @param k
     * @return 
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        
        Map<Integer, List<Edge>> edges = new HashMap<>();
        for(int[] f : flights){
            edges.computeIfAbsent(f[0], a -> new ArrayList<>()).add(new Edge(f[0], f[1], f[2]));
        }
        
        //BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.add(src);
        
        int[] cur = new int[n]; // distances, curr 
        Arrays.fill(cur, Integer.MAX_VALUE);
        cur[src] = 0;
        int[] next = Arrays.copyOf(cur, n); //distances, next 
        
        int v;
        int w;
        for(int i = k; i >= 0; i--){
            for(int j = queue.size(); j > 0; j--){
                v = queue.poll();
                
                if(!edges.containsKey(v)){
                    continue;
                }
                
                
                for(Edge e : edges.get(v)){
                    if(( w = cur[v] + e.weight) < next[e.to]){
                        next[e.to] = w;
                        
                        queue.add(e.to);
                    }
                }
            }
            
            System.arraycopy(next, 0, cur, 0, n);
        }
        
        return cur[dst] == Integer.MAX_VALUE ? -1 : cur[dst];
    }
    
    class Edge{
        int from;
        int to;
        int weight;
        
        Edge(int from, int to, int weight){
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    /**
     * BFS, not sparse,  E >> V
     * 
     * Time Complexity O(V * V)
     * 
     * @param n: a integer
     * @param flights: a 2D array
     * @param src: a integer
     * @param dst: a integer
     * @param K: a integer
     * @return a integer
     */
    public int findCheapestPrice_2(int n, int[][] flights, int src, int dst, int K) {
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

    public static void main(String[] args){
        int[][][][] inputs = {
            {
                {{4}},
                {{0,1,100},{1,2,100},{2,0,100},{1,3,600},{2,3,200}},
                {{0,3,1}},   
                {{700}}
            },
            {
                {{4}},
                {{0,1,1},{0,2,5},{1,2,1},{2,3,1}},
                {{0,3,1}},   
                {{6}}
            },
            {   {{5}},
                {{1,2,10},{2,0,7},{1,3,8},{4,0,10},{3,4,2},{4,2,10},{0,3,3},{3,1,6},{2,4,5}},
                {{0,4,1}},
                {{5}}
            },
            {
                {{3}},
                {{0,1,100},{1,2,100},{0,2,500}},
                {{0,2,0}},
                {{500}}
            },
            {
                {{10}},
                {{3,4,4},{2,5,6},{4,7,10},{9,6,5},{7,4,4},{6,2,10},{6,8,6},{7,9,4},{1,5,4},{1,0,4},{9,7,3},{7,0,5},{6,5,8},{1,7,6},{4,0,9},{5,9,1},{8,7,3},{1,2,6},{4,1,5},{5,2,4},{1,9,1},{7,8,10},{0,4,2},{7,2,8}},
                {{6,0,7}},
                {{14}}
            }
        };

        CheapestFlightsWithKStops sv = new CheapestFlightsWithKStops();
        
        CheapestFlightsWithKStops2 sv2 = new CheapestFlightsWithKStops2();
        
        
        for(int[][][] input : inputs){
            System.out.println(String.format("\nn:%d, \nflights:%s, \nsrc:%d, dst:%d, K:%d", input[0][0][0], Misc.array2String(input[1], true), input[2][0][0], input[2][0][1], input[2][0][2] ));
            
            
            Assert.assertEquals("sv ", input[3][0][0], sv.findCheapestPrice(input[0][0][0], input[1], input[2][0][0], input[2][0][1], input[2][0][2]));
            Assert.assertEquals("sv ", input[3][0][0], sv.findCheapestPrice_2(input[0][0][0], input[1], input[2][0][0], input[2][0][1], input[2][0][2]));

            Assert.assertEquals("sv2 ", input[3][0][0], sv2.findCheapestPrice(input[0][0][0], input[1], input[2][0][0], input[2][0][1], input[2][0][2]));
              
        }
        
    }

}
