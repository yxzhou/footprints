package fgafa.dp.matrix.PathSum;

import java.util.ArrayList;
import java.util.List;

/*
 * Given a triangle, find the minimum path sum from top to bottom. 
 * Each step you may move to adjacent numbers on the row below.
 * 
 * For example, given the following triangle
 * [
 *      [2],
 *     [3,4],
 *    [6,5,7],
 *   [4,1,8,3]
 * ]
 * The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
 * 
 * Note:
 * Bonus point if you are able to do this using only O(n) extra space, 
 * where n is the total number of rows in the triangle.
 */
public class Triangle
{

  /*
   * define m[n][i] is the min path in the nth row,  ( i >= 0, i < n )
   *   
   * up-bottom
  */
  public int minimumTotal(List<List<Integer>> triangle) {
    int result = 0;
    //check
    if(triangle == null || triangle.size() == 0 )
      return result;
    
    int cols;
    List<Integer> currRow = triangle.get(0);
    List<Integer> nextRow;
    int[] currRowMin = {currRow.get(0)}; 
    int[] nextRowMin;
    
    for(int row=1; row < triangle.size(); row ++){
      nextRow = triangle.get(row);
      
      cols = currRow.size();
      nextRowMin = new int[cols+1]; 
          
      nextRowMin[0] = currRowMin[0] + nextRow.get(0);
      for(int col = 1; col < cols; col ++){
        nextRowMin[col] = Math.min(currRowMin[col - 1], currRowMin[col]) + nextRow.get(col);
        
      }
      nextRowMin[cols] = currRowMin[cols - 1] + nextRow.get(cols);  
      
      currRow = nextRow;
      currRowMin = nextRowMin;
    }
    
    //find the min in currRowMin
    result = Integer.MAX_VALUE;
    for(int i=0; i< currRowMin.length; i++){
      result = Math.min(result, currRowMin[i]);
    }
    
    return result;
  }
  
  
  /**
   * @param triangle: a list of lists of integers.
   * @return: An integer, minimum path sum.
   */
  public int minimumTotal_n(List<List<Integer>> triangle) {
      //check
      if(null == triangle || 0 == triangle.size() || 0 == triangle.get(triangle.size() - 1).size()){
          return 0;
      }
      
      //count from triangle's bottom to top
      int[] sums = new int[triangle.get(triangle.size() - 1).size() + 1]; //default all are 0
      List<Integer> currLayer;
      for(int row = triangle.size()-1; row>=0; row--){
          currLayer = triangle.get(row);
          
          for(int col = 0; col < currLayer.size(); col++){
              sums[col] = Math.min(sums[col], sums[col + 1]) + currLayer.get(col);
          }
      }
      
      return sums[0];
  }
  
  /*
   * define p[i][j] is the min path in the ith row, jth column,  ( 0<=i<n, 0<=j<n )
   *   
   * bottom-up
  */
  public int minimumTotal_X(List<List<Integer>> triangle) {
    //check
    if(triangle == null || triangle.size() == 0 )
      return 0;
    
    int n = triangle.size();
    int[] p = new int[n+1];
    
    while(n-- > 0){
        for(int i = 0; i <= n; ++i) {
            p[i] = triangle.get(n).get(i) + ((p[i] < p[i+1])? p[i] : p[i+1]);
        }
    }
    return p[0];

  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    int[][] input = {
    		{2},
    		{3,4},
    		{6,5,7},
    		{1,4,8,3}
    };

    List<List<Integer>> triangle = new ArrayList<>();
    for(int[] level : input){
    	List<Integer> list = new ArrayList<>(level.length);
    	for(int num : level){
        	list.add(num);
    	}
    	triangle.add(list);
    }

    Triangle sv = new Triangle();
    System.out.println(sv.minimumTotal_X(triangle));
  }

}
