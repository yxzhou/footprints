package array.subArray;

import util.Misc;

import java.util.HashMap;
import java.util.Map;

public class SubMatricSumEqual {

	/**
	 * Given an integer matrix, find a submatrix where the sum of numbers is zero.
	 * Your code should return the coordinate of the left-up and right-down number.
	 * 
	 * Example  Given matrix
	 * [
	 *   [1 ,5 ,7],
	 *   [3 ,7 ,-8],
	 *   [4 ,-8 ,9],
	 * ]
	 * return [(1,1), (2,2)]
	 * 
	 * Challenge O(n^3) time.
	 */
    /**
     * @param matrix an integer matrix
     * @return the coordinate of the left-up and right-down number
     */
    public int[][] submatrixSum(int[][] matrix) {
        int[][] ans = new int[2][2];
        if(null == matrix){
            return ans;
        }
        
        int rowNum = matrix.length;
        int colNum = matrix[0].length;
        long[] colSum = new long[colNum];
        long sum = 0;
        Map<Long, Integer> map = new HashMap<>(); //Map<sum value, column index>
        for(int topRow = 0; topRow < rowNum; topRow++){
            for(int bottomRow = topRow; bottomRow< rowNum; bottomRow++){
                for(int col = 0; col < colNum; col++){
                    if(bottomRow == topRow){
                        colSum[col] = 0;
                    }
                    if(0 == col){
                        sum = 0;
                        map = new HashMap<>();
                        map.put(0L, -1);
                    }
                        
                    colSum[col] += matrix[bottomRow][col];
                    sum += colSum[col];
                    
                    if(map.containsKey(sum)){
                        ans[0][0] = topRow;
                        ans[0][1] = map.get(sum) + 1;
                        ans[1][0] = bottomRow;
                        ans[1][1] = col;
                        return ans;
                    }
                    
                    map.put(sum, col);
                }
            }
        }
        
        return ans;
    }
	
	
	public static void main(String[] args) {
		SubMatricSumEqual sv = new SubMatricSumEqual();

		
		int[][][] matrixs = {
			{
				  {1 ,5 ,7}, 
				  {3 ,7 ,-8}, 
				  {4 ,-8 ,9}
			}
			,
			{
				{1,1,-3,1,1}
			}
			,
			{
				{1},
				{1},
				{-3},
				{1},
				{1}
			}
//			,
//			{
//				{{1},{1},{1},{1},{1},{1},{1},{1},{1},{1},{1},{-10},{1},{1},{1},{1},{1},{1},{1},{1},{1},{1},{1}
//				}
//			}
		
		};

		for(int i=0; i< matrixs.length; i++){
			System.out.println(Misc.array2String(sv.submatrixSum(matrixs[i])));
		}


	}

}
