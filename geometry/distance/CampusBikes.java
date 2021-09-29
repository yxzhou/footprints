/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry.distance;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *  _https://www.lintcode.com/problem/1160
 * 
 * On a campus represented as a 2D grid, there are N workers and M bikes, with N≤M
 * Each worker and bike is a 2D coordinate on this grid.
 *
 * Our goal is to assign a bike to each worker. Among the available bikes and workers, we choose the (worker, bike) pair\
 * with the shortest Manhattan distance between each other, and assign the bike to that worker. 
 * (If there are multiple (worker, bike) pairs with the same shortest Manhattan distance, we choose the pair with the smallest worker index; 
 * if there are multiple ways to do that, we choose the pair with the smallest bike index). We repeat this process until there are no available workers.
 * 
 * 
 * 
 */
public class CampusBikes {
    
    /**
     *  Heap to store all Entry<worker, biker, distance>
     * 
     * Time O(mnlogmn) Space O(mn)
     * 
     */
    public int[] assignBikes(int[][] workers, int[][] bikes) {

        int n = workers.length;
        int m = bikes.length;

        PriorityQueue<Entry> minHeap = new PriorityQueue<>(n * m, (e1, e2) -> e1.distance == e2.distance ? (e1.workerId == e2.workerId ? e1.bikeId - e2.bikeId : e1.workerId - e2.workerId) : e1.distance - e2.distance);

        for(int w = 0; w < n; w++){
            for(int b = 0; b < m; b++){
                minHeap.add(new Entry(w, b, Math.abs(workers[w][0] - bikes[b][0]) + Math.abs(workers[w][1] - bikes[b][1])));
            }
        }
        
        int[] result = new int[n];
        Arrays.fill(result, -1);
        boolean[] visited = new boolean[m]; //to mark if the bike is used
        
        Entry top;
        while(!minHeap.isEmpty()){
            top = minHeap.poll();
            
            if(result[top.workerId] != -1 || visited[top.bikeId]){
                continue;
            }
            
            result[top.workerId] = top.bikeId;
            visited[top.bikeId] = true;
        }
        
        return result;
    }

    class Entry {
        int workerId;
        int bikeId;
        int distance;

        Entry(int w, int b, int d){
            workerId = w;
            bikeId = b;
            distance = d;
        }
        
    }
    
//    class Entry implements Comparable<Entry>{
//        int workerId;
//        int bikeId;
//        int distance;
//
//        @Override
//        public int compareTo(Entry e2) {
//            return distance == e2.distance ? (workerId == e2.workerId ? bikeId - e2.bikeId : workerId - e2.workerId) : distance - e2.distance;
//        }
//        
//    }
    
    
    /**
     * Heap[i] to store all Pair<biker, distance> for worker i
     * 
     * Time O(n*mlogm + nn  ) Space O(mn)
     * 
     */
    public int[] assignBikes_cache(int[][] workers, int[][] bikes) {
        int n = workers.length;
        int m = bikes.length;
    
        Queue<PriorityQueue<Entry>> queue = new LinkedList<>(); //<workerId, Heap<Entry>>
        PriorityQueue<Entry> heap;
        for(int w = 0; w < n; w++){
            heap = new PriorityQueue<>(m, (e1, e2) -> e1.distance == e2.distance ? e1.bikeId - e2.bikeId : e1.distance - e2.distance);
            queue.add(heap);

            for(int b = 0; b < m; b++){
                heap.add(new Entry(w, b, Math.abs(workers[w][0] - bikes[b][0]) + Math.abs(workers[w][1] - bikes[b][1])));
            }
        }
        
        int[] result = new int[n];
        Arrays.fill(result, -1);
        boolean[] visited = new boolean[m]; //to mark if the bike is used
        
        Entry min = new Entry(-1, -1, Integer.MAX_VALUE);
        Entry top;
        while(!queue.isEmpty()){
            for(int i = queue.size(); i > 0; i--){
                heap = queue.poll();
                if(result[heap.peek().workerId] != -1 ){
                    continue;
                }
                queue.add(heap);
                
                while(!heap.isEmpty() && visited[heap.peek().bikeId] ){
                    heap.poll();
                }

                top = heap.peek();
                if(top.distance < min.distance){
                    min = top;
                }
            }
            
            if(min.distance != Integer.MAX_VALUE){
                //System.out.println( min.workerId + " " + min.bikeId + " " + min.distance);
                result[min.workerId] = min.bikeId;
                visited[min.bikeId] = true;
                min.distance = Integer.MAX_VALUE;
            }
        }
  
        return result;
    }
}
