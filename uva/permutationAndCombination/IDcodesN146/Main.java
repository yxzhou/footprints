package fgafa.uva.permutationAndCombination.IDcodesN146;

import java.io.*;
import java.util.*;

/*
 *  Input       Output 
 *  54321       No Successor
 *  35421       41235
 *  354321      412335
 */

class Main
{
   
  final static String NO_SUCCESSOR = "No Successor";

  public String nextPermutation(String curr){
    int len = curr.length();
    int i = len -1, j = len -1;
    
    while(i>0 && curr.charAt(i) <= curr.charAt(i-1) ){
      i -- ;
    }
      
    if(i == 0)
      return NO_SUCCESSOR;
    
    i--;
    char ci = curr.charAt(i);
    while(j>0 && curr.charAt(j) <= ci){
      j --;
    }
    
    StringBuilder sb = new StringBuilder();
    
    for(int k=0; k<i; k++)
      sb.append(curr.charAt(k));
    sb.append(curr.charAt(j));
    for(int k=len-1; k>j; k--)
      sb.append(curr.charAt(k));
    sb.append(curr.charAt(i));
    for(int k=j-1; k>i; k--)
      sb.append(curr.charAt(k));    
    
    return sb.toString();
  }
  
  public static void main(String[] args) throws Exception {

    //init
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    
    String line;
    try {
      while (in.hasNext()) {        
        //read
        line = in.nextLine().trim();

        //exit when it's #
        if("#".equals(line))
            return;
        
        System.out.println(sv.nextPermutation(line));
        
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