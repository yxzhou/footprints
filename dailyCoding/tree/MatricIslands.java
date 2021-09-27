package dailyCoding.tree;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Given a matrix of 1s and 0s, return the number of "islands" in the matrix. A 1 represents land and 0 represents water, so an island is a group of 1s that are neighboring and their perimiter is surrounded by water.

 For example, this matrix has 4 islands.

 1 0 0 0 0
 0 0 1 1 0
 0 1 1 0 0
 0 0 0 0 0
 1 1 0 0 1
 1 1 0 0 1

 *
 * Tags: amazon
 *
 */

public class MatricIslands {

    public int islands(int[][] matrix){
        if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
            return 0;
        }

        int rowNum = matrix.length;
        int columnNum = matrix[0].length;
        boolean[][] visited = new boolean[rowNum][columnNum];

        int count = 0;
        for(int i = 0; i < rowNum; i++){
            for(int j = 0; j < columnNum; j++){
                if(matrix[i][j] == 1 && !visited[i][j]){
                    dfs(matrix, visited, i, j);
                    count++;
                }
            }
        }

        return count;
    }

    private void dfs(int[][] matrix, boolean[][] visited, int i, int j){
        if(i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length){
            return;
        }

        if(0 == matrix[i][j] || visited[i][j]){
            return;
        }

        visited[i][j] = true;

        dfs(matrix, visited, i + 1, j);
        dfs(matrix, visited, i - 1, j);
        dfs(matrix, visited, i, j + 1);
        dfs(matrix, visited, i, j - 1);
    }

    @Test public void test(){

        int[][] matrix = {
                {1, 0, 0, 0, 0},
                {0, 0, 1, 1, 0},
                {0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {1, 1, 0, 0, 1},
                {1, 1, 0, 0, 1}
        };

        Assert.assertEquals(4, islands(matrix));

    }

}
