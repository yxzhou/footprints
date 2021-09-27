package dfsbfs.wordSearch;

/**
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
    public boolean exist(char[][] board, String word) {
      if(null == board || null == word){
          return false;
      }

      int rowsNum = board.length;
      int colsNum = board[0].length;

      boolean[][] visited = new boolean[rowsNum][colsNum]; //default all are false
      char[] target = word.toCharArray();

      for(int row = 0; row < rowsNum; row++){
          for(int col = 0; col < colsNum; col++){
              if(dfs(board, row, col, target, 0, visited)){
                  return true;
              }
          }
      }

      return false;
    }

    static int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private boolean dfs(char[][] board, int row, int col, char[] word, int i, boolean[][] visited){

        if(row < 0 || col < 0 || row >= board.length || col >= board[0].length || word[i] != board[row][col] || visited[row][col]){
            return false;
        }

        if(i == word.length - 1){
            return true;
        }

        visited[row][col] = true;

        for(int[] diff : diffs){
            if(dfs(board, row + diff[0], col + diff[1], word, i + 1, visited)){
                return true;
            }
        }

        visited[row][col] = false;

        return false;
    }



    /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
