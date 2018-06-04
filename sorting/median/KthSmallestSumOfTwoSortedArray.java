package fgafa.sorting.median;

import java.util.PriorityQueue;

import fgafa.sorting.median.KthSmallestOfSortedMatrix.Node;

/**
 * 
 * Find the kth smallest number in sum of two sorted array.

    Example
    Given two sortedarrays:
    
    [
      [1 ,5 ,7],
      [3 ,7 ,8],
    ]
    
    k = 1, return 1 + 3
    k = 2, return 1 + 7 or 3 + 5
    k = 4, 1 + 8
    
    solution
    
      1  5  7
    3 
    7
    8
 *
 */

public class KthSmallestSumOfTwoSortedArray {

    public int kthSmallest_n(int[] arr1, int[] arr2, int k) {
        //check
        if(null == arr1 || 0 == arr1.length || null == arr2 || 0 == arr2.length){
            return Integer.MAX_VALUE;
        }
        
        int m = arr1.length;
        int n = arr2.length;
        
        if( k < 1 || k > m + n){
            return Integer.MAX_VALUE;
        }
        
        int result = Integer.MAX_VALUE;
        PriorityQueue<Node> minHeap=new PriorityQueue<Node>();
        minHeap.offer(new Node(0, 0, arr1[0] + arr2[0]));
        
        boolean[][] isVisited = new boolean[m][n]; //default all are false
        isVisited[0][0] = true;
        
        for(int count = 0; count < k; count++){
            Node p = minHeap.poll();
            result = p.value;
            
            if(p.i < m - 1 && !isVisited[p.i+1][p.j]){
                minHeap.offer(new Node(p.i + 1, p.j, arr1[p.i + 1] + arr2[p.j]));
                isVisited[p.i+1][p.j] = true;
            }
                
            if(p.j < n - 1 && !isVisited[p.i][p.j + 1]){
                minHeap.offer(new Node(p.i, p.j + 1, arr1[p.i] + arr2[p.j + 1]));
                isVisited[p.i][p.j + 1] = true;
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
