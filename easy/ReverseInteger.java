package fgafa.easy;

/**
 * 
 * Reverse digits of an integer.
 * 
 * Need confirm the following cases.
 * Case1: x = 123, return 321
 * Case2: x = -123, return -321
 * Case3: x = 120, return 21
 * Case4: overflow, example x= Integer.MIN_VALUE, return Integer.MAX_VALUE 
 *
 */
public class ReverseInteger
{

    public int reverse(int x) {
        int input = x;
        if (x < 0){
            input = 0 - input;
        }

        // result = Long.valueOf((new
        // StringBuilder().append(result)).reverse().toString());

        long output = 0;
        while (input > 0) {
            output = output * 10 + input % 10;
            input = input / 10;
        }

        if (x < 0) {
            output = 0 - output;
        }

        if (output > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else if (output < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        } else {
            return (int) output;
        }

    }
  
  
  public int reverse_n(int x) {
	  boolean isNegative = (x < 0);
	  long input = x;
	  input = isNegative ? 0 - input : input;
      
//      long output = 0;
//      while(x>0){
//    	  output = output * 10 + x % 10;
//    	  x /= 10;
//      }
      long output = Long.valueOf((new StringBuilder().append(input)).reverse().toString());
      
      output = isNegative ? 0 - output : output; 
      output = Math.min(output, Integer.MAX_VALUE);
      output = Math.max(output, Integer.MIN_VALUE);
      
      return (int)output;
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
	ReverseInteger sv = new ReverseInteger();  
	  
    int[] input = {0, Integer.MAX_VALUE, Integer.MIN_VALUE, 123, -123, 100, -2147483648};

    for(int x : input){
    	System.out.println("\ninput: " + x + " => " + sv.reverse_n(x));
    }
    
  }

}
