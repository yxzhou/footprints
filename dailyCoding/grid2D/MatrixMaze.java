package fgafa.dailyCoding.grid2D;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * Given this matrix, a start coordinate, and an end coordinate, return the minimum number of steps required to reach the end coordinate from the start.
 * If there is no possible path, then return null. You can move up, left, down, and right. You cannot move through walls. You cannot wrap around the edges of the board.

 For example, given the following board:

 [
 [f, f, f, f],
 [t, t, f, t],
 [f, f, f, f],
 [f, f, f, f],
 ]

 and start = (3, 0) (bottom left) and end = (0, 0) (top left), the minimum number of steps required to reach the end is 7,
 ince we would need to go through, (1, 2) because there is a wall everywhere else on the second row.
 *
 *
 * Tags: Google, DFS, BFS
 *
 */

public class MatrixMaze {

    public int matricMaze(boolean[][] maze, int[] startPoint, int[] endPoint){
        //check

        int rowNumber = maze.length;
        int colNumber = maze[0].length;

        int[][] steps = new int[rowNumber][colNumber];

        //init
        for(int i = 0; i < rowNumber; i++){
            for(int j = 0; j < colNumber; j++){
                if(maze[i][j]){
                    steps[i][j] = -1;
                }else{
                    steps[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        matricMaze_dfs(startPoint, endPoint, steps);
        //matricMaze_bfs(startPoint, endPoint, steps);

        if(steps[endPoint[0]][endPoint[1]] == -1 || steps[endPoint[0]][endPoint[1]] == Integer.MAX_VALUE){
            return -1;
        }else{
            return steps[endPoint[0]][endPoint[1]];
        }
    }

    final int[][] neighbors = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
    };

    private void matricMaze_dfs(int[] currPoint, int[] endPoint, int[][] steps){
        if(currPoint[0] == endPoint[0] && currPoint[1] == endPoint[1]){
            return;
        }

        for(int[] diff : neighbors){
            int neighborX = currPoint[0] + diff[0];
            int neighborY = currPoint[1] + diff[1];

            if(steps[currPoint[0]][currPoint[1]] + 1 < steps[neighborX][neighborY]){
                steps[neighborX][neighborY] = steps[currPoint[0]][currPoint[1]] + 1;

                int[] next = {neighborX, neighborY};
                matricMaze_dfs(next, endPoint, steps);
            }
        }
    }

    public void matricMaze_bfs(int[] startPoint, int[] endPoint, int[][] steps){

        Queue<int[]> queue = new LinkedList<>();
        queue.add(startPoint);
        int count = 0;

        while(!queue.isEmpty()){
            int[] currPoint = queue.poll();
            if(currPoint[0] == endPoint[0] && currPoint[1] == endPoint[1]){
                return;
            }

            count++;

            for(int[] diff : neighbors) {
                int neighborX = currPoint[0] + diff[0];
                int neighborY = currPoint[1] + diff[1];

                if (count < steps[neighborX][neighborY]) {
                    steps[neighborX][neighborY] = count;

                    int[] next = { neighborX, neighborY };
                    queue.add(next);
                }
            }
        }
    }


}
