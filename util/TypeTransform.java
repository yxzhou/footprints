package fgafa.util;

import java.math.BigDecimal;
import java.util.Stack;

public class TypeTransform
{


  
  /**
   *
   * @return 
   */
  public static String decimal2BinaryStr(double decimal) {

    String result = decimal2BinaryStr_Inte(decimal);
    result += decimal2BinaryStr_Deci(decimal);
    return result;
  }

  public static String byteToHex(byte b) {
    int i = b & 0xFF;
    return Integer.toHexString(i);
  }
  
  //convert Integer to Hex      //Integer.toHexString(int i) 
  //convert Integer to Octal    //Integer.toOctalString(int i) 
  //convert Integer to Binary   //Integer.toBinaryString(int i) 
  //convert Hex to Integer      //Integer.valueOf("FFFF",16).toString() 
  //convert Octal to Integer    //Integer.valueOf("876",8).toString() 
  //convert Binary to Integer   //Integer.valueOf("0101",2).toString() 

  /**
   * convert the integer part to binary string 
   * @param decimal 
   * @return 
   */
  private static String decimal2BinaryStr_Inte(double decimal) {
    // return Integer.toBinaryString((int)d);
    // the following is the detail logical of the above
    String result = "";
    long inte = (long) decimal;
    int index = 0;
    while (true) {
      result += inte % 2;
      inte = inte / 2;
      index++;

      //add a space to every 4 
      if (index % 4 == 0) {
        result += " ";
      }

      //append enough 0 in the end 
      if (inte == 0) {
        while (index % 4 != 0) {
          result += "0";
          index++;
        }
        break;
      }
    }

    //reverse c to cc, example '0010' to '0100'
    char[] c = result.toCharArray();
    char[] cc = new char[c.length];
    for (int i = c.length; i > 0; i--) {
      cc[cc.length - i] = c[i - 1];
    }
    
    return new String(cc);
  }



  /**
   * convert the decimal fraction part to binary string
   * @param ddecimal
   * @return 
   */
  private static String decimal2BinaryStr_Deci(double ddecimal) {
    return decimal2BinaryStr_Deci(ddecimal, 0);
  }



  /**
   * convert the decimal fraction part to binary string
   * @param decimal 
   * @param scale 
   * @return 
   */
  private static String decimal2BinaryStr_Deci(double decimal, int scale) {
    //get the decimal fraction with a subtraction
    double deci = sub(decimal, (long) decimal);
    if (deci == 0) {
      return "";
    }

    //to avoid dead loop, set the default scale as 0, 
    //example to 0.51, the scale = (4-2)*4 = 8, it means we use 4 binary digit to every decimal digit. 
    if (scale == 0) {
      scale = (String.valueOf(deci).length() - 2) * 4;
    }
    
    int index = 0;
    StringBuilder inteStr = new StringBuilder();
    double tempD = 0.d;

    //add a dot at the beginning
    inteStr.append(".");
    
    while (true) {
      //append enough 0 in the end
      if (deci == 0 || index == scale) {
        while (index % 4 != 0) {
          inteStr.append("0");
          index++;
        }
        break;
      }

      tempD = deci * 2;
      inteStr.append((int) tempD);
      deci = sub(tempD, (int) tempD);
      index++;
      
      //add a space to every 4 
      if (index % 4 == 0) {
        inteStr.append(" ");
      }
    }
    
    
    
    return inteStr.toString();
  }
  
  public static int convertBinaryToDecimal(char[] cars) {
    int result = 0;
    int num = 0;
    for (int i = cars.length - 1; 0 <= i; i--) {
      int temp = 2;
      if (num == 0) {
        temp = 1;
      } else if (num == 1) {
        temp = 2;
      } else {
        for (int j = 1; j < num; j++) {
          temp = temp * 2;
        }
      }
      
      int sum = Integer.parseInt(String.valueOf(cars[i]));
      result = result + (sum * temp);
      num++;
    }
   
    return result;
  }
  
  
  /**
   * subtraction with BigDecimal 
   *
   * @return the result of 
   */
  private static double sub(double minuend, double subtrahend) {
    BigDecimal b1 = new BigDecimal(Double.toString(minuend));
    BigDecimal b2 = new BigDecimal(Double.toString(subtrahend));
    return b1.subtract(b2).doubleValue();
  }
  
  
  private static final int ASCII_VALUE_OF_ZERO = 48; // '0' is 48, '1' is 49,
  private static final int ASCII_VALUE_OF_NINE = 57; // '9' is 57
  
  
  public static int char2Int(char c){
    return (int)c - ASCII_VALUE_OF_ZERO;
  }
  
  public static char int2Char(int digit){
    return   (char) (ASCII_VALUE_OF_ZERO + digit);
  }  
  
  private boolean isValidDigit(char digit){   
    return digit >= ASCII_VALUE_OF_ZERO && digit <= ASCII_VALUE_OF_NINE ; // '0' is 48, '1' is 49,'9' is 57
  }
  
  /*
   * convert a ASCII String to int number.
   *
   * cases:
   * if the string is null or empty or with specail character, such as "12a3", throws IllegalArgumentException
   * if the string is "+" or "-", throws IllegalArgumentException.
   * if the string is "+0" or "-0", return 0
   * if the string is "123" or "+123", return 123; if the string is "-123", return -123.
   * if the string is "0012", return 12
   * if the string-integer is overflow, return Integer.MAX_VALUE or Integer.MIN_VALUE
   *
   * ?? if it's "10e2"
   *
   */
  public static int atoi(String number) {
      if (null == number) {
          throw new IllegalArgumentException("can't convert to a Integer.");
      }

      //trim the number
      number = number.trim();
      if (0 == number.length() || "+".equals(number) || "-".equals(number)) {
          throw new IllegalArgumentException("can't convert to a Integer.");
      }

      long result = 0;
      int i = 0;

      //check for sign as the first character,
      boolean negated = false;
      char sign = number.charAt(0);
      if ('+' == sign){
          i++;
      }else if('-' == sign){
          i++;
          negated = true;
      }

      char c;
      for ( ; i < number.length(); i++) {
          c = number.charAt(i);

          if (c >= '0' && c <= '9'){
              result = result * 10 + (c - '0');
          }else{
              throw new IllegalArgumentException("The String contains characters other than digit:" + c);
          }

      }

      if (negated) {
          result = 0 - result;
      }

      result = Math.min(result, Integer.MAX_VALUE);
      result = Math.max(result, Integer.MIN_VALUE);

      return (int) result;
  }

  
  /*
   * convert a int number to a ASCII character String 
   * if it's '-123', {'-','1','2','3'}
   * 
   */
  public static StringBuilder itoa(int number) {

      long n = number;
      boolean negated = false;
      if (n < 0) {
          negated = true;
          n = 0 - n;
      }

      //init
      Stack stack = new Stack();
      int digit;
      while (n > 0) {
          digit = (int)(n % 10);
          n = n / 10;

          //stack.push((char) (ASCII_VALUE_OF_ZERO + digit));
          stack.push(digit);
      }

      StringBuilder result = new StringBuilder();
      if (negated) {
          result.append('-');
      }

      while (!stack.isEmpty()) {
          result.append(stack.pop());
      }

      return result;
  }
  
  public static void main(String[] args) {

    String str = "-683";
    int number = -683;
    //int x = instance.atoi(input);

    String[] input = {
    		//null,
    		//"",
    		//" ",
    		"012",
    		" 012 ",
    		"-0120",
    		"+0120",
    		"9999999999999999",
    		//"99999999a99999999",
    		"-999999999999999",
    		"999999999999999999999",
    		"9999999999999999999999999999999",
    		//"99999999a9999999999999",
    		"-99999999999999999999",
    		"-99999999999999999999999999999999",
    		"-00000999999999999999",
    };
    for(String str1 : input){
        int n = atoi(str1);

        System.out.println(String.format("%s -> %d -> %s", str1, n, itoa(n)));
    }

    //System.out.println(number + " --atoi-- " + itoa(number));

    //System.out.println(number + " --atoi from SDK-- " + String.valueOf(number));

    double decimal = 3.72d;  //??  -2.75

    System.out.println(decimal + " --convert to binary-- " + decimal2BinaryStr(decimal));
    
  }

}
