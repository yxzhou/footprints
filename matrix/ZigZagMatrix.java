package matrix;

import util.Misc;

/**
 * 
 * Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in ZigZag-order.

Example
Given a matrix:

[
  [1, 2,  3,  4],
  [5, 6,  7,  8],
  [9,10, 11, 12]
]
return [1, 2, 5, 9, 6, 3, 4, 7, 10, 11, 8, 12]
 *
 */

public class ZigZagMatrix {

    /**
     * @param matrix: a matrix of integers
     * @return: an array of integers
     */ 
    public int[] printZMatrix(int[][] matrix) {
        //check
        if(null == matrix || 0 == matrix.length){
            return new int[0];
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        int[] result = new int[m*n];
        for(int k = 0, index = 0, steps = m + n - 1; k < steps; k++){
        	if(0 == (k & 1)){ // even
        		for(int i = k, j = 0; i >= 0; i--, j++){
        			if(i < m && j < n){
            			result[index++] = matrix[i][j];
        			}
        		}
        	}else{ // odd
        		for(int i = 0, j = k; j >= 0; i++, j--){
        			if(i < m && j < n){
            			result[index++] = matrix[i][j];
        			}
        		}
        	}
        }
        
        return result;
    }
    
    public int[] printZMatrix_2(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return null;

        int m = matrix.length - 1, n = matrix[0].length - 1;
        int[] result = new int[(m + 1) * (n + 1)];
        int index = 0;
        for (int i = 0; i <= m + n; i++) {
            if (i % 2 == 0) {
                for (int x = i; x >= 0; x--) {
                    // valid matrix index
                    if ((x <= m) && (i - x <= n)) {
                        result[index] = matrix[x][i - x];
                        index++;
                    }
                }
            } else {
                for (int x = 0; x <= i; x++) {
                    if ((x <= m) && (i - x <= n)) {
                        result[index] = matrix[x][i - x];
                        index++;
                    }
                }
            }
        }

        return result;
    }
    
    public static void main(String[] args){
    	ZigZagMatrix sv = new ZigZagMatrix();
    	
    	int[][][] matrix = {
    			{
    				{1}
    			},
    			
    			{
    				{1,2,3}
    			},
    			
    			{
    				{1},
    				{2},
    				{3}
    			},
    			
    			{
    				{ 1, 2, 3, 4 }, 
    				{ 5, 6, 7, 8 }, 
    				{ 9, 10, 11, 12 }
    			},

    			{
    				{ 1, 2 }, 
    				{ 5, 6 }, 
    				{ 9, 10 }
    			},
    	};
    	
		for(int i = 0; i < matrix.length; i++){
		    System.out.println( Misc.array2String(matrix[i]) );
		    System.out.println(Misc.array2String(sv.printZMatrix(matrix[i])));
		    System.out.println(Misc.array2String(sv.printZMatrix_2(matrix[i])));
		}
    }
    
}
