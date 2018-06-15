package fgafa.game;

/**
 * refer to http://www.leetcode.com/2011/01/ctrla-ctrlc-ctrlv.html
 * 
 * Imagine you have a special keyboard with the following keys:
 *
 * A
 * Ctrl+A
 * Ctrl+C
 * Ctrl+V
 * where CTRL+A, CTRL+C, CTRL+V each acts as one function key for “Select All”, “Copy”, and “Paste” operations respectively.
 *
 * If you can only press the keyboard for N times (with the above four keys), please write a program to produce maximum numbers of A. 
 * If possible, please also print out the sequence of keys.
 *
 * That is to say, the input parameter is N (No. of keys that you can press), the output is M (No. of As that you can produce).
 *
 * solution: 
 * 1) press 2 times A, get AA.  press 2 times A, and CTRL+A, CTRL+C, CTRL+V, CTRL+V (total it's 6 times ), get AAAA.
 * 2) define 2A as 2 times A, (2 times press,  M = M + 2 )
 *    define 2D as CTRL+A, CTRL+C, CTRL+V, CTRL+V (4 times press,  M = M * 2), 
 *    define 3D as CTRL+A, CTRL+C, CTRL+V, CTRL+V, CTRL+V (5 times press,  M = M * 3)
 *    define 2D2D as (CTRL+A, CTRL+C, CTRL+V, CTRL+V), (CTRL+A, CTRL+C, CTRL+V, CTRL+V) (8 times press,  M = M * 2 * 2)
 *  
 * define n is the input parameter,
 * define the output is M, 
 * define f(n) is the sequence of keys, 
 * define k is the number of steps 
 * define (a1, ---, ak), sum(a1, a2, ---, ak)
 * (e.g.     ).
 *   
 * f(n) = nA            M=n         n<=7           
 *      = 3A3D          M=3*3=9     n =8    sum=6(3+3)       k=2
 *      = 4A3D=3A4D=12  M=3*4=12    n =9    sum=7(3+4)       k=2
 *      = 4A4D          M=4*4=16    n =10   sum=8(4+4)       k=2
 *      = 5A4D=>4A5D    M=5*4=20    n =11   sum=9(4+5)       k=2
 *      = 5A5D          M=5*5=25    n =12   sum=10(5+5)      k=2
 *      = 5A6D          M=5*6=30    n =13
 *      = 6A6D          M=6*6=36    n =14   sum=12           k=2
 *      = 6A7D=>3A4D4D  M=3*4*4=48  n =15   sum=11(3+4+4)    k=3 
 *      = 4A4D4D        M=4*4*4=64  n =16
 *      ---
 *      = 3A4D4D4D                  n =21 
 *      ---
 *      = 4A5D5D5D                  n =25
 *      = 5A5D5D5D                  n =26
 *      = 3A4D4D4D4D                n =27 
 *      ---
 *      
 *  f(n) = a1Aa2Da3D---akD    
 *  1) a1 + a2 + -- + ak = n-2(k-2)
 *  2) M = a1*a2*a3*---*ak      
 *  3) if it want M max, a1, a2, ---, ak should be closed, ABS|ai-aj| <= 1.  
 *      
 *  when ax+(a+1)(k-x) = n-2(k-1)  ( a>=2, (n+2)/2>k>=2, n>7, x<k ) , get the max( a^x * (a+1)^(k-x) ) 
 *           
 */

public class CTRLC
{

 
/*
 * when ax+(a+1)(k-x) = n-2(k-1)   , get the max( a^x * (a+1)^(k-x) ) 
 * and a>=2, (n+4)/2>=k>=2, n>7, x<k 
 * 
 * n-2(k-1) = ax+(a+1)(k-x) >= ak ==> k<=(n+2)/(a+2)  ==> k<=(n+2)/4
 * 
 */
public long f(int n) {
  if (n <= 7) return n;

  int k = 2;    // define k as the steps number,  the default is 2 when n > 7
  int kmax = (n+2)/4;  // n-2(k-1) = ax+(a+1)(k-x) >= ak ==> k<=(n+2)/(a+2)  ==> k<=(n+2)/4 
  int sum = 0;   // sum = n-2(k-1)
  long mul = 1;  // the total times, e.g. to 3A3D, the total times is   
  int a;   //aAxD
  int x;   //aAxD
  
  long mulMax = 1;
  //int aM = 0;
  //int xM = 0;
  //int kM = 0;
  
  while (k <= kmax) {
      sum = n - 2*(k-1);
      a = sum/k;
      x = sum%k;
      
      mul = (long)Math.pow(a, k-x) * (long)Math.pow(a+1, x);
      
      if(mul > mulMax){
        mulMax = mul;
        //aM = a;
        //xM = x;
        //kM = k;
      }
      
      k++;
  }

  //System.out.print("\t\t" + mulMax + "\t" + getSteps(aM, xM, kM));
  return mulMax;
}

/*
 * 
 * 
 */
private String getSteps(int a, int x, int k){
  if(a==0)
    return "";
  
  StringBuffer sb = new StringBuffer();
    
  sb.append(a);
  sb.append("A");
  
  for(int i=0; i< k-x-1; i++){
    sb.append(a);
    sb.append("D");    
  }
    
  for(int i=0; i< x; i++){
    sb.append(a+1);
    sb.append("D");    
  }
  
  return sb.toString();
}

  /**
   * @param args
   */
  public static void main(String[] args) {
    CTRLC s = new CTRLC(); 
    
    int n = 101;
    for(int i =0; i< n; i++ ){
      System.out.print("\n  "+ i + ": ");
      //System.out.print("\t"+ s.f(i) );
      s.f(i);
    }

  }

}
