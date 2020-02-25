package fgafa.graph.traversal;

import org.junit.Assert;
import org.junit.Test;

/**
 * Leetcode #959
 *
 * In a N x N grid composed of 1 x 1 squares, each 1 x 1 square consists of a /, \, or blank space.  These characters divide the square into contiguous regions.
 *
 * (Note that backslash characters are escaped, so a \ is represented as "\\".)
 *
 * Return the number of regions.
 *
 *
 *
 * Example 1:
 *
 * Input:
 * [
 *   " /",
 *   "/ "
 * ]
 * Output: 2
 * Explanation: The 2x2 grid is as follows:
 *
 * Example 2:
 *
 * Input:
 * [
 *   " /",
 *   "  "
 * ]
 * Output: 1
 * Explanation: The 2x2 grid is as follows:
 *
 * Example 3:
 *
 * Input:
 * [
 *   "\\/",
 *   "/\\"
 * ]
 * Output: 4
 * Explanation: (Recall that because \ characters are escaped, "\\/" refers to \/, and "/\\" refers to /\.)
 * The 2x2 grid is as follows:
 *
 * Example 4:
 *
 * Input:
 * [
 *   "/\\",
 *   "\\/"
 * ]
 * Output: 5
 * Explanation: (Recall that because \ characters are escaped, "/\\" refers to /\, and "\\/" refers to \/.)
 * The 2x2 grid is as follows:
 *
 * Example 5:
 *
 * Input:
 * [
 *   "//",
 *   "/ "
 * ]
 * Output: 3
 * Explanation: The 2x2 grid is as follows:
 *
 *
 *
 * Note:
 *
 * 1 <= grid.length == grid[0].length <= 30
 * grid[i][j] is either '/', '\', or ' '.
 *
 */

public class RegionsCutBySlash {

    @Test public void test(){

        int[] test = new int[]{-1, -1};

        Assert.assertEquals(2, regionsBySlashes(new String[]{" /","/ "}));

        Assert.assertEquals(4, regionsBySlashes(new String[]{"\\/","/\\"}));
    }

    //to each grid square, it will be splitted to 4 triangles with '/' and '\',
    //dfs
    public int regionsBySlashes(String[] grid) {

        int n = grid.length; // row and col both are n.
        int m = 4 * n * n;

        boolean[] triangles = new boolean[m]; // default all are false

        int count = 0;
        int curr;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < n; c++){
                curr = (r * n + c) * 4;

                for(int diff = 0; diff < 4; diff++){
                    if(!triangles[curr + diff]){
                        count++;

                        dfs(grid, r, c, diff, triangles);
                    }
                }
            }
        }

        return count;
    }

    private void dfs(String[] grid, int r, int c, int diff, boolean[] triangles){
        int n = grid.length;
        if(r < 0 || r >= n || c < 0 || c >= n){
            return;
        }

        int curr = (r * n + c) * 4;

        if(triangles[curr + diff]){
            return;
        }

        //triangles[curr + diff ] = true;
        helper(grid, r, c, curr, diff, triangles);

        switch (grid[r].charAt(c)){
            case ' ':
                for(int k = 1; k < 4; k++ ){
                    helper(grid, r, c, curr, (diff + k) % 4, triangles);
                }
                break;
            case '/':
                helper(grid, r, c, curr, diff ^ 0b10, triangles);
                break;
            case '\\':
                helper(grid, r, c, curr, diff ^ 0b01, triangles);
                break;
        }
//        char cc = grid[r].charAt(c);
//        if(cc == ' ' ){
//            for(int k = 1; k < 4; k++ ){
//                helper(grid, r, c, curr, (diff + k) % 4, triangles);
//            }
//        }else if(cc == '/'){
//            helper(grid, r, c, curr, diff ^ 0b10, triangles);
//        }else{
//            helper(grid, r, c, curr, diff ^ 0b01, triangles);
//        }

    }

    private void helper(String[] grid, int r, int c, int curr, int diff, boolean[] triangles){
        triangles[curr + diff] = true;

        switch (diff){
            case 0:
                dfs(grid, r - 1, c, 3, triangles);
                break;
            case 1:
                dfs(grid, r, c + 1, 2, triangles);
                break;
            case 2:
                dfs(grid, r, c - 1, 1, triangles);
                break;
            case 3:
                dfs(grid, r + 1, c, 0, triangles);
                break;

        }
//        if(diff == 0){
//            dfs(grid, r - 1, c, 3, triangles);
//        }else if(diff == 1){
//            dfs(grid, r, c + 1, 2, triangles);
//        }else if(diff == 2){
//            dfs(grid, r, c - 1, 1, triangles);
//        }else { // == 3
//            dfs(grid, r + 1, c, 0, triangles);
//        }
    }

}
