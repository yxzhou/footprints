package matrix;

import java.util.ArrayList;
import java.util.List;

import algs4.stdlib.In;

import util.Constants;
import util.Misc;

/*
 * Problem #1:
 * 
 * Given a matrix (2D array) of m x n elements (m rows, n columns), 
 * write a function that prints the elements in the array in a spiral manner
 *
 * e.g. input:
 *   a1, a2, a3, a4
 *   b1, b2, b3, b4
 *   c1, c2, c3, c4
 *   
 * define the top-left as (0,0), so the output is  
 *  from top-left to top-right, [(0, 0),(0, cols-1))  (inclusive (0,0), exclusive (0, cols-1) )  
 *  from top-right to bottom-right, [(0, cols-1),(rows-1, cols-1))
 *  from bottom-right to bottom-left, [(rows-1, cols-1),(rows-1,0))
 *  from bottom-left to top-left, [(rows-1, 0), (0,0))  
 *  
 * output: a1, a2, a3, a4, b4, c4, c3, c2, c1, b1, b2, b3   
 * 
 * 
 */

/*
 * testcases:
 * 
 * #1
 * {{1}}
 * 
 * #2
 * {{1, 2}}
 * 
 * #3
 * {{1, 2, 3}}
 * 
 * #4
 * {{1},
 *  {2}
 * }
 * 
 * #5
 * {{1},
 *  {2},
 *  {3}
 * }
 * 
 * #6
 * {{1, 2},
 *  {4, 3}
 * }
 * 
 * #7
 * {{ 1,  2,  3, 4},
 *  {10, 11, 12, 5},
 *  { 9,  8,  7, 6},
 * }
 * 
 * #8
 * {{ 1,  2,  3},
 *  {10, 11,  4},
 *  { 9, 12,  5},
 *  { 8,  7,  6}
 * }
 * 
 */


public class SpiralMatrix
{

  public List<Integer> spiralOrder_recursive(int[][] matrix) {
    List<Integer> list = new ArrayList<Integer>();
    
    //check
    if(matrix == null || matrix.length == 0 || matrix[0].length == 0)
      return list;
    
    
    spiralOrder_recursive(list, matrix, 0, 0);
    
    return list;
  }
  
  private void spiralOrder_recursive(List<Integer> list, int[][] matrix, int m, int n){
    
    int rows = matrix.length;
    int cols = matrix[0].length;
    
    if(m > (rows-1)/2 || n > (cols-1)/2 )   //if it's one row or column ? if it's 2 rows or columns ?  if it's 3 rows or columns ?
      return;
    
    int m_topLeft  = m;    
    int n_topLeft  = n;
    int m_bottomRight = rows - m -1;  // rows - 1 - m
    int n_bottomRight = cols - n -1;  // cols - 1 - n
    
    
    //print this circle by clock direction / Spiral
    if(m_topLeft == m_bottomRight){
      //from top-left to top-right , [ ]
      for(int i=n_topLeft; i<=n_bottomRight; i++)
        list.add(matrix[m_topLeft][i]);
      
      return;
    }
    
    if(n_topLeft == n_bottomRight){
      //from top-right to bottom-right ,  [ ]
      for(int i=m_topLeft; i<=m_bottomRight; i++)
        list.add(matrix[i][n_bottomRight]);
      
      return;
    }
    
    //from top-left to top-right , [ )
    for(int i=n_topLeft; i<n_bottomRight; i++)
      list.add(matrix[m_topLeft][i]);
        
    //from top-right to bottom-right , [ )
    for(int i=m_topLeft; i<m_bottomRight; i++)
      list.add(matrix[i][n_bottomRight]);
    
    //from bottom-right to bottom-left , [ )
    for(int i=n_bottomRight; i>n_topLeft; i--)
      list.add(matrix[m_bottomRight][i]);    
    
    //from bottom-left to top-left , [ )
    for(int i=m_bottomRight; i>m_topLeft; i--)
      list.add(matrix[i][n_topLeft]);
    
    spiralOrder_recursive(list, matrix, m+1, n+1);
    
  }
  
  public List<Integer> spiralOrder_iterate(int[][] matrix) {
    List<Integer> list = new ArrayList<Integer>();
    
    //check
    if(matrix == null || matrix.length == 0 || matrix[0].length == 0)
      return list;
    
    
    int topLeft_x = 0, topLeft_y = 0;
    int bottomRight_x = matrix[0].length - 1, bottomRight_y = matrix.length - 1;
    
    for( ; topLeft_x<bottomRight_x && topLeft_y<bottomRight_y; topLeft_x ++, topLeft_y ++, bottomRight_x--, bottomRight_y-- ){
      //from top-left to top-right , [ )
      for(int i=topLeft_x; i<bottomRight_x; i++)
        list.add(matrix[topLeft_y][i]);
          
      //from top-right to bottom-right , [ )
      for(int i=topLeft_y; i<bottomRight_y; i++)
        list.add(matrix[i][bottomRight_x]);
      
      //from bottom-right to bottom-left , [ )
      for(int i=bottomRight_x; i>topLeft_x; i--)
        list.add(matrix[bottomRight_y][i]);    
      
      //from bottom-left to top-left , [ )
      for(int i=bottomRight_y; i>topLeft_y; i--)
        list.add(matrix[i][topLeft_x]);
    }
    
    if(topLeft_y == bottomRight_y){
      //from top-left to top-right , [ ]
      for(int i=topLeft_x; i<=bottomRight_x; i++)
        list.add(matrix[topLeft_y][i]);
      
      return list;   // 
    }
    
    if(topLeft_x == bottomRight_x){
      //from top-right to bottom-right ,  [ ]
      for(int i=topLeft_y; i<=bottomRight_y; i++)
        list.add(matrix[i][bottomRight_x]);
      
      return list;  //
    }
      
    return list;
  }

    public List<Integer> spiralMatric_n(int[][] matric){
        List<Integer> result = new ArrayList<>();

        if(null == matric || 0 == matric.length || 0 == matric[0].length){
            return result;
        }


        int topRight = 0;
        int row = matric.length - 1;
        int column = matric[0].length - 1;

        for( ; topRight < row && topRight < column; topRight++, row--, column--){
            for(int i = topRight; i < column; i++){
                result.add(matric[topRight][i]);
            }

            for(int i = topRight; i < row; i++){
                result.add(matric[i][column]);
            }

            for(int i = column; i > topRight; i--){
                result.add(matric[row][i]);
            }

            for(int i = row; i > topRight; i--){
                result.add(matric[i][topRight]);
            }
        }

        /*The rest:
        *  case 1, one point
        *  case 2, one row
        *  case 3, one column
        */
        if(topRight == column){ //for case 1, case 2
            for(int i = topRight; i <= row; i++){
                result.add(matric[i][column]);
            }
        }else if(topRight == row){ //for case 3
            for(int i = topRight; i <= column; i++){
                result.add(matric[topRight][i]);
            }
        }

        return result;
    }
  
	public List<Integer> spiralOrder_n2(int[][] matrix) {
		List<Integer> list = new ArrayList<Integer>();

		// check
		if ( null == matrix || 0 == matrix.length  || 0 == matrix[0].length)
			return list;

		int topLeft = 0;
		int bottomRightX = matrix[0].length - 1;
		int bottomRightY = matrix.length - 1;
		for( ; topLeft < bottomRightX && topLeft < bottomRightY; topLeft++, bottomRightX--, bottomRightY--){
			for( int col = topLeft; col<bottomRightX; col++ ){
				list.add(matrix[topLeft][col]);
			}
			for( int row = topLeft; row<bottomRightY; row++){
				list.add(matrix[row][bottomRightX]);
			}
			for( int col = bottomRightX; col>topLeft; col-- ){
				list.add(matrix[bottomRightY][col]);
			}
			for( int row = bottomRightY; row>topLeft; row-- ){
				list.add(matrix[row][topLeft]);
			}
		}
		
		if(topLeft == bottomRightY){
			for( int col = topLeft; col<=bottomRightX; col++ ){
				list.add(matrix[topLeft][col]);
			}
			return list;
		}
		if(topLeft == bottomRightX){
			for( int row=topLeft; row<=bottomRightY; row++){
				list.add(matrix[row][bottomRightX]);
			}
			return list;
		}
		return list;
	}
	
  /**
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("------------Start-------------- ");

    //init a matrix with int[][]
//    int rowM = 3; // MxN matrix   
//    int colN = 5; // MxN matrix
//
//    Random random = new Random();
//    int[][] matrix = new int[rowM][colN];
//    for (int row = 0; row < rowM; row++) {
//      for (int col = 0; col < colN; col++) {
//        matrix[row][col] = random.nextInt(15); // + random.nextInt(5) - 5;
//      }
//    }

    //input from file
    String inputF = Constants.TEST_DATA_URL + "matrix_spiral_input.txt";

    
    In in = new In(inputF);
    int caseNum = in.readInt();
    List<int[][]> cases = new ArrayList<int[][]>();
    
    for(int k=0; k < caseNum; k++){
      int rowsNum = in.readInt();
      int colsNum = in.readInt();
      
      int[][] aInt = new int[rowsNum][colsNum];
      
      for (int i = 0; i < rowsNum; i++) {
        for (int j = 0; j < colsNum; j++) {
          aInt[i][j] = in.readInt();
        } 
      }
      
      cases.add(aInt);
    }

    String outputF = Constants.TEST_DATA_URL + "matrix_spiral_output.txt";
    In in2 = new In(outputF);

    String[] output = new String[caseNum];
    for(int i=0; i < caseNum; i++){
      output[i] = in2.readLine();
    }
    
    for(int i=0; i < caseNum; i++){
      int[][] matrix = cases.get(i); 
      
      //print out the original matrix
      System.out.println("\n--print the matrix======================= " + i);
      System.out.println(Misc.array2String(matrix));

      System.out.println("--print the matrix------------------------ ");
      SpiralMatrix s = new SpiralMatrix();
      Misc.printArrayList_Integer(s.spiralOrder_recursive(matrix));
      Misc.printArrayList_Integer(s.spiralOrder_iterate(matrix));
      
      System.out.println("\n" + output[i]);
    }
    

    
    System.out.println("------------End-------------- ");
  }

}
