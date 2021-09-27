package stringmatching;

import util.Misc;

/*
 * KMP - Knuth-Morris-Pratt
 * 
 * 
 * 
 */
public class KMP
{

  /**
   * 
   * 
   * @param P
   * @param next
   */
  //preprocess
  private static void getNexts(char[] P, int[] next) {
    //---------------
//    int m = P.length;
//
//    int k = 0;
//    for (int i = 2; i < m; i++) {
//      while (k > 0 && P[k + 1] != P[i]) {
//        k = next[k];
//      }
//      if (P[k + 1] == P[i]) {
//        k++;
//      }
//      next[i] = k;
//    }

    //---------------
        int m = P.length;
        
        int k = 0;
        for (int i = 2; i <= m; i++) {
            while (k > 0 && P[k] != P[i - 1]) {
                k = next[k - 1];
            }
            if (P[k] == P[i - 1]) {
                k ++;
            }
            next[i - 1] = k;
        }

    //---------------    
  }

  public static void KMPMatcher(char[] T, char[] P) {

    int n = T.length;
    int m = P.length;

    //fail 
    int[] next = new int[m]; // the default value all are 0
    //System.out.println(" The prefix function initial value is: " + misc.array2String(fail));

    //preprocessing
    getNexts(P, next);

    System.out.println("   The prefix function is: " + Misc.array2String(next));

    //if (true) return;
    System.out.println("The text is:" + Misc.array2String(T));

    int q = 0;
    for (int i = 1; i <= n; i++) {
      while (q > 0 && P[q] != T[i - 1]) {
        q = next[q - 1];
      }

      if (P[q] == T[i - 1]) {
        q = q + 1;
      }

      if (q == m) {
        System.out.print("           ");
        for (int l = 0; l < (i - m + 1); l++)
          System.out.print(" ");
        System.out.println(P);

        q = next[q - 1];
      }
    }
  }

  /**
   * 
   * 
   * @param P
   * @param next
   */
  //preprocess
  private static void getNexts_wildcard(char[] P, int[] next) {
    //---------------
//    int m = P.length;
//
//    int k = 0;
//    for (int i = 2; i < m; i++) {
//      while (k > 0 && P[k + 1] != P[i]) {
//        k = next[k];
//      }
//      if (P[k + 1] == P[i]) {
//        k++;
//      }
//      next[i] = k;
//    }

    //---------------
        int m = P.length;
        
        int k = 0;
        for (int i = 2; i <= m; i++) {
            while (k > 0 && P[k] != P[i - 1] && P[k] !='?') {
                k = next[k - 1];
            }
            if (P[k] == P[i - 1] || P[k] == '?' ) {
                k ++;
            }
            next[i - 1] = k;
        }

    //---------------    
  }

  public static void KMPMatcher_wildcard(char[] T, char[] P) {

    int n = T.length;
    int m = P.length;

    //fail 
    int[] next = new int[m]; // the default value all are 0
    //System.out.println(" The prefix function initial value is: " + misc.array2String(fail));

    //preprocessing
    getNexts_wildcard(P, next);

    System.out.println("   The prefix function is: " + Misc.array2String(next));

    //if (true) return;
    System.out.println("The text is:" + Misc.array2String(T));

    int q = 0;
    for (int i = 1; i <= n; i++) {
      while (q > 0 && P[q] != T[i - 1]) {
        q = next[q - 1];
      }

      if (P[q] == T[i - 1] || P[q] == '?') {
        q = q + 1;
      }

      if (q == m) {
        System.out.print("           ");
        for (int l = 0; l < (i - m + 1); l++)
          System.out.print(" ");
        System.out.println(P);

        q = next[q - 1];
      }
    }
  }


  public static void main(String[] args) {

    // pattern P  
    String[] P = {"abacabcabacab", "abaabcac", "ababababca", "ababbabbabbababbabb", "ababacb", "abcdefgegcsgcasse"}; 
    //"abaabcac";      -> 00112010
    //"ababababca";     -> 0012345601
     
    // T is the string to match  
    String T = "acabaabaabcacaabcaabaabcacaabc";

    System.out.println("--The Text is:       : " + T);
    for(int i=0; i< P.length; i++){
      System.out.println(i + ". The pattern is:       : " + P[i]);
      KMPMatcher(T.toCharArray(), P[i].toCharArray());      
      //KMPMatcher_wildcard(T.toCharArray(), P[i].toCharArray());  
    }

  }

}
