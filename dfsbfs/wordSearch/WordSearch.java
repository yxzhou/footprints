package dfsbfs.wordSearch;

import junit.framework.Assert;
import matrix.SmallestRectangle;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/123/
 * 
 * Given a 2D board and a word, find if the word exists in the grid.
 *
 * The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally
 * or vertically neighboring. The same letter cell may not be used more than once.
 * 
 * Example 1:
 * Input:
 * board = [ "ABCE", "SFCS", "ADEE"]  word = "ABCCED" 
 * Output: true 
 * Explanation:
 * [    
 *      A B C E
 *      S F C S
 *      A D E E
 * ]
 * (0,0)->(0,1)->(0,2)->(1,2)->(2,2)->(2,1)
 *
 * word = "SEE", -> returns true,
 * word = "ABCB", -> returns false.
 * 
 * Example 2:
 * Input: board = ["z"] word = "z" 
 * Output: true 
 * 
 * Thoughts:
 *  In fact, it's a graph travel.
 * 
 */
public class WordSearch {

    /* */
    public boolean exist(char[][] board, String word) {
        if (null == board || board.length == 0 || null == word) {
            return false;
        }

        int rowsNum = board.length;
        int colsNum = board[0].length;

        boolean[][] visited = new boolean[rowsNum][colsNum]; //default all are false

        for (int row = 0; row < rowsNum; row++) {
            for (int col = 0; col < colsNum; col++) {
                if (dfs(board, row, col, word, 0, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    static int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private boolean dfs(char[][] board, int row, int col, String word, int i, boolean[][] visited) {

        if (row < 0 || col < 0 || row >= board.length || col >= board[0].length || visited[row][col] || word.charAt(i) != board[row][col]) {
            return false;
        }

        if (i == word.length() - 1) {
            return true;
        }

        visited[row][col] = true;

        for (int[] diff : diffs) {
            if (dfs(board, row + diff[0], col + diff[1], word, i + 1, visited)) {
                return true;
            }
        }

        visited[row][col] = false;

        return false;
    }



    public static void main(String[] args){
        String[][][] inputs = {
            {
                {"z"},
                {"z"},
                {"true"}
            },
            {
                {
                    "ABCE",
                    "SFCS",
                    "ADEE"
                },
                {"ABCCED"},
                {"true"}
            }


        };
        
        WordSearch sv = new WordSearch();
        
        char[][] matrix;
        for(String[][] input : inputs){
            matrix= Misc.convert(input[0]);
                    
            Misc.printMetrix(matrix);
            
            Assert.assertEquals(input[2][0], String.valueOf(sv.exist(matrix, input[1][0])) );
            
        }
        
    }
    


}
