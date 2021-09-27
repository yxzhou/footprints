package matrix;

import java.util.Random;

import util.Misc;

/*
 * 
 * Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in place.
 * 
 * Follow up:
 * Did you use extra space?
 * A straight forward solution using O(mn) space is probably a bad idea.
 * A simple improvement uses O(m + n) space, but still not the best solution.
 * Could you devise a constant space solution?
 * 
 */

public class SetZero
{
  
  /*
   * Time O(m*n)   Space O(m+n)
   */
  public static void setZeros(int[][] matrix){
    //check
    
    //init
    int rowNum = matrix.length;
    int columnNum = matrix[0].length;  // it's MxN, means the column number is same.
    int[] rows = new int[rowNum]; //default it's 0, if it's 1, means all cells in this row would be set 0.  
    int[] columns = new int[columnNum]; //default it's 0, if it's 1, means all cells in this column would be set 0.
    
    //check cell by row, if it's 0, records the row and column, continue to the next row
    for(int row = 0; row< rowNum; row++){
      for(int col = 0; col < columnNum; col++){
        if(matrix[row][col] == 0){
          rows[row] = 1;
          columns[col] = 1;
        }
      }
    }
    
    //set cells in the recorded rows and columns to 0   
    for(int row = 0; row< rowNum; row++){
      for(int col = 0; col < columnNum; col++){
        if( rows[row] == 1 || columns[col] == 1 )
          matrix[row][col] = 0;
      }
    }  
    
  }

  /*
   * Time O(m*n)   Space O(1)
   */
  public static void setZeros_X(int[][] matrix){
    //init
    int rowNum = matrix.length;
    int columnNum = matrix[0].length;  // it's MxN, means the column number is same.
    
    //check cell by row, if it's 0, records the row and column, continue to the next row
    /* the first row and column is special, check them first,  
     * row0=0 means the first row all should be 0, col0=0 means the first column all should be 0*/
    int row0 = -1;  
    int col0 = -1;
    for (int row = 0; row < rowNum; row++) {
      if(matrix[row][0] == 0){
        col0 = 0;
        break;
      }
    }
    for(int col = 0; col < columnNum; col++){
      if(matrix[0][col] == 0){
        row0 = 0;
        break;
      }
    }
    /* if one column all would be 0, set matrix[0][col] = 0, if one row all would be 0, set matrix[row][0] = 0 */
    for(int row = 1; row< rowNum; row++){
      for(int col = 1; col < columnNum; col++){
        if(matrix[row][col] == 0){
          matrix[0][col] = 0;  
          matrix[row][0] = 0;     
        }
      }
    }
    
    //set cells in the recorded rows and columns to 0   
    for (int row = 1; row < rowNum; row++) {
      for (int col = 1; col < columnNum; col++) {
        if (matrix[row][0] == 0 || matrix[0][col] == 0)
          matrix[row][col] = 0;
      }
    } 

    if (col0 == 0) {
      for (int row = 0; row < rowNum; row++)
        matrix[row][0] = 0;

    }
    if (row0 == 0) {
      for (int col = 0; col < columnNum; col++)
        matrix[0][col] = 0;
    }
    
  }
  
  
  public void setZeroes_n(int[][] matrix) {
      if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
    	  return;
      }
      
      boolean firstRow = false;
      boolean firstCol = false;
      final int M = matrix.length;
      final int N = matrix[0].length;
      //when there is 0 in the first row, set firstRow as true
      for( int col=0; col<N; col ++){
    	  if( 0 == matrix[0][col]){
    		  firstRow = true;
    		  break;
    	  }
      }
      //when there is 0 in the first col, set firstCol as true
      for( int row =0; row<M; row++){
    	  if( 0 == matrix[row][0]){
    		  firstCol = true;
    		  break;
    	  }
      }
      //if one column all would be 0, set matrix[0][col] = 0, if one row all would be 0, set matrix[row][0] = 0 
      for(int row=1; row<M; row++){
    	  for(int col=1; col<N; col++){
    		  if(0 == matrix[row][col]){
    			  matrix[0][col] = 0;
    			  matrix[row][0] = 0;
    		  }
    	  }
      }
      
      for(int row=1; row<M; row++){
    	  for(int col=1; col<N; col++){
    		  if(0 == matrix[0][col] || 0 == matrix[row][0]){
    			  matrix[row][col] = 0;
    		  }
    	  }
      }
      if(firstRow){
          for( int col=0; col<N; col ++){
        	  matrix[0][col] = 0;
          }
      }
      if(firstCol){
          for( int row=0; row<M; row ++){
        	  matrix[row][0] = 0;
          }
      }
      
  }
  
  public static void main(String[] args) {
    
    System.out.println("------------Start-------------- " );
    
    //init a matrix with int[][]
    int rowN = 7;  // MxN matrix
    int colM = 8;  // MxN matrix
    
    Random random = new Random();
    int[][]  aInt = new int[rowN][colM];    
    for(int row = 0; row< rowN; row++){
      for(int col = 0; col< colM; col++){
        if( row > 3 & col < 3 )
          aInt[row][col] = random.nextInt(5)  ;
        else
          aInt[row][col] = row * 10 + col  ;
      }
    }
    
    System.out.println(Misc.array2String(aInt)); 

    setZeros(aInt);

    System.out.println("--setZero------------------------ " );
    System.out.println(Misc.array2String(aInt)); 
    
    System.out.println("------------End-------------- " );

  }

}
