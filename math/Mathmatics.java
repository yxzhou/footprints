package math;

import java.util.Queue;
import java.util.Stack;


/*
 *
 * 
 */

public class Mathmatics {
  
    /**
     * case1: 000=> 0 
     * case2: 0012=> 12 
     * case3: a21 => ""
     *
     * @param num
     * @return
     */
    public String valid(String num) {
        if (num == null || num.length() == 0) {
            return "";
        }

        StringBuffer result = new StringBuffer();
        int i = 0;
        int len = num.length();
        for (; i < len; i++) {
            if (num.charAt(i) != '0') {
                break;
            }
        }
        if (i == len) {
            return "0";
        }

        for (; i < len; i++) {
            if (isValidDigit(num.charAt(i))) {
                result.append(num.charAt(i));
            } else {
                return "";
            }
        }

        return result.toString();
    }

    private boolean isValidDigit(char digit) {
        boolean result = false;

        if (digit > 47 && digit < 58){ // '0' is 48, '1' is 49,'9' is 57
            result = true;
        }

        return result;
    }

    private int getDigit(char digit) {
        return digit - 48;
    }

  
    /**
     * check if b1 is small than b2
     * @param b1, binary string, example 101
     * @param b2, binary string, example 11
     * @return true when b1>=b2, false when b1<b2
     */
    private boolean compare(String b1, String b2) {
        boolean result = false;
        int len1 = b1.length();
        int len2 = b2.length();
        if (len1 < len2) {
            return true;
        } else if (len1 > len2) {
            return false;
        }

        for (int i = 0; i < len1; i++) {
            if (b1.charAt(i) < b2.charAt(i)) {
                return true;
            }
        }

        return result;
    }
  
 

  
    /* input n and m, both are positive, return the n/m   
     * 
     * Time O(n/m)
     */
    private int divideBySubstract(long n, long m) {
        int r = 0;

        while (m <= n) {
            r++;
            n = n - m;
        }

        return r;
    }

    /**
     * 
     * solution #1,  log2(x) = Math.log(x)/Math.log(2), log10 x = ln x / ln 10
     * solution #2,  
     * 
     */
    public static int log2_div(int num) {
        if (num < 0) {
            throw new IllegalArgumentException();
        }
        if (num == 0) {
            return 0;
        }

        //return (int)(java.lang.Math.log(num & 0xffffffffL) / java.lang.Math.log(2)); 
        return (int) (java.lang.Math.log(num & 0x7fffffffL) / java.lang.Math.log(2));
    }

    public static int log2_bit(int num) {
        if (num < 0) {
            throw new IllegalArgumentException();
        }

        int log = 0;
        if ((num & 0xffff0000) != 0) {
            num >>>= 16;
            log += 16;
        }
        if (num >= 256) {
            num >>>= 8;
            log += 8;
        }
        if (num >= 16) {
            num >>>= 4;
            log += 4;
        }
        if (num >= 4) {
            num >>>= 2;
            log += 2;
        }
        return log + (num >>> 1);
    }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
	System.out.println(" 100/9: \t" + (100/9));  //11
	System.out.println(" -100/9: \t" + (-100/9));  // -11
	  
    Mathmatics s = new Mathmatics();
    

  
    /* test log2 
    int[] n = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1000, 9999, 10000, 99999, 100000, 555555, Integer.MAX_VALUE, Integer.MIN_VALUE};
    for(int i =0; i< n.length; i++){
      //System.out.println( n[i] + " num & 0x7fffffffL " + (n[i] & 0x7fffffffL));
      //System.out.println( (0-n[i]) + " num & 0x7fffffffL " + ((0-n[i]) & 0x7fffffffL));
      //System.out.println( Integer.toBinaryString((0-n[i]) ));
      System.out.println(" log2("+n[i]+") is " + log2_bit(n[i]) + " " + log2_div(n[i]));
      //System.out.println(" log2("+(0-n[i])+") is " + log2_bit((0-n[i])) + " " + log2_div((0-n[i])));
    }
    */
  }
  
}
