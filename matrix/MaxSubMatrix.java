package fgafa.matrix;

import java.util.Random;
import java.util.Stack;

import fgafa.util.Misc;


/*
 * Given an NxM matrix of positive and negative integers, write code to find the 
 * sub-matrix with the largest possible sum.
 * 
 * This is about 2 dimension (BCM 2.15). To 1 dimension, refer to Array23.java (BCM 2.14)
 * 
 * O( rows * cols * min(rows, cols)
 * 
 * case 1: Given an N*m matrix of 0 and 1, find the max sub matrix with all 1.
 * Solution: change 0 to Integer.MIN_VALUE; find the sub-matrix with the largest possible sum 
 *  
 * case 2: Given an N*m matrix of 0 and 1, find the max sub matrix. ( all are 1 )
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
   * O(n*m) version  Space O(n*m)
   * 
   * @param matrix
   * @return
   */
  public int maximalRectangle1(char[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return 0;
    }

    final int M = matrix.length;
    final int N = matrix[0].length;

    int[][] mx = new int[M][N]; // mx[m - 1][] store the tmp value (0 or 1).  mx[m][] store the height from m-1 to m.( 0<=m<=m-2 )

    for (int col = 0; col < N; col++) {

      mx[M - 1][col] = matrix[M - 1][col] - '0';
      for (int row = M - 2; row >= 0; row--) {
        if (matrix[row][col] == '1') 
          mx[row][col] = mx[row + 1][col] + 1;
        else 
          mx[row][col] = 0;
        
      }
    }

    int maxArea = 0;
    for (int[] height : mx) {
      /* Max rectangle in a histogram problem */
      Stack<Integer> stack = new Stack<Integer>();
      for (int i = 0; i < N; i++) {

        if (stack.isEmpty() || height[i] >= height[stack.peek()]) {
          stack.push(i);
          continue;
        }

        int lastIdx = -1;
        while (!stack.isEmpty() && height[i] < height[stack.peek()]) {
          lastIdx = stack.pop();

          maxArea = Math.max(maxArea, (i - lastIdx) * height[lastIdx]);
        }

        if (lastIdx >= 0) {
          height[lastIdx] = height[i];
          stack.push(lastIdx);
        }
        
      }

      while (!stack.isEmpty()) {
        int idx = stack.pop();
        
        maxArea = Math.max(maxArea, (N - idx) * height[idx]);
      }
    }

    return maxArea;
  }
  
  
  public int maximalRectangle11(char[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return 0;
    }

    final int M = matrix.length;
    final int N = matrix[0].length;

    int[][] mx = new int[M][N]; // mx[m - 1][] store the tmp value (0 or 1).  mx[m][] store the height from m-1 to m.( 0<=m<=m-2 )

    for (int col = 0; col < N; col++) {

      mx[M - 1][col] = matrix[M - 1][col] - '0';
      for (int row = M - 2; row >= 0; row--) {
        if (matrix[row][col] == '1') 
          mx[row][col] = mx[row + 1][col] + 1;
        else 
          mx[row][col] = 0;
        
      }
    }

    int maxArea = 0;
    for (int[] height : mx) {
      /* Max rectangle in a histogram problem */
      Stack<Integer> stack = new Stack<Integer>();

      int i=0;
      while (i < N) {
        if(stack.empty() || height[stack.peek()] <= height[i])
            stack.push(i++);
        else {
            int t = stack.pop();  
          
            maxArea = Math.max(maxArea, height[t] * (stack.empty() ? i : i - stack.peek() - 1 ));
        }
      }
  
      while (!stack.isEmpty()) {
        int t = stack.pop();
        
        maxArea = Math.max(maxArea, height[t] * (stack.empty() ? i : i - stack.peek() - 1 ));
      }  
      
    }

    return maxArea;
  }

  /* TODO  recorrect */
  public int maximalRectangle2(char[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return 0;
    }

    final int M = matrix.length;
    final int N = matrix[0].length;

    int[] H = new int[N];
    int[] L = new int[N];
    int[] R = new int[N]; 

    int ret = 0;
    for (int i = 0; i < M; ++i) {
        int left = 0, right = N;
        // calculate L(i, j) from left to right
        for (int j = 0; j < N; ++j) {
            if (matrix[i][j] == '1') {
                ++H[j];
                L[j] = Math.max(L[j], left);
            }
            else {
                left = j+1;
                H[j] = 0; L[j] = 0; R[j] = N;
            }
        }
        // calculate R(i, j) from right to right
        for (int j = N-1; j >= 0; --j) {
            if (matrix[i][j] == '1') {
                R[j] = Math.min(R[j], right);
                ret = Math.max(ret, H[j]*(R[j]-L[j]));
            }
            else {
                right = j;
            }
        }
    }

    return ret;
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
    System.out.println(sv.maximalRectangle1(matrix));
    
    
    System.out.println("------------End--maximalRectangle------------ ");
  }

}
