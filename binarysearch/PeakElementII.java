package binarysearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/390
 * 
 * Given an integer matrix A which has the following features :
 *   The numbers in adjacent positions are different.
 *   The matrix has n rows and m columns.
 *   For all i < m, A[0][i] < A[1][i] && A[n - 2][i] > A[n - 1][i].
 *   For all j < n, A[j][0] < A[j][1] && A[j][m - 2] > A[j][m - 1].
 *   
 * We define a position (i, j) is a peek if:
 *   A[j][i] > A[j+1][i] && A[j][i] > A[j-1][i] && A[j][i] > A[j][i+1] && A[j][i] > A[j][i-1]
 *   
 * Find a peak element in this matrix. Return the index of the peak.
 * Guarantee that there is at least one peak, and if there are multiple peaks, return any one of them.
 * 
 * Example 1:
 * Input: 
    [
      [1, 2, 3, 6,  5],
      [16,41,23,22, 6],
      [15,17,24,21, 7],
      [14,18,19,20,10],
      [13,14,11,10, 9]
    ]
 * Output: [1,1]
 * Explanation: [2,2] is also acceptable. The element at [1,1] is 41, greater than every element adjacent to it.
 * 
 * Example 2:
 * Input: 
    [
      [1, 5, 3],
      [4,10, 9],
      [2, 8, 7]
    ]
 * Output: [1,1]
 * Explanation: There is only one peek.
 *
 * Challenge 
 *   Solve it in O(n+m) time.
 *   If you come up with an algorithm that you thought it is O(n log m) or O(m log n), can you prove it is actually 
 *   O(n+m) or propose a similar but O(n+m) algorithm?
 *
 * Thoughts:
 *    This is 2D Peak. It's 1D in the PeakElement. The binary search method in 1D can only pick a peak instead of the 
 *  largest grid on the row or column.
 * 
 *   m1) greedy. 
 *   Because the numbers in adjacent positions are different. a greedy way is always go to the higher until a peak. 
 *   The worst case is O(n*m), 
 *   when there are multiple higher, random pick one, the time complexity is O(n + m)
 * 
 *   m2) binary search on column, 
 *   m3) binary search on column and row     
 * 
 */

public class PeakElementII {

       
    /**
     * define r0 as the first row, r1 as the last row,
     *   mid as the mid row, 
     *   traversal the mid row, find the biggest grid A[mid, c] on horizonal, now check the vertical, 
     *   if( A[mid][c] < A[mid + 1][c]), the bigger gridd is in the down half part. set r0 as mid;
     *   if( mid > 0 && A[mid][c] < A[mid - 1][c]), the bigger grid is in the up half part. set r1 as mid;
     *   or A[mid][c] is the peak. 
     *   loop --
     * 
     * Time O( mlogn ),  n is row number, m is column number
     * 
     * @param A
     * @return 
     */
    public List<Integer> findPeakII_m2(int[][] A) {

        int m = A[0].length;
        
        int up = 0;
        int down = A.length - 1;
        int mid;
        int col; // the max on the row
        while(up < down){
            mid = up + (down - up) / 2;
            
            col = 0;
            for(int i = 0; i < m; i++){
                if(A[mid][col] < A[mid][i]){
                    col = i;
                }
            }
            
            if(A[mid][col] < A[mid + 1][col]){
                up = mid;
            }else if(mid > 0 && A[mid][col] < A[mid - 1][col]) {
                down = mid;
            }else { // A[mid][col] > A[mid + 1][col] && A[mid][col] > A[mid - 1][col]
                return Arrays.asList(mid, col);
            }
        }
        
        return Collections.EMPTY_LIST ;
    }
    
    
    /**
     * Time O(n + m) 
     * 
     * @param A
     * @return 
     */
    public List<Integer> findPeakII_m3(int[][] A) {
        
        int n = A.length;
        int m = A[0].length;
        
        int up = 0;
        int down = n;
        int left = 0;
        int right = m;

        int r; // row
        int c; //col
        while(up < down && left < right){
            //by row
            r = up + (down - up) / 2;
            
            c = left;
            for(int i = left; i < right; i++){
                if(A[r][c] < A[r][i]){
                    c = i;
                }
            }
            
            if (A[r][c] < A[r + 1][c]) {
                up = r;
            } else if (r > 0 && A[r][c] < A[r - 1][c]) {
                down = r;
            } else { //A[midRow][col] > A[midRow + 1][col] && A[midRow][col] > A[midRow - 1][col]
                return Arrays.asList(r, c);
            }
            
            //by column
            c = left + (right - left) / 2;
            
            r = up;
            for (int i = up; i < down; i++) {
                if (A[r][c] < A[i][c]) {
                    r = i;
                }
            }
            
            if (A[r][c] < A[r][c + 1]) {
                left = c;
            } else if (c > 0 && A[r][c] < A[r][c - 1]) {
                right = c;
            } else {//A[row][midCol] > A[row][midCol + 1] && A[row][midCol] > A[row][midCol - 1]
                return Arrays.asList(r, c);
            }
        }
        
        //Guarantee that there is at least one peak
        return Collections.EMPTY_LIST;
    }
    
    /**
     * Wrong !!
     * 
     * @param A: An integer matrix
     * @return The index of the peak
     */
    public List<Integer> findPeakII_32(int[][] A) {
        
        int n = A.length;
        int m = A[0].length;

        int up = 0; 
        int down = n - 1;
        int left = 0;
        int right = m - 1;
        
        int r;
        int c;
        while( up < down && left < right  ){ 
            r = up + (left - up) / 2;
            c = findPeakOnRow(A, r, left, right);
            
            if( A[r][c] < A[r + 1][c] ){
                up = r;
            }else if( r > 0 && A[r][c] < A[r - 1][c] ){
                down = r;
            }else{
                return Arrays.asList(r, c);
            }
            
            c = left + (right - left) / 2;
            r = findPeakOnColumn(A, c, up, down);
            
            if (A[r][c] < A[r][c + 1]) {
                left = c;
            } else if (c > 0 && A[r][c] < A[r][c - 1]) {
                right = c;
            } else {//A[row][midCol] > A[row][midCol + 1] && A[row][midCol] > A[row][midCol - 1]
                return Arrays.asList(r, c);
            }

            //System.out.println(String.format("nr = %d, r = %d, c = %d", nr, r, c));    
        }

        //Guarantee that there is at least one peak
        return Collections.EMPTY_LIST;
    }

    private int findPeakOnRow(int[][] A, int r, int left, int right){

        int mid;
        while(left + 1 < right){
            mid = left + (right - left) / 2;

            if(A[r][mid] < A[r][mid + 1] ){
                left = mid;
            }else{
                right = mid;
            }
        }

        return A[r][left] > A[r][right]? left : right;
    }

    private int findPeakOnColumn(int[][] A, int c, int up, int down){

        int mid;
        while(up + 1 < down){
            mid = up + (down - up) / 2;

            if(A[mid][c] < A[mid + 1][c]){
                up = mid;
            }else{
                down = mid;
            }
        }

        return A[up][c] > A[down][c]? up : down; 
    }
    
    /**
     * Time O(n + m)
     * 
     * @param A
     * @return 
     */
    public List<Integer> findPeakII_greedy_n(int[][] A) {
        
        int n = A.length;
        int m = A[0].length;
        Random random = new Random();
        
        int r = -1;
        int c = -1;
        int nr = n/2; // (0, 0) not work
        int nc = m/2;
        
        int count;
        while( r != nr || c != nc ) {
            r = nr;
            c = nc;
            
            count = 0;

            //up
            if(r > 0 && A[r][c] < A[r - 1][c]){
                count++;
                
                if(random.nextInt(count) == 0){
                    nr = r - 1;
                    continue;
                }
            }
            
            //down
            if(r + 1 < n && A[r][c] < A[r + 1][c] ){
                count++;
                
                if(random.nextInt(count) == 0){
                    nr = r + 1;
                    continue;
                }
            }
            
            //left
            if(c > 0 && A[r][c] < A[r][c - 1] ){
                count++;
                
                if(random.nextInt(count) == 0){
                    nc = c - 1;
                    continue;
                }
            }
            
            //right
            if(c + 1 < m && A[r][c] < A[r][c + 1] ){
                count++;
                
                if(random.nextInt(count) == 0){
                    nc = c + 1;
                }
            }
            
        }
        
        return Arrays.asList(r, c);
    }
   
    /**
     * Time O(n * m)
     * 
     * @param A
     * @return 
     */
    public List<Integer> findPeakII_greedy(int[][] A) {
        
        int n = A.length;
        int m = A[0].length;
        
        int r = -1;
        int c = -1;
        int nr = n/2;
        int nc = m/2;
        while( r != nr || c != nc ) {
            r = nr;
            c = nc;

            if(r > 0 && A[r][c] < A[r - 1][c]){ //up 
                nr = r - 1;
            }else if(r + 1 < n && A[r][c] < A[r + 1][c] ){ //down
                nr = r + 1;
            }else if(c > 0 && A[r][c] < A[r][c - 1] ){//left
                nc = c - 1;
            }else if(c + 1 < m && A[r][c] < A[r][c + 1] ){//right
                nc = c + 1;
            }
        }
        
        return Arrays.asList(r, c);
    }
    
    
    public static void main(String[] args){
        
        int[][][][] inputs = {
            {
                {
                    {1, 5, 3},
                    {4,10, 9},
                    {2, 8, 7}
                },
                {{1, 1}}
            },
            {
                {
                    {1,  2,  3,  6,  5},
                    {16, 41, 23, 22, 6},
                    {15, 17, 22, 21, 7},
                    {14, 18, 19, 20, 10},
                    {13, 14, 11, 10, 9}
                },
                {{1, 1}}
            },
            {
                {
                    {1,  2,  3,  4,  5},
                    {16, 18, 19, 20, 6},
                    {15, 25, 26, 21, 7},
                    {14, 24, 23, 22, 8},
                    {13, 12, 11, 10, 9}
                },
                {{2, 2}}
            },
            {
                {
                    {0, 0, 0, 0, 0},
                    {0, 1, 2, 7, 0},
                    {0, 4, 3, 9, 0},
                    {0, 6, 7, 8, 0}, 
                    {0, 0, 0, 0, 0}
                },
                {{2, 3}}
            },
            {
                {
                    {0, 1, 2, 3, 2},
                    {1, 2, 4, 5, 4},
                    {2, 3, 6, 4, 3},
                    {3, 8, 7, 3, 2},
                    {2, 7, 5, 2, 1}
                },
                {{3, 1}}
            }
        };
        
        PeakElementII sv = new PeakElementII();
        
        for(int[][][] input : inputs){
            System.out.println( String.format("\n%s", Misc.array2String(input[0], false)) );
            
            Assert.assertEquals( Misc.array2String(input[1][0]).toString(), Misc.array2String(sv.findPeakII_greedy(input[0])).toString() );
            Assert.assertEquals( Misc.array2String(input[1][0]).toString(), Misc.array2String(sv.findPeakII_greedy_n(input[0])).toString() );
            
            Assert.assertEquals(Misc.array2String(input[1][0]).toString(), Misc.array2String(sv.findPeakII_m3(input[0])).toString() );
            //Assert.assertEquals( Misc.array2String(input[1][0]).toString(), Misc.array2String(sv.findPeakII_32(input[0])).toString() );
            
            Assert.assertEquals(Misc.array2String(input[1][0]).toString(), Misc.array2String(sv.findPeakII_m2(input[0])).toString() );
        }
        
    }
    
    
}
