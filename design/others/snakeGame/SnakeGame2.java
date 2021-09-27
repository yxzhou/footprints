package design.others.snakeGame;

import util.Misc;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class SnakeGame2 {


    int height;
    int width;
    Deque<Integer> snake;
    Set<Integer> body; // Used to check if it bites itself
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
    public SnakeGame2(int width, int height, int[][] food) {
        this.height = height;
        this.width = width;
        this.food = food;
        this.score = 0;

        this.snake = new LinkedList<>();
        this.body = new HashSet<>();

        this.snake.addFirst(0);
        this.body.add(0);
    }

    /**
     * Moves the snake.
     *
     * @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     * @return The game's score after the move. Return -1 if game over.
     * Game over when snake crosses the screen boundary or bites its body.
     */
    public int move(String direction) {
        Integer head = snake.peekFirst();
        int row = head / width;
        int col = head % width;

        switch(direction){
            case "U":
                row--;
                break;
            case "D":
                row++;
                break;
            case "L":
                col--;
                break;
            default: //"R"
                col++;

        }

        Integer newHead = row * width + col;
        body.remove(snake.peekLast());

        // Check if out of bound, and if it bites itself
        // NOTE: If next is equal to last point, it's considered as valid
        if (row < 0 || row >= height || col < 0 || col >= width || body.contains(newHead)) {
            return -1;
        }

        snake.addFirst(newHead);
        body.add(newHead);
        // Check if it reaches food
        if (score < food.length && row == food[score][0] && col == food[score][1]) {
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

        SnakeGame2 snake = new SnakeGame2(3, 3, new int[][]{{2, 0}, {0, 0}, {0, 2}, {0, 1}, {2, 2}, {0, 1} });

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