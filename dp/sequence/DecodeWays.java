package fgafa.dp.sequence;

import java.util.Hashtable;

/**
 * 
 * A message containing letters from A-Z is being encoded to numbers using the following mapping:
 * 
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 * Given an encoded message containing digits, determine the total number of ways to decode it.
 * 
 * For example,
 * Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).
 * 
 * The number of ways decoding "12" is 2.
 * 
 *
 */
public class DecodeWays
{

  /**
   * Suppose there is a string that includes n digits. Define c[n] as the number of ways decoding s.charAt[1 -- n]  
   * init c[0] = 1;
   * 
   * c[i] = 0;
   * if s.charAt[i] is valid, c[i] += c[i - 1];
   * if (s.charAt[i-1], s.charAt[i]) is valid, c[i] += c[i-2];  
   *  
   * From right to left
   */
  public int numDecodings_recur(String s) {
    int result = 0;
    
    //check
    if(s == null || s.length() == 0 )
      return result;
        
    int[] returnValue = new int[1]; 
    returnValue[0] = 0;
    numDecodings_recur(s, 0, s.length(), returnValue);
    
    return returnValue[0];
  }
  

  private void numDecodings_recur(String s, int index, int len, int[] returnValue) {
    
    //System.out.println(s+ "\t"+ index +"\t"+len+"\t"+returnValue[0]);
    
    //check
    if(index == len){
      returnValue[0] ++;
      return;
    }
    
    char firstDigit = s.charAt(index);
    //if (firstDigit > 48 && firstDigit < 58) { // '0' is 48, '1' is 49,'9' is 57
    if (firstDigit > '0' && firstDigit < 58) {
      numDecodings_recur(s, index + 1, len, returnValue);

      if (index + 2 <= len) {
        int first2Digits = Integer.valueOf(s.substring(index, index + 2));

        if (first2Digits > 0 && first2Digits < 27) { // A is 1, Z is 26
          numDecodings_recur(s, index + 2, len, returnValue);
        }
      }
    }
  }
  
  
  /* same as the numDecodings_recur(String s) , from right to left*/
  public  int numDecodings_dp(String s) {
    
    //check
    if(s == null || s.length() == 0 )
      return 0;
    
    //check if there is bad character (not in [0, 9]) 
    //check if there is break point ( such as 000)
    
    int n = s.length();
    
    int[] c = new int[n+1];
    c[0] = 1;
    
    for(int i = 1; i <= n; i++ ) {

      if ( s.charAt(i-1) > '0' && s.charAt(i-1) <= '9')
        c[i] += c[i-1];
      
		if (i >= 2
				&& ((s.charAt(i - 2) == '1' && s.charAt(i - 1) >= '0' && s.charAt(i - 1) <= '9') 
						|| (s.charAt(i - 2) == '2' && s.charAt(i - 1) >= '0' && s.charAt(i - 1) <= '6')))
			c[i] += c[i - 2];
      
    }
  
    return c[n];
  }
  
  
  
  /**
   * 
   * Example:  
   * P(s) = 1   when s is valid ( It's 2 digits, the first digit is in 1--9, these 2 digits is in 1--26 )
   *        0   when s is not valid
   * f("1") = fib(1)
   * f("12") = fib(2)
   * f("123") = fib(3) 
   * f("111222") = fib(6)
   * 
   * f("10034") = 0;  //when the first digit and firstD+secondD digit are not valid, see "00"
   * f("12034") = f("1")*f("34")     // when the first digit is not valid, the firstD+secondD digit is valid, see '0'
   * f("12834") = f("12")*f("834")=f("12")*f("34")     //when the first digit is valid, the firstD*10+secondD is not valid. see '8' 
   * f("13234") = f("13")*f("234")    //when the first digit is valid, the firstD*10+secondD is not valid. see '3'
   * 
   * @param s
   * @return
   */
  public int numDecodings_X(String s) {
    //System.out.println("="+s);
    
    int result = 0;
    
    //check
    if(s == null || s.length() == 0 )
      return result;
    
    int len = s.length();
    char[] str = s.toCharArray();
    
    //check the first digit
    if(!isValid(str[0]))
      return 0;
    
    //just one digit
    if(len == 1){
        return 1;
    }
           
    boolean is1DigitValid = false, is2DigitsValid = false;
    int index=0;
    Hashtable<Integer, Integer> fibCache = new Hashtable<Integer, Integer>();
    //while(index <len - 2){
      
      while( index <len - 1 &&  (is1DigitValid = isValid(str[index])) && (is2DigitsValid = isValid(str[index], str[index+1])) )
        index ++;
      
      if(!is1DigitValid && !is2DigitsValid){
        /*f("10034") = 0;  //when the first digit and firstD+secondD digit are not valid.*/
        return 0;        
      }
      else if(!is1DigitValid){
        /*f("12034") = f("1")*f("34")     // when the first digit is not valid, the firstD+secondD digit is valid*/
        result =  fib(index - 1, fibCache);
        
        //if(index + 1 < len - 1)
        //System.out.println("==" + index +"==" + s.substring(index + 1) );
        result *= numDecodings_X(s.substring(index + 1));
          
      }else if(!is2DigitsValid){
        if(!isValid(str[index+1]))
          return 0;
        
        /* *
         * f("12834") = f("12")*f("34")    //when the first digit is valid, the firstD*10+secondD is not valid.  
         * f("13234") = f("13")*f("234")    //when the first digit is valid, the firstD*10+secondD is not valid. 
         * */
        result =  fib(index+1, fibCache);
        
        if(index + 1 < len - 1)
          result *= numDecodings_X(s.substring(index + 1));
          
      }else{ // all digits except the last one are valid
        
        if(index == len -1 && isValid(str[index]))
          result = fib(len, fibCache);
        else 
          result = fib(index-1, fibCache);
        
      }
        
    //}
    
    return result;
  }
  
  
  private boolean isValid(char firstDigit){
    boolean result = false;
    
    if (firstDigit > 48 && firstDigit < 58) // '0' is 48, '1' is 49,'9' is 57
      result = true;
    
    return result;
  }
  
  private boolean isValid(char firstDigit, char secondDigit){
    boolean result = false;

    /*A is 1, Z is 26 ; '0' is 48, '1' is 49,'9' is 57 ;  the first digit have to be 1-9, the second digit have to be 0-*/
    if (( firstDigit == 49 && secondDigit >= 48 && secondDigit < 58 ) || ( firstDigit == 50 && secondDigit >= 48 && secondDigit < 55 ) )  
      result = true;
      
    return result;
  }
  
  private int fib(int num, Hashtable<Integer, Integer> fibCache){
    
    if(!fibCache.containsKey(num)){
      fibCache.put(num, fib_matrix_X(num));
    }
    
    return fibCache.get(num);
  }
  
  /* copy from math.Fibonacci */
  private int fib_matrix_X(int num)  {
    
    int r1 =0;    
    int r2 =1;    
    
    int a1 = 1; 
    int a2 = 0; 
    
    int tmp1, tmp2;
    num = num + 1;   //this is very important
    for (; num > 0; num >>= 1 ) {
      //F(2k+1) = F(k) + F(k-1) = r1 + r2
      if ((num & 1) == 1) {
        tmp1 = a1 * (r1 + r2) + a2 * r1;
        tmp2 = r1 * a1 + r2 * a2;
        
        r1 = tmp1;
        r2 = tmp2;
      }
     
      tmp1 = a1 * a1 + 2 * a1 * a2;     
      tmp2 = a1 * a1 + a2 * a2;       

      a1 = tmp1;
      a2 = tmp2;
      
    }

    return r1;
  }
  
  
  public static void main(String[] args) {

    DecodeWays sv = new DecodeWays();
    
    char[] c = {'2','8'};
    System.out.println("==="+ sv.isValid(c[0]));
    System.out.println("==="+ sv.isValid(c[0], c[1]));
    
    String s = "012345678";
    System.out.println(s.substring(3));
    
    
    String[] str = {"", "-1", "ab", "01"
        , "1", "10", "11", "21", "28", "1211", "111221"
        , "110", "230", "301", "1090", "10034", "12034", "1200", "1203", "12834", "834", "1280", "12801"
        ,"6065812287883668764831544958683283296479682877898293612168136334983851946827579555449329483852397155"
        ,"4757562545844617494555774581341211511296816786586787755257741178599337186486723247528324612117156948"
        ,"1159314227869675749153973158896359637455398771636981264557866779635662185364345272665484523344457179"};
    int[] n = {0, 0, 0, 0
        , 1, 1, 2, 2, 1, 5, 13
        , 1, 0, 0, 0, 0, 1, 0, 1, 2, 1, 0, 0
        , 0, 589824, 6912};
    
    for(int i=0; i<str.length; i++){
      System.out.println("\nDecodeWayss: " + str[i]);
      
      System.out.println("Result:\t" + sv.numDecodings_recur(str[i]) );
      System.out.println("Result:\t" + sv.numDecodings_X(str[i]) );
      
      
      System.out.println("Expect:\t" + n[i] );
      
    }

  }

}
