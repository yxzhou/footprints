package fgafa.design.others;

/**
 *
 * Design a Tic-tac-toe game that is played between two players on a n x n grid.
 *
 * You may assume the following rules:
 *
 * A move is guaranteed to be valid and is placed on an empty block.
 * Once a winning condition is reached, no more moves is allowed.
 * A player who succeeds in placing n of their marks in a horizontal, vertical, or diagonal row wins the game.
 * Example:
 * Given n = 3, assume that player 1 is "X" and player 2 is "O" in the board.
 *
 * TicTacToe toe = new TicTacToe(3);
 *
 * toe.move(0, 0, 1); -> Returns 0 (no one wins)
 * |X| | |
 * | | | |    // Player 1 makes a move at (0, 0).
 * | | | |
 *
 * toe.move(0, 2, 2); -> Returns 0 (no one wins)
 * |X| |O|
 * | | | |    // Player 2 makes a move at (0, 2).
 * | | | |
 *
 * toe.move(2, 2, 1); -> Returns 0 (no one wins)
 * |X| |O|
 * | | | |    // Player 1 makes a move at (2, 2).
 * | | |X|
 *
 * toe.move(1, 1, 2); -> Returns 0 (no one wins)
 * |X| |O|
 * | |O| |    // Player 2 makes a move at (1, 1).
 * | | |X|
 *
 * toe.move(2, 0, 1); -> Returns 0 (no one wins)
 * |X| |O|
 * | |O| |    // Player 1 makes a move at (2, 0).
 * |X| |X|
 *
 * toe.move(1, 0, 2); -> Returns 0 (no one wins)
 * |X| |O|
 * |O|O| |    // Player 2 makes a move at (1, 0).
 * |X| |X|
 *
 * toe.move(2, 1, 1); -> Returns 1 (player 1 wins)
 * |X| |O|
 * |O|O| |    // Player 1 makes a move at (2, 1).
 *
 * |X|X|X|
 * Follow up:
 * Could you do better than O(n2) per move() operation?
 *
 */

public class TicTacToe {

    int n;
    boolean[][] visited;

    int[] rowStatus;
    int[] colStatus;
    int diagonStatus = 0;
    int antiDiagonStatus = 0;

    /** Initialize your data structure here. */
    public TicTacToe(int n) {
        this.n = n;
        this.visited = new boolean[n][n]; //default all are false;

        this.rowStatus = new int[n]; //default all are 0
        this.colStatus = new int[n];
    }

    /** Player {player} makes a move at ({row}, {col}).
     @param row The row of the board.
     @param col The column of the board.
     @param player The player, can be either 1 or 2.
     @return The current winning condition, can be either:
     0: No one wins.
     1: Player 1 wins.
     2: Player 2 wins. */
    public int move(int row, int col, int player) {
        if(row < 0 || row >= n || col < 0 || col >= n || visited[row][col] || (player != 1 && player != 2)){
            return 0;
        }

        visited[row][col] = true;

        int diff = (player == 1? 1 : -1 ); // when player == 1

        rowStatus[row] += diff;
        colStatus[col] += diff;

        if(row == col){
            diagonStatus += diff;
        }

        if(row + col == n - 1){
            antiDiagonStatus += diff;
        }

        if(Math.abs(rowStatus[row]) == n || Math.abs(colStatus[col]) == n || Math.abs(diagonStatus) == n || Math.abs(antiDiagonStatus) == n){
            return player;
        }

        return 0;
    }
}

/**
 * Your TicTacToe object will be instantiated and called as such:
 * TicTacToe obj = new TicTacToe(n);
 * int param_1 = obj.move(row,col,player);
 */
