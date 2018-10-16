package fgafa.uva.dp.LetMeCountTheWayN357;

import java.io.*;
import java.util.*;

class Main
{

  //currency consists of penny, nickel, dime, quarter, half-dollar (1c, 5c, 10c, 25c, 50c)
  private static final int[] set = {1, 5, 10, 25, 50};
  private static final int MAX = 30001;  //0 and 30000 inclusive
    
  private static long[] dp = new long[MAX];  
  
  static{
   /* dp[i][j] = dp[i-1][j] + dp[i][j-set[i]] */
     dp[0] = 1;
     for(int i=0; i<5; i++)
       for(int j=0; j<MAX; j++)
         if(j >= set[i])
           dp[j] += dp[j-set[i]];

  }
  
  public static void main(String[] args) throws Exception {

    //init
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    
    int curr;    
    try {
      while (in.hasNext()) {        
        //read
        curr = in.nextInt();

        if(dp[curr] == 1)
          System.out.printf("There is only 1 way to produce %d cents change.%n", curr);
        else
          System.out.printf("There are %d ways to produce %d cents change.%n", dp[curr], curr);
        
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