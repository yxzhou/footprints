package uva.dp.MakingChangeN166;

import java.io.*;
import java.util.*;

class Main
{

  // currency consists of $2, $1, 50c, 20c, 10c and 5c coins
  private static final int[] set = {1, 2, 4, 10, 20, 40};

  // always be less than $5.00
  private static final int MAX = 101;


  /*
   *  
   */
  public static int makeChange(int[] coins, int curr) {
    // init
    int[] dp = new int[MAX];
    for (int i = 1; i < MAX; i++)
      dp[i] = Integer.MAX_VALUE;
    dp[0] = 0;
    
    //all possible Addition 
    int leftBorder = 0;
    for (int c = 0; c < 6; c++) {
      while (coins[c] > 0) {
        for (int i = Math.min(leftBorder, MAX - set[c] - 1); i > -1; i--)
          if(dp[i] != Integer.MAX_VALUE)
            dp[i + set[c]] = Math.min(dp[i] + 1, dp[i + set[c]]);

        leftBorder += set[c];
        coins[c]--;
      }
    }

    //all possible subtraction
    for (int i = curr + 1; i < Math.min(leftBorder + 1, MAX); i++) 
      if(dp[i] != Integer.MAX_VALUE)
        dp[curr] = Math.min(dp[curr], dp[i] + minimumChange(i - curr));

    return dp[curr];
  }



  private static int minimumChange(int v) {
    for (int i = 5; i > -1; i--) {
      if (v >= set[i])
        return 1 + minimumChange(v - set[i]);
    }
    return 0;
  }



  public static void main(String[] args) throws Exception {

    // init
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");

    int curr;
    int[] coins = new int[6];
    boolean exitFlag = true;
    try {
      while (in.hasNext()) {
        // init
        exitFlag = true;

        // read
        for (int i = 0; i < 6; i++) {
          coins[i] = in.nextInt();
          exitFlag &= (coins[i] == 0);
        }

        // exit when --
        if (exitFlag)
          return;

        //output
        curr = (int) (in.nextDouble() * 20);
        System.out.printf("%3d%n", makeChange(coins, curr));

      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      in.close();
    }

  }

}