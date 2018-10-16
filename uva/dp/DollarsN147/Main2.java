package fgafa.uva.dp.DollarsN147;

import java.io.*;
import java.util.*;

class Main2
{
   

  //currency consists of $100, $50, $20, $10, and $5 notes and $2, $1, 50c, 20c, 10c and 5c coins
  private static final int[] set = {1, 2, 4, 10, 20, 40, 100, 200, 400, 1000, 2000};// {  2000, 1000, 400, 200, 100, 40, 20, 10, 4, 2, 1};
  private static final double EPS = 1e-3d;
  
  //private static long[][] results = new long[11][6001];  // real numbers no greater than $300.00,  300*20 = 6000,  
  private static long[] dp = new long[6001];
  
  static{
   long start = System.currentTimeMillis(); 
    
//    for(int j=0; j<6001; j++){
//      results[0][j] = 1;
//    }
//    
//    for(int j=0; j<6001; j++){
//      results[1][j] = (j>>1) + 1;
//    }
//    
   /* dp[i][j] = dp[i-1][j-set[i]*0] + dp[i-1][j-set[i]*1] + --- */
//    for(int i=2; i >= 0; i--){
//      for(int j=0; j<6001; j++){
//        for(int k=j/set[i]; k>=0 ; k--)
//          results[i][j] +=  makeChange_cache(j-set[i]*k, i-1);  // results[i+1][j-set[i]*k];
//      }
//    }
//    
//    System.out.println("0.10 " + results[9][2]);
//    System.out.println("0.20 " + results[8][4]);
//    System.out.println("0.50 " + results[7][10]);
//    System.out.println("   1 " + results[6][20]);
//    System.out.println("   2 " + results[5][40]);
//    System.out.println("   5 " + results[4][100]);
//    System.out.println("  10 " + results[3][200]);
//    System.out.println("  20 " + results[2][400]);
//    System.out.println("  50 " + results[1][1000]);
//    System.out.println(" 100 " + results[0][2000]);
//    System.out.println(" 300 " + results[0][6000]);
    
   /* dp[i][j] = dp[i-1][j] + dp[i][j-set[i]] */
     dp[0] = 1;
     for(int i=0; i<11; i++)
       for(int j=0; j<6001; j++)
         if(j >= set[i])
           dp[j] += dp[j-set[i]];
   
     System.out.println("0.10 " + dp[2]);
     System.out.println("0.20 " + dp[4]);
     System.out.println("0.50 " + dp[10]);
     System.out.println("   1 " + dp[20]);
     System.out.println("   2 " + dp[40]);
     System.out.println("   5 " + dp[100]);
     System.out.println("  10 " + dp[200]);
     System.out.println("  20 " + dp[400]);
     System.out.println("  50 " + dp[1000]);
     System.out.println(" 100 " + dp[2000]);
     System.out.println(" 300 " + dp[6000]);
     
    System.out.println( System.currentTimeMillis() - start );
  }
  
//  public static long makeChange_cache(int curr, int index){      
//    if(curr == 0)
//      return 1;
//
//    while( curr < set[index])
//      index --;
//      
//    return results[index][curr];
//  }
  
  
  /*
   * ok when curr < about 50 
   */
  public static long makeChange_recursive(int curr, int index){   
    if(index == 10 || curr == 0)
      return 1;
    
    int ways = 0;
    for(int i=0;  set[index]*i <= curr; i++ )
      ways += makeChange_recursive(curr - set[index]*i, index - 1 );
      
    return ways;
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