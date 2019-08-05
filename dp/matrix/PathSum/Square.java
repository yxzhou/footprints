package fgafa.dp.matrix.PathSum;

/**
 * 
 * 
 * @author yxzhou
 *
 */
public class Square
{

  /*
   * Given a m x n grid filled with non-negative numbers, 
   * find a path from top left to bottom right which minimizes the sum of all numbers along its path.
   * 
   * Solution:  DP
   * define dist[x][y] to store the min path from grid[0][0] to grid[x][y] 
   * dist[x][y] = grid[0][0]                          x=0, y=0
   *            = dist[x-1][0]+grid[x][0]             x>0, y=0
   *            = dist[0][y-1]+grid[0][y]             x=0, y>0
   *            = min(dist[x][y-1],dist[x-1][y])+grid[x][y]             x>0, y>0
   * 
   * Note: You can only move either down or right at any point in time.
   *
   * Time O(M*N)  Space O(M*N) 
   */
  public int minPathSum1(int[][] grid) {
    //check
    if(grid == null || grid.length == 0 )
      return 0;
    
    int m = grid.length; // row
    int n = grid[0].length;  //column
    int[][] dist = new int[m][n];
    dist[0][0] = grid[0][0];
    
    for(int row =1; row < m; row ++){
      dist[row][0] = dist[row-1][0] + grid[row][0];
    }
    for(int col =1; col < n; col ++){
      dist[0][col] = dist[0][col-1] + grid[0][col];
    }
    
    for(int row = 1; row < m; row ++ ){
      for(int col = 1; col < n; col ++){
        dist[row][col] = Math.min(dist[row][col-1], dist[row-1][col]) + grid[row][col] ;
      }
    }
    
    return dist[m-1][n-1];
  }

  
  /* Time O(M*N)  Space O(M) */
  public int minPathSum2(int[][] grid) {
    //check
    if(grid == null || grid.length == 0 )
      return 0;
    
    int m = grid.length; // row
    int n = grid[0].length;  //column
    int[] dist = new int[n+1];
    for(int i=0; i<=n ; i++)
      dist[i] = Integer.MAX_VALUE;
    
    dist[1] = 0;
        
    for(int row = 0; row < m; row ++ ){
      for(int col = 0; col < n; col ++){
        dist[col+1] = Math.min(dist[col+1], dist[col]) + grid[row][col] ;
      }
    }
    
    return dist[n];
  }
  
  /*
  * similar with minPathSum 
  * 
  * Time O(M*N)  Space O(M*N) 
  */
  public int maxPathSum(int[][] grid) {
    //TODO
    return -1;
  }
  
  
  /*
   * Given a m x n grid filled with money (non-negative numbers), 
   * Two men start from top-left to bottom-right, find 2 path which maximizes the sum of all money along its path.
   * 
   * Note: The man can only move either down or right at any point in time. The money in one grid can only be gotten by one man.  
   * 
   * Solution:  DP
   * Define f[x][y][p][q] to store the max money that the 2 guy get in grid[x][y] and grid[p][q]. 
   * The start point is f[0][0][0][0], and the end point is f[m][n][m][n]   
   *  
   * f[x][y][p][q] = grid[0][0]                       x=p=y=q=0
   *            = sum[0][t]                           x=p=0  t=max(y, q) sum[0][t] = sum[0][t-1] + grid[0][t]
   *            = sum[t][0]                           y=q=0  t=max(x, p) sum[t][0] = sum[t-1][0] + grid[t][0]
   *            = max(f[x][y-1][p-1][q],f[x][y-1][p][q-1] 
   *                  , f[x-1][y][p-1][q],f[x-1][y][p][q-1]) 
   *                  + grid[x][y] + (x=p&&y=q)? 0 : grid[p][q]            
   *
   * Time O(M*M*N*N)  Space O(M*M*N*N) 
   */
  public int maxPathSum_two(int[][] grid) {
    
    //check
    if(grid == null || grid.length == 0 )
      return 0;
    
    int m = grid.length; // row
    int n = grid[0].length;  //column
    int[][][][] dp = new int[m + 1][n + 1][m + 1][n + 1];
    dp[1][1][1][1] = grid[0][0];
    
//    for(int row =2; row <= m; row ++){
//      dp[row][1][1][1] = dp[row-1][1][1][1] +  grid[row-1][0];
//      
//      for(int p=1; p<=row; p++){
//        dp[row][1][p][1] = dp[row][1][1][1];
//        dp[p][1][row][1] = dp[row][1][1][1];
//      }  
//        
//    }
//    for(int col =2; col <= n; col ++){
//      dp[1][col][1][1] = dp[1][col-1][1][1] +  grid[0][col-1];
//      
//      for(int q=1; q<=col; q++){
//        dp[1][col][1][q] = dp[1][col][1][1];
//        dp[1][q][1][col] = dp[1][col][1][1];
//      }  
//    }
    
    for(int x = 1; x <= m; x ++ ) {
      for (int y = 1; y <= n; y++) {

        for (int p = 1; p <= m; p++) {
          for (int q = 1; q <= n; q++) {
            dp[x][y][p][q] = max(dp[x][y - 1][p][q - 1], dp[x - 1][y][p][q - 1], dp[x][y - 1][p - 1][q], dp[x - 1][y][p - 1][q])
                    + grid[x - 1][y - 1] + ((x == p && y == q) ? 0 : grid[p - 1][q - 1]);
          }
        }
      }
    }
    
    return dp[m][n][m][n];
  }

  private int max(int x1, int x2, int x3, int x4){
    return Math.max(Math.max(x1, x2), Math.max(x3, x4));
  }
  
  
  public int maxPathSum_two2(int[][] grid) {
    
    //check
    if(grid == null || grid.length == 0 )
      return 0;
    
    int m = grid.length; // row
    int n = grid[0].length;  //column
    int[][][][] dp = new int[m + 1][n + 1][m + 1][n + 1];
    dp[m-1][n-1][m-1][n-1] = grid[0][0];
    
//    for(int row=m-1; row >=0; row --){
//      dp[row][1][1][1] = dp[row-1][1][1][1] +  grid[row-1][0];
//      
//      for(int p=1; p<=row; p++){
//        dp[row][1][p][1] = dp[row][1][1][1];
//        dp[p][1][row][1] = dp[row][1][1][1];
//      }  
//        
//    }
//    for(int col =2; col <= n; col ++){
//      dp[1][col][1][1] = dp[1][col-1][1][1] +  grid[0][col-1];
//      
//      for(int q=1; q<=col; q++){
//        dp[1][col][1][q] = dp[1][col][1][1];
//        dp[1][q][1][col] = dp[1][col][1][1];
//      }  
//    }
    
    for(int x = m-1; x >= 0; x -- ) {
      for (int y = n - 1; y >= 0; y--) {
        for (int p = m - 1; p >= 0; p--) {
          for (int q = n - 1; q >= 0; q--) {
            dp[x][y][p][q] = max(dp[x][y + 1][p][q + 1], dp[x + 1][y][p][q + 1], dp[x][y + 1][p + 1][q], dp[x + 1][y][p + 1][q])
                    + grid[x][y] + ((x == p && y == q) ? 0 : grid[p][q]);
          }
        }
      }
    }
      
    
    return dp[0][0][0][0];
  }
  /**
   * @param args
   */
  public static void main(String[] args) {
    Square sv = new Square();
    
    int[][] matrix = {{3, 6, 7, 5}, {4, 9, 8, 11}, {12, 2, 10, 1}};

    System.out.println(sv.maxPathSum_two(matrix));
  }

}
