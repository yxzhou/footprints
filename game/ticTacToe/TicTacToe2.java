package game.TicTacToe;

/**
 * Requirement please see TicTacToe.java
 * 
 * This solution is more neat, 
 *    In TicTacToe.java, it allocates 2 arrays to 2 players. Here it only use 1 array. 
 *    The point is that players number is 2, it cannot "saving" if player number is 3 or more.
 *
 */


public class TicTacToe2 {

    int n;
    int[] vs; // vertical states, 0 means all are player 1, n means all are player 2
    int[] hs; //horizontal states
    int[] ds; //diagonal and antidiagonal states

    int n1;

    /**
     * Initialize your data structure here.
     */
    public TicTacToe2(int n) {
        this.n = n;
        this.n1 = n - 1;

        vs = new int[n];
        hs = new int[n];
        ds = new int[2];
    }

    /**
     * Player {player} makes a move at ({row}, {col}).
     *
     * @param row    The row of the board.
     * @param col    The column of the board.
     * @param player The player, can be either 1 or 2.
     * @return The current winning condition, can be either:
     * 0: No one wins.
     * 1: Player 1 wins.
     * 2: Player 2 wins.
     */
    public int move(int row, int col, int player) {
        int diff = (player == 1 ? -1 : 1);

        vs[row] += diff;
        hs[col] += diff;

        if (row == col) {
            ds[0] += diff;
        }

        if (row + col == n1) {
            ds[1] += diff;
        }

        int sum = diff * n;
        if (vs[row] == sum || hs[col] == sum || ds[0] == sum || ds[1] == sum) {
            return player;
        }

        return 0;
    }
}