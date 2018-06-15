package fgafa.math;

import java.util.Random;

public class MathmaticsII
{
  
  /*
   * Even == the last bit is 0
   * 
   */
  public static boolean isEven(int num){     
    if(num < 0) return false;
    
    return (num & 1) == 0;
  }
  
  public static boolean isOdd(int num){     
    if(num < 0) return false;
    
    return (num & 1) != 0;
  }
  
  /*
   * calculate the GCD (greatest common divisor) 
   * 
   * Basic mathmatics (Euclidean algorithm) :  
   *
   * Example:  
   * Mod: GCD(42, 30) = GCD(30, 12) = GCD(12, 6) = GCD(6, 0)
   * Sub: GCD(42, 30) = GCD(30, 12) = GCD(12, 18) = GCD(18, 12) = GCD(12, 6)= GCD(6, 6) = GCD(6, 0) = 6  
   */
  
  public static int calGCD_mod(int x, int y){
    if(x < y) return calGCD_mod(y, x);
   
    if(y == 0)
      return x;
    else
      return calGCD_mod(y, x%y);
     
    //iteral
//    int tmp;
//    while(y != 0){
//      tmp = y;
//      y = x % y;
//      x = tmp;
//    }
//    return x;
    
  }
  
  public int calGCD_n(int a, int b){
      
      if(b == 0){
          return a;
      }
      
      return calGCD_n(b,  a%b);
  }
  
  public static int calGCD_sub(int x, int y){
    
    if(x < y) return calGCD_sub(y, x);
    if(y == 0) 
      return x;
    else{
      if(isEven(x)){
        if(isEven(y))
          return ( calGCD_sub(x >> 1, y >>1) << 1);
        else
          return calGCD_sub(x >>1, y) ;
      }else{
        if(isEven(y))
          return calGCD_sub(x, y >>1);
        else
          return calGCD_sub(y, x - y) ;
      }
      
    }    
  }
  


  
  
  /*
   * calculate the LCM (Least Common Multiple)
   * 
   * LCM(x,y) = (x*y) / GCD(x, y)
   *  
   */
  public static int calLCM(int x, int y){
    
    return x * y / calGCD_mod(x, y);
    
  }
    

   
  

  

  
  
  public static void main(String[] args){
    MathmaticsII sv = new MathmaticsII();
    
    int x = 120;
    int y = 72;
    System.out.println("--- The Greatest Common Divisor of "  + x + " and " + y + " is: " + calGCD_mod(x, y) + " " + calGCD_sub(x, y));
    System.out.println("--- The Least Common Multiple of "  + x + " and " + y + " is: " + calLCM(x, y) );

    // test
    int divident = 6;
    int divisor = 3;
    
    int quotient =  divident / divisor;
    double s1 = java.lang.Math.log(divident);
    double s2 = java.lang.Math.log(divisor);
        
    int q1 = 1 << (int)(s1 - s2);
    
    int q2 = 1 << 1;
    
    System.out.println("test if Integer.MIN_VALUE < 0: "+ (Integer.MIN_VALUE < 0) + "\t" +  Integer.MIN_VALUE);
    System.out.println("test if -Integer.MIN_VALUE < 0: "+ (-Integer.MIN_VALUE < 0) + "\t" +  Integer.MIN_VALUE);
    long min = (long)Integer.MIN_VALUE; 
    long abs_min = (long)(min^(min>>31)) - (min>>31);
    System.out.println("test if -min < 0: "+ (-min < 0) + "\t" +  -min);
    System.out.println("test if abs_min < 0: "+ (abs_min < 0) + "\t" +  abs_min);
    System.out.println("test if Integer.MAX_VALUE > 0: "+ (Integer.MAX_VALUE > 0) + "\t" +  Integer.MAX_VALUE);
    System.out.println("test if -Integer.MAX_VALUE > 0: "+ (-Integer.MAX_VALUE > 0) + "\t" +  Integer.MAX_VALUE);
    
    
    // test 
    //int int1 = Integer.MAX_VALUE; 
    int int1 = Integer.MIN_VALUE;
    
    float result = 1.0f;
    for(int i = 0; i< 30; i++){
      System.out.println("====="+i);
      
      try {
        result *= int1; 
        System.out.println(result);
        
        if(Float.isInfinite(result))
          System.out.println("==Infinity=");
        
      }
      catch (Exception e) {
        System.out.println("over");
      }
      
    }

    
  }
  
}
