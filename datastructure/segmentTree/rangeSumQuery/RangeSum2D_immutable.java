package datastructure.segmentTree.rangeSumQuery;

/**
 * 
 * Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).

    Range Sum Query 2D
    The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.
    
    Example:
    Given matrix = [
      [3, 0, 1, 4, 2],
      [5, 6, 3, 2, 1],
      [1, 2, 0, 1, 5],
      [4, 1, 0, 1, 7],
      [1, 0, 3, 0, 5]
    ]
    
    sumRegion(2, 1, 4, 3) -> 8
    sumRegion(1, 1, 2, 2) -> 11
    sumRegion(1, 2, 2, 4) -> 12
    Note:
    You may assume that the matrix does not change.
    There are many calls to sumRegion function.
    You may assume that row1 ≤ row2 and col1 ≤ col2.
 *
 */

public class RangeSum2D_immutable {
    int[][] sums;

    public RangeSum2D_immutable(int[][] matrix) {
        if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
            return;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        sums = new int[m+1][n+1];
        
        int rowSum = 0;
        for(int row = 0; row < m; row++){
            rowSum = 0;
            for(int col = 0; col < n; col++){
                rowSum += matrix[row][col];
                sums[row + 1][col + 1] = sums[row][col + 1] + rowSum;
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return sums[row2 + 1][col2 + 1] - sums[row1][col2 + 1] - sums[row2+1][col1] + sums[row1][col1];
    }
    
}


// Your NumMatrix object will be instantiated and called as such:
// NumMatrix numMatrix = new NumMatrix(matrix);
// numMatrix.sumRegion(0, 1, 2, 3);
// numMatrix.sumRegion(1, 2, 3, 4);
