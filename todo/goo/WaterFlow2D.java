package fgafa.todo.goo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * Give a matrix m, m[i][j] means the altitude, and there are some points whose altitude is 0.
 * Return the points who can flow to all 0's.
 *
 *
 *
 */

public class WaterFlow2D {

    public List<int[]> waterflow2D(int[][] altitudes){
        if(null == altitudes || 0 == altitudes.length || 0 == altitudes[0].length){
            return new ArrayList<>();
        }

        int rowNum = altitudes.length;
        int columnNum = altitudes[0].length;
        boolean[][] visited = new boolean[rowNum][columnNum];

        int height = 0;
        for(int x = 0; x < rowNum; x++){
            for(int y = 0; y < columnNum; y++){
                if(altitudes[x][y] == 0){
                    int h = bfs(altitudes, visited, x, y);

                    if(height == 0 && h == 0){
                        height = 1;
                    } else if(height != 0 && h == 0){
                        return new ArrayList<>();
                    }

                    if(h > height){
                        height = h;
                    }
                }
            }
        }

        List<int[]> result = new ArrayList<>();
        for(int x = 0; x < rowNum; x++) {
            for (int y = 0; y < columnNum; y++) {
                if(visited[x][y] && altitudes[x][y] >= height){
                    result.add(new int[]{x, y});
                }
            }
        }

        return result;
    }

    static final int[][] diffs = {{1, 0},{-1, 0}, {0, 1}, {0, -1}};

    private int bfs(int[][] altitudes, boolean[][] visited, int x, int y){
        Queue<String> queue = new LinkedList<>();
        queue.add(buildKey(x, y));

        while(!queue.isEmpty()) {
            int[] point = splitKey(queue.poll());
            visited[point[0]][point[1]] = true;

            for(int[] next : diffs){
                if(altitudes[next[0]][next[1]] > altitudes[point[0]][point[1]]){

                    if(visited[next[0]][next[1]]){
                        return altitudes[next[0]][next[1]];
                    }

                    queue.add(buildKey(next[0], next[1]));
                }
            }
        }

        return 0;
    }

    private String buildKey(int x, int y){
        return x + " " + y;
    }

    private int[] splitKey(String key){
        int[] result = new int[2];
        String[] tokens = key.split(" ");
        result[0] = Integer.valueOf(tokens[0]);
        result[1] = Integer.valueOf(tokens[1]);
        return result;
    }
}
