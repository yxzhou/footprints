package dp.matrix.PathSum;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 *
 * Leetcode #741
 *
 * In a N x N grid representing a field of cherries, each cell is one of three possible integers.
 0 means the cell is empty, so you can pass through;
 1 means the cell contains a cherry, that you can pick up and pass through;
 -1 means the cell contains a thorn that blocks your way.


 Your task is to collect maximum number of cherries possible by following the rules below:
 1) Starting at the position (0, 0) and reaching (N-1, N-1) by moving right or down through valid path cells (cells with value 0 or 1);
 2) After reaching (N-1, N-1), returning to (0, 0) by moving left or up through valid path cells;
 3) When passing through a path cell containing a cherry, you pick it up and the cell becomes an empty cell (0);
 4) If there is no valid path between (0, 0) and (N-1, N-1), then no cherries can be collected.
 *
 *
 *
 *
 *
 *
 */


public class CherryPickupII {

    /**
     *
     * from up-left to bottom-right
     *
     * Time O(n^3), Space O(n^4)
     *
     */
    public int cherryPickup(int[][] grid) {
        int N = grid.length;
        int[][][][] f = new int[N + 1][N + 1][N + 1][N + 1]; //default all are 0
        for(int[][][] a1 : f){
            for(int[][] a2 : a1){
                for(int[] a3 : a2){
                    Arrays.fill(a3, -1);
                }
            }
        }
        f[0][1][0][1] = 0;

        int max;
        for(int t = 1, end = N * 2; t < end; t++){
            for(int x = Math.max(1, t - N + 1), y = t - x + 1; x <= N && y > 0; x++, y--){
                for(int p = Math.max(1, t - N + 1), q = t - p + 1; p <= N & q > 0; p++, q--){
                    if(grid[x - 1][y - 1] == -1 || grid[p - 1][q - 1] == -1){
                        continue;
                    }

                    max = Math.max(Math.max(f[x - 1][y][p - 1][q], f[x - 1][y][p][q - 1]), Math.max(f[x][y - 1][p - 1][q], f[x][y - 1][p][q - 1]));

                    if(max > -1){
                        f[x][y][p][q] = max + grid[x - 1][y - 1] + ( x == p ? 0 : grid[p - 1][q - 1]);
                    }
                }
            }
        }

        return Math.max(f[N][N][N][N], 0);
    }

    /**
     *
     * from up-left to bottom-right
     *
     * Time O(n^3), Space O(n^2)
     *
     *
     * Thought:
     *   Suppose there are 2 persons pick the cherry from the (0, 0) to (N-1, N-1).
     *   At 1st move, these 2 person can be moved to
     *     {(0, 1), (0,1)}
     *     {(0, 1), (1,0)}
     *     {(1, 0), (0,1)}
     *     {(1, 0), (1,0)}
     *
     *   From top-left to bottom-right, total it need move  N * 2 - 2 times.
     *
     *
     */
    public int cherryPickup_n(int[][] grid) {
        int N = grid.length;
        int[][] f = new int[N][N]; //default all are 0
        for(int[] row : f){
            Arrays.fill(row, -1);
        }

        //f
        for(int t = 1, end = N * 2 - 1; t < end; t++){
            int[][] fn = new int[N][N]; //default all are 0
            for(int[] row : fn){
                Arrays.fill(row, -1);
            }

            //to person1, the position (x, y),  x + y == t and x < N and y > 0
            for(int x = Math.max(0, t - N + 1), y = t - x; x < N && y >= 0; x++, y--){
                // same to the person2, (p, q)
                for(int p = Math.max(0, t - N + 1), q = t - p; p < N & q >= 0; p++, q--){

                    if(grid[x][y] == -1 || grid[p][q] == -1){
                        continue;
                    }

                    //max = Math.max(Math.max(f[x - 1][p - 1], f[x - 1][p]), Math.max(f[x][p - 1], f[x][p]));
                    int max = -1;
                    for(int px = x - 1; px <= x; px++){
                        for(int pp = p - 1; pp <= p; pp++ ){
                            if(px >= 0 && pp >= 0){
                                max = Math.max(max, f[px][pp]);
                            }
                        }
                    }

                    if(max > -1){
                        fn[x][p] = max + grid[x][y] + ( x == p ? 0 : grid[p][q]);
                    }
                }
            }

            f = fn;
        }

        return Math.max(f[N-1][N-1], 0);
    }


    @Test
    public void test(){

        //Functions func = this.cherryPickup();

        int[][] grid = {
                {0,1,-1},
                {1,0,-1},
                {1,1,1}};

        Assert.assertEquals(5, cherryPickup(grid));

        grid = new int[][]{
                {1,1,-1},
                {1,-1,1},
                {-1,1,1}};
        Assert.assertEquals(0, cherryPickup(grid));

        grid = new int[][]{
                { 1, -1, 1},
                {-1, 1, 1},
                { 1, 1, 1}};
        Assert.assertEquals(0, cherryPickup(grid));

        grid = new int[][]{
                { 1,-1, 1,-1, 1, 1, 1, 1, 1,-1},
                {-1, 1, 1,-1,-1, 1, 1, 1, 1, 1},
                { 1, 1, 1,-1, 1, 1, 1, 1, 1, 1},
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {-1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                { 1,-1, 1, 1, 1, 1,-1, 1, 1, 1},
                { 1, 1, 1,-1, 1, 1,-1, 1, 1, 1},
                { 1,-1, 1,-1,-1, 1, 1, 1, 1, 1},
                { 1, 1,-1,-1, 1, 1, 1,-1, 1,-1},
                { 1, 1,-1, 1, 1, 1, 1, 1, 1, 1}};

        Assert.assertEquals(0, cherryPickup(grid));

    }
}
