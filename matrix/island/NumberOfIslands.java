package matrix.island;

/**
 * Number of Islands    _https://www.lintcode.com/problem/433
 *
 * Given a boolean 2D matrix, 0 is represented as the sea, 1 is represented as the island. If two 1 is adjacent, we consider them in the same island. We only consider up/down/left/right adjacent.
 *
 * Find the number of islands.
 * 
 * Example:
 * input: 
 *         {0,0,0,0},
 *         {0,1,0,0},
 *         {0,1,0,0},
 *         {0,1,1,0}
 * 
 * output:   1 
 * 
 * input: 
 *         {0,0,0,0},
 *         {0,1,0,0},
 *         {0,1,0,0},
 *         {0,0,1,0}
 * 
 * output:   2 
 *
 * tages:  dfs
 *
 */

class NumberOfIslands
{

    /**
     * @param grid: a boolean 2D matrix
     * @return: an integer
     */
    public int numIslands(boolean[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0){
            return 0;
        }

        int n = grid.length;
        int m = grid[0].length;
        boolean[][] visited = new boolean[n][m];

        int count = 0;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(grid[r][c] && !visited[r][c]){
                    dfs(grid, visited, r, c);
                    count++;
                }
            }
        }

        return count;
    }

    int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private void dfs(boolean[][] grid, boolean[][] visited, int r, int c){
        if(r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || !grid[r][c] || visited[r][c]){
            return;
        }

        visited[r][c] = true;

        for(int[] diff : diffs){
            dfs(grid, visited, r + diff[0], c + diff[1]);
        }
    }

  
}
