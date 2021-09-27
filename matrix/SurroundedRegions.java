package matrix;

import util.Misc;

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
	 * Time O(n*n) Space O(1)
	 * 
	 * recursive,  stackOverFlow when matrix is too big
	 */
	public void solve_n(char[][] board) {
		if (null == board || 3 > board.length || 3 > board[0].length) {
			return;
		}

		int rowNum = board.length;
		int colNum = board[0].length;

		// check the cells on the boarder, change 'O' to 'Y'
		for (int colIndex = 0; colIndex < colNum; colIndex++) {
			dfs(board, 0, colIndex);
			dfs(board, rowNum - 1, colIndex);
		}
		for (int rowIndex = 1; rowIndex < rowNum - 1; rowIndex++) {
			dfs(board, rowIndex, 0);
			dfs(board, rowIndex, colNum - 1);
		}

		// check all the cells, change 'O' to 'X' and change 'Y' to 'O'
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

	//dfs and change change 'O' to 'Y'
	private void dfs(char[][] board, int rowIndex, int colIndex) {
		if (rowIndex < 0 || rowIndex >= board.length || colIndex < 0 || colIndex >= board[0].length || 'O' == board[rowIndex][colIndex]) {
			return;
		}

		board[rowIndex][colIndex] = 'Y';

		dfs(board, rowIndex - 1, colIndex);
		dfs(board, rowIndex + 1, colIndex);
		dfs(board, rowIndex, colIndex - 1);
		dfs(board, rowIndex, colIndex + 1);
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
			  
			  matrix = sv.buildMetrix(strs);
			  sv.solve_n(matrix);
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
