package dfsbfs.distance;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Build Post Office II,  _https://www.lintcode.com/problem/573
 *
 * Given a 2D grid, each cell is either a wall 2, an house 1 or empty 0 (the number zero, one, two),
 * find a place to build a post office so that the sum of the distance from the post office to all the houses is smallest.
 *
 * Returns the sum of the minimum distances from all houses to the post office.Return -1 if it is not possible.
 *
 * Notes:
 * You cannot pass through wall and house, but can pass through empty.
 * You only build post office on an empty.
 *
 *
 */

public class PostOfficeII {

    /**
     * @param grid: a 2D grid
     * @return: An integer
     */

    int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    public int shortestDistance(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0){
            return -1;
        }

        int n = grid.length;
        int m = grid[0].length;

        int[][] sums = new int[n][m]; //sum[r][c] is the sum of the distance from [r, c] to all the house
        int[][] counts = new int[n][m]; //count[r][c] is how many house [r, c] can reach to
        boolean[][] visited = new boolean[n][m];;
        int houseNum = 0;
        Queue<Integer> queue = new LinkedList<>();
        int p, i, j, ni, nj;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(grid[r][c] == 1){
                    houseNum++;
                    queue.add(r * m + c);

                    for(int d = 1; !queue.isEmpty(); d++){
                        for(int k = queue.size(); k > 0; k--){
                            p = queue.poll();
                            i = p / m;
                            j = p % m;

                            for(int[] diff : diffs){
                                ni = i + diff[0];
                                nj = j + diff[1];

                                if(ni < 0 || ni >= n || nj < 0 || nj >= m || grid[ni][nj] != 0 || visited[ni][nj]){
                                    continue;
                                }

                                queue.add(ni * m + nj);
                                visited[ni][nj] = true;
                                sums[ni][nj] += d;
                                counts[ni][nj]++;
                            }
                        }
                    }

                    for(boolean[] v : visited){
                        Arrays.fill(v, false);
                    }
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(counts[r][c] == houseNum){
                    min = Math.min(min, sums[r][c]);
                }
            }
        }

        return min == Integer.MAX_VALUE? -1 : min;
    }

}
