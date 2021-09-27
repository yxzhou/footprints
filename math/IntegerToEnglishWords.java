package math;

import java.util.Random;

/**
 * number to string 
 * Q1, small number,  input 0 - 999 
 *     Example to 919, it return nine hundred and nineteen. 
 *
 * Q2, input 0 - 999, 999
 *     Example to 99,919, it return ninety-nine thousand, nine hundred and nineteen.
 *  
 * Q3, input 0 - 2^31 - 1
 *     Example, 1234567 -> "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
 */

public class IntegerToEnglishWords {


    
    /*
     * number to string    
     * 
     * @ input 0 - 999, 999
     * @ return Example to 99,919, it return ninety-nine thousand, nine hundred and nineteen.
     */
    public String number2String_million(int num){
      StringBuffer sb = new StringBuffer();
      
      if(num == 0){
        sb.append("Zero");
        return sb.toString();      
      }

      
      int higherDigits = num / 1000;

      if(higherDigits != 0){
        sb.append(number2String_thousand(higherDigits));
        sb.append(" Thousand");
      }
        
      int lowerDigits = num - higherDigits * 1000;
      if(lowerDigits != 0){
        if(higherDigits != 0)
          sb.append(", ");
        
        sb.append(number2String_thousand(lowerDigits));
      }
      
      return sb.toString();
    }
    
    /*
     * small number to string 
     * 
     * @ input 0 - 999
     * @ return Example to 919, it return nine hundred and nineteen.  
     */  
    private static String[] word1 = {"Zero", "One", "Two", "Three", "Four", "Five",
        "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen",
        "Forteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static String[] word20 = {"", "", "Twenty", "Thirty", "Forty", "Fifty",
        "Sixty", "Seventy", "Eighty", "Ninety"};
    
    public String number2String_thousand(int num){
      StringBuffer sb = new StringBuffer();
      
      if(num == 0){
          return sb.toString();   
      }
      
      int hundredDigit = num / 100;
      if (hundredDigit != 0) {
        sb.append(word1[hundredDigit]);
        sb.append(" Hundred ");

        num = num - hundredDigit * 100;
      }

      if (num == 0)
        return sb.toString();
      
      if (hundredDigit != 0) 
        sb.append("and ");
      
      int tenDigit = num / 10;
      if (tenDigit == 0 || tenDigit == 1) {
        // the num is 1 - 9 or 11 - 19
        sb.append(word1[num]);

      }
      else {
        // the num is 20 - 99
        sb.append(word20[tenDigit]);
        
        int lowestDigit = num - tenDigit * 10;
        try {
            if(lowestDigit == 0)
              return sb.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        sb.append("-");
        sb.append(word1[lowestDigit]);
      }
      
      return sb.toString();    
    }
    
    
    public static void main(String[] args){
        IntegerToEnglishWords sv = new IntegerToEnglishWords();
        
    //
        Random random = new Random();
        int num = 0;
        for(int i=0; i < 50; i++){
          //num = random.nextInt(99);
          num = num + i * random.nextInt(9);
          
          System.out.println( num + ": \t" + sv.number2String_million(num) );
        }
        
    }
    
}
