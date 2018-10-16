package fgafa.uva.dp.HistoryGradingn111;

import java.io.*;
import java.util.*;

class Main
{
 
  
  /*
   * fetch Longest Common Subsequence with DP on two arrays.
   * input arr1={a1, a2, ---, am},  arr2={b1, b2, ---, bn}  where m>=n 
   * 
   * We use the notation opt[i][j] = length of LCS of x[0..i-1] and y[0..j-1]
   * opt[i][j] = 0                              if i == 0 or j == 0
   *           = opt[i-1][j-1] + 1              if arr1[i] == arr2[j]
   *           = max(opt[i][j-1], opt[i-1][j])  otherwise
   * 
   *           
   * time O(m*n) and space O(m*n)
   * 
   */

  public int calLCSeq_DP2(int[] ref, int[] student, int n){

    //main program
    int[][] opt = new int[n+1][n+1];  // opt[i][j] = length of LCS of x[0..i] and y[0..j], default it's 0

    // compute length of LCS and all subproblems via dynamic programming
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
            if (ref[j] == student[i])
                opt[i][j] = opt[i-1][j-1] + 1;
            else 
                opt[i][j] = Math.max(opt[i][j-1], opt[i-1][j]);
        }
    }
    
    return opt[n][n];
  }

  
  public static void main(String[] args) throws Exception {
    Main sv = new Main();
    
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    int n;
    
    try {
      while (in.hasNext()) {        
        //read
        n = in.nextInt();
        int[] reference = new int[n+1]; 
        for(int i=1; i<=n; i++){
          reference[in.nextInt()] = i;
        }
        
        while(in.hasNext()) { 
          int[] student = new int[n+1];
          
          /*The second line will contain n integers, indicating the correct chronological order of n events*/
          for(int i=1; i<=n; i++){
            //student[i] = in.nextInt();
            student[in.nextInt()] = i ;
          }
        
          //main and output        
          System.out.println(sv.calLCSeq_DP2(reference, student, n));          
        }

      }
    }
    catch (Exception e) {
      //e.printStackTrace();
    }
    finally {
      in.close();
    }
    
  }
  
}
