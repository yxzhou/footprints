package fgafa.matrix;

import java.util.LinkedList;

import fgafa.util.Misc;

/*
 * Given a 2D board containing 'X' and 'O', capture all regions surrounded by
 * 'X'. A region is captured by flipping all 'O's into 'X's in that surrounded
 * region . 
 * 
 * For example,
    X X X X 
    X O O X 
    X X O X 
    X O X X 
    
    After running your function, the board should be: 
    X X X X 
    X X X X 
    X X X X 
    X O X X
 */
public class SurroundedRegions
{
	/**
	 * Time O(n*n) Space O(n)
	 */
  public void solve(char[][] board) {
    // pre check
    if (board == null || board.length == 0 || board[0].length == 0)
      return;
    int rows = board.length, cols = board[0].length;

    // init
    boolean[][] isVisited = new boolean[rows][cols]; // default it's false

    // check, start from the 4 boundary line
    for (int firstRow = 0, lastRow = rows - 1, col = 0; col < cols; col++) {
      check_dfs(board, firstRow, col, isVisited, rows, cols);
      check_dfs(board, lastRow, col, isVisited, rows, cols);
    }
    for (int firstCol = 0, lastCol = cols - 1, row = 0; row < rows; row++) {
      check_dfs(board, row, firstCol, isVisited, rows, cols);
      check_dfs(board, row, lastCol, isVisited, rows, cols);
    }

    // set the nonvisited cell as 1
    for (int row = 1; row < rows; row++)
      // we can ignore the 4 board line
      for (int col = 1; col < cols; col++)
        if (!isVisited[row][col] && board[row][col] == 'O')
          board[row][col] = 'X';

  }



  private void check_dfs(char[][] board, int row, int col,
      boolean[][] isVisited, int rows, int cols) {

    if (board[row][col] == 'X'
        || (isVisited[row][col] && board[row][col] == 'O'))
      return;

    isVisited[row][col] = true;

    //to avoid double-check the 4 edges
    if (row > 1) // it's 1 instead of 0, 
      check_dfs(board, row - 1, col, isVisited, rows, cols);
    if (row < rows - 2)
      check_dfs(board, row + 1, col, isVisited, rows, cols);
    if (col > 1)
      check_dfs(board, row, col - 1, isVisited, rows, cols);
    if (col < cols - 2)
      check_dfs(board, row, col + 1, isVisited, rows, cols);

  }


	/**
	 * Time O(n*n) Space O(1)
	 * 
	 * recursive,  stackOverFlow when matrix is too big
	 */
	public void solve_n(char[][] board) {
		if (null == board || 3 > board.length || 3 > board[0].length) {
			return;
		}
		// assume it's 2D matrix
		int rowNum = board.length;
		int colNum = board[0].length;
		// start from the bourdary
		for (int colIndex = 0; colIndex < colNum; colIndex++) {
			dfs(board, 0, colIndex);
			dfs(board, rowNum - 1, colIndex);
		}
		for (int rowIndex = 1; rowIndex < rowNum - 1; rowIndex++) {
			dfs(board, rowIndex, 0);
			dfs(board, rowIndex, colNum - 1);
		}

		// change 'O' to 'X' and change 'Y' to 'O'
		for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {
			for (int colIndex = 0; colIndex < colNum; colIndex++) {
				if ('O' == board[rowIndex][colIndex]) {
					board[rowIndex][colIndex] = 'X';
				} else if ('Y' == board[rowIndex][colIndex]) {
					board[rowIndex][colIndex] = 'O';
				}
			}
		}

	}

	private void dfs(char[][] board, int rowIndex, int colIndex) {
		if (rowIndex >= 0 && rowIndex < board.length && colIndex >= 0
				&& colIndex < board[0].length) {
			if ('O' == board[rowIndex][colIndex]) {
				board[rowIndex][colIndex] = 'Y';

				dfs(board, rowIndex - 1, colIndex);
				dfs(board, rowIndex + 1, colIndex);
				dfs(board, rowIndex, colIndex - 1);
				dfs(board, rowIndex, colIndex + 1);
			}
		}
	}
  
	/**
	 * wrong,  
	 * 
	 * Time O(n*n) Space O(1)
	 */
	public void solve_n2_wrong(char[][] board) {
		// assume it's 2D matrix
		if (null == board || 3 > board.length || 3 > board[0].length) {
			return;
		}

		int rowNum = board.length;
		int colNum = board[0].length;
		// start from the boundary
		for(int topLeft = 0, bottomRightX = rowNum -1, bottomRightY = colNum - 1; 
				topLeft <= bottomRightX && topLeft <= bottomRightY; 
				topLeft ++, bottomRightX --, bottomRightY --){
			for (int colIndex = topLeft; colIndex <= bottomRightY; colIndex++) {
				if( 'O' == board[topLeft][colIndex] && ( 0 == topLeft || 'Y' == board[topLeft - 1][colIndex] )){
					board[topLeft][colIndex] = 'Y';	
				}
				if( 'O' == board[bottomRightX][colIndex] && ( rowNum - 1 == bottomRightX || 'Y' == board[bottomRightX + 1][colIndex])){
					board[bottomRightX][colIndex] = 'Y';	
				}
			}
			for (int rowIndex = topLeft; rowIndex <= bottomRightX; rowIndex++) {
				if( 'O' == board[rowIndex][topLeft] && ( 0 == topLeft || 'Y' == board[rowIndex][topLeft - 1])){
					board[rowIndex][topLeft] = 'Y';	
				}
				if( 'O' == board[rowIndex][bottomRightY] && ( colNum - 1 == bottomRightY || 'Y' == board[rowIndex][bottomRightY + 1])){
					board[rowIndex][bottomRightY] = 'Y';	
				}
			}
			
			for (int colIndex = topLeft+1; colIndex <= bottomRightY; colIndex++) {
				if('O' == board[topLeft][colIndex] && 'Y' == board[topLeft][colIndex-1] ){
					board[topLeft][colIndex] = 'Y';	
				}
				if('O' == board[bottomRightX][colIndex] && 'Y' == board[bottomRightX][colIndex-1] ){
					board[bottomRightX][colIndex] = 'Y';	
				}
			}
			for (int colIndex = bottomRightY -1 ; colIndex >= topLeft; colIndex--) {
				if('O' == board[topLeft][colIndex] && 'Y' == board[topLeft][colIndex+1] ){
					board[topLeft][colIndex] = 'Y';	
				}
				if('O' == board[bottomRightX][colIndex] && 'Y' == board[bottomRightX][colIndex+1] ){
					board[bottomRightX][colIndex] = 'Y';	
				}
			}
			
			for (int rowIndex = topLeft+1; rowIndex <= bottomRightX; rowIndex++) {
				if('O' == board[rowIndex][topLeft] && 'Y' == board[rowIndex - 1][topLeft] ){
					board[rowIndex][topLeft] = 'Y';	
				}
				if('O' == board[rowIndex][bottomRightY] &&  'Y' == board[rowIndex - 1][bottomRightY] ){
					board[rowIndex][bottomRightY] = 'Y';	
				}
			}
			for (int rowIndex = bottomRightX-1; rowIndex >= topLeft; rowIndex--) {
				if('O' == board[rowIndex][topLeft] && 'Y' == board[rowIndex + 1][topLeft] ){
					board[rowIndex][topLeft] = 'Y';	
				}
				if('O' == board[rowIndex][bottomRightY] && 'Y' == board[rowIndex + 1][bottomRightY] ){
					board[rowIndex][bottomRightY] = 'Y';	
				}
			}
		}

		// change 'O' to 'X' and change 'Y' to 'O'
		for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {
			for (int colIndex = 0; colIndex < colNum; colIndex++) {
				if ('O' == board[rowIndex][colIndex]) {
					board[rowIndex][colIndex] = 'X';
				} else if ('Y' == board[rowIndex][colIndex]) {
					board[rowIndex][colIndex] = 'O';
				}
			}
		}
	}	
	
	/**
	 * 
	 * @param board
	 */
	public void solve_x(char[][] board) {
		if (null == board || 3 > board.length || 3 > board[0].length) {
			return;	
		}
		
		// it's 2D matrix
		int rowNum = board.length;
		int colNum = board[0].length;
		// start from the bourdary
		for (int colIndex = 0; colIndex < colNum; colIndex++) {
			bfs(board, 0, colIndex);
			bfs(board, rowNum - 1, colIndex);
		}
		for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {
			bfs(board, rowIndex, 0);
			bfs(board, rowIndex, colNum - 1);
		}

		// change 'O' to 'X' and change 'Y' to 'O'
		for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {
			for (int colIndex = 0; colIndex < colNum; colIndex++) {
				if ('O' == board[rowIndex][colIndex]) {
					board[rowIndex][colIndex] = 'X';
				} else if ('Y' == board[rowIndex][colIndex]) {
					board[rowIndex][colIndex] = 'O';
				}
			}
		}
	}

	private void bfs(char[][] board, int rowIndex, int colIndex) {
		if ('O' != board[rowIndex][colIndex])
			return;
		
		board[rowIndex][colIndex] = 'Y';
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		int colNum = board[0].length;
		
		int code = rowIndex * colNum + colIndex;
		queue.add(code);
		while (!queue.isEmpty()) {
			code = queue.pop();
			int row = code / colNum;
			int col = code % colNum;
			if (row > 0 && board[row - 1][col] == 'O') {
				queue.add((row - 1) * colNum + col);
				board[row - 1][col] = 'Y';
			}
			if (row < board.length - 1 && board[row + 1][col] == 'O') {
				queue.add((row + 1) * colNum + col);
				board[row + 1][col] = 'Y';
			}
			if (col > 0 && board[row][col - 1] == 'O') {
				queue.add(row * colNum + col - 1);
				board[row][col - 1] = 'Y';
			}
			if (col < board[0].length - 1 && board[row][col + 1] == 'O') {
				queue.add(row * colNum + col + 1);
				board[row][col + 1] = 'Y';
			}
		}
	}
	
	  /**
	   * @param args
	   */
	  public static void main(String[] args) {
		  String[][] input = {
				  {"XOXOXO","OXOXOX","XOXOXO","OXOXOX"},
				  {"XOXX","OXOX","XOXO","OXOX","XOXO","OXOX"},
				  {"OXOOOX","OOXXXO","XXXXXO","OOOOXX","XXOOXO","OOXXXX"}
		  };
		  
		  SurroundedRegions sv = new SurroundedRegions();
		  
		  for(String[] strs : input){
			  System.out.println();
			  char[][] matrix = sv.buildMetrix(strs);
			  Misc.printMetrix(matrix);
			  
			  sv.solve(matrix);
			  Misc.printMetrix(matrix);
			  
			  matrix = sv.buildMetrix(strs);
			  sv.solve_n(matrix);
			  Misc.printMetrix(matrix);
			  
			  matrix = sv.buildMetrix(strs);
			  sv.solve_n2_wrong(matrix);
			  Misc.printMetrix(matrix);
			  
			  matrix = sv.buildMetrix(strs);
			  sv.solve_x(matrix);
			  Misc.printMetrix(matrix);
			  
		  }


	  }
	  
	  private char[][] buildMetrix(String[] strs){
		  if(null == strs || 0 == strs.length || 0 == strs[0].length())
			  return null;
		  
		  char[][] matrix = new char[strs.length][strs[0].length()];
		  for(int i=0; i< strs.length; i++){
			  matrix[i] = strs[i].toCharArray();
		  }
		  
		  return matrix;
	  }

}
