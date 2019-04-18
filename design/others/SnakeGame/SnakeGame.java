package fgafa.design.others.SnakeGame;

import fgafa.util.Misc;

import java.util.*;

public class SnakeGame {

    class Point {
        private int r;
        private int c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public boolean equals(Point p) {
            return this.r == p.r && this.c == p.c;
        }

//        public int hashCode() {
//            return Objects.hash(r, c);
//        }
    }

    int rows;
    int cols;
    Deque<Point> snake;
    Set<Point> body; // Used to check if it bites itself
    int[][] food;
    int score;

    /**
     * Initialize your data structure here.
     *
     * @param width  - screen width
     * @param height - screen height
     * @param food   - A list of food positions
     *               E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
     */
    public SnakeGame(int width, int height, int[][] food) {
        this.rows = height;
        this.cols = width;
        this.food = food;
        this.score = 0;

        this.snake = new LinkedList<>();
        this.body = new HashSet<>();

        Point p = new Point(0, 0);
        this.snake.addFirst(p);
        this.body.add(p);
    }

    /**
     * Moves the snake.
     *
     * @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     * @return The game's score after the move. Return -1 if game over.
     * Game over when snake crosses the screen boundary or bites its body.
     */
    public int move(String direction) {
        Point head = snake.peekFirst();
        Point next = new Point(head.r, head.c);

        switch(direction){
            case "U":
                next.r--;
                break;
            case "D":
                next.r++;
                break;
            case "L":
                next.c--;
                break;
            default: //"R"
                next.c++;

        }

        body.remove(snake.peekLast());

        // Check if out of bound, and if it bites itself
        // NOTE: If next is equal to last point, it's considered as valid
        if (next.r < 0 || next.r == rows || next.c < 0 || next.c == cols || body.contains(next)) {
            return -1;
        }

        snake.addFirst(next);
        body.add(next);
        // Check if it reaches food
        if (score < food.length && next.r == food[score][0] && next.c == food[score][1]) {
            score++;
        } else {
            snake.removeLast();
        }
        return score;
    }

    //@Test public void test(){
    public static void main(String[] args){
        //["SnakeGame","move","move","move","move","move","move","move","move","move","move","move","move","move","move","move"]
        //[[3,3,[[2,0],[0,0],[0,2],[0,1],[2,2],[0,1]]],["D"],["D"],["R"],["U"],["U"],["L"],["D"],["R"],["R"],["U"],["L"],["L"],["D"],["R"],["U"]]

        SnakeGame snake = new SnakeGame(3, 3, new int[][]{{2, 0}, {0, 0}, {0, 2}, {0, 1}, {2, 2}, {0, 1} });

        String[] directions = {"D","D","R","U","U","L","D","R","R","U","L","L","D","R","U"};
        int[] result = new int[directions.length];
        int[] expects = {0,1,1,1,1,2,2,2,2,3,4,4,4,4,-1};

        int i = 0;
        for(String direction : directions){
            result[i++] = snake.move(direction);
        }

        System.out.println(Misc.array2String(result));

        //.assertArrayEquals(expects, result);
    }
}