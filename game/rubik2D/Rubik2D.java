package fgafa.game.rubik2D;


import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 *
 * This problem was asked by Airbnb.
 *
 * An 8-puzzle is a game played on a 3 x 3 board of tiles, with the ninth tile missing. The remaining tiles are labeled 1 through 8 but shuffled randomly. Tiles may slide horizontally or vertically into an empty space, but may not be removed from the board.
 *
 * Design a class to represent the board, and find a series of steps to bring the board to the state [[1, 2, 3], [4, 5, 6], [7, 8, None]].
 *
 */

public class Rubik2D {

    // tiles are labeled 1 through 8,  the empty space is labeled 0.
    class BoardStatus{
        int[][] board = new int[3][3];

        int x; // the x_position of the space,  which row
        int y; // the y_position of the space,  which column

        BoardStatus(int[][] board){
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if(board[i][j] == 0){
                        x = i;
                        y = j;
                    }

                    this.board[i][j] = board[i][j];
                }
            }
        }

        @Override
        public boolean equals(Object obj){
            //todo
            return false;
        }


    }

    static BoardStatus finalStatus;

    Rubik2D(){
        finalStatus = new BoardStatus(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
    }


    public List<BoardStatus> organize(BoardStatus start){

        Stack<BoardStatus> steps = new Stack<>();
        steps.add(start);

        if(organize(steps, new HashSet<>(), finalStatus)){
            return new LinkedList<>(steps);
        }

        return Collections.emptyList();
    }

    //dfs
    static int[][] diffs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private boolean organize( Stack<BoardStatus> steps, Set<BoardStatus> visited, BoardStatus target){
        BoardStatus curr = steps.peek();

        if(curr.equals(target)){
            return true;
        }

        if(visited.contains(curr)){
            return false;
        }

        visited.add(curr);

        for(int i = 0; i < diffs.length; i++){
            int newX = curr.x + diffs[i][0];
            int newY = curr.y + diffs[i][1];

            if(newX < 0 || newX >= curr.board.length || newY < 0 || newY >= curr.board[0].length ){
                continue;
            }

            BoardStatus next = new BoardStatus(curr.board);

            next.board[next.x][next.y] = next.board[newX][newY];
            next.board[newX][newY] = 0;
            next.x = newX;
            next.y = newY;

            steps.add(next);

            if(organize(steps, visited, target)){
                return true;
            }

            steps.pop();
        }

        return false;
    }

}
