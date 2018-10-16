package fgafa.uva.dp.DollarsN147;

import java.io.*;
import java.util.*;

class Main
{
  //currency consists of $100, $50, $20, $10, and $5 notes and $2, $1, 50c, 20c, 10c and 5c coins
  private static final int[] set = {1, 2, 4, 10, 20, 40, 100, 200, 400, 1000, 2000};// {  2000, 1000, 400, 200, 100, 40, 20, 10, 4, 2, 1};
  private static final double EPS = 1e-3d;
    
  private static long[] dp = new long[6001];
  
  static{
   /* dp[i][j] = dp[i-1][j] + dp[i][j-set[i]] */
     dp[0] = 1;
     for(int i=0; i<11; i++)
       for(int j=0; j<6001; j++)
         if(j >= set[i])
           dp[j] += dp[j-set[i]];
   
  }
   
  
  public static void main(String[] args) throws Exception {

    //init
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    
    double curr;
    long ways;
    try {
      while (in.hasNext()) {        
        //read
        curr = in.nextDouble();

        //exit when it's 0.00
        if(curr < EPS )
            return;

        ways = dp[(int)(curr * 20)];  // curr * 100 / 5 ==> curr * 20,  Each amount will be valid, that is will be a multiple of 5c
        
        System.out.printf("%6.2f%17d%n",curr, ways);
        
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