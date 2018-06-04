package fgafa.game.TicTacToe;

/**
 * Requirement please see TicTacToe.java
 * 
 * This solution is more neat, 
 *    In TicTacToe.java, it allocates 2 arrays to 2 players. Here it only use 1 array. 
 *    The point is that players number is 2, it cannot "saving" if player number is 3 or more.
 *
 */


public class TicTacToe2 {

    int[] rowStatus;
    int[] colStatus;
    int[] diagStatus; // diag and xdiag
    
    int n;
    
    public TicTacToe2(int n){
        rowStatus = new int[n]; // rowStatus[1] is the row status in second row;
        colStatus = new int[n];
        diagStatus = new int[2];
        
        this.n = n;
    }
    
    /** Player {player} makes a move at ({row}, {col}).
    @param row The row of the board.
    @param col The column of the board.
    @param player The player, can be either 0 or 1.
    @return The current winning condition, can be either:
            0: No one wins.
            1: Player 1 wins.
            2: Player 2 wins. */
    public int move(int row, int col, int player) {
        int count = (player == 0)? 1 : -1;
        
        rowStatus[row] += count;
        colStatus[col] += count;
        
        if(row == col){
            diagStatus[0] += count;
        }
        if(row + col == n - 1){
            diagStatus[1] += count;
        }
        
        if (Math.abs(rowStatus[row]) == n 
                    || Math.abs(colStatus[col]) == n
                    || Math.abs(diagStatus[0]) == n
                    || Math.abs(diagStatus[1]) == n) {
            return player + 1;
        }
        
        return 0;
    }
    
}
