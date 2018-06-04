package fgafa.game.wordSearch;

/*
 * Given a 2D board and a word, find if the word exists in the grid.
 * <br>
 * The word can be constructed from letters of sequentially adjacent cell,<br> 
 * where "adjacent" cells are those horizontally or vertically neighboring. <br>
 * The same letter cell may not be used more than once.<br>
 * <br>
 * For example,
 * Given board =
 * [
 *   ["ABCE"],
 *   ["SFCS"],
 *   ["ADEE"]
 * ]
 *<br>
 * word = "ABCCED", -> returns true,
 * word = "SEE", -> returns true,
 * word = "ABCB", -> returns false.
 * 
 * In fact, it's a graph travel.
 */
public class WordSearch
{

  /* */
  public boolean exist_dfs(char[][] board, String word) {
      if (word == null || word.length() == 0) {
          return true;
      }
      if (board == null) {
          return false;
      }

    int rows = board.length;
    int cols = board[0].length;
    boolean[][] isUsed = new boolean[rows][cols]; // default it's false;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (exist(board, row, col, word, 0, isUsed))
          return true;
      }
    }

    return false;
  }


  /* dfs */
  private boolean exist(char[][] board, int row, int col, String word,
      int start, boolean[][] isUsed) {
    //
	if(row > -1 && row < board.length && col > -1 && col < board[0].length && !isUsed[row][col]){
	    isUsed[row][col] = true;
	
	    if (board[row][col] == word.charAt(start)) {
	      if (start == word.length() - 1 
	          || exist(board, row - 1, col, word, start + 1, isUsed)
	          || exist(board, row + 1, col, word, start + 1, isUsed)
	          || exist(board, row, col - 1, word, start + 1, isUsed)
	          || exist(board, row, col + 1, word, start + 1, isUsed)){
		        return true;
	      }
	    }
	
	    isUsed[row][col] = false;  //back tracking
	}
	
    return false;
  }


  public boolean exist_2(char[][] board, String word) {
      if (word == null || word.length() == 0) {
          return true;
      }
      if (board == null) {
          return false;
      }
      
      for (int i = 0; i < board.length; i++) {
          for (int j = 0; j < board[0].length; j++) {
              if (board[i][j] == word.charAt(0)) {
                  if (search(board, word, i, j, 0)) {
                      return true;
                  }
              }
          }
      }
      return false;
  }

    private boolean search(char[][] board, String word, int i, int j, int start) {

        if (i > -1 && i < board.length && j > -1 && j < board[0].length && board[i][j] == word.charAt(start)) {
            board[i][j] = '#';

            boolean rst = start == word.length() - 1
                        || search(board, word, i, j - 1, start + 1)
                        || search(board, word, i, j + 1, start + 1)
                        || search(board, word, i + 1, j, start + 1)
                        || search(board, word, i - 1, j, start + 1);

            board[i][j] = word.charAt(start); // back tracking
            
            return rst;
        }

        return false;
    }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
