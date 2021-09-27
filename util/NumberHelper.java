package util;

import java.text.DecimalFormat;

/**
 * <b>NumberHelper</b> class. <br />
 * <p>
 * </p>
 * 
 */
public class NumberHelper
{
  public static double formatDouble(double value, int length) {
    DecimalFormat fmt = new DecimalFormat("#0.00");
    fmt.setMinimumFractionDigits(length);
    fmt.setMaximumFractionDigits(length);
    return Double.parseDouble(fmt.format(value));
  }
  public static String formatDouble(double value,String format) {
        DecimalFormat fmt = new DecimalFormat(format);
        return fmt.format(value);
  }
  
  public static int getNextInt(int begin, int end){
    
    return begin + (int)(Math.random() * (end - begin + 1)) ;  // get a random in [begin, end-1] 
  }
}



