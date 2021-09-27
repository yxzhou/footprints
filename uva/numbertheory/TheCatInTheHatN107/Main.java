package uva.numbertheory.TheCatInTheHatN107;

import java.io.BufferedInputStream;
import java.util.Scanner;

class Main
{


  /*
   * Define:
   *  N as the constant of cats inside each (non-smallest) cat's hat.
   *  K (generation) as the recursive level
   *  
   * We want to get:
   *  lazyCat = 1 + n + n^2 + --- + n^(k-1),  ( n^k == workerCat )
   *  totalH = initH * ( 1 + n/(n+1) + n^2/(n+1)^2 + --- + n^k/(n+1)^k )  ( initH / (n+1)^k+1 == 1  )
   * 
   * We have to get n and k with initH and workerCat. 
   * 
   * 1) k * log(n) = log(workerCat)
   * 2) k * log(n+1) = log(initH)
   * 
   * Time O( ) Space O( )
   */
  public void cal(int initH, int workerCat) {

    /*
     * lg(n + 1) / lg(n) = lg(initH) / lg(workerCat) 
     * lg(n + 1) / lg(n) is Strictly Decrease Monotonically 
     */
    int n=0;
    int start = 1, end = workerCat, mid = 0;
    while (start <= end) {   
      mid = start + ((end - start) >> 1);

      //System.out.println(start + " " + end + " " + mid);
      double fRes = Math.log10(initH) * Math.log10(mid);
      double fTemp = Math.log10(mid + 1) * Math.log10(workerCat);
      //System.out.println(fRes + " " + fTemp);
      
      if (Math.abs(fRes - fTemp) < 1e-10) {
        n = mid;
        break;
      }
      else if (fTemp > fRes) 
        start = mid + 1;
      else 
        end = mid - 1;

    }
    int k = (int) Math.floor( Math.log10(initH) / Math.log10(n + 1) + 1e-9);
    //System.out.println(n + " -- " + k );
    //System.out.println(Math.pow(n+1, k) + " -- " + Math.pow(n, k) );
    
    //
    int totalCat = 0, totalH = 0;
    int currGenerationLazyCat = 1;

    for (int i = 0; i <= k; ++i) {
      
      totalH += currGenerationLazyCat * initH;
      totalCat += currGenerationLazyCat;
      initH /= (n + 1);
      currGenerationLazyCat *= n;
    }
    
    System.out.println((totalCat - workerCat) + " " + totalH);

  }



  /**
   * @param args
   */
  public static void main(String[] args) {
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");

    try {
      while (in.hasNext()) {
        int initH = in.nextInt();
        int workerCat = in.nextInt();

        if(initH == 0 && workerCat==0)
          break;
          
        sv.cal(initH, workerCat);
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
