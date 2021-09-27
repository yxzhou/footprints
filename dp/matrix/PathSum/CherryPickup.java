package dp.matrix.PathSum;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * In a N x N grid representing a field of cherries, each cell is one of three possible integers.
 0 means the cell is empty, so you can pass through;
 1 means the cell contains a cherry, that you can pick up and pass through;
 -1 means the cell contains a thorn that blocks your way.


 Your task is to collect maximum number of cherries possible by following the rules below:
 1) Starting at the position (0, 0) and reaching (N-1, N-1) by moving right or down through valid path cells (cells with value 0 or 1);
 2) When passing through a path cell containing a cherry, you pick it up and the cell becomes an empty cell (0);
 3) If there is no valid path between (0, 0) and (N-1, N-1), then no cherries can be collected.
 *
 */
public class CherryPickup {
    public int cherryPickup(int[][] grid) {
        int N = grid.length;
        int[][] dp = new int[N][N]; //default all are 0
        dp[0][0] = grid[0][0];

        for(int i = 1; i < N; i++){
            if(dp[0][i - 1] == -1 || grid[0][i] == -1){
                dp[0][i] = -1;
            }else{
                dp[0][i] = dp[0][i - 1] + grid[0][i];
            }

            if(dp[i - 1][0] == -1 || grid[i][0] == -1){
                dp[i][0] = -1;
            }else{
                dp[i][0] = dp[0][i - 1] + grid[i][0];
            }
        }

        for(int x = 1; x < N; x++){
            for(int y = 1; y < N; y++){
                if(grid[x][y] == -1){
                    dp[x][y] = -1;
                    continue;
                }

                dp[x][y] = Math.max(dp[x - 1][y], dp[x][y - 1]);

                if(dp[x][y] > -1){
                    dp[x][y] += grid[x][y];
                }
            }
        }

        return dp[N - 1][N - 1];
    }


    public int cherryPickup_n(int[][] grid) {
        int N = grid.length;
        int[][] dp = new int[N][N]; //default all are 0
        dp[0][0] = grid[0][0];

        for(int i = 1; i < N; i++){
            if(dp[0][i - 1] == -1 || grid[0][i] == -1){
                dp[0][i] = -1;
            }else{
                dp[0][i] = dp[0][i - 1] + grid[0][i];
            }

            if(dp[i - 1][0] == -1 || grid[i][0] == -1){
                dp[i][0] = -1;
            }else{
                dp[i][0] = dp[0][i - 1] + grid[i][0];
            }
        }

        for(int x = 1; x < N; x++){
            for(int y = 1; y < N; y++){
                if(grid[x][y] == -1){
                    dp[x][y] = -1;
                    continue;
                }

                dp[x][y] = Math.max(dp[x - 1][y], dp[x][y - 1]);

                if(dp[x][y] > -1){
                    dp[x][y] += grid[x][y];
                }
            }
        }

        return dp[N - 1][N - 1];
    }

    @Test public void test(){
        int[][][] grids = {
            {
                {1, 1, -1},
                {1, -1, 1},
                {-1, 1, 1}
            },
            {
                {1, 1, -1},
                {1, -1, 1},
                {1, 1, 1}
            },
            {
                {1, 1, -1},
                {1, 1, 1},
                {-1, 1, 1}
            },
            {
                {0, 0, -1},
                {1, 0, 0},
                {-1, 1, 1}
            }
        };

        int[] expects = {
                -1,
                5,
                5,
                3
        };

        for(int i = 0;i < grids.length; i++){
            Assert.assertEquals(expects[i], cherryPickup(grids[i]));
        }
    }

}
