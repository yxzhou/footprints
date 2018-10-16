package fgafa.uva.adhoc.PerfectHash188;

import java.io.*;
import java.util.*;

class Main
{

  public static int hashWord(String word){
    int w = 0;
    
    for(char c : word.toCharArray())
      w =  (w << 5) + (c-96);  //w*32 ,  'a'=97
    
    return w;
  }

  public static int hashLine_iterative(int c, int[] w, int n, int len){
        
    outer: while(true){

      //System.out.println("==="+c);
      
      for(int i = len - n; i< len; i++)
        for(int j=i+1; j< len; j++)
          if( (c/w[i]) % n == (c/w[j]) % n ){
            c = Math.min((c/w[i] + 1) * w[i] , (c/w[j] + 1) * w[j]);
            continue outer; 
          }
       
      return c;
    }
      
  }
  
  public static int hashLine_recurrsive(int c, int[] w, int n){
    
    //System.out.println("==="+c);
    
    for(int i = n; i< w.length; i++)
      for(int j=i+1; j< w.length; j++)
        if( (c/w[i]) % n == (c/w[j]) % n )
          return hashLine_recurrsive(Math.min(((int)c/w[i] + 1) * w[i] , ((int)c/w[j] + 1) * w[j]), w, n);
    
    return c;
  }
  
  public static void main(String[] args) throws Exception {

    //init
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    //long start = System.currentTimeMillis();
    
    String line;
    int n = 0, max = 13;
    int[] w;    
    StringTokenizer st; 
    try {
      outer: while (in.hasNext()) { 
        //init
        n=0;
        w = new int[max];
        
        //read
        line = in.nextLine();
        st = new StringTokenizer(line, " ");
        
        while(st.hasMoreTokens())
          w[n++] = hashWord(st.nextToken());
        
        //order 
        Arrays.sort(w);
        //check
        for(int i=max-n+1; i<max; i++)
          if(w[i] == w[i-1])
            continue outer;
          
        //main and output       
        System.out.println(line);
        System.out.println(hashLine_iterative(w[max - 2], w, n, max));  // c is start from the second largest.
        System.out.println();
        
       }
      
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      in.close();
      
      //System.out.println(System.currentTimeMillis() - start);
    }
    
  }
    
}