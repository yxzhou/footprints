package game.rubik2D;


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

public class Rubik2D2 {

    // tiles are labeled 1 through 8,  the empty space is labeled 0.
    class BoardStatus{
        int board = 0;  // 8 tiles excludes the empty space
        int position; // the position of the space,  which row

        BoardStatus(int[][] board){
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    if(board[i][j] == 0){
                        position = i * N + j;
                    }else{
                        this.board = (this.board << 4) | board[i][j];
                    }
                }
            }
        }

        BoardStatus(){
        }

        @Override
        public boolean equals(Object obj){
            if(obj == this){
                return true;
            }

            if(obj instanceof BoardStatus){
                BoardStatus bs = (BoardStatus)obj;
                return bs.position == this.position && bs.board == this.board ;
            }

            return false;
        }


    }

    static final int N = 3;

    static BoardStatus finalStatus;

    Rubik2D2(){
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
    static int[][] diffs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // move the space to { up, down, left, right  }
    //static int[] diffs = {-3, 3, -1, 1};
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
            int newX = curr.position / N + diffs[i][0];
            int newY = curr.position % N + diffs[i][1];

            if(newX < 0 || newX >= N || newY < 0 || newY >= N ){
                continue;
            }

            BoardStatus next = new BoardStatus();
            next.position = newX * N + newY;

            if(diffs[i][0] == 1){
                //todo
                //next.board = (curr.board & 0xf00fffff) | ;

            } else if (diffs[i][0] == -1) {
                //todo
            }// to {0, -1}, {0, 1}, next.board needn't be changed

            steps.add(next);

            if(organize(steps, visited, target)){
                return true;
            }

            steps.pop();
        }

        return false;
    }

}
