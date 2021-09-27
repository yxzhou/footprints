package dp.matrix;

import org.junit.Assert;
import org.junit.Test;

import java.util.BitSet;

/**
 * Leetcode #980
 *
 * On a 2-dimensional grid, there are 4 types of squares:
 * 1 represents the starting square.  There is exactly one starting square.
 * 2 represents the ending square.  There is exactly one ending square.
 * 0 represents empty squares we can walk over.
 * -1 represents obstacles that we cannot walk over.
 *
 * Return the number of 4-directional walks from the starting square to the ending square, that walk over every non-obstacle square exactly once.
 *
 *
 *
 * Example 1:
 * Input: [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
 * Output: 2
 * Explanation: We have the following two paths:
 * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
 * 2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)
 *
 * Example 2:
 * Input: [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
 * Output: 4
 * Explanation: We have the following four paths:
 * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
 * 2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
 * 3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
 * 4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)
 *
 * Example 3:
 * Input: [[0,1],[2,0]]
 * Output: 0
 * Explanation:
 * There is no path that walks over every empty square exactly once.
 *
 * Note that the starting and ending square can be anywhere in the grid.
 *
 * Note:
 * 1 <= grid.length * grid[0].length <= 20
 *
 */

public class UniquePathsIII {

    public int uniquePathsIII(int[][] grid) {
        int r = grid.length;
        int c = grid[0].length;

        BitSet target = new BitSet();
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                if(grid[i][j] != -1){
                    target.set(i * c + j, true);
                }
            }
        }

        //System.out.println(target.toString());

        int[] result = new int[1];
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                if(grid[i][j] == 1){
                    dfs(grid, i, j, target, new BitSet(), result);
                }
            }
        }

        return result[0];
    }

    static int[][] diffs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private void dfs(int[][] grid, int i, int j, BitSet target, BitSet state, int[] result ){
        if(i < 0 || i == grid.length || j < 0 || j == grid[0].length || grid[i][j] == -1){
            return;
        }

        int index = i * grid[0].length + j;

        if(state.get(index)){
            return;
        }

        state.set(index, true);

        if(grid[i][j] == 2){
            //System.out.println(state.toString());

            if(target.equals(state)){
                result[0]++;
            }
        }else{
            for(int[] diff : diffs){
                dfs(grid, i + diff[0], j + diff[1], target, state, result);
            }
        }

        state.set(index, false);
    }


    @Test public void test(){

        Assert.assertEquals(1, uniquePathsIII(new int[][]{{1,0},{2, 0}}));
        Assert.assertEquals(0, uniquePathsIII(new int[][]{{0,1},{2, 0}}));

        //Assert.assertEquals(2, uniquePathsIII(new int[][]{{1,0,0,0},{0,0,0,0},{0,0,2,-1}}) );

        Assert.assertEquals(4, uniquePathsIII(new int[][]{{1,0,0,0},{0,0,0,0},{0,0,0,2}}));
    }

}
