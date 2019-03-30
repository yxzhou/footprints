package fgafa.dailyCoding.bfsdfs;


import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Given a 2D board of characters and a word, find if the word exists in the grid.
 * 
 * The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.
 * 
 * For example, given the following board:
 * 
 * [
 * ['A','B','C','E'],
 * ['S','F','C','S'],
 * ['A','D','E','E']
 * ]
 * 
 * exists(board, "ABCCED") returns true, exists(board, "SEE") returns true, exists(board, "ABCB") returns false.
 *
 * tags: Coursera
 *
 */

public class Search2D {

    public boolean search2D(char[][] board, String target){
        if(null == board || 0 == board.length || 0 == board[0].length || null == target || 0 == target.length()){
            return false;
        }

        boolean[][] visited = new boolean[board.length][board[0].length];

        for(int rowId = 0; rowId < board.length; rowId++){
            for(int columnId = 0; columnId < board[0].length; columnId++){
                if(target.charAt(0) == board[rowId][columnId] && search2D(board, rowId, columnId, target, 1, visited)){
                    return true;
                }
            }
        }

        return false;
    }

    int[][] directions = {{1, 0},{-1, 0},{0, 1},{0, -1}};
    private boolean search2D(char[][] board, int rowId, int columnId, String target, int index, boolean[][] visited){
        if(index == target.length()){
            return true;
        }

        visited[rowId][columnId] = true;

        for(int i = 0; i < directions.length; i++){
            int newRowId = rowId + directions[i][0];
            int newColumnId = columnId + directions[i][1];

            if(newRowId < 0 || newRowId >= board.length || newColumnId < 0 || newColumnId>= board[rowId].length){
                continue;
            }

            if(!visited[newRowId][newColumnId] && target.charAt(index) == board[newRowId][newColumnId] && search2D(board, newRowId, newColumnId, target, index + 1, visited)){
                return true;
            }

        }

        visited[rowId][columnId] = false;

        return false;
    }

    @Test public void test(){
        char[][] board = {
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}};

        System.out.println(search2D(board, "ABCCED"));

        Assert.assertTrue(search2D(board, "ABCCED"));
        Assert.assertTrue(search2D(board, "SEE"));
        Assert.assertFalse(search2D(board, "ABCB"));
    }
}
