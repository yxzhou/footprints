package dp.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/*
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 * 
 * The robot can only move either down or right at any point in time. 
 * The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 * 
 * How many possible unique paths are there?
 * 
 * Note: m and n will be at most 100.
 */
public class UniquePaths
{
  /*
   * Define f(m,n) as the possible unique paths.
   * f(m,n) = 1   m = 1 or n=1
   *        = f(m-1, n) + f(m, n-1)  
   * 
   * Time O(m*n)   Space O(m*n)
   * 
   */
  public int uniquePaths(int m, int n) {
    int[][] pathNum = new int[m][n]; 
    
    for(int row=0; row< m; row++){
      pathNum[row][0] = 1;
    }
    for(int col=0; col< n; col++){
      pathNum[0][col] = 1;
    }
    
    for(int row=1; row< m; row++){     //m>2 and n>2
      for(int col=1; col< n; col++){
        pathNum[row][col] = pathNum[row - 1][col] + pathNum[row][col - 1];
      }
    }
    
    return pathNum[m-1][n-1];
  }
  
  /**
   * @param n, m: positive integer (1 <= n ,m <= 100)
   * @return an integer
   */
	public int uniquePaths_n(int m, int n) {
		// init
		int[] paths = new int[n];
		paths[0] = 1;

		// main
		for (int row = 0; row < m; row++) {
			for (int col = 1; col < n; col++) {
				paths[col] += paths[col - 1];
			}
		}

		// return
		return paths[n - 1];
	}
  
  /*
   * to every path, it must include (m-1) down and (n-1) right.  it's a simple combination.
   * C(m-1, m+n-2) if m < n.
   * 
   * //m>2 and n>2
   * 
   * Time O(n)   Space O(1)
   */
  public int uniquePaths2(int m, int n) {
    if( m<2 || n< 2)
      return 1;
    
    if(m > n)
      return uniquePaths2(n, m);
    
    double result = 1;
    
    for(int i=m+n-2; i > n-1; i--)
      result *=i;
    
    for(int i=m-1; i > 1; i--)
      result = result / i;
    
    return (int)result;
  }

  public double uniquePaths22(int m, int n) {
    if (m < 2 || n < 2)
      return 0;

    if (m > n){
      return uniquePaths2(n, m);
    }

    final int N = 200;
    Factor fsv = new Factor(N);
    HashMap<Integer, HashMap<Integer, Integer>> factors = fsv.getFactors(); // example  <2,<2, 1>>, <3,<3, 1>>, <4,<2, 2>>, <5,<5, 1>>, <6,<<2, 1>, <3, 1>>>

    HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
    for (int i = m + n - 2; i > n - 1; i--) {
      if (!factors.containsKey(i)) {
        System.out.println("==" + i);
      }
      fsv.mergeFactor(result, factors.get(i));
    }

    for (int i = m - 1; i > 1; i--) {
      fsv.quitFactor(result, factors.get(i));
    }

    double returnvalue = 1;
    for (Integer key : result.keySet()) {
      returnvalue *= Math.pow(key, result.get(key));
    }

    return returnvalue;
  }


  class Factor {
    int n;
    List<Integer> primes = new ArrayList<Integer>();

    public Factor(int n) {
      this.n = n;
    }

    public HashMap<Integer, HashMap<Integer, Integer>> getFactors() {
      HashMap<Integer, HashMap<Integer, Integer>> factors = new HashMap<Integer, HashMap<Integer, Integer>>();

      int limit = (int) Math.sqrt(n);
      HashMap<Integer, Integer> factor;
      for (int i = 2; i <= limit; i++) {
        if (!factors.containsKey(i)) {
          for (int j = i * i; j <= n; j += i) {
            if (factors.containsKey(j))
              factor = factors.get(j);
            else {
              factor = new HashMap<Integer, Integer>();
              factors.put(j, factor);
            }
            factor.put(i, 1);
          }
        }
      }

      //add the prime 
      for (int i = 2; i <= n; i++) {
        if (!factors.containsKey(i)) {
          primes.add(i);

          factor = new HashMap<Integer, Integer>();
          factors.put(i, factor);
          factor.put(i, 1);
        }
      }

      //get the factors for factorial
      int mul;
      for (int i = 2; i <= n; i++) {
        factor = factors.get(i);

        mul = 1;
        for (Integer it : factor.keySet())
          mul *= it;

        mul = i / mul;

        if (mul > 1)
          mergeFactor(factor, factors.get(mul));
      }

      return factors;
    }

    public void mergeFactor(HashMap<Integer, Integer> f1, HashMap<Integer, Integer> f2) {
      for (Integer it : f2.keySet()) {
        if (f1.containsKey(it))
          f1.put(it, f1.get(it) + f2.get(it));
        else
          f1.put(it, f2.get(it));
      }
    }

    public void quitFactor(HashMap<Integer, Integer> f1, HashMap<Integer, Integer> f2) {
      for (Integer it : f2.keySet())
        f1.put(it, f1.get(it) - f2.get(it));

    }
  }


  /**
   * @param args
   */
  public static void main(String[] args) {
    UniquePaths sv = new UniquePaths();
    //int m = 6, n=4;
    int N = 100;

    long start1 = System.currentTimeMillis();

    for (int m = 2; m < N; m++) {
      for (int n = m; n < N; n++) {
        System.out.println(m + "--" + n + ": " + sv.uniquePaths(m, n) + "\t" + sv.uniquePaths2(m, n) + "\t" + sv.uniquePaths22(m, n));
        sv.uniquePaths(m, n);
      }
    }


    long start2 = System.currentTimeMillis();

    for (int m = 2; m < N; m++) {
      for (int n = m; n < N; n++) {
        //System.out.println(m + "--" +n + ": "+ sv.uniquePaths(m, n) + "\t" + sv.uniquePaths2(m, n));
        sv.uniquePaths2(m, n);
      }
    }

    long start3 = System.currentTimeMillis();

    for (int m = 2; m < N; m++) {
      for (int n = m; n < N; n++) {
        //System.out.println(m + "--" +n + ": "+ sv.uniquePaths(m, n) + "\t" + sv.uniquePaths2(m, n));
        sv.uniquePaths2(m, n);
      }
    }

    System.out.println((start2 - start1) + " " + (start3 - start2) + " " + (System.currentTimeMillis() - start3));

  }

}
