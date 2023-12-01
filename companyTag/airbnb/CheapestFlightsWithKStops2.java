/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * 
 */
public class CheapestFlightsWithKStops2 {
    
    /**
     * Similar with Dijkstra,  with PriorityQuery
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
        for(int[] f : flights ){
            edges.computeIfAbsent(f[0], a -> new ArrayList<>()).add(new Edge(f[0], f[1], f[2]));
        }
        
        Queue<Path> heap = new PriorityQueue<>( (a, b) -> Integer.compare(a.price, b.price) );
        heap.add(new Path(src, 0, 0));

        Path cur;
        k++;
        while(!heap.isEmpty()){
            cur = heap.poll();
            
            if(cur.to == dst){
                return cur.price;
            }
            
            if(!edges.containsKey(cur.to) || cur.stop == k ){
                continue;
            }
            
            for(Edge e : edges.get(cur.to) ){
                heap.add(new Path(e.to, cur.price + e.price, cur.stop + 1));
            }
        }        
                
        return -1;
    }
    
    class Edge{
        int from;
        int to;
        int price;
        
        Edge(int from, int to, int weight){
            this.from = from;
            this.to = to;
            this.price = weight;
        }
    }
    
    class Path{
        //int from; //it's always the src 
        int to;
        int price; // the total price from the "src" to the "to" 
        int stop; // the number of stop from the "src" to the "to" 
        
        Path(int to, int weight, int stop){
            this.to = to;
            this.price = weight;
            this.stop = stop;
        }
    }
    
    
}
