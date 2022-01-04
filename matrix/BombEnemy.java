package matrix;

/**
 * 
 * https://leetcode.com/problems/bomb-enemy/

 *  Given a 2D grid, each cell is either a wall 'W', an enemy 'E' or empty '0' (the number zero), 
    return the maximum enemies you can kill using one bomb.
 * 
    The bomb kills all the enemies in the same row and column from the planted point until it hits the wall 
    since the wall is too strong to be destroyed.
 * 
 *  Note that you can only put the bomb at an empty cell.
    
    Example:
    For the given grid
    
    0 E 0 0
    E 0 W E
    0 E 0 0
    
    return 3. (Placing a bomb at (1,1) kills 3 enemies)
 *
 */

public class BombEnemy {

    /*
     * @param grid: Given a 2D grid, each cell is either 'W', 'E' or '0'
     * @return an integer, the maximum enemies you can kill using one bomb
     */
    public int maxKilledEnemies(char[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0){
            return 0;
        }

        int n = grid.length;
        int m = grid[0].length;

        int max = 0;

        int[] numOnCol = new int[m];
        int numOnRow = 0;
        for(int r = 0; r < n; r++ ){
            for(int c = 0; c < m; c++){
                if(c == 0 || grid[r][c - 1] == 'W'){
                    numOnRow = checkOnRow(grid, r, c, m);
                }

                if(r == 0 || grid[r - 1][c] == 'W'){
                    numOnCol[c] = checkOnColumn(grid, r, c, n);
                }

                if(grid[r][c] == '0'){
                    max = Math.max( max, numOnRow + numOnCol[c] );
                }
            }
        }

        return max;
    }

    private int checkOnRow(char[][] grid, int r, int c, int m){
        int count = 0;
        while(c < m && grid[r][c] != 'W'){
            count += grid[r][c] == 'E' ? 1 : 0;
            c++;    
        }
        return count;
    }

    private int checkOnColumn(char[][] grid, int r, int c, int n){
        int count = 0;
        while(r < n && grid[r][c] != 'W'){
            count += grid[r][c] == 'E' ? 1 : 0;
            r++;
        }
        return count;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
