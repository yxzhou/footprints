package matrix;

/**
 * 
 * :https://leetcode.com/problems/bomb-enemy/

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

    public int maxKilledEnemies(char[][] grid) { 
        if(null == grid || 0 == grid.length || 0 == grid[0].length){
            return 0;
        }
        
        int max = 0;
        int[] upToDown = new int[grid[0].length];
        int leftToRight = -1;
        
        for(int row = 0; row < grid.length; row++){
            for(int col = 0; col < grid[0].length; col++){
                if(row == 0 || 'W' == grid[row][col]){
                    upToDown[col] = countVerticalEnemy(grid, row + 1, col);
                }
                
                if(col == 0 || 'W' == grid[row][col]){
                    leftToRight = countHorizonEnemy(grid, row, col + 1);
                }
                
                
                if('0' == grid[row][col]){
                    max = Math.max(max, leftToRight + upToDown[col]);
                } 
            }
        }
        
        return max;
    }
    
    private int countHorizonEnemy(char[][] grid, int row, int col){
        int count = 0;
        
        for( ; col < grid[0].length; col++ ){
            if('E' == grid[row][col]){
                count++;
            }else if('W' == grid[row][col]){
                break;
            }
        }
        
        return count;
    }
    
    private int countVerticalEnemy(char[][] grid, int row, int col){
        int count = 0;
        
        for( ; row < grid.length; row++ ){
            if('E' == grid[row][col]){
                count++;
            }else if('W' == grid[row][col]){
                break;
            }
        }
        
        return count;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
