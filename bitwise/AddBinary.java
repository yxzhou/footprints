package bitwise;

/**
 * Problem #1
 * Given two binary strings, return their sum (also a binary string).
 * 
 * For example,
 * a = "11"
 * b = "1"
 * Return "100".
 *
 * Constraints:
 * 1 <= a.length, b.length <= 104
 * a and b consist only of '0' or '1' characters.
 * Each string does not contain leading zeros except for the zero itself.
 *
 *
 * Solution #1
 *
 *
 * Problem #2
 * Give k, a, b, which means a and b are a k-ary number, and outputs the k-ary number of a + b.
 *
 * Example1
 * Input: k = 3, a = "12", b = "1"
 * Output: 20
 * Explanation:
 * 12 + 1 = 20 in 3 bases.
 *
 * Example2
 * Input: k = 10, a = "12", b = "1"
 * Output: 13
 * Explanation:
 * 12 + 1 = 13 in 10 bases.
 *
 * Constrains:
 * 2 <= k <= 10
 * a, b are strings, the length does not exceed 1000.
 * There may be a leading zero.
 *
 */

public class AddBinary
{
  
  public String addBinary(String a, String b) {
	    //check	  
	    if((a == null || a.length() == 0) && (b == null || b.length() == 0))
	      return "0";
	    else if(a == null || a.length() == 0 )
	      return b;
	    else if(b == null || b.length() == 0 )
	      return a;
	  
	    int aIndexEnd = a.length()-1;
	    int bIndexEnd = b.length()-1;
	    
	    //main
	    int result = 0;
	    int carry = 0;     
	    StringBuilder sb = new StringBuilder();
	    
		while (aIndexEnd >= 0 && bIndexEnd >= 0) {
			result = carry + a.charAt(aIndexEnd--) + b.charAt(bIndexEnd--) - 96;
			carry = 0;
			if (result > 1) {
				carry = 1;
				result -= 2;
			} 

			sb.append(result);
		}

		String rest = aIndexEnd >= 0 ? a : b;
		int restIndexEnd = aIndexEnd >= 0 ? aIndexEnd : bIndexEnd;
		while (restIndexEnd >= 0) {
			result = carry + rest.charAt(restIndexEnd--) - 48;
			carry = 0;
			if (result > 1) {
				carry = 1;
				result -= 2;
			} 

			sb.append(result);
		}
	    
	    if(carry != 0)
	      sb.append(carry);
	    
	    return sb.reverse().toString();
	  }
  
  public String addBinary_x(String a, String b) {
      //check   
      if(null == a || 0 == a.length() || null == b || 0 == b.length()){
          return "";
      }
      
      StringBuilder result = new StringBuilder();
      int carry = 0;
      int tmp = 0;

      for(int aIndex = a.length()-1, bIndex = b.length()-1; bIndex >= 0 || aIndex >= 0; ){
          tmp = carry;
          
          if(aIndex >= 0){
              tmp += a.charAt(aIndex--) - 48; // '0' is 48
          }
          if(bIndex >= 0){
              tmp += b.charAt(bIndex--) - 48;
          }

          carry = tmp / 2;
          tmp = tmp & 1;  //tmp % 2
          
          result.append(tmp);
      }
      
      if(carry > 0){
          result.append(carry);
      }
      
      return result.reverse().toString();
  }


    public String addBinary_x2(String a, String b) {
        //check
        if(null == a || 0 == a.length() || null == b || 0 == b.length()){
            return "";
        }

        StringBuilder result = new StringBuilder();
        int c = 0; //carry
        int va = 0; //value
        int vb = 0;
        for(int aIndex = a.length()-1, bIndex = b.length()-1; bIndex >= 0 || aIndex >= 0; ){
            if(aIndex >= 0){
                va = a.charAt(aIndex--) - 48; // '0' is 48
            }else{
                va = 0;
            }

            if(bIndex >= 0){
                vb = b.charAt(bIndex--) - 48;
            }else {
                vb = 0;
            }

            result.append(va ^ vb ^ c);
            c = (va & vb) | (va & c) | (vb & c);
        }

        if(c > 0){
            result.append(c);
        }

        return result.reverse().toString();
    }

    public String addBinary_xx(String a, String b) {
        if(a == null || b == null){
            return "";
        }

        StringBuilder sb = new StringBuilder();

        boolean carry = false;
        boolean ia;
        boolean ib;

        int i = a.length() - 1;
        int j = b.length() - 1;
        while( i >= 0 && j >= 0 ){
            ia = isOne(a, i--);
            ib = isOne(b, j--);

            sb.append(carry ^ ia ^ ib ? '1' : '0');

            carry = carry ? ia|ib : ia&ib;
        }

        while( i >= 0 ){
            ia = isOne(a, i--);

            sb.append(carry ^ ia ? '1' : '0');
            carry = carry & ia;
        }

        while( j >= 0 ){
            ib = isOne(b, j--);

            sb.append(carry ^ ib ? '1' : '0');
            carry = carry & ib;
        }

        if(carry){
            sb.append('1');
        }

        return sb.reverse().toString();
    }

    public String addBinary_xx2(String a, String b) {
        if(a == null || b == null){
            return "";
        }

        StringBuilder sb = new StringBuilder();

        boolean carry = false;
        boolean ia;
        boolean ib;

        for( int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0;){
            ia = isOne(a, i--);
            ib = isOne(b, j--);

            sb.append( (carry ^ ia ^ ib) ? '1' : '0');
            carry = carry ? ia|ib : ia&ib;
        }

        if(carry){
            sb.append('1');
        }

        return sb.reverse().toString();
    }

    private boolean isOne(String s, int i){
        if(i >= 0 && i < s.length() ){
            return s.charAt(i) == '1';
        }

        return false;
    }


  /**
   * @param a a number
   * @param b a number
   * @return the result
   */
  public String addBinary_n(String a, String b) {
      if (a == null || b == null || a.length() == 0 || b.length() == 0) {
          return null;
      }
      int decimalA = Integer.parseInt(a, 2);
      int decimalB = Integer.parseInt(b, 2);
      
      int sum = decimalA + decimalB;
      
      return Integer.toBinaryString(sum);
  }

  /*
   * param a: The first integer
   * param b: The second integer
   * return: The sum of a and b
   */
  public static String addString(String x, String y) {
      int a = Integer.parseInt(x, 2);
      int b = Integer.parseInt(y, 2);

      int carry;
      while (b != 0) {
          carry = a & b;
          a = a ^ b;
          b = carry << 1;
      }
      return Integer.toBinaryString(a);
  }

    public String addition(int k, String a, String b) {
        StringBuilder sb = new StringBuilder();

        int sum = 0;
        for(int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0; ){
            sum += digitAt(a, i--) + digitAt(b, j--);

            sb.append(sum % k);
            sum /= k;
        }

        while(sum > 0){
            sb.append(sum % k);
            sum /= k;
        }

        //remove the leading zero
        for(int i = sb.length() - 1; i >= 0; i--){
            if(sb.charAt(i) != '0'){
                break;
            }

            sb.deleteCharAt(i);
        }

        return sb.reverse().toString();
    }

    private int digitAt(String s, int i){
        if(i >= 0 && i < s.length()){
            return s.charAt(i) - 48; // 48 is '0'
        }

        return 0;
    }

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    char a0 = '0';
    System.out.println("\nchar a is: "+ (a0 + 0));
	  
//    if(true) return;
    char[] arr = "0100a".toCharArray();
	System.out.println("\nString.toCharArray: " );
    
    AddBinary sv = new AddBinary();
    
    String[] a = {"0","11","111","000","010", "0100", "1111"};
    String[] b = {"0","1","1","1","11", "010", "111"};
    
    for(int i=0; i<a.length; i++){
      System.out.println("\nInput a: "+ a[i]);
      System.out.println("Input b: "+ b[i]);
      
      System.out.println("Output : "+ sv.addBinary(a[i], b[i]));
      System.out.println("Output : "+ sv.addBinary_x(a[i], b[i]));
      System.out.println("Output : "+ sv.addBinary_x2(a[i], b[i]));
      
      System.out.println("Output : "+ addString(a[i], b[i]));
    }

  }

}
