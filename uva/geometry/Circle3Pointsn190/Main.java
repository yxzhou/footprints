package uva.geometry.Circle3Pointsn190;

import java.io.*;
import java.util.*;

class Main
{
  
  /*
   * (x - h)^2 + (y - k)^2 = r^2
   * x^2 + y^2 +c*x + d*y + e = 0 
   */
  
  public static void main(String[] args) throws Exception {

    //init    
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    long start = System.currentTimeMillis();
    
    final int N = 3;
    final double EPS = 1e-6d;
    double x, y, xd10, yd10, xd12, yd12, c, d, r, h, k, e, tmp;
    double[][] p = new double[N][2];    // 3 points,  
    try {
      while (in.hasNext()) {        
        //read 
        for(int i = 0; i< N; i++){
          p[i][0] = in.nextDouble();  //x
          p[i][1] = in.nextDouble();  //y
        }
                
        /* main
         * (x1*x1 - x0*x0) + (y1*y1 - y0*y0) + c(x1-x0) + d(y1-y0) = 0 
         * (x1*x1 - x2*x2) + (y1*y1 - y2*y2) + c(x1-x2) + d(y1-y2) = 0
         * 
         * when x1 = x2:  d = y1+y0, c= 0 - (x1+x2) - (y1-y2)(y1+y2 +y1+y0) / (x1-x2) 
         * 
         */
        xd10 = p[1][0] - p[0][0];  //x1 - x0
        yd10 = p[1][1] - p[0][1];  //y1 - y0
        xd12 = p[1][0] - p[2][0];  //x1 - x2
        yd12 = p[1][1] - p[2][1];  //y1 - y2          
        
        if(Math.abs(xd10) < EPS ){
          if(Math.abs(yd10) < EPS)
            continue;   // it means there are tow points duplicated. And the circle can't be got.  
          d = 0 - p[1][1] - p[0][1] ; 
          c = (yd12 - yd10) * yd12 / xd12 - (p[1][0] + p[2][0]); 
        } 
        else if(Math.abs(xd12) < EPS){
          if(Math.abs(yd12) < EPS)
            continue;   // it means there are tow points duplicated. And the circle can't be got. 
          d = 0 - p[1][1] - p[2][1]; 
          c = (yd10 - yd12) * yd10 / xd10 - (p[1][0] + p[0][0]);          
        }
        else if(Math.abs(yd10) < EPS){
          //if Math.abs(yd12) < EPS, it should be found at the above instead of here. 
          c = 0 - p[1][0] - p[0][0];
          d = (xd12 - xd10) * xd12 / yd12 -(p[1][1] + p[2][1]); 
        }
        else if(Math.abs(yd12) < EPS){
          c = 0 - p[1][0] - p[2][0];
          d = (xd10 - xd12) * xd10 / yd10 - (p[1][1] + p[0][1]);       
        }
        else{
          if(xd10 * yd12 == yd10 * xd12) // it means the three points are in one line. The circle can't be got.
            continue;
          
          d = (xd10 * xd12 * (xd12 - xd10) + xd12 * yd10 * (p[1][1] + p[0][1]) - xd10 * yd12 * (p[1][1] + p[2][1]) ) / (xd10 * yd12 - xd12*yd10) ; 
          c = 0 - (p[1][0] + p[0][0]) - (p[1][1] + p[0][1] + d) * yd10 / xd10; 
        }
        
        e = 0 - Math.pow(p[0][0], 2) - Math.pow(p[0][1], 2) - c*p[0][0] - d*p[0][1]; 
        
        h = c / 2;
        k = d / 2;
        r = Math.sqrt(Math.pow(p[1][0] + h, 2) + Math.pow(p[1][1] + k, 2)); 
            
        System.out.printf("(x %s %.3f)^2 + (y %s %.3f)^2 = %.3f^2%n", h<0?"-":"+" , Math.abs(h), k<0?"-":"+" , Math.abs(k) , r);
        System.out.printf("x^2 + y^2 %s %.3fx %s %.3fy %s %.3f = 0%n", c<0?"-":"+" , Math.abs(c), d<0?"-":"+",  Math.abs(d), e<0?"-":"+",  Math.abs(e));
        System.out.println();
        
       }
      
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    finally {
      in.close();
      System.out.println(System.currentTimeMillis() - start);
    }
    
    
  }
    
}