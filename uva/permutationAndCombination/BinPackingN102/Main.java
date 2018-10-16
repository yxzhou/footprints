package fgafa.uva.permutationAndCombination.BinPackingN102;

import java.io.BufferedInputStream;
import java.util.Scanner;

class Main
{
  
 

  /*
   * Time O( )  Space O()
   */
  public void binPacking(int[][] bins, int sum) {
      
    int subSum = 0, maxSubSum = -1;
    int perm = 0;
    
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 3; j++)// 
        subSum += bins[j][permutation[i][j]];
      /*
       * ">" and the the permutation is to achieve: <br> 
       * If more than one order of brown, green, and clear bins yields the minimum number of movements
       * then the alphabetically first string representing a minimal configuration should be printed.
       */      
      if(subSum > maxSubSum){  
        perm = i;
        maxSubSum = subSum;
      }
      
      subSum = 0;
    }
    
    //output
    StringBuilder bd = new StringBuilder();
    for (int j = 0; j < 3; j++){
      bd.append(category[permutation[perm][j]]);
    }
    bd.append(" ");
    bd.append(sum - maxSubSum);
    
    System.out.println(bd.toString());
    
  }

  private static char[] category = {'B','G','C'};  // 0-B, 1-G, 2-C
  /*
   * If more than one order of brown, green, and clear bins yields the minimum
   * number of movements then the alphabetically first string representing a
   * minimal configuration should be printed. 
   * so we check in order of BCG, BGC, CBG, CGB, GBC, GCB
   */
  private static int[][] permutation = {{0, 2, 1}, {0, 1, 2}, {2, 0, 1}, {2, 1, 0}, {1, 0, 2}, {1, 2, 0}};  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
      
    int sum = 0;
    int[][] bins = new int[3][3];  // BGCBGCBGC 
            
    try{
      while(in.hasNext()) {
        for(int i=0; i<3; i++){
          for(int j=0; j<3; j++){
            bins[i][j] = in.nextInt();
            
            sum += bins[i][j];
          }
        }
      
        sv.binPacking(bins, sum);
        
        sum = 0;
      }
    }catch (Exception e){
      //e.printStackTrace();
    }finally{
      in.close();
    }
    
    
  }
  
}
