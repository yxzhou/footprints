/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy;

/**
 *
 * A matrix is Toeplitz if every diagonal from top-left to bottom-right has the same element.
 * Now given an M x N matrix, return True if and only if the matrix is Toeplitz.
 * 
 * 
 * matrix will be a 2D array of integers.
 * matrix will have a number of rows and columns in range [1, 20].
 * matrix[i][j] will be integers in range [0, 99].
 * 
 * Example 1:
 * Input: matrix = [[1,2,3,4],[5,1,2,3],[9,5,1,2]]
 * Output: True
 * Explanation:
 *  1234
 *  5123
 *  9512
 * 
 * In the above grid, the diagonals are "[9]", "[5, 5]", "[1, 1, 1]", "[2, 2, 2]", "[3, 3]", "[4]", and in each diagonal
 * all elements are the same, so the answer is True.
 * 
 * Example 2:
 * Input: matrix = [[1,2],[2,2]]
 * Output: False
 * Explanation:
 * The diagonal "[1, 2]" has different elements.
 * 
 */
public class ToeplitzMatrix {
    /**
     * @param matrix: the given matrix
     * @return True if and only if the matrix is Toeplitz
     */
    public boolean isToeplitzMatrix(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int curr;
        for(int r = 0, c = 0; r < n; r++){
            curr = matrix[r][c];

            for(int i = r + 1, j = c + 1; i < n && j < m; i++, j++){
                if( curr != matrix[i][j] ){
                    return false;
                }
            }
        }

        for(int r = 0, c = 1; c < m; c++){
            curr = matrix[r][c];

            for(int i = r + 1, j = c + 1; i < n && j < m; i++, j++){
                if( curr != matrix[i][j] ){
                    return false;
                }
            }
        }

        return true;
    }
    
    public boolean isToeplitzMatrix_2(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        
        for(int r = 1; r < n; r++){
            for(int c = 1; c < m; c++){
                if(matrix[r - 1][c - 1] != matrix[r][c]){
                    return false;
                }
            }
        }
        
        return true;
    }
    
}
