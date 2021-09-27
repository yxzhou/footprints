package sorting.median;

import java.util.PriorityQueue;

/**
 * 
 * Find the kth smallest number in N sorted arrays.

    Example
    Given M sorted arrays:
    
    [
      [1 ,5 ,7],
      [4 ,8 ,9],
      [3 ,7 ,8],
    ]
    k = 4, return 5
    
    Challenge
    O(k log n), n is the number of arrays.
 *
 */

public class KthSmallestOfNSortedArray {

    /* Time O(k * logn),  Space O(n), n is the number of arrays*/
    public int kthSmallest_n(int[][] arrays, int k) {

        //check
        if(null == arrays || 0 == arrays.length || k < 1){
            return Integer.MAX_VALUE;
        }
        
        int total = 0;
        for(int i = 0; i < arrays.length; i++){
            total += arrays[i].length;
        }
        if( k > total ){
            return Integer.MAX_VALUE;
        }
        
        int result = Integer.MAX_VALUE;
        PriorityQueue<Node> minHeap=new PriorityQueue<Node>();
        
        for(int i = 0; i < arrays.length; i++){
            minHeap.offer(new Node(i, 0, arrays[i][0]));
        }
        
        for(int count = 0; count < k; count++){
            Node p = minHeap.poll();
            result = p.value;
                
            if(p.j < arrays[p.i].length - 1){
                minHeap.offer(new Node(p.i, p.j + 1, arrays[p.i][p.j + 1]));
            }
        }
        
        return result;
    }
    
    class Node implements Comparable<Node>{
        int value;
        int i;
        int j;
        
        public Node(int i, int j, int value){
            this.i = i;
            this.j = j;
            this.value = value;
        }
        
        @Override
        public int compareTo(Node other) {
            return this.value - other.value;
        }
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
