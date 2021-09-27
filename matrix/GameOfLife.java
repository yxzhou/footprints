package matrix;

/**
 * 
 * According to the Wikipedia's article: "The Game of Life, also known simply as Life, 
 * is a cellular automaton devised by the British mathematician John Horton Conway in 1970."

    Given a board with m by n cells, each cell has an initial state live (1) or dead (0). 
    Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using 
    the following four rules (taken from the above Wikipedia article):
        Any live cell with fewer than two live neighbors dies, as if caused by under-population.
        Any live cell with two or three live neighbors lives on to the next generation.
        Any live cell with more than three live neighbors dies, as if by over-population..
        Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
     
    
    Write a function to compute the next state (after one update) of the board given its current state.
    
    Follow up: 
    1) Could you solve it in-place? Remember that the board needs to be updated at the same time: 
    You cannot update some cells first and then use their updated values to update other cells.
    2) In this question, we represent the board using a 2D array. In principle, the board is infinite, 
    which would cause problems when the active area encroaches the border of the array. How would you address these problems?
 *
 */

public class GameOfLife {
    public void gameOfLife(int[][] board) {
        if(null == board || 0 == board.length || 0 == board[0].length){
            return;
        }
        
        int m = board.length;
        int n = board[0].length;
        
        int count = 0;
        int[] dx = {-1, -1, -1,  0, 0,  1, 1, 1};
        int[] dy = {-1,  0,  1, -1, 1, -1, 0, 1};
        int x;
        int y;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                 count = 0;
                 
                 for(int p = 0; p < dx.length; p++){
                     x = i + dx[p];
                     y = j + dy[p];
                     if(x >= 0 && x < m && y >= 0 && y < n){
                         if(board[x][y] == 2 || board[x][y] == 1){
                             count ++;
                         }   
                     }
                 }

                if(board[i][j] == 1){
                    if(count < 2 || count > 3){
                        board[i][j] = 2; // 1 -> 0   ==> 10
                    }//else{
//                        board[i][j] = 1;  // 1 -> 1
//                    }
                }else{
                    if(count == 3){
                        board[i][j] = 3; // 0 -> 1 ==> 01
                    }//else{
//                        board[i][j] = 0;  // 0 -> 0
//                    }
                }
                
            }
        }
        
        
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                board[i][j] &= 1;
            }
        }
    }
    
}
