package fgafa.dp.matrix.PathSum;

/**
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
 */

public class CherryPickupII {

    //todo

    public int cherryPickup(int[][] grid) {
        int N = grid.length;
        int[][][][] dp = new int[N + 1][N + 1][N + 1][N + 1]; //default all are 0

        for(int x = 1; x <= N; x++){
            for(int y = 1; y <= N; y++){
                if(grid[x - 1][y - 1] == -1){
                    continue;
                }

                for(int p = 1; p <= N; p++){
                    for(int q = 1; q <= N; q++){
                        if(grid[p - 1][q - 1] == -1){
                            continue;
                        }

                        dp[x][y][p][q] = max(dp[x - 1][y][p - 1][q], dp[x][y - 1][p][q -1], dp[x][y - 1][p - 1][q], dp[x - 1][y][p][q -1]) + grid[x - 1][y - 1] + ( x == p && y == q ? 0 : grid[p - 1][q - 1]);
                    }
                }

            }
        }


        return dp[N][N][N][N];
    }


    private int max(int a, int b, int c, int d){
        return Math.max(Math.max(Math.max(a, b), c), d);
    }
}
