package matrix;

import util.Misc;

/* 
 * 
 * Problem #2:
 * 
 * Given an integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.
 * 
 * e.g. input n = 3,
 * 
 * You should return the following matrix:
 * [
 *  [ 1, 2, 3 ],
 *  [ 8, 9, 4 ],
 *  [ 7, 6, 5 ]
 * ]
 * 
 */

/*
 * testcases:
 * 
 * #1  n < 1
 * new int[0][0]
 * 
 * #2  n = 1
 * {{1}}
 * 
 * #3  n = 2
 * {{1, 2},
 *  {4, 3}
 * }
 * 
 * #4  n = 3
 * {{1, 2, 3},
 *  {8, 9, 4},
 *  {7, 6, 5},
 * }
 * 
 */
public class SpiralMatrixII
{

  /*
   *  Given an integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.
   * 
   * e.g. input n = 3,
   * 
   * You should return the following matrix:
   * [
   *  [ 1, 2, 3 ],
   *  [ 8, 9, 4 ],
   *  [ 7, 6, 5 ]
   * ]
   */
  public int[][] generateMatrix(int n) {
    //check
    if(n<1)
      return new int[0][0];
    
    int[][] result = new int[n][n];

    generateMatrix(result, 0, 1);
    
    return result;
  }

  private void generateMatrix(int[][] matrix, int n, int index){
    int rows = matrix.length;
    int cols = rows;
    
    if( n > (rows-1)/2 )   
      return;
    
    int x_topLeft  = n;
    int y_topLeft  = n;    
    int x_bottomRight = cols - n -1;  // cols - 1 - n  
    int y_bottomRight = rows - n -1;  // rows - 1 - m
    
    if(x_topLeft == x_bottomRight){
      matrix[n][n] = index ++;
      return;
    }
    
    //from top-left to top-right , [ )
    for(int i=x_topLeft; i<x_bottomRight; i++)
      matrix[y_topLeft][i] = index ++;
        
    //from top-right to bottom-right , [ )
    for(int i=y_topLeft; i<y_bottomRight; i++)
      matrix[i][x_bottomRight] = index ++;
    
    //from bottom-right to bottom-left , [ )
    for(int i=x_bottomRight; i>x_topLeft; i--)
      matrix[y_bottomRight][i] = index ++;    
    
    //from bottom-left to top-left , [ )
    for(int i=y_bottomRight; i>y_topLeft; i--)
      matrix[i][x_topLeft] = index ++;
    
    generateMatrix(matrix, n+1, index);
    
  }
  
  public int[][] generateMatrix2(int n) {
    //check
    if(n<1)
      return new int[0][0];
      
    if(n == 1){
      int[][] tmp = {{1}};
      return tmp; 
    }
    
    int[][] matrix = new int[n][n];

    int start = 0, end = n - 1, index = 1;  // index = n^2, int maybe not enough.
    while(start < end){
      //from top-left to top-right , [ )
      for(int i=start; i<end; i++)
        matrix[start][i] = index ++;
          
      //from top-right to bottom-right , [ )
      for(int i=start; i<end; i++)
        matrix[i][end] = index ++;
      
      //from bottom-right to bottom-left , [ )
      for(int i=end; i>start; i--)
        matrix[end][i] = index ++;    
      
      //from bottom-left to top-left , [ )
      for(int i=end; i>start; i--)
        matrix[i][start] = index ++;
      
      start ++;
      end --;
    }
    
    if(start == end)
      matrix[start][start] = index; 
    
    return matrix;
  }
  
	public int[][] generateMatrix_n(int n) {
		// check
		if (n < 1) {
			return new int[0][0];
		}

		if (1 == n) {
			return new int[][] { { 1 } };
		}

		int[][] matrix = new int[n][n];
		int topLeft = 0;
		int bottomRight = n-1;
		int count = 1;
		for( ; topLeft < bottomRight; topLeft ++, bottomRight --){
			//top horizon line
			for(int x = topLeft; x<bottomRight; x++){
				matrix[topLeft][x] = count++;
			}
			//right vertical line
			for(int y = topLeft; y<bottomRight; y++){
				matrix[y][bottomRight] = count++;
			}			
			//bottom horizon line
			for(int x = bottomRight; x>topLeft; x--){
				matrix[bottomRight][x] = count++;
			}
			//left vertical line
			for(int y = bottomRight; y>topLeft; y--){
				matrix[y][topLeft] = count++;
			}
		}
		
		if(topLeft == bottomRight){
			matrix[topLeft][topLeft] = count;
		}
		
		return matrix;
	}
  

	
  /**
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("------------Start-------------- ");

    SpiralMatrixII sv = new SpiralMatrixII();
    
    for(int i=0; i < 5; i++){
      int[][] matrix = sv.generateMatrix_n(i);
      
      //print out the original matrix
      System.out.println("\n--print the matrix======================= " + i);
      System.out.println(Misc.array2String(matrix));

    }
    
    System.out.println("------------End-------------- ");
  }

}
