package fgafa.dp.sequence;


/**
 * Leetcode #91
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
  public int decodeWays_recur(String s) {
    int result = 0;
    
    //check
    if(s == null || s.length() == 0 )
      return result;
        
    int[] returnValue = new int[1]; 
    returnValue[0] = 0;
    decodeWays_recur(s, 0, s.length(), returnValue);
    
    return returnValue[0];
  }
  

  private void decodeWays_recur(String s, int index, int len, int[] returnValue) {
    
    //System.out.println(s+ "\t"+ index +"\t"+len+"\t"+returnValue[0]);
    
    //check
    if(index == len){
      returnValue[0] ++;
      return;
    }
    
    char firstDigit = s.charAt(index);
    //if (firstDigit > 48 && firstDigit < 58) { // '0' is 48, '1' is 49,'9' is 57
    if (firstDigit > '0' && firstDigit < 58) {
      decodeWays_recur(s, index + 1, len, returnValue);

      if (index + 2 <= len) {
        int first2Digits = Integer.valueOf(s.substring(index, index + 2));

        if (first2Digits > 0 && first2Digits < 27) { // A is 1, Z is 26
          decodeWays_recur(s, index + 2, len, returnValue);
        }
      }
    }
  }
  
  
  /* same as the numDecodings_recur(String s) , from right to left*/
  public int decodeWays_dp(String s) {
    
    //check
    if(s == null || s.length() == 0 )
      return 0;
    
    //check if there is bad character (not in [0, 9]) 
    //check if there is break point ( such as 000)
    
    int n = s.length();
    
    int[] dp = new int[n+1];
    dp[0] = 1;
    dp[1] = 1;

    for(int i = 2; i <= n; i++ ) {
      if ( s.charAt(i-1) > '0' && s.charAt(i-1) <= '9') {
          dp[i] += dp[i - 1];
      }
      
		if ((s.charAt(i - 2) == '1' && s.charAt(i - 1) >= '0' && s.charAt(i - 1) <= '9')
						|| (s.charAt(i - 2) == '2' && s.charAt(i - 1) >= '0' && s.charAt(i - 1) <= '6')) {
            dp[i] += dp[i - 2];
        }
      
    }
  
    return dp[n];
  }
  
  public int numDecodings_n(String s){
      if(null == s  || 0 == s.length()){
          return 0;
      }

      final int ZERO = '0';
      
      int f1 = 1; // f[i - 1], the decode way by i - 1
      int f2 = 1; // f[i], the decode way by i

      int p = s.charAt(0) - ZERO;
      int c;

      if(!isValid(p)){
          return 0;
      }

      for(int k = 1; k < s.length(); k++){
          c = s.charAt(k) - ZERO;

          if(!isValid(c) && !isValid(p, c)){
              return 0;
          }
          
          int tmp = 0;
          if(isValid(c)){
              tmp += f2;
          }
          
          if(isValid(p, c)){
             tmp += f1;
          }
          
          f1 = f2;
          f2 = tmp;

          p = c;
      }
      
      return f2;
  }
  
  private boolean isValid(int digit){
      return digit >= 1 && digit <= 9;
  }
  
  private boolean isValid(int digit1, int digit2){
      return (digit1 == 1  && (digit2 >= 0 && digit2 <= 9)) || (digit1 == 2 && (digit2 >= 0 && digit2 <= 6));
  }

    /**
     *  case1,  "0"
     *  case2,  "100"
     *
     *
     */

    public int numDecodings(String s) {
        if(s == null || s.length() == 0 || s.charAt(0) == '0'){
            return 0;
        }

        final int zero = '0';

        int one = 1;
        int two = 0;
        int tmp;

        int pre= s.charAt(0) - zero;
        int curr;
        for(int i = 1, end = s.length(); i < end; i++){
            curr = s.charAt(i) - zero;

            if(curr == 0 && (pre == 0 || pre > 2)){
                return 0;
            }

            pre = pre * 10 + curr;

            if(pre > 26){
                one += two;
                two = 0;
            }else if(curr == 0){ // pre == 10 or 20
                two = one;
                one = 0;
            }else if(pre > 10 && pre < 27){
                tmp = two;
                two = one;
                one += tmp;
            }else{ //pre < 10
                one = two;
                two = 0;
            }

            pre = curr;
        }

        return one + two;
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
      
      System.out.println("Result:\t" + sv.decodeWays_recur(str[i]) );
      System.out.println("Result:\t" + sv.decodeWays_dp(str[i]) );
      System.out.println("Result:\t" + sv.numDecodings_n(str[i]) );
      
      
      System.out.println("Expect:\t" + n[i] );
      
    }

  }

}
