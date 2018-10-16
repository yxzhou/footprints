package fgafa.uva.dp.StringComputerN164;

import java.io.*;
import java.util.*;

//import fgafa.util.Misc;

class Main
{

  /*
   * calculate the min steps change, (  )
   * input arr1={a1, a2, ---, am},  arr2={b1, b2, ---, bn}  
   * 
   * We use the notation dp[i][j] = the min steps change of x[0..i] and y[0..j]
   * dp[i][j] = j                              if i == 0
   *          = i                              if j == 0
   *          = min(dp[i][j-1] + 1,   (Insert)            
   *                dp[i-1][j] + 1,   (Delete)
   *                dp[i-1][j-1]) + s (Change) if arr1[i] == arr2[j], s=0;  else s=1
   *               )    
   *           
   * time O(m*n) and space O(m*n)
   * 
   */
  //bottom-up translation of this recurrence
  public String stringChange_DP(char[] arr1, char[] arr2) {
    //init
    int M = arr1.length;
    int N = arr2.length;  

    int[][] dp = new int[M + 1][N + 1];
    final int O = 0, C = 1, I = 2, D = 3;
    int[][] actions = new int[M+1][N+1]; 

    for(int j = 0; j<=N; j++){
      dp[0][j] = j;
      actions[0][j] = I;
    }  
    for(int i = 0; i<=M; i++){
      dp[i][0] = i;
      actions[i][0] = D;
    }  
    
    //main
    int s = 0;
    for (int i = 1; i <= M; i++) {
      for (int j = 1; j <= N; j++) {
        if (arr1[i - 1] == arr2[j -1])
          s = 0;
        else 
          s = 1;
        
        dp[i][j] = Math.min(dp[i-1][j-1] + s, Math.min(dp[i][j-1], dp[i-1][j]) + 1 );
        
//        if (dp[i][j] == dp[i-1][j-1])  // Match
//          actions[i][j] = O;
//        else if (dp[i][j] == dp[i-1][j-1] + 1)  // Change
//          actions[i][j] = C;
//        else if (dp[i][j] == dp[i][j-1] + 1)  // Insert
//          actions[i][j] = I;
//        else if (dp[i][j] == dp[i-1][j] + 1)  // Delete
//          actions[i][j] = D;          
        
        if (dp[i][j] == dp[i-1][j] + 1)  // Delete
          actions[i][j] = D;
        else if (dp[i][j] == dp[i][j-1] + 1)  // Insert
          actions[i][j] = I;
        else if (dp[i][j] == dp[i-1][j-1] + 1)  // Change
          actions[i][j] = C;
        else                                // Match
          actions[i][j] = O;
        
      }
    }

    // recover LCS itself and print it to standard output
    //System.out.println(Misc.array2String(dp));
    //System.out.println(Misc.array2String(actions));
    
    //backtracker,  output
    List<String> output= new ArrayList<String>();  
    int i = M,j = N;
    while(true){
      if(i==0 && j==0) break;
      switch(actions[i][j]) {
        case D:
          output.add("D" + arr1[i-1] + String.format("%02d", j+1));
          i--;
          break;
        case I:
          output.add("I" + arr2[j-1] + String.format("%02d", j));
          j --;
          break;
        case C:
          output.add("C" + arr2[j-1] + String.format("%02d", j));
        case O:
          i --;
          j --;
          break;
      }      
    }
    
    StringBuilder sb = new StringBuilder();
    for(int k = output.size() -1; k>-1;  k--)
      sb.append(output.get(k));
    sb.append("E");
    return sb.toString();
  }
  
  
  public static void main(String[] args) throws Exception {

    //init
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    //long start = System.currentTimeMillis();
    
    String line;
    String[] strs = new String[2]; 
    StringTokenizer st; 
    try {
      while (in.hasNext()) { 
        //init
        
        //read
        line = in.nextLine().trim();
        
        //exit when it's '#'
        if("#".equals(line))
          return;
        else if("".equals(line))
          continue;
        
        st = new StringTokenizer(line, " ");
        
        for(int i=0; st.hasMoreTokens(); i++)
          strs[i] = st.nextToken();
        
        //main and output               
        System.out.println(sv.stringChange_DP(strs[0].toCharArray(), strs[1].toCharArray()));  
        
        
       }
      
    }
    catch (Exception e) {
      //e.printStackTrace();
    }
    finally {
      in.close();
      
      //System.out.println(System.currentTimeMillis() - start);
    }
    
  }
    
}