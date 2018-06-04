package fgafa.util.Random;

public class Random
{
 
  /*
   * An efficient program to randomly select a number from stream of numbers, with O(1) space
   * Given a stream of numbers, generate a random number from the stream. 
   * You are allowed to use only O(1) space and the input is in the form of stream, so can�t store the previously seen numbers.
   * 
   * So how do we generate a random number from the whole stream such that the probability of picking any number is 1/n. with O(1) extra space? 
   * This problem is a variation of Reservoir Sampling. Here the value of k is 1.
   * 
   * 1) Initialize count as 0, count is used to store count of numbers seen so far in stream.
   * 2) For each number x from stream, do following
   *   a) Increment count by 1.
   *   b) If count is 1, set result as x, and return result.
   *   c) Generate a random number from 0 to count-1. Let the generated random number be i.
   *   d) If i is equal to count � 1, update the result as x.
   * 
   * To simplify proof, let us first consider the last element, the last element replaces the previously stored result with 1/n probability. 
   * So probability of getting last element as result is 1/n.
   * Let us now talk about second last element. When second last element processed first time, the probability that it replaced the previous result is 1/(n-1). 
   * The probability that previous result stays when nth item is considered is (n-1)/n. 
   * So probability that the second last element is picked in last iteration is [1/(n-1)] * [(n-1)/n] which is 1/n.
   * Similarly, we can prove for third last element and others.
   * 
   */
  static int res;    // The resultant random number
  static int count = 0;  //Count of numbers visited so far in stream
  
  public static int selectRandom(int x){   
      count++;  // increment count of numbers seen so far
   
      // If this is the first element from stream, return it
      if (count == 1)
          res = x;
      else
      {
          // Generate a random number from 0 to count - 1
          java.util.Random random = new java.util.Random();
          int i = random.nextInt(count);
   
          // Replace the prev random number with new number with 1/count probability
          if (i == count - 1)
              res  = x;
      }
      return res;
  }
   
  
  /*
   * Given a function Random5() that returns integers from 1 to 5 with equal probability, 
   * write a function that returns integers from 1 to 7 with equal probability.
   *  
   * Minimize the number of calls to Random5() method. Also, use of any other library function is not allowed and no floating point arithmetic allowed.
   */
  
  private int random5() // given method that returns 1 to 5 with equal probability
  {
      // some code here
    // Generate a random number from 0 to count - 1
    java.util.Random random = new java.util.Random();
    return random.nextInt(5) + 1;
    
  }
   
  public int random7() // returns 1 to 7 with equal probability
  {
      int i;
      i = 5*random5() + random5() - 5;
      if (i < 22)
          return i%7 + 1;
      return random7();
  }
  
  /*
   * Given a function random01() that returns integers 0 or 1 with equal probability, 
   * write a function that returns integers from 1 to n-1 with equal probability using only.
   * 
   * Time O(log n)
   */
  private int rand01(){
    // some code here
    // Generate a random number from 0 to 1
    java.util.Random random = new java.util.Random();
    return random.nextInt(2);
  }
  public int rand(int n)
  {
     while (true) {
        int t = n, r = 0;
        while (t>0) {
           r = (r << 1) + rand01();
           t >>= 1;
        }
        if (r < n) return r;
     }
  }
  
  // Driver program to test above function.
  public static void main(String[] args) 
  {
      int[] stream = {1, 3, 5, 6, 7, 9};
      int n = stream.length;
   
      // Use a different seed value for every run.

      for (int i = 0; i < n; ++i)
          System.out.printf("Random number from first %d numbers is %d \n",
                                  i+1, selectRandom(stream[i]));
      
  }
  


}
