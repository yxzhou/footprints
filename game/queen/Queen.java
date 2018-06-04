package fgafa.game.queen;

/*
 * the very fast method
 * 
 */

public class Queen
{

  public static int SUM = 0;
  public static long upperlimit = 1;

  public int totalNQueens(int n) {
    if ((n < 1) || (n > 32)) {
      System.out.println(" Just available from 1 to 32\n");;
      return -1;
    }
    //System.out.println(n + " Queen \n");
    
    SUM = 0 ;
    upperlimit = (1 << n) - 1;
    
    compute(0, 0, 0);
    
    return SUM;
  }

  private static void compute(long row, long ld, long rd) {

    if (row == upperlimit) {
      SUM++;

    } else {
      long pos = upperlimit & ~(row | ld | rd);
      while (pos != 0) {

        long p = pos & -pos;
        pos -= p;
        compute(row + p, (ld + p) << 1, (rd + p) >> 1);
      }

    }

  }


  public int totalNQueens_2(int n) {
      assert(n>0);
      return countSolution(1,new int[n]);
  }
  
  public int countSolution(int k, int[] pattern){
      int n= pattern.length;
      assert(k<=n);
      int res = 0;
      main:
      for(int i=0;i<n;i++){
          for(int j=0;j<k-1;j++){
              if(pattern[j]==i||Math.abs(j-k+1)==Math.abs(pattern[j]-i))
              continue main;
          }
          pattern[k-1]=i;
          if(k==n) return 1;
          else res+=countSolution(k+1,pattern);
      }
      return res;
  }
  
//  public static void main(String[] args){
//    
//    int n = 32;
//    System.out.println("---:" + ((upperlimit << n) - 1));
//    
//    long pos = 124;
//    System.out.println("---:" + (-pos));
//    System.out.println("---:" + (pos & -pos));
//    System.out.println("---:" + Long.toBinaryString(pos));
//    System.out.println("---:" + Long.toBinaryString(-pos));
//    System.out.println("---:" + Long.toBinaryString(pos & -pos));
//  }

  public static void main(String[] args) {
    //Calendar start = Calendar.getInstance();
    int n = 12; // default value

    if (args.length > 0)
      n = Integer.parseInt(args[0]);
    
    Queen sv = new Queen();
    
    for(int i=1; i<=n; i++){
      long startTime = System.currentTimeMillis();
      //System.out.println("---:" + Long.toBinaryString(upperlimit));

      sv.totalNQueens(i);

      System.out.print("Totally it has " + SUM + " combinations. ");
      System.out.print("It takes " + (System.currentTimeMillis() - startTime)
          + " ms \n");
    }
  }
}