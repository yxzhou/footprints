package fgafa.uva.geometry.PipeFitterN121;

import java.io.*;
import java.util.*;

class Main
{
  //private static final double THREE_SQRT = Math.sqrt(3); 
  private static final double SKEW_UNIT = Math.sqrt(3) * 2 / 3; 

  public static void main(String[] args) throws Exception {

    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    double width, height; 

    try {
      while (in.hasNext()) { 
        //init
        int grid = 0, skew1 = 0, skew2 = 0, lineN = 0;
        
        //read
        width = in.nextDouble();
        height = in.nextDouble();

        if(height >= 1 && width >= 1){
          grid = (int)width * (int) height;
          //System.out.print(  "\n grid : "+ grid );  

          lineN = (int)((height - 1) * SKEW_UNIT) + 1;
          skew1 = (lineN >> 1) * (int)(width - 0.5) + (lineN - (lineN >> 1)) * (int)width; 
          //System.out.print( " lineN: "+ lineN +" skew1: " + skew1); 
          
          lineN = (int)((width - 1) * SKEW_UNIT) + 1;
          skew2 = (lineN >> 1) * (int)(height - 0.5) + (lineN - (lineN >> 1)) * (int)height; 
          //System.out.print( " lineN: "+ lineN +" skew2: " + skew2); 
          
          skew1 = Math.max(skew1, skew2);
        }
    
        //output
        System.out.println( Math.max(grid, skew1) + ((skew1 > grid)? " skew" : " grid")); 
        
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
