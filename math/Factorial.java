package math;

import java.math.BigDecimal;

import util.TimeCost;

/**
 * 
 * BigDecimal factorial(int n)          not recursive
 * BigDecimal factorial_R(int n)        recursive
 * StringBuffer factorial_Array(int n)  int array to avoid overflow
 * 
 * Note:
 * 1 There is overflow, if it's int, the max is 12!, if it's long, the max 20! .  
 *   To avoid it, we use java.math.BigDecimal or int array
 * 2 There is java.lang.StackOverflowError if there are too many recursive call. 
 *   Example, when n = 10000, factorial_R throw java.lang.StackOverflowError. 
 * 3 There is performance issue if there are big number multiple.
 *   Example, The factorial(10000)      timeCost:797,  The factorial(50000)       timeCost:22717
 *            The factorial2(10000)     timeCost:562,  The factorial_Array(50000) timeCost:19217
              The factorial_Array(10000)timeCost:1766, The factorial_Array(50000) timeCost:53229
 */

public class Factorial
{

  /**
   * n ! = 1 * 2 * 3 * --- *n
   * 
   * 
   * 
   * @param n
   * @return
   */
  public static BigDecimal factorial(int n) {
    return factorial(2, n);
  }
  
  /**
   * n! = (1*2*---* n/2)*(n/2+1 * --- * n)
   * 
   * @param n
   * @return
   */
  public static BigDecimal factorial2(int n) {
    BigDecimal r1 = factorial(2, n/2);
    BigDecimal r2 = factorial(n/2+1, n);
    return r1.multiply(r2);
  }

  public static BigDecimal factorial4(int n) {
    BigDecimal r1 = factorial(2, n/4);
    BigDecimal r2 = factorial(n/4+1, n/2);
    BigDecimal r3 = factorial(n/2+1, n - n/4);
    BigDecimal r4 = factorial(n- n/4 + 1, n);
    return (r4.multiply(r3)).multiply(r2.multiply(r1));
  }
  
  private static BigDecimal factorial(int begin, int end){
    BigDecimal result = new BigDecimal(1);
    BigDecimal a;
    for (int i = begin; i <= end; i++) {
      a = new BigDecimal(i);
      result = result.multiply(a);
    }
    return result;
  }

  /**
   * recursive, F(n) = F(n-1) * n
   * 
   * @param n
   * @return
   */

  public static BigDecimal factorial_R(int n)  {
    
    try{
      BigDecimal nBD = new BigDecimal(n);

      BigDecimal bd1 = new BigDecimal(1);
      if (nBD.equals(bd1)) {
        return bd1;
      }
      else
        return nBD.multiply(factorial_R(--n));      
    }catch (Exception e){
      e.printStackTrace();
    }

    return null;
    
  }


  /**
   * return n!,  store the big number in a int array
   * 
   * input n, to get n! 
   * step 1, get the bit length  of n!, [lg(n!) + 1]
   * step 2, create a int array store the n!, 
   * 
   * e.g.  120 ( 5! ),  it's {0, 2, 1} in the int array. 
   *   multiple 6 to get 6!, (0*6, 2*6, 1*6) => (0, 12, 6) => (0, 2, 7)       
   * 
   * @param n
   * @return
   */
  public static StringBuffer factorial_Array(int n) {
    // get the length
    // assert getBitLength(n) != len : "not match of n:" + n +" and len"+len;

    int len = getBitLength(n);
    int[] arrValue = new int[len];
    arrValue[0] = 1; // the first set as 1, the other is 0 as default

    int index = 0;
    double bitCount = 1;
    for (index = 2; index <= n; index++) {
      int multiValue = 0;
      bitCount += java.lang.Math.log10((double) index);
      for (int j = 0; j < (int) bitCount; ++j) {
        multiValue += (index * arrValue[j]);
        arrValue[j] = (multiValue % 10);
        multiValue /= 10;
      }
    }

    StringBuffer sb = new StringBuffer();

    for (int k = arrValue.length - 1; k >= 0; k--) {
      sb.append(arrValue[k]);
    }

    return sb;

  }

  /**
   * return the digits of n!
   * example n = 5, 
   * bitLength = 1 + lg1 + lg2 + lg3 + lg4 + lg5.
   * because, suppose bitLengh as x, 10^x > n! => x = lg(n!) + 1, 
   * lg(n!) = lg (1 * 2 * 3 * 4 * 5) = lg1 + lg2 + lg3 + lg4 + lg5 
   * 
   * @param n
   * @return
   */
  private static int getBitLength(int n) {
    double sum = 1.0;
    for (int i = 1; i <= n; i++)
      sum += java.lang.Math.log10((double) i);
    return (int) sum;
  }


  
  /**
   * @param args
   */
  public static void main(String[] args) {

    //System.out.println("test lg120 = " + Math.log10(120));
    //if(true) return;
    
    int[] arr = {1, 2, 3, 4, 12, 13, 20, 25, 100, 1000, }; //10000, 50000
    //time = System.currentTimeMillis();
    TimeCost tc = TimeCost.getInstance();
    tc.init();
    
    for (int i = 0; i < arr.length; i++) {
      
      
      System.out.println("The factorial       of " + arr[i] + " is: "
          + factorial(arr[i]).toString() + " timeCost:"+ tc.getTimeCost());
      System.out.println("The factorial       of " + arr[i] + " is: "
          + factorial2(arr[i]).toString() + " timeCost:"+ tc.getTimeCost());   
      
      System.out.println("The factorial_R     of " + arr[i] + " is: "
          + factorial_R(arr[i]).toString() + " timeCost:"+ tc.getTimeCost());
      
      
//      System.out.println("The factorial_Array of " + arr[i] + " is: "
//          + factorial_Array(arr[i]).toString() + " timeCost:"+ getTimeCost());
      
       // check the performance
      factorial(arr[i]).toString();
      System.out.println("The factorial       of " + arr[i] + " timeCost:"+ tc.getTimeCost());   
      
      factorial2(arr[i]).toString();
      System.out.println("The factorial       of " + arr[i] + " timeCost:"+ tc.getTimeCost());       

      factorial4(arr[i]).toString();
      System.out.println("The factorial       of " + arr[i] + " timeCost:"+ tc.getTimeCost()); 
      
      factorial_Array(arr[i]).toString();
      System.out.println("The factorial_Array of " + arr[i] + " timeCost:"+ tc.getTimeCost());
      
    }

  }

}
