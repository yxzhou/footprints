package fgafa.stringmatching;

public class RabinKarp2
{

  // get the value of the character in the set   
  private static int getV(char p, String set) {
    //the default Charset is ASCII, 256 characters. example it's 48 to '0', it's 65 to 'A'
    if( null == set) 
      return (int)p;
    
    //if there is a special set, return the relative position. 
    for (int i = 0; i < set.length(); i++) {
      if (p == set.charAt(i))
        return i;
    }
    return -1;
  }



  private static long longRandomPrime(String P, String set) {

    //Fibonacci 
    return 13;
  }



  /*
   * check the string if match
   */
  private static boolean check(String T, String P, int i) {
    for (int j = 0; j < P.length(); j++)
      if (P.charAt(j) != T.charAt(i + j))
        return false;
    return true;
  }



  private static int RK(String T, String P, String set) {
    //default Charset is ASCII, it has 256 characters defaultly 
    int d = 256;            
    if(null != set)
      d = set.length(); // d is the size of the character set    
    
    int n = T.length();
    int m = P.length();
    long q = longRandomPrime(P, set); // to reduce constant time, select a modul
    long h = 1; // = pow(long(d), m-1);  
    {
      for (int i = 1; i <= m - 1; i++)
        h = (d * h) % q;
    }

    //preprocess
    long p = 0;
    long t = 0;
    for (int i = 0; i < m; i++) {
      p = (d * p + getV(P.charAt(i), set)) % q;
      t = (d * t + getV(T.charAt(i), set)) % q;
    }

    //matching
    for (int s = 0; s < n - m; s++) {
      //the next sub-string 
      t = (getV(T.charAt(s + m), set) + d
          * (t + q - h * getV(T.charAt(s), set) % q))
          % q;

      if (p == t || check(T, P, s)) {
        return s + 1;
      }
            
    }
    return -1;
  }



  public static void main(String[] args) {
    // set is the character set
    String set = null;   //  it means the default Charset is ASCII, 256 characters.
    //set = "0123456a789";

        
    // pattern P  
    String P = "23a65";
    // T is the string to match  
    String T = "25856923a6589780";

    int offset = RK(T, P, set);

    // print results
    System.out.println("text:    " + T);
    // from brute force search method 1
    System.out.print("pattern: ");
    for (int i = 0; i < offset; i++)
      System.out.print(" ");
    System.out.println(P);

  }

}
