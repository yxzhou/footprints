package util.random;

/*
 * Q1 Given a function Random5() that returns integers from 1 to 5 with equal probability,
 * write a function that returns integers from 1 to 7 with equal probability.
 *
 * Minimize the number of calls to Random5() method. Also, use of any other library function is not allowed and no floating point arithmetic allowed.
 *
 * Q2 Given a function random01() that returns integers 0 or 1 with equal probability,
 * write a function that returns integers from 1 to n-1 with equal probability using only.
 */

public class Random
{
  public int random7() {// returns 1 to 7 with equal probability
      int i = 5 * random5() - random5();

      if (i < 21) {
          return i % 7 + 1;
      }

      return random7();
  }

    public int random7_2(){
        int x = random5() * 5 + random5();

        if(x >= 7 && x < 28){
            return x / 7;
        }

        return random7();
    }

    private int random5() {// given method that returns 1 to 5 with equal probability
        java.util.Random random = new java.util.Random();
        return random.nextInt(5) + 1;
    }
  
  /**
   * Time O(log n)
   */
  public int random(int n) {
     while (true) {
        int t = n, result = 0;
        while (t > 0) {
           result = (result << 1) + random01();
           t >>= 1;
        }
        if (result < n) {
            return result;
        }
     }
  }

    private int random01(){
        // Generate a random number from 0 to 1
        java.util.Random random = new java.util.Random();
        return random.nextInt(2);
    }
}
