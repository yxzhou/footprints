/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

/**
 *
 * Given a 2D array, and each line has only 0 and 1, the front part is 0, and the latter part is 1. Find the number of columns in the leftmost 1 of all the rows in the array.

 * Notes:
 * The number of rows in the array and the number of columns do not exceed 1000
 * In order to limit the time complexity, your program will run 50000 times
 *
 * Example
 * Given arr = [[0,0,0,1],[1,1,1,1]], return 0.
 * Explanation:   Arr[1][0] is the leftmost 1 in all rows and its column is 0.
 * 
 * Given arr = [[0,0,0,1],[0,1,1,1]], return 1.
 * Explanation:  Arr[1][1] is the leftmost 1 in all rows and its column is 1.
 * 
 */
public class LeftMostColumnOne {
    
    
    /**
     * define n as the row number, m as the column number
     * Time O( n*m ), Space O(1)
     */
    public int getColumn(int[][] arr) {
        if(arr == null){
            return -1;
        }

        int n = arr.length;
        int m = arr[0].length;

        int result = m; 

        for(int r = 0; r < n; r++){
            for(int c = 0; c < result; c++){
                if(arr[r][c] == 1){
                    result = c;
                    break;
                }
            }
        }

        return result == m ? -1 : result;
    }
    
    /**
     * define n as the row number, m as the column number
     * Time O( m ), Space O(1)
     */
    public int getColumn_n(int[][] arr) {
        if(arr == null){
            return -1;
        }

        int n = arr.length;
        int m = arr[0].length;

        int result = m - 1; 

        for(int r = 0; r < n && result >= 0; r++){ // note result >= 0 
            while( result >= 0 && arr[r][result] == 1 ){
                result--;
            }
        }

        result++;
        return result == m ? -1 : result;
    }
}
