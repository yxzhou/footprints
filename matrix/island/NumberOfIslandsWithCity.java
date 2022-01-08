package matrix.island;

/**
 * Island City   _https://www.lintcode.com/problem/897
 *
 * Given a matrix of size n x m, the elements in the matrix are 0、1、2. 0 for the sea, 1 for the island, and 2 for the city on the island(You can assume that 2 is built on 1, ie 2 also represents the island).
 * If two 1 are adjacent, then these two 1 belong to the same island. Find the number of islands with at least one city.
 *
 * We only consider up, down, left and right as adjacent.
 * n <= 100，m <= 100.
 * You can assume that the four sides of the matrix are surrounded by the sea.
 *
 */

public class NumberOfIslandsWithCity {

    /**
     * @param grid: an integer matrix
     * @return: an integer
     */
    public int numIslandCities(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0){
            return 0;
        }

        int n = grid.length;
        int m = grid[0].length;

        int count = 0;
        boolean[][] visited = new boolean[n][m];

        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(!visited[r][c] && grid[r][c] > 0 && dfs(grid, visited, r, c)){
                    count++;
                }
            }
        }

        return count;
    }

    int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private boolean dfs(int[][] grid, boolean[][] visited, int r, int c){
        if(r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || visited[r][c] || grid[r][c] == 0){
            return false;
        }

        visited[r][c] = true;

        boolean foundCity = (grid[r][c] == 2);

        for(int[] diff : diffs){
            foundCity |= dfs(grid, visited, r + diff[0], c + diff[1]);
        }

        return foundCity;
    }


}
