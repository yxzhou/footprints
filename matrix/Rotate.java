package matrix;

import util.Misc;

/*
 * Given an image represented by a matrix, where each pixel in the image is 4 bytes, write
 * a method to rotate the image by 90 degrees. Can you do this in place?
 * 
 */

public class Rotate
{

  /*
   * rotate the matrix(n*n) in clockwise direction 
   * 
   * 
   */
  public String[][] rotate90(String[][] arr){
    int n = arr.length;
    
    int iMax = n/2;
    int last;
    int first;
    String tmpValue;
    
    for(int i=0; i< iMax; i++){
      last = n - 1 - i;
      
      for(int j=i; j< last; j++){
        tmpValue = arr[i][j];               //
        first = n - 1 - j;
        
        arr[i][j] = arr[first][i];          //left -> top
        arr[first][i] = arr[last][first];   //bottom -> left
        arr[last][first] = arr[j][last];    //right -> bottom
        arr[j][last] = tmpValue;            //top -> right
      }
      
    }

    return arr;
  }
    
  public void rotate90(int[][] arr) {
    int n = arr.length;
    
    int iMax = n/2;
    int last;
    int first;
    int tmpValue;
    
    for(int i=0; i< iMax; i++){
      last = n - 1 - i;
      
      for(int j=i; j< last; j++){
        tmpValue = arr[i][j];               //
        first = n - 1 - j;
        
        arr[i][j] = arr[first][i];          //left -> top
        arr[first][i] = arr[last][first];   //bottom -> left
        arr[last][first] = arr[j][last];    //right -> bottom
        arr[j][last] = tmpValue;            //top -> right
      }      
    }    
  }
  
  public void rotate90_n(int[][] matrix) {
      if(null == matrix)
    	  return;
      
      int tmp;
      int diff;
      for(int i = 0, j = matrix.length-1; i<j; i++, j--){
    	  for(int k = i; k<j; k++ ){
    		  diff = j-k+i;
    		  tmp = matrix[i][k]; 
    		  matrix[i][k] = matrix[diff][i];
    		  matrix[diff][i] = matrix[j][diff];
    		  matrix[j][diff] = matrix[k][j];
    		  matrix[k][j] = tmp;
    	  }
      }
  }
  
  
  /**
   * @param matrix: A list of lists of integers
   * @return: Void
   */
  public void rotate90_n2(int[][] matrix) {
      //check
      if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
          return;
      }
      
      int n = matrix.length;
      int tmp;
      for(int x = 0, y = n-1; x < y; x++, y--){
          for(int i = 0; i< y-x; i++){
              tmp = matrix[x][x+i];
              matrix[x][x+i] = matrix[y-i][x]; 
              matrix[y-i][x] = matrix[y][y-i];
              matrix[y][y-i] = matrix[x+i][y];
              matrix[x+i][y] = tmp;
          }
      }

  }
  
  public int[] rotate90_2(int[] src) {
    int n = src.length;
    int[] dest = new int[n];
    
    int x,y;
    for (y=0; y<n; y++)
    for (x=0; x<n; x++)
      dest[n*y+x] = src[n*x+y];
    
    return dest;
  }

  /*  */
  public int[][] rotate90_2(int[][] src) {
    int n = src.length;
    int[][] dest = new int[n][n];
    
    /*  */
    for(int i=0; i< n; i++)    
      for(int j=0; j< n; j++)
        dest[i][j] = src[n-j-1][i];
    
    return dest;
  }
  
  
  public int[][] rotate90_2_tiling(int[][] src) {
    int n = src.length;
    int[][] dest = new int[n][n];
    final int block = 8; 
    
    /* Loop tiling */
    for(int bi=0; bi< n; bi+=block)    
      for(int bj=0; bj< n; bj+=block)
        for(int i=0; i<block; i++)
          for(int j=0; j<block; j++)
            dest[bi + i][bj + j] = src[n - bj - j - 1][bi + i];
    
    return dest;
  }
  
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Rotate m = new Rotate();
    
    //init a matrix with int[][]
    int n = 9;  // n*n matrix

//    String[][]  a = new String[n][n];
//    for(int i = 0; i< n; i++){
//      for(int j = 0; j< n; j++){
//        a[i][j] = i + "" + j;
//      }
//    }
//    System.out.println(Misc.array2String(a));
//
//    //test
//    System.out.println(Misc.array2String(m.rotate90(a)));   
//
//    //System.out.println("Result:" + Misc.array2String(a.clone()));
//
//    System.out.println("Result:" +   Misc.array2String( m.rotate90( m.rotate90( m.rotate90( m.rotate90(a) )))));
    

    //init a matrix with int[][]
    // n*n matrix
    
    for(n = 1; n < 6; n++){
	    int[][] nums = new int[n][n];
	    for(int i = 0; i< n; i++){
	      for(int j = 0; j< n; j++){
	    	  nums[i][j] = (i+1) * 10 + j + 1;
	      }
	    }
	    System.out.println(Misc.array2String(nums));
	
	    //test
	    m.rotate90_n(nums);
	    System.out.println(Misc.array2String(nums));   
	
	    //System.out.println("Result:" + Misc.array2String(a.clone()));
	
	    m.rotate90_n(nums);
	    m.rotate90_n(nums);
	    m.rotate90_n(nums);
	    System.out.println("Result:" +   Misc.array2String( nums ));
    }
    
  }
}
