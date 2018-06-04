package fgafa.matrix; 

import java.util.LinkedList;

import fgafa.util.Misc;

/*
 * Find how many islands. 
 * 
 * Example:
 * input: 
 *         {0,0,0,0},
 *         {0,1,0,0},
 *         {0,1,0,0},
 *         {0,1,1,0}
 * 
 * output:   1 
 * 
 * input: 
 *         {0,0,0,0},
 *         {0,1,0,0},
 *         {0,1,0,0},
 *         {0,0,1,0}
 * 
 * output:   2 
 * 
 */

class Islands
{

  public int getIslandNum(int[][] matrix){
    int result = 0;
    //check the input
    if(matrix == null || matrix.length == 0)
      return result;

    int rowNum = matrix.length;
    int colNum = matrix[0].length;
    
    boolean[][] isVisited = new boolean[rowNum][colNum];
    for(int i=0; i<rowNum; i++)
      for(int j=0; j<colNum; j++)
        isVisited[i][j] = false;
    

    for(int i=0; i<rowNum; i++){
      for(int j=0; j<colNum; j++){
        if( !isVisited[i][j] && matrix[i][j] == 1){
          setVisited(matrix, isVisited, i, j);
          result ++;
        }  
      }
    }   
    
    return result;
  }
  
  private void setVisited(int[][] matrix, boolean[][] isVisited, int row, int col){
    
    if(!isVisited[row][col] && matrix[row][col] == 1){
      isVisited[row][col] = true;
      
      if(row < matrix.length -1)
        setVisited(matrix, isVisited, row+1, col);
      if(col < matrix[0].length -1)
        setVisited(matrix, isVisited, row, col + 1);  
      if(row > 0)
        setVisited(matrix, isVisited, row-1, col);
      if(col > 0)
        setVisited(matrix, isVisited, row, col-1);        
      
    }
  }
  
  
  public int numIslands(char[][] grid) {
	  int result = 0;
      if(null == grid || 0 == grid.length){
    	  return result;
      }
      
      boolean[][] isVisited = new boolean[grid.length][grid[0].length]; //default all are false;
      for(int i = 0; i < grid.length; i++){
    	  for(int j = 0; j< grid[0].length; j++){
    		  if(!isVisited[i][j] && '1' == grid[i][j]){
    			  result ++;
    			  bfs(isVisited, grid, i, j);
    		  }
    	  }
      }
    		  
      return result;
  }
  
  private void bfs(boolean[][] isVisited, char[][] grid, int rowIndex, int colIndex){
	  isVisited[rowIndex][colIndex] = true;
	  
	  LinkedList<Integer> queue = new LinkedList<>();
	  final int N = grid[0].length;
	  queue.add(rowIndex * N + colIndex);
	  
	  while(!queue.isEmpty()){
		  rowIndex = queue.peek() / N;
		  colIndex = queue.peek() % N;
		  
		  if(rowIndex > 0 && !isVisited[rowIndex - 1][colIndex] && '1' == grid[rowIndex - 1][colIndex] ){
			  isVisited[rowIndex - 1][colIndex] = true;
			  queue.add( queue.peek() - N );
		  }
		  if(rowIndex < grid.length - 1 && !isVisited[rowIndex + 1][colIndex] && '1' == grid[rowIndex + 1][colIndex] ){
			  isVisited[rowIndex + 1][colIndex] = true;
			  queue.add( queue.peek() + N );
		  }
		  if(colIndex > 0 && !isVisited[rowIndex][colIndex - 1] && '1' == grid[rowIndex][colIndex - 1] ){
			  isVisited[rowIndex][colIndex - 1] = true;
			  queue.add( queue.peek() - 1 );
		  }
		  if(colIndex < grid[0].length - 1 && !isVisited[rowIndex][colIndex + 1] && '1' == grid[rowIndex][colIndex + 1] ){
			  isVisited[rowIndex][colIndex + 1] = true;
			  queue.add( queue.peek() + 1 );
		  }
		  
		  queue.pop();
	  }
  }
  
	/**
	 * @param grid
	 *            a boolean 2D matrix, false is represented as the sea, true is
	 *            represented as the island. If two 1 is adjacent, we consider
	 *            them in the same island. We only consider up/down/left/right
	 *            adjacent.
	 * @return an integer
	 */
  public int numIslands(boolean[][] grid) {
      //check
      if(null == grid || 0 == grid.length){
          return 0;
      }
      
      int rowsNum = grid.length;
      int colsNum = grid[0].length;
      boolean[][] isVisited = new boolean[rowsNum][colsNum];
      int count = 0;
      
      for(int i = 0; i < rowsNum; i++){
          for(int j = 0; j < colsNum; j++){
              if(!isVisited[i][j] && grid[i][j]){
                  count++;
                  dfs(grid, isVisited, i, j);
              }
          }
      }
      
      return count;
  }
  
  private void dfs(boolean[][] grid, boolean[][] isVisited, int i, int j){
      if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length){
          return;
      }
      
      if(!isVisited[i][j] && grid[i][j]){
          isVisited[i][j] = true;
          dfs(grid, isVisited, i - 1, j);
          dfs(grid, isVisited, i + 1, j);
          dfs(grid, isVisited, i, j - 1);
          dfs(grid, isVisited, i, j + 1);
      }
  }
  
  public int numIslands_2(boolean[][] grid) {
      //check
      if(null == grid || 0 == grid.length){
          return 0;
      }
      
      int rowsNum = grid.length;
      int colsNum = grid[0].length;
      int count = 0;
      
      for(int i = 0; i < rowsNum; i++){
          for(int j = 0; j < colsNum; j++){
              if(grid[i][j]){
                  count++;
                  dfs(grid, i, j);
              }
          }
      }
      
      return count;
  }
  
  private void dfs(boolean[][] grid, int i, int j){
      if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length){
          return;
      }
      
      if( grid[i][j]){
    	  grid[i][j] = false;
          dfs(grid, i - 1, j);
          dfs(grid, i + 1, j);
          dfs(grid, i, j - 1);
          dfs(grid, i, j + 1);
      }
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {

	    int[][][] matrix = {
	        null,
	        {{0}},
	        {{1}},
	        {
	        {0,0,0,0},
	        {0,1,0,0},
	        {0,1,0,0},
	        {0,0,1,0}
	        }, 
	        {
	        {0,0,0,0},
	        {0,1,0,0},
	        {0,1,0,0},
	        {1,1,1,0}
	        }, 
	        {
	        {0,0,0,0},
	        {0,1,0,1},
	        {0,1,0,1},
	        {1,1,1,1}
	        }
	    };

		Islands sv = new Islands();

		for (int i = 0; i < matrix.length; i++) {
			System.out.println(Misc.array2String(matrix[i]));
			System.out.println(sv.getIslandNum(matrix[i]));
		}
	
		System.out.println();
				
	    String[][] input = {
	    		{"1"},
	    		{"0"},
	    		{"101"},
	    		{"1","0","1"},
	    		{"1010", "0101"},
	    		{"0000","0100","0100","0010"},
	    		{"0000","0100","0100","1110"},
	    		{"0000","0101","0101","1111"}
	    };
    
		for (int i = 0; i < input.length; i++) {
			char[][] grid = sv.convert(input[i]);
			Misc.printMetrix(grid);
			System.out.println(sv.numIslands(grid));
		}
	}

	private char[][] convert(String[] strs) {
		char[][] grid = new char[strs.length][strs[0].length()];
		for (int i = 0; i < strs.length; i++) {
			grid[i] = strs[i].toCharArray();
		}

		return grid;
	}
  
}
