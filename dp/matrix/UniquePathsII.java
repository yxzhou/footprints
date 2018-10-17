package fgafa.dp.matrix;
/*
 * Follow up for "Unique AvoidRoads":
 * 
 * Now consider if some obstacles are added to the grids. How many unique paths would there be?
 * 
 * An obstacle and empty space is marked as 1 and 0 respectively in the grid.
 * 
 * For example,
 * There is one obstacle in the middle of a 3x3 grid as illustrated below.
 * 
 * [
 *   [0,0,0],
 *   [0,1,0],
 *   [0,0,0]
 * ]
 * The total number of unique paths is 2.
 * 
 * Note: m and n will be at most 100.
 */
public class UniquePathsII
{
  /*
   * Define f(m,n) as the possible unique paths.
   * f(m,n) = 1   m = 1 or n=1 
   *        = f(m-1, n) + f(m, n-1)  
   * 
   */
  public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    int rows = obstacleGrid.length;
    int cols = obstacleGrid[0].length;
        
    int[][] pathNum = new int[rows][cols];  // all pathNum[i][j] are 0
    
    for(int row=0; row< rows; row++){
      if(obstacleGrid[row][0] == 0)
        pathNum[row][0] = 1;
      else
        break;
    }
    for(int col=0; col< cols; col++){
      if(obstacleGrid[0][col] == 0)
        pathNum[0][col] = 1;
      else
        break;
    }
    
    for(int row=1; row< rows; row++){
      for(int col=1; col< cols; col++){
        if(obstacleGrid[row][col] == 0)
          pathNum[row][col] = pathNum[row - 1][col] + pathNum[row][col - 1];
        
      }
    }
    
    return pathNum[rows-1][cols-1];
  }
  
  /**
   * @param obstacleGrid: A list of lists of integers
   * @return: An integer
   */
	public int uniquePathsWithObstacles_n(int[][] obstacleGrid) {
		// check
		if (null == obstacleGrid) {
			return 0;
		}

		// init
		int m = obstacleGrid.length;
		int n = obstacleGrid[0].length;
		
		if(0 == m || 0 == n || 1 == obstacleGrid[0][0]){
			return 0;
		}
		
		int[][] paths = new int[m][n]; // default all are 0

		for (int row = 0; row < m; row++) {
			if (0 == obstacleGrid[row][0]) {
				paths[row][0] = 1;
			}else{
				break;
			}
		}
		for (int col = 0; col < n; col++) {
			if (0 == obstacleGrid[0][col]) {
				paths[0][col] = 1;
			}else{
				break;
			}
		}

		// main
		for (int row = 1; row < m; row++) {
			for (int col = 1; col < n; col++) {
				if (0 == obstacleGrid[row][col]) {
					paths[row][col] = paths[row - 1][col] + paths[row][col - 1];
				}
			}
		}

		// return
		return paths[m - 1][n - 1];
	}
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
