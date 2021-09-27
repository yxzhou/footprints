package matrix.Islands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Number of Islands II
 *
 * Given a n,m which means the row and column of the 2D matrix and an array of pair A( size k).
 * Originally, the 2D matrix is all 0 which means there is only sea in the matrix.
 * The list pair has k operator and each operator has two integer A[i].x, A[i].y means that you can change the grid matrix[A[i].x][A[i].y] from sea to island.
 * Return how many island are there in the matrix after each operator.You need to return an array of size K.
 *
 * 0 is represented as the sea, 1 is represented as the island. If two 1 is adjacent, we consider them in the same island.
 * We only consider up/down/left/right adjacent.
 *
 *
 * Example:
 Given m = 3, n = 3, positions = [[0,0], [0,1], [1,2], [2,1]].

 Initially, the 2d grid grid is filled with water. (Assume 0 represents water and 1 represents land).
 0 0 0
 0 0 0
 0 0 0

 Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land.
 1 0 0
 0 0 0   Number of islands = 1
 0 0 0

 Operation #2: addLand(0, 1) turns the water at grid[0][1] into a land.
 1 1 0
 0 0 0   Number of islands = 1
 0 0 0

 Operation #3: addLand(1, 2) turns the water at grid[1][2] into a land.
 1 1 0
 0 0 1   Number of islands = 2
 0 0 0

 Operation #4: addLand(2, 1) turns the water at grid[2][1] into a land.
 1 1 0
 0 0 1   Number of islands = 3
 0 1 0

 We return the result as an array: [1, 1, 2, 3]

 Challenge:
 Can you do it in time complexity O(k log mn), where k is the length of the positions?

 tags:  union find

 */
public class NumberOfIslandsII {
    /**
     * Definition for a point.
     */
    class Point {
        int x;
        int y;

        Point() {
            x = 0;
            y = 0;
        }

        Point(int a, int b) {
            x = a;
            y = b;
        }
    }


    /**
     * @param n: An integer
     * @param m: An integer
     * @param operators: an array of point
     * @return: an integer array
     */
    public List<Integer> numIslands2(int n, int m, Point[] operators) {
        if(operators == null){
            return Collections.EMPTY_LIST;
        }

        int[][] islands = new int[n][m];
        int count = 0;
        int index = 1;
        List<Integer> result = new ArrayList<>(operators.length);

        for(Point p : operators){
            if(islands[p.x][p.y] == 0){
                count += 1 - connected(islands, p.x, p.y, index++);
            }

            result.add(count);
        }

        return result;
    }

    int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private int connected(int[][] islands, int r, int c, int index){
        int max = 0;

        int nr;
        int nc;
        for(int[] diff : diffs){
            nr = r + diff[0];
            nc = c + diff[1];

            if(nr >= 0 && nr < islands.length && nc >= 0 && nc < islands[0].length && islands[nr][nc] > 0 && islands[nr][nc] != index){
                max = Math.max(max, islands[nr][nc]);
            }
        }

        int count = 0;
        if(max == 0){
            islands[r][c] = index;
        }else{
            count = 1;

            for(int[] diff : diffs){
                nr = r + diff[0];
                nc = c + diff[1];

                if(nr >= 0 && nr < islands.length && nc >= 0 && nc < islands[0].length && islands[nr][nc] > 0 && islands[nr][nc] != max){
                    count++;

                    dfs(islands, nr, nc, islands[nr][nc], max);
                }
            }

            islands[r][c] = max;
        }

        return count;
    }

    private void dfs(int[][] islands, int r, int c, int curr, int next){
        if(r < 0 || r >= islands.length || c < 0 || c >= islands[0].length || islands[r][c] != curr){
            return;
        }

        islands[r][c] = next;

        for(int[] diff : diffs){
            dfs(islands, r + diff[0], c + diff[1], curr, next);
        }
    }


}
