package sorting.kth;

import java.util.PriorityQueue;
import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1272
 * 
 * Given a m * n matrix where each of the rows and columns are sorted in ascending order, find the kth smallest element
 * in the matrix.
 *
 * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
 *
 * Example1
 * [[ 1, 5, 9],
 *  [10, 11, 13],
 *  [12, 13, 15]]  k = 8 
 * Output: 13 
 * 
 * Example2
 * Input: [[-5]] 1 
 * Output: -5 
 * 
 * Challenge:
 *    If k << n^2, what's the best algorithm? How about k ~ n^2?
 * 
 * Thoughts:
 * the up-left is the minimum, then second min is between matrix[0][1] and matrix[1][0] 
 * 
 * m1) one by one, with two minHeap, 
 * Time O(k * logn), when k << n^2, it's O(k * log k)
 * 
 * there is n^2 elements in the matrix, if k > ((n^2) / 2),  kth smallest -> (n^2 - k + 1)_the biggest element.
 * 
 * m2) Because the rows and column are sorted, the up-left is the minimum, the bottom-right is the maximum, 
 *  so to a number x, it takes O(n) to find out how many elements <= x. 
 *  binary search in [ matrix[0][0], matrix[n - 1][n - 1] ],  
 * 
 *  Time O( n * log (range) ), range = matrix[n - 1][n - 1] -  matrix[0][0]
 *   
 *
 * if k << n^2, m1 is O(k * log k), m1 is better.
 * if k close to n^2,  change it to (n^2 - k + 1)_th biggest element, m1 is better.
 * if range << n and k is close to n,  m2 is better.
 *
 */

public class KthSmallestOfSortedMatrix {

    /**
     * Time O(k * logn), when k << n^2, it's O(k * log k)
     * 
     * @param matrix: List[List[int]]
     * @param k: a integer
     * @return: return a integer
     */
    int[][] diffs = {{0, 1}, {1, 0}};

    public int kthSmallest(int[][] matrix, int k) {
        //assert matrix == null || k < 1 || matrix.length * matrix.length < k;
        
        int n = matrix.length;

        PriorityQueue<Cell> minHeap = new PriorityQueue<>( (a,b) -> Integer.compare(matrix[a.row][a.col], matrix[b.row][b.col] ) );
        minHeap.add(new Cell(0, 0));

        boolean[][] visited = new boolean[n][n];

        Cell top;
        int nr, nc;
        while( k > 1 ){
            top = minHeap.poll();
            k--;

            for(int[] diff : diffs){
                nr = top.row + diff[0];
                nc = top.col + diff[1];

                if(nr < n && nc < n && !visited[nr][nc] ){
                    minHeap.add(new Cell(nr, nc));
                    visited[nr][nc] = true;
                }
            }

        }

        top = minHeap.poll();
        return matrix[top.row][top.col];
    }     
    
    class Cell{
        int row;
        int col;
        
        Cell(int row, int col){
            this.row = row;
            this.col = col;
        }
    }
 
    /**
     * binary search, find a number x, that smaller or equal   
     * Time O( n * log(range) ), range is matrix[n - 1][n - 1] - matrix[0][0]
     * 
     * @param matrix
     * @param k
     * @return 
     */
    public int kthSmallest_BS(int[][] matrix, int k){
        //assert matrix == null || k < 1 || matrix.length * matrix.length < k;
        
        int n = matrix.length;
        
        int low = matrix[0][0];
        int high = matrix[n - 1][n - 1];
        
        int mid;
        while(low + 1 < high){
            mid = low + (high - low) / 2;
            
            if(countSmallerAndEqual(matrix, mid) < k ){
                low = mid;
            } else {
                high = mid;
            }
        }
        
        if(countSmallerAndEqual(matrix, low) >= k){
            return low;
        }
        
        return high;
    }
    
    public int kthSmallest_BS2(int[][] matrix, int k){
        //assert matrix == null || k < 1 || matrix.length * matrix.length < k;
        
        int n = matrix.length;
        
        int low = matrix[0][0];
        int high = matrix[n - 1][n - 1];
        
        int mid;
        while(low < high){
            mid = low + (high - low) / 2;
            
            if(countSmallerAndEqual(matrix, mid) < k ){
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        
        return low;
    }
    
    /**
     * find how many elements are <= val in the matrix, 
     * 
     * Time complecity O(m + n)
     * 
     * @param matrix, a sorted matrix
     * @param target
     * @return 
     */
    private int countSmallerAndEqual(int[][] matrix, int target){
        int n = matrix.length;
        
        int count = 0;
        
        //start from the left_bottom corner
        for(int r = 0, c = n - 1; r < n && c >= 0; ){
            if(matrix[r][c] > target){
                c--;
            }else{ // <= val
                count += c + 1;
                
                r++;
            }
        }
        
        return count;
    }
    
    public static void main(String[] args){
        int[][][][] inputs = {
            //{ matrix, {{k}, {expect}} }
            {
                {{-5}}, {{1}, {-5}}
            },
            {
                {
                    { 1,  5,  9},
                    {10, 11, 13},
                    {12, 13, 15}
                }, {{8}, {13}}
                
            },
        };
        
        KthSmallestOfSortedMatrix sv = new KthSmallestOfSortedMatrix();
        
        for(int[][][] input : inputs){
            System.out.println(String.format("\n %s, \n--- %d", Misc.array2String(input[0]), input[1][0][0]));
            
            Assert.assertEquals(input[1][1][0], sv.kthSmallest(input[0], input[1][0][0]));
            Assert.assertEquals(input[1][1][0], sv.kthSmallest_BS(input[0], input[1][0][0]));
            Assert.assertEquals(input[1][1][0], sv.kthSmallest_BS2(input[0], input[1][0][0]));
        }
        
    }
}
