package sorting.median;

import java.util.PriorityQueue;

/**
 * 
 * Find the kth smallest number in at row and column sorted matrix.

    Example
    Given k = 4 and a matrix:
    
    [
      [1 ,5 ,7],
      [3 ,7 ,8],
      [4 ,8 ,9],
    ]
    return 5
    
    Challenge
    O(k log n), n is the maximal number in width and height.
 *
 */

public class KthSmallestOfSortedMatrix {


    /**
     * @param matrix: a matrix of integers
     * @param k: an integer
     * @return: the kth smallest number in the matrix
     */
    /* Time O(k * logn),  Space O(k) + O(n*m) , n is the maximal number in width and height,*/
    public int kthSmallest_n(int[][] matrix, int k) {

        //check
        if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
            return Integer.MAX_VALUE;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        if( k < 1 || k > m * n){
            return Integer.MAX_VALUE;
        }
        
        int result = Integer.MAX_VALUE;
        PriorityQueue<Node> minHeap=new PriorityQueue<Node>();
        minHeap.offer(new Node(0, 0, matrix[0][0]));
        
        boolean[][] isVisited = new boolean[m][n]; //default all are false
        isVisited[0][0] = true;
        
        Node p;
        for(int count = 0; count < k; count++){
            p = minHeap.poll();
            result = p.value;
            
            if(p.i < m - 1 && !isVisited[p.i+1][p.j]){
                minHeap.offer(new Node(p.i + 1, p.j, matrix[p.i + 1][p.j]));
                isVisited[p.i+1][p.j] = true;
            }
                
            if(p.j < n - 1 && !isVisited[p.i][p.j + 1]){
                minHeap.offer(new Node(p.i, p.j + 1, matrix[p.i][p.j + 1]));
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
    
    /* Time O(k * logn),  Space O(m*n - (m + n)) */
    public int kthSmallest(int[][] matrix, int k) {
        //check
        if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
            return Integer.MAX_VALUE;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        if( k < 1 || k > m * n){
            return Integer.MAX_VALUE;
        }
        
        int result = Integer.MAX_VALUE;
        PriorityQueue<Integer> minHeap=new PriorityQueue<Integer>();
        
        for(int sum = 1; sum <= k; sum++){
            for(int i = 0, j = sum - 1; i < sum; i++, j--){
                if(i >= 0 && i < m && j >=0 && j < n){
                    minHeap.offer(matrix[i][j]);
                }
            }
            
            result = minHeap.poll();
        }
        
        return result;
    }
}
