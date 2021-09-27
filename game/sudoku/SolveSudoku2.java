package game.sudoku;

import algs4.stdlib.In;

/**
 * For Leetcode #37
 * 
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 * 
 * Empty cells are indicated by the character '.'.
 * 
 * You may assume that there will be only one unique solution.
 */


public class SolveSudoku2 {

  static int N = 9;

  public void solveSudoku(char[][] board) {
    short[][] state = new short[3][N]; // state[0] - rows, state[1]-cols, state[2]-boxes

    char c;
    for(int i = 0; i < N; i++){
      for(int j = 0; j < N; j++){
        c = board[i][j];

        if(c != '.'){
          set(state, i, j, (i / 3) * 3 + j / 3, c - '1', true);
        }
      }
    }

    dfs(board, 0, 0, state);
  }

  private boolean dfs(char[][] board, int r, int c, short[][] state){
    if(c == N){
      r++;
      c = 0;
    }

    int b;
    int x;
    for(int i = r; i < N; i++){
      for(int j = (i == r? c : 0); j < N; j++){
        if(board[i][j] == '.'){
          b = (i / 3) * 3 + j / 3;
          x = state[0][i] | state[1][j] | state[2][b];

          for(int v = 0; v < N; v++ ){
            if( ((x >> v) & 1) == 0 ){
              set(state, i, j, b, v, true);
              board[i][j] = (char)('1' + v);

              if( dfs(board, i, j + 1, state) ){
                return true;
              }

              set(state, i, j, b, v, false);
            }
          }

          board[i][j] = '.';
          return false;
        }
      }
    }

    return true;
  }

  private void set(short[][] state, int i, int j, int p, int v, boolean b){
    int x = (1 << v);

    if(b){
      state[0][i] |= x;
      state[1][j] |= x;
      state[2][p] |= x;
    }else{
      x = ~x;

      state[0][i] &= x;
      state[1][j] &= x;
      state[2][p] &= x;
    }

  }




      /**
       * Print the specified Sudoku problem and its solution.  The
       * problem is encoded as specified in the class documentation
       * above.
       *
       * @param args The command-line arguments encoding the problem.
       */
      public static void main(String[] args) {
        SolveSudoku2 sv = new SolveSudoku2();

        char[][] matrix = new char[][]{{'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};

        writeMatrix(matrix);



        sv.solveSudoku(matrix); // solves in place
        writeMatrix(matrix);

      }

  private static char[][] parseProblem(In in) {
    char[][] problem = new char[9][9]; // default 0 vals
    int V = in.readInt();
    String tmp;
    for (int n = 0; n < V; ++n) {
      tmp = in.readString();
      int i = Integer.parseInt(tmp.substring(0, 1));
      int j = Integer.parseInt(tmp.substring(1, 2));
      int val = Integer.parseInt(tmp.substring(2, 3));
      problem[i][j] = (char) (val + '0');
    }
    return problem;
  }


  private static void writeMatrix(char[][] solution) {
    for (int i = 0; i < 9; ++i) {
      if (i % 3 == 0) {
        System.out.println("\n -----------------------");
      }

      for (int j = 0; j < 9; ++j) {
        if (j % 3 == 0) {
          System.out.print("| ");
        }
        System.out.print(solution[i][j] == '0' ? " " : solution[i][j]);

        System.out.print(' ');
      }
      System.out.println("|");
    }
    System.out.println(" -----------------------");
  }

}
