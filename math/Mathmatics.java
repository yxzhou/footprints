package math;

import java.util.Queue;
import java.util.Stack;


/*
 * 1) add
 * You have two numbers represented by a linked list, where each node contains a single digit, 
 * Write a function that adds the two numbers and returns the sum as a linked list.
 * 
 * Exampe:
 * input: (3->1->5) (6->5->9->2)
 * output: 6->9->0->7
 * 
 * careercup linkedlist 126
 * 
 * 
 * 2) multiple
 * 120*6
 *  input: (0->2->1) (6)
 * output: (0*6, 2*6, 1*6) => (0, 12, 6) => (0, 2, 7)    
 * 
 *  TODO
 *  120*16
 *  input: (0->2->1) (6, 1)
 * output: (0*6, 2*6, 1*6) => (0, 12, 6) => (0, 2, 7)   
 * 
 * 
 * 3) square root 
 *  Newton's Method,  http://en.wikipedia.org/wiki/Newton%27s_method#Square%5Froot%5Fof%5Fa%5Fnumber
 *  
 *  
 * 
 */

public class Mathmatics
{

  /*
   * There are many method of computing square roots,  here it's Newton's method
   *
   *  input, int n.
   * output, double x, x^2 is close to n.
   * 
   *  x^2 = n;
   *  f(x) = x^2 - n;  
   *  f'(x) = 2x;   
   * 
   *  f'(x0) = (f(x0) - 0)/(x0 - x1)  // 2 points of (x0, f(x0)) and (x1, 0)
   *
   *  => x1 = x0 - f(x0)/f'(x0)
   *  => x1 = x0 - (x0^2 - n) / (2x0)
   *  
   *  estimate x0 = n/4;  
   *  x1 = x0 - (x0^2 - n) / (2x0);
   *  x2 = x1 - (x1^2 - n) / (2x1);
   *  ---  
   * 
   *  e.g: 
   *  x^2 = 612
   *  estimate x0 = 10;
   *  x1 = x0 - f(x0)/f'(x0) = 10 -  (10*10 - 612)/(2*10)       = 35.6
   *  x2 = x1 - f(x1)/f'(x1) = 35.6- (35.6*35.6 - 612)/(2*35.6) = 26.395505617
   *  x3 = x2 - f(x2)/f'(x2) = ---                              = 24.790635492
   *  x4 = x3 - f(x3)/f'(x3) = ---                              = 24.738688294
   *  x5 = x4 - f(x4)/f'(x4) = ---                              = 24.738633753
   *  ---
   */
  public double sqrt_Newton(float n){
    //check 
    if(n<0) return Double.NaN;
    if(n == 0 || n == 1) return n;
    
    double x = java.lang.Math.max((double) n/4, 1);
    //final int TIMES = 15;
    
    //for(int i=0; i<TIMES; i++){
    double ret;
    while(true){
      x = x - (double)(x * x - n) / (double)(2 * x) ;
      
      ret = (double)x * x;
      if(java.lang.Math.abs(ret - n) < 0.00001)
        return x;
      
      //System.out.println( i + " --- " + x);
    }    
  }
  
//  private double max(double x, double y){
//    return x>y? x: y;
//  } 
/**
 * input	output
 * 0		0
 * -1		0
 * 4		2
 * 5		2
 * 
 * @param x
 * @return
 */
	public int sqrt_binarysearch(int x) {
		if (x < 2)
			return x;

		int low = 1;
		int high = x;
		while (low < high) {
			int mid = low + ((high - low) >> 1);
			long ret = (long) mid * mid;
			long ret1 = ret + (mid << 1) + 1; // (mi+1)*(mi+1)
			if (ret == x || (ret < x && x < ret1))
				return mid;

			if (ret < x)
				low = mid + 1;
			else
				high = mid - 1;
		}

		return low;
	}

    public int sqrt_binarysearch_n(int x) {
        //check
        if(x < 0){
            return -1;
        }
        
        int high = x;
        int low = 0;
        int middle;
        long squareMiddle;
        long squareMiddlePlus;
        while(low < high){
            middle = low + ((high - low) >> 1);
            squareMiddle = (long)middle * middle; //(long) is very important
            if(squareMiddle == x){
                return middle - 1;
            }else if(squareMiddle > x){
                high = middle - 1;
            }else{//squareMiddle < x
                squareMiddlePlus = squareMiddle + (middle << 1) + 1;
                if(squareMiddlePlus == x){
                    return middle + 1;
                }else if(squareMiddlePlus > x){
                    return middle;
                }else{//squareMiddlePlus < x
                    low = middle + 1;
                }
            }
        }
        
        return low;
    }
    
  public double sqrt_binarysearch(float n, final float e){
    //check
    if(n<0) return Double.NaN;
    if(n == 0 || n == 1) return n;
    
    double left = 0;
    double right = n;

    if(n < 1){
      left = n;
      right = 1;
    }
    
    double mid;
    double ret;
    while(left < right){
      mid = left + (double)(right-left)/2;
      ret = (double)mid * mid;
      
      if(java.lang.Math.abs(ret - n) < e)
        return mid;
      
      if(ret < n)
        left = mid;
      else
        right = mid;
      
      //System.out.println("left="+left+", right="+right+", mid="+mid);
    }
    
    return Double.NaN;
    
  }
  
  /*
   * iteral addition
   * 
   *  input: (3->1->5) (6->5->9->2)
   * output: (6->9->0->7)
   * 
   * 
   */
  public void add_stack(Stack<String> num1, Stack<String> num2, Stack<String> qResult){
    int result = 0;
    int carry = 0;
    int tmp = 0;
    
    while(!num1.isEmpty() || !num2.isEmpty()){
      tmp = 0;
      if(!num1.isEmpty())
        tmp += Integer.valueOf((String)num1.pop());
              
      if(!num2.isEmpty())
        tmp += Integer.valueOf((String)num2.pop());
      
      result = tmp + carry;
      carry = result / 10;
      result = result % 10; 
      //result = result - carry * 10;
      
      qResult.push(String.valueOf(result));
    }
    
    if(carry != 0)
      qResult.push(String.valueOf(carry));
  }
  
  public String add_string(String num1, String num2){
    
    //check
    num1 = valid(num1);
    num2 = valid(num2);
    //System.out.println("=="+num1 + " " + num2);
    if(num1.equals("0"))
      return num2;
    if(num2.equals("0"))
      return num1; 
    
    StringBuffer resultSB = new StringBuffer();
    int result = 0;
    int carry = 0;
    int tmp = 0;
    for(int i=num1.length()-1, j=num2.length()-1; i >=0  || j >= 0;  ){
      tmp = 0;
      if(i >=0)
        tmp += getDigit(num1.charAt(i--));
               
      if(j>=0)
        tmp += getDigit(num2.charAt(j--));
      
      result = tmp + carry;
      carry = result / 10;
      result = result % 10; 
      
      resultSB.append(result);
    }
    
    if(carry != 0)
      resultSB.append(carry);
      
    return resultSB.reverse().toString();
  }

    public String addStrings(String num1, String num2) {
        if(num1 == null){
            return num2;
        }
        if(num2 == null){
            return num1;
        }

        if(num1.length() > num2.length()){
            return addStrings(num2, num1);
        }

        StringBuilder result = new StringBuilder(num2.length() + 1);

        int i = num1.length() - 1;
        int j = num2.length() - 1;

        int carry = 0;
        int value = 0;
        int base = '0';
        int base2 = base + base;
        for( ; i >= 0 && j >= 0; i--, j--){
            value = num1.charAt(i) + num2.charAt(j) - base2 + carry;

            carry = value / 10;

            result.append( value % 10 );
        }

        for( ; j >= 0 ; j--){
            value = num2.charAt(j) - base + carry;

            carry = value / 10;

            result.append( value % 10 );
        }

        if(carry != 0){
            result.append( carry );
        }

        return result.reverse().toString();
    }

	/**
	 * 
	 * Given a non-negative number represented as an array of digits, plus one
	 * to the number.
	 * 
	 * The digits are stored such that the most significant digit is at the head
	 * of the list.
	 */
  public int[] plusOne(int[] digits) {
    //check
    if(digits == null || digits.length == 0)
      return new int[]{1};
    
    int carry = 1;
    //int result = 0;
    int len = digits.length;
       
    int[] result = new int[len];
    for(int i= len - 1; i>=0; i-- ){
      result[i] = digits[i] + carry;
      if(result[i] == 10){
        result[i] -= 10;
        carry = 1;
      }else
        carry = 0;
    }
    
    if(carry != 0){
      int[] resultN = new int[len+1];
      resultN[0] = carry;
      for(int i= len - 1; i>=0; i--)
        resultN[i+1] = result[i];
      return resultN;
    }else
      return result;
    
  }
  
	public int[] plusOne_x(int[] digits) {
		if (null == digits || 0 == digits.length)
			return new int[] { 1 };

		int[] ret = new int[digits.length];
		int carry = 1;
		for (int i = digits.length - 1; i >= 0; i--) {
			ret[i] = digits[i] + carry;
			if (10 == ret[i]) {
				ret[i] = 0;
				carry = 1;
			} else {
				carry = 0;
			}
		}

		if (0 != carry) {
			int[] newRet = new int[digits.length + 1];
			newRet[0] = carry;

			for (int i = 0; i < ret.length; i++) {
				newRet[i + 1] = ret[i];
			}

			return newRet;
		}

		return ret;
	}
  
    /**
     * @param digits a number represented as an array of digits
     * @return the result
     */
    public int[] plusOne_n(int[] digits) {
        // check
        if(null == digits || 0 == digits.length){
            return digits;
        }
        
        int[] newDigits = new int[digits.length];
        int carry = 1;
        int digit;
        for(int i = digits.length - 1; i >= 0; i-- ){
            digit = digits[i] + carry;
            newDigits[i] = digit % 10;
            carry = digit / 10;
        }
        

        if(carry == 0){
        	return newDigits;
        }
        
        int[] newDigits2 = new int[digits.length + 1];
        newDigits2[0] = carry;

        int j = 1;
        for(int x : newDigits){
        	newDigits2[j++] = x;
        }
        
        return newDigits2;
    }
	
  /*
   * iteral multiplication
   * 
   *  120*6
   *  input: (120) (6)
   * output: (1*6, 2*6, 0*6) => (6, 12, 0) => (7, 2, 0)    
   * 
   */
  private String multiple(String s1, int s2){
    //check
    if(s2==0)
      return "0";
      
    StringBuffer sb = new StringBuffer(); 
    int result = 0;
    int carry = 0;
    int tmp = s2;    
    for(int i=s1.length()-1; i>=0; i--){
      
      tmp = s2 * getDigit(s1.charAt(i));
        
      result = tmp + carry;
      carry = result / 10;
      result = result % 10; 
      //result = result - carry * 10;
      
      sb.append(result);
    }
    
    if(carry != 0){
      sb.append(carry);
    }
    
    return sb.reverse().toString();
  }
  
  /*
   * iteral multiplication
   * 
   *  120*16
   *  (120)*(16) => ((120)*(6)) + ((120)*(1))
   *     
   */
  public String multiply(String num1, String num2) {
    String result = new String();
    
    //check
    num1 = valid(num1);
    num2 = valid(num2);
    if(num1 == null || num1.length() == 0 || num2 == null || num2.length() == 0)
      return result;
      
    if(num1.equals("0") || num2.equals("0"))
      return "0";
    
    if(num1.length() < num2.length()){
      String tmp = num1;
      num1 = num2;
      num2 = tmp;
    }
    
    int len = num2.length();
    String tmpResult;
    String suffix = "";  
    for(int i=len - 1; i>=0;  i--){
      tmpResult = multiple(num1, getDigit(num2.charAt(i)) ) + suffix;
      result = add_string(result, tmpResult);
      suffix += "0";
    }
    
    return result;
    
  }
  
  /*
   * iteral multiplication
   * 
   *  120*16
   *  (120)*(16) => {1*1, 1*6 + 2*1, 2*6+0*1, 0*6} => {}
   *     
   */
  public String multiply_2(String num1, String num2) {
    //check
    num1 = num1.trim();
    num2 = num2.trim();
    if(num1 == null || num2 == null )  // isNotNum(num1) || isNotNum(num2)
      return null;
    if("0".equals(num1) || "0".equals(num2))
      return "0";
    
    //main,  example (120)*(16) => {1*1, 1*6 + 2*1, 2*6+0*1, 0*6} => {}
    //ArrayList<Integer> result = new ArrayList<Integer>();
    int length1 = num1.length(), length2 = num2.length(), lengthT = length1+length2;
    int[] result = new int[lengthT];  //default all are 0
    for(int i=0; i< length1; i++)
      for(int j=0; j< length2; j++)
        result[i + j] += getDigit(num1.charAt(length1 - 1 - i)) * getDigit(num2.charAt(length2 - 1 - j)); 
    
    //shift
    for(int i=0, carry = 0, curr = 0; i< lengthT; i++){
      curr = result[i] + carry;
      
      if(curr > 9){
        carry = curr / 10;
        result[i] = curr % 10;
      } else {
        carry = 0;
        result[i] = curr;
      }
      
    }
    
    //for return
    StringBuilder sb = new StringBuilder();
    int i = lengthT - 1;
    while(i > -1 && result[i] == 0) i--;
    
    while( i > -1)
      sb.append(result[i--]);        
    
    return sb.toString();
  }
  
	/**
	 * 
	 * Given two numbers represented as strings, return multiplication of the
	 * numbers as a string.
	 * 
	 * Note: The numbers can be arbitrarily large and are non-negative.
	 */
	public String multiply_n(String num1, String num2) {
		// check input
		if (null == num1 || null == num2) {
			return null;
		}

		//
		int length = num1.length() + num2.length() - 2;
		int[] result = new int[length + 2]; // default all are 0
		for (int i = 0; i < num1.length(); i++) {
			for (int j = 0; j < num2.length(); j++) {
				result[length - i - j] += (num1.charAt(i) - 48) * (num2.charAt(j) - 48);
			}
		}

		//
		for (int i = 0; i < result.length - 1; i++) {
			result[i + 1] += result[i] / 10;
			result[i] = result[i] % 10;
		}

		int i = result.length - 1;
		for (; i >= 0 && 0 == result[i]; i--)
			;
		StringBuilder sb = new StringBuilder();
		for (; i >= 0; i--) {
			sb.append(result[i]);
		}
		return 0 == sb.length()? "0" : sb.toString();
	}
  
	
	public String multiply_n2(String num1, String num2) {
		// check input
		if (null == num1 || null == num2) {
			return null;
		}

		//
		int length = num1.length() + num2.length() - 2;
		int[] result = new int[length + 2]; // default all are 0
		for (int i = num1.length() - 1; i >= 0 ; i--) {
			for (int j = num2.length() - 1; j >= 0 ; j--) {
				result[length - i - j] += (num1.charAt(i) - 48) * (num2.charAt(j) - 48);
			}
		}

		//
		for (int i = 0; i < result.length - 1; i++) {
			result[i + 1] += result[i] / 10;
			result[i] = result[i] % 10;
		}

		int i = result.length - 1;
		for (; i >= 0 && 0 == result[i]; i--)
			;
		StringBuilder sb = new StringBuilder();
		for (; i >= 0; i--) {
			sb.append(result[i]);
		}
		return 0 == sb.length()? "0" : sb.toString();
	}


    /**
     *
     * Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also represented as a string.
     *
     * Example 1:
     * Input: num1 = "2", num2 = "3"
     * Output: "6"
     *
     * Example 2:
     * Input: num1 = "123", num2 = "456"
     * Output: "56088"
     *
     * Note:
     * The length of both num1 and num2 is < 110.
     * Both num1 and num2 contain only digits 0-9.
     * Both num1 and num2 do not contain any leading zero, except the number 0 itself.
     * You must not use any built-in BigInteger library or convert the inputs to integer directly.
     *
     */
    public String multiply_x(String num1, String num2) {
        if(num1.equals("0") || num2.equals("0")){
            return "0";
        }

        char[] c1 = num1.toCharArray();
        char[] c2 = num2.toCharArray();

        int l1 = c1.length;
        int l2 = c2.length;

        int[] result = new int[l1 + l2]; //default all are 0
        //Arrays.fill(result, 0);

        int n;
        int m;
        for(int i = l1 - 1, p = result.length - 1; i >= 0; i--, p--){
            n = c1[i] - '0';

            if(n == 0){
                continue;
            }

            for(int j = l2 - 1, q = p; j >= 0; j--, q--){
                m = c2[j] - '0';

                result[q] += m * n;
            }
        }

        for(int p = result.length - 1; p > 0; p--){
            result[p - 1] +=  result[p] / 10;
            result[p] %= 10;
        }

        StringBuilder sb = new StringBuilder();
        boolean flag = true;
        for (int i = 0; i < result.length; i++) {
            if(flag && result[i] == 0){
                continue;
            }

            flag = false;
            sb.append(result[i]);
        }
        return sb.toString();
    }

  /**
   * case1:  000=> 0
   * case2:  0012=> 12 
   * case3:  a21 => ""
   * 
   * @param num
   * @return
   */
  private String valid(String num){
    if(num == null || num.length() == 0 )
      return "";

    StringBuffer result = new StringBuffer();
    int i=0;
    int len = num.length();
    for(; i< len; i++){
      if(num.charAt(i) != '0')
        break;
    }
    if(i == len)
      return "0";
    
    for(; i<len; i++){
      if(isValidDigit(num.charAt(i)))
        result.append(num.charAt(i));
      else
        return "";
    }
    
    return result.toString();
  }
  
  private boolean isValidDigit(char digit){
    boolean result = false;
    
    if (digit > 47 && digit < 58) // '0' is 48, '1' is 49,'9' is 57
      result = true;
    
    return result;
  }
  
  private int getDigit(char digit){
    return digit - 48;
  }
  
/*
 * TODO.  now 9+9 = 8 
 * 
 * 
 * recurr
 * 
 * 
 */
  protected int add_R(Queue<String> q1, Queue<String> q2, Stack<String> qResult) {
    int result = 0;
    int carry = 0;
    int tmp = 0;
    int tmp4s1 = 0;
    int tmp4s2 = 0;
    
    if(q1.size() > q2.size())
      tmp4s1 = Integer.valueOf((String)q1.poll());
    else if(q1.size() < q2.size())
      tmp4s2 = Integer.valueOf((String)q2.poll());
    else{
      tmp4s1 = Integer.valueOf((String)q1.poll());
      tmp4s2 = Integer.valueOf((String)q2.poll());
    }
    tmp = tmp4s1 + tmp4s2;

    if (!q1.isEmpty() && !q2.isEmpty())
      carry = add_R(q1, q2, qResult);

    result = tmp + carry;
    carry = result / 10;
    result = result % 10;

    qResult.push(String.valueOf(result));

    return carry;

  }


  
  /**
   * check if b1 is small than b2
   * @param b1, binary string, example 101
   * @param b2, binary string, example 11
   * @return true when b1>=b2, false when b1<b2
   */
  private boolean compare(String b1, String b2){
    boolean result = false;
    int len1 =b1.length();
    int len2 =b2.length();
    if(len1 < len2)
      return true;
    else if(len1 > len2) 
      return false;
          
    for(int i=0; i<len1; i++){
      if( b1.charAt(i) < b2.charAt(i) )
        return true;
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
  public static int log2_div(int num){
    if(num < 0)
      throw new IllegalArgumentException();
    if(num == 0)
      return 0;
    
    //return (int)(java.lang.Math.log(num & 0xffffffffL) / java.lang.Math.log(2)); 
    return (int)(java.lang.Math.log(num & 0x7fffffffL) / java.lang.Math.log(2));
  }
  
  public static int log2_bit(int num){
    if(num < 0)
      throw new IllegalArgumentException();
    
    int log = 0;
    if( ( num & 0xffff0000 ) != 0 ) { num >>>= 16; log += 16; }
    if( num >= 256 ) { num >>>= 8; log += 8; }
    if( num >= 16  ) { num >>>= 4; log += 4; }
    if( num >= 4   ) { num >>>= 2; log += 2; }
    return log + ( num >>> 1 );
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
	System.out.println(" 100/9: \t" + (100/9));  //11
	System.out.println(" -100/9: \t" + (-100/9));  // -11
	  
    Mathmatics s = new Mathmatics();
    
    /* test add     
    int[] input1 = {9};//{3, 1, 5};
    int[] input2 = {9};//{6, 5, 9, 2};
    
    Stack<String> s1 = new Stack<String>();
    for(int i = 0; i < input1.length; i++){
      s1.push(String.valueOf(input1[i]));
    }
    
    Stack<String> s2 = new Stack<String>();
    for(int i=0; i< input2.length; i++){
      s2.push(String.valueOf(input2[i]));
    }
    
    Stack<String> qResult2 = new Stack<String>();
    s.add_stack(s1, s2, qResult2);
    
    System.out.println(qResult2.toString());    
   */ 
    
    /* test multiply      
    //System.out.println(Integer.valueOf('1'));

    String[] num1 = {"01a","009", "0", "00","9","9","909","909","123456789", "6913259244"};
    String[] num2 = {"12","020","9","9","9","99","9","909","987654321", "71103343"};
    String[] ans = {"","180","0","0","81","891","8181","826281","121932631112635269","491555843274052692"};
    
    for(int i=0; i< num1.length; i++){
      System.out.println("\nInput "+num1[i]+" * "+ num2[i]);
      
      System.out.println(s.multiply(num1[i], num2[i]));
      System.out.println(s.multiply_n(num1[i], num2[i]));
      
      System.out.println(ans[i]);
    }
*/
    
    
    /* test square root    */  
    //float fn ;
    //float[] fn = {-1, 0, 1, 3, 0.01f, 0.25f, 0.0000001f, 1.44f, 25, 100, 10000, 100000, 1000000, 10000000, 100000000};
    int[] fn = {999999999};
    //for(n = 0; n < 30; n++){
    //  fn = (float)n/100;
    double sqrt;
    double sqrt_B;
    for(int i=0; i< fn.length; i++){
      System.out.print("\nThe square root of "  );
      System.out.format("%d%n", fn[i]);
      
      sqrt =  s.sqrt_Newton(fn[i]);
      System.out.println("\n with Newton's method: \t" +sqrt );
      //System.out.format("%10.3f%n", sqrt);
      
      sqrt_B = s.sqrt_binarysearch(fn[i]);
      System.out.println(" with binary search: \t" + sqrt_B);
      //System.out.format("%8d%n", sqrt_B);
      //System.out.println();
      
      sqrt_B = s.sqrt_binarysearch_n(fn[i]);
      System.out.println(" with binary search: \t" + sqrt_B);
      
    }

      
    
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
