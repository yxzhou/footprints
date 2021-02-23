package fgafa.design.others.snakeGame;

import fgafa.util.Misc;

import java.util.*;

public class SnakeGame3 {
    int w; //width
    int h; //heigth
    int[][] foods;
    int f = 0;

    Map<String, Integer> dMap;
    int[][] diffs;

    Deque<Integer> snake;
    Set<Integer> snakeSet;

    /** Initialize your data structure here.
     @param width - screen width
     @param height - screen height
     @param food - A list of food positions
     E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame3(int width, int height, int[][] food) {
        w = width;
        h = height;

        foods = food;

        dMap = new HashMap<>();
        String[] directions = { "U", "L", "R", "D"};
        diffs = new int[][]{{-1, 0}, {0, -1}, {0, 1}, {1, 0}}; //{ Up, Left, Right, Down } (row, col)
        for(int i = 0; i < directions.length; i++){
            dMap.put(directions[i], i);
        }

        snake = new LinkedList<>();
        snake.add(0);
        snakeSet = new HashSet<>();
        snakeSet.add(0);
    }

    /** Moves the snake.
     @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     @return The game's score after the move. Return -1 if game over.
     Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        int head = snake.getFirst();

        int[] d = diffs[dMap.get(direction)];
        int row = head / w + d[0];
        int col = head % w + d[1];

        if(row < 0 || row >= h || col < 0 || col >= w){
            return -1;
        }

        int p = row * w + col;

        if(p != snake.getLast() && snakeSet.contains(p)){
            return -1;
        }

        snake.addFirst(p);
        snakeSet.add(p);
        if(f < foods.length && foods[f][0] == row && foods[f][1] == col){
            f++;
        }else{
            int tail = snake.removeLast();

            if(p != tail){
                snakeSet.remove(tail);
            }
        }

        return f;
    }

    //@Test public void test(){
    public static void main(String[] args){
/**
        //["SnakeGame","move","move","move","move","move","move","move","move","move","move","move","move","move","move","move"]
        //[[3,3,[[2,0],[0,0],[0,2],[0,1],[2,2],[0,1]]],["D"],["D"],["R"],["U"],["U"],["L"],["D"],["R"],["R"],["U"],["L"],["L"],["D"],["R"],["U"]]

        SnakeGame3 snake = new SnakeGame3(3, 3, new int[][]{{2, 0}, {0, 0}, {0, 2}, {0, 1}, {2, 2}, {0, 1} });

        String[] directions = {"D","D","R","U","U","L","D","R","R","U","L","L","D","R","U"};
        int[] result = new int[directions.length];
        int[] expects = {0,1,1,1,1,2,2,2,2,3,4,4,4,4,-1};
**/

/**
//        ["SnakeGame","move","move","move","move","move","move","move","move","move","move","move","move","move","move","move","move","move","move","move"]
//[[3,3,[[0,1},{0,2],[1,2],[2,2],[2,1],[2,0],[1,0]]],["R"],["R"],["D"],["D"],["L"],["L"],["U"],["U"],["R"],["R"],["D"],["D"],["L"],["L"],["U"],["R"],["U"],["L"],["D"]]

        SnakeGame3 snake = new SnakeGame3(3, 3, new int[][]{{0,1},{0,2},{1,2},{2,2},{2,1},{2,0},{1,0}});

        String[] directions = {"R","R","D","D","L","L","U","U","R","R","D","D","L","L","U","R","U","L","D"};
        int[] result = new int[directions.length];
        int[] expects = {1,2,3,4,5,6,7,7,7,7,7,7,7,7,7,7,7,7,-1};
**/

//        ["SnakeGame","move","move","move","move","move","move"]
//[[1,10,[[5,0],[6,0],[0,0]]],["D"],["D"],["D"],["D"],["D"],["D"]]

        SnakeGame3 snake = new SnakeGame3(1, 10, new int[][]{{5,0},{6,0},{0,0}});

        String[] directions = {"D","D","D","D","D","D"};
        int[] result = new int[directions.length];
        int[] expects = {0,0,0,0,1,2};

        int i = 0;
        for(String direction : directions){
            result[i++] = snake.move(direction);
        }

        System.out.println(Misc.array2String(result));
        System.out.println(Misc.array2String(expects));

        //.assertArrayEquals(expects, result);
    }
}