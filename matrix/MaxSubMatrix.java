package matrix;

import util.Misc;

import java.util.Random;
import java.util.Stack;


/*
 * Given an NxM matrix of positive and negative integers, write code to find the 
 * sub-matrix with the largest possible sum.
 * 
 * This is about 2 dimension (BCM 2.15). To 1 dimension, refer to Array23.java (BCM 2.14)
 * 
 * O( rows * cols * min(rows, cols)
 * 
 * case 1: Given an N*M matrix of 0 and 1, find the max sub matrix with all 1.
 * Solution: change 0 to Integer.MIN_VALUE; find the sub-matrix with the largest possible sum 
 *  
 * case 2: Given an N*M matrix of 0 and 1, find the max sub matrix. ( all are 1 )  
 * 
 */

public class MaxSubMatrix
{

  /*
   * Time O(cols),  Space O(1)
   * 
   * from string.LargestSubSum
   */
  private int maxSubArray(int n, int a[]) {
    int currSum = 0, maxSubSum = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) {
      //compare sum and a[i]
      currSum = Math.max(a[i], currSum + a[i]);
      
      //compare sum and maxSubSum
      maxSubSum = Math.max(currSum, maxSubSum);
    }
    return maxSubSum;
  }
 

  /*
   * Time O( rows * cols * min(rows, cols))  Space O(cols)
   */
  public int maxSubMatrix(int[][] array, int rows, int cols) {
    int maxMatrix = Integer.MIN_VALUE;
    //int startRow=0, endRow=0, startcol=0, endcol = 0;
    
     // to every cols (1 dimension), store the max   
    for (int i = 0; i < rows; i++) {
      
      int[] b = new int[cols];
      int sumMatrix;
      for (int j = i; j < rows; j++)// 
      {
        int k;
        for (k = 0; k < cols; k++) {
          b[k] += array[j][k];
        }
     
        sumMatrix = maxSubArray(cols, b);
        maxMatrix = Math.max(maxMatrix, sumMatrix);
        
        //get the maxMatrix and startRow and endRow
//        if(maxMatrix <= sumMatrix){
//          maxMatrix = sumMatrix;
//          startRow = i;
//          endRow = j;
//        }
        
      }
    }
    
    //System.out.println("startRow and endRow are:" + startRow +" and "+ endRow);
    
    return maxMatrix;
  }


 
  /**
   * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle
   * containing all ones and return its area.
   * 
   * Time O(n*m)  Space O(n*m)
   *
   * Using Histograms - Stack
   */

  public int maximalRectangle(char[][] matrix) {
    if(matrix == null || matrix.length == 0){
      return 0;
    }

    int max = 0;
    int r = matrix.length;
    int c = matrix[0].length;

    int[] height = new int[c];
    Stack<Integer> s = new Stack<>();

    int top;
    int w;
    for(int i = 0; i < r; i++){
      for(int j = 0; j < c; j++){
        if(matrix[i][j] == '1'){
          height[j] += 1;
        }else{ // == '0'
          height[j] = 0;
        }

        /* Max rectangle in a histogram problem */
        while(!s.isEmpty() && height[j] < height[s.peek()]){
          top = s.pop();
          w = s.isEmpty()? j : j - s.peek() - 1;
          max = Math.max(max, height[top] * w);
        }

        s.push(j);
      }

      while(!s.isEmpty()){
        top = s.pop();
        w = s.isEmpty()? c : c - s.peek() - 1;
        max = Math.max(max, height[top] * w);
      }
    }

    return max;
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    MaxSubMatrix sv = new MaxSubMatrix();

    System.out.println("------------Start--maxSubMatrix------------ ");

    //init a matrix with int[][]
    int rowN = 4; // MxN matrix
    int colM = 5; // MxN matrix

    Random random = new Random();
    int[][] aInt = new int[rowN][colM];
    for (int row = 0; row < rowN; row++) {
      for (int col = 0; col < colM; col++) {
        //if (row > 3 & col < 3)
          aInt[row][col] = random.nextInt(5) - 2; // + random.nextInt(5) - 5;
//        else
//          aInt[row][col] = row * 10 + col;
      }
    }

    //print out the original matrix
    System.out.println(Misc.array2String(aInt));

    System.out.println("--get the sub matrix------------------------ ");

    int max = sv.maxSubMatrix(aInt, rowN, colM);

    //print out the result matrix
    System.out.println(max);
    
    
    
    System.out.println("------------End--maxSubMatrix------------ ");
    System.out.println("------------Start--maximalRectangle------------ ");
    
    String[] strs = {"0001010", "0100000", "0101001", "0011001", "1111110",
        "1001011", "0100101", "1101110", "1010101", "1110000"
    // "000"
    };

    char[][] matrix = new char[strs.length][];

    for (int i = 0; i < strs.length; i++) {
      matrix[i] = strs[i].toCharArray();
    }

    System.out.println( Misc.array2String(strs) );
    System.out.println(sv.maximalRectangle(matrix));
    
    
    System.out.println("------------End--maximalRectangle------------ ");
  }

}
