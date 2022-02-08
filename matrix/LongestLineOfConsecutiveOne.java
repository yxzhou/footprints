/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package matrix;

/**
 * _https://www.lintcode.com/problem/875
 * 
 * Given a 01 matrix, find the longest line of consecutive 1 in the matrix. The line could be horizontal, vertical,
 * diagonal or anti-diagonal.
 *
 * The number of elements in the given matrix will not exceed 10,000. 
 * 
 * 
 * Example 1:
 * Input: 
 * [[0,1,1,0], 
 *  [0,1,1,0], 
 *  [0,0,0,1]] 
 * Output: 3 Explanation: (0,1) (1,2) (2,3) 
 * 
 * Example 2:
 * Input: 
 * [[0,0],
 *  [1,1]] 
 * Output: 2
 *
 * Thoughts: 
 *   Define n = M.length, m = M[0].length;
 *   m1) check row by row, column by column, diagonal by diagonal, anti-diagonal by anti-diagonal
 *   Time O(n*m*4 ), Space O(1) 
 *   m2) with array to store heights, width, diagonals and antiDiagonals
 *   Time O(n * m) Space O(n + m)
 */
public class LongestLineOfConsecutiveOne {
    /**
     * 
     * Time O(n * m) Space O(n + m)
     * 
     * @param M: the 01 matrix
     * @return the longest line of consecutive one in the matrix
     */
    public int longestLine(int[][] M) {
        if(M == null || M.length == 0 || M[0].length == 0 ){
            return 0;
        }

        int max = 0;
        int n = M.length;
        int m = M[0].length;
        
        int[] heights = new int[m]; //vertical
        int[] widths = new int[n]; //horizontal 
        int[] diagonals = new int[n + m];
        int[] antiDiagonals = new int[n + m]; 

        for(int r = 0; r < n; r++ ){
            for(int c = 0; c < m; c++ ){
                if(M[r][c] == 1){
                    heights[c] += 1;
                    widths[r] += 1;
                    diagonals[r - c + m] += 1;
                    antiDiagonals[r + c] += 1;

                    max = Math.max(max, Math.max(heights[c], widths[r]));
                    max = Math.max(max, Math.max(diagonals[r - c + m], antiDiagonals[r + c]));
                }else{
                    heights[c] = 0;
                    widths[r] = 0;
                    diagonals[r - c + m] = 0;
                    antiDiagonals[r + c] = 0;
                }
            }
        }

        return max;
    }
    
    /**
     * Define n = M.length, m = M[0].length;
     * Time O(n * m) Space O(n + m)
     * 
     * @param M: the 01 matrix
     * @return the longest line of consecutive one in the matrix
     */
    public int longestLine_2(int[][] M) {
        if(M == null || M.length == 0 || M[0].length == 0 ){
            return 0;
        }

        int max = 0;
        int n = M.length;
        int m = M[0].length;
        
        int localMax;
        
        //horizontal, check row by row
        for(int r = 0; r < n; r++){
            localMax = 0;
            
            for(int c = 0; c < m; c++){
                if(M[r][c] == 0){
                    localMax = 0;
                } else {
                    localMax++;
                    
                    max = Math.max(max, localMax);
                }
            }
        }
        
        //vertical, check column by column
        for(int c = 0; c < m; c++){
            localMax = 0;
            
            for(int r = 0; r < n; r++){
                if(M[r][c] == 0){
                    localMax = 0;
                } else {
                    localMax++;
                    
                    max = Math.max(max, localMax);
                }
            }
        }
        
        //diagonal, 
        for(int p = 0; p < n + m; p++){
            localMax = 0;
            for(int r = 0, c; r < n; r++){
                c = r - p + m;
                
                if(c < 0 || c >= m){
                    continue;
                }
                
                if(M[r][c] == 0){
                    localMax = 0;
                } else {
                    localMax++;
                    
                    max = Math.max(max, localMax);
                }
            }
                
            localMax = 0;
            for(int r = 0, c; r < n; r++){
                c = p - r;
                
                if(c < 0 || c >= m){
                    continue;
                }
                
                if(M[r][c] == 0){
                    localMax = 0;
                } else {
                    localMax++;
                    
                    max = Math.max(max, localMax);
                }
            }
            
        }

        return max;
    }
}
