package matrix;

import algs4.stdlib.In;

import util.Constants;
import util.Misc;
import util.TimeCost;

/**
 * 
 * Write an efficient algorithm that searches for a value in an n x m table (two-dimensional array). 
 * This table is sorted along the rows and columns — that is,
 * Table[i][j] <= Table[i][j + 1],  
 * Table[i][j] <= Table[i + 1][j]
 *
 * So the bottom-right element of a sub_matrix is biggest,  the top-left element of a sub_matrix is smallest
 *
 * A very in-depth analysis see 
 * _http://www.leetcode.com/category/divide-and-conquer
 *   
 */

public class Search2
{

  
  /**
   * Step-wise Linear Search 
   *  
   * Steps: 
   * Start search from the top-right.
   * 1)if it == target,  return successfully
   * 2)If it < target,  move to left.
   * 3)else ( it > target ), it move to down.   
   * 
   * O(rows+cols) 
   *     
   */
  public boolean stepWise(int[][] matrix, int target){
      //check
      if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
          return false;
      }
      
    int rows= matrix.length;
    int cols= matrix[0].length;
    
    //init
    int row = 0; 
    int col = cols - 1;   // the start point is top-right. 
    
    while(row < rows && col >= 0){
      if(matrix[row][col] == target ){
        return true;
      }else if(matrix[row][col] > target){
        col --;
      }else{
        row ++;
      }
    }
    
    return false;
  }
  
  /**
   * @param matrix: A list of lists of integers
   * @param: A number you want to search in the matrix
   * @return: An integer indicate the occurrence of target in the given matrix
   */
  public int stepWiseII(int[][] matrix, int target) {
      int count = 0;
      //check
      if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
          return count;
      }
      
      int m = matrix.length; //how many rows
      int n = matrix[0].length; //how many columns
      
      for( int row = 0, col = n -1; row < m && col >=0; ){
          if(matrix[row][col] == target){
              count++;
              
              row++;
              col--;
          }else if(matrix[row][col] < target){
              row++;
          }else{// matrix[row][col] > target
              col--;
          }
      }
      
      return count;
  }
  
  /*
   * Step-wise Linear Search + binary search in the line (row and col)
   *  
   * Steps: 
   * Start search from the top-right.
   * 1)if it == target,  return successfully
   * 2)If it < target,  it move to left, binary search on the row   
   * 3)else ( it > target ), it move to down, binary search on the column   
   * 
   * O(log(rows) + log(cols))  ??  worst case O( rows*log(rows) + cols*log(cols) ) ??
   *     
   */
  public boolean stepWise_Binary(int[][] arr, int elem){
    int rowNum= arr.length;
    int colNum= arr[0].length;
    
    //init
    int row = 0; 
    int col = colNum - 1;   // the start point is top-right. 

    int middle, right, left, top, bottom;  
    while(row < rowNum && col >= 0){
      if( elem == arr[row][col] ){
        return true;
      }else if(elem < arr[row][col] ){
        //col --;
        left = 0; 
        right = col;

        while( right > left + 1 )
        {
          middle = ( left + right )/2;

          if( elem == arr[row][middle])
            return true;
          else if( elem > arr[row][middle] )
            left = middle;
          else
            right = middle;    
        }
        
        col = left;
        if(elem < arr[row][col] )
          return false;
        
      }else{
        //row ++;
        bottom = rowNum - 1;
        top = row; 
 
        while( bottom > top + 1 )
        {
          middle = ( top + bottom )/2;

          if( elem == arr[middle][col])
            return true;
          else if( elem > arr[middle][col] )
            top = middle;
          else
            bottom = middle;    
        }
        
        row = bottom;
        if(elem > arr[row][col] )
          return false;
      }
    }
    
    return false;
  }
  
  
  /**
   * TODO,   need correction
   * 
   * binary search  
   * 
   * Because the bottom-right element of a sub_matrix is biggest.
   *   
   * 1)start search from the last column, from the top and bottom, find the middle-right
   *   if target=the middle-right, return successfully
   *   If target>the middle-right, means the target is not in the sub-matrix( row from top to middle ), set top=middle
   *   else 
   *   
   * 2)If target>the bottom-right element of the sub_matrix,  enlarge the scope with the next line.
   * 3)else, the target is in the last line.   
   * 
   * O(rows+cols)   ???
   * 
   */
  public boolean binarySearch(int[][] arr, int elem){
    
    //binary selection on rows.  top to bottom.
    int top = 0;
    int bottom = arr.length - 1; 
    int middle = 0; 
        
    //
    //check the first column, top to left
    if(elem > arr[bottom][0]  ){
      top = bottom;
    }else{
      while( bottom > top + 1 )
      {
        middle = ( top + bottom )/2;

        if( elem > arr[middle][0] )
          top = middle;
        else
          bottom = middle;    
      }
          
      if(arr[bottom][0] == elem || arr[top][0] == elem)
        return true;      
    }
      
    //binary selection on columns,  left to right
    int left = 0;
    int right = arr[top].length - 1;
    
    while( right > left + 1 )
    {
      middle = ( left + right )/2;

      if( elem > arr[top][middle] )
        left = middle;
      else
        right = middle;    
    }    
    
    if(arr[top][left] == elem || arr[top][right] == elem)
      return true;
      
    return false;
  }
  
  /*
   * there are one center and 4 quadPard. 
   * 
   * T(n) = 3T(n/2) + c
   * f(n) = 1,  n^log(b,a) =n^log(2,3) = n^1.5 ,  so T(n) = O(n^1.5)
   * 
   */
  public boolean quadPartition(int[][] arr, int target){
    return quadPartition(arr, target, 0, 0, arr[0].length - 1, arr.length -1);
  }
  private boolean quadPartition(int[][] arr, int target, int topLeft_x, int topLeft_y, int bottomRight_x, int bottomRight_y) {
    if (topLeft_x > bottomRight_x || topLeft_y > bottomRight_y) 
      return false;
    if(topLeft_x == bottomRight_x && topLeft_y == bottomRight_y)
      return arr[topLeft_y][topLeft_x] == target;
    if (target < arr[topLeft_y][topLeft_x] || target > arr[bottomRight_y][bottomRight_x]) 
      return false;
    
    //System.out.printf("(%d,%d), (%d, %d) \n",topLeft_x, topLeft_y, bottomRight_x, bottomRight_y);
    
    int center_x = topLeft_x + ((bottomRight_x-topLeft_x)>>1);
    int center_y = topLeft_y + ((bottomRight_y-topLeft_y)>>1);
    if (arr[center_y][center_x] == target) {
      return true;
    }

    return quadPartition(arr, target, topLeft_x, topLeft_y, center_x, center_y) 
        || quadPartition(arr, target, center_x + 1, center_y + 1, bottomRight_x, bottomRight_y)
        || quadPartition(arr, target, center_x + 1, topLeft_y, bottomRight_x, center_y)
        ||quadPartition(arr, target, topLeft_x, center_y + 1, center_x, bottomRight_y);
  }
   
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Search2 m = new Search2();
    TimeCost tc = TimeCost.getInstance();
    
    /*-------------simple test start --------------- */
  int[][] aInt = {
      {1, 4, 7, 11, 15},
      {2, 5, 8, 12, 19}, 
      {3, 6, 9, 16, 22},
      {10, 13, 14, 17, 24}, 
      {18, 21, 23, 26, 30}, 
      {22, 25, 27, 28, 31},
      {29, 31, 32, 34, 35}};
  
  int[] elems = {9, 10, 23, 33, 40};        
  
  System.out.println(Misc.array2String(aInt));

  tc.init();
    
  for (int i = 0; i < elems.length; i++) {

          System.out.println("\nIs " + elems[i] + " in? "
              + m.stepWise(aInt, elems[i]) + " timeCost:"+ tc.getTimeCost());
          
          System.out.println("Is " + elems[i] + " in? "
              + m.stepWise_Binary(aInt, elems[i]) + " timeCost:"+ tc.getTimeCost());
          
          System.out.println("Is " + elems[i] + " in? "
              + m.quadPartition(aInt, elems[i]) + " timeCost:"+ tc.getTimeCost());
          
        }
    
    //-------------simple test end ---------------

    //*-------------performance test start-------------
//    int n = 1000; // n*n matrix
//
//    int[][] aInt = new int[n][n];
//    int tmp = 1;
//    aInt[0][0] = 1;
//    for (int i = 0; i < n; i++) {
//      aInt[i][0] = ((i / 2) * 2000) + 1;
//
//      for (int j = 1; j < n; j++) {
//        aInt[i][j] = aInt[i][j - 1] + 2;
//      }
//
//    }
//
//    int[] elems = {503886, 517885};
        
    String filename = Constants.TEST_DATA_URL + "matrix_search_performance1000_input.txt";
    
    In in = new In(filename);
    
    int rowsNum = in.readInt();
    int colsNum = in.readInt();
    System.out.println("--rows:" + rowsNum + " --cols:"+colsNum);
    
    int[][] aInt_big = new int[rowsNum][colsNum];
    
    for (int i = 0; i < rowsNum; i++) {
      for (int j = 0; j < colsNum; j++) {
        aInt_big[i][j] = in.readInt();
      } 
    }
    
    int caseNum = in.readInt();
    int[] elems_big = new int[caseNum];
    for(int i=0; i < caseNum; i++){
      elems_big[i] = in.readInt();
    }

    //compare result
//    for (int i = 0; i < elems_big.length; i++) {
//        System.out.printf("\n %b, %b, %b \n", m.stepWise(aInt_big, elems_big[i]), m.stepWise_Binary(aInt_big, elems_big[i]), m.quadPartition(aInt_big, elems_big[i]) );        
//    }
    
    //compare timecost
    tc.init();
    for (int i = 0; i < elems_big.length; i++) {
      m.stepWise(aInt_big, elems_big[i]);
    }
    System.out.println(" stepWise timeCost:"+ tc.getTimeCost());
    
    for (int i = 0; i < elems_big.length; i++) {
      m.stepWise_Binary(aInt_big, elems_big[i]) ;
    }
    System.out.println(" stepWise_Binary timeCost:"+ tc.getTimeCost());    

    for (int i = 0; i < elems_big.length; i++) {
      m.quadPartition(aInt_big, elems_big[i]) ;
    }
    System.out.println(" quadPartition timeCost:"+ tc.getTimeCost());  
    //-------------performance test end-------------*/
  }
}
