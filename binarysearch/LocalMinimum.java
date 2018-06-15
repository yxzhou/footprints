package fgafa.binarysearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * see http://algs4.cs.princeton.edu/14analysis/ 
 *  
 *Q1: local minumum of a array. 
 * Suppose we are given an array A[1 .. n] with the special property that A[1] ≥ A[2] and A[n − 1] ≤ A[n]. 
 * We say that an element A[x] is a local minimum if it is less than or equal to both its neighbors, 
 * or more formally, if A[x − 1] ≥ A[x] and A[x] ≤ A[x + 1]. 
 * For example, there are ﬁve local minima in the following array:
 * 9 7 7 2 1 3 7 5 4 7 3 3 4 8 6 9
 * We can obviously ﬁnd a local minimum in O(n) time by scanning through the array. 
 * Describe and analyze an algorithm that ﬁnds a local minimum in O(log n) time.
 *
 *Q2: Local minimum of a matrix. 
 * Given an N-by-N array a of N^2 distinct integers, design an O(N) algorithm to find a local minimum: 
 * an pair of indices i and j such that 
 *  a[i][j] < a[i+1][j], 
 *  a[i][j] < a[i][j+1], 
 *  a[i][j] < a[i-1][j],  
 *  a[i][j] < a[i][j-1]. 
 *
 * Hint: 
 * Find the minimum entry in row N/2, say a[N/2][j]. Check its two vertical neighbors a[N/2-1][j] and a[N/2+1][j]. 
 * Recur in the half with the smaller neighbor. In that half, find the minimum entry in column N/2.
 */

public class LocalMinimum
{
  /*
   * 
   * find one LM in 1 dimension
   * Time O(logn)
   */
  public int findLocalMin(int[] arr){
    if(arr == null || arr.length == 0 )
      return -1;  // error code
     
    return findOneLM_recursive(arr,  0, arr.length - 1);
  }
  
  private int findOneLM_recursive(int[] arr, int start, int end){
    if(end - start > 2){
      int mid = start + ((end -start) >> 1);
      
      if(arr[mid - 1] >= arr[mid] && arr[mid] <= arr[mid + 1])
        return mid;
      else if(arr[mid - 1] <= arr[mid] )  // arr[start] >= a[start+1] && arr[mid - 1] <= arr[mid] 
        return findOneLM_recursive(arr, start, mid);
      else //if(arr[mid] >= arr[mid + 1] ) // arr[mid] >= arr[mid + 1] && arr[end-1] <= arr[end] 
        return findOneLM_recursive(arr, mid, end);
    }
    return -1;  // error code, it means no Local Minimum. 
  }  
  
  
  
  /*
   * Local minimum of a matrix. 
   * Given an N-by-N array a of N2 distinct integers, design an O(N) algorithm to find a local minimum: 
   * an pair of indices i and j such that 
   *  a[i][j] < a[i+1][j], 
   *  a[i][j] < a[i][j+1], 
   *  a[i][j] < a[i-1][j], and 
   *  a[i][j] < a[i][j-1]. 
   *  
   *  Solution, it's like a raindrop on a lotus leaf. The raindrop will flow to the "lower" place.
   *  eg. For array below, N=5:
   *  
   *  1  12  3   1  -23  
   *  7   9  8   5   6
   *  4   5  6  -1  77
   *  7   0  35 -2  -4
   *  6  83  1   7  -6
   *  
   *  Step 1: The middle row is [4 5 6 -1 77]. ie. row number 3.
   *  Step 2: Minimum entry in current row is -1.
   *  Step 3: Column neighbors for min entry (ie. -1) are 5 and -2. -2 being the minimum neighbor. Its in the 4th row.
   *  Continue with steps 2-3 till we get the local min
   *  
   *  Time O(logn + n*5*log5) = O(n) 
   */
  public int findLM(int[][] matrix){
    //check
    if(matrix == null || matrix.length == 0 || matrix[0].length == 0)
      return -1;  //error code, it means no local min
    
    //init
    int row = matrix.length / 2;
    int col = findLocalMin(matrix[row]);
    
    return findLM(matrix, row, col);
  }
  
  private int findLM(int[][] matrix, int rowTH, int colTH){
    if(colTH >0 && colTH < matrix[0].length && rowTH > 0 && rowTH < matrix.length){
      
      //(rowth, colth) and 4 neighbors point, find the smallest one
      HashMap<Integer, String> hm = new HashMap();  //<key=matrix[xth][yth], value=xth+","+yth>
      List values = new ArrayList<Integer>(); // store the matrix[xth][yth]
      fillIn(hm, values, matrix, rowTH, colTH);
      Collections.sort(values);
      
      int[] position = splitValue(hm.get(values.get(0)));
      if( position[0] == rowTH && position[1] == colTH) 
        return matrix[rowTH][colTH];
      else
        return findLM(matrix, position[0], position[1]);            
    }
    
    return -1;  // error code, it means no Local Minimum
  }
  
  private void fillIn(HashMap<Integer, String> hm, List values, int[][] matrix, int rowTH, int colTH){
    //
    hm.put(matrix[rowTH][colTH], getValue(rowTH,colTH));
    values.add(matrix[rowTH][colTH]);
    
    //left side neighbor
    hm.put(matrix[rowTH][colTH-1], getValue(rowTH,colTH-1));
    values.add(matrix[rowTH][colTH-1]);
    
    //right side neighbor
    hm.put(matrix[rowTH][colTH+1], getValue(rowTH,colTH+1));
    values.add(matrix[rowTH][colTH+1]);
    
    //up side neighbor
    hm.put(matrix[rowTH-1][colTH], getValue(rowTH-1,colTH));
    values.add(matrix[rowTH-1][colTH]);
    
    //down side neighbor
    hm.put(matrix[rowTH+1][colTH], getValue(rowTH+1,colTH));
    values.add(matrix[rowTH+1][colTH]);
  }
  
  private String getValue(int rowTH, int colTH){
    return rowTH+","+colTH;
  }
  private int[] splitValue(String value){
    int[] ret = new int[2];
    int index = value.indexOf(',');
    ret[0] = Integer.valueOf(value.substring(0, index));
    ret[0] = Integer.valueOf(value.substring(index+1));
    
    return ret;
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
