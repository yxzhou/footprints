package binarysearch;

import util.Misc;


/**
 * 
 * You have to paint N boards of length {A0, A1, A2 … AN-1}. 
 * There are K painters available and you are also given how much time a painter takes to paint 1 unit of board. 
 * You have to get this job done as soon as possible under the constraints that 
 * any painter will only paint continuous sections of board, say board {2, 3, 4} or only board {1} or nothing.
 *
 * Refer to _http://www.leetcode.com/2011/04/the-painters-partition-problem.html
 * 
 * ?Q1, the above is partition by order in the array, how about partition by value?
 * 
 */

public class PainterPartition
{
  
  /**
   * Divide a int array into k or fewer partitions, and try to make it more average (the maximum sum over all the partitions is minimized)
   * Defined M[n][k] to show the result that divide n elements into k or fewer partitions
   * M[j][i] = a[0]      when j = 1, 
   *         = a[0]+ -- +a[j-1] = sum(a[0], -, a[j-1])       when i = 0
   *         = max(a[0],-, a[i] )   when j==i,  i <= k
   *         = min(best, max(partition(A, p, k-1), sum(A, p, n-1)));   p is from k-1 to n-1
   * 
   * Time O(k*N*N), space O(k*N)
   * @param a,  a int array 
   * @param k,  
   * @return  the max partition in the optimal partition 
   *          return -1, when the arrya is null  or k < 1
   * 
   */
  public int findMaxPartition_DP(int[] a, int k){
    //check
    if(a == null || k<1 )
      return -1;
    
    int n = a.length;
    
    if(k >= n)
      return getMax(a, 0, n);
    
    int[][] M = new int[n+1][k+1];
    
    int[] sum = new int[n+1];  // sum[i]= a[0]+---+a[i-1], sum[0] == 0, sum[k-1] = 0
    for(int i=1; i<=n; i++)
      sum[i] = sum[i-1] + a[i-1];
    
    for(int i=1; i<=k; i++)
      M[1][i] = a[0]; 
    for(int i = 2; i <=n; i++)
      M[i][1] = sum[i];
    for(int i=2; i<=k; i++)
      M[i][i] = max(M[i-1][i-1], a[i-1]);//getMax(a, 0, i);    
    
    for (int i = 2; i <= k; i++) {
      for (int j = i+1; j <= n; j++) {  //
      //for (int j = 2; j <= n; j++) {  //
        //best = min(best, max(partition(A, j, k-1), sum(A, j, n-1)));   j is from k-1 to n-1
        int best = Integer.MAX_VALUE; //M[j][i-1];

        for (int p = 1; p <= j; p++) {
          if (best > sum[j] - sum[p])
            best = min(best, max(M[p][i-1], sum[j] - sum[p]));
        }
        M[j][i] = best;
      }
    }
    
    //System.out.println(""+Misc.array2String(M));
    
    return M[n][k];
  }
  
  private int max(int x, int y){
    return (x > y) ? x : y ;
  }

  private int min(int x, int y){
    return (x < y) ? x : y ;
  }

  private int getMax(int[] a, int start, int end){
    int max = a[start];
    for(int i = start + 1; i < end; i++)
      max = max(max, a[i]);
    
    return max;
  }

  private int getSum(int[] a, int start, int end){
    int sum = a[start];
    for(int i = start + 1; i < end; i++)
      sum += a[i];
    
    return sum;
  }
  
  /**
   * Divide a int array into k or fewer partitions, and try to make it more average (the maximum sum over all the partitions is minimized)
   * The maximum sum is from max(a) to sum(a). 
   * Binary search the maximum sum,  to find:
   *  1 the maximum sum is minimized
   *  2 it's partition by k painters. 
   * 
   * O(k * log(sum(a, 0, a.length))  ) -- O( k * log C ), 
   * 
   * @param a
   * @param k
   * @return
   */
  public int findMaxPartition(int[] a, int k) {
    //check
    if(a == null || k<1 )
      return -1;
    
    int n = a.length;
    
    if(k >= n)
      return getMax(a, 0, n);
    
    int low = getMax(a, 0, n);
    int high = getSum(a, 0, n);
    //System.out.println("low"+ low + " high"+ high);
    
    int mid;
    int painterNum;
    while(low < high){
      mid = low + (high - low) / 2;  // avoid overflow, compare to (high + low) / 2
      painterNum =  getPainterNumByOrder(a, mid);
      
      // If it need fewer painter than the expect , the max task of the painters would be higher.  
      if( painterNum <= k ){
        high = mid;   
      }else{
        low = mid + 1;
      }
    }
    
    return low;
  }
  
  /**
   * partition by order
   * @param a
   * @param maxPartitionPerPainter
   * @return
   */
  private int getPainterNumByOrder(int[] a, int maxPartitionPerPainter){
    int total = 0;
    int painterNum = 1;  // default it's 1, it means we need 1 painter at least.
    
    for(int i=0; i< a.length; i++){
      total += a[i];
      
      if(total > maxPartitionPerPainter){
        total = a[i];
        painterNum ++;
      }
    }
    
    return painterNum;
  }
  /**
   * partition by value
   * TODO
   * 
   * @param a
   * @param maxPartitionPerPainter
   * @return
   */
  private int getPainterNumByV(int[] a, int maxPartitionPerPainter){
    int total = 0;
    int painterNum = 1;  // default it's 1, it means we need 1 painter at least.
    
    // make a in descend order 
    // partition by value   
    
    return painterNum;
  }  
  
  public static void main(String[] args) {
    PainterPartition s = new PainterPartition();
    
    int[][] a = {null, {95}, {95},  {95, 31, 13}, {95, 31, 13, 87, 71, 48, 37, 73, 64, 93}};
    int[] k = {2, -1, 2, 2, 10}; 
    
    for(int i=0; i<k.length; i++){
      System.out.println( "\nDivide A:"+ Misc.array2String(a[i]) + "into K:" + k[i]  );
      
      System.out.println("findMaxPartition_DP(), it is:" + s.findMaxPartition_DP(a[i], k[i]));
      
      System.out.println("findMaxPartition(), it is:" + s.findMaxPartition(a[i], k[i]));
      
    }
    
    int[] a2 = {95, 31, 13, 87, 71, 48, 37, 73, 64, 93};
  
    
    for(int i=0; i<a2.length; i++){
      System.out.println( "\nDivide A:"+ Misc.array2String(a2) + "into K:" + i  );
      
      System.out.println("findMaxPartition_DP(), it is:" + s.findMaxPartition_DP(a2, i));
      
      System.out.println("findMaxPartition(), it is:" + s.findMaxPartition(a2, i));
      
    }    
    
    
  }

}
