package fgafa.uva.numbertheory.FermatPythagorasN106;

import java.io.BufferedInputStream;
import java.util.Scanner;

class Main
{

  static int[] ss = new int[1000001];  //Each integer in the input file will be less than or equal to 1,000,000

  /*
   * Time O( ) Space O( )
   */
  public void cal(int n) {

    int m, r, s, x, y, k, z;
    int up;
    int tri = 0, total = 0;
    m = (int) Math.sqrt(n);
    if (m * m < n)
      m++;

    for (r = 1; r <= m; r++) {      
      up = Math.min((int) Math.sqrt(n - r * r), r - 1);
      for (s = 1; s <= up; s++) {
        x = r * r - s * s;
        y = 2 * r * s;
        z = r * r + s * s;
        if (x * x + y * y == z * z && z <= n) {
          if (gcd(x, y) == 1) {
            tri++;
            
            for( k = n/z; k>0; k--){
              ss[k * x] = 1;
              ss[k * y] = 1;
              ss[k * z] = 1;
            }

          }
        }
      }
    }
    
    for (k = 1; k <= n; k++) {
      if (ss[k] == 0)
        total++;
      ss[k] = 0;
    }
    
    System.out.println(tri + " " + total);

  }



  private int gcd(int a, int b) {
    return b > 0 ? gcd(b, a % b) : a;

  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");

    try {
      while (in.hasNext()) {
        int n = in.nextInt();

        sv.cal(n);
      }
    }
    catch (Exception e) {
      // e.printStackTrace();
    }
    finally {
      in.close();
    }

  }

}
