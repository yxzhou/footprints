package fgafa.uva.adhoc.PowerofCryptographyN113;

import java.io.*;
import java.util.*;

class Main
{
  public static void main(String[] args) throws Exception {

    
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    double n, p;
    try {
      while (in.hasNext()) { 
        //init
        
        //read
        n = in.nextDouble();
        p = in.nextDouble();

        //output
        System.out.println(Math.round(Math.pow(p, 1/n)));
        
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
