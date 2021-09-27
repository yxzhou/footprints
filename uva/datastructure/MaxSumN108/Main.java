package uva.datastructure.MaxSumN108;

import java.io.BufferedInputStream;
import java.util.Scanner;

class Main
{
  
  /*
   * Time O(n),  Space O(1)
   */
  private int maxSubArray(int n, int a[]) {
    int sum = 0, maxSubSum = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) {
      //compare sum and a[i], sum = max(a[i], sum + a[i]);
      sum = Math.max(a[i], sum + a[i]);
      
      //compare sum and maxSubSum
      maxSubSum = Math.max(sum, maxSubSum);
    }
    return maxSubSum;
  }
 

  /*
   * Time O( n*n*n)  Space O(n)
   */
  public int maxSubMatrix(int[][] array, int n) {
    //check
//    if(array == null || n==0)
//      return Integer.MIN_VALUE;
    
    int maxMatrix = Integer.MIN_VALUE;
    
     // to every column (1 dimension), store the max   
    for (int i = 0; i < n; i++) {
      
      int[] b = new int[n];
      int sumMatrix;
      for (int j = i; j < n; j++)// 
      {
        int k;
        for (k = 0; k < n; k++) {
          b[k] += array[j][k];
        }
     
        sumMatrix = maxSubArray(n, b);
        maxMatrix = Math.max(maxMatrix, sumMatrix);
       
      }
    }
    
    return maxMatrix;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    
    try{
      int n = in.nextInt();
      int[][] matrix = new int[n][n]; 
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          matrix[i][j] = in.nextInt();
        }
      }
      
      System.out.println(sv.maxSubMatrix(matrix, n));      
    }catch(Exception e){
      
    }finally{
      in.close();
    }

    
  }

  
}
