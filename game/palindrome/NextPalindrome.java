package fgafa.game.palindrome;

public class NextPalindrome
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    //int input = Integer.parseInt(args[0]);

    int input = Integer.parseInt("951");
    long res = NextPalindrome.findNthPalindrome(input);
    System.out.println(input + "th palindormic number is:" + res);

    System.out.println();
        
    int[] arr = {1, 10, 100, 90, 900, 909, 9900, 99000, 10109, 10199, 9909};
    for(int i=0; i< arr.length; i++){
      System.out.println(" The closestPair palindormic number of "+ arr[i] + " is: " +getClosedPalindrome(arr[i]));
    }
    
    
  }

  /*
   * 
   * Return the Nth palindromic Number 
   * 
   * There are the following rule to a n digit number
   * when n = 1, there are 9, (1, 2, 3, 4, 5, 6, 7, 8 and 9)               Total 9 
   * when n = 2,           9, (11, 22, 33, 44, 55, 66, 77, 88 and 99)      Total 9+9 
   * when n = 3,           90, (101, 202, ..., 111, 121, ... )             Total 9+9+90
   * when n = 4,           90, (1001, 2002, ..., 1111, 1221, ... )         Total 9+9+90+90
   * when n = 5,           900,                                            Total 9+9+90+90+900 
   * when n = 6,           900,                                            Total 9+9+90+90+900+900 
   * 
   * The palindromic number is decided by the LEFT part. example when n == 4 (abba), the left part (ab) is from 10 to 99; 
   * when n == 5 (abcba), the LEFT part (abc) is from 100 to 999. when n == 6 (abccba), the LEFT part (abc) is from 100 to 999.
   * 
   * @param: index, the N
   * @return: the Nth palindromic
   * 
   * 
   */
  static long findNthPalindrome(int index) {
    /*step #1, how many digits of the return value*/
    int count = 0; // the total num 
    int number = 9; //the num on the bit, when n = 1, there are 9; when n = 3 or 4, it's 9* 10; when n = 5 or 6, it's 9* 10*10; 
    int w = 0; //how many digit 
  
    //init w is 0 and number is 9,  
    //cycle 1, number is 9, w is 1, count is 9, 
    //cycle 2, number is 9, w is 2, count is 9+9
    //cycle 3, number is 90, w is 3 digit, count is 9+9+90
    while (true) {
      if (w > 0 && (w&1) == 0) // w is Even 
        number *= 10;
      
      w++;
      
      if (count + number > index)
        break;
  
      count += number;
    }
  
    index -= count; // index = index - count;
  
    /*step #2, get the return value, the first digit cann't be 0.*/  
    //when n = 3, it's [aba], the LEFT part is [ab], h is 10; 
    //when n = 5, it's [abcba], the LEFT part is [abc], h is 100;
    //got the LEFT part. Example, when n=5 (abcba), the LEFT part is abc = [index - (9+9+90+90)] + h 
    long h = 1;  
    for (int i = 0; i < (w - 1) / 2; i++) 
      h *= 10;
    long left = h + index; 
    
    //got the return value
    long res = left;  //return value
    if ((w&1) == 1) //w is Odd
      left /= 10;
  
    while (left != 0) { 
      res = res * 10 + left % 10;
      left /= 10;
    }
  
    return res;
  }

  /*
   * 
   * Return the closestPair palindromic Number of the positive input. <br>
   * if n is a palindrome, return itself <br>
   * 
   * The palindromic number is decided by the LEFT part. 
   * example, (abcde),  
   * the closed palindrome is (abcba ), or (ab[c+1]ba) or (ab[c-1]ba),  <br> 
   * 
   * Here it have to pay attention to [c+1]=10 or [c-1]=-1. 
   * when [c+1] = 10, (ab[c+1]ba) will be (a[b+1]0[b+1]a) ---- 
   * 
   * @param: n, 
   * @return: the closestPair palindrome to n
   * 
   * 
   */
  public static int getClosedPalindrome(int n) {
    //check
    if(n<0)
      return -1;
    if(n<10)
      return n;
      
    //get origin left and origin right from input n
    int len = String.valueOf(n).length();  // len == how many digits of n 
    int i = len/2;  
    int num = 10;   
    while(--i>0){
      num *= 10;
    }
    //example: to (abcde), the left is (abc), the right is (de); to (abcd),  the left is (ab), the right is (cd)
    int right = n % num;
    int left = n/num;

    //get the new right from the origin left
    int rightN = reverse(left, len);
    
    //compare the origin right and the new right, get the left  
    if( Math.abs(rightN - right) <= num /2  )
      ; //the result is (abcba)
    else if (right > rightN)      
      left = left + 1; //the result is [ab(c+1)ba]
    else
      left = left - 1;  //the result is [ab(c-1)ba]      
    
    rightN = reverse(left, len);
    return left * num + rightN;
  }

  private static int reverse(int left, int len){
    //System.out.println("--left:"+ left+ "--len:" +len);
    if((len & 1) == 1)  // Odd length
      left = left / 10;  

    int rightN = 0; 
    while(left > 0){
      rightN = rightN * 10 + left % 10; 
      left /= 10;
    }
    
    //System.out.println("--rightN:"+rightN);
    return rightN;
  }
}
